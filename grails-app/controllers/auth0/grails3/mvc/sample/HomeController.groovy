package auth0.grails3.mvc.sample

import com.auth0.example.AdminService
import com.auth0.spring.security.mvc.Auth0UserDetails
import com.auth0.web.Auth0Config
import com.auth0.web.Auth0User
import com.auth0.web.SessionUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder

@PreAuthorize("hasAuthority('ROLE_ADMIN')")
class HomeController {

    static defaultAction = "home"

    @Autowired
    AdminService adminService

    @Autowired
    Auth0Config auth0Config

    def home() {
        log.info("Home page")

        Auth0UserDetails principal = (Auth0UserDetails) SecurityContextHolder.context.authentication.principal
        if (principal.authorities.any { it.authority == 'ROLE_ADMIN'}) {
            // just a simple callout to demonstrate role based authorization at service level
            // non-Admin user would be rejected trying to call this service
            adminService.ensureAdmin()
        }

        Auth0User user = SessionUtils.getAuth0User(request)
        [user: user]
    }
}
