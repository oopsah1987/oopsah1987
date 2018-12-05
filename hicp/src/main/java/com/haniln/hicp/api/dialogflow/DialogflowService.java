package com.haniln.hicp.api.dialogflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.haniln.hicp.components.google.Credentials;
import com.haniln.hicp.components.google.Dialogflow;
import com.haniln.hicp.components.utils.Util;

/**
 * Google Dialogflow
 * @author  : Lee Jung Min
 * @version : 2018-11-21 creation
 */
@Service
public class DialogflowService {
    protected static final Logger logger = LoggerFactory.getLogger(DialogflowService.class);

    public Map<String,Object> getGoogleCredentials() throws Exception {
        Credentials credentials = new Credentials();
        return credentials.getAccessToken("./credentials/leejungmin-222309-61e736cf9a89.json");
    }

    public Map<String,Object> detectIntent(Map<String,Object> inputs) throws Exception {
        Map<String,Object> outputs = new HashMap<String,Object>();
        String projectId = "leejungmin-222309"; // from database leejungmin-222309 happycall-3d8e3
        String sessionId = (String)inputs.get("sessionId");
        String accessToken = (String)inputs.get("accessToken");
        String languageCode = "ko"; // 서버
        String timezone = null==inputs.get("timezone")?"Asia/Seoul":(String)inputs.get("timezone");
        String inputText = (String)inputs.get("text");
        String timestamp = Util.getTime("yyyyMMddHHmmssSSS", timezone);
        String yyyyMMdd = timestamp.substring(0, 8);
        String HHmm = timestamp.substring(8, 12);

        Dialogflow dialogflow = new Dialogflow(projectId, sessionId);
        Map<String,Object> response = dialogflow.detectIntent(accessToken, inputText, timezone, languageCode);
        logger.debug(">>>>>>>>>> response = "+response);
        List<Map<String,Object>> text = dialogflow.getText(response);
        for (int i=0,n=text.size(); i<n; i++) {
            Map<String,Object> vo = text.get(i);
            vo.put("date", Util.toLocaleDateString(yyyyMMdd, languageCode));
            vo.put("time", Util.toLocaleTimeString(yyyyMMdd+HHmm, languageCode));
        }
        outputs.put("timestamp", timestamp);
        outputs.put("text", text);
        outputs.put("action", null); // 딜라이팅 버튼등
        return outputs;
    }
}