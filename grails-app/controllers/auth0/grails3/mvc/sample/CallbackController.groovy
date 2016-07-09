package auth0.grails3.mvc.sample

import com.auth0.web.Auth0CallbackHandler
import org.grails.web.util.WebUtils
import org.springframework.beans.factory.annotation.Autowired

class CallbackController {

    static defaultAction = "callback"

    @Autowired
    Auth0CallbackHandler callback

    def callback() {
        def req = WebUtils.retrieveGrailsWebRequest().getCurrentRequest()
        def res = WebUtils.retrieveGrailsWebRequest().getCurrentResponse()
        callback.handle(req, res)
    }
}
