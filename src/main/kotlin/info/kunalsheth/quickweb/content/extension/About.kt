package info.kunalsheth.quickweb.content.extension

import info.kunalsheth.quickweb.content.QW_HTML
import kotlinx.html.blockQuote
import kotlinx.html.p
import spark.kotlin.RouteHandler

/**
 * Created by kunal on 7/16/17.
 */
object About : info.kunalsheth.quickweb.content.extension.SimplePage() {
    override val content: info.kunalsheth.quickweb.QW_Config.() -> RouteHandler.() -> QW_HTML = {
        {
            {
                blockQuote { +companySlogan }
                p { +companyDescription }
            }
        }
    }
}