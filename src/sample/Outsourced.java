package sample;

public class Outsourced extends Part {
    private String companyName;
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName)   //constructor
    {
        super(id, name, price, stock, min, max);
        this.companyName=companyName;
    }

    //getters and setters

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getCompanyName(){
        return this.companyName;
    }
}
