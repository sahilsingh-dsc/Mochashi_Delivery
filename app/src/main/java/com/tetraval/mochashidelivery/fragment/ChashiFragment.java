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
import com.tetraval.mochashidelivery.R;
import com.tetraval.mochashidelivery.adapter.ChashiAdapter;
import com.tetraval.mochashidelivery.model.ChashiModel;

import java.util.ArrayList;
import java.util.List;

public class ChashiFragment extends Fragment {

    RecyclerView recyclerChashi;
    List<ChashiModel> chashiModelList;
    ChashiAdapter chashiAdapter;
    FirebaseFirestore db;
    TextView txtMap;
    double total_amount, local_total = 0;

    public ChashiFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chashi, container, false);

        db = FirebaseFirestore.getInstance();

        recyclerChashi = view.findViewById(R.id.recyclerChashi);
        txtMap = view.findViewById(R.id.txtMap);
        txtMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChashiMapsActivity.class));
            }
        });
        recyclerChashi.setLayoutManager(new LinearLayoutManager(getContext()));

        chashiModelList = new ArrayList<>();
        chashiModelList.clear();

        fetchChashi();

        return view;
    }

    private void fetchChashi(){
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

                                chashiModel.setC_uid(document.getString("p_uid"));
                                chashiModel.setC_photo(document.getString("p_chashi_photo"));
                                chashiModel.setC_name(document.getString("p_chashi_name"));
                                chashiModel.setC_address(document.getString("p_chashi_address"));
                                chashiModel.setC_lat(document.getString("p_lat"));
                                chashiModel.setC_long(document.getString("p_long"));
                                chashiModel.setC_unit(document.getString("p_unit"));
                                chashiModel.setC_category(document.getString("p_category"));
                                chashiModel.setC_total_qty(document.getString("p_bquantity"));
                                chashiModel.setC_status(document.getString("p_delivery_status"));
                                chashiModel.setC_total_amount(""+b_qty*total_amount);
                                chashiModel.setC_received(document.getString("p_received_qty"));

                                chashiModelList.add(chashiModel);
                                chashiAdapter = new ChashiAdapter(getContext(), chashiModelList);
                                chashiAdapter.notifyDataSetChanged();
                                recyclerChashi.setAdapter(chashiAdapter);

                            }


                        }
                    }
                }else {
                    Toast.makeText(getContext(), "Database Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}