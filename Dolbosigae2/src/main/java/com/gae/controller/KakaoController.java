package com.gae.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class KakaoController {

    private static final Logger logger = LoggerFactory.getLogger(KakaoController.class);

    private final String REST_API_KEY = "b8908d4e43f44454659c9dd7d3e9d56e";
    private final String REDIRECT_URI = "http://localhost:9999/kakao/callback";

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

        String apiURL = "https://kauth.kakao.com/oauth/token";
        String postParams = "grant_type=authorization_code"
                + "&client_id=" + REST_API_KEY
                + "&redirect_uri=" + REDIRECT_URI
                + "&code=" + code;

        String res = requestKakaoServer(apiURL, postParams);

        Map<String, String> response = new HashMap<>();
        if (res != null && !res.isEmpty()) {
            JSONObject json = new JSONObject(res);
            session.setAttribute("accessToken", json.getString("access_token"));
            session.setAttribute("refreshToken", json.getString("refresh_token"));
            session.setAttribute("expiresIn", json.getInt("expires_in"));
            session.setAttribute("scope", json.getString("scope"));
            response.put("status", "success");
        } else {
            response.put("status", "failure");
        }
        return response;
    }

    public String requestKakaoServer(String apiURL, String postParams) {
        StringBuilder res = new StringBuilder();
        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setDoOutput(true);

            // POST 파라미터 전송
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = postParams.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
            }

            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                res.append(responseLine.trim());
            }
            br.close();
        } catch (Exception e) {
            logger.error("Error during request to Kakao server: ", e);
        }
        logger.debug("Response content: {}", res.toString());  // 응답 내용을 로그에 기록
        return res.toString();
    }
}
