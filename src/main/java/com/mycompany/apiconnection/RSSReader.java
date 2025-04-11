/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apiconnection;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author rokom
 * https://github.com/rometools/rome/issues/276
 */
public class RSSReader {
    private String url;
    private SyndFeed feed;
 
    RSSReader(String url) {
        this.url = url;
    }
    
    public List<SyndEntryImpl> getTitles() throws IOException, FeedException {
        List<SyndEntryImpl> rssEntries = null;
        try (CloseableHttpClient client = HttpClients.createMinimal()) {
          HttpUriRequest request = new HttpGet(url);
          try (CloseableHttpResponse response = client.execute(request);
               InputStream stream = response.getEntity().getContent()) {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(stream));
            rssEntries = feed.getEntries();
            rssEntries.get(0);
            for (int i = 0; i < rssEntries.size(); i++) {
                System.out.println(rssEntries.get(i).getTitle());
            }
          }
        } catch (IOException | FeedException e) {
            System.out.println(e);
        }
        return rssEntries;
    }
}
