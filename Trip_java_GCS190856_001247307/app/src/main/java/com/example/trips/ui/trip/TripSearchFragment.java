package com.example.trips.ui.trip;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.trips.R;
import com.example.trips.models.Trip;
import com.example.trips.ui.dialog.CalendarFragment;

public class TripSearchFragment extends DialogFragment implements CalendarFragment.Listener {
    private EditText editTextTripSearchName, editTextDateOfTripSearch, editTextTripSearchDestination;
    private Button searchBtn;
    private ImageButton imageButtonSearchBack;

    public TripSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_search, container, false);

        editTextTripSearchName = view.findViewById(R.id.editTextTripSearchName);
        editTextTripSearchDestination = view.findViewById(R.id.editTextTripSearchDestination);
        editTextDateOfTripSearch = view.findViewById(R.id.editTextDateOfTripSearch);
        searchBtn = view.findViewById(R.id.searchBtn);
        imageButtonSearchBack = view.findViewById(R.id.imageButtonSearchBack);

        editTextDateOfTripSearch.setOnTouchListener((v, motionEvent) -> openCalendar(motionEvent));

        imageButtonSearchBack.setOnClickListener(v -> dismiss());

        searchBtn.setOnClickListener(v -> search());

        return view;
    }

    private void search() {
        Trip trip = new Trip();

        String date = editTextDateOfTripSearch.getText().toString();
        String name = editTextTripSearchName.getText().toString();
        String destination = editTextTripSearchDestination.getText().toString();

        if (date != null && !date.trim().isEmpty())
            trip.setDateOfTrip(date);

        if (name != null && !name.trim().isEmpty())
            trip.setName(name);

        if(destination != null && !name.trim().isEmpty())
            trip.setDestination(destination);

        Listener listener = (Listener) getParentFragment();
        listener.sendFromResidentSearchFragment(trip);

        dismiss();
    }

    private boolean openCalendar(MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            new CalendarFragment().show(getChildFragmentManager(), null);
        }

        return false;
    }

    @Override
    public void setDate(String date) {
        editTextDateOfTripSearch.setText(date);
    }

    public interface Listener {
        void sendFromResidentSearchFragment(Trip trip);
    }
}
