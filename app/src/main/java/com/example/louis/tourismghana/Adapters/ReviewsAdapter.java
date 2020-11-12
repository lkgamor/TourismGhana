package com.example.louis.tourismghana.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.louis.tourismghana.R;

import java.util.List;

/**
 * Created by Louis on 4/12/2018.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private List<Reviews> reviewsList;

    public ReviewsAdapter(List<Reviews> reviewsList) {
        this.reviewsList = reviewsList;
    }

    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_reviews_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ViewHolder holder, int position) {

        holder.username.setText(reviewsList.get(position).getName());
        holder.message.setText(reviewsList.get(position).getMessage());
        holder.dateTime.setText(reviewsList.get(position).getDateTime());
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View viewHolder;

        public TextView username;
        public TextView message;
        public TextView dateTime;

        public ViewHolder(View itemView) {
            super(itemView);

            viewHolder = itemView;
            username = (TextView)viewHolder.findViewById(R.id.name);
            message = (TextView)viewHolder.findViewById(R.id.message);
            dateTime = (TextView)viewHolder.findViewById(R.id.dateTime);
        }
    }
}
