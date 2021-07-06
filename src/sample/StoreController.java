package sample;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StoreController implements Initializable {
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

    public  List<Product> products1 = new ArrayList<>();

    private List<Product> getData()
    {
        List<Product> products = new ArrayList< >();
        Product product= new Product();
        try {
            int count =1;
            DatabaseConnection con = new DatabaseConnection();
            Connection conDb = con.getConnection();
            PreparedStatement statement ;
            String query = "Select PRODUCTNAME from products where productid =?";
            statement = conDb.prepareStatement(query);

            for (int i=1;i<=8;i++){
                statement.setString(1,("P00"+count));
                ResultSet rs = statement.executeQuery();
                while(rs.next()) {
                    System.out.println(rs.getString("productname"));
                    product.setName(rs.getString("productname"));
                    product.setPrice(1200.0);
                    product.setImgsrc("images/ProductImages/iphone-12-blue-select-2020.png");
                    products.add(product);
                }
                count++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return products;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File shipfile = new File("images/shipment.png");
        Image ShipmentImage = new Image(shipfile.toURI().toString());
        shippingImageView.setImage(ShipmentImage);
        File ShopOnlineFile = new File("images/shop.png");
        Image ShopImage = new Image(ShopOnlineFile.toURI().toString());
        shopOnlineView.setImage(ShopImage);
        File SelectedImageFile = new File("images/ProductImages/iphone-12-blue-select-2020.png");
        Image SelectedImage = new Image(SelectedImageFile.toURI().toString());
        SelectedItemImage.setImage(SelectedImage);
        File accountFile = new File("images/accountincon.png");
        Image accountFileImage = new Image(accountFile.toURI().toString());
        MyAccountImageView.setImage(accountFileImage);
        File CartFile = new File("images/cart.png");
        Image CartImage = new Image(CartFile.toURI().toString());
        CartImageView.setImage(CartImage);
        products1.addAll(getData());
        try {
            int column=0;
            int row = 0;
            for (int i = 1; i < products1.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(products1.get(i));
                if (column==1)
                {
                 column =0;
                 row++;
                }
                System.out.println(products1.get(i).getName());
                gridpane.add(anchorPane,column++,row);
                GridPane.setValignment(anchorPane, VPos.CENTER);
                GridPane.setMargin(anchorPane,new Insets(40.0f,40.0f,40.0f,40.0f));
            }


        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }


}
