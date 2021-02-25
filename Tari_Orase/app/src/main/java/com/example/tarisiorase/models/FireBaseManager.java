package com.example.tarisiorase.models;



import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tarisiorase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FireBaseManager {

    public static final String TRADITION_TABLE_NAME="traditions";
    private DatabaseReference databaseReference;

    private static FireBaseManager fireBaseManager;

    private FireBaseManager(){
        databaseReference= FirebaseDatabase.getInstance().getReference(TRADITION_TABLE_NAME);
    }

    public static FireBaseManager getInstance(){
        if(fireBaseManager==null){
            synchronized (FireBaseManager.class){
                if(fireBaseManager==null){
                    fireBaseManager=new FireBaseManager();
                }
            }
        }

        return fireBaseManager;
    }

    public void upsert(Tradition tradition){
        if(tradition==null)
            return;
        if(tradition.getIdTradition()==null || tradition.getIdTradition().trim().isEmpty()){
            String id=databaseReference.push().getKey();
            tradition.setIdTradition(id);
        }

        databaseReference.child(tradition.getIdTradition()).setValue(tradition);
    }

    public void delete(final Tradition tradition){
        if(tradition==null || tradition.getIdTradition()==null || tradition.getIdTradition().trim().isEmpty()){
            return;
        }
        databaseReference.child(tradition.getIdTradition()).removeValue();
    }

    public void attachDataChangedEventListener(final Callback<List<Tradition>> callback){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Tradition> traditionList=new ArrayList<>();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Tradition tradition=dataSnapshot.getValue(Tradition.class);
                    if(tradition!=null){
                        traditionList.add(tradition);
                    }
                }
                callback.runResultOnUIThread(traditionList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
