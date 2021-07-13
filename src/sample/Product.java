package sample;

import javafx.scene.image.Image;
import java.io.File;

public class Product
{
    private String name;
    private String imgsrc;
    private double  price;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    private String productid;
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImgsrc() {
        File imgFile = new File(imgsrc);
        return new Image(imgFile.toURI().toString());
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
