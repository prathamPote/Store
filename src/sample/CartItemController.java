package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CartItemController {
    @FXML
    private Label NameLbl,PriceLbl,QuantityLbl;
    @FXML
    private Button IncrementBtn,DecrementBtn,CloseBtn;
    @FXML
    private ImageView CartItemImage,CloseImage;
    private RemoveListner removeListner;
    private CartProduct item;
    private AnchorPane anchorPane;
    public static final String Currency = "Rs.";
    public void RemoveItemOnAction(ActionEvent event) throws Exception {
        removeListner.OnRemoveListner(item,anchorPane);
    }
    public void setData(CartProduct item, RemoveListner removeListner, AnchorPane anchorPane)
    {
        this.anchorPane = anchorPane;
        this.removeListner=removeListner;
        this.item=item;
        NameLbl.setText(item.getName());
        PriceLbl.setText(Currency+ item.getPrice());
        QuantityLbl.setText(Integer.toString(item.getQuantity()));
        CartItemImage.setImage(item.getImgsrc());
        File file = new File("images/cartitemjpg.jpg");
        Image closeimg = new Image(file.toURI().toString());
        CloseImage.setImage(closeimg);
    }
    public void SetOnIncrement(ActionEvent event)throws Exception
    {
        QuantityLbl.setText( String.valueOf(Integer.parseInt(QuantityLbl.getText())+1));
        DatabaseConnection con = new DatabaseConnection();
        Connection conDb = con.getConnection();
        Statement statement = conDb.createStatement();
        Statement price = conDb.createStatement();
        double Price = item.getPrice()*Integer.parseInt(QuantityLbl.getText());
        PriceLbl.setText(String.valueOf(Price));
        ResultSet rs =statement.executeQuery("update cart set quantity ="+QuantityLbl.getText()+"where productid = '"+item.getProductid()+"'");
        ResultSet pricers = price.executeQuery("update cart set price ="+Price+"where productid = '"+item.getProductid()+"'");
        pricers.next();
        rs.next();
        statement.executeQuery("Commit");
    }
    public void SetOnDecrement(ActionEvent event)throws Exception
    {
        QuantityLbl.setText( String.valueOf(Integer.parseInt(QuantityLbl.getText())-1));
        DatabaseConnection con = new DatabaseConnection();
        Connection conDb = con.getConnection();
        Statement statement = conDb.createStatement();
        Statement price = conDb.createStatement();
        double Price = item.getPrice()*Integer.parseInt(QuantityLbl.getText());
        PriceLbl.setText(String.valueOf(Price));
        ResultSet rs =statement.executeQuery("update cart set quantity ="+QuantityLbl.getText()+"where productid = '"+item.getProductid()+"'");
        ResultSet pricers = price.executeQuery("update cart set price ="+Price+"where productid = '"+item.getProductid()+"'");
        pricers.next();
        rs.next();
        statement.executeQuery("Commit");
    }


}
