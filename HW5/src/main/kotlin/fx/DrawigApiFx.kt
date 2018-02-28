package fx

import DrawingApi
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.stage.Stage

class DrawigApiFx: DrawingApi {

    class App: Application() {
        companion object {
            val canvas = Canvas(640.0, 480.0)
            val gc = canvas.graphicsContext2D
        }

        override fun start(primaryStage: Stage?) {
            primaryStage?.title = "Api: JavaFx"
            val root = Group()
            root.children.add(canvas)
            primaryStage?.scene = Scene(root)
            primaryStage?.show()
        }
    }

    override fun getDrawingAreaWidth(): Long = App.canvas.width.toLong()

    override fun getDrawingAreaHeight(): Long = App.canvas.height.toLong()

    override fun drawCircle(x: Long, y: Long, r: Long) {
        App.gc.fill = Color.BLACK
        App.gc.fillOval(
                (x - r).toDouble(),
                (y - r).toDouble(),
                (r * 2).toDouble(),
                (r * 2).toDouble())
    }

    override fun drawLine(x1: Long, y1: Long, x2: Long, y2: Long) {
        App.gc.stroke = Color.BLACK
        App.gc.strokeLine(x1.toDouble(), y1.toDouble(), x2.toDouble(), y2.toDouble())
    }

    override fun drawText(x: Long, y: Long, text: String) {
        App.gc.fill = Color.WHITE
        val textToRender = Text(text)
        val h = textToRender.boundsInLocal.height
        val w = textToRender.boundsInLocal.width
        App.gc.fillText(text, x.toDouble() - w / 2, y.toDouble() + h / 2)
    }

    override fun drawLoop(x: Long, y: Long, r: Long) {
        App.gc.stroke = Color.BLACK
        App.gc.strokeOval(
                (x - r).toDouble(),
                (y - r).toDouble(),
                (r * 2).toDouble(),
                (r * 2).toDouble())
    }

    override fun showResult() {
        Application.launch(App::class.java)
    }
}