package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;


public class CartItemController {
    @FXML private Label NameLbl,PriceLbl,QuantityLbl;
    @FXML private Button IncrementBtn,DecrementBtn,CloseBtn;
    @FXML private ImageView CartItemImage,CloseImage;


    private CartProduct item;
    public static final String Currency = "Rs.";

    public void setData(CartProduct item)
    {
        this.item=item;
        NameLbl.setText(item.getName());
        PriceLbl.setText(Currency+ item.getPrice());
        QuantityLbl.setText(Integer.toString(item.getQuantity()));
        File imageFile = new File("images/ProductImages/iphone-12-blue-select-2020.png");
        Image image = new Image(imageFile.toURI().toString());
        CartItemImage.setImage(image);
        CloseImage.setImage(image);
    }
    public void 

}
