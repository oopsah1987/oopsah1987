package com.haniln.hicp.api.dialogflow;

import java.util.HashMap;
import java.util.Map;

import com.haniln.hicp.components.utils.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Google Dialogflow
 * 
 * @author : Lee Jung Min
 * @version : 2018-11-21 creation
 */
@RestController
@RequestMapping("/api/dialogflow")
public class DialogflowController {
    private static final Logger logger = LoggerFactory.getLogger(DialogflowController.class);

    @Autowired
    private DialogflowService service;

    @RequestMapping(value = "/credentials", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getGoogleCredentials() throws Exception {
        return new ResponseEntity<>(this.service.getGoogleCredentials(), HttpStatus.OK);
    }

    /**
     * @param String accessToken : Google oauth2 accessToken
     * @param String text
     * @param String timezone : Asia/Seoul
     * @param String sessionId : XY2S...
     */ 
    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getTime(@RequestParam Map<String, Object> inputs) throws Exception {
        Map<String, Object> outputs = new HashMap<>();
        String languageCode = "ko"; // 서버
        String timezone = null == inputs.get("timezone") ? "Asia/Seoul" : (String) inputs.get("timezone");
        String timestamp = Util.getTime("yyyyMMddHHmmssSSS", timezone);
        String yyyyMMdd = timestamp.substring(0, 8);
        String HHmm = timestamp.substring(8, 12);
        outputs.put("date", Util.toLocaleDateString(yyyyMMdd, languageCode));
        outputs.put("time", Util.toLocaleTimeString(yyyyMMdd + HHmm, languageCode));
        return new ResponseEntity<>(outputs, HttpStatus.OK);
    }

    /**
     * @param String accessToken : Google oauth2 accessToken
     * @param String text
     * @param String timezone : Asia/Seoul
     * @param String sessionId : XY2S...
     */
    @RequestMapping(value = "/detect/intent", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> detectIntent(@RequestBody Map<String, Object> inputs) throws Exception {
        this.setParameters(inputs);
        Map<String, Object> outputs = new HashMap<>();
        outputs.put("inputs", inputs);
        outputs.put("outputs", service.detectIntent(inputs));
        return new ResponseEntity<>(outputs, HttpStatus.OK);
    }

    private Map<String, Object> setParameters(Map<String, Object> inputs) {
        logger.debug("parameters = " + inputs);
        return inputs;
    }
}