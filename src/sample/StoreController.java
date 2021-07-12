
package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class StoreController implements Initializable {
    @FXML
    private ImageView shippingImageView, shopOnlineView, SelectedItemImage, MyAccountImageView, CartImageView;

    @FXML
    private Label SelectedItemName, SelectedItemPrice;
    @FXML
    private Button AddToCartBtn,SearchBtn;
    @FXML
    private VBox ChoseItemCard;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane gridpane;
    @FXML
    private ComboBox CategoryComboBox,QuantityComboBox;
    @FXML
    private TextField SearchTxtField;

    public List<Product> Products1 = new ArrayList<>();
    private SelectListner selectListner;
     Product ChosenProduct= new Product();

    public void AddtoCartBtnOnAction(ActionEvent event) throws Exception
    {
    DatabaseConnection con = new DatabaseConnection();
    Connection condb = con.getConnection();
    Statement statement = condb.createStatement();
    Statement checkcart = condb.createStatement();
    ResultSet check = checkcart.executeQuery("Select count(*) from cart where productid = '"+ChosenProduct.getProductid()+"'");
    check.next();
    if (check.getInt(1)==1){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Item is already in the cart");
        alert.setTitle("Electronic Fellows");
        alert.show();
    }
    else {
        statement.executeQuery("Insert into Cart values('" + ChosenProduct.getProductid() + "','" + ChosenProduct.getName() + "'," + ChosenProduct.getQuantity() + "," + ChosenProduct.getPrice() * ChosenProduct.getQuantity() + ")");
        statement.executeQuery("Commit");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Added to cart");
        alert.setTitle("Electronic Fellows");
        alert.show();
    }
    }
    public void MyAccountOnAction(MouseEvent event)throws Exception
    {
        // Linking My Account Interface to Store Interface
        Stage StoreStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MyAccount.fxml")));
        StoreStage.setTitle("Electronic Fellows");
        StoreStage.initStyle(StageStyle.DECORATED);
        StoreStage.setScene(new Scene(root, 1164, 636));
        StoreStage.show();
    }
    public void CartOnAction(MouseEvent event)throws Exception
    {
        // Add cart interface
    }
    int count1=0;
    public void SetSearchBtnOnAction(ActionEvent event)throws Exception
    {
         String key = SearchTxtField.getText();
           DatabaseConnection con = new DatabaseConnection();
           Connection condb = con.getConnection();
           Statement statement1 = condb.createStatement();
           Statement statement2 = condb.createStatement();
           ResultSet search = statement1.executeQuery("Select * from products where PRODUCTNAME LIKE '%"+key+"%'");
           ResultSet count = statement2.executeQuery("Select count(*) from products where PRODUCTNAME LIKE '%"+key+"%'");
           count.next();
           if (count.getInt(1)==0)
           {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setContentText("Item not found");
               alert.setTitle("Electronic Fellows");
               alert.show();
           }
            List<Product> SearchProducts = new ArrayList<>();
                for (int i = 1; i <= count.getInt(1); i++) {
                     while(search.next()){
                         Product product = new Product();
                         System.out.println(search.getString(2));
                        product.setProductid(search.getString(1));
                        product.setName(search.getString(2));
                        product.setPrice(search.getDouble(3));
                        int imgid = Integer.parseInt(product.getProductid().substring(3));
                        product.setImgsrc("images/ProductImages/Mobile Phones/" + imgid + ".png");
                        SearchProducts.add(product);
                    }
                }
                gridpane.getChildren().removeAll();
                gridpane.getChildren().clear();
                int column = 0;
                int row = 1;
        selectListner = new SelectListner() {
            @Override
            public void OnClickListner(Product product) {
                SetChosenProduct(product);
            }
        };
        for (int i = 0; i < SearchProducts.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Item.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            ItemController itemController = fxmlLoader.getController();
            itemController.setData(SearchProducts.get(i), selectListner);
            if (column == 3) {
                column = 0;
                row++;
            }
            System.out.println(SearchProducts.get(i).getName());
            gridpane.add(anchorPane, column++, row);
            GridPane.setValignment(anchorPane, VPos.CENTER);
            GridPane.setMargin(anchorPane, new Insets(40.0f, 40.0f, 40.0f, 40.0f));
        }
        SearchProducts.clear();
        count1++;

        }
    public void SetChosenProduct(Product product) {
        SelectedItemName.setText(product.getName());
        SelectedItemPrice.setText("Rs."+product.getPrice().toString());
        SelectedItemImage.setImage(product.getImgsrc());
        ChosenProduct.setPrice(product.getPrice());
        ChosenProduct.setName(product.getName());
        ChosenProduct.setQuantity(Integer.parseInt(QuantityComboBox.getSelectionModel().getSelectedItem().toString()));
        ChosenProduct.setProductid(product.getProductid());
    }
    public void SetQuantityOnAction(ActionEvent event)
    {
        ChosenProduct.setQuantity(Integer.parseInt(QuantityComboBox.getSelectionModel().getSelectedItem().toString()));
    }
    private List<Product> getData(String choice) {
        List<Product> products = new ArrayList<>();

        try {
            DatabaseConnection con = new DatabaseConnection();
            Connection conDb = con.getConnection();
            PreparedStatement Namestatement, PriceStatment, IdStatement;
            Statement CountStatement = conDb.createStatement();
            ResultSet CountRs = CountStatement.executeQuery("Select count(*) from products");
            CountRs.next();
            if (choice.equalsIgnoreCase("Mobile Phones")) {
                String Namequery = "Select PRODUCTNAME from products where productid =? and catid = 'Cat001'";
                String Pricequery = "Select PRODUCTPRICE from products where productid = ? and catid='Cat001'";
                Namestatement = conDb.prepareStatement(Namequery);
                PriceStatment = conDb.prepareStatement(Pricequery);
                for (int i = 1; i <= CountRs.getInt(1); i++) {
                    Product product = new Product();
                    Namestatement.setString(1, ("P00" + i));
                    PriceStatment.setString(1, ("P00" + i));
                    ResultSet rs1 = Namestatement.executeQuery();
                    ResultSet rs2 = PriceStatment.executeQuery();
                    while (rs1.next() && rs2.next()) {
                        product.setName(rs1.getString("productname"));
                        product.setPrice(rs2.getFloat("productprice"));
                        product.setImgsrc("images/ProductImages/Mobile Phones/"+i+".png");
                        product.setProductid("P00"+i);
                        products.add(product);
                    }

                }

            } else if (choice.equalsIgnoreCase("Laptops")) {
                String Namequery = "Select PRODUCTNAME from products where productid =? and CATID = 'Cat002'";
                String Pricequery = "Select PRODUCTPRICE from products where productid = ? and catid='Cat002'";
                Namestatement = conDb.prepareStatement(Namequery);
                PriceStatment = conDb.prepareStatement(Pricequery);
                for (int i = 31; i <= CountRs.getInt(1); i++) {
                    Product product = new Product();
                    System.out.println(CountRs.getInt(1));
                    Namestatement.setString(1, ("P00" + i));
                    PriceStatment.setString(1, ("P00" + i));
                    ResultSet rs1 = Namestatement.executeQuery();
                    ResultSet rs2 = PriceStatment.executeQuery();
                    while (rs1.next() && rs2.next()) {
                        System.out.println(rs1.getString("productname"));
                        product.setName(rs1.getString("productname"));
                        product.setPrice(rs2.getFloat("productprice"));
                        product.setImgsrc("images/ProductImages/Mobile Phones/"+i+".png");
                        product.setProductid("P00"+i);
                        products.add(product);
                    }
                }
            } else if (choice.equalsIgnoreCase("Tablets")) {
                String Namequery = "Select PRODUCTNAME from products where productid =? and CATID = 'Cat003'";
                String Pricequery = "Select PRODUCTPRICE from products where productid = ? and catid='Cat003'";
                Namestatement = conDb.prepareStatement(Namequery);
                PriceStatment = conDb.prepareStatement(Pricequery);
                for (int i = 1; i <= CountRs.getInt(1); i++) {
                    Product product = new Product();
                    System.out.println(CountRs.getInt(1));
                    Namestatement.setString(1, ("P00" + i));
                    PriceStatment.setString(1, ("P00" + i));
                    ResultSet rs1 = Namestatement.executeQuery();
                    ResultSet rs2 = PriceStatment.executeQuery();
                    while (rs1.next() && rs2.next()) {
                        System.out.println(rs1.getString("productname"));
                        product.setName(rs1.getString("productname"));
                        product.setPrice(rs2.getFloat("productprice"));
                        product.setImgsrc("images/ProductImages/Mobile Phones/"+i+".png");
                        product.setProductid("P00"+i);
                        products.add(product);
                    }
                }
            } else if (choice.equalsIgnoreCase("Computer Pheripherals")) {
                String Namequery = "Select PRODUCTNAME from products where productid =? and CATID = 'Cat004'";
                String Pricequery = "Select PRODUCTPRICE from products where productid = ? and catid='Cat004'";
                Namestatement = conDb.prepareStatement(Namequery);
                PriceStatment = conDb.prepareStatement(Pricequery);
                for (int i = 1; i <= CountRs.getInt(1); i++) {
                    Product product = new Product();
                    System.out.println(CountRs.getInt(1));
                    Namestatement.setString(1, ("P00" + i));
                    PriceStatment.setString(1, ("P00" + i));
                    ResultSet rs1 = Namestatement.executeQuery();
                    ResultSet rs2 = PriceStatment.executeQuery();
                    while (rs1.next() && rs2.next()) {
                        System.out.println(rs1.getString("productname"));
                        product.setName(rs1.getString("productname"));
                        product.setPrice(rs2.getFloat("productprice"));
                        product.setImgsrc("images/ProductImages/Mobile Phones/"+i+".png");
                        product.setProductid("P00"+i);
                        products.add(product);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    int count = 0;

    public void OnComboBoxAction(ActionEvent event) {
        try {
            if (count != 0) {
                gridpane.getChildren().removeAll();
                gridpane.getChildren().clear();
            }
            String choice = CategoryComboBox.getSelectionModel().getSelectedItem().toString();
            int column = 0;
            int row = 1;
            Products1.addAll(getData(choice));
            selectListner = new SelectListner() {
                @Override
                public void OnClickListner(Product product) {
                    SetChosenProduct(product);
                }
            };

            for (int i = 0; i < Products1.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(Products1.get(i), selectListner);
                if (column == 3) {
                    column = 0;
                    row++;
                }
                System.out.println(Products1.get(i).getName());
                gridpane.add(anchorPane, column++, row);
                GridPane.setValignment(anchorPane, VPos.CENTER);
                GridPane.setMargin(anchorPane, new Insets(40.0f, 40.0f, 40.0f, 40.0f));
            }
            Products1.clear();
            count++;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File shipfile = new File("images/shipment.png");
        Image ShipmentImage = new Image(shipfile.toURI().toString());
        shippingImageView.setImage(ShipmentImage);
        File ShopOnlineFile = new File("images/shop.png");
        Image ShopImage = new Image(ShopOnlineFile.toURI().toString());
        shopOnlineView.setImage(ShopImage);
        File SelectedImageFile = new File("images/ProductImages/1.png");
        Image SelectedImage = new Image(SelectedImageFile.toURI().toString());
        SelectedItemImage.setImage(SelectedImage);
        File accountFile = new File("images/accountincon.png");
        Image accountFileImage = new Image(accountFile.toURI().toString());
        MyAccountImageView.setImage(accountFileImage);
        File CartFile = new File("images/cart.png");
        Image CartImage = new Image(CartFile.toURI().toString());
        CartImageView.setImage(CartImage);
        ObservableList<String> categories = FXCollections.observableArrayList();
        ObservableList<Integer> quantity = FXCollections.observableArrayList();
        quantity.addAll(1,2,3,4,5);
        try {
            DatabaseConnection con = new DatabaseConnection();
            Connection conDb = con.getConnection();
            Statement statement = conDb.createStatement();
            for (int i = 1; i <= 4; i++) {
                ResultSet resultSet = statement.executeQuery("Select CATNAME from category where CATID = 'Cat00" + i + "'");
                while (resultSet.next()) {
                    categories.add(resultSet.getString("CATNAME"));
                }
            }
            QuantityComboBox.setItems(quantity);
            QuantityComboBox.setValue(quantity.get(0));
            CategoryComboBox.setItems(categories);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}