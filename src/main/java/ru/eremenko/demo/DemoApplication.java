package ru.eremenko.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;
import ru.eremenko.demo.service.CurrencyService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		BrowserOpener opener = new BrowserOpener();
		opener.openLinkInBrowser("http://localhost:8080/exchange");
	}

}
