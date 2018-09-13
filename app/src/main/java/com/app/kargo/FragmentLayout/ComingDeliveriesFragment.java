package com.app.kargo.FragmentLayout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.kargo.Adapter.ComingDeliveriesRecyclerAdapter;
import com.app.kargo.Delivery;
import com.app.kargo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComingDeliveriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ComingDeliveriesRecyclerAdapter adapter;




    public ComingDeliveriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview =  inflater.inflate(R.layout.fragment_coming_deliveries, container, false);


        //Faux elements
        final List<Delivery> deliveryList = new ArrayList<>();
        deliveryList.add(new Delivery(1, "Ikea", "Lyon 7eme", "Mr Jean", 2, 2, false));
        deliveryList.add(new Delivery(2, "Ikea", "Lyon 7eme", "Mme Leblanc", 3, 2, false));
        deliveryList.add(new Delivery(3, "Ikea", "Lyon 7eme", "Mme Pervenche",3, 2, false));
        deliveryList.add(new Delivery(5, "Ikea", "Lyon 7eme", "Mme Solo", 5, 2, false));

        adapter = new ComingDeliveriesRecyclerAdapter(getContext(), deliveryList);


        recyclerView = rootview.findViewById(R.id.coming_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return rootview;
    }

}
