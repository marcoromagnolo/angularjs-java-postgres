package dmt.server.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dmt.server.controller.request.RegisterRequest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author Marco Romagnolo
 */
public class AccountControllerTest {

    @Test
    @Ignore("Use only when application is up and running")
    public void testRegister() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        RegisterRequest register = new RegisterRequest("Joe", "Doe", "j.doe@email.com", "jdoe", "pass", "Rome/Italy");
        String request = mapper.writeValueAsString(register);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(request, headers);
        String uri = "http://localhost:8080/api/register";
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.exchange(uri, HttpMethod.POST, entity, String.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}
