package auth0.grails3.mvc.sample

import grails.converters.JSON

class ErrorController {
    def handleUnexpectedError() {
        def exception = request.exception?.cause?.target
        render([status: 500, exception: exception] as JSON)
    }

    def handleAuthenticationError() {
        render([status: 401, message: "Authentication Error"] as JSON)
    }

    def handleForbiddenError() {
        render([status: 403, message: "Access Denied"] as JSON)
    }

    def handleNotFoundError() {
        render([status: 404, message: "Target url was not found"] as JSON)
    }
}