package com.example.louis.tourismghana.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.louis.tourismghana.R;

import java.util.List;

/**
 * Created by Louis on 3/18/2018.
 */

public class FactsAdapter extends RecyclerView.Adapter<FactsAdapter.ViewHolder> {

    private List<Facts> factsList;
    private CustomOnItemClickListener itemClickListener;

    public FactsAdapter(List<Facts> factsList)
    {
        this.factsList = factsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_facts_layout, parent, false);
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
    public void onBindViewHolder(FactsAdapter.ViewHolder holder, int position) {

        holder.factNumber.setText(factsList.get(position).getTitle());
        holder.factDesc.setText(factsList.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return factsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View myViewHolder;

        public TextView factNumber;
        public TextView factDesc;
        public CardView cardView;
        //public int position = 0;

        public ViewHolder(View itemView) {
            super(itemView);

            myViewHolder = itemView;
            cardView = (CardView)myViewHolder.findViewById(R.id.factscardView);
            factNumber = (TextView)myViewHolder.findViewById(R.id.fact_number);
            factDesc = (TextView)myViewHolder.findViewById(R.id.fact_desc);

        }
    }

    public interface CustomOnItemClickListener
    {
        void OnItemClick(View view, int position);
    }

    public void setItemClickListener(CustomOnItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }
}
