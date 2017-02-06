package com.example.keywordnews;

import android.util.Log;

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

    private ArrayList<URL> rssURLs;

    private RSSReader() {
        rssURLs = new ArrayList<>();
    }

    public static RSSReader getInstance() {
        if (instance == null)
            instance = new RSSReader();
        return instance;
    }

    public void addURL(URL url) {
        rssURLs.add(url);
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
//        return string.substring(idx1+1, idx2);
        return "IMG";
    }

    public ArrayList<NewsItem> LoadNewsData(ArrayList<NewsItem> Newsitems){
        try {
//            DocumentBuilder builder;
//            Document doc;
            String crp;
            for(URL rssURL : rssURLs) {
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = builder.parse(rssURL.openStream());
                crp = doc.getElementsByTagName("title").item(0).getFirstChild().getNodeValue();
                NodeList items = doc.getElementsByTagName("item");

                for (int ii = 0; ii < items.getLength(); ii++) {
                    Element item = (Element) items.item(ii);
                    Newsitems.add(new NewsItem( crp, getValue(item, "title"), getValue(item, "link"), getImageUrl(item)));
                }
                Log.d("MIM", "RSS : " + rssURL.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Newsitems;
    }
}
