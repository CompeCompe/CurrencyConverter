package ru.eremenko.demo.parser;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

@Component
public class XMLparser  extends DefaultHandler {
    @Getter
    private static Map<String, Double> courses;
    private String name;
    private String code;
    private int nominal;
    private double value;
    private String elementValue;


    @Autowired
    public XMLparser() {
        courses = new HashMap<>();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        elementValue = new String(ch, start, length);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName.toUpperCase()) {
            case "NUMCODE":
                break;
            case "CHARCODE":
                code = "(" + elementValue + ") ";
                break;
            case "NOMINAL":
                nominal = Integer.parseInt(elementValue);
                break;
            case "NAME":
                name = code + elementValue;
                break;
            case "VALUE":
                value = Double.parseDouble(elementValue.replaceAll(",",".")) / nominal;
                courses.put(name,value);
                break;
            case "VALUTE":
                code = "";
                name = "";
                nominal = 0;
                value = 0;
                break;
            case "VALCURS":
                courses.put("(RUB) Российский рубль",1.0);
                break;
        }
    }
}
