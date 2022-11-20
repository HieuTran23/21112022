package com.example.trips.ui.dialog;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.trips.R;

public class CalendarFragment extends DialogFragment {
    private CalendarView calendar;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendar = view.findViewById(R.id.calendar);

        calendar.setOnDateChangeListener((calendarView, year, month, day) -> {
            String date = "";

            ++month;

            date += day < 10 ? "0" + day : day;
            date += "/";
            date += month < 10 ? "0" + month : month;
            date += "/";
            date += year;

            Listener listener = (Listener) getParentFragment();
            if (listener != null) {
                listener.setDate(date);
            }

            dismiss();
        });

        return view;
    }

    public interface Listener{
        void setDate(String date);
    }
}