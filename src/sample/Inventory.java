package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static javafx.collections.FXCollections.observableArrayList;

public class Inventory {
    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;
    private Product selectedProduct;
    private Part selectedPart;
    private int selectedIndex;
    private int partIDNumber;
    private int prodIDNumber;



    public Inventory() {                //constructor
        this.allParts = observableArrayList();
        this.allProducts = observableArrayList();
        this.partIDNumber = 3;
        this.prodIDNumber = 3;
    }

    //getters and setters

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public int getProdIDNumber() {
        return prodIDNumber;
    }

    public void setProdIDNumber(int prodIDNumber){
        this.prodIDNumber = prodIDNumber;
    }

    public int getPartIDNumber() {
        return partIDNumber;
    }

    public void setPartIDNumber(int partIDNumber) {
        this.partIDNumber = partIDNumber;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public Part getSelectedPart() {
        return selectedPart;
    }

    public void setSelectedPart(Part selectedPart) {
        this.selectedPart = selectedPart;
    }

    public void addPart(Part newPart){
        this.allParts.add(newPart);
    }

    public void addProduct(Product newProduct){
        this.allProducts.add(newProduct);
    }


    public Part lookupPart(int partId){
        for (Part part:allParts){      //search through each part in allParts
            if(part.getId()== partId)
                return part;           //return part if partId matches

        }
        return null;
    }

    public Product lookupProduct(int productId){
        for (Product product:allProducts){      //search through each product in allProducts
            if(product.getId()== productId)
                return product;           //return product if productId matches

        }
        return null;
    }

    public ObservableList<Part> lookupPart(String name){
        ObservableList<Part> tempPartList = observableArrayList();
        for (Part part:allParts){               //search through each part in allParts
            if(part.getName().contains(name))
                tempPartList.add(part);         //add part to list of matching parts
        }
        return tempPartList;
    }

    public ObservableList<Product> lookupProduct(String name){
        ObservableList<Product> tempProductList = FXCollections.observableArrayList();
        for(Product product:allProducts){           //search through each product in allProducts
            if(product.getName().contains(name))
                tempProductList.add(product);      //add product to list of matching products
        }
        return tempProductList;
    }

    public void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    public boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    public boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    public ObservableList<Part> getAllParts(){
        return this.allParts;
    }

    public ObservableList<Product> getAllProducts(){
        return this.allProducts;
    }

}
