package sample;

import javafx.scene.image.Image;

import java.io.File;

public class CartProduct {

    String name;
    Double price;
    int Quantity;
    String Productid;
    String Imgsrc;

    public String getProductid() {
        return Productid;
    }

    public void setProductid(String productid) {
        Productid = productid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public Image getImgsrc() {
        File imgFile = new File(Imgsrc);
        return new Image(imgFile.toURI().toString());
    }
    public void setImgsrc(String imgsrc) {
       Imgsrc= imgsrc;
    }
}
