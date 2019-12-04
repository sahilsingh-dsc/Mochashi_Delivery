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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tetraval.mochashidelivery.R;
import com.tetraval.mochashidelivery.UserMapsActivity;
import com.tetraval.mochashidelivery.model.CustomerModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    Context context;
    List<CustomerModel> customerModelList;
    double current_credit;

    public CustomerAdapter(Context context, List<CustomerModel> customerModelList) {
        this.context = context;
        this.customerModelList = customerModelList;
    }

    @NonNull
    @Override
    public CustomerAdapter.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_item, parent, false);



        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomerAdapter.CustomerViewHolder holder, int position) {
        final CustomerModel customerModel = customerModelList.get(position);
        holder.tlAR.setPrefixText("₹ ");
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("customer_profiles").document(customerModel.getO_customer_uid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){

                    current_credit = Double.parseDouble(task.getResult().getString("p_credits"));

                }else {
                    Toast.makeText(context, "Database Eror", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.imgCustomerMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("c_name", customerModel.getP_name());
                bundle.putString("c_address", customerModel.getP_address());
                bundle.putString("c_lat", customerModel.getP_lat());
                bundle.putString("c_long", customerModel.getP_long());
                Intent intent = new Intent(context, UserMapsActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.txtCustomerName.setText(customerModel.getP_name());
        holder.txtCustomerAddress.setText(customerModel.getP_address());
        holder.txtProductName.setText(customerModel.getP_category());
        holder.txtReceivableQty.setText(customerModel.getP_qty() +" "+customerModel.getP_unit());
        holder.txtAmountToPay.setText("₹"+customerModel.getP_total_amount());
        if (customerModel.getP_status().equals("0")){
            holder.btnCloseDeal.setVisibility(View.VISIBLE);
            holder.tiDeliveredQty.setVisibility(View.VISIBLE);
            holder.tlRQ.setVisibility(View.VISIBLE);
            holder.lvRec.setVisibility(View.GONE);
            holder.btnCloseDeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String qty = holder.tiDeliveredQty.getText().toString();
                    String r_amt = holder.tiAmountRec.getText().toString();
                    double rec = Double.parseDouble(qty);
                    double amt = Double.parseDouble(r_amt);
                    if (qty.isEmpty()){
                        holder.tiDeliveredQty.setError("Please enter received quantity");
                        return;
                    }
                    if (rec == 0.0){
                        holder.tiDeliveredQty.setError("Received quantity cannot be 0");
                        return;
                    }
                    if (r_amt.isEmpty()){
                        holder.tiAmountRec.setError("Please enter received amount");
                        return;
                    }
                    if (amt == 0.0){
                        holder.tiAmountRec.setError("Received amount cannot be 0");
                        return;
                    }

                    double r_able = Double.parseDouble(customerModel.getP_total_amount());
                    double m_credit = amt - r_able;
                    m_credit = m_credit + current_credit;
                    String p_credits = String.valueOf(m_credit);


                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    Map map = new HashMap();
                    map.put("p_credits", p_credits);
                    CollectionReference handqQtyCol = db.collection("customer_profiles");
                    handqQtyCol.document(customerModel.getO_customer_uid()).update(map);


                    Map map1 = new HashMap();
                    map1.put("amount_receivable", ""+customerModel.getP_total_amount());
                    map1.put("amount_received", ""+amt);
                    map1.put("order_uid", customerModel.getP_uid());
                    CollectionReference rec_amtRef = db.collection("customer_delivery_received");
                    rec_amtRef.document(customerModel.getO_customer_uid()).set(map1);


                    Map map2 = new HashMap();
                    map2.put("p_delivery_status", "1");
                    map2.put("p_received_qty", qty);

                    db.collection("chashi_orders")
                            .document(customerModel.getP_uid())
                            .update(map2);
                    Toast.makeText(context, "Deal Closed!", Toast.LENGTH_SHORT).show();
                    holder.btnCloseDeal.setVisibility(View.GONE);
                    holder.tiDeliveredQty.setVisibility(View.GONE);
                    holder.tlRQ.setVisibility(View.GONE);
                    holder.tlAR.setVisibility(View.GONE);
                    holder.lvRec.setVisibility(View.VISIBLE);
                    holder.txtGivenQty.setText(rec+" "+customerModel.getP_unit());
                }
            });
        } else {
            holder.btnCloseDeal.setVisibility(View.GONE);
            holder.tiDeliveredQty.setVisibility(View.GONE);
            holder.tlRQ.setVisibility(View.GONE);
            holder.tlAR.setVisibility(View.GONE);
            holder.lvRec.setVisibility(View.VISIBLE);
        }
        holder.txtGivenQty.setText(customerModel.getP_delivered()+" "+customerModel.getP_unit());
        holder.tlRQ.setSuffixText(customerModel.getP_unit());

    }

    @Override
    public int getItemCount() {
        return customerModelList.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCustomerMap;
        TextView txtCustomerName, txtCustomerAddress, txtProductName, txtReceivableQty, txtAmountToPay, txtGivenQty;
        TextInputLayout tlRQ, tlAR;
        TextInputEditText tiDeliveredQty, tiAmountRec;
        MaterialButton btnCloseDeal;
        LinearLayout lvRec;


        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCustomerMap = itemView.findViewById(R.id.imgCustomerMap);
            txtCustomerName = itemView.findViewById(R.id.txtCustomerName);
            txtCustomerAddress = itemView.findViewById(R.id.txtCustomerAddress);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtReceivableQty = itemView.findViewById(R.id.txtReceivableQty);
            txtAmountToPay = itemView.findViewById(R.id.txtAmountToPay);
            tlRQ = itemView.findViewById(R.id.tlRQ);
            txtGivenQty = itemView.findViewById(R.id.txtGivenQty);
            btnCloseDeal = itemView.findViewById(R.id.btnCloseDeal);
            lvRec = itemView.findViewById(R.id.lvRec);
            tiDeliveredQty = itemView.findViewById(R.id.tiDeliveredQty);
            tlAR = itemView.findViewById(R.id.tlAR);
            tiAmountRec = itemView.findViewById(R.id.tiAmountRec);

        }
    }
}
