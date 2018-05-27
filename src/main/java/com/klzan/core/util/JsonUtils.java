package com.klzan.core.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * Utils - JSON
 * 
 * @author suhao
 * @version 1.0
 */
public final class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /** ObjectMapper */
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 不可实例化
     */
    private JsonUtils() {
    }

    /**
     * 将对象转换为JSON字符串
     * 
     * @param value
     *            对象
     * @return JSON字符串
     */
    public static String toJson(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            logger.error("Object 转 JSON 出错！", e);
            throw new RuntimeException(e);
        }
    }

    public static String xml2Json(String value) {
        try {
            Map[] collection = new XmlMapper().readValue(value, Map[].class);
            return toJson(collection);
        } catch (IOException e) {
            logger.error("XNL 转 JSON 出错！", e);
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 将JSON字符串转换为对象
     * 
     * @param json
     *            JSON字符串
     * @param type
     *            对象类型
     * @return 对象
     */
    public static <T> T toObject(String json, Class<T> type) {
        Assert.hasText(json);
        Assert.notNull(type);
        try {
            return mapper.readValue(json, type);
        } catch (Exception e) {
            logger.error("JSON 转 Object 出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将JSON字符串转换为对象
     * 
     * @param json
     *            JSON字符串
     * @param type
     *            对象类型
     * @return 对象
     */
    public static <T> T toObjectCamel(String json, Class<T> type) {
        Assert.hasText(json);
        Assert.notNull(type);
        try {
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            return mapper.readValue(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON字符串转换为对象
     * 
     * @param json
     *            JSON字符串
     * @param typeReference
     *            对象类型
     * @return 对象
     */
    public static <T> T toObject(String json, TypeReference<?> typeReference) {

        Assert.hasText(json);
        Assert.notNull(typeReference);

        try {
            return mapper.readValue(json, typeReference);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON字符串转换为对象
     * 
     * @param json
     *            JSON字符串
     * @param javaType
     *            对象类型
     * @return 对象
     */
    public static <T> T toObject(String json, JavaType javaType) {

        Assert.hasText(json);
        Assert.notNull(javaType);

        try {
            return mapper.readValue(json, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将对象转换为JSON流
     * 
     * @param writer
     *            writer
     * @param value
     *            对象
     */
    public static void writeValue(Writer writer, Object value) {
        try {
            mapper.writeValue(writer, value);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        sb.append("<info>");
        sb.append("<user>");
        sb.append("<name>李二</name>");
        sb.append("<age>36</age>");
        sb.append("</user>");
        sb.append("<user>");
        sb.append("<name>王五</name>");
        sb.append("<age>21</age>");
        sb.append("</user>");
        sb.append("</info>");
        sb.append("<info>");
        sb.append("<user>");
        sb.append("<name>李三</name>");
        sb.append("<age>29</age>");
        sb.append("</user>");
        sb.append("<user>");
        sb.append("<name>王六</name>");
        sb.append("<age>18</age>");
        sb.append("</user>");
        sb.append("</info>");
        System.out.println(JsonUtils.xml2Json(sb.toString()));
    }

}