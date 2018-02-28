import java.time.Duration
import java.time.Instant

class LastHourEventStatistic(private val clock: Clock = NormalClock()): EventStatistic {
    private var statisticMap = mutableMapOf<String, Array<Int>>()
    private var lastUpdateTime = clock.now()

    private fun updateMap(now: Instant) {
        val duration = Duration.between(lastUpdateTime, now)
        val minutes = duration.toMinutes()
        statisticMap.forEach { statElem ->
            (statElem.value.size - minutes - 1 downTo 0).forEach {
                statElem.value[it.toInt() + minutes.toInt()] = statElem.value[it.toInt()]
            }
            for (it in 0 until Math.min(minutes, statElem.value.size.toLong())) {
                statElem.value[it.toInt()] = 0
            }
        }
        lastUpdateTime = now
    }

    override fun incEvent(name: String) {
        updateMap(clock.now())
        if(!statisticMap.containsKey(name)) {
            statisticMap[name] = Array(60) {0}
        }
        statisticMap[name]!![0]++
    }

    override fun getEventStatisticByName(name: String): Array<Int> {
        updateMap(clock.now())
        if (statisticMap[name] != null) {
            return statisticMap[name]!!
        } else {
            throw Error("No such event name")
        }
    }

    override fun getAllEventStatistic(): Array<Int> {
        updateMap(clock.now())
        val ret = Array(60) {0}
        statisticMap.forEach{ entry ->
            entry.value.forEachIndexed { index, it ->
                ret[index] += it
            }
        }
        return ret
    }

    override fun printStatistic() {
        updateMap(clock.now())
        statisticMap.forEach {
            println("Event ${it.key} rpm:")
            print("[")
            it.value.forEachIndexed { index, rpm ->
                print("$rpm")
                if(index != it.value.size - 1) {
                    print(" ")
                }
            }
            println("]")
        }
    }
}