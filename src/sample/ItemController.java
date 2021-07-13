package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class ItemController {
    @FXML
    private Label NameLabel,PriceLabel;
    @FXML
    private ImageView ItemImage;
    @FXML
    private void click(MouseEvent event)
    {
        selectListner.OnClickListner(product);
    }
    private Product product;
    private SelectListner selectListner;
    public static final String Currency = "Rs.";

    public void setData(Product product,SelectListner selectListner)
    {
         this.product=product;
         this.selectListner=selectListner;
        NameLabel.setText(product.getName());
        PriceLabel.setText(Currency+ product.getPrice());
        ItemImage.setImage(product.getImgsrc());
    }
}
