package com.example.keywordnews;

import com.example.keywordnews.model.NewsItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by 밈석 on 2017-01-30.
 */
public class RSSReader {
    private static RSSReader instance = null;

    private URL rssURL;

    private RSSReader() {}

    public static RSSReader getInstance() {
        if (instance == null)
            instance = new RSSReader();
        return instance;
    }

    public void setURL(URL url) {
        rssURL = url;
    }

    public String getValue(Element parent, String nodeName) {
        return parent.getElementsByTagName(nodeName).item(0).getFirstChild().getNodeValue();
    }

    public String getImageUrl(Element parent){
        String nodeName = "description";
        String string = parent.getElementsByTagName(nodeName).item(0).getFirstChild().getNodeValue();
        int idx1, idx2;
        idx1 = string.indexOf("\"");
        idx2 = string.indexOf("\"", idx1+1);
        return string.substring(idx1+1, idx2);
    }

    public ArrayList<NewsItem> LoadNewsData(ArrayList<NewsItem> Newsitems){
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(rssURL.openStream());

            NodeList items = doc.getElementsByTagName("item");

            for (int ii = 0; ii < items.getLength(); ii++) {
                Element item = (Element)items.item(ii);
                Newsitems.add(new NewsItem(getValue(item, "title"), getValue(item, "pubDate"), getImageUrl(item)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Newsitems;
    }
}
