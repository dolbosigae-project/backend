package com.gae.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gae.service.KakaoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class KakaoController {

    private static final Logger logger = LoggerFactory.getLogger(KakaoController.class);
    private final String REST_API_KEY = "b8908d4e43f44454659c9dd7d3e9d56e";
    private final String REDIRECT_URI = "http://localhost:9999/kakao/callback";

    @Autowired
    private KakaoService kakaoService;

    @GetMapping("/kakao")
    public void kakaoLogin(HttpServletResponse response) throws IOException {
        String apiURL = "https://kauth.kakao.com/oauth/authorize?"
                + "response_type=code"
                + "&client_id=" + REST_API_KEY
                + "&redirect_uri=" + REDIRECT_URI;
        response.sendRedirect(apiURL);
    }

    @GetMapping("/kakao/callback")
    public Map<String, String> kakaoCallBack(HttpSession session, @RequestParam String code) {
        logger.debug("Received code: {}", code);

        String accessToken = kakaoService.getAccessToken(code);
        logger.debug("Access Token: {}", accessToken);

        Map<String, String> response = new HashMap<>();
        if (accessToken != null && !accessToken.isEmpty()) {
            session.setAttribute("accessToken", accessToken);

            // 사용자 정보 가져오기
            Map<String, Object> userInfo = kakaoService.getUserInfo(accessToken);
            String nickname = (String) userInfo.get("nickname");
            String profileImage = (String) userInfo.get("profile_image");

            response.put("status", "success");
            response.put("nickname", nickname);
            response.put("profile_image", profileImage);
        } else {
            response.put("status", "failure");
            response.put("error", "Access token not found in response");
        }

        return response;
    }

    @GetMapping("/kakao/logout")
    public Map<String, String> kakaoLogout(HttpSession session) {
        String accessToken = (String) session.getAttribute("accessToken");
        Map<String, String> response = new HashMap<>();
        if (accessToken != null && !accessToken.isEmpty()) {
            kakaoService.kakaoLogout(accessToken);
            session.invalidate();
            response.put("status", "success");
        } else {
            response.put("status", "failure");
            response.put("error", "No access token found in session");
        }
        return response;
    }
}
