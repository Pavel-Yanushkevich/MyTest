package net.startdevelop.portal.modules.somenew;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import com.google.android.youtube.player.YouTubeBaseActivity;
import net.startdevelop.portal.R;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SomeNewActivity extends YouTubeBaseActivity {
    private Toolbar toolbar;
    private RecyclerView listSomeNew;
    private List<String> listText = new ArrayList<>();
    private SomeNewAdapter someNewAdapter;
    private String toolbar_name, name, url, time, newsId;
    private Element someNew;
    private boolean loading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_somenew);

        Intent intent = getIntent();
        toolbar_name = intent.getStringExtra("toolbar_name");
        name = intent.getStringExtra("name");
        url = intent.getStringExtra("url");
        time = intent.getStringExtra("time");
        newsId = intent.getStringExtra("newsId");
        setToolbar();

        listText.add(name);
        listText.add(time);

        listSomeNew = (RecyclerView)findViewById(R.id.listSomeNew);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        someNewAdapter = new SomeNewAdapter(this);
        if(loading) {
            loading=false;
            parseAndSaveNews();
        }
        listSomeNew.setAdapter(someNewAdapter);
        listSomeNew.setLayoutManager(llm);
    }

    private void parseAndSaveNews() {
        new SomeThread().execute();
    }

    private void setToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(toolbar_name);
        toolbar.setNavigationIcon(R.mipmap.ic_keyboard_backspace_black_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    class SomeThread extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... arg) {
            Document doc;
            try {
                doc = Jsoup.connect(url).get();
                if (someNew !=null){
                    someNew=null;
                }
                someNew = doc.select(".post #"+newsId).first();//Тело новости
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            extractContent(someNew, listText);
            someNewAdapter.updateData(listText);
        }
    }

    private void extractContent(Node node, List<String> listText) {
        if (node instanceof TextNode) {
            String text = ((TextNode) node).text().trim();
            if (!text.isEmpty()) listText.add(text); // Добавляем только непустые тексты
        } else if ("img".equals(node.nodeName())) {
            listText.add(node.attr("src"));
        } else if ("iframe".equals(node.nodeName())) {
            listText.add(node.attr("src"));
        } else {
            for (Node child : node.childNodes()) {
                extractContent(child, listText);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listText.clear();
        someNewAdapter.updateData(listText);
    }
}
