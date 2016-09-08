package auth0.grails3.mvc.sample

import grails.converters.JSON

//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
class HomeController {

    static defaultAction = "home"

    AdminService adminService

    def home() {
        log.info("Home page")

        adminService.ensureAdmin()
        println User.list()
        //Tokens tokens = SessionUtils.getTokens(request)
        //Auth0User user = SessionUtils.getAuth0User(request)
        render ([user: user] as JSON)
    }

    def createUser(){
        def user = adminService.createUser(params.password, params.email)
        render ([user: user] as JSON)
    }

    def findAllUsers(){
        render ([users: adminService.findAllUsers()] as JSON)
    }
}
