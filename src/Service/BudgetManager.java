package Service;
import Model.Expenses;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class BudgetManager {
    private int  budget;
    private ArrayList<Expenses> expenses;

    public BudgetManager(int budget){
        this.budget = budget;
        expenses = new ArrayList<>();
    }

    public void saveExpenses() throws IOException {

        BufferedWriter writer =
                new BufferedWriter(
                        new FileWriter("expenses.txt")
                );

        for (Expenses expense : expenses) {

            writer.write(
                    expense.getCategory()
                            + "," +
                            expense.getName()
                            + "," +
                            expense.getAmount()
            );

            writer.newLine();
        }

        writer.close();
    }

    public void loadExpenses() {

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader("expenses.txt")
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                String category = parts[0];
                String name = parts[1];
                int amount = Integer.parseInt(parts[2]);

                Expenses expense =
                        new Expenses(category, name, amount);

                expenses.add(expense);
            }

            reader.close();

        } catch (IOException e) {

            System.out.println("No previous expense data found.");
        }
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

            System.out.println(
                    expense.getCategory()
                    + " | " +
                    expense.getName()
                    + " - " +
                    expense.getAmount());
        }
    }
}
