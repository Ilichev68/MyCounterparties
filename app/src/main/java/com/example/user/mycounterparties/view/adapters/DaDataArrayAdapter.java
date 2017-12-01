package com.example.user.mycounterparties.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.List;

/**
 * Created by User on 02.11.2017.
 */

public class DaDataArrayAdapter<String> extends ArrayAdapter<String> {

    private Filter filter = new KNoFilter();
    private List<String> items;

    public DaDataArrayAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.items = items;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    private class KNoFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence arg0) {
            FilterResults result = new FilterResults();
            result.values = items;
            result.count = items.size();
            return result;
        }

        @Override
        protected void publishResults(CharSequence arg0, FilterResults arg1) {
            notifyDataSetChanged();
        }
    }
}
