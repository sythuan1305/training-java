package com.beetech.trainningJava.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Utils {
    public final static String DEFAULT_COOKIE_VALUE = "defaultCookieValue";

    private static final int KBinBytes = 1024;

    public static long memoryUsed() {
        return ((Runtime.getRuntime().totalMemory() / KBinBytes) - (Runtime
                .getRuntime().freeMemory() / KBinBytes));
    }

    public static void performGC() {
        for (int i = 0; i < 10; i++) {
            System.gc();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static Map<String, Object> JsonParserObjectWithEncodedBase64(String cookieValue) {
        byte[] decodedBytes = Base64.getDecoder().decode(cookieValue);
        try {
            return new LinkedHashMap<>(new JSONParser(new String(decodedBytes)).parseObject());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Map<String, Object>> JsonParserListObjectWithEncodedURL(String cookieValue) {
        if (Utils.DEFAULT_COOKIE_VALUE.equals(cookieValue)) {
            return null;
        }

        String decodeURL = URLDecoder.decode(cookieValue, StandardCharsets.UTF_8);
        System.out.println("decodeURL: " + decodeURL);
        try {
            return new ArrayList<>((List<Map<String, Object>>) new JSONParser(decodeURL).parse());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    // ---------

    public static String JsonParserString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    // ---------

    public static String ChangeVndToUsd(String vnd) {
        return checkRoundDirection(BigDecimal.valueOf(Double.parseDouble(vnd) / 23000)).toString();
    }

    public static BigDecimal checkRoundDirection(BigDecimal number) {
        BigDecimal roundedUp = number.setScale(2, RoundingMode.CEILING);
        BigDecimal roundedDown = number.setScale(2, RoundingMode.FLOOR);
        if (number.subtract(roundedDown).compareTo(roundedUp.subtract(number)) < 0) {
            return roundedDown;
        } else {
            return roundedUp;
        }
    }

    // ---------
    public static void deleteCookie(String name, HttpServletResponse response) {
        Cookie cookie = new Cookie("cart", "");
        cookie.setMaxAge(0); // Đặt thời gian hết hạn của cookie vào quá khứ
        cookie.setPath("/"); // Đảm bảo cookie được gửi đến tất cả các đường dẫn trên trang web
        response.addCookie(cookie); // Thêm cookie vào phản hồi (response)
    }
}
