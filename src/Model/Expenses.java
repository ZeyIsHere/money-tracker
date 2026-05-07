package Model;

public class Expenses {
    private String name;
    private int amount;
    private String category;

    public Expenses(String category, String name, int amount){
        this.name = name;
        this.amount = amount;
        this.category = category;
    }
    public String getName(){
        return name;
    }
    public int getAmount(){
        return amount;
    }

    public String getCategory(){
        return category;
    }
}
