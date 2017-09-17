package info.kunalsheth.quickweb.content

import info.kunalsheth.quickweb.MIME
import info.kunalsheth.quickweb.QW_Config
import kotlinx.html.HtmlBlockTag
import spark.kotlin.*

/**
 * Created by kunal on 7/11/17.
 */
sealed class QW_Object {
    abstract val method: QW_Object.RequestMethod
    open val accept = DEFAULT_ACCEPT
    open val route = "/${this::class.qualifiedName!!.replace('.', '_')}"
    abstract val function: QW_Config.() -> RouteHandler.() -> Any
    abstract fun install(config: QW_Config)

    enum class RequestMethod {
        GET, POST, PUT, DELETE, OPTIONS, ANY;
    }
}

abstract class Behavior : QW_Object() {
    final override val method = QW_Object.RequestMethod.ANY
    abstract override val function: QW_Config.() -> RouteHandler.() -> Unit

    override fun install(config: QW_Config) = config.run {
        when (type) {
            Behavior.BehaviorType.BEFORE -> before(route, accept, function())
            Behavior.BehaviorType.AFTER -> after(route, accept, function())
        }
    }

    abstract val type: Behavior.BehaviorType

    enum class BehaviorType {
        BEFORE, AFTER
    }
}

sealed class Resource : QW_Object() {
    abstract val mime: MIME

    override fun install(config: QW_Config) = config.run {
        val partial: RouteHandler.() -> Any = when (this@Resource) {
            is Data -> dataFunctionModifier()(this@Resource)
            is Page -> pageFunctionModifier()(this@Resource)
        }
        when (method) {
            QW_Object.RequestMethod.GET -> get(route, accept, partial)
            QW_Object.RequestMethod.POST -> post(route, accept, partial)
            QW_Object.RequestMethod.PUT -> put(route, accept, partial)
            QW_Object.RequestMethod.DELETE -> delete(route, accept, partial)
            QW_Object.RequestMethod.OPTIONS -> options(route, accept, partial)
            QW_Object.RequestMethod.ANY -> {
                get(route, accept, partial)
                post(route, accept, partial)
                put(route, accept, partial)
                delete(route, accept, partial)
                options(route, accept, partial)
            }
        }
    }
}

abstract class Data : Resource() {
    abstract override val function: QW_Config.() -> RouteHandler.() -> ByteArray
}

typealias QW_HTML = HtmlBlockTag.() -> Unit

abstract class Page : Resource() {
    open val displayTitle = this::class.simpleName!!.replace(
            """(\p{Ll})(\p{Lu})""".toRegex(),
            "$1 $2"
    ).removePrefix("QW_")
    final override val mime = MIME.`text html`
    abstract override val function: QW_Config.() -> RouteHandler.() -> QW_HTML
}