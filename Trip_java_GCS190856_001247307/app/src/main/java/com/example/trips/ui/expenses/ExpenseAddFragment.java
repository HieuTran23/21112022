package com.example.trips.ui.expenses;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trips.R;
import com.example.trips.models.Expense;
import com.example.trips.models.Trip;
import com.example.trips.ui.dialog.CalendarFragment;
import com.example.trips.ui.dialog.TimePickerFragment;

public class ExpenseAddFragment extends DialogFragment implements CalendarFragment.Listener, TimePickerFragment.Listener{
    private ImageView imageViewBack;
    private EditText editTextExpenseAmount, editTextExpenseComment, editTextDateOfExpense, editTextTimeOfExpense;
    private AutoCompleteTextView autoCompleteTextViewExpenseType;
    private ArrayAdapter<String> arrayAdapterExpenseType;
    private String[] expenseTypes;
    private Button buttonCreateExpense;
    private TextView textViewExpenseErrorMessage;
    private Expense expense;

    public ExpenseAddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense_add, container, false);

        imageViewBack = view.findViewById(R.id.imageViewBack);
        editTextExpenseAmount = view.findViewById(R.id.editTextExpenseAmount);
        editTextExpenseComment = view.findViewById(R.id.editTextExpenseComment);
        editTextDateOfExpense = view.findViewById(R.id.editTextDateOfExpense);
        editTextTimeOfExpense = view.findViewById(R.id.editTextTimeOfExpense);
        autoCompleteTextViewExpenseType = view.findViewById(R.id.autoCompleteTextViewExpenseType);
        buttonCreateExpense = view.findViewById(R.id.buttonCreateExpense);
        expenseTypes = getResources().getStringArray(R.array.expensive_type);
        textViewExpenseErrorMessage = view.findViewById(R.id.textViewExpenseErrorMessage);

        arrayAdapterExpenseType = new ArrayAdapter(requireContext(), R.layout.dropdown_expensive_type_item, expenseTypes);

        autoCompleteTextViewExpenseType.setAdapter(arrayAdapterExpenseType);


        editTextDateOfExpense.setOnTouchListener((v, motion) -> openCalendar(motion));

        editTextTimeOfExpense.setOnTouchListener((v, motion) -> openTime(motion));

        imageViewBack.setOnClickListener(v -> closeFragment());

        buttonCreateExpense.setOnClickListener(v -> createExpenses());

        return view;
    }

    private boolean openTime (MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            new TimePickerFragment().show(getChildFragmentManager(), null);
        }

        return false;
    }

    private boolean openCalendar(MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            new CalendarFragment().show(getChildFragmentManager(), null);
        }

        return false;
    }

    private void closeFragment(){
        dismiss();
    }

    private void createExpenses() {
        if(validateForm()){
            expense = new Expense();

            expense.setType(autoCompleteTextViewExpenseType.getText().toString());
            expense.setAmount(Integer.parseInt(editTextExpenseAmount.getText().toString()));
            expense.setDate(editTextDateOfExpense.getText().toString());
            expense.setTime(editTextTimeOfExpense.getText().toString());
            expense.setComment(editTextExpenseComment.getText().toString());

            Listener listener = (Listener) getParentFragment();
            listener.getExpenseForm(expense);

            dismiss();
        }
    }

    private boolean validateForm(){
        String error = "";
        boolean checkResult = true;

        String type = autoCompleteTextViewExpenseType.getText().toString();
        String amount = editTextExpenseAmount.getText().toString();
        String date = editTextDateOfExpense.getText().toString();
        String time = editTextTimeOfExpense.getText().toString();
        String comment = editTextExpenseComment.getText().toString();

        if(type == null || type.trim().isEmpty()) {
            error += "* Missing expense type \n";
            checkResult = false;
        }

        if(amount == null || amount.trim().isEmpty()) {
            error += "* Missing amount \n";
            checkResult = false;
        }

        if(date == null || date.trim().isEmpty()){
            error += "* Missing trip destination \n";
            checkResult = false;
        }

        if (time == null || time.trim().isEmpty()){
            error += "* Missing trip date \n";
            checkResult = false;
        }

        textViewExpenseErrorMessage.setText(error);

        return checkResult;
    }

    @Override
    public void setDate(String date) {
        editTextDateOfExpense.setText(date);
    }

    @Override
    public void sendFromTimePickerFragment(String time) {
        editTextTimeOfExpense.setText(time);
    }

    public interface Listener {
        void getExpenseForm(Expense expense);
    }
}