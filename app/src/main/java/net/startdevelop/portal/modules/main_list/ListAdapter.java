package net.startdevelop.portal.modules.main_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import net.startdevelop.portal.R;
import net.startdevelop.portal.models.NewsModel;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder>{

    private List<NewsModel> list;
    private Context context;
    private ListClickListener listClickListener;

    public ListAdapter(Context context, ListClickListener listClickListener) {
        this.context = context;
        this.listClickListener = listClickListener;
    }

    public void updateData(List<NewsModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mainlist,parent,false);
        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, final int position) {
        final NewsModel newsModel = list.get(position);
        setDefaultState(holder);
        holder.name.setText(newsModel.getName());
        holder.time.setText(newsModel.getTime());
        Glide.with(context).load(newsModel.getImage()).asBitmap().format(DecodeFormat.PREFER_RGB_565).thumbnail(0.1f).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listClickListener.onItemClick(newsModel);
            }
        });
    }

    private void setDefaultState(final ListViewHolder holder) {
        holder.name.setText("");
        holder.time.setText("");
        holder.image.setImageDrawable(null);
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        } else {
            return 0;
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView time;
        private ImageView image;

        ListViewHolder(View itemView){
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.ivImage);
            this.name = (TextView) itemView.findViewById(R.id.tvName);
            this.time = (TextView) itemView.findViewById(R.id.tvTime);
        }
    }
}
