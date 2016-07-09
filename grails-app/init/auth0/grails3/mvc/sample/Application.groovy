package auth0.grails3.mvc.sample

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.PropertySource

@ComponentScan(basePackages = ["com.auth0.example", "com.auth0.web", "com.auth0.spring.security.mvc"])
@EnableAutoConfiguration
@PropertySource("classpath:auth0.properties")
class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }
}