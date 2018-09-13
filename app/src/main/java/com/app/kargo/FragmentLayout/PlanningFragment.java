package com.app.kargo.FragmentLayout;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.app.kargo.R;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanningFragment extends Fragment {

    private CheckBox mondayCheckbox;
    private CheckBox tuesdayCheckbox;
    private CheckBox wednesdayCheckbox;
    private CheckBox thursdayCheckbox;
    private CheckBox fridayCheckbox;
    private CheckBox saturdayCheckbox;
    private CheckBox sundayCheckbox;

    private RangeSeekBar mondayRangeSeekBar;
    private RangeSeekBar tuesdayRangeSeekBar;
    private RangeSeekBar wednesdayRangeSeekBar;
    private RangeSeekBar thursdayRangeSeekBar;
    private RangeSeekBar fridayRangeSeekBar;
    private RangeSeekBar saturdayRangeSeekBar;
    private RangeSeekBar sundayRangeSeekBar;

    private TextView mondayTextView;
    private TextView tuesdayTextView;
    private TextView wednesdayTextView;
    private TextView thursdayTextView;
    private TextView fridayTextView;
    private TextView saturdayTextView;
    private TextView sundayTextView;




    private TextView totalTextView;

    private List<CheckBox> checkBoxArrayList;
    private List<RangeSeekBar> seekBarArrayList;
    private List<TextView> daysTextViewArrayList;



    public PlanningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_planning, container, false);

        mondayCheckbox =  rootview.findViewById(R.id.planning_monday_checkbox);
        tuesdayCheckbox =  rootview.findViewById(R.id.planning_tuesday_checkbox);
        wednesdayCheckbox =  rootview.findViewById(R.id.planning_wednesday_checkbox);
        thursdayCheckbox =  rootview.findViewById(R.id.planning_thursday_checkbox);
        fridayCheckbox = rootview.findViewById(R.id.planning_friday_checkbox);
        saturdayCheckbox = rootview.findViewById(R.id.planning_saturday_checkbox);
        sundayCheckbox = rootview.findViewById(R.id.planning_sunday_checkbox);

        checkBoxArrayList = new ArrayList<>();
        checkBoxArrayList.add(mondayCheckbox);
        checkBoxArrayList.add(tuesdayCheckbox);
        checkBoxArrayList.add(wednesdayCheckbox);
        checkBoxArrayList.add(thursdayCheckbox);
        checkBoxArrayList.add(fridayCheckbox);
        checkBoxArrayList.add(saturdayCheckbox);
        checkBoxArrayList.add(sundayCheckbox);

        mondayRangeSeekBar = rootview.findViewById(R.id.planning_monday_rangeseekbar);
        tuesdayRangeSeekBar = rootview.findViewById(R.id.planning_tuesday_rangeseekbar);
        wednesdayRangeSeekBar = rootview.findViewById(R.id.planning_wednesday_rangeseekbar);
        thursdayRangeSeekBar = rootview.findViewById(R.id.planning_thursday_rangeseekbar);
        fridayRangeSeekBar = rootview.findViewById(R.id.planning_friday_rangeseekbar);
        saturdayRangeSeekBar = rootview.findViewById(R.id.planning_saturday_rangeseekbar);
        sundayRangeSeekBar = rootview.findViewById(R.id.planning_sunday_rangeseekbar);

        seekBarArrayList = new ArrayList<>();
        seekBarArrayList.add(mondayRangeSeekBar);
        seekBarArrayList.add(tuesdayRangeSeekBar);
        seekBarArrayList.add(wednesdayRangeSeekBar);
        seekBarArrayList.add(thursdayRangeSeekBar);
        seekBarArrayList.add(fridayRangeSeekBar);
        seekBarArrayList.add(saturdayRangeSeekBar);
        seekBarArrayList.add(sundayRangeSeekBar);

        mondayTextView = rootview.findViewById(R.id.planning_monday_textview);
        tuesdayTextView = rootview.findViewById(R.id.planning_tuesday_textview);
        wednesdayTextView = rootview.findViewById(R.id.planning_wednesday_textview);
        thursdayTextView = rootview.findViewById(R.id.planning_thursday_textview);
        fridayTextView = rootview.findViewById(R.id.planning_friday_textview);
        saturdayTextView = rootview.findViewById(R.id.planning_saturday_textview);
        sundayTextView = rootview.findViewById(R.id.planning_sunday_textview);

        daysTextViewArrayList = new ArrayList<>();
        daysTextViewArrayList.add(mondayTextView);
        daysTextViewArrayList.add(tuesdayTextView);
        daysTextViewArrayList.add(wednesdayTextView);
        daysTextViewArrayList.add(thursdayTextView);
        daysTextViewArrayList.add(fridayTextView);
        daysTextViewArrayList.add(saturdayTextView);
        daysTextViewArrayList.add(sundayTextView);

        totalTextView = rootview.findViewById(R.id.planning_total_textview);

        for (int i = 0; i < 7; i++) {

            final CheckBox checkBox = checkBoxArrayList.get(i);
            final RangeSeekBar seekBar = seekBarArrayList.get(i);
            final TextView daysTextView = daysTextViewArrayList.get(i);

            seekBar.setSelectedMinValue(12);
            seekBar.setSelectedMaxValue(16);



            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    changeBarState(b, seekBar, daysTextView);

                }
            });

            seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                    setTotalTextView();
                }
            });

        }

        setTotalTextView();



        return rootview;
    }



    private void changeBarState (boolean b, RangeSeekBar seekBar, TextView textView) {
        if (b) {

            seekBar.setEnabled(false);
            textView.setTextColor(getContext().getResources().getColor(R.color.text_unselected));
            seekBarArrayList.remove(seekBar);
            setTotalTextView();

        } else {

            seekBar.setEnabled(true);
            textView.setTextColor(getContext().getResources().getColor(R.color.text));
            seekBarArrayList.add(seekBar);
            setTotalTextView();
        }

    }

    private void setTotalTextView () {

        int totalHour = 0;
        for (RangeSeekBar seekBar: seekBarArrayList) {

            totalHour += seekBar.getSelectedMaxValue().intValue() - seekBar.getSelectedMinValue().intValue();

        }

        if (totalHour >= 35) {
            totalTextView.setTextColor(Color.RED);
        } else {
            totalTextView.setTextColor(getActivity().getResources().getColor(R.color.text));
        }
        totalTextView.setText(Integer.toString(totalHour));

    }

}

