package sample;

public abstract class Part {
    private int id, stock, min, max;
    private double price;
    private String name;

    public Part(int id, String name, double price, int stock, int min, int max){    //constructor
        this.id=id;
        this.name=name;
        this.price=price;
        this.stock=stock;
        this.min=min;
        this.max=max;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
