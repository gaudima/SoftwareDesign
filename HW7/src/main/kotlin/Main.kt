fun main(args: Array<String>) {
    val stat = LastHourEventStatistic()
    stat.incEvent("event0")
    stat.incEvent("event1")
    stat.incEvent("event0")
    stat.printStatistic()
}