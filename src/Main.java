import Model.Expenses;
import Service.BudgetManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter Weekly Budget: ");
        int userBudget = input.nextInt();
        input.nextLine();

        BudgetManager manager = new BudgetManager(userBudget);
        manager.loadExpenses();

        while (true) {

            System.out.println("=== MAIN MENU ===");
            System.out.println("1. Add Expense");
            System.out.println("2. Show Total Expense");
            System.out.println("3. Show Remaining Money");
            System.out.println("4. Exit");

            System.out.print("Choose Option: ");
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Add Category: ");
                    String category = input.nextLine();

                    System.out.print("Add Expense Name: ");
                    String name = input.nextLine();

                    System.out.print("Add Total Expense: ");
                    int amount = input.nextInt();

                    Expenses expense = new Expenses(category, name, amount);

                    manager.addExpenses(expense);

                    System.out.println("Expense Added!");

                    break;

                case 2:
                    manager.showExpense();

                    break;

                case 3:

                    System.out.println("Remaining Money: "
                            + manager.getRemainingMoney());

                    break;

                case 4:

                    System.out.println("Exiting...");
                    manager.saveExpenses();
                    return;
            }
        }
    }
}