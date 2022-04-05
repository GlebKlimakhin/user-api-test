package com.example.adminservice.service;

import com.example.adminservice.dto.TokenResponse;
import com.example.adminservice.dto.UserDto;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private RestTemplate restTemplate;

    public TokenResponse getAuthToken(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("username", username);
        map.add("password", password);
        map.add("scope", "read write");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("myClientApp", "9999"));
        ResponseEntity<TokenResponse> tokenResponse = restTemplate.postForEntity("http://localhost:8080/api/v1/oauth/token",
                            httpEntity,
                            TokenResponse.class
                );
        if(httpEntity.getBody() == null) {
            throw new RuntimeException();
        }
        return tokenResponse.getBody();
        //вытаскиваем токен, получаем авторизацию, тут ещё пароль добавим в exchange
                            //в методах апи передаём хэдер в запрос)
                            //profit!
    }

    public List<UserDto> getUsersList(String authorization) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authorization);
        HttpEntity<List<UserDto>> httpEntity = new HttpEntity<>(headers);
        restTemplate.exchange("http://localhost:8080/api/v1/users/",
                HttpMethod.GET,
                httpEntity,
                List.class);
        return httpEntity.getBody();
    }

}
