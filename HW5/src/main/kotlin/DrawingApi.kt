interface DrawingApi {
    fun getDrawingAreaWidth(): Long
    fun getDrawingAreaHeight(): Long
    fun drawCircle(x: Long, y: Long, r: Long)
    fun drawLoop(x: Long, y: Long, r: Long)
    fun drawLine(x1: Long, y1: Long, x2: Long, y2: Long)
    fun drawText(x: Long, y: Long, text: String)
    fun showResult()
}