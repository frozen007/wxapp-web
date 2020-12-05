package com.myz.wxapp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class JsonKit {
    private static final Logger logger = LoggerFactory.getLogger(JsonKit.class);

    @Autowired
    private ObjectMapper objectMapper;

    public Map<String, Object> fromJson(String jsonStr) {

        try {
            return objectMapper.readValue(jsonStr, Map.class);
        } catch (JsonProcessingException e) {
            logger.error("error when parse json", e);
        }

        return null;
    }

    public String toJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            logger.error("error when write json", e);
        }
        return "";
    }
}
