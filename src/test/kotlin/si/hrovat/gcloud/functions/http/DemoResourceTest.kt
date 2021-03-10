package si.hrovat.gcloud.functions.http

import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

@QuarkusTest
@TestHTTPEndpoint(DemoResource::class)
internal class DemoResourceTest {

    @Test
    fun `test hello endpoint`() {
        val name = "Primoz"
        When {
            get("/{name}", name)
        } Then {
            statusCode(200)
            body(equalTo("Hello $name from gCloud!"))
        }
    }
}