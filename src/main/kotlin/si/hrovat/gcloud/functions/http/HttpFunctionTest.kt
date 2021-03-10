package si.hrovat.gcloud.functions.http

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@ApplicationScoped
@Path("/hello")
class DemoResource @Inject constructor(val testService: TestInjectionService) {

    @Path("/{name}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun helloName(@PathParam("name") name: String): Response =
        Response.ok(testService.testInjectMethodWithParam(name)).build()
}

@ApplicationScoped
class TestInjectionService {

    fun testInjectMethod(): String = "Hello from gCloud!"
    fun testInjectMethodWithParam(name: String): String = "Hello $name from gCloud!"
}