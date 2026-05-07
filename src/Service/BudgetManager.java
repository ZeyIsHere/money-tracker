package Service;

import Model.Expenses;

import java.util.ArrayList;

public class BudgetManager {
    private int  budget;
    private ArrayList<Expenses> expenses;

    public BudgetManager(int budget){
        this.budget = budget;
        expenses = new ArrayList<>();
    }

    public void addExpenses(Expenses expense){
        expenses.add(expense);
    }
    public int getTotalExpense(){

        int total = 0;

        for (Expenses expense : expenses){
            total += expense.getAmount();
        }

        return total;
    }
    public int getRemainingMoney(){
        return budget - getTotalExpense();
    }

    public void showExpense(){
        for (Expenses expense : expenses) {
            System.out.println(expense.getName()
                    + " - " +
                    expense.getAmount());
        }
    }
}
