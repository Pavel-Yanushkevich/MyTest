package net.startdevelop.portal.modules.main_list;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.startdevelop.portal.R;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.startdevelop.portal.models.NewsModel;

public class ListFragment extends Fragment implements ListClickListener{

    private ListAdapter listAdapter;
    private RecyclerView recyclerView;
    private Elements namesList, urlsList, timesList, imagesList, newsIdsList;
    private List<NewsModel> listModels = new ArrayList<>();
    private boolean loading = true;
    private ListFragmentListener listFragmentListener;
    private int numberPage=1;
    private String address;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, null);
        if (view != null){
            address=getArguments().getString("address");
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
            listAdapter = new ListAdapter(getActivity(), this);
            parseAndSaveNews(numberPage);
            recyclerView.setAdapter(listAdapter);
            recyclerView.setLayoutManager(llm);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0) {
                        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                       // Log.d("logs", "lastVisibleItemPosition-" + lastVisibleItemPosition);
                        if (loading){
                            if (lastVisibleItemPosition == listAdapter.getItemCount() - 1) {
                                numberPage++;
                                loading = false;
                                parseAndSaveNews(numberPage);
                            }
                        }
                    }
                }
            });
        }
        return view;
    }

    private void parseAndSaveNews(int numberPage) {
        new NewsThread().execute();
    }

    @Override
    public void onItemClick(NewsModel newsModel) {
        listFragmentListener.openSomeNewFragment(newsModel);
    }

    class NewsThread extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... arg) {
            Document doc;
            try {
                doc = Jsoup.connect("https://banana.by/index.php?cstart="+numberPage+address).get();

                if(namesList!=null && urlsList!=null && timesList!=null && imagesList!=null&& newsIdsList!=null) {
                    namesList.clear();
                    urlsList.clear();
                    timesList.clear();
                    imagesList.clear();
                    newsIdsList.clear();
                }
                namesList = doc.select(".main_post .mp_img .mp_name"); //Заголовки новостей
                urlsList = doc.select(".main_post .mp_img a"); //Ссылки новостей
                timesList = doc.select(".main_post .mp_time"); //Время добавления новотей
                imagesList = doc.select(".main_post .mp_img a img"); //Картинки новостей
                newsIdsList = doc.select(".main_post .mp_text div"); //ID новостей
                if (namesList!=null && urlsList!=null && timesList!=null && imagesList!=null&& newsIdsList!=null){
                    for (int a=0; a<newsIdsList.size(); a++) {
                        if (newsIdsList.get(a).id().equals("")) {
                            namesList.remove(a - 1);
                            urlsList.remove(a - 1);
                            timesList.remove(a - 1);
                            imagesList.remove(a - 1);
                            newsIdsList.remove(a);
                            newsIdsList.remove(a - 1);
                            a--;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
                saveNews();
        }
    }

    private void saveNews() {
        if (namesList!=null && urlsList!=null && timesList!=null && imagesList!=null&& newsIdsList!=null) {
            for (int a = 0; a < namesList.size(); a++) {
               final NewsModel newsModel = new NewsModel();
                newsModel.setName(namesList.get(a).text());
                newsModel.setUrl(urlsList.get(a).attr("abs:href"));
                newsModel.setTime(timesList.get(a).text());
                newsModel.setImage(imagesList.get(a).attr("abs:src"));
                newsModel.setNewsId(newsIdsList.get(a).id());
                listModels.add(newsModel);
            }
        }
            listAdapter.updateData(listModels);
            //Log.d("logs", "list size-" + listModels.size());
           // Log.d("logs", "adapteritemcount-" + listAdapter.getItemCount());
            loading = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listModels.clear();
        listAdapter.updateData(listModels);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListFragmentListener){
            listFragmentListener = (ListFragmentListener)context;
        }else{
            throw new RuntimeException();
        }
    }
}
