package com.tetraval.mochashidelivery.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tetraval.mochashidelivery.R;
import com.tetraval.mochashidelivery.UserMapsActivity;
import com.tetraval.mochashidelivery.model.ChashiModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChashiAdapter extends RecyclerView.Adapter<ChashiAdapter.ChashiViewHolder> {

    Context context;
    List<ChashiModel> chashiModelList;

    public ChashiAdapter(Context context, List<ChashiModel> chashiModelList) {
        this.context = context;
        this.chashiModelList = chashiModelList;
    }

    @NonNull
    @Override
    public ChashiAdapter.ChashiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chashi_list_item, parent, false);
        return new ChashiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChashiAdapter.ChashiViewHolder holder, int position) {
        final ChashiModel chashiModel = chashiModelList.get(position);
        holder.imgChashiMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("c_name", chashiModel.getC_name());
                bundle.putString("c_address", chashiModel.getC_address());
                bundle.putString("c_lat", chashiModel.getC_lat());
                bundle.putString("c_long", chashiModel.getC_long());
                Intent intent = new Intent(context, UserMapsActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.txtChashiName.setText(chashiModel.getC_name());
        holder.txtChashiAddress.setText(chashiModel.getC_address());
        holder.txtProductName.setText(chashiModel.getC_category());
        holder.txtReceivableQty.setText(chashiModel.getC_total_qty() +" "+chashiModel.getC_unit());
        holder.txtAmountToPay.setText("₹"+chashiModel.getC_total_amount());
        if (chashiModel.getC_status().equals("0")){
            holder.btnCloseDeal.setVisibility(View.VISIBLE);
            holder.tiReceivedQty.setVisibility(View.VISIBLE);
            holder.tlRQ.setVisibility(View.VISIBLE);
            holder.lvRec.setVisibility(View.GONE);
            holder.btnCloseDeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String qty = holder.tiReceivedQty.getText().toString();
                    double rec = Double.parseDouble(qty);
                    if (qty.isEmpty()){
                        holder.tiReceivedQty.setError("Please enter delivered quantity");
                        return;
                    }
                    if (rec == 0.0){
                        holder.tiReceivedQty.setError("Delivered quantity cannot bt 0");
                        return;
                    }
                    Map map = new HashMap();
                    map.put("p_delivery_status", "1");
                    map.put("p_received_qty", qty);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("chashi_products")
                            .document(chashiModel.getC_uid())
                            .update(map);
                    Toast.makeText(context, "Deal Closed!", Toast.LENGTH_SHORT).show();
                    holder.btnCloseDeal.setVisibility(View.GONE);
                    holder.tiReceivedQty.setVisibility(View.GONE);
                    holder.tlRQ.setVisibility(View.GONE);
                    holder.lvRec.setVisibility(View.VISIBLE);
                    holder.txtTakenQty.setText(rec+" "+chashiModel.getC_unit());
                }
            });
        } else {
            holder.btnCloseDeal.setVisibility(View.GONE);
            holder.tiReceivedQty.setVisibility(View.GONE);
            holder.tlRQ.setVisibility(View.GONE);
            holder.lvRec.setVisibility(View.VISIBLE);
        }
        holder.txtTakenQty.setText(chashiModel.getC_received()+" "+chashiModel.getC_unit());
        holder.tlRQ.setSuffixText(chashiModel.getC_unit());

    }

    @Override
    public int getItemCount() {
        return chashiModelList.size();
    }

    public class ChashiViewHolder extends RecyclerView.ViewHolder {

        ImageView imgChashiMap;
        TextView txtChashiName, txtChashiAddress, txtProductName, txtReceivableQty, txtAmountToPay, txtTakenQty;
        TextInputLayout tlRQ;
        TextInputEditText tiReceivedQty;
        MaterialButton btnCloseDeal;
        LinearLayout lvRec;


        public ChashiViewHolder(@NonNull View itemView) {
            super(itemView);

            imgChashiMap = itemView.findViewById(R.id.imgChashiMap);
            txtChashiName = itemView.findViewById(R.id.txtChashiName);
            txtChashiAddress = itemView.findViewById(R.id.txtChashiAddress);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtReceivableQty = itemView.findViewById(R.id.txtReceivableQty);
            txtAmountToPay = itemView.findViewById(R.id.txtAmountToPay);
            tlRQ = itemView.findViewById(R.id.tlRQ);
            tiReceivedQty = itemView.findViewById(R.id.tiReceivedQty);
            btnCloseDeal = itemView.findViewById(R.id.btnCloseDeal);
            lvRec = itemView.findViewById(R.id.lvRec);
            txtTakenQty = itemView.findViewById(R.id.txtTakenQty);

        }
    }
}
