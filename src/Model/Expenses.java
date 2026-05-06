package Model;

public class Expenses {
    private String name;
    private int amount;

    public Expenses(String name, int amount){
        this.name = name;
        this.amount = amount;
    }
    public String getName(){
        return name;
    }
    public int getAmount(){
        return amount;
    }
}
