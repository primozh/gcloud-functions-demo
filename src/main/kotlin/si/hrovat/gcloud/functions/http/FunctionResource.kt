package si.hrovat.gcloud.functions.http

import com.fasterxml.jackson.annotation.JsonProperty
import io.smallrye.common.annotation.Blocking
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.slf4j.LoggerFactory
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

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
@Path("wish-list")
class WishListResource @Inject constructor(val wishListService: WishListService, val uriInfo: UriInfo) {

    companion object {
        private val logger = LoggerFactory.getLogger(WishListResource::class.java)
    }

    @POST
    @Blocking
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun createWishList(item: WishList): Response {
        logger.info("Received wish list {}", item)
        val wishList = wishListService.createList(item)
        return Response.created(uriInfo.requestUriBuilder.path(wishList?.id).build()).entity(wishList).build()
    }

    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun addToWishList(@PathParam("id") id: String, item: WishListItem): Response {
        val wishList = wishListService.addItem(id, item)
        return Response.created(uriInfo.requestUriBuilder.path(wishList.id).build()).entity(wishList).build()
    }

    @GET
    @Path("/{id}")
    @Blocking
    @Produces(MediaType.APPLICATION_JSON)
    fun getWishList(@PathParam("id") id: String): Response {
        val wishList = wishListService.getWishList(id)
        return Response.ok(wishList).build()
    }
}

data class WishListItem(
    @JsonProperty("item_id") var itemId: String,
    @JsonProperty("item_name") var itemName: String
) {
    constructor() : this("", "")
}

data class WishList(
    @JsonProperty("id") var id: String?,
    @JsonProperty("name") var name: String,
    @JsonProperty("items") var items: List<WishListItem>?
) {
    constructor() : this(null, "", null)
}

@ApplicationScoped
class TestInjectionService {

    fun testInjectMethod(): String = "Hello from gCloud!"
    fun testInjectMethodWithParam(name: String): String = "Hello $name from gCloud!"
}