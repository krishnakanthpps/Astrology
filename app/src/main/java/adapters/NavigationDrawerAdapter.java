package adapters;

/**
 * @author harsha
 */

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.anagha.astrology.R;

import java.util.Collections;
import java.util.List;

import models.NavDrawerItem;


public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public NavDrawerItem getPosition(int position) {
        return data.get(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        if (position == 0) {
            holder.title.setText(current.getTitle());
            holder.title.setVisibility(View.GONE);
            holder.titleIM.setImageResource(current.getTitleIM());
            holder.titleIM.setVisibility(View.GONE);
        } else {
            holder.title.setText(current.getTitle());
            holder.titleIM.setImageResource(current.getTitleIM());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView titleIM;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            titleIM = (ImageView) itemView.findViewById(R.id.friend_profile_picture);

        }
    }
}
