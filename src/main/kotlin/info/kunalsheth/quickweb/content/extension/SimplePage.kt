package info.kunalsheth.quickweb.content.extension

import info.kunalsheth.quickweb.QW_Config
import info.kunalsheth.quickweb.content.Page
import info.kunalsheth.quickweb.content.QW_HTML
import kotlinx.html.h2
import spark.kotlin.RouteHandler

/**
 * Created by kunal on 7/16/17.
 */
abstract class SimplePage : Page() {
    override val method = RequestMethod.GET
    final override val function: QW_Config.() -> RouteHandler.() -> QW_HTML = {
        {
            {
                h2 { +displayTitle }
                content()()()
            }
        }
    }
    abstract val content: QW_Config.() -> RouteHandler.() -> QW_HTML
}