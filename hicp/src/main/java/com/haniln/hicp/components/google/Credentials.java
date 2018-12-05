package com.haniln.hicp.components.google;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;

public class Credentials {
    /** 
     * @see getAccessToken('D:/leejungmin-222309-61e736cf9a89.json')
     * @return Map : { accessToken: "xxxx..", tokenType: "Bearer" }
     */
    public Map<String,Object> getAccessToken(String credentialsFilePath) throws Exception {
        Map<String,Object> outputs = new HashMap<String,Object>();
        InputStream credentialsJSON = Credentials.class.getResourceAsStream("credentials/leejungmin-222309-61e736cf9a89.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsJSON);
        //GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsFilePath));
        credentials = GoogleCredentials.getApplicationDefault();
        if (credentials.createScopedRequired()) {
            credentials = credentials.createScoped(Lists.newArrayList("https://www.googleapis.com/auth/dialogflow"));
        }
        credentials.refreshIfExpired();
        AccessToken token = credentials.getAccessToken();
        // or AccessToken refreshToken = credentials.refreshAccessToken();
        outputs.put("accessToken", token.getTokenValue());
        outputs.put("tokenType", "Bearer");
        return outputs;
    }
}