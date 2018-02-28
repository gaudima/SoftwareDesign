package awt

import DrawingApi
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import kotlin.system.exitProcess

class DrawingApiAwt: DrawingApi {
    private class DrawFrame(val commands: MutableList<(Graphics2D) -> Unit>): Frame() {
        override fun paint(gr: Graphics?) {
            val g2 = gr as Graphics2D
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            commands.forEach { command ->
                command(g2)
            }
        }
    }

    private val frame = DrawFrame(mutableListOf())

    init {
        frame.setSize(640, 480)
    }

    override fun getDrawingAreaWidth(): Long = frame.size.width.toLong()

    override fun getDrawingAreaHeight(): Long = frame.size.height.toLong()

    override fun drawCircle(x: Long, y: Long, r: Long) {
        frame.commands.add { g ->
            g.color = Color.black
            g.fill(Ellipse2D.Double(
                    (x - r).toDouble(),
                    (y - r).toDouble(),
                    (r * 2).toDouble(),
                    (r * 2).toDouble()))
        }
    }

    override fun drawLine(x1: Long, y1: Long, x2: Long, y2: Long) {
        frame.commands.add { g ->
            g.color = Color.black
            g.draw(Line2D.Double(x1.toDouble(), y1.toDouble(), x2.toDouble(), y2.toDouble()))
        }
    }

    override fun drawText(x: Long, y: Long, text: String) {
        frame.commands.add { g ->
            g.color = Color.white
            val h = g.fontMetrics.height
            val w = g.fontMetrics.stringWidth(text)
            g.drawString(text, x.toFloat() - w / 2, y.toFloat() + h / 2)
        }
    }

    override fun drawLoop(x: Long, y: Long, r: Long) {
        frame.commands.add { g ->
            g.color = Color.black
            g.draw(Ellipse2D.Double(
                    x.toDouble() - r.toDouble(),
                    y.toDouble() - r.toDouble(),
                    (r * 2).toDouble(),
                    (r * 2).toDouble()))
        }
    }

    override fun showResult() {
        frame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(p0: WindowEvent?) {
                exitProcess(0)
            }
        })
        frame.title = "Api: JavaAwt"
        frame.isVisible = true
    }
}