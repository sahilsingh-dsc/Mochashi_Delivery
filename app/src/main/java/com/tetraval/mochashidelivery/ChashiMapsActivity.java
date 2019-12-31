package com.tetraval.mochashidelivery;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.tetraval.mochashidelivery.adapter.ChashiAdapter;
import com.tetraval.mochashidelivery.model.ChashiModel;

public class ChashiMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseFirestore db;
    double total_amount, local_total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chashi_maps);

        db = FirebaseFirestore.getInstance();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Query query = db.collection("chashi_products");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    if (!task.getResult().isEmpty()){
                        for (DocumentSnapshot document : task.getResult()){
                            ChashiModel chashiModel = new ChashiModel();
                            double b_qty = Double.parseDouble(document.getString("p_bquantity"));
                            total_amount = Double.parseDouble(document.getString("p_rate"));
                            local_total = total_amount + local_total;

                            if (b_qty != 0){

                                double lat = Double.parseDouble(document.getString("p_lat"));
                                double lng = Double.parseDouble(document.getString("p_long"));
                                LatLng sydney = new LatLng(lat, lng);
                                mMap.addMarker(new MarkerOptions().position(sydney).title(document.getString("p_chashi_name")));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                            }


                        }
                    }
                }else {
                    Toast.makeText(ChashiMapsActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Add a marker in Sydney and move the camera

    }
}
