import Model.Expenses;
import Service.BudgetManager;

public class Main {
    public static void main(String[] args) {

        BudgetManager manager = new BudgetManager(100000);

        Expenses food = new Expenses("Food", 25000);
        Expenses appleMusic = new Expenses("appleMusic", 50000);

        manager.addExpenses(food);
        manager.addExpenses(appleMusic);

        System.out.println("Remaining Money: " + manager.getRemainingMoney());
    }
}