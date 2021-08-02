package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MyOrdersController implements Initializable
{
    @FXML
    private  ImageView MyOrdersImage;
    @FXML
    private Button CloseBtn;
    @FXML
    private Label OrderIdLbl,OrderDateLbl,OrderValueLbl,OrderStatusLbl,WelcomeLbl;
    @FXML
    private GridPane gridpane;
    @FXML
    private ComboBox OrderComboBox;
    @FXML
    private SplitPane splitpane;

    @Override
    public void initialize( URL url, ResourceBundle resourceBundle)
    {

        File MyOrdersImgFile = new File("images/MY ORDER.png");
        Image MyOrdersImg = new Image(MyOrdersImgFile.toURI().toString());
        MyOrdersImage.setImage(MyOrdersImg);
        System.out.println(splitpane.toString());
        splitpane.setVisible(true);
        OrderIdLbl.setVisible(false);OrderDateLbl.setVisible(false);OrderStatusLbl.setVisible(false);OrderValueLbl.setVisible(false);
        WelcomeLbl.setText("Hey "+LoginController.LoggedinConsumer.getName());
        try {
            DatabaseConnection connection = new DatabaseConnection();
            Connection con = connection.getConnection();
            Statement orders = con.createStatement();
            ObservableList<String> orderslist =FXCollections.observableArrayList();
            ResultSet order = orders.executeQuery("Select orderid from orders where cid = "+LoginController.LoggedinConsumer.getCid());
            while (order.next())
            {
                orderslist.add(order.getString(1));
            }
            OrderComboBox.setItems(orderslist);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void CloseBtnOnAction(ActionEvent event)
    {
        Stage stage = (Stage) CloseBtn.getScene().getWindow();
        stage.close();
    }
    public void ComboBoxOnAction(ActionEvent event)throws Exception
    {
        String selection = OrderComboBox.getSelectionModel().getSelectedItem().toString();
        DatabaseConnection connection = new DatabaseConnection();
        Connection con = connection.getConnection();
        Statement orders = con.createStatement();
        ResultSet Orders = orders.executeQuery("Select * from orderedproducts where orderid = "+selection);
        Statement details = con.createStatement();
        ResultSet Details = details.executeQuery(" select distinct o.orderid,o.orderstatus,o.orderdate,(select sum(productprice)from orderedproducts p where orderid ="+selection+") from orders o join orderedproducts p on(o.orderid=p.orderid) where o.orderid="+selection+" and p.orderid="+selection);
        Details.next();
        OrderValueLbl.setText("Order Value :"+Details.getFloat(4));
        OrderValueLbl.setVisible(true);
        OrderStatusLbl.setText(Details.getString(2));
        OrderStatusLbl.setVisible(true);
        OrderDateLbl.setText("Order date: "+Details.getDate(3).toString());
        OrderDateLbl.setVisible(true);
        OrderIdLbl.setText("Order id: "+Details.getInt(1));
        OrderIdLbl.setVisible(true);
        List<CartProduct> products = new ArrayList<>();
        while (Orders.next())
        {
            CartProduct product = new CartProduct();
            product.setProductid(Orders.getString(2));
            product.setName(Orders.getString(3));
            product.setPrice(Orders.getDouble(4));
            product.setQuantity(Orders.getInt(5));
            int imgid = Integer.parseInt(product.getProductid().substring(3));
            product.setImgsrc("images/ProductImages/Mobile Phones/"+imgid+".png");
            products.add(product);
        }
        gridpane.getChildren().clear();
        int column = 0;
        int row =1;
        for (int i = 0; i < products.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("orderitem.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            Orderitemcontrolle itemController = fxmlLoader.getController();
            itemController.setData(products.get(i));
            if (column == 2) {
                column = 0;
                row++;
            }
            System.out.println(products.get(i).getName());
            gridpane.add(anchorPane, column++, row);
            GridPane.setValignment(anchorPane, VPos.CENTER);
            GridPane.setMargin(anchorPane, new Insets(10.0f, 10.0f, 10.0f, 10.0f));
        }
        products.clear();

    }
    public void printInvoice()
    {

    }
}
