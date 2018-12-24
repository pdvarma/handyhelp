package com.handyhelp.handlyhelp.users;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.handyhelp.handlyhelp.R;
import com.handyhelp.handlyhelp.constants.RestConstants;
import com.handyhelp.handlyhelp.pojo.Booked;

import java.util.List;

/**
 * Created by tanmay on 06/08/18.
 */

public class BookedListingAdapter extends RecyclerView.Adapter<BookedListingAdapter.MyViewHolder> implements RestConstants {

    public List<Booked> helperList;
    public Context context;

    public BookedListingAdapter(List<Booked> helperList, Context context) {
        this.helperList = helperList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booked_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Booked helper= helperList.get(holder.getAdapterPosition());
        holder.helperName.setText("Helper Name : "+String.valueOf(helper.getFull_name_helper()));
        holder.helperContact.setText("Contact : "+String.valueOf(helper.getContact_helper()));
        holder.helperServices.setText("Service Name : " + String.valueOf(helper.getService_name()));
        holder.helperPaymentStatus.setText("Payment Successful");
        holder.helperDate.setText("Date : "+String.valueOf(helper.getDate()));
    }

    @Override
    public int getItemCount() {
        Log.d("LIST SIZE", String.valueOf(helperList.size()));
        return helperList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView helperName,helperContact,helperRating,helperServices,helperPaymentStatus,helperDate;
        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.helperName = (TextView)view.findViewById(R.id.textView_helper_name_booked);
            this.helperContact = (TextView)view.findViewById(R.id.textView_helper_contact_booked);
            this.helperServices = (TextView)view.findViewById(R.id.textView_service_name_booked);
            this.helperPaymentStatus = (TextView)view.findViewById(R.id.textView_payment_booked);
            this.helperDate = (TextView)view.findViewById(R.id.textView_date_booked);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
