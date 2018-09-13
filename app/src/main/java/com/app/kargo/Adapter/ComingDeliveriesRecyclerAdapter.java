package com.app.kargo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.kargo.Delivery;
import com.app.kargo.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by pbric on 08/08/2017.
 */

public class ComingDeliveriesRecyclerAdapter extends RecyclerView.Adapter<ComingDeliveriesRecyclerAdapter.ComingDeliveryViewHolder> {


    Context context;

    List<Delivery> data = Collections.emptyList();

    public ComingDeliveriesRecyclerAdapter(Context context, List<Delivery> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ComingDeliveryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coming_delivery, parent, false);
        ComingDeliveryViewHolder viewHolder = new ComingDeliveryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ComingDeliveryViewHolder holder, int position) {

        Delivery delivery = data.get(position);

        holder.destinationTextView.setText(delivery.getDeliveryDestination());
        holder.durationTextView.setText(Integer.toString(delivery.getDuration()) + "h");
        holder.packageNumberTextView.setText(Integer.toString(delivery.getPackageNumber()) + " Colis");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ComingDeliveryViewHolder extends RecyclerView.ViewHolder {

        TextView destinationTextView;
        TextView packageNumberTextView;
        TextView durationTextView;

        public ComingDeliveryViewHolder(View itemView) {
            super(itemView);

            destinationTextView = itemView.findViewById(R.id.currentd_start_to_destination_textview);
            packageNumberTextView = itemView.findViewById(R.id.currentd_package_number_textview);
            durationTextView = itemView.findViewById(R.id.currentd_time_textview);
        }
    }
}
