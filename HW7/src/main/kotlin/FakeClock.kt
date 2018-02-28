import java.time.Instant

class FakeClock(private var now: Instant = Instant.now()): Clock {

    fun addSeconds(amount: Long) {
        now = now.plusSeconds(amount)
    }

    fun addMinutes(amout: Long) {
        addSeconds(amout * 60)
    }

    fun addHours(amount: Long) {
        addMinutes(amount * 60)
    }

    override fun now(): Instant = now
}