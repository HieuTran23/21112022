package com.example.trips.ui.expenses;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.trips.R;
import com.example.trips.database.DAO;
import com.example.trips.models.Expense;

import java.util.ArrayList;

public class ExpenseListFragment extends Fragment {
    public static final String TRIP_ID = "trip_id";

    private ArrayList<Expense> expenseArrayList = new ArrayList<>();

    private DAO _db;
    private RecyclerView recyclerViewExpenseList;

    public ExpenseListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        _db = new DAO(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);

        if(getArguments() != null) {
            Expense expense = new Expense();
            expense.setTripId(getArguments().getLong(TRIP_ID));

            expenseArrayList = _db.getExpenseList(expense, null, false);
        }

        Log.d("LIST", expenseArrayList.toString());

        recyclerViewExpenseList = view.findViewById(R.id.recyclerViewExpenseList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerViewExpenseList.setAdapter(new ExpenseAdapter(getContext() ,expenseArrayList));
        recyclerViewExpenseList.setLayoutManager(linearLayoutManager);

        return view;
    }
}