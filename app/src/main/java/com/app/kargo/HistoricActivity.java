package com.app.kargo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.app.kargo.Adapter.HistoricListAdapter;

import java.util.ArrayList;

public class HistoricActivity extends AppCompatActivity {

    ArrayList<HistoricItem> historicItems;
    HistoricListAdapter historicListAdapter;
    ListView listView;

    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic);

        historicItems = new ArrayList<>();
        historicItems.add(new HistoricItem("Auj.", "Mme Durand", 15));
        historicItems.add(new HistoricItem("Auj.", "Mme Durand", 15));
        historicItems.add(new HistoricItem("Auj.", "Mme Durand", 15));
        historicItems.add(new HistoricItem("Auj.", "Mme Durand", 15));
        historicItems.add(new HistoricItem("Auj.", "Mme Durand", 15));
        historicItems.add(new HistoricItem("Auj.", "Mme Durand", 15));
        historicItems.add(new HistoricItem("Auj.", "Mme Durand", 15));
        historicItems.add(new HistoricItem("Auj.", "Mme Durand", 15));
        historicItems.add(new HistoricItem("Auj.", "Mme Durand", 15));
        historicItems.add(new HistoricItem("Auj.", "Mme Durand", 15));
        historicItems.add(new HistoricItem("Auj.", "Mme Durand", 15));
        historicItems.add(new HistoricItem("Auj.", "Mme Durand", 15));
        historicItems.add(new HistoricItem("Auj.", "Mme Durand", 15));
        historicItems.add(new HistoricItem("Auj.", "Mme Durand", 15));
        historicItems.add(new HistoricItem("Auj.", "Mme Durand", 15));
        historicItems.add(new HistoricItem("Auj.", "Mme Durand", 15));
        historicListAdapter = new HistoricListAdapter(this, historicItems);
        listView = (ListView) findViewById(R.id.historic_listview);
        listView.setAdapter(historicListAdapter);

        finishButton = (Button) findViewById(R.id.end_historic_button);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
