import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.jackson.jacksonDeserializerOf
import com.github.kittinunf.fuel.jackson.mapper
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*
class TwitterSearchConnection(host: String, private val consumerKey: String, private val consumerSecret: String) {
    var authHeader: String? = null

    class AccessTokenResponse(val token_type: String, val access_token: String)

    init {
        FuelManager.instance.basePath = host
    }

    fun connect() {
        authHeader = "Bearer " + getAccessToken()
    }

    fun getBearerTokenBase64(): String {
        val bearerToken =
                URLEncoder.encode(consumerKey, "UTF-8") +
                ":" +
                URLEncoder.encode(consumerSecret, "UTF-8")
        return String(Base64.getEncoder().encode(bearerToken.toByteArray()))
    }

    fun getAccessToken(): String {
        val (_, _, result) =
                "/oauth2/token"
                        .httpPost(listOf("grant_type" to "client_credentials"))
                        .header("Authorization" to "Basic " + getBearerTokenBase64())
                        .responseObject(jacksonDeserializerOf<AccessTokenResponse>())
        return result.get().access_token
    }

    fun <T: Any> sendRequest(url: String,
                             params: List<Pair<String, String>>,
                             deserializable: Deserializable<T>): T {
        val encodedParams = params.map { it.first to URLEncoder.encode(it.second, "UTF-8") }
        val (_, _, result) =
                url.httpGet(encodedParams)
                    .header("Authorization" to authHeader!!)
                    .response(deserializable)
        return result.get()
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Status(val created_at: Date, val text: String, val id: Long) {
    override fun toString() = "Status(created_at=$created_at)"
}

@JsonIgnoreProperties(ignoreUnknown = true)
class SearchResult(var statuses: MutableList<Status>) {
    override fun toString() = "SearchResult(statuses=$statuses)"
}

class TwitterSearch(val connection: TwitterSearchConnection) {
    init {
        println("Connecting...")
        connection.connect()
        mapper.dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy")
    }

    fun getResults(query: String, max_id: Long? = null): SearchResult {
        val params = mutableListOf(
                "q" to query,
                "result_type" to "recent",
                "count" to "100")

        if (max_id != null) {
            params.add("max_id" to max_id.toString())
        }
        return connection.sendRequest("1.1/search/tweets.json", params, jacksonDeserializerOf())
    }

    fun searchForHours(query: String, hours: Int): Array<Int> {
        if (hours < 0 || hours > 24) {
            throw IllegalArgumentException("hours must be between 0 and 24")
        }
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR_OF_DAY, -hours)
        val dateToCompare = calendar.time
        val results = SearchResult(mutableListOf())
        var counter = 0
        var intermResults: SearchResult
        do {
            println("Search request count: $counter")
            try {
                intermResults = if(results.statuses.isEmpty()) {
                    getResults(query)
                } else {
                    getResults(query, results.statuses.last().id - 1)
                }
                results.statuses.addAll(intermResults.statuses)
                counter++
            } catch (e: HttpException) {
                println("caught exception")
                break
            }
        } while (!intermResults.statuses.isEmpty() && intermResults.statuses.last().created_at.after(dateToCompare))
        val times = Array<Date>(hours + 1) {
            val ret = calendar.time
            calendar.add(Calendar.HOUR_OF_DAY, 1)
            ret
        }
        val resultArray = Array(hours) { 0 }
        for(i in 0 until times.size - 1) {
            for(res in results.statuses) {
                if (res.created_at.after(times[i]) && res.created_at.before(times[i + 1])) {
                    resultArray[i]++
                }
            }
        }
        return resultArray
    }
}