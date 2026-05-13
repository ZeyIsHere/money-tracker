package Model;

public class Expenses {
    private String name;
    private int amount;
    private String category;
    private String date;

    public Expenses(String category, String name, int amount, String date){
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public String getName() {
        return name;
    }
    public int getAmount(){
        return amount;
    }

    public String getCategory(){
        return category;
    }
    public String getDate(){
        return date;
    }
    public String toString(){
        return name + " - " + category + " - " + date + " - " + amount;
    }
}
