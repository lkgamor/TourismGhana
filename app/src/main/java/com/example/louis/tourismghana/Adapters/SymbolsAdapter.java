package com.example.louis.tourismghana.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.louis.tourismghana.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/*
  Created by Louis on 3/30/2018.
 */

public class SymbolsAdapter extends RecyclerView.Adapter<SymbolsAdapter.ViewHolder> {

    private Context context;
    private List<Symbols> symbolsList;
    private CustomOnItemClickLister itemClickListener;

    public SymbolsAdapter(Context context, List<Symbols> symbolsList)
    {
        this.context = context;
        this.symbolsList = symbolsList;
    }

    @Override
    public SymbolsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_symbols_layout, parent, false);
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
    public void onBindViewHolder(SymbolsAdapter.ViewHolder holder, final int position) {

        holder.symbolName.setText(symbolsList.get(position).getName());
        holder.symbolMeaning.setText(symbolsList.get(position).getMeaning());

        Picasso.with(context)
                .load(symbolsList.get(position).getImage())
                .fit()
                .centerInside()
                .placeholder(R.drawable.image_not_found)
                .into(holder.symbolImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Toast.makeText(context, "Could not load "
                                + symbolsList.get(position).getName()
                                + " picture at this time", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return symbolsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View myViewHolder;

        public CardView cardView;
        public ImageView symbolImage;
        public TextView symbolName;
        public TextView symbolMeaning;

        public ViewHolder(View itemView) {
            super(itemView);

            myViewHolder = itemView;
            cardView = (CardView)myViewHolder.findViewById(R.id.symbolscardView);
            symbolName = (TextView)myViewHolder.findViewById(R.id.symbolName);
            symbolMeaning = (TextView)myViewHolder.findViewById(R.id.symbolMeaning);
            symbolImage = (ImageView)myViewHolder.findViewById(R.id.symbolImage);
        }
    }

    public interface CustomOnItemClickLister
    {
        void OnClickListener(View view, int position);
    }

    public void setOnClickListener(CustomOnItemClickLister itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }
}
