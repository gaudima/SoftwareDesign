import com.github.kittinunf.fuel.core.Deserializable
import com.github.kittinunf.fuel.jackson.jacksonDeserializerOf
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import io.mockk.every
import io.mockk.mockk
import java.util.*

object TwitterSearchSpec: Spek({
    given("Twitter search class") {
        val calendar = Calendar.getInstance()
        val statuses = mutableListOf<Status>()
        (1..3).forEach {
            statuses.add(Status(calendar.time, "tweet #$it", it.toLong()))
        }
        val fakeResults = SearchResult(statuses)
        val connection = mockk<TwitterSearchConnection>()
        every {
            connection.sendRequest(any(), any(), any<Deserializable<SearchResult>>())
        } returns fakeResults
        every {
            connection.connect()
        } returns Unit
        val search = TwitterSearch(connection)
        on("get tweets for last 24 hours") {
            val res = search.searchForHours("", 24).toList()
            it("result should be [3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3]") {
                assert.that(res,
                        equalTo(listOf(3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3)))
            }
        }
    }
})