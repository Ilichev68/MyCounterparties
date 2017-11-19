package com.example.user.mycounterparties.ui.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.user.mycounterparties.R;
import com.example.user.mycounterparties.ui.CounterpartiesItem;

import java.util.List;

/**
 * Created by User on 17.11.2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<CounterpartiesItem> items;
    private Listner listner;

    public RecyclerViewAdapter(List<CounterpartiesItem> items) {
        this.items = items;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.counterparties_item, parent, false);
        return new RecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        CounterpartiesItem counterpartiesItem = items.get(position);
        holder.bindCounterparties(counterpartiesItem);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setListner(Listner listner) {
        this.listner = listner;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView name;
        private TextView address;
        private CounterpartiesItem counterpartiesItem;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onClick(counterpartiesItem.getName() + "," +  counterpartiesItem.getAddress());
                }
            });

            cardView = itemView.findViewById(R.id.cv);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
        }

        void bindCounterparties(CounterpartiesItem counterpartiesItem){
            this.counterpartiesItem = counterpartiesItem;
            name.setText(counterpartiesItem.getName());
            address.setText(counterpartiesItem.getAddress());
        }
    }

    public interface Listner{
        void onClick(String nameAndAddress);
    }
}

