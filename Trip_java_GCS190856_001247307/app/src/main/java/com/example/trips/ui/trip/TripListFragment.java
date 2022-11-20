package com.example.trips.ui.trip;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.trips.R;
import com.example.trips.database.DAO;
import com.example.trips.models.Trip;

import java.util.ArrayList;

public class TripListFragment extends Fragment implements TripSearchFragment.Listener{
    private ArrayList<Trip> tripList = new ArrayList<>();
    private DAO _db;
    private RecyclerView recyclerViewTripList;
    private TripAdapter tripAdapter;
    private EditText editTextTripListSearch;
    private ImageButton imageButtonTripListSearch, imageButtonTripListFilter;

    public TripListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        _db = new DAO(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_list, container, false);

        recyclerViewTripList = view.findViewById(R.id.recyclerViewExpenseList);
        editTextTripListSearch = view.findViewById(R.id.editTextTripListSearch);
        imageButtonTripListSearch = view.findViewById(R.id.imageButtonTripListSearch);
        imageButtonTripListFilter = view.findViewById(R.id.imageButtonTripListFilter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        tripAdapter = new TripAdapter(tripList);

        recyclerViewTripList.setAdapter(tripAdapter);
        recyclerViewTripList.setLayoutManager(new LinearLayoutManager(getContext()));

        editTextTripListSearch.addTextChangedListener(filter());

        imageButtonTripListFilter.setOnClickListener(v -> openAdvanceSearch());

        return view;
    }

    private void openAdvanceSearch() {
        new TripSearchFragment().show(getChildFragmentManager(), null);
    }

    @Override
    public void onResume() {
        super.onResume();

        reloadList(null);
    }

    private TextWatcher filter() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                tripAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    private void reloadList(Trip trip) {
        tripList = _db.getTripList(trip, null, false);

        tripAdapter.updateList(tripList);

        if(!tripList.isEmpty()) Log.d("List", tripList.toString());
        // Show "No Resident." message.
        if(tripList.isEmpty()) Toast.makeText(getActivity(), "List is empty", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendFromResidentSearchFragment(Trip trip) {
        if(trip.isEmpty()) {
            reloadList(null);
        }

        reloadList(trip);
        return;
    }
}