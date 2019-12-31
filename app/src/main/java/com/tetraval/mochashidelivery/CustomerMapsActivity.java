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
import com.tetraval.mochashidelivery.adapter.CustomerAdapter;
import com.tetraval.mochashidelivery.model.CustomerModel;

public class CustomerMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_maps);

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

        Query query = db.collection("chashi_orders");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    CustomerModel customerModel = new CustomerModel();
                    if (!task.getResult().isEmpty()){
                        for (DocumentSnapshot document : task.getResult()) {

                            double lat = Double.parseDouble(document.getString("o_lat"));
                            double lng = Double.parseDouble(document.getString("o_long"));
                            LatLng sydney = new LatLng(lat, lng);
                            mMap.addMarker(new MarkerOptions().position(sydney).title(document.getString("o_customer_name")));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                        }

                    } else {
                        Toast.makeText(CustomerMapsActivity.this, "No products to deliver.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(CustomerMapsActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Add a marker in Sydney and move the camera

    }
}
