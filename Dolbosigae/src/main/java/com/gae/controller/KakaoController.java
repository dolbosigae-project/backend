package com.gae.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
public class KakaoController {

    private static final Logger logger = LoggerFactory.getLogger(KakaoController.class);

    private final String REST_API_KEY = "b8908d4e43f44454659c9dd7d3e9d56e";
    private final String REDIRECT_URI = "http://localhost:9999/kakao/callback";

    @GetMapping("/kakao")
    public String kakaoLogin() {
        String apiURL = "https://kauth.kakao.com/oauth/authorize?"
                + "response_type=code"
                + "&client_id=" + REST_API_KEY
                + "&redirect_uri=" + REDIRECT_URI;

        return "redirect:" + apiURL;
    }

    @GetMapping("/kakao/callback")
    public String kakaoCallBack(HttpSession session, @RequestParam String code) {
        logger.debug("Received code: {}", code);  // 디버깅 로그 추가

        String apiURL = "https://kauth.kakao.com/oauth/token?"
                + "grant_type=authorization_code"
                + "&client_id=" + REST_API_KEY
                + "&redirect_uri=" + REDIRECT_URI
                + "&code=" + code;

        String res = requestKakaoServer(apiURL, null);
        
        logger.debug("Response from Kakao: {}", res);  // 디버깅 로그 추가

        if (res != null && !res.equals("")) {
            JSONObject json = new JSONObject(res);
            session.setAttribute("accessToken", json.get("access_token"));
            session.setAttribute("refreshToken", json.get("refresh_token"));
            return "redirect:/?loginSuccess=true"; // 로그인 성공 시 쿼리 파라미터 추가
        } else {
            return "redirect:/login?loginSuccess=false"; // 로그인 실패 시 쿼리 파라미터 추가
        }
    }

    @GetMapping("/kakao/logout")
    @ResponseBody
    public String logout(HttpSession session) {
        session.invalidate();
        return "Logout successful";
    }

    public String requestKakaoServer(String apiURL, String header) {
        StringBuffer res = new StringBuffer();
        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            if (header != null && !header.equals("")) {
                con.setRequestProperty("Authorization", header);
            }

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else { // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                res.append(inputLine);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res.toString();
    }
}
