package com.example.keywordnews;

import android.util.Log;

import com.example.keywordnews.model.NewsItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by 밈석 on 2017-01-30.
 */
public class RSSReader {
    private static RSSReader instance = null;
//TODO: rss 피드들을 해쉬맵을 통해 String으로 접근할 수 있게 하면 좋을듯.
    private static ArrayList<URL> rssURLs;

    private RSSReader() {
        rssURLs = new ArrayList<>();
    }

    public static RSSReader getInstance() {
        if (instance == null) {
            instance = new RSSReader();
            try {
                // <국제>
                rssURLs.add(new URL("http://www.chosun.com/site/data/rss/international.xml")); //조선
                rssURLs.add(new URL("http://rss.nocutnews.co.kr/NocutGlobal.xml")); //노컷
                rssURLs.add(new URL("http://rss.donga.com/international.xml")); //동아
                rssURLs.add(new URL("http://rss.segye.com/segye_international.xml")); //세계
                rssURLs.add(new URL("http://file.mk.co.kr/news/rss/rss_30300018.xml")); //매일
                rssURLs.add(new URL("http://www.fnnews.com/rss/fn_realnews_international.xml")); //파이낸셜
                rssURLs.add(new URL("http://biz.heraldm.com/rss/010110000000.xml")); //헤럴드
            } catch(MalformedURLException e){
                Log.d("MIM", "RSSReader getInstance() MalformedURL ");

            }
        }
        return instance;
    }

    public String getValue(Element parent, String nodeName) {
        return parent.getElementsByTagName(nodeName).item(0).getFirstChild().getNodeValue();
    }

    public String getDate(Element parent){
//        각 언론사의 피드마다 날짜 태그가 다름 -_-;;
        Node formDCdate = parent.getElementsByTagName("dc:date").item(0);
        Node formPUBdate = parent.getElementsByTagName("pubDate").item(0);
        Node formATOM   = parent.getElementsByTagName("atom:published").item(0);

        Node date = (formDCdate != null ? formDCdate : (formPUBdate != null) ? formPUBdate : formATOM);

        return date.getFirstChild().getNodeValue();
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
                    Log.d("MIM", "date : " + getDate(item));
                }
                Log.d("MIM", "RSS : " + rssURL.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Newsitems;
    }
}
