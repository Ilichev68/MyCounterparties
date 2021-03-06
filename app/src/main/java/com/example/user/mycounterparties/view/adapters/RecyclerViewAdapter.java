package com.example.user.mycounterparties.view.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.example.user.mycounterparties.R;
import com.example.user.mycounterparties.view.CounterpartiesItem;

import java.util.ArrayList;

/**
 * Created by User on 17.11.2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    private ArrayList<CounterpartiesItem> items;
    private ArrayList<CounterpartiesItem> mFilteredList;
    private Listner listner;

    public RecyclerViewAdapter(ArrayList<CounterpartiesItem> items) {
        this.items = items;
        mFilteredList = items;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.counterparties_item, parent, false);
        return new RecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        CounterpartiesItem counterpartiesItem = mFilteredList.get(position);
        holder.bindCounterparties(counterpartiesItem);

    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    public void setListner(Listner listner) {
        this.listner = listner;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = items;
                } else {

                    ArrayList<CounterpartiesItem> filteredList = new ArrayList<>();

                    for (CounterpartiesItem counterpartiesItem : items) {

                        if (counterpartiesItem.getName().toLowerCase().contains(charString) || counterpartiesItem.getAddress().toLowerCase().contains(charString)) {

                            filteredList.add(counterpartiesItem);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<CounterpartiesItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView name;
        private TextView address;
        private CounterpartiesItem counterpartiesItem;
        private CheckBox isFavorite;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onClick(counterpartiesItem.getName() + "," + counterpartiesItem.getAddress());
                }
            });


            cardView = itemView.findViewById(R.id.cv);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            isFavorite = itemView.findViewById(R.id.chbFavoriteCounterpartiy);

            isFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        counterpartiesItem.setFavorite(true);
                        listner.onCheckedChanged(counterpartiesItem.isFavorite(), counterpartiesItem.getName() + "," + counterpartiesItem.getAddress());
                    } else if (!b){
                        counterpartiesItem.setFavorite(false);
                        listner.onCheckedChanged(counterpartiesItem.isFavorite(), counterpartiesItem.getName() + "," + counterpartiesItem.getAddress());
                    }
                }
            });
        }

        void bindCounterparties(CounterpartiesItem counterpartiesItem) {
            this.counterpartiesItem = counterpartiesItem;
            name.setText(counterpartiesItem.getName());
            address.setText(counterpartiesItem.getAddress());
            isFavorite.setChecked(counterpartiesItem.isFavorite());
        }
    }

    public interface Listner {
        void onClick(String nameAndAddress);

        void onCheckedChanged(boolean isChecked, String counterpartiesName);
    }
}

