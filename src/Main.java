import Model.Expenses;
import Service.BudgetManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

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
                manager.setBudget(userBudget);
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
            System.out.println("6. Exit");
            System.out.println("Choose Option: ");

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
                    System.out.print("Enter Expense Date: ");
                    String date = input.nextLine();
                    System.out.print("Add Total Expense: ");
                    if (!input.hasNextInt()) {
                        System.out.println("Invalid Amount!");
                        input.nextLine();
                        break;
                    }
                    int amount = input.nextInt();
                    input.nextLine();
                    Expenses expense =
                            new Expenses(
                                    category,
                                    name,
                                    amount,
                                    date
                            );
                    manager.addExpenses(expense);
                    System.out.println("Expense Added!");
                    break;

                case 2:
                    manager.showExpense();
                    break;

                case 3:
                    manager.showExpenseWithNumbers();
                    System.out.print(
                            "Choose expense to remove: "
                    );
                    if (!input.hasNextInt()) {
                        System.out.println("Invalid Input!");
                        input.nextLine();
                        break;
                    }
                    int removeChoice = input.nextInt();
                    input.nextLine();
                    manager.removeExpense(
                            removeChoice - 1
                    );
                    System.out.println(
                            "Expense Removed!"
                    );
                    break;

                case 4:
                    System.out.print("Enter your new budget: ");
                    if (!input.hasNextInt()) {
                        System.out.println("Invalid Input!");
                        input.nextLine();
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
                    int monthlyTotal =
                            manager.getMonthlyExpense(monthYear);
                    System.out.print("Monthly Spending: " + monthlyTotal);
                    break;

                case 6:
                    System.out.println("Exiting...");
                    manager.saveExpenses();
                    manager.saveBudget();
                    return;

                default:
                    System.out.println(
                            "Invalid Option!"
                    );
            }
        }
    }
}