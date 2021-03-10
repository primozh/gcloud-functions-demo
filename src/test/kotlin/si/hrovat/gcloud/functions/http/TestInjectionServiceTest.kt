package si.hrovat.gcloud.functions.http

import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
internal class TestInjectionServiceTest {

    @Inject
    lateinit var testService: TestInjectionService

    @Test
    fun `test inject method`() {
        assertEquals("Hello from gCloud!", testService.testInjectMethod())
    }

    @Test
    fun `test inject method with param`() {
        val name = "Primoz"
        assertEquals("Hello $name from gCloud!", testService.testInjectMethodWithParam(name))
    }
}