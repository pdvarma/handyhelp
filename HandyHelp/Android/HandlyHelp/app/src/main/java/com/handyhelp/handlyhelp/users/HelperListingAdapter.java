package com.handyhelp.handlyhelp.users;

/**
 * Created by tanmay on 05/08/18.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.handyhelp.handlyhelp.R;
import com.handyhelp.handlyhelp.constants.RestConstants;
import com.handyhelp.handlyhelp.pojo.Helper;

import java.io.Serializable;
import java.util.List;

public class HelperListingAdapter extends RecyclerView.Adapter<HelperListingAdapter.MyViewHolder> implements RestConstants {

    public List<Helper> helperList;
    public Context context;

    public HelperListingAdapter(List<Helper> helperList, Context context) {
        this.helperList = helperList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.helper_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Helper helper= helperList.get(holder.getAdapterPosition());
        holder.helperName.setText("Helper Name : " + String.valueOf(helper.getFull_name()));
        holder.helperEmail.setText("Helper Email : " + String.valueOf(helper.getEmail()));
        holder.helperRating.setText("Rating : " + helper.getRating());
        holder.helperServices.setText("Service : " + helper.getService_name());

        holder.button_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,BookingDetails.class);
                intent.putExtra("name",helperList.get(position).getFull_name());
                intent.putExtra("email",helperList.get(position).getEmail());
                intent.putExtra("id",helperList.get(position).getHelper_id());
                intent.putExtra("contact",helperList.get(position).getContact());
                intent.putExtra("service_name",helperList.get(position).getService_name());
                intent.putExtra("service_id",helperList.get(position).getServices());
                intent.putExtra("rating",helperList.get(position).getRating());

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("my object", helperList.get(position));
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("LIST SIZE", String.valueOf(helperList.size()));
        return helperList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView helperName,helperEmail,helperRating,helperServices;
        Button button_book;
        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.helperName = (TextView)view.findViewById(R.id.textView_helper_name_listing);
            this.helperEmail = (TextView)view.findViewById(R.id.textView_helper_email_listing);
            this.helperRating = (TextView)view.findViewById(R.id.textView_helper_rating_listing);
            this.helperServices = (TextView)view.findViewById(R.id.textView_helper_services_listing);
            this.button_book = (Button) view.findViewById(R.id.button_book);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
