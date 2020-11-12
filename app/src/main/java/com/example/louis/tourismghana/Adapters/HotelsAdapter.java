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

/**
 * Created by Louis on 4/6/2018.
 */

public class HotelsAdapter extends RecyclerView.Adapter<HotelsAdapter.ViewHolder> {

    private Context context;
    private List<Hotels> hotelList;
    private OnItemClickListener itemClickListener;

    public HotelsAdapter(Context context, List<Hotels> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
    }

    @Override
    public HotelsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_hotels_list_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.OnClickListener(view, viewHolder.getLayoutPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HotelsAdapter.ViewHolder holder, final int position) {

        holder.hotelName.setText(hotelList.get(position).getName());
        Picasso.with(context)
                .load(hotelList.get(position).getImage())
                .placeholder(R.drawable.image_not_found)
                .fit()
                .centerCrop()
                .into(holder.hotelPic, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(context, "Could not load "
                                + hotelList.get(position).getName()
                                + " picture at this time", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView hotelName;
        public ImageView hotelPic;
        public ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            View viewHolder = itemView;

            progressBar = (ProgressBar)viewHolder.findViewById(R.id.actionProgress);
            hotelName = (TextView)viewHolder.findViewById(R.id.hotelName);
            hotelPic = (ImageView)viewHolder.findViewById(R.id.hotelImage);
        }
    }

    public interface OnItemClickListener{
        void OnClickListener(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        itemClickListener = listener;
    }
}
