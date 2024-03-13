package com.sangil.springaws.authentication.oauth.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.sangil.springaws.authentication.jwt.AuthTokensGenerator;
import com.sangil.springaws.authentication.jwt.dto.AuthTokens;
import com.sangil.springaws.user.domain.Role;
import com.sangil.springaws.user.domain.User;
import com.sangil.springaws.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;

    public AuthTokens socialLogin(String registrationId, String code){
//      registrationId (Google 정보) + code (Authorization Code)로 AccessToken을 얻어서
//      AccessToken으로 사용자 정보 가져오는 것까지 구현 완
        String accessToken = getAccessToken(registrationId, code);
        JsonNode userResourceNode = getUserResource(registrationId, accessToken);

//      DB에 사용자가 등록되었는지 여부 -> 등록x -> User 테이블에 계정 생성
        String name = userResourceNode.get("name").asText();
        String email = userResourceNode.get("email").asText();
        String picture = userResourceNode.get("picture").asText();
        User user = userRepository.findByEmail(email)
                .orElseGet(()->{
                    User newUser = User.builder()
                            .name(name)
                            .email(email)
                            .picture(picture)
                            .role(Role.USER) // 일반 사용자
                            .build();
                    return userRepository.save(newUser);
                });

//      사용자 정보로 jwt 토큰 발행 ( + refreshToken )
        return authTokensGenerator.generate(user.getId());
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
