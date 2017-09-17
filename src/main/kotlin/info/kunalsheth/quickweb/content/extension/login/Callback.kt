package info.kunalsheth.quickweb.content.extension.login

import info.kunalsheth.quickweb.QW_Config
import info.kunalsheth.quickweb.content.Page
import info.kunalsheth.quickweb.content.QW_HTML
import org.pac4j.core.config.Config
import org.pac4j.sparkjava.CallbackRoute
import spark.Spark.*
import spark.kotlin.RouteHandler

/**
 * Created by kunal on 7/20/17.
 */
class Callback(pac4jConfig: Config) : Page() {
    val callbackRoute = CallbackRoute(pac4jConfig)

    override fun install(config: QW_Config) {
        get(route, callbackRoute)
        post(route, callbackRoute)
        put(route, callbackRoute)
        delete(route, callbackRoute)
        options(route, callbackRoute)
    }

    override val method = RequestMethod.ANY
    override val function: QW_Config.() -> RouteHandler.() -> QW_HTML = { { {} } }
}