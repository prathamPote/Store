package sample;
package application;

import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class CartController implements Initializable {

    @FXML private BorderPane borderpane;
    @FXML private Label CartLbl,AddressLbl,Pincodelbl,CityLbl,StateLbl,ShipAdrLbl,PayOptLbl,OrderMsgsLbl;
    @FXML private TextField AddressTxt,PincodeTxt,CityTxt,StateTxt;
    @FXML private Button ConOrdBtn;
    @FXML private RadioButton PayoptBtn1,PayoptBtn2,PayoptBtn3,PayoptBtn4;
    @FXML private ScrollPane scrollPane;
    @FXML private GridPane gridpane;

    public List<CartProduct> Cartproducts1 = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Cartproducts1.addAll(getData());
        try {
            gridpane.getChildren().clear();
            int column=0;
            int row = 1;
            for (int i = 0; i < Cartproducts1.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("CartItemController.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                CartItemController itemController = fxmlLoader.getController();
                itemController.setData(Cartproducts1.get(i));
                if (column==3)
                {
                    column =0;
                    row++;
                }
                System.out.println(Cartproducts1.get(i).getName());
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


    private List<CartProduct> getData()
    {
        List<CartProduct> products = new ArrayList< >();

        try {
            int count =1;

            DatabaseConnection con = new DatabaseConnection();
            Connection conDb = con.getConnection();
            PreparedStatement Namestatement,PriceStatment,QuantityStatement;
            String Namequery = "Select PRODUCTNAME from cart where productid =?";
            String Pricequery= "Select PRODUCTPRICE from cart where productid = ?";
            String Quantityquery= "Select quantity from cart where productid = ?";

            Namestatement = conDb.prepareStatement(Namequery);
            PriceStatment=conDb.prepareStatement(Pricequery);
            QuantityStatement= conDb.prepareStatement(Quantityquery);
            for (int i=1;i<=8;i++){
                CartProduct product= new CartProduct();
                Namestatement.setString(1,("P00"+count));
                PriceStatment.setString(1,("P00"+count));
                ResultSet rs1 = Namestatement.executeQuery();
                ResultSet rs2 = PriceStatment.executeQuery();
                ResultSet rs3 = QuantityStatement.executeQuery();

                float price = rs2.getFloat(1) * rs3.getInt(1);
                while(rs1.next() &&rs2.next() ) {
                    System.out.println(rs1.getString("productname"));
                    product.setName(rs1.getString("productname"));
                    product.setPrice((double) price);
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

    public Boolean UserAddress() {
        String address = AddressTxt.getText();
        String pincode = PincodeTxt.getText();
        String city = CityTxt.getText();
        String state = StateTxt.getText();
            String UserAddress = "Insert into address values (" + ThreadLocalRandom.current().nextInt() + ",'" + address + "'," + pincode + ",'" + city + "','" + state + "')";
            try {
                DatabaseConnection con = new DatabaseConnection();
                Connection conDb = con.getConnection();
                Statement statement = conDb.createStatement();
                ResultSet query = statement.executeQuery(UserAddress);
                while (query.next()) {
                    statement.executeQuery("commit");
                    return true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        return false;
    }
    public class PayoptLbl extends Application{
        public void setConOrdBtnOnAction(ActionEvent event)throws Exception{
            ToggleGroup group = new ToggleGroup();
            PayoptBtn1 button1 = new PayoptBtn1("Cash on delivery");
            PayoptBtn2 button2 = new PayoptBtn2("PhonePe / Gpay");
            PayoptBtn3 button3 = new PayoptBtn3("Credit /Debit card");
            PayoptBtn4 button4 = new PayoptBtn4("NetBanking");
            button1.setToggleGroup(group);
            button2.setToggleGroup(group);
            button3.setToggleGroup(group);
            button4.setToggleGroup(group);
            String address = AddressTxt.getText();
            String pincode = PincodeTxt.getText();
            String city = CityTxt.getText();
            String state = StateTxt.getText();

            if (address.isBlank() == true || pincode.isBlank()==true|| city.isBlank()==true|| state.isBlank()==true)
            {
                OrderMsgsLbl.setText("Please fill all the details");
            }
            else
            { (UserAddress())
                    OrderMsgsLbl.setText("Order SuccessFull");
            }


        }
    }





}
