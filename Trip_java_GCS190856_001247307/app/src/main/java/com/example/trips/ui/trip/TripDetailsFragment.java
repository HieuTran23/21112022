package com.example.trips.ui.trip;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trips.ui.dialog.DeleteConfirmFragment;
import com.example.trips.R;
import com.example.trips.database.DAO;
import com.example.trips.models.Expense;
import com.example.trips.models.Trip;
import com.example.trips.ui.expenses.ExpenseAddFragment;
import com.example.trips.ui.expenses.ExpenseListFragment;

public class TripDetailsFragment extends Fragment implements ExpenseAddFragment.Listener, DeleteConfirmFragment.FragmentListener {
    public static final String TRIP_DETAIL = "tripDetail";
    private TextView textViewTripName_Detail, textViewTripDestination_Detail, textViewDateOfTrip_Detail, textViewTripDescription_Detail, textViewRiskAssessment_Detail, textViewTripType_Detail, textViewPeopleNumber_Detail;
    private ImageView imageViewMenuBtn;
    private Trip trip;
    private DAO _db;

    public TripDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        _db = new DAO(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_details, container, false);

        textViewTripName_Detail = view.findViewById(R.id.textViewTripName_Detail);
        textViewTripDestination_Detail = view.findViewById(R.id.textViewTripDestination_Detail);
        textViewDateOfTrip_Detail = view.findViewById(R.id.textViewDateOfTrip_Detail);
        textViewTripDescription_Detail = view.findViewById(R.id.textViewTripDescription_Detail);
        textViewRiskAssessment_Detail = view.findViewById(R.id.textViewRiskAssessment_Detail);
        textViewTripType_Detail = view.findViewById(R.id.textViewTripType_Detail);
        textViewPeopleNumber_Detail = view.findViewById(R.id.textViewPeopleNumber_Detail);
        imageViewMenuBtn = view.findViewById(R.id.imageViewMenuBtn);

        imageViewMenuBtn.setOnClickListener(v -> openMenu(v));

        showDetail();
        showExpenseList();

        return view;
    }

    private void openMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), imageViewMenuBtn);

        popupMenu.getMenuInflater().inflate(R.menu.trip_detail_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(menuItem -> handleMenuItemOnClick(menuItem));

        popupMenu.show();
    }

    private boolean handleMenuItemOnClick(MenuItem menuItem){
        switch (menuItem.getItemId()) {
            case R.id.menuItemEditTrip:
                Bundle bundle = new Bundle();

                bundle.putSerializable(TripEditFragment.EDIT_TRIP, trip);

                Navigation.findNavController(getView()).navigate(R.id.trip_edit, bundle);
                return true;

            case R.id.menuItemDeleteTrip:
                showDeleteConfirmFragment();
                return true;

            case R.id.menuItemAddExpensesTrip:
                new ExpenseAddFragment().show(getChildFragmentManager(), null);
                return true;
        }

        return true;
    }

    private void showDetail(){
        if(getArguments() != null) {
            trip = (Trip) getArguments().getSerializable(TRIP_DETAIL);
            trip = _db.getTrip(trip.getId());

            textViewTripName_Detail.setText(trip.getName());
            textViewTripDestination_Detail.setText(trip.getDestination());
            textViewDateOfTrip_Detail.setText(trip.getDateOfTrip());
            textViewTripDescription_Detail.setText(checkDescription(trip.getDescription()));
            textViewRiskAssessment_Detail.setBackgroundResource(checkRiskAssessment(trip.getRiskAssessment()));
            textViewTripType_Detail.setText(trip.getType());
            textViewPeopleNumber_Detail.setText(trip.getPeopleNumber());
        }
    }

    private String checkDescription(String description){
        if(description == null || description.isEmpty()) return "";
        return description;
    }

    private int checkRiskAssessment(int riskAssessment){
        if(riskAssessment != 0) return R.drawable.ic_baseline_check_circle_24;
        return R.drawable.ic_baseline_check_circle_no_active;
    }

    private void showExpenseList() {
        if(getArguments() != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ExpenseListFragment.TRIP_ID, trip.getId());

            getChildFragmentManager().getFragments().get(0).setArguments(bundle);
        }
    }

    private void reloadExpenseList() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ExpenseListFragment.TRIP_ID, trip.getId());

        getChildFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainerViewExpenseList, ExpenseListFragment.class, bundle).commit();
    }

    @Override
    public void getExpenseForm(Expense expense) {
        if(expense != null) {
            expense.setTripId(trip.getId());

            long status = _db.insertExpense(expense);
            Toast.makeText(getContext(), status == -1 ? "Fail" : "Success", Toast.LENGTH_SHORT).show();

            reloadExpenseList();
        }
    }

    protected void showDeleteConfirmFragment() {
        new DeleteConfirmFragment("Delete confirm").show(getChildFragmentManager(), null);
    }

    @Override
    public void sendFromDeleteConfirmFragment(int status) {
        if (status == 1 && trip != null) {
            long result = _db.deleteTrip(trip.getId());

            if (result > 0) {
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigateUp();

                return;
            }
        }

        Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
    }
}