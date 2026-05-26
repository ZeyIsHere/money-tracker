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
                if (userBudget <= 0) {
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
                    manager.addExpenseMenu(input, manager);
                    break;

                case 2:
                    manager.showExpense();
                    break;

                case 3:
                    manager.removeExpenseMenu(input, manager);
                    break;

                case 4:
                    manager.updateBudgetMenu(input, manager);
                    break;

                case 5:
                    manager.showMonthlySpendingMenu(input, manager);
                    break;
                case 6:
                    manager.showBiggestExpenseMenu(input, manager);
                    break;

                case 7:
                    manager.searchExpensesMenu(input, manager);
                    break;

                case 8:
                    manager.exitMenu(manager);

                default:
                    System.out.println(
                            "Invalid Option!"
                    );
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
}