package com.haniln.hicp.components.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {

    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    /** Object = String null 일때 String 객체로 리턴 */
    public static String toString(Object obj) {
        if(null != obj) return obj.toString();
        else return "";
    }

    /** @see toLocaleDateString("20181123", "ko"), toLocaleDateString("20181123", "en-US") 
     *  @return 2018년 11월 23일 금요일, Friday, November 23, 2018 */
    public static String toLocaleDateString(String yyyyMMdd, String languageCode) throws Exception {
        String output = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = format.parse(yyyyMMdd);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int yyyy = calendar.get(Calendar.YEAR);
        int MM = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DATE);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (-1 < languageCode.indexOf("ko")) {
            output = yyyy+"년 "+Util.getMonthString(MM, languageCode)+" "+dd+"일 "+Util.getDayOfWeek(dayOfWeek, languageCode);
        } else {
            output = Util.getDayOfWeek(dayOfWeek, languageCode)+", "+Util.getMonthString(MM, languageCode)+" "+dd+", "+yyyy;
        }
        return output;
    }

    public static String toLocaleTimeString(String yyyyMMddHHmm, String languageCode) throws Exception {
        String output = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = format.parse(yyyyMMddHHmm);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int mm = calendar.get(Calendar.MINUTE);
        int ampm = calendar.get(Calendar.AM_PM);
        if (-1 < languageCode.indexOf("ko")) {
            output = (Calendar.AM==ampm?"오전 ":"오후 ") + hh + ":" + mm;
        } else {
            output = hh + ":" + mm + (Calendar.AM==ampm?" AM":" PM");
        }
        return output;
    }

    /** @see getMonthString(0, "ko"), getDayOfWeek(0, "en-US")
     *  @return 1월, January */
    public static String getMonthString(int month, String languageCode) {
        String output = null;
        boolean isKorean = -1<languageCode.indexOf("ko")?true:false;
        switch(month) {
            case  0: output = isKorean?"1월":"January"; break;
            case  1: output = isKorean?"2월":"February"; break;
            case  2: output = isKorean?"3월":"March"; break;
            case  3: output = isKorean?"4월":"April"; break;
            case  4: output = isKorean?"5월":"May"; break;
            case  5: output = isKorean?"6월":"June"; break;
            case  6: output = isKorean?"7월":"July"; break;
            case  7: output = isKorean?"8월":"August"; break;
            case  8: output = isKorean?"9월":"September"; break;
            case  9: output = isKorean?"10월":"October"; break;
            case 10: output = isKorean?"11월":"November"; break;
            case 11: output = isKorean?"12월":"December"; break;
        }
        return output;
    }

    /** @see getDayOfWeek(1, "ko"), getDayOfWeek(1, "en-US")
     *  @return 일요일, Sunday */
    public static String getDayOfWeek(int dayOfWeek, String languageCode) {
        System.out.println(dayOfWeek);
        String output = null;
        boolean isKorean = -1<languageCode.indexOf("ko")?true:false;
        switch(dayOfWeek) {
            case 1: output = isKorean?"일요일":"Sunday"; break;
            case 2: output = isKorean?"월요일":"Monday"; break;
            case 3: output = isKorean?"화요일":"Tuesday"; break;
            case 4: output = isKorean?"수요일":"Wednesday"; break;
            case 5: output = isKorean?"목요일":"Thursday"; break;
            case 6: output = isKorean?"금요일":"Friday"; break;
            case 7: output = isKorean?"토요일":"Saturday"; break;
        }
        return output;
    }

    /** 현재시간을 format 형태로 리턴 <br>
     * - 입력예 : getTime("yyyyMMddHHmmssSSS")
     * - timeZone : Asia/Seoul */
     public static String getTime(String format) {
        return Util.getTime(format, "Asia/Seoul");
    }

    /** 입력한 표준시의 현재시간을 format 형태로 리턴 <br>
     * - 입력예 : getTime("yyyyMMddHHmmssSSS")<br>
     * - timeZone<br>
     *   1.Asia/Seoul : 한국표준시<br>
     *   2.JST : 일본표준시<br>
     *   3.Greenwich : 그리니치표준시<br>
     *   4.America/Los_Angeles : 태평양표준시<br>
     *   5.America/New_York : 동부표준시<br>
     *   6.Pacific/Honolulu : 하와이표준시<br>
     *   7.Asia/Shanghai : 중국표준시 */
    public static String getTime(String format, String timeZone) {
        SimpleDateFormat formatter = null;
        Calendar calendar = null;
        String rtnTime = "";
        try {
            formatter = new SimpleDateFormat(format);
            formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
            calendar = new GregorianCalendar();
            rtnTime = formatter.format(calendar.getTime());
        } catch(IllegalArgumentException ie) {
            throw ie;
        }
        return rtnTime;
    }

    /** JSON 문자열을 Map 객체로 변경한다.<br>
     * 예) jsonToMap("{\"id\":126,\"pId\":0,\"name\":\"이름\"}") */
    public static Map<String,Object> jsonToMap(String jsonString) throws Exception {
        if(null == jsonString || "".equals(jsonString)) return null;
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = new HashMap<String,Object>();
        map = mapper.readValue(jsonString, new TypeReference<Map<String,Object>>(){});
        return map;
    }

    /**  Map 객체를 JSON 문자열로 변경한다.<br>
     * 예) mapToJson(map) => {\"111\":\"aaaa\",\"222\":[{\"aaa\":\"aaa\"}]} */
    @SuppressWarnings("unchecked")
    public static String mapToJson(Map<String,Object> map) {
        if(null == map) return null;
        JSONObject json = new JSONObject();
        json.putAll(map);
        return json.toJSONString();
    }

    /** 랜덤문자를 생성하여 리턴한다.
     *  @param digits : 키 자리수
     *  @return : 0a5BwE
     *  @see getRandomCharater(6) */
    public static String getRandomCharacters(int digits) {
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<digits; i++) {
            builder.append(chars.charAt(random.nextInt(chars.length())));
        }
        return builder.toString();
    }
}