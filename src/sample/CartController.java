package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class CartController implements Initializable {

    public static List<CartProduct> Cartproducts1 = new ArrayList<>();
    public static  List<CartProduct> Cartprodcutsfinal = new ArrayList<>();
    public  List<CartProduct> Cartproducts2 = new ArrayList<>();
    @FXML
    private BorderPane borderpane;
    @FXML
    private Label CartLbl, AddressLbl, Pincodelbl, CityLbl, StateLbl, ShipAdrLbl, PayOptLbl, OrderMsgsLbl;
    @FXML
    private TextField AddressTxt, PincodeTxt, CityTxt, StateTxt;
    @FXML
    private Button ConOrdBtn;
    @FXML
    private RadioButton PayoptBtn1, PayoptBtn2, PayoptBtn3, PayoptBtn4;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane gridpane;
    private RemoveListner removeListner;
    @FXML private Label TotalPriceLbl;

    public int orderid= ThreadLocalRandom.current().nextInt(1,100);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Cartproducts1.addAll(getData());
        int count1 = 0;
        try {
            if (count1 != 0)
                gridpane.getChildren().clear();
            int column = 0;
            int row = 1;
            TotalPriceLbl.setVisible(false);
            for (CartProduct cartProduct : Cartproducts1) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("CartItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                CartItemController itemController = fxmlLoader.getController();
                removeListner = new RemoveListner() {
                    @Override
                    public void OnRemoveListner(CartProduct product,AnchorPane anchorPane1)throws Exception {
                        gridpane.getChildren().remove(anchorPane1);
                        DatabaseConnection con = new DatabaseConnection();
                        Connection conDb = con.getConnection();
                        Statement statement = conDb.createStatement();
                        ResultSet remove = statement.executeQuery("Delete from cart where productid = '"+product.getProductid()+"'");
                        remove.next();
                        statement.executeQuery("Commit");
                    }
                };
                itemController.setData(cartProduct,removeListner,anchorPane);
                if (column == 1) {
                    column = 0;
                    row++;
                }
                gridpane.add(anchorPane, column++, row);
                GridPane.setValignment(anchorPane, VPos.CENTER);
                GridPane.setMargin(anchorPane, new Insets(10.0f, 10.0f, 10.0f, 10.0f));
            }
            Cartproducts2.addAll(Cartproducts1);
            Cartproducts1.clear();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
        private static double Totalprice;
        public void OnrefreshAction(ActionEvent event)throws Exception
        {
            DatabaseConnection con = new DatabaseConnection();
            Connection conDb = con.getConnection();
            Statement statement = conDb.createStatement();
            ResultSet rs = statement.executeQuery("select sum(price) from cart");
            rs.next();
            Totalprice=rs.getDouble(1);
            TotalPriceLbl.setText(String.valueOf(rs.getDouble(1)));
            TotalPriceLbl.setVisible(true);
        }
        public void OnConfirmAction(ActionEvent event)throws Exception
        {
            if (UserAddress()==true)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Electronic Fellows");
                alert.setContentText("Order Confirmed");
                alert.show();
                DatabaseConnection con = new DatabaseConnection();
                Connection conDb = con.getConnection();
                Statement statement = conDb.createStatement();
                Statement myorders = conDb.createStatement();
                String orderstatus ="Order Confirmed";
                int cid = LoginController.LoggedinConsumer.getCid();
                ResultSet rs = myorders.executeQuery("insert into orders values("+orderid+",'Order confirmed',(select current_date from dual),"+cid+")");
                myorders.executeQuery("commit");
                statement.executeQuery("truncate table cart");
                statement.executeQuery("commit");
                PreparedStatement orders = conDb.prepareStatement("insert into orderedproducts values(?,?,?,?,?)");
                Statement count = conDb.createStatement();
                ResultSet countrs = count.executeQuery("Select count(*) from cart");
                countrs.next();
                Statement cart = conDb.createStatement();
                ResultSet Cartitems = cart.executeQuery("Select * from cart");
                Cartprodcutsfinal = Cartproducts2.stream().distinct().collect(Collectors.toList());
                for (int i = 0; i< Cartprodcutsfinal.size(); i++)
                {
                    orders.setInt(1,orderid);
                    orders.setString(2,Cartprodcutsfinal.get(i).getProductid());
                    orders.setString(3,Cartprodcutsfinal.get(i).getName());
                    orders.setFloat(4,Float.parseFloat(String.valueOf(Cartprodcutsfinal.get(i).getPrice())));
                    orders.setInt(5,Cartprodcutsfinal.get(i).getQuantity());
                    ResultSet resultSet = orders.executeQuery();
                    resultSet.next();
                    statement.executeQuery("commit");
                }

            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Electronic Fellows");
                alert.setContentText("Order Failed");
                alert.show();
            }
        }

    private List<CartProduct> getData() {
        List<CartProduct> products = new ArrayList<>();

        try {
            DatabaseConnection con = new DatabaseConnection();
            Connection conDb = con.getConnection();
            Statement statement = conDb.createStatement();
            Statement cart = conDb.createStatement();
            ResultSet Cart = cart.executeQuery("Select * from cart");
            ResultSet count = statement.executeQuery("Select count(*) from cart");
            count.next();
            if (count.getInt(1) == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Cart is Empty, Add products");
                alert.setTitle("Electronic Fellows");
                alert.show();
            }
            while (Cart.next()) {
                CartProduct product = new CartProduct();
                product.setProductid(Cart.getString(1));
                product.setName(Cart.getString(2));
                product.setQuantity(Cart.getInt(3));
                product.setPrice(Cart.getDouble(4));
                int imgid = Integer.parseInt(Cart.getString("Productid").substring(3));
                product.setImgsrc("images/ProductImages/Mobile Phones/"+imgid+".png");
                products.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public Boolean UserAddress() {
        String addressLine = AddressTxt.getText();
        String pincode = PincodeTxt.getText();
        String city = CityTxt.getText();
        String state = StateTxt.getText();
        String payment;
        if (addressLine.isEmpty()||pincode.isEmpty()||city.isEmpty()||state.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please fill all the fields");
            alert.setTitle("Electronic Fellows");
            alert.show();
        }
        String UserAddress = "Insert into address values('"+addressLine+"','"+pincode+"','"+city+"','"+state+"',"+LoginController.LoggedinConsumer.getCid()+")";
        if (PayoptBtn1.isSelected())
             payment = "Insert into payments values("+ThreadLocalRandom.current().nextInt(1,100)+","+Totalprice+",'Cash On Delivery',(select current_date from dual),"+LoginController.LoggedinConsumer.getCid()+")";
        else if (PayoptBtn2.isSelected())
            payment = "Insert into payments values("+ThreadLocalRandom.current().nextInt(1,100)+","+Totalprice+",'Gpay/Phonepe',(select current_date from dual),"+LoginController.LoggedinConsumer.getCid()+")";
        else if (PayoptBtn3.isSelected())
            payment = "Insert into payments values("+ThreadLocalRandom.current().nextInt(1,100)+","+Totalprice+",'Credit/Debitcard',(select current_date from dual),"+LoginController.LoggedinConsumer.getCid()+")";
       else if(PayoptBtn4.isSelected())
            payment = "Insert into payments values("+ThreadLocalRandom.current().nextInt(1,100)+","+Totalprice+",'NetBanking',(select current_date from dual),"+LoginController.LoggedinConsumer.getCid()+")";
       else {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Electronic Fellows");
           alert.setContentText("Select payment option");
           alert.show();
           return false;
        }
        try {
            DatabaseConnection con = new DatabaseConnection();
            Connection conDb = con.getConnection();
            Statement statement = conDb.createStatement();
            ResultSet query = statement.executeQuery(UserAddress);
            Statement paymentstatement = conDb.createStatement();
            ResultSet pay = paymentstatement.executeQuery(payment);
            pay.next();
            while (query.next()) {
                statement.executeQuery("commit");
                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }
}

