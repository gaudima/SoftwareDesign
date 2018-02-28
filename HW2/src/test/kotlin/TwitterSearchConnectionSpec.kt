import com.github.kittinunf.fuel.core.deserializers.StringDeserializer
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.xebialabs.restito.builder.stub.StubHttp.whenHttp
import com.xebialabs.restito.semantics.Action.*
import com.xebialabs.restito.semantics.Condition.*
import com.xebialabs.restito.server.StubServer

object TwitterSearchConnectionSpec: Spek({

    given("Twitter search connection class") {
        var stubServer: StubServer? = null
        beforeGroup {
            stubServer = StubServer(6061).run()
            whenHttp(stubServer)
                    .match(post("/oauth2/token")/*, withHeader("Authorization", "Basic a2V5OnNlY3JldA==")*/)
                    .then(ok(), stringContent("{\"token_type\":\"bearer\",\"access_token\":\"acc_token\"}"))
            whenHttp(stubServer)
                    .match(get("/test"), withHeader("Authorization", "Bearer acc_token"))
                    .then(ok(), stringContent("response"))

        }

        val searchConnection = TwitterSearchConnection("http://localhost:6061", "key", "secret")
        on("get bearer base64 encoded token") {
            val token = searchConnection.getBearerTokenBase64()
            it("should return a2V5OnNlY3JldA==") {
                assert.that(token, equalTo("a2V5OnNlY3JldA=="))
            }
        }

        on("get access token from server") {
            val accessToken = searchConnection.getAccessToken()
            it("access token should be equal to acc_token") {
                assert.that(accessToken, equalTo("acc_token"))
            }
        }

        on("connection") {
            searchConnection.connect()
            it("searchConnection.authHeader should be equal to 'Bearer acc_token'") {
                assert.that(searchConnection.authHeader, equalTo("Bearer acc_token"))
            }
        }

        on("on send request") {
            val resp = searchConnection.sendRequest("/test", listOf(), StringDeserializer())
            it("response should equal to 'response'") {
                assert.that(resp, equalTo("response"))
            }
        }

        afterGroup {
            stubServer?.stop()
        }
    }
})