package com.gae.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class KakaoService {

    private static final Logger logger = LoggerFactory.getLogger(KakaoService.class);
    private final String kakaoApiKey = "b8908d4e43f44454659c9dd7d3e9d56e";
    private final String kakaoRedirectUri = "http://localhost:9999/kakao/callback";

    public String getAccessToken(String code) {
        String accessToken = "";
        String refreshToken = "";
        String reqUrl = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=").append(kakaoApiKey);
            sb.append("&redirect_uri=").append(kakaoRedirectUri);
            sb.append("&code=").append(code);

            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            logger.info("[KakaoApi.getAccessToken] responseCode = {}", responseCode);

            BufferedReader br;
            if (responseCode >= 200 && responseCode < 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line;
            StringBuilder responseSb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseSb.append(line);
            }
            String result = responseSb.toString();
            logger.info("responseBody = {}", result); // 응답 본문 로그 출력

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            // Null 체크 추가
            if (element.getAsJsonObject().get("access_token") != null) {
                accessToken = element.getAsJsonObject().get("access_token").getAsString();
            }
            if (element.getAsJsonObject().get("refresh_token") != null) {
                refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();
            }

            br.close();
            bw.close();
        } catch (Exception e) {
            logger.error("Error during request to Kakao server: ", e);
        }
        return accessToken;
    }

    public HashMap<String, Object> getUserInfo(String accessToken) {
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqUrl = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            int responseCode = conn.getResponseCode();
            logger.info("[KakaoApi.getUserInfo] responseCode : {}", responseCode);

            BufferedReader br;
            if (responseCode >= 200 && responseCode <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line = "";
            StringBuilder responseSb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseSb.append(line);
            }
            String result = responseSb.toString();
            logger.info("responseBody = {}", result); // 응답 본문 로그 출력

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String profileImage = properties.getAsJsonObject().get("profile_image").getAsString();

            userInfo.put("nickname", nickname);
            userInfo.put("profile_image", profileImage);

            br.close();

        } catch (Exception e) {
            logger.error("Error during request to Kakao server: ", e);
        }
        return userInfo;
    }

    public void kakaoLogout(String accessToken) {
        String reqUrl = "https://kapi.kakao.com/v1/user/logout";

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            logger.info("[KakaoApi.kakaoLogout] responseCode : {}", responseCode);

            BufferedReader br;
            if (responseCode >= 200 && responseCode <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line = "";
            StringBuilder responseSb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseSb.append(line);
            }
            String result = responseSb.toString();
            logger.info("kakao logout - responseBody = {}", result);

        } catch (Exception e) {
            logger.error("Error during request to Kakao server: ", e);
        }
    }
}
