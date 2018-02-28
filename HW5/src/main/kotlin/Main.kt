import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import com.xenomachina.argparser.mainBody
import kotlin.system.exitProcess

class Args(parser: ArgParser) {
    val api by parser.storing(
            "-a",
            "--api",
            help = "drawing api name (JavaAwt or JavaFx)").default("JavaAwt")
    val matrixRepr by parser.flagging(
            "-m",
            "--matrix",
            help = "toggle matrix representation").default(false)
}

fun main(args: Array<String>) = mainBody("Graph visualizer") {
    Args(ArgParser(args)).run {
        val graph = Graph.getGraphForApiName(api)
        val n = readLine()?.toInt()
        if(n == null) {
            println("Wrong graph file format")
            exitProcess(-1)
        }
        if (graph != null) {
            val graphRepr = Array(n) { arrayOf<Int>() }
            (0 until n).forEach {
                val l = readLine()
                if (l != null) {
                    graphRepr[it] = l.split(" ").map {
                        it.toInt()
                    }.toTypedArray()
                }
                if (l == null || matrixRepr && graphRepr[it].size != n) {
                    println("Wrong graph file format")
                    exitProcess(-1)
                }
            }

            if (matrixRepr) {
                graph.setGrpahMatrix(graphRepr)
            } else {
                graph.setGraphList(graphRepr)
            }
            graph.drawGraph()
        } else {
            print("No such drawing api")
            exitProcess(-1)
        }
    }
}