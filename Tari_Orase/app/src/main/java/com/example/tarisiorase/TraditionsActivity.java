package com.example.tarisiorase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarisiorase.adapters.TraditionAdapter;
import com.example.tarisiorase.models.Callback;
import com.example.tarisiorase.models.FireBaseManager;
import com.example.tarisiorase.models.Tradition;

import java.util.ArrayList;
import java.util.List;

public class TraditionsActivity extends AppCompatActivity {

    EditText etLocation,etMoment,etShortDescription;
    Button btn_addTraditionFB,btn_deleteTraditionFB,btn_clearAll;
    ListView lvTraditions;
    List<Tradition> traditionList=new ArrayList<>();
    TraditionAdapter adapter;
    private FireBaseManager fireBaseManager;
    int selectedTraditionIndex=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traditions);

        initControllers();
        fireBaseManager=FireBaseManager.getInstance();
        fireBaseManager.attachDataChangedEventListener(dataChangeCallback());
        adapter=new TraditionAdapter(getApplicationContext(),traditionList);
        lvTraditions.setAdapter(adapter);

    }

    private Callback<List<Tradition>> dataChangeCallback() {
        return new Callback<List<Tradition>>() {
            @Override
            public void runResultOnUIThread(List<Tradition> result) {
                if(result!=null){
                    traditionList.clear();
                    traditionList.addAll(result);
                    notifyTraditionsListAdapter();
                    clearFields();
                }
            }
        };
    }



    private View.OnClickListener clearFieldsEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        };
    }

    private View.OnClickListener deleteTraditionEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTraditionIndex!=-1){
                    fireBaseManager.delete(traditionList.get(selectedTraditionIndex));
                }
            }
        };
    }

    private AdapterView.OnItemClickListener coachSelectEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTraditionIndex=position;
                etLocation.setText(traditionList.get(selectedTraditionIndex).getLocation());
                etMoment.setText(traditionList.get(selectedTraditionIndex).getMoment());
                etShortDescription.setText(traditionList.get(selectedTraditionIndex).getShortDescription());
            }
        };
    }
    private View.OnClickListener addTraditionEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForms()){
                    Tradition tradition=createFromViews();
                    fireBaseManager.upsert(tradition);
                }
            }
        };
    }

    private void initControllers(){
        etLocation=findViewById(R.id.etLocation);
        etMoment=findViewById(R.id.etMoment);
        etShortDescription=findViewById(R.id.etShortDescription);
        btn_addTraditionFB=findViewById(R.id.btn_AddTraditionFB);
        btn_deleteTraditionFB=findViewById(R.id.btn_DeleteTraditionFB);
        btn_clearAll=findViewById(R.id.btnClearAll);
        lvTraditions=findViewById(R.id.lvTraditions);
        lvTraditions.setOnItemClickListener(coachSelectEventListener());
        btn_addTraditionFB.setOnClickListener(addTraditionEventListener());
        btn_deleteTraditionFB.setOnClickListener(deleteTraditionEventListener());
        btn_clearAll.setOnClickListener(clearFieldsEventListener());
    }
    private void clearFields() {
    etLocation.setText(null);
    etMoment.setText(null);
    etShortDescription.setText(null);
    selectedTraditionIndex=-1;
    }

    private Tradition createFromViews(){
        String location=etLocation.getText().toString();
        String moment=etMoment.getText().toString();
        String shortDescription=etShortDescription.getText().toString();

        return new Tradition(location,moment,shortDescription);
    }

    private boolean validateForms(){
        if(etLocation.getText().toString().trim().isEmpty()){
            Toast.makeText(this,R.string.camp_obligatoriu,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(etMoment.getText().toString().trim().isEmpty()){
            Toast.makeText(this,R.string.camp_obligatoriu,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(etShortDescription.getText().toString().trim().isEmpty()){
            Toast.makeText(this,R.string.camp_obligatoriu,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void notifyTraditionsListAdapter(){
        TraditionAdapter traditionAdapter=(TraditionAdapter)lvTraditions.getAdapter();
        traditionAdapter.notifyDataSetChanged();
    }
}
