package auth0.grails3.mvc.sample

import org.springframework.security.access.AccessDeniedException

class UrlMappings {

    static mappings = {
        login:
        "/login"(controller: 'login')
        logout:
        "/logout"(controller: 'logout')
        callback:
        "/callback"(controller: 'callback')
        home:
        "/portal/home"(controller: 'home')

        //"/"(view:"/index")
        "500"(controller: 'error', exception: AccessDeniedException,
                action: "handleAuthenticationError")
        "500"(controller: 'error', action: "handleUnexpectedError")
        "401"(controller: 'error', action: "handleAuthenticationError")
        "403"(controller: 'error', action: "handleForbiddenError")
        "404"(controller: 'error', action: "handleNotFoundError")
    }
}
