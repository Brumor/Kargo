package com.app.kargo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.kargo.Delivery;
import com.app.kargo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pbric on 08/08/2017.
 */

public class CurrentDeliveriesRecyclerAdapter extends RecyclerView.Adapter<CurrentDeliveriesRecyclerAdapter.CurrentDeliveryViewHolder> {

    Context context;

    List<Delivery> data = Collections.emptyList();
    List<CurrentDeliveryViewHolder> holderList = new ArrayList<>();

    public CurrentDeliveriesRecyclerAdapter(Context context, List<Delivery> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public CurrentDeliveryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_delivery, parent, false);
        CurrentDeliveryViewHolder viewHolder = new CurrentDeliveryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CurrentDeliveryViewHolder holder, int position) {

        Delivery delivery = data.get(position);

        holderList.add(holder);

        holder.timeTextView.setText(Integer.toString(delivery.getDuration()) + "h");
        holder.adressTextView.setText(delivery.getDeliveryDestination());
        holder.nameTextView.setText(delivery.getUserName());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public CurrentDeliveryViewHolder getCDViewHolder(int position) {
        return holderList.get(position);
    }



    //Viewholder gardant les informations contenues dans chaque elements.
    public class CurrentDeliveryViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView adressTextView;


        public TextView timeTextView;
        public ImageView doneIcon;
        public RelativeLayout container;


        public CurrentDeliveryViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.current_username_textview);
            adressTextView = itemView.findViewById(R.id.current_adress_textview);
            timeTextView = itemView.findViewById(R.id.current_time_textview);
            doneIcon = itemView.findViewById(R.id.done_icon);
            container = itemView.findViewById(R.id.current_delivery_container);

        }
    }
}
