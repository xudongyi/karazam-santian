package com.klzan.core.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public final class XmlUtils {
    private static final String encoding = "UTF-8";

    private XmlUtils() {
    }

    public static String convertString(Object value) {
        try {
            JAXBContext context = JAXBContext.newInstance(value.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", true);
            marshaller.setProperty("jaxb.encoding", "UTF-8");
            StringWriter writer = new StringWriter();
            marshaller.marshal(value, writer);
            return writer.toString();
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static <T> T convertObject(String xml, Class<T> valueType) {
        try {
            JAXBContext context = JAXBContext.newInstance(valueType);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T)unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static String getRootName(String xmlStr) throws Exception {
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new StringReader(xmlStr));
        Element root = doc.getRootElement();
        return root.getName();
    }

    public static Map<String, String> xml2Map(String xmlStr) throws JDOMException, IOException {
        Map<String, String> rtnMap = new LinkedHashMap();
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new StringReader(xmlStr));
        Element root = doc.getRootElement();
        String rootName = root.getName();
        rtnMap.put("root.name", rootName);
        convert(root, rtnMap, rootName);
        return rtnMap;
    }

    public static void convert(Element e, Map<String, String> map, String lastname) {
        String name;
        if (e.getAttributes().size() > 0) {
            Iterator it_attr = e.getAttributes().iterator();

            while(it_attr.hasNext()) {
                Attribute attribute = (Attribute)it_attr.next();
                String attrname = attribute.getName();
                name = e.getAttributeValue(attrname);
                map.put(lastname + "." + attrname, name);
            }
        }

        List<Element> children = e.getChildren();
        Iterator it = children.iterator();

        while(true) {
            while(it.hasNext()) {
                Element child = (Element)it.next();
                name = child.getName();
                if (child.getChildren().size() > 0) {
                    convert(child, map, lastname + "." + child.getName());
                } else {
                    map.put(name, child.getText());
                    if (child.getAttributes().size() > 0) {
                        Iterator attr = child.getAttributes().iterator();

                        while(attr.hasNext()) {
                            Attribute attribute = (Attribute)attr.next();
                            String attrname = attribute.getName();
                            String attrvalue = child.getAttributeValue(attrname);
                            map.put(lastname + "." + child.getName() + "." + attrname, attrvalue);
                        }
                    }
                }
            }

            return;
        }
    }

    public static List<Map<String, String>> xml2List(String xmlStr) throws JDOMException, IOException {
        List<Map<String, String>> rtnList = new ArrayList();
        Map<String, String> rtnMap = new HashMap();
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new StringReader(xmlStr));
        Element root = doc.getRootElement();
        String rootName = root.getName();
        rtnMap.put("root.name", rootName);
        convert2List(root, rtnMap, rootName, rtnList);
        if (rtnList.size() == 0) {
            rtnList.add(rtnMap);
        }

        return rtnList;
    }

    public static void convert2List(Element e, Map<String, String> map, String lastname, List<Map<String, String>> list) {
        String name;
        if (e.getAttributes().size() > 0) {
            Iterator it_attr = e.getAttributes().iterator();

            while(it_attr.hasNext()) {
                Attribute attribute = (Attribute)it_attr.next();
                String attrname = attribute.getName();
                name = e.getAttributeValue(attrname);
                ((Map)map).put(attrname, name);
            }
        }

        List<Element> children = e.getChildren();
        Iterator it = children.iterator();

        while(it.hasNext()) {
            Element child = (Element)it.next();
            name = lastname + "." + child.getName();
            if (child.getChildren().size() > 0) {
                convert(child, (Map)map, name);
            } else {
                ((Map)map).put(name, child.getText());
                if (child.getAttributes().size() > 0) {
                    Iterator attr = child.getAttributes().iterator();

                    while(attr.hasNext()) {
                        Attribute attribute = (Attribute)attr.next();
                        String attrname = attribute.getName();
                        String attrvalue = child.getAttributeValue(attrname);
                        ((Map)map).put(name + "." + attrname, attrvalue);
                    }
                }
            }

            if (e.getChildren(child.getName()).size() > 1) {
                Map<String, String> aMap = new HashMap();
                aMap.putAll((Map)map);
                list.add(aMap);
                map = new HashMap();
                ((Map)map).put("root.name", aMap.get("root.name"));
            }
        }

    }

    public static void printMap(Map<String, String> map) {
        Iterator keys = map.keySet().iterator();

        while(keys.hasNext()) {
            String key = (String)keys.next();
            System.out.println(key + ":" + (String)map.get(key));
        }

    }
}
