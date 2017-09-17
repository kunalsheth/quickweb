package info.kunalsheth.quickweb

import spark.kotlin.RouteHandler
import java.io.File

/**
 * Created by kunal on 7/7/17.
 */
data class QW_Config(
        val keystore: File? = null,
        val keystorePassword: (() -> String)? = null,
        val trustStore: File? = keystore,
        val trustStorePassword: (() -> String)? = null,

        val port: Int = 6402,
        //        val host: String = "localhost",
//        val baseUrl: String = "http${if (keystore != null) "s" else ""}://$host:$port",

        val brandName: String = "Kunal Sheth & Co.",
        val companySlogan: String = "Kunal is just really, really awesome!",
        val companyDescription: String = "[insert Kunal's life story here]",

        val pageTitleModifier: (String) -> String = { "$brandName | $it" },
        val pageFunctionModifier: info.kunalsheth.quickweb.QW_Config.() -> info.kunalsheth.quickweb.content.Page.() -> RouteHandler.() -> Any = info.kunalsheth.quickweb.pageFunctionModifier,
        val dataFunctionModifier: info.kunalsheth.quickweb.QW_Config.() -> info.kunalsheth.quickweb.content.Data.() -> RouteHandler.() -> Any = info.kunalsheth.quickweb.dataFunctionModifier,

        val login: info.kunalsheth.quickweb.content.extension.login.Login? = null,
        val navBarPages: Iterable<info.kunalsheth.quickweb.content.Page> = emptyList(),
        val footerPages: Iterable<info.kunalsheth.quickweb.content.Page> = emptyList()
)