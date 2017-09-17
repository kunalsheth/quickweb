package info.kunalsheth.quickweb.content.extension.login

import info.kunalsheth.quickweb.QW_Config
import info.kunalsheth.quickweb.content.Page
import info.kunalsheth.quickweb.content.QW_HTML
import org.pac4j.core.config.Config
import org.pac4j.sparkjava.LogoutRoute
import spark.kotlin.RouteHandler

/**
 * Created by kunal on 7/20/17.
 */
class Logout(pac4jConfig: Config) : Page() {
    val logoutRoute = LogoutRoute(pac4jConfig, "/")

    override fun install(config: QW_Config) {
        spark.Spark.get(route, logoutRoute)
    }

    override val method = RequestMethod.ANY
    override val function: QW_Config.() -> RouteHandler.() -> QW_HTML = { { {} } }
}