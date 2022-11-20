package com.example.trips.ui.trip;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trips.R;
import com.example.trips.database.DAO;
import com.example.trips.models.Trip;

public class AddTripConfirmFragment extends Fragment {
    public static final String ADD_TRIP_CONFIRM = "addTripConfirm";
    private Trip trip;
    private TextView textViewTripName, textViewTripDestination, textViewDateOfTrip, textViewTripDescription, textViewTripType, textViewTripPeopleNumberType;
    private Switch switchRiskAssessment;
    private Button confirmButton;
    private DAO _db;

    public AddTripConfirmFragment() {
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
        View view = inflater.inflate(R.layout.fragment_trip_add_confirm, container, false);

        textViewTripName = view.findViewById(R.id.textViewTripName);
        textViewTripDestination = view.findViewById(R.id.textViewTripDestination);
        textViewDateOfTrip = view.findViewById(R.id.textViewDateOfTrip);
        textViewTripDescription = view.findViewById(R.id.textViewTripDescription);
        switchRiskAssessment = view.findViewById(R.id.switchRiskAssessment);
        textViewTripType = view.findViewById(R.id.textViewTripType);
        textViewTripPeopleNumberType = view.findViewById(R.id.textViewTripPeopleTypeNumber);
        confirmButton = view.findViewById(R.id.confirmButton);


        showDetail();

        if(trip.getId() != -1) {
            confirmButton.setOnClickListener(v -> updateTrip());

            return view;
        }

        confirmButton.setOnClickListener(v -> createTrip());
        switchRiskAssessment.setClickable(false);

        return view;
    }

    private void showDetail() {
        if(getArguments() != null) {
            trip = (Trip) getArguments().getSerializable(ADD_TRIP_CONFIRM);

            boolean riskAssessmentChecked = false;

            if(trip.getRiskAssessment() == 1) riskAssessmentChecked = true;

            textViewTripName.setText(trip.getName());
            textViewTripDestination.setText(trip.getDestination());
            textViewDateOfTrip.setText(trip.getDateOfTrip());
            textViewTripDescription.setText(trip.getDescription());
            switchRiskAssessment.setChecked(riskAssessmentChecked);
            textViewTripType.setText(trip.getType());
            textViewTripPeopleNumberType.setText(trip.getPeopleNumber());
        }
    }

    private void createTrip() {
        long result = _db.insertTrip(trip);


        Navigation.findNavController(getView()).navigate(R.id.trip_add);
        Toast.makeText(getActivity(), "Add success", Toast.LENGTH_SHORT).show();
        return;
    }

    private void updateTrip() {
        long result = _db.updateTrip(trip);

        Bundle bundle = new Bundle();

        bundle.putSerializable(TripDetailsFragment.TRIP_DETAIL, trip);

        Navigation.findNavController(getView()).popBackStack(R.id.trip_edit, true);
//        Navigation.findNavController(getView()).navigate(R.id.tripDetailsFragment, bundle);
        Toast.makeText(getActivity(), "Update success", Toast.LENGTH_SHORT).show();
        return;
    }
}