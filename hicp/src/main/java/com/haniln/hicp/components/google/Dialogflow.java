package com.haniln.hicp.components.google;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.haniln.hicp.components.utils.Util;

public class Dialogflow {
    private String URL;

    public Dialogflow(String projectId, String sessionId) {
        this.URL = "https://dialogflow.googleapis.com/v2/projects/"+projectId+"/agent/sessions/"+sessionId+":detectIntent";
    }

    /**
     * @see detectIntent("xxxx..", "Hello?", "ko", "Asia/Seoul")
     */
    public Map<String,Object> detectIntent(String accessToken, String text, String timezone, String languageCode) throws Exception {
        StringBuilder builder = new StringBuilder();
        HttpPost http = new HttpPost(URL);
        HttpClient client = HttpClientBuilder.create().build();
        http.setHeader(HttpHeaders.AUTHORIZATION, "Bearer "+accessToken);
        String jsonString = "{ \"queryInput\": { \"text\": { \"text\": \""+text+"\", \"languageCode\": \""+languageCode+"\"} }, \"queryParams\": { \"timeZone\": \""+timezone+"\" } }";
        try {
            StringEntity entity = new StringEntity(jsonString, "UTF-8");
            entity.setContentType("application/json");
            http.setEntity(entity);
            HttpResponse response = client.execute(http);
            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            String line = null;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            int statusCode = response.getStatusLine().getStatusCode();
            if (200 != statusCode) {
                throw new Exception("Failed : Http Status Code = "+statusCode+", Message = "+Util.jsonToMap(builder.toString()).get("error"));
            }
        } catch(Exception e) {
            throw e;
        } finally {
            http.releaseConnection();
        }
        return Util.jsonToMap(builder.toString());
    }

    /**
     * @see getText(dialogflow.detectIntent(....));
     * @return [{ "text": "hello" }, { "text": "I'm bot." }]
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> getText(Map<String,Object> detectIntentResponse) throws Exception {
        List<Map<String,Object>> text = new ArrayList<>();
        Map<String,Object> queryResult = (Map<String,Object>)detectIntentResponse.get("queryResult");
        List<Map<String,Object>> fulfillmentMessages = (List<Map<String,Object>>)queryResult.get("fulfillmentMessages");
        for (int i=0,n=fulfillmentMessages.size(); i<n; i++) {
            Map<String,Object> fulfillmentMessage = fulfillmentMessages.get(i);
            if (null != fulfillmentMessage.get("text")) {
                Map<String,Object> text1 = (Map<String,Object>)fulfillmentMessage.get("text");
                List<String> text2 = (List<String>)text1.get("text");
                for (int j=0,m=text2.size(); j<m; j++) {
                    Map<String,Object> vo = new HashMap<>();
                    vo.put("text", text2.get(j));
                    text.add(vo);
                }
            }
        }
        return text;
    }
}