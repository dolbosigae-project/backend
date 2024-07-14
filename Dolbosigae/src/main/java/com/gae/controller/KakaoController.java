package com.gae.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
public class KakaoController {
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
    @ResponseBody
    public String kakaoCallBack(HttpSession session, @RequestParam String code) {
        String apiURL = "https://kauth.kakao.com/oauth/token?"
                + "grant_type=authorization_code"
                + "&client_id=" + REST_API_KEY
                + "&redirect_uri=" + REDIRECT_URI
                + "&code=" + code;
        
        String res = requestKakaoServer(apiURL, null);
        
        if(res != null && !res.equals("")) {
            JSONObject json = new JSONObject(res);
            session.setAttribute("accessToken", json.get("access_token"));
            session.setAttribute("refreshToken", json.get("refresh_token"));
            return "redirect:/";
        } else {
            return "redirect:/login";
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
