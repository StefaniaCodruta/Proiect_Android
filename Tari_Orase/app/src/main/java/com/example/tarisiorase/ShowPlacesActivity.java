package com.example.tarisiorase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tarisiorase.adapters.PlaceAdapter;
import com.example.tarisiorase.database.DatabaseManager;
import com.example.tarisiorase.models.Place;

import java.util.ArrayList;
import java.util.List;

public class ShowPlacesActivity extends AppCompatActivity {

    private ArrayAdapter<Place> placesArrayAdapter;
    List<Place> placesList= new ArrayList<>();

    ListView lvVisitedPlaces;
    Intent intent;
    public static Place placeSent;
    int selectedIndex=0;
    int selectedIndexUpdate=0;
    static DatabaseManager databaseManager;
    private PlaceAdapter placeAdapter;

    public static String UPDATE_PLACE="updatePlace";
    public static int REQUEST_UPDATE_PLACE=500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_places);

        databaseManager=DatabaseManager.getInstance(getApplicationContext());
        intent=getIntent();
        placesList=databaseManager.getPlacesDao().getAllPlaces();
        lvVisitedPlaces=findViewById(R.id.lvVisitedPlaces);
//        placesArrayAdapter= new ArrayAdapter<>(getApplicationContext(),
//                android.R.layout.simple_list_item_1, placesList);
//        lvVisitedPlaces.setAdapter(placesArrayAdapter);
//        placesArrayAdapter.notifyDataSetChanged();
        placeAdapter=new PlaceAdapter(getApplicationContext(),placesList);
        lvVisitedPlaces.setAdapter(placeAdapter);
        placeAdapter.notifyDataSetChanged();

        lvVisitedPlaces.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex=position;

                AlertDialog.Builder alertDialog=new AlertDialog.Builder(ShowPlacesActivity.this);
                alertDialog.setMessage(R.string.deleteWarningQuestion).setTitle(R.string.deleteRecord)
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                placesList.remove(selectedIndex);
                                placeAdapter.notifyDataSetChanged();

                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog=alertDialog.create();
                dialog.show();
                new DeletePlace().execute(placesList.get(position));
                return true;
            }
        });

        lvVisitedPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ShowPlacesActivity.this,AddPlaceActivity.class);
                Place place=placesList.get(position);
                placeSent=place;
                selectedIndexUpdate=position;
                intent.putExtra(UPDATE_PLACE,place);
                startActivityForResult(intent,REQUEST_UPDATE_PLACE);


            }
        });

    }

    public static class DeletePlace extends AsyncTask<Place, Void, Void> {
        @Override
        protected Void doInBackground(Place... places) {
            databaseManager.getPlacesDao().deletePlace(places[0]);
            return null;
        }
    }

    public static class UpdatePlace extends AsyncTask<Place,Void,Void> {

        @Override
        protected Void doInBackground(Place... places) {
            databaseManager.getPlacesDao().updatePlace(places[0]);
            return  null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_UPDATE_PLACE && resultCode==RESULT_OK && data.hasExtra(AddPlaceActivity.UPDATE_PLACE_KEY)){
            Place place=data.getParcelableExtra(AddPlaceActivity.UPDATE_PLACE_KEY);
            //
            placesList.set(selectedIndexUpdate,place);
            new UpdatePlace().execute(place);
            assert place != null;
            Toast.makeText(getApplicationContext(),place.toString(),Toast.LENGTH_SHORT).show();
            placeAdapter.notifyDataSetChanged();

        }

    }
}
