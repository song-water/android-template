import com.water.song.template.network.test.GITHUB_API_USER_DATA
import com.water.song.template.network.test.GithubService
import com.water.song.template.runBlockingTest
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.delay
import kotlin.test.Test
import kotlin.test.assertEquals

private val Url.hostWithPortIfRequired: String
    get() = if (port == protocol.defaultPort) { host } else { hostWithPort }

private val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"

private const val API_URL = "https://api.github.com/users"
private const val NORMAL_USER = "jetbrains"
private const val TIMEOUT_USER = "timeout_jetbrains"
private const val DELAY_USER = NORMAL_USER
private const val NORMAL_API_URL = "$API_URL/$NORMAL_USER"
private const val TIMEOUT_API_URL = "$API_URL/$TIMEOUT_USER"

private fun MockRequestHandleScope.responseNormalApiJsonData(): HttpResponseData {
    val responseHeaders =
        headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
    return respond(GITHUB_API_USER_DATA, headers = responseHeaders)
}

internal val client = HttpClient(MockEngine) {
    engine {
        addHandler { request ->
            when (request.url.fullUrl) {
                NORMAL_API_URL -> {
                    responseNormalApiJsonData()
                }
                TIMEOUT_API_URL -> {
                    delay(3000)
                    responseNormalApiJsonData()
                }
                else -> {
                    error("Unhandled ${request.url.fullUrl}")
                }
            }
        }
    }
}

private data class Result(
    var successContent: String = "",
    var failureContent: String = "",
    var completeState: Boolean = true
)

class GithubServiceTest {

    @Test
    fun test_githubUser_normal() = runBlockingTest {
        val expectResult = Result(successContent = GITHUB_API_USER_DATA, completeState = true)
        val actualResult = Result()
        GithubService.getInstance().githubUser(client, NORMAL_USER)
            .onSuccess(coroutineContext) {
                actualResult.successContent = it.value
            }
            .onFailure(coroutineContext) {
                actualResult.failureContent = it.error.description
            }
            .onComplete(coroutineContext) {
                actualResult.completeState = it
            }
            .call(timeoutMillis = 1000)
            .join()
        assertEquals(expectResult, actualResult)
    }

    @Test
    fun test_githubUser_timeout() = runBlockingTest {
        val expectResult = Result(
            failureContent = "Timed out waiting for 1000 ms",
            completeState = false
        )
        val actualResult = Result()
        GithubService.getInstance().githubUser(client, TIMEOUT_USER)
            .onSuccess(coroutineContext) {
                actualResult.successContent = it.value
            }
            .onFailure(coroutineContext) {
                actualResult.failureContent = it.error.description
            }
            .onComplete(coroutineContext) {
                actualResult.completeState = it
            }
            .call(timeoutMillis = 1000)
            .join()
        assertEquals(expectResult, actualResult)
    }

    @Test
    fun test_githubUser_delay() = runBlockingTest {
        val expectResult = Result(successContent = GITHUB_API_USER_DATA, completeState = true)
        val actualResult = Result()
        GithubService.getInstance().githubUser(client, DELAY_USER)
            .onSuccess(coroutineContext) {
                actualResult.successContent = it.value
            }
            .onFailure(coroutineContext) {
                actualResult.failureContent = it.error.description
            }
            .onComplete(coroutineContext) {
                actualResult.completeState = it
            }
            .delayCall(timeoutMillis = 1000, delayMillis = 1000)
            .join()
        assertEquals(expectResult, actualResult)
    }
}