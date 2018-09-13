package com.app.kargo.FragmentLayout;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.kargo.HistoricActivity;
import com.app.kargo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountActivityFragment extends Fragment {

    Button historicButton;


    public AccountActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_account_activity, container, false);

        historicButton = rootview.findViewById(R.id.historic_button);

        historicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HistoricActivity.class);

                startActivity(intent);
            }
        });






        return rootview;
    }

}
