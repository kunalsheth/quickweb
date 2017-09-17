package info.kunalsheth.quickweb

import spark.kotlin.port
import spark.kotlin.secure

/**
 * Created by kunal on 7/12/17.
 */
object LibQuickWeb {
    fun start(config: info.kunalsheth.quickweb.QW_Config, vararg content: info.kunalsheth.quickweb.content.QW_Object) = config.run {
        port(port)

        if (keystore != null && keystorePassword != null) {
            val keystorePassword = keystorePassword.invoke()
            secure(
                    keystore.path, keystorePassword,
                    trustStore?.path ?: keystore.path,
                    trustStorePassword?.invoke() ?: keystorePassword
            )
        }

        val loginPages = if (config.login != null) setOf(
                config.login,
                config.login.forgotPassword,
                config.login.signUp,
                config.login.callback,
                config.login.logout
        ) else emptySet<info.kunalsheth.quickweb.content.QW_Object>()

        (config.navBarPages + config.footerPages + loginPages + content)
                .distinct()
                .forEach {
                    it.install(config)
                    println("Installed ${it.route}")
                }
        Unit
    }
}