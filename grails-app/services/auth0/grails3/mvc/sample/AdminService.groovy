package auth0.grails3.mvc.sample

import com.auth0.spring.security.mvc.Auth0UserDetails
import grails.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.client.RestTemplate

@Transactional
class AdminService {

    RestTemplate restTemplate
    @Value(value = "${auth0.issuer}")
    String issuer

    List findAllUsers(){
        List users = restTemplate.getForObject(issuer+"api/v2/users", List.class)
                .header("authorization", "Bearer YOUR_ID_TOKEN_HERE")
    }

    boolean ensureAdmin() {
        final Authentication authentication = SecurityContextHolder.context.authentication
        final Auth0UserDetails currentUser = (Auth0UserDetails) authentication.principal
        log.info(currentUser.username)
        return true
    }
}
