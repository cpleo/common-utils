package com.pgleo.utils;

import java.lang.reflect.Field;

/**
 * @author : pgleo
 * @version V1.0
 * @Description: 对象工具类
 * @date Date : 16:2616:26
 * @since JDK 1.8.0.181
 */
public class ObecjtUtil {

    /**
     * 对象属性复制
     * @param src 源对象
     * @param dest 填充对象
     */
    public static void copyProperties(Object src,Object dest){
        if(src==null || dest==null){
            return ;
        }
        Field[] srcFields = src.getClass().getDeclaredFields();
        for(Field field:srcFields){
            String fieldName = field.getName();
            try {
                Field destField = dest.getClass().getDeclaredField(fieldName);
                if(field.getType().equals(destField.getType())){
                    destField.setAccessible(true);
                    field.setAccessible(true);
                    destField.set(dest, field.get(src));
                }
            } catch (NoSuchFieldException e) {
            } catch (IllegalAccessException e) {
            }
        }
    }
}
