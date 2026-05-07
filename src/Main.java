import Model.Expenses;
import Service.BudgetManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int Choice;

        BudgetManager manager = new BudgetManager(100000);

        Scanner input = new Scanner(System.in);

        while (true){
            System.out.println("===MAIN MENU===");
            System.out.println("1. Add Expense");
            System.out.println("2. Show Remaining Money");
            System.out.println("3. Exit");
            System.out.println("Choose Option: ");
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Add Expense Name: ");
                    String name = input.nextLine();

                    System.out.print("Add Total Expense: ");
                    int amount = input.nextInt();

                    Expenses expense = new Expenses(name, amount);

                    manager.addExpenses(expense);

                    System.out.println("Expense Added!");

                    break;

                case 2:
                    System.out.println(manager.getRemainingMoney());

                    break;

                case 3:
                    System.out.println("Exiting....");

                    return;
            }
        }
    }
}