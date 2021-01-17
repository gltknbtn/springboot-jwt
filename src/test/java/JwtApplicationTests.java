import com.javainuse.SpringBootHelloWorldApplication;
import com.javainuse.dto.UserDTO;
import com.javainuse.model.JwtRequest;
import com.javainuse.model.JwtResponse;
import com.javainuse.model.User;
import com.javainuse.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootHelloWorldApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    private final HttpHeaders httpHeaders = new HttpHeaders();

    @Test
    public void testRegisterUser() {
        String username ="gltknbtn";
        String pwd = "123";
        ResponseEntity<Response<User>> response = registerUser(username, pwd);
        assertEquals("gltknbtn", response.getBody().getData().getUsername());
    }

    private ResponseEntity<Response<User>> registerUser(String username, String pwd) {
        UserDTO entityBody = UserDTO.builder().username(username).password(pwd).build();
        HttpEntity<UserDTO> httpEntity = new HttpEntity<>(entityBody, httpHeaders);

        ResponseEntity<Response<User>> response =
                testRestTemplate.exchange(createUrlWithPort("/register"),HttpMethod.POST, httpEntity, new ParameterizedTypeReference<Response<User>>() {});
        return response;
    }

    @Test
    public void testGetJwtToken() {
        String username ="gltknbtn";
        String pwd = "123";
        ResponseEntity<Response<JwtResponse>> response = getJwtToken(username, pwd);
        assertTrue(!StringUtils.isEmpty(response.getBody().getData().getJwttoken()));
    }

    private ResponseEntity<Response<JwtResponse>> getJwtToken(String username, String pwd) {
        JwtRequest requestBody = JwtRequest.builder().username(username).password(pwd).build();
        HttpEntity<JwtRequest> httpEntity = new HttpEntity<>(requestBody,httpHeaders);
        ResponseEntity<Response<JwtResponse>> response =
                testRestTemplate.exchange(createUrlWithPort("/authenticate"), HttpMethod.POST, httpEntity, new ParameterizedTypeReference<Response<JwtResponse>>() {
        });
        return response;
    }

    @Test
    public void testResponseWithUnauthorized() {
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<Response<Object>> response =
                testRestTemplate.exchange(createUrlWithPort("/hello?messageType=1"), HttpMethod.GET, httpEntity, new ParameterizedTypeReference<Response<Object>>() {});
        Integer expected = 401;
        assertEquals(expected, response.getBody().getError().getErrorCode());
    }

    @Test
    public void testResponseWithSuccessfully() {
        ResponseEntity<Response<JwtResponse>> jwtResponse = getJwtToken("gltknbtn", "123");
        String jwToken = jwtResponse.getBody().getData().getJwttoken();
        httpHeaders.add("Authorization", "Bearer "+ jwToken);

        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<Response<Object>> response =
                testRestTemplate.exchange(createUrlWithPort("/hello?messageType=1"), HttpMethod.GET, httpEntity, new ParameterizedTypeReference<Response<Object>>() {});
        int expected = 200;
        assertEquals(expected, response.getStatusCode().value());
    }
    @Test
    public void testResponseWithValidationException() {
        ResponseEntity<Response<JwtResponse>> jwtResponse = getJwtToken("gltknbtn", "123");
        String jwToken = jwtResponse.getBody().getData().getJwttoken();
        httpHeaders.add("Authorization", "Bearer "+ jwToken);

        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<Response<Object>> response =
                testRestTemplate.exchange(createUrlWithPort("/hello?messageType=5"), HttpMethod.GET, httpEntity, new ParameterizedTypeReference<Response<Object>>() {});
        assertTrue(!CollectionUtils.isEmpty(response.getBody().getValidations()));
    }

    private String createUrlWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
