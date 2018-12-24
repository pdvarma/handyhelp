package com.handyhelp.handlyhelp.users;

/**
 * Created by tanmay on 05/08/18.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.handyhelp.handlyhelp.R;
import com.handyhelp.handlyhelp.constants.RestConstants;
import com.handyhelp.handlyhelp.pojo.Service;

import java.util.List;

public class ServiceListingAdapter extends RecyclerView.Adapter<ServiceListingAdapter.MyViewHolder> implements RestConstants {

    public List<Service> serviceList;
    public Context context;

    public ServiceListingAdapter(List<Service> serviceList, Context context) {
        this.serviceList = serviceList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Service helper= serviceList.get(holder.getAdapterPosition());
        holder.serviceName.setText(String.valueOf(helper.getService_name()));

        holder.serviceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, helper.getService_id(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,HelperListing.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("SERVICE_ID", helper.getService_id());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("LIST SIZE", String.valueOf(serviceList.size()));
        return serviceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView serviceName;
        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.serviceName = (TextView)view.findViewById(R.id.textView_service_name_listing);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
