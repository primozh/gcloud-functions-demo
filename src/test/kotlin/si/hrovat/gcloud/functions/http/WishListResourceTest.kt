package si.hrovat.gcloud.functions.http

import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.Header
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import java.util.*
import javax.enterprise.context.ApplicationScoped

@QuarkusTest
@TestHTTPEndpoint(WishListResource::class)
internal class WishListResourceTest {

    @Test
    fun `test create wish list`() {
        val wishList = WishList(null, "test", null)
        val mock = Mockito.mock(WishListService::class.java)
        Mockito.`when`(mock.createList(wishList))
            .thenReturn(WishList(UUID.randomUUID().toString(), wishList.name, null))
        QuarkusMock.installMockForType(mock, WishListService::class.java)

        val objectMapper = ObjectMapper()
        Given {
            body(objectMapper.writeValueAsString(wishList))
            header(Header("Content-Type", "application/json"))
        } When {
            post()
        } Then {
            statusCode(201)
            header("Location", notNullValue())
        }
    }
}