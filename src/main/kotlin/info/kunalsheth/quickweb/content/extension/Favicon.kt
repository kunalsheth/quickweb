package info.kunalsheth.quickweb.content.extension

import info.kunalsheth.quickweb.MIME
import info.kunalsheth.quickweb.QW_Config
import info.kunalsheth.quickweb.content.Data
import info.kunalsheth.quickweb.content.QW_Object
import spark.kotlin.RouteHandler

/**
 * Created by kunal on 7/16/17.
 */
class Favicon(
        pngData: ByteArray = ClassLoader.getSystemClassLoader().getResourceAsStream("favicon.ico").readBytes()
) : Data() {
    override val method = QW_Object.RequestMethod.GET
    override val mime = MIME.`image png`
    override val function: QW_Config.() -> RouteHandler.() -> ByteArray = { { pngData } }
}