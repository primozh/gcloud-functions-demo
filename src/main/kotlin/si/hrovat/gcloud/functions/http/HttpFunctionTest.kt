package si.hrovat.gcloud.functions.http

import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpRequest
import com.google.cloud.functions.HttpResponse
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@Named("testHttpFunction")
@ApplicationScoped
class HttpFunctionTest : HttpFunction {

    override fun service(request: HttpRequest?, response: HttpResponse?) {
        response?.writer?.write("Hello from gCloud!")
    }
}