package auth0.grails3.mvc.sample

import grails.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Transactional
class DaoService {

    RestTemplate restTemplate
    @Value('${auth0.managementToken}')
    String managementToken

    def makeHttpRequestToAuth0ManagementApi(String url, HttpMethod method, Class returnType, Object body = null) {
        HttpHeaders headers = new HttpHeaders()
        headers.set("Authorization", "Bearer $managementToken")
        HttpEntity httpEntity = new HttpEntity<Object>(body, headers)

        ResponseEntity entity = restTemplate.exchange(url, method, httpEntity, returnType)
        return entity.getBody()
        return null
    }
}
