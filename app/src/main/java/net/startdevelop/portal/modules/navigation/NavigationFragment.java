package net.startdevelop.portal.modules.navigation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.startdevelop.portal.R;
import net.startdevelop.portal.models.NavigationModel;
import java.util.ArrayList;
import java.util.List;

public class NavigationFragment extends Fragment implements  NavigationClickListener{

    private RecyclerView recycler_navigation;
    private NavigationAdapter navigationAdapter;
    private List<NavigationModel> listNavigation = new ArrayList<>();
    private NavigationFragmentListener navigationFragmentListener;
    private String[] categories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_navigation,null);
        if (view != null){
            categories = getResources().getStringArray(R.array.categories);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            recycler_navigation = (RecyclerView)view.findViewById(R.id.recycler_navigation);
            navigationAdapter = new NavigationAdapter(getActivity(), this);
            recycler_navigation.setLayoutManager(llm);
            recycler_navigation.setAdapter(navigationAdapter);
            for (int a=0; a<categories.length; a++){
                NavigationModel navigationModel = new NavigationModel();
                navigationModel.setTvNavigation(categories[a]);
                listNavigation.add(navigationModel);
            }
            navigationAdapter.updateData(listNavigation);
        }
        return view;
    }

    @Override
    public void onItemClick(final NavigationModel navigationModel) {
        navigationFragmentListener.openSomeListFragment(navigationModel);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationFragmentListener){
            navigationFragmentListener = (NavigationFragmentListener)context;
        }else{
            throw new RuntimeException();
        }
    }
}
