interface EventStatistic {
    fun incEvent(name: String)
    fun getEventStatisticByName(name: String): Array<Int>
    fun getAllEventStatistic(): Array<Int>
    fun printStatistic()
}