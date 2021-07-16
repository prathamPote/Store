package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;
public class LoginController implements Initializable {
    @FXML
    private Button RegisterBtn;
    @FXML
    private Button LoginBtn;
    @FXML
    private Label MessageLbl;
    @FXML
    private ImageView brandingImageView, lockImageView;
    @FXML
    private TextField UserNameTxt, PassTxt;

    public static Consumer LoggedinConsumer = new Consumer();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File branding = new File("images/attachment_101759249.png");
        File lock = new File("images/lock-2752132-2284949.png");
        Image lockimage = new Image(lock.toURI().toString());
        lockImageView.setImage(lockimage);
        Image brandingimage = new Image(branding.toURI().toString());
        brandingImageView.setImage(brandingimage);

    }

    public void setLoginBtn(ActionEvent event)throws Exception {
        if (UserNameTxt.getText().isBlank() == true && PassTxt.getText().isBlank() == true) {
            MessageLbl.setText("Invalid Credentials");
        } else {
            if(validateLogin())
            {
                Stage stage = (Stage) LoginBtn.getScene().getWindow();
                stage.close();
                Stage StoreStage = new Stage();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Store.fxml")));
                StoreStage.setTitle("Electronic Fellows");
                StoreStage.initStyle(StageStyle.DECORATED);
                StoreStage.setScene(new Scene(root, 1164, 636));
                StoreStage.show();
            }


        }
    }

    public boolean validateLogin() {

        String verifyLogin = "SELECT count(1) from consumer where emailid = '"+UserNameTxt.getText() + "' AND password = '" + PassTxt.getText() + "'";
        String getdeatils = "Select * from consumer where emailid='"+UserNameTxt.getText()+"' and password = '"+PassTxt.getText()+"'";
        try {
            DatabaseConnection con = new DatabaseConnection();
            Connection conDb = con.getConnection();
            Statement statement = conDb.createStatement();
            Statement consumer = conDb.createStatement();
            ResultSet Consumer = consumer.executeQuery(getdeatils);
            Consumer.next();
            ResultSet query = statement.executeQuery(verifyLogin);
            while (query.next())
            {
                if (query.getInt(1)==1)
                {
                    MessageLbl.setText("Login SuccessFull");
                    LoggedinConsumer.setCid(Consumer.getInt(1));
                    LoggedinConsumer.setName(Consumer.getString(2));
                    LoggedinConsumer.setPhoneno(Consumer.getInt(3));
                    LoggedinConsumer.setEmailid(Consumer.getString(4));
                    LoggedinConsumer.setPassword(Consumer.getString(5));
                    LoggedinConsumer.setDOB(Consumer.getDate(6).toString());
                    return true;
                }
                else {
                    MessageLbl.setText("Invalid Credentials!");
                    return false;
                }
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return false;

    }

    public void setRegisterBtn(ActionEvent event) throws Exception {
        Stage stage = (Stage) RegisterBtn.getScene().getWindow();
        stage.close();
        Stage registerStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Register.fxml")));
        registerStage.setTitle("Electronic Fellows");
        registerStage.initStyle(StageStyle.TRANSPARENT);
        registerStage.setScene(new Scene(root, 600, 400));
        registerStage.show();
    }
}
