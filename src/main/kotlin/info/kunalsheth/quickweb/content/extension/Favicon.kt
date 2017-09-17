package info.kunalsheth.quickweb.content.extension

import spark.kotlin.RouteHandler

/**
 * Created by kunal on 7/16/17.
 */
class Favicon(
        pngData: ByteArray = ClassLoader.getSystemClassLoader().getResourceAsStream("favicon.ico").readBytes()
) : info.kunalsheth.quickweb.content.Data() {
    override val method = RequestMethod.GET
    override val mime = info.kunalsheth.quickweb.MIME.`image png`
    override val function: info.kunalsheth.quickweb.QW_Config.() -> RouteHandler.() -> ByteArray = { { pngData } }
}