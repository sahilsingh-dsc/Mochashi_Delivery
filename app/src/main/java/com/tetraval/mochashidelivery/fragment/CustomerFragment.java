package com.tetraval.mochashidelivery.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.tetraval.mochashidelivery.ChashiMapsActivity;
import com.tetraval.mochashidelivery.CustomerMapsActivity;
import com.tetraval.mochashidelivery.R;
import com.tetraval.mochashidelivery.adapter.ChashiAdapter;
import com.tetraval.mochashidelivery.adapter.CustomerAdapter;
import com.tetraval.mochashidelivery.model.CustomerModel;

import java.util.ArrayList;
import java.util.List;

public class CustomerFragment extends Fragment {

    RecyclerView recyclerCustomer;
    List<CustomerModel> customerModelList;
    CustomerAdapter customerAdapter;
    FirebaseFirestore db;
    TextView txtMap;

    public CustomerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);

        db = FirebaseFirestore.getInstance();

        recyclerCustomer = view.findViewById(R.id.recyclerCustomer);
        recyclerCustomer.setLayoutManager(new LinearLayoutManager(getContext()));
        txtMap = view.findViewById(R.id.txtMap);
        txtMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CustomerMapsActivity.class));
            }
        });

        customerModelList = new ArrayList<>();
        customerModelList.clear();

        fetchCustomer();

        return view;
    }


    private void fetchCustomer(){
        Query query = db.collection("chashi_orders");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    CustomerModel customerModel = new CustomerModel();
                    if (!task.getResult().isEmpty()){
                        for (DocumentSnapshot document : task.getResult()){

                                customerModel.setP_uid(document.getString("o_uid"));
                                customerModel.setP_photo("");
                                customerModel.setP_name(document.getString("o_customer_name"));
                                customerModel.setP_address(document.getString("o_customer_address"));
                                customerModel.setP_lat(document.getString("o_lat"));
                                customerModel.setP_long(document.getString("o_long"));
                                customerModel.setP_unit(document.getString("o_unit"));
                                customerModel.setP_category(document.getString("o_p_category"));
                                customerModel.setP_total_amount(document.getString("o_total"));
                                customerModel.setP_qty(document.getString("o_quantity"));
                                customerModel.setP_delivered(document.getString("p_received_qty"));
                                customerModel.setP_status(document.getString("p_delivery_status"));
                                customerModel.setO_customer_uid(document.getString("o_customer_uid"));

                            customerModelList.add(customerModel);

                            }

                            customerAdapter = new CustomerAdapter(getContext(), customerModelList);
                            customerAdapter.notifyDataSetChanged();
                            recyclerCustomer.setAdapter(customerAdapter);
                    } else {
                        Toast.makeText(getContext(), "No products to deliver.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Database Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
