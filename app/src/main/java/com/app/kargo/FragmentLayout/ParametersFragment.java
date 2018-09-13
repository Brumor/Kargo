package com.app.kargo.FragmentLayout;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.kargo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParametersFragment extends Fragment {

    private SeekBar radiusSeekBar;

    private Spinner brandSpinner;
    private Spinner modeleSpinner;

    private RadioButton adressRadioButton;

    private TextView radiusTextView;
    private TextView adressTextView;

    private EditText adressEditText;

    private Button lButton;
    private Button xlButton;
    private Button xxlButton;
    private Button packageButton;
    private Button coldButton;
    private Button fragileButton;

    private boolean isPackageEnabled = false;
    private boolean isColdEnabled = false;
    private boolean isFragileEnabled = false;

    public ParametersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_parameters, container, false);

        radiusTextView= rootview.findViewById(R.id.radius_value_textview);
        adressTextView = rootview.findViewById(R.id.parameters_adress_textview);

        adressEditText = rootview.findViewById(R.id.parameters_adress_edittext);

        lButton = rootview.findViewById(R.id.button_l);
        xlButton = rootview.findViewById(R.id.button_xl);
        xxlButton = rootview.findViewById(R.id.button_xxl);
        packageButton = rootview.findViewById(R.id.button_package);
        coldButton = rootview.findViewById(R.id.button_cold);
        fragileButton = rootview.findViewById(R.id.button_fragile);

        adressRadioButton = rootview.findViewById(R.id.parameters_adress_radiobutton);

        //Set color for car size
        lButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lButton.setTextColor(Color.WHITE);
                xlButton.setTextColor(Color.LTGRAY);
                xxlButton.setTextColor(Color.LTGRAY);
            }
        });
        xlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lButton.setTextColor(Color.LTGRAY);
                xlButton.setTextColor(Color.WHITE);
                xxlButton.setTextColor(Color.LTGRAY);
            }
        });
        xxlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lButton.setTextColor(Color.LTGRAY);
                xlButton.setTextColor(Color.LTGRAY);
                xxlButton.setTextColor(Color.WHITE);
            }
        });
        packageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isPackageEnabled) {
                    packageButton.setTextColor(Color.BLACK);
                    isPackageEnabled = false;
                } else {
                    packageButton.setTextColor(Color.WHITE);
                    isPackageEnabled = true;
                }

            }
        });
        coldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isColdEnabled) {
                    coldButton.setTextColor(Color.BLACK);
                    isColdEnabled = false;
                } else {
                    coldButton.setTextColor(Color.WHITE);
                    isColdEnabled = true;
                }

            }
        });
        fragileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isFragileEnabled) {
                    fragileButton.setTextColor(Color.BLACK);
                    isFragileEnabled = false;
                } else {
                    fragileButton.setTextColor(Color.WHITE);
                    isFragileEnabled = true;
                }

            }
        });

        //if adress radiobutton is not selected, disable the textview
        adressRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    adressEditText.setEnabled(true);
                    adressTextView.setTextColor(getActivity().getResources().getColor(R.color.text));
                } else {
                    adressEditText.setEnabled(false);
                    adressTextView.setTextColor(getActivity().getResources().getColor(R.color.text_unselected));
                }

            }
        });

        //Change the text as the bar change
        radiusSeekBar = rootview.findViewById(R.id.radius_seekbar);
        radiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                radiusTextView.setText(Integer.toString(i) + " km");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        brandSpinner = rootview.findViewById(R.id.car_brand_spinner);
        modeleSpinner = rootview.findViewById(R.id.car_modele_spinner);

        ArrayAdapter<CharSequence> brandAdapter = ArrayAdapter.createFromResource(getContext(), R.array.car_brand, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> modeleAdapter = ArrayAdapter.createFromResource(getContext(), R.array.car_modele, android.R.layout.simple_spinner_item);


        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        brandSpinner.setAdapter(brandAdapter);
        modeleSpinner.setAdapter(modeleAdapter);

        return rootview;
    }

}
