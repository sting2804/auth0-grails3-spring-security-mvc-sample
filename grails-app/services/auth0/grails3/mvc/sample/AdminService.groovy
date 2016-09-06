package auth0.grails3.mvc.sample

import com.auth0.Auth0
import com.auth0.authentication.AuthenticationAPIClient
import com.auth0.authentication.result.DatabaseUser
import com.auth0.spring.security.mvc.Auth0UserDetails
import grails.transaction.Transactional
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

@Transactional
class AdminService {

    @Autowired
    RestTemplate restTemplate
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


    List findAllUsers(){
        String idToken = RestService.getIdToken()
        HttpHeaders headers = new HttpHeaders()
        headers.set("Authorization", "Bearer $managementToken");
        List users = []
        try{
            ResponseEntity entity = restTemplate.exchange(issuer+"api/v2/users", HttpMethod.GET, new HttpEntity<Object>(headers), List)
            users = entity.getBody()
        } catch (HttpClientErrorException e){
            e.printStackTrace()
        }
        return users
    }

    DatabaseUser createUser(String email, String password, String username = null){
        Auth0 auth0 = new Auth0(clientId, clientSecret, domain)
        AuthenticationAPIClient client = auth0.newAuthenticationAPIClient()
        def res = client.signUp(email, password, username).execute()
        DatabaseUser newUser = client.createUser(email, password, username).execute()
    }

    boolean ensureAdmin() {
        final Auth0UserDetails currentUser = getAuth0UserDetails()
        log.info(currentUser.username)
        return true
    }

    Auth0UserDetails getAuth0UserDetails(){
        final Authentication authentication = SecurityContextHolder.context.authentication
        def a  = authentication.credentials
        def b  = authentication.details
        final Auth0UserDetails currentUser = (Auth0UserDetails) authentication.principal
        return currentUser
    }
}
