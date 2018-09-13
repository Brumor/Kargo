package com.app.kargo.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.kargo.HistoricItem;
import com.app.kargo.R;

import java.util.ArrayList;

/**
 * Created by pbric on 31/07/2017.
 */

public class HistoricListAdapter extends ArrayAdapter<HistoricItem> {


    public HistoricListAdapter(Context context, ArrayList<HistoricItem> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View listItemView;

        if (convertView != null) {
            listItemView = convertView;
        } else {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.historic_item, parent, false);

            TextView dateTextview = (TextView) listItemView.findViewById(R.id.historic_item_date);
            TextView nameTextview = (TextView) listItemView.findViewById(R.id.historic_item_name);
            TextView priceTextview = (TextView) listItemView.findViewById(R.id.historic_item_price);

            HistoricItem current = getItem(position);

            dateTextview.setText(current.getTime());
            nameTextview.setText(current.getName());
            priceTextview.setText(Integer.toString(current.getAmount()));

        }

        return listItemView;
    }
}
