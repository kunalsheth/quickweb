package info.kunalsheth.quickweb.content.extension

import info.kunalsheth.quickweb.QW_Config
import info.kunalsheth.quickweb.content.QW_HTML
import kotlinx.html.blockQuote
import kotlinx.html.p
import spark.kotlin.RouteHandler

/**
 * Created by kunal on 7/16/17.
 */
object About : SimplePage() {
    override val content: QW_Config.() -> RouteHandler.() -> QW_HTML = {
        {
            {
                blockQuote { +companySlogan }
                p { +companyDescription }
            }
        }
    }
}