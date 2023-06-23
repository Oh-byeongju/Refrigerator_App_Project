package com.example.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiExamSearchBlog {

    static String[] title_J = new String[10];
    static String[] link_J = new String[10];
    static String[] description_J = new String[10];
    static String[] bloggername_J = new String[10];
    static String[] bloggerlink_J = new String[10];
    static String[] postdate_J = new String[10];
    static ArrayList<String> mlist = new ArrayList<>();

    public ArrayList<String> getNaverSearch(String keyword){                   // 나중에 매개변수로 텍스트 받기
        String clientId = "LuB8lrunrQGCZGBs38ie"; //애플리케이션 클라이언트 아이디값"
        String clientSecret = "AgMbIUCTG_"; //애플리케이션 클라이언트 시크릿값"

        String text = null;
        String text2 = null;
        try {
            text = URLEncoder.encode(keyword, "UTF-8");
            text2 = URLEncoder.encode(" 레시피", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text + text2;    // json 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String responseBody = get(apiURL, requestHeaders); // json 값 리턴
        return parseData(responseBody);

    }


    private static String get(String apiUrl, Map<String, String> requestHeaders) {

        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }

    }


    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    // JSON 객체 반환 함수
    public ArrayList<String> parseData(String responseBody) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(responseBody.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                title_J[i] = item.getString("title");
                link_J[i] = item.getString("link");
                description_J[i] = item.getString("description");
                bloggername_J[i] = item.getString("bloggername");
                bloggerlink_J[i] = item.getString("bloggerlink");
                postdate_J[i] = item.getString("postdate");

                String titleFilter = title_J[i].replaceAll("<b>", "");
                String title = titleFilter.replaceAll("</b>", "");

                String descriptionFilter = description_J[i].replaceAll("<b>", "");
                String description = descriptionFilter.replaceAll("</b>", "");

                mlist.add(title);
                mlist.add(description);
                mlist.add(link_J[i]);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mlist;
    }
}

