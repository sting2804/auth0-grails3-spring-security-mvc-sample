import auth0.grails3.mvc.sample.DaoService

import javax.servlet.ServletContext

class BootStrap {
    DaoService daoService
    def init = { ServletContext servletContext ->
        daoService.createLocalUsersFromListOfAuth0Users()
    }
    def destroy = {
    }
}
