import Model.Expenses;
import Service.BudgetManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException   {

        Scanner input = new Scanner(System.in);

        BudgetManager manager =
                new BudgetManager(0);

        manager.loadBudget();
        manager.loadExpenses();

        if (manager.getBudget() == 0) {
            while (true) {
                System.out.print("Enter Weekly Budget: ");

                if (!input.hasNextInt()) {
                        System.out.println("Invalid Input!");
                        input.nextLine();
                        continue;
                }
                int userBudget = input.nextInt();
                input.nextLine();
                if (userBudget <= 0){
                    System.out.println("Budget must not be zero or a negative value!");
                    continue;
                }
                manager.setBudget(userBudget);
                manager.saveBudget();
                break;
            }
        }

        while (true) {
            System.out.println("=== MAIN MENU ===");
            System.out.println(
                    "Weekly Budget: "
                            + manager.getBudget()
            );
            System.out.println(
                    "Estimated Monthly Budget: "
                            + manager.getMonthlyBudget()
            );
            System.out.println(
                    "Remaining Money: "
                            + manager.getRemainingMoney()
            );

            System.out.println();
            System.out.println("1. Add Expense");
            System.out.println("2. Show Expenses");
            System.out.println("3. Remove Expense");
            System.out.println("4. Update Budget");
            System.out.println("5. Show Monthly Spending");
            System.out.println("6. Show Biggest Expense");
            System.out.println("7. Search Expenses");
            System.out.println("8. Exit");
            System.out.print("Choose Option: ");

            if (!input.hasNextInt()) {
                System.out.println("Invalid Input!");
                input.nextLine();
                continue;
            }

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Add Category: ");
                    String category = input.nextLine();
                    System.out.print("Add Expense Name: ");
                    String name = input.nextLine();
                    System.out.print("Enter Expense Date: (xx-xx-xxxx)");
                    String date = input.nextLine();
                    String[] parts = date.split("-");
                    if (parts.length !=3){
                        System.out.println("Invalid date format!");
                        break;
                    }
                    try {
                        int day = Integer.parseInt(parts[0]);
                        int month = Integer.parseInt(parts[1]);
                        int Year = Integer.parseInt(parts[2]);
                        if (day >= 32 || day <= 0 || month >= 12 || month <= 0 || Year >= 9999 || Year <= 0) {
                            System.out.println("Invalid date format!");
                            break;
                        }
                    }
                    catch (NumberFormatException e){
                        System.out.println("Invalid Date Format");
                        break;
                    }
                    System.out.print("Add Total Expense: ");
                    if (!input.hasNextInt()) {
                        System.out.println("Invalid Amount!");
                        input.nextLine();
                        try{
                            Thread.sleep(1000);
                        }
                        catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        break;
                    }
                    int amount = input.nextInt();
                    input.nextLine();
                    if (amount <= 0){
                        System.out.println("Expense amount must not be 0 or a negative value!");
                    }

                    Expenses expense =
                            new Expenses(
                                    category,
                                    name,
                                    amount,
                                    date
                            );
                    if (manager.isDuplicateExpense(category, name, amount, date)){
                        System.out.println ("Warning: Expense already exists!");
                        System.out.println ("Add Anyway? (Y/N)");
                        String Confirm = input.nextLine();
                        if (Confirm.equalsIgnoreCase("n")){
                            System.out.println ("Canceled!");
                            try{
                                Thread.sleep(1000);
                            }
                            catch (InterruptedException e){
                                e.printStackTrace();
                            }
                            break;
                        }
                    }

                    manager.addExpense(expense);
                    System.out.println("Expense Added!");
                    manager.saveExpenses();
                    try{
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    manager.showExpense();
                    break;

                case 3:
                    if (manager.getExpenseCount() == 0){
                        System.out.println("No expenses yet!");
                        try{
                            Thread.sleep(1000);
                        }
                        catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        break;
                    }


                    manager.showExpenseWithNumbers();
                    System.out.print("Choose expense to remove: ");
                    if (!input.hasNextInt()) {
                        System.out.println("Invalid Input!");
                        input.nextLine();
                        try{
                            Thread.sleep(1000);
                        }
                        catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        break;
                    }
                    int removeChoice = input.nextInt();
                    input.nextLine();
                    if (removeChoice > 0 && removeChoice <= manager.getExpenseCount()){
                        manager.removeExpense(removeChoice - 1);
                        System.out.println(
                                "Expense Removed!"
                        );
                        try{
                            Thread.sleep(1000);
                        }
                        catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }

                    else{
                        System.out.println("Invalid Expense Number!");
                    }
                    try{
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    manager.saveExpenses();
                    break;

                case 4:
                    System.out.print("Enter your new budget: ");
                    if (!input.hasNextInt()) {
                        System.out.println("Invalid Input!");
                        input.nextLine();
                        try{
                            Thread.sleep(1000);
                        }
                        catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        break;
                    }

                    int newBudget = input.nextInt();
                    input.nextLine();
                    manager.setBudget(newBudget);
                    manager.saveBudget();
                    System.out.println("Budget Updated!");
                    break;

                case 5:
                    System.out.print("Enter Month/Year(-5-2026)");
                    String monthYear = input.nextLine();
                    if (!monthYear.matches("^(0?[1-9]|1[0-2])-\\d{4}$")) {
                        System.out.println("Invalid format! Use MM-YYYY");
                        try{
                            Thread.sleep(1000);
                        }
                        catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        break;
                    }
                    int monthlyTotal =
                            manager.getMonthlyExpense(monthYear);
                    System.out.print("Monthly Spending: " + monthlyTotal);
                    try{
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    break;

                case 6:
                    Expenses biggest = manager.getBiggestExpenses();
                    if (biggest == null) {
                        System.out.println("No expenses found.");
                    }
                    else {
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
                    try{
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    break;

                case 7:
                    System.out.print("Enter expense name: ");
                    String keyword = input.nextLine();
                    manager.searchExpenses(keyword);
                    try{
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    break;

                case 8:
                    System.out.println("Exiting...");
                    manager.saveExpenses();
                    manager.saveBudget();
                    return;

                default:
                    System.out.println(
                            "Invalid Option!"
                    );
                    try{
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
            }
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

}