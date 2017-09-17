package info.kunalsheth.quickweb.content.extension.login

import info.kunalsheth.quickweb.content.QW_HTML
import kotlinx.html.*
import org.pac4j.core.context.Pac4jConstants
import org.pac4j.core.credentials.UsernamePasswordCredentials
import org.pac4j.core.profile.CommonProfile
import org.pac4j.core.profile.ProfileManager
import org.pac4j.http.client.indirect.FormClient
import org.pac4j.sparkjava.DefaultHttpActionAdapter
import org.pac4j.sparkjava.SecurityFilter
import org.pac4j.sparkjava.SparkWebContext
import spark.kotlin.RouteHandler

/**
 * Created by kunal on 7/16/17.
 */
abstract class Login : info.kunalsheth.quickweb.content.extension.SimplePage() {

    open val client = {
        FormClient(route, { credentials, context ->
            credentials.userProfile = (context!! as SparkWebContext)
                    .routeHandler()
                    .authenticate(credentials as UsernamePasswordCredentials)
        })
    }()

    open val pac4jConfig = {
        val pac4jConfig = org.pac4j.core.config.Config(client)
        pac4jConfig.clients.defaultClient = client
        pac4jConfig.httpActionAdapter = DefaultHttpActionAdapter()
        pac4jConfig
    }()

    open val callback = {
        val callback = info.kunalsheth.quickweb.content.extension.login.Callback(pac4jConfig)
        pac4jConfig.clients.callbackUrl = callback.route
        callback
    }()

    open val logout = Logout(pac4jConfig)

    abstract val signUp: info.kunalsheth.quickweb.content.Page
    abstract val forgotPassword: info.kunalsheth.quickweb.content.Page

    abstract val authenticate: RouteHandler.(UsernamePasswordCredentials) -> CommonProfile
    abstract val authorizers: Map<info.kunalsheth.quickweb.content.Resource, RouteHandler.(Collection<CommonProfile?>) -> Boolean>

    override fun install(config: info.kunalsheth.quickweb.QW_Config) {
        super.install(config)

        authorizers.forEach { (resource, authorizer) ->
            val authorizerName = authorizer.hashCode().toString()

            pac4jConfig.addAuthorizer(authorizerName) { context, profiles ->
                (context as SparkWebContext)
                        .routeHandler()
                        .authorizer(profiles)
            }

            spark.Spark.before(
                    resource.route,
                    SecurityFilter(pac4jConfig, client.name, authorizerName)
            )
        }
    }

    override val content: info.kunalsheth.quickweb.QW_Config.() -> RouteHandler.() -> QW_HTML = {
        {
            {
                div {
                    style = "max-width: 330px;"
                    style += "margin: 0 auto;"

                    form(action = callback.route, method = FormMethod.post) {
                        input(type = InputType.email, classes = "form-control", name = Pac4jConstants.USERNAME) {
                            placeholder = "Email Address"
                            required = true
                            autoFocus = true
                            value = request.queryParamOrDefault(Pac4jConstants.USERNAME, "")
                        }

                        input(type = InputType.password, classes = "form-control", name = Pac4jConstants.PASSWORD) {
                            placeholder = "Password"
                            required = true
                        }

                        div(classes = "checkbox") {
                            label {
                                input(type = InputType.checkBox, name = rememberMe) {
                                    +" Remember me\n"
                                }
                            }
                        }

                        if (request.queryParams("error") != null)
                            div("alert alert-danger") { +"Invalid Credentials" }

                        button(classes = "btn btn-primary btn-block") { +"Sign in" }
                    }
                    p("text-muted") {
                        +"Don't have an account? "
                        a(signUp.route) { +signUp.displayTitle }
                        br
                        +"Forgot Your Password? "
                        a(forgotPassword.route) { +forgotPassword.displayTitle }
                    }
                }
            }
        }
    }
}

val rememberMe = "remember-me"
fun SparkWebContext.routeHandler() = RouteHandler(sparkRequest, sparkResponse)
fun SparkWebContext.profileManager() = ProfileManager<CommonProfile>(this)
fun RouteHandler.sparkWebContext() = SparkWebContext(request, response)
fun RouteHandler.profileManager() = sparkWebContext().profileManager()