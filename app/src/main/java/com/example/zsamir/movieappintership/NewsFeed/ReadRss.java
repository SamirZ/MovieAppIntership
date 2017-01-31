package com.example.zsamir.movieappintership.NewsFeed;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.zsamir.movieappintership.Adapters.NewsFeedAdapter;
import com.example.zsamir.movieappintership.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by zsami on 21-Jan-17.
 */

public class ReadRss extends AsyncTask<Void,Void,Void> {

    private ArrayList<FeedItem> feedItems;
    private Context context;
    private ProgressDialog progressDialog;
    private String address = "http://www.boxofficemojo.com/data/rss.php?file=topstories.xml";
    private URL url;
    private RecyclerView recyclerView;

    public ReadRss(Context context, RecyclerView recyclerView){
        this.context = context;
        this.recyclerView = recyclerView;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();

        NewsFeedAdapter newsFeedAdapter = new NewsFeedAdapter(feedItems,context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(newsFeedAdapter);
    }

    @Override
    protected Void doInBackground(Void... params) {
        ProcessXML(getData());
        return null;
    }

    private void ProcessXML(Document data) {
        if(data!=null){
            feedItems = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for(int i=0;i<items.getLength();i++){
                Node currentChild = items.item(i);
                if(currentChild.getNodeName().equalsIgnoreCase("item")){
                    NodeList itemChilds = currentChild.getChildNodes();

                    FeedItem feedItem = new FeedItem();

                    for(int j=0;j<itemChilds.getLength();j++){
                        Node current = itemChilds.item(j);
                        if(current.getNodeName().equalsIgnoreCase("title")){
                            feedItem.setTitle(current.getTextContent());
                        }else if(current.getNodeName().equalsIgnoreCase("description")){
                            feedItem.setDescription(current.getTextContent());
                        }else if(current.getNodeName().equalsIgnoreCase("link")){
                            feedItem.setLink(current.getTextContent());
                        }else if(current.getNodeName().equalsIgnoreCase("author")){
                            feedItem.setAuthor(current.getTextContent());
                        }else if(current.getNodeName().equalsIgnoreCase("pubDate")){
                            feedItem.setPubDate(current.getTextContent());
                        }
                    }

                    feedItems.add(feedItem);
                }
            }
        }
    }

    public Document getData(){
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            return builder.parse(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
