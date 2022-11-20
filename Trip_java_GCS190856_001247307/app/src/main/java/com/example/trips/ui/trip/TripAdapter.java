package com.example.trips.ui.trip;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trips.R;
import com.example.trips.models.Trip;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> implements Filterable {
    private ArrayList<Trip> tripList, filteredTripList;
    private TripAdapter.ItemFilter itemFilter = new TripAdapter.ItemFilter();

    public TripAdapter(ArrayList<Trip> list) {
        filteredTripList = list;
        tripList = list;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(ArrayList<Trip> list){
        filteredTripList = list;
        tripList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_trip, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip trip = filteredTripList.get(position);

        holder.textViewTripName_listItem.setText(trip.getName());
        holder.textViewTripDestination_listItem.setText(trip.getDestination());
        holder.textViewDateOfTrip_listItem.setText(trip.getDateOfTrip());
        if(checkRiskAssessment(trip.getRiskAssessment())) {
            holder.textViewRiskAssessment_listItem.setBackgroundResource(R.drawable.ic_baseline_check_circle_24);
        }
        holder.textViewTripType_listItem.setText(trip.getType());
        holder.textViewTripPeopleNumberType_listItem.setText(trip.getPeopleNumber());
    }

    @Override
    public int getItemCount() {
        return filteredTripList == null ? 0 : filteredTripList.size();
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout listTripItem;
        private TextView textViewTripName_listItem, textViewDateOfTrip_listItem, textViewTripDestination_listItem, textViewRiskAssessment_listItem, textViewTripType_listItem, textViewTripPeopleNumberType_listItem;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewTripName_listItem = itemView.findViewById(R.id.textViewExpenseType_listItem);
            textViewTripDestination_listItem = itemView.findViewById(R.id.textViewExpenseComment_listItem);
            textViewDateOfTrip_listItem = itemView.findViewById(R.id.textViewExpenseDate_listItem);
            textViewRiskAssessment_listItem = itemView.findViewById(R.id.textViewRiskAssessment_listItem);
            textViewTripType_listItem = itemView.findViewById(R.id.textViewTripType);
            textViewTripPeopleNumberType_listItem = itemView.findViewById(R.id.textViewExpenseAmount_listItem);

            listTripItem = itemView.findViewById(R.id.tripListItemBox);
            listTripItem.setOnClickListener(v -> tripDetail(v));
        }

        private void tripDetail(View view) {
            Trip trip = filteredTripList.get(getAdapterPosition());

            Bundle bundle = new Bundle();
            bundle.putSerializable(TripDetailsFragment.TRIP_DETAIL, trip);

            Navigation.findNavController(view).navigate(R.id.tripDetailsFragment, bundle);
        }
    }

    private boolean checkRiskAssessment(int riskAssessment){
        if(riskAssessment != 0) return true;
        return false;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final ArrayList<Trip> list = tripList;
            final ArrayList<Trip> newlist = new ArrayList<>(list.size());

            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();

            for (Trip resident : list) {
                String filterableString = resident.toString();
                if (filterableString.toLowerCase().contains(filterString)) newlist.add(resident);
            }

            results.values = newlist;
            results.count = newlist.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredTripList = (ArrayList<Trip>) results.values;
            notifyDataSetChanged();
        }
    }
}
