package auth0.grails3.mvc.sample

import com.auth0.web.SessionUtils
import com.auth0.web.Tokens
import org.grails.web.util.WebUtils

import javax.servlet.http.HttpServletRequest

class RestService {

    static HttpServletRequest getRequest() {
        return WebUtils.retrieveGrailsWebRequest().getCurrentRequest()
    }

   static String getIdToken(){
        Tokens tokens = SessionUtils.getTokens(request)
        return tokens.idToken
    }

    static String getAccessToken(){
        Tokens tokens = SessionUtils.getTokens(request)
        return tokens.accessToken
    }
}
