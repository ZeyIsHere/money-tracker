import Model.Expenses;
import Service.BudgetManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);

        int userBudget;

        while (true) {

            System.out.print("Enter Weekly Budget: ");

            if (!input.hasNextInt()) {

                System.out.println("Invalid Input!");
                input.nextLine();
                continue;
            }

            userBudget = input.nextInt();
            input.nextLine();

            break;
        }

        BudgetManager manager = new BudgetManager(userBudget);
        manager.loadExpenses();

        while (true) {

            System.out.println("=== MAIN MENU ===");
            System.out.println("1. Add Expense");
            System.out.println("2. Show Expenses");
            System.out.println("3. Show Remaining Money");
            System.out.println("4. Exit");

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

                    System.out.print("Add Total Expense: ");

                    if (!input.hasNextInt()) {

                        System.out.println("Invalid Amount!");
                        input.nextLine();
                        break;
                    }

                    int amount = input.nextInt();
                    input.nextLine();

                    Expenses expense =
                            new Expenses(category, name, amount);

                    manager.addExpenses(expense);

                    System.out.println("Expense Added!");

                    break;

                case 2:

                    manager.showExpense();

                    break;

                case 3:

                    System.out.println(
                            "Remaining Money: "
                                    + manager.getRemainingMoney()
                    );

                    break;

                case 4:

                    System.out.println("Exiting...");
                    manager.saveExpenses();

                    return;

                default:

                    System.out.println("Invalid Option!");
            }
        }
    }
}