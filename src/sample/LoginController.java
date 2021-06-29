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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File branding = new File("images/attachment_101759249.png");
        File lock = new File("images/lock-2752132-2284949.png");
        Image lockimage = new Image(lock.toURI().toString());
        lockImageView.setImage(lockimage);
        Image brandingimage = new Image(branding.toURI().toString());
        brandingImageView.setImage(brandingimage);


    }

    public void setLoginBtn(ActionEvent event) {
        if (UserNameTxt.getText().isBlank() == true && PassTxt.getText().isBlank() == true) {
            MessageLbl.setText("Invalid Credentials");
        } else {
            validateLogin();
        }
    }

    public void validateLogin() {

        String verifyLogin = "SELECT count(1) from consumer where emailid = '"+UserNameTxt.getText() + "' AND password = '" + PassTxt.getText() + "'";
        try {
            DatabaseConnection con = new DatabaseConnection();
            Connection conDb = con.getConnection();
            Statement statement = conDb.createStatement();
            ResultSet query = statement.executeQuery(verifyLogin);
            while (query.next())
            {
                if (query.getInt(1)==1)
                {
                    MessageLbl.setText("Login SuccessFull");
                }
                else {
                    MessageLbl.setText("Invalid Credentials!");
                }
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

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
