package auth0.grails3.mvc.sample

import com.auth0.web.Auth0Config
import com.auth0.web.NonceUtils
import com.auth0.web.SessionUtils

import org.grails.web.util.WebUtils
import org.springframework.beans.factory.annotation.Autowired

class LoginController {

    static defaultAction = "login"

    @Autowired
    Auth0Config auth0Config

    def login() {
        log.info("Performing login");
        def req = WebUtils.retrieveGrailsWebRequest().getCurrentRequest()
        // add a Nonce value to session storage
        NonceUtils.addNonceToStorage(req)
        def model = [:]
        model['clientId'] = auth0Config.getClientId()
        model['domain'] = auth0Config.getDomain()
        model['loginCallback'] = auth0Config.getLoginCallback()
        model['state'] = SessionUtils.getState(req)
        render(view: "login", model: [model: model])
    }

}
