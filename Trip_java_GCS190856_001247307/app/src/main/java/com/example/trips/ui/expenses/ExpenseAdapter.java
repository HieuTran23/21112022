package com.example.trips.ui.expenses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trips.R;
import com.example.trips.database.DAO;
import com.example.trips.models.Expense;
import com.example.trips.ui.dialog.CalendarFragment;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    private ArrayList<Expense> expensesList;
    private DAO _db;

    public ExpenseAdapter(ArrayList<Expense> list) {
        expensesList = list;
    }

    public ExpenseAdapter(Context context, ArrayList<Expense> list) {
        _db = new DAO(context);
        expensesList = list;
    }

    @SuppressLint("NotifyDataSetChanged")
    public  void updateList(ArrayList<Expense> list) {
        expensesList = list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_expense, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = expensesList.get(position);

        holder.textViewExpenseType_listItem.setText(expense.getType());
        holder.textViewExpenseAmount_listItem.setText("$" + String.valueOf(expense.getAmount()));
        holder.textViewExpenseDate_listItem.setText(expense.getDate());
        holder.textViewExpenseTime_listItem.setText(expense.getTime());
        holder.textViewExpenseComment_listItem.setText(expense.getComment());

        holder.imageViewListItemExpenseMenuBtn.setOnClickListener(v -> openMenu(v, holder.imageViewListItemExpenseMenuBtn, expense));
    }

    private void openMenu(View view, ImageView imageViewExpenseListItemBtn, Expense expense) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), imageViewExpenseListItemBtn);

        popupMenu.getMenuInflater().inflate(R.menu.expense_list_item_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(menuItem -> handleMenuItemOnClick(menuItem, expense));

        popupMenu.show();
    }

    private boolean handleMenuItemOnClick(MenuItem menuItem, Expense expense){
        switch (menuItem.getItemId()) {
            case R.id.menuItemDeleteExpense:
                long status = _db.deleteExpense(expense.getId());

                return true;
        }

        return true;
    }

    @Override
    public int getItemCount() {
        return expensesList == null ? 0 : expensesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView textViewExpenseType_listItem, textViewExpenseAmount_listItem, textViewExpenseDate_listItem, textViewExpenseTime_listItem, textViewExpenseComment_listItem;
        private ImageView imageViewListItemExpenseMenuBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewExpenseType_listItem = itemView.findViewById(R.id.textViewExpenseType_listItem);
            textViewExpenseAmount_listItem = itemView.findViewById(R.id.textViewExpenseAmount_listItem);
            textViewExpenseDate_listItem = itemView.findViewById(R.id.textViewExpenseDate_listItem);
            textViewExpenseTime_listItem = itemView.findViewById(R.id.textViewExpenseTime_listItem);
            textViewExpenseComment_listItem = itemView.findViewById(R.id.textViewExpenseComment_listItem);
            imageViewListItemExpenseMenuBtn = itemView.findViewById(R.id.imageViewExpesnseListItemMenuBtn);
        }
    }
}
