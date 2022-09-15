package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private int id, stock, min, max;
    private double price;
    private String name;
    private ObservableList<Part> associatedParts;




    public Product(int id, String name, double price, int stock, int min, int max) {    //constructor
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        associatedParts= FXCollections.observableArrayList();
    }
    // I originally had an exception when I set associatedParts to an emptyObservableList, because emptyObservableList is uneditable, and I later tried to add elements to it.


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

    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return this.associatedParts.remove(selectedAssociatedPart);
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
