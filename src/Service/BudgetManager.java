package Service;

import Model.Expenses;

import java.io.*;
import java.util.ArrayList;

public class BudgetManager {
    private int budget;
    private ArrayList<Expenses> expenses;
    public BudgetManager(int budget) {
        this.budget = budget;
        expenses = new ArrayList<>();
    }
    public int getBudget() {
        return budget;
    }
    public int getMonthlyBudget() {
        return budget * 4;
    }

        public int getExpenseCount(){
        return expenses.size();
        }

    public void saveBudget() throws IOException {
        BufferedWriter writer =
                new BufferedWriter(
                        new FileWriter("budget.txt")
                );
        writer.write(String.valueOf(budget));
        writer.close();
    }

    public void loadBudget() {
        try {
            BufferedReader reader =
                    new BufferedReader(
                            new FileReader("budget.txt")
                    );
            String line = reader.readLine();
            budget = Integer.parseInt(line);
            reader.close();
        } catch (IOException e) {
            System.out.println("No saved budget found.");
        }
    }

    public void saveExpenses() throws IOException {
        BufferedWriter writer =
                new BufferedWriter(
                        new FileWriter("expenses.txt")
                );
        for (Expenses expense : expenses) {
            writer.write(toString());
            writer.newLine();
        }
        writer.close();
    }

    public void loadExpenses() {
        expenses.clear();
        try {
            BufferedReader reader =
                    new BufferedReader(
                            new FileReader("expenses.txt")
                    );
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 4) {
                    System.out.println(
                            "Skipping corrupted line: "
                                    + line
                    );
                    continue;
                }
                try {
                    String category = parts[0];
                    String name = parts[1];
                    int amount =
                            Integer.parseInt(parts[2]);
                    String date = parts[3];
                    Expenses expense =
                            new Expenses(
                                    category,
                                    name,
                                    amount,
                                    date
                            );
                    expenses.add(expense);
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Skipping corrupted line: "
                                    + line
                    );
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(
                    "No previous expense data found."
            );
        }
    }

    public void addExpense(Expenses expense) {
        expenses.add(expense);
    }
    public int getTotalExpense() {
        int total = 0;
        for (Expenses expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }
    public int getRemainingMoney() {
        return budget - getTotalExpense();
    }

    public void showExpense() {
        ArrayList<String> printedCategories =
                new ArrayList<>();
        for (Expenses expense : expenses) {
            if (!printedCategories.contains(
                    expense.getCategory())) {
                int categoryTotal = 0;
                System.out.println(
                                "=== "
                                + expense.getCategory()
                                + " ==="
                );
                printedCategories.add(
                        expense.getCategory()
                );
                for (Expenses otherExpense : expenses) {
                    if (expense.getCategory()
                            .equals(
                                    otherExpense.getCategory()
                            )) {
                        categoryTotal += otherExpense.getAmount();
                        System.out.println("TOTAL EXPENSE: " + categoryTotal);
                        System.out.println(toString());
                    }
                }
                System.out.println();
            }
        }
    }

    public int getMonthlyExpense(String monthYear){
     int total = 0;
     for (Expenses expense : expenses){
         if (expense.getDate().contains(monthYear)){
             total += expense.getAmount();
         }
     }
        return total;
    }

    public void removeExpense(int index){
        expenses.remove(index);
    }

    public void showExpenseWithNumbers(){
        for (int i = 0; i < expenses.size(); i++){
            Expenses expense = expenses.get(i);
            System.out.println((i + 1) + toString());
        }
    }

    public Expenses getBiggestExpenses(){
        if (expenses.isEmpty()){
            return null;
        }
        Expenses biggest = expenses.get(0);
        for (Expenses expense : expenses){
            if (expense.getAmount() > biggest.getAmount()){
                biggest = expense;
            }
        }
        return biggest;
    }

    public boolean isDuplicateExpense(
            String category,
            String name,
            int amount,
            String date
    ) {
        for (Expenses expense : expenses) {
            if (expense.getCategory().equals(category)
                    && expense.getName().equals(name)
                    && expense.getAmount() == amount
                    && expense.getDate().equals(date)) {

                return true;
            }
        }

        return false;
    }



    public void searchExpenses(String keyword){
        boolean found = false;
        System.out.println("Search Results: ");
        for (Expenses expense : expenses){
            if ( expense.getName() .toLowerCase() .contains(keyword.toLowerCase())){
            found = true;
                System.out.println(toString());
            }
        }
        if (!found){
            System.out.println("No Matches Found");
        }
    }

    public void setBudget(int userBudget) {
        this.budget = userBudget;
    }
}