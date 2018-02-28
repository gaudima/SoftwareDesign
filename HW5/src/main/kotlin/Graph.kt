import awt.DrawingApiAwt
import fx.DrawigApiFx

class Graph(protected val drawingApi: DrawingApi) {
    private var graph: Array<Array<Int>?> = arrayOf()

    companion object {
        fun getGraphForApiName(apiName: String): Graph? {
            if (apiName == "JavaAwt") {
                return Graph(DrawingApiAwt())
            } else if (apiName == "JavaFx") {
                return Graph(DrawigApiFx())
            }
            return null
        }
    }

    fun setGrpahMatrix(graph: Array<Array<Int>>) {
        this.graph = Array(graph.size) { null }
        graph.forEachIndexed { index, it ->
            val row = mutableListOf<Int>()
            it.forEachIndexed { index2, it2 ->
                if (it2 != 0) {
                    row.add(index2)
                }
            }
            this.graph[index] = row.toTypedArray()
        }
    }

    fun setGraphList(graph: Array<Array<Int>>) {
        this.graph = Array(graph.size) {
            Array(graph[it].size) { itt ->
                graph[it][itt]
            }
        }
    }

    fun drawGraph() {
        val xCenter = drawingApi.getDrawingAreaWidth() / 2
        val yCenter = drawingApi.getDrawingAreaHeight() / 2
        val circleRadius = 150
        graph.forEachIndexed { index, it ->
            val angle1 = Math.PI * 2 / graph.size * index
            val dX1 = circleRadius * Math.sin(angle1)
            val dY1 = -circleRadius * Math.cos(angle1)
            it?.forEachIndexed { _, it2  ->
                if (it2 != index) {
                    val angle2 = Math.PI * 2 / graph.size * it2
                    val dX2 = circleRadius * Math.sin(angle2)
                    val dY2 = -circleRadius * Math.cos(angle2)
                    drawingApi.drawLine(
                            xCenter + dX1.toLong(),
                            yCenter + dY1.toLong(),
                            xCenter + dX2.toLong(),
                            yCenter + dY2.toLong())
                } else {
                    val ddX1 = dX1 + 15 * Math.sin(angle1)
                    val ddY1 = dY1 - 15 * Math.cos(angle1)
                    drawingApi.drawLoop(xCenter + ddX1.toLong(), yCenter + ddY1.toLong(), 15)
                }
            }
        }
        graph.forEachIndexed { index, _ ->
            val angle = Math.PI * 2 / graph.size * index
            val dX = circleRadius * Math.sin(angle)
            val dY = -circleRadius * Math.cos(angle)
            drawingApi.drawCircle(xCenter + dX.toLong(), yCenter + dY.toLong(), 15)
            drawingApi.drawText(xCenter + dX.toLong(), yCenter + dY.toLong(), index.toString())
        }
        drawingApi.showResult()
    }
}