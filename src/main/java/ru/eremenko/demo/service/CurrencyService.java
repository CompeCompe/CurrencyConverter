package ru.eremenko.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import ru.eremenko.demo.parser.XMLparser;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
@Service
public class CurrencyService {

    @Autowired
    private XMLparser xmlParser;

    public void getCourses() throws IOException, ParserConfigurationException, SAXException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = format.format(new Date());
        URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + dateString);

        InputStream xmlStream = url.openStream();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        SAXParser parser = factory.newSAXParser();
        parser.parse(xmlStream, xmlParser);
    }

}
