package com.auth0.example

import com.auth0.spring.security.mvc.Auth0UserDetails
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

/**
 * Demonstration of method level Role based authorization
 * Only an authenticated and authorized User with Admin
 * rights can access this resource.
 *
 * Also demonstrates how to retrieve the UserDetails object
 * representing the Authentication's principal from within
 * a service
 *
 */
@CompileStatic
@Service
@Slf4j
class AdminService {

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    boolean ensureAdmin() {
        final Authentication authentication = SecurityContextHolder.context.authentication
        final Auth0UserDetails currentUser = (Auth0UserDetails) authentication.principal
        log.info("Current user accessed Admin secured resource: {}", currentUser.username)
        return true
    }
}
