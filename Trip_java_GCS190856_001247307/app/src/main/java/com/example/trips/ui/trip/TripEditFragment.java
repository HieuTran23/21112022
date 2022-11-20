package com.example.trips.ui.trip;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.trips.R;
import com.example.trips.database.DAO;
import com.example.trips.models.Trip;
import com.example.trips.ui.dialog.CalendarFragment;

public class TripEditFragment extends Fragment implements CalendarFragment.Listener {
    public static final String EDIT_TRIP = "editTrip";
    private EditText editTextTripName;
    private EditText editTextTripDestination;
    private EditText editTextDateOfTrip;
    private EditText editTextTripDescription;
    private Switch switchRiskAssessment;
    private Button buttonUpdate;
    private TextView errorMessage;
    private TextView textViewTripId;
    private DAO _db;
    private Trip trip;
    private String[] tripTypeItems, tripPeopleTypeItems;
    private AutoCompleteTextView autoCompleteTextViewTripType, autoCompleteTextViewTripPeopleType;
    private ArrayAdapter<String> arrayAdapterTripTypeItems, arrayAdapterTripPeopleTypeItems;


    public TripEditFragment() {}


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        _db = new DAO(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_edit, container, false);

        editTextTripName = view.findViewById(R.id.editTextTripSearchName);
        editTextTripDestination = view.findViewById(R.id.editTextTripDestination);
        editTextDateOfTrip = view.findViewById(R.id.editTextDateOfTripSearch);
        editTextTripDescription = view.findViewById(R.id.editTextTripSearchDestination);
        switchRiskAssessment = view.findViewById(R.id.switchRiskAssessment);
        buttonUpdate = view.findViewById(R.id.searchBtn);
        errorMessage = view.findViewById(R.id.textViewErrorMessage);
        textViewTripId = view.findViewById(R.id.textViewTripId);
        autoCompleteTextViewTripType = view.findViewById(R.id.autoCompleteTextViewTripType);
        autoCompleteTextViewTripPeopleType = view.findViewById(R.id.autoCompleteTextViewExpenseType);
        tripTypeItems = getResources().getStringArray(R.array.trip_type);
        tripPeopleTypeItems = getResources().getStringArray(R.array.trip_people_type);


        arrayAdapterTripTypeItems = new ArrayAdapter(requireContext(), R.layout.dropdown_trip_type_item, tripTypeItems);
        arrayAdapterTripPeopleTypeItems = new ArrayAdapter(requireContext(), R.layout.dropdown_trip_people_type, tripPeopleTypeItems);

        autoCompleteTextViewTripType.setAdapter(arrayAdapterTripTypeItems);
        autoCompleteTextViewTripPeopleType.setAdapter(arrayAdapterTripPeopleTypeItems);

        autoCompleteTextViewTripType.setOnItemClickListener((adapterView, v, position, id) -> handleTripTypeItemOnClick(adapterView, position));
        autoCompleteTextViewTripPeopleType.setOnItemClickListener(((adapterView, v, position, id) -> handleTripPeopleTypeItemOnClick(adapterView, position)));


        showEdit();

        editTextDateOfTrip.setOnTouchListener((v, motion) -> openCalendar(motion));
        buttonUpdate.setOnClickListener(v -> updateTrip());

        return view;
    }

    private void showEdit(){
        if(getArguments() != null){
            trip = (Trip) getArguments().getSerializable(EDIT_TRIP);

            textViewTripId.setText("# " + String.valueOf(trip.getId()));
            editTextTripName.setText(trip.getName());
            editTextDateOfTrip.setText(trip.getDateOfTrip());
            editTextTripDestination.setText(trip.getDestination());
            editTextTripDescription.setText(checkDescription(trip.getDescription()));
            switchRiskAssessment.setChecked(checkRiskAssessment(trip.getRiskAssessment()));
        }
    }

    private  void handleTripPeopleTypeItemOnClick(AdapterView<?> adapterView, int position) {
        String item = adapterView.getItemAtPosition(position).toString();
    }

    private void handleTripTypeItemOnClick(AdapterView<?> adapterView, int position) {
        String item = adapterView.getItemAtPosition(position).toString();
    }

    private String checkDescription(String description){
        if(description == null || description.isEmpty()) return "";
        return description;
    }

    private boolean checkRiskAssessment(int riskAssessment){
        if(riskAssessment != 0) return true;
        return false;
    }

    private void updateTrip() {
        if(validateForm()) {
            Trip trip = createFormInput();
            Bundle bundle = new Bundle();

            bundle.putSerializable(AddTripConfirmFragment.ADD_TRIP_CONFIRM, trip);

            Navigation.findNavController(getView()).navigate(R.id.addTripConfirmFragment, bundle);
            return;
        }
    }

    private Trip createFormInput() {
        String name = editTextTripName.getText().toString();
        String destination = editTextTripDestination.getText().toString();
        String dateOfTrip = editTextDateOfTrip.getText().toString();
        String description = editTextTripDescription.getText().toString();
        String type = autoCompleteTextViewTripType.getText().toString();
        String peopleNumberType = autoCompleteTextViewTripPeopleType.getText().toString();

        int riskAssessment = 0;

        if(switchRiskAssessment.isChecked()){
            riskAssessment = 1;
        }

        return new Trip(trip.getId(), name, destination, dateOfTrip, description, riskAssessment, type, peopleNumberType);
    }

    private boolean validateForm(){
        String error = "";
        boolean checkResult = true;

        String name = editTextTripName.getText().toString();
        String destination = editTextTripDestination.getText().toString();
        String dateOfTrip = editTextDateOfTrip.getText().toString();

        if(name == null || name.trim().isEmpty()) {
            error += "* Missing trip name \n";
            checkResult = false;
        }

        if(destination == null || destination.trim().isEmpty()){
            error += "* Missing trip destination \n";
            checkResult = false;
        }

        if (dateOfTrip == null || dateOfTrip.trim().isEmpty()){
            error += "* Missing trip date \n";
            checkResult = false;
        }

        errorMessage.setText(error);

        return checkResult;
    }

    private boolean openCalendar(MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            new CalendarFragment().show(getChildFragmentManager(), null);
        }

        return false;
    }

    @Override
    public void setDate(String date) {
        editTextDateOfTrip.setText(date);
    }
}