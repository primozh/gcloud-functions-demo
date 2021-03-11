package si.hrovat.gcloud.functions.http

import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.FirestoreOptions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.enterprise.inject.Produces
import javax.inject.Inject

@ApplicationScoped
class GCloudApiProducer {

    @ConfigProperty(name = "gcloud.project-id")
    lateinit var projectId: String

    @Produces
    @ApplicationScoped
    fun firestore(): Firestore {
        val options = FirestoreOptions
            .getDefaultInstance()
            .toBuilder()
            .setProjectId(projectId)
            .setCredentials(GoogleCredentials.getApplicationDefault())
            .build()
        return options.service
    }
}

@ApplicationScoped
class WishListService {

    val log: Logger = LoggerFactory.getLogger(WishListService::class.java)

    @ConfigProperty(name = "gcloud.collection")
    lateinit var collection: String

    @Inject
    lateinit var db: Firestore

    fun createList(wishList: WishList): WishList? {
        val document = db.collection(collection).document()
        val result = document.set(wishList).get()

        log.info("Got result in {}", result.updateTime)

        val storedD = document.get().get()
        val list = storedD.toObject(WishList::class.java)
        list?.id = storedD.id

        log.info("Found wish list with id {}", list?.id)

        return list
    }

    fun getWishList(id: String): WishList? {
        val document = db.collection(collection).document(id).get().get()
        return document.toObject(WishList::class.java)
    }

    fun addItem(id: String, item: WishListItem): WishList {
        TODO("Not yet implemented")
    }

}