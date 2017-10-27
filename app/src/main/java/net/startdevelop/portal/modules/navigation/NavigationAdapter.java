package net.startdevelop.portal.modules.navigation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.startdevelop.portal.R;
import net.startdevelop.portal.models.NavigationModel;
import java.util.List;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.NavigationViewHolder> {

    private List<NavigationModel> listNavigation;
    private Context context;
    private NavigationClickListener navigationClickListener;
    private int selectPos=0;

    public NavigationAdapter(Context context, NavigationClickListener navigationClickListener) {
        this.context = context;
        this.navigationClickListener = navigationClickListener;
    }

    public void updateData(List<NavigationModel> listNavigation) {
        this.listNavigation = listNavigation;
        notifyDataSetChanged();
    }

    @Override
    public NavigationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navigation,parent,false);
        return new NavigationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NavigationViewHolder holder, final int position) {
        final NavigationModel navigationModel = listNavigation.get(position);
        setDefaultState(holder);
        holder.textNavigation.setText(navigationModel.getTvNavigation());
        if(position == selectPos) {
            holder.pictureNavigation.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPos = position;
                navigationClickListener.onItemClick(navigationModel);
                notifyDataSetChanged();
            }
        });
    }

    private void setDefaultState(final NavigationViewHolder holder) {
        holder.textNavigation.setText(null);
        holder.pictureNavigation.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        if (listNavigation != null){
            return listNavigation.size();
        } else {
            return 0;
        }
    }

    class NavigationViewHolder extends RecyclerView.ViewHolder{
        private TextView textNavigation;
        private ImageView pictureNavigation;

        NavigationViewHolder(final View itemView){
            super(itemView);
            this.textNavigation = (TextView) itemView.findViewById(R.id.tvNavigation);
            this.pictureNavigation = (ImageView)itemView.findViewById(R.id.ivNavigation);
        }
    }
}
