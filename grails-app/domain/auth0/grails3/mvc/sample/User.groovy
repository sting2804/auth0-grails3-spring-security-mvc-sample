package auth0.grails3.mvc.sample

class User {

    String username
    String picture
    String email
    boolean emailVerified
    String firstName
    String surname
    Date createdAt
    Date updatedAt
    String userId

    static constraints = {
        picture nullable: true
        firstName nullable: true
        surname nullable: true
        createdAt nullable: true
        updatedAt nullable: true
        userId nullable: true
    }
}
