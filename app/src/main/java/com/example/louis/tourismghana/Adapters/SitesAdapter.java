package com.example.louis.tourismghana.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.louis.tourismghana.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by Louis on 4/2/2018.
 */

public class SitesAdapter extends RecyclerView.Adapter<SitesAdapter.ViewHolder>{

    private Context context;
    private List<Site> sitesList;
    private OnItemClickListener itemClickListener;

    public SitesAdapter(Context context, List<Site> sitesList) {
        this.context = context;
        this.sitesList = sitesList;
    }

    @Override
    public SitesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_site_list_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.OnItemClick(view, viewHolder.getLayoutPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SitesAdapter.ViewHolder holder, final int position) {

        holder.siteName.setText(sitesList.get(position).getName());
        Picasso.with(context)
                .load(sitesList.get(position).getImage())
                .placeholder(R.drawable.image_not_found)
                .fit()
                .centerCrop()
                .into(holder.siteImage, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(context, "Could not load "
                                + sitesList.get(position).getName()
                                + " picture at this time", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return sitesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View viewHolder;
        TextView siteName;
        ImageView siteImage;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            viewHolder = itemView;
            siteName = (TextView) viewHolder.findViewById(R.id.site_name);
            siteImage = (ImageView) viewHolder.findViewById(R.id.siteImage);
            progressBar = (ProgressBar) viewHolder.findViewById(R.id.actionProgress);
        }
    }

    public interface OnItemClickListener{

        void OnItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.itemClickListener = onItemClickListener;
    }
}
