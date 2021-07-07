package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class ItemController {
    @FXML
    private Label NameLabel,PriceLabel;
    @FXML
    private ImageView ItemImage;
    private Product product;
    public static final String Currency = "Rs.";

    public void setData(Product product)
    {
         this.product=product;
        NameLabel.setText(product.getName());
        PriceLabel.setText(Currency+ product.getPrice());
        File imageFile = new File("images/ProductImages/iphone-12-blue-select-2020.png");
        Image image = new Image(imageFile.toURI().toString());
        ItemImage.setImage(image);
    }
}
