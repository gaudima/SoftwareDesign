import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object LastHourEventStatisticSpec: Spek({
    given("Last hour event statistics class") {
        val clock = FakeClock()
        val eventStat = LastHourEventStatistic(clock)
        on("get event statistics [no events]") {
            try {
                eventStat.getEventStatisticByName("event0")
            } catch (e: Error) {
                assertThat(e.message, equalTo("No such event name"))
            }
        }
        on("event0 increment") {
            eventStat.incEvent("event0")
            val rpm = eventStat.getEventStatisticByName("event0").toList()
            it("should increment event0 rpm[0]") {
                assertThat(rpm, equalTo(listOf(
                        1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                )))
            }
        }
        on("event0 increment after minute") {
            clock.addMinutes(1)
            eventStat.incEvent("event0")
            val rpm = eventStat.getEventStatisticByName("event0").toList()
            it("should shift rpm right and increment event0 rpm[0]") {
                assertThat(rpm, equalTo(listOf(
                        1, 1, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                )))
            }
        }
        on("event1 increment") {
            eventStat.incEvent("event1")
            val rpm = eventStat.getEventStatisticByName("event1").toList()
            it("should increment event1 rpm[0]") {
                assertThat(rpm, equalTo(listOf(
                        1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                )))
            }
            val allRpm = eventStat.getAllEventStatistic().toList()
            it("all event statistic should update") {
                assertThat(allRpm, equalTo(listOf(
                        2, 1, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                )))
            }
        }
        on("event0 increment after 10 minutes") {
            clock.addMinutes(10)
            eventStat.incEvent("event0")
            val rpm = eventStat.getEventStatisticByName("event0").toList()
            it("should shift rpm right 10 places and increment event0 rpm[0]") {
                assertThat(rpm, equalTo(listOf(
                        1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        1, 1, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                )))
            }
            val rpm1 = eventStat.getEventStatisticByName("event1").toList()
            it("should shift rpm of event1 right 10 places") {
                assertThat(rpm1, equalTo(listOf(
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                )))
            }
            val allRpm = eventStat.getAllEventStatistic().toList()
            it("should shift rpm of event1 right 10 places") {
                assertThat(allRpm, equalTo(listOf(
                        1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        2, 1, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                )))
            }
        }
        on("hour passed no event increments") {
            clock.addHours(1)
            val rpm = eventStat.getEventStatisticByName("event0").toList()
            it("should clear rpm of event0") {
                assertThat(rpm, equalTo(listOf(
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                )))
            }
            val rpm1 = eventStat.getEventStatisticByName("event1").toList()
            it("should clear rpm of event1") {
                assertThat(rpm1, equalTo(listOf(
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                )))
            }
            val rpmAll = eventStat.getAllEventStatistic().toList()
            it("should clear rpm of all") {
                assertThat(rpmAll, equalTo(listOf(
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                )))
            }
        }
    }
})