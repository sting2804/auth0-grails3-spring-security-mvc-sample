package auth0.grails3.mvc.sample

import com.auth0.web.Auth0Config
import com.auth0.web.Auth0User
import com.auth0.web.SessionUtils
import grails.converters.JSON
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('ROLE_ADMIN')")
class HomeController {

    static defaultAction = "home"

    AdminService adminService
    Auth0Config auth0Config

    def home() {
        log.info("Home page")

        adminService.ensureAdmin()
        Auth0User user = SessionUtils.getAuth0User(request)
        render ([user: user] as JSON)
    }
}
