package info.kunalsheth.quickweb

import info.kunalsheth.quickweb.content.Data
import info.kunalsheth.quickweb.content.Page
import info.kunalsheth.quickweb.content.extension.login.profileManager
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import spark.kotlin.RouteHandler

/**
 * Created by kunal on 7/7/17.
 */
val pageFunctionModifier: QW_Config.() -> Page.() -> RouteHandler.() -> String = {
    {
        {
            type(mime.type)
            createHTML().run {
                html {
                    lang = "en"
                    head {
                        unsafe { +libbootstrap }
                        title { +pageTitleModifier(displayTitle) }
                        meta(name = "author", content = "Kunal Sheth")
                        meta(name = "description", content = companyDescription)
                    }
                    body {
                        nav(classes = "navbar navbar-default navbar-fixed-top") {
                            div(classes = "container") {
                                div(classes = "navbar-header") {
                                    button(type = ButtonType.button, classes = "navbar-toggle collapsed") {
                                        attributes.putAll(mapOf(
                                                "data-toggle" to "collapse",
                                                "data-target" to "#navbar",
                                                "aria-expanded" to "false",
                                                "aria-controls" to "navbar"
                                        ))
                                        span(classes = "sr-only") { +"Toggle navigation" }
                                        span(classes = "icon-bar")
                                        span(classes = "icon-bar")
                                        span(classes = "icon-bar")
                                    }
                                    a(href = "/", classes = "navbar-brand") { +brandName }
                                }
                                div(classes = "collapse navbar-collapse") {
                                    id = "navbar"
                                    ul(classes = "nav navbar-nav") {
                                        navBarPages.forEach {
                                            li { a(href = it.route) { +it.displayTitle } }
                                        }
                                        if (login != null) {
                                            if (!profileManager().isAuthenticated) {
                                                li { a(href = login.route) { +login.displayTitle } }
                                            } else {
                                                li { a(href = login.logout.route) { +login.logout.displayTitle } }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        div(classes = "container") {
                            style = "padding-top: 60px;"

                            function()()()

                            footer(classes = "footer") {
                                div(classes = "container text-muted") {
                                    footerPages.forEach {
                                        span {
                                            style = "float: right;"
                                            style += "white-space: nowrap;"
                                            style += "padding: 10px 15px;"
                                            a(href = it.route) { +it.displayTitle }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

val dataFunctionModifier: QW_Config.() -> Data.() -> RouteHandler.() -> ByteArray = {
    {
        {
            type(mime.type)
            function()()
        }
    }
}

private val libbootstrap = """
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js" integrity="sha384-xBuQ/xzmlsLoJpyjoggmTEz8OWUFM0/RC5BsqQBDX2v5cMvDHcMakNTNrHIW2I5f" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
"""