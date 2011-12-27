package controllers;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class DailyBibleVerses extends Application{
   
	public static void dailyBibleVerse() throws Exception {

		URL yahoo = new URL("http://believer.com/outreach/versetodayNIV.xml");
        URLConnection dailyVerse = yahoo.openConnection();
        dailyVerse.setRequestProperty("Content-Type","text/xml");
        InputStream is = dailyVerse.getInputStream();
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document xmlDoc = builder.parse(is);
        
        String parsedXML = xmlDoc.getElementsByTagName("Content").item(0).getTextContent();
        
        render(parsedXML);
    }
}
