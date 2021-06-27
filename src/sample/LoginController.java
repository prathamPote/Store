package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
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
    public void validateLogin()
    {

    }

    public void setRegisterBtn(ActionEvent event) {
        Stage stage = (Stage) RegisterBtn.getScene().getWindow();
        stage.close();
    }
}
