package kr.momjobgo.eyou.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Converter<T>{

    public String toJsonString(T obj) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        String jsonInString = om.writeValueAsString(obj);
        return jsonInString;
    }

}
