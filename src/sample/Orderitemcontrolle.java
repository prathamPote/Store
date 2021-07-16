package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Orderitemcontrolle
{
    @FXML
    private Label NameLbl,PriceLbl,QuantityLbl;
    @FXML
    private ImageView CartItemImage;
    private CartProduct item;
    public static final String Currency = "Rs.";

    public void setData(CartProduct item)
    {
        this.item=item;
        NameLbl.setText(item.getName());
        PriceLbl.setText(Currency+ item.getPrice());
        QuantityLbl.setText(Integer.toString(item.getQuantity()));
        CartItemImage.setImage(item.getImgsrc());
    }
}

