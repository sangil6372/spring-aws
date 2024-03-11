package com.sangil.springaws.authentication.oauth.service;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final Environment env;
    private final RestTemplate restTemplate = new RestTemplate();


    public void socialLogin(String registrationId, String code){
        System.out.println("registrationId = " + registrationId);
        System.out.println("code = " + code);

        String accessToken = getAccessToken(registrationId, code);
        System.out.println("accessToken = " + accessToken);

        JsonNode userResourceNode = getUserResource(registrationId, accessToken);
        System.out.println("userResourceNode = " + userResourceNode);

        String id = userResourceNode.get("id").asText();
        String email = userResourceNode.get("email").asText();
        String nickname = userResourceNode.get("name").asText();
        System.out.println("id = " + id);
        System.out.println("email = " + email);
        System.out.println("nickname = " + nickname);
    }

    private String getAccessToken(String registrationId, String authorizationCode) {
        String clientId = env.getProperty("oauth2." + registrationId + ".client-id");
        String clientSecret = env.getProperty("oauth2." + registrationId + ".client-secret");
        String redirectUri = env.getProperty("oauth2." + registrationId + ".redirect-uri");
        String tokenUri = env.getProperty("oauth2." + registrationId + ".token-uri");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        return accessTokenNode.get("access_token").asText();
    }

    public JsonNode getUserResource(String registrationId, String accessToken){
        // registrationId를 기반으로 환경 설정에서 사용자 정보를 가져오기 위한 리소스 URI를 조회합니다.
        String resourceUri = env.getProperty("oauth2."+registrationId+".resource-uri");

        // HTTP 요청 헤더를 생성합니다.
        HttpHeaders headers = new HttpHeaders();

        // HTTP 헤더에 인증 정보를 추가합니다. Bearer 토큰 사용.
        headers.set("Authorization", "Bearer " + accessToken);

        // 생성된 헤더를 포함하여 새로운 HttpEntity 객체를 생성합니다. 본문은 비워둡니다.
        HttpEntity entity = new HttpEntity(headers);

        // restTemplate를 사용하여 리소스 서버에 HTTP GET 요청을 보냅니다.
        // 요청의 결과로 받은 JSON 응답 본문을 JsonNode 객체로 반환합니다.
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();

    }
}
