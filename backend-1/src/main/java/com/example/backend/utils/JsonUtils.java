package com.example.backend.utils;



import java.io.UnsupportedEncodingException;
import java.util.Base64;

import com.example.backend.dto.JwtPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;

public class JsonUtils {
    public static JwtPayload parseJson(String jsonString) throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException {
    	 
    	String payloadJsonObject=getPayloadJsonObjectFromBase64String(jsonString);
    	ObjectMapper mapper = new ObjectMapper();
    	 JwtPayload payload=null;
    	 try {
              payload = mapper.readValue(payloadJsonObject, JwtPayload.class);
         } catch (IOException e) {
             e.printStackTrace();
         }

        return payload;
    }
    
    public static String getPayloadJsonObjectFromBase64String(String jwt) throws UnsupportedEncodingException
    {
    	jwt=jwt.replace("Bearer ", "");
		String[] pieces = jwt.split("\\.");
		String b64payload = pieces[1];
		String jsonString = new String(Base64.getDecoder().decode(b64payload), "UTF-8");
		return jsonString;
    }
}
