package auth0.grails3.mvc.sample

import com.auth0.Auth0
import com.auth0.authentication.AuthenticationAPIClient
import com.auth0.authentication.result.DatabaseUser
import com.auth0.authentication.result.UserIdentity
import com.auth0.authentication.result.UserProfile
import com.auth0.spring.security.mvc.Auth0UserDetails
import com.auth0.web.Auth0User
import grails.transaction.Transactional
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

import java.security.Principal

@Transactional
class AdminService {

    RestTemplate restTemplate
    DaoService daoService

    @Value('${auth0.connection}')
    String connection
    @Value('${auth0.domain}')
    String domain
    @Value('${auth0.clientId}')
    String clientId
    @Value('${auth0.clientSecret}')
    String clientSecret
    @Value('${auth0.managementToken}')
    String managementToken
    @Value('${auth0.issuer}')
    String issuer


    List findAllUsers() {
        String url = issuer + "api/v2/users?q=app_metadata.clients%3Asome.domain.auth0.com&search_engine=v2"
        return daoService.makeHttpRequestToAuth0ManagementApi(url, HttpMethod.GET, List.class)
    }

    def createUser(String password, String email) {
        String url = issuer + "api/v2/users"
        Map userMap = [
                connection: connection,
                name: email,
                nickname: email.substring(0, email.indexOf('@')),
                password: password,
                email: email,
                email_verified: false,
                app_metadata:[
                        clients:[
                                domain
                        ]
                ]
        ]
        return daoService.makeHttpRequestToAuth0ManagementApi(url, HttpMethod.POST, Object, userMap)
    }

    boolean ensureAdmin() {
        final Auth0UserDetails currentUser = getAuth0UserDetails()
        log.info(currentUser.username)
        return true
    }

    Auth0UserDetails getAuth0UserDetails() {
        final Authentication authentication = SecurityContextHolder.context.authentication
        def a = authentication.credentials
        def b = authentication.details
        final Auth0UserDetails currentUser = (Auth0UserDetails) authentication.principal
        return currentUser
    }
}
