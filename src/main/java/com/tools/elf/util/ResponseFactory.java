package com.tools.elf.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @create 2017-11-20 18:11
 **/
public class ResponseFactory {
    public static Object instance(Boolean b , Object data, String info){
        Map<String,Object> map =new HashMap<String, Object>();
        map.put("success",b);
        map.put("data",data);
        map.put("info",info);
        return map;
    }
}
