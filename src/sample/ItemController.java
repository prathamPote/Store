package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;

public class ItemController {
    @FXML
    private ImageView shippingImageView, shopOnlineView, SelectedItemImage, MyAccountImageView, CartImageView;

    @FXML
    private Label SelectedItemName,SelectedItemPrice;
    @FXML
    private Button AddToCartBtn;
    @FXML
    private VBox ChoseItemCard;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane gridpane;
    @FXML
    private ComboBox CategoryComboBox;
    }
}
