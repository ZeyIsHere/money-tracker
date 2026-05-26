package Service;

import Model.
        Expenses;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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

    public int getExpenseCount() {
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

    public int getMonthlyExpense(String monthYear) {
        int total = 0;
        for (Expenses expense : expenses) {
            if (expense.getDate().contains(monthYear)) {
                total += expense.getAmount();
            }
        }
        return total;
    }

    public void removeExpense(int index) {
        expenses.remove(index);
    }

    public void showExpenseWithNumbers() {
        for (int i = 0; i < expenses.size(); i++) {
            Expenses expense = expenses.get(i);
            System.out.println((i + 1) + toString());
        }
    }

    public Expenses getBiggestExpenses() {
        if (expenses.isEmpty()) {
            return null;
        }
        Expenses biggest = expenses.get(0);
        for (Expenses expense : expenses) {
            if (expense.getAmount() > biggest.getAmount()) {
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


    public void searchExpenses(String keyword) {
        boolean found = false;
        System.out.println("Search Results: ");
        for (Expenses expense : expenses) {
            if (expense.getName().toLowerCase().contains(keyword.toLowerCase())) {
                found = true;
                System.out.println(toString());
            }
        }
        if (!found) {
            System.out.println("No Matches Found");
        }
    }

    public static void addExpenseMenu(
            Scanner input,
            BudgetManager manager
    ) throws IOException {
        System.out.print("Add Category: ");
        String category = input.nextLine();
        System.out.print("Add Expense Name: ");
        String name = input.nextLine();
        System.out.print("Enter Expense Date: ");
        String date = input.nextLine();
        String[] parts = date.split("-");
        if (parts.length != 3) {
            System.out.println(
                    "Invalid date format!"
            );
            return;
        }
        try {
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            if ((day >= 32 || day <= 0)
                    || (month > 12 || month <= 0)
                    || (year >= 9999 || year <= 0)) {
                System.out.println(
                        "Invalid date format!"
                );
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(
                    "Date must contain numbers only!"
            );
            return;
        }
        System.out.print("Add Total Expense: ");
        if (!input.hasNextInt()) {
            System.out.println("Invalid Amount!");
            input.nextLine();
            return;
        }
        int amount = input.nextInt();
        input.nextLine();
        if (amount <= 0) {
            System.out.println(
                    "Expense must be positive!"
            );
            return;
        }
        Expenses expense =
                new Expenses(
                        category,
                        name,
                        amount,
                        date
                );
        if (manager.isDuplicateExpense(
                category,
                name,
                amount,
                date
        )) {
            System.out.println(
                    "Warning: Similar expense already exists."
            );
            System.out.print(
                    "Add anyway? (y/n): "
            );
            String confirm = input.nextLine();
            if (confirm.equalsIgnoreCase("n")) {
                System.out.println(
                        "Expense addition cancelled."
                );
                return;
            }
        }
        manager.addExpense(expense);
        manager.saveExpenses();
        System.out.println("Expense Added!");
    }

    public static void removeExpenseMenu(Scanner input, BudgetManager manager) throws IOException{
        if (manager.getExpenseCount() == 0) {
            System.out.println("No expenses yet!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }


        manager.showExpenseWithNumbers();
        System.out.print("Choose expense to remove: ");
        if (!input.hasNextInt()) {
            System.out.println("Invalid Input!");
            input.nextLine();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        int removeChoice = input.nextInt();
        input.nextLine();
        if (removeChoice > 0 && removeChoice <= manager.getExpenseCount()) {
            manager.removeExpense(removeChoice - 1);
            System.out.println(
                    "Expense Removed!"
            );
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid Expense Number!");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        manager.saveExpenses();

    }

    public static void updateBudgetMenu(Scanner input, BudgetManager manager)throws IOException{
        System.out.print("Enter your new budget: ");
        if (!input.hasNextInt()) {
            System.out.println("Invalid Input!");
            input.nextLine();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }

        int newBudget = input.nextInt();
        input.nextLine();
        manager.setBudget(newBudget);
        manager.saveBudget();
        System.out.println("Budget Updated!");
    }

    public static void showMonthlySpendingMenu(Scanner input, BudgetManager manager) throws  IOException{
        System.out.print("Enter Month/Year(-5-2026)");
        String monthYear = input.nextLine();
        if (!monthYear.matches("^(0?[1-9]|1[0-2])-\\d{4}$")) {
            System.out.println("Invalid format! Use MM-YYYY");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        int monthlyTotal =
                manager.getMonthlyExpense(monthYear);
        System.out.print("Monthly Spending: " + monthlyTotal);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void showBiggestExpenseMenu(Scanner input, BudgetManager manager)throws IOException{
        Expenses biggest = manager.getBiggestExpenses();
        if (biggest == null) {
            System.out.println("No expenses found.");
        } else {
            System.out.println("Biggest Expense: ");
            System.out.println(biggest.getName()
                    + " - "
                    + biggest.getAmount()
                    + " - "
                    + biggest.getCategory()
                    + " - "
                    + biggest.getDate()
            );
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void searchExpensesMenu(Scanner input, BudgetManager manager)throws IOException{
        System.out.print("Enter expense name: ");
        String keyword = input.nextLine();
        manager.searchExpenses(keyword);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void exitMenu(BudgetManager manager)throws IOException{
        System.out.println("Exiting...");
        manager.saveExpenses();
        manager.saveBudget();
        return;
    }


    public void setBudget(int userBudget) {
        this.budget = userBudget;
    }
}