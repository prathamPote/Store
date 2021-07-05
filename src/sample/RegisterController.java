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
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class RegisterController implements Initializable
{
        @FXML private ImageView UserImageView;
        @FXML private Button RegisterBtn,LoginBtn;
        @FXML private Label passwordMsgLbl,FinalMsgsLbl;
        @FXML private TextField NameTxt,EmailTxt,PhoneTxt,Pass1Txt,Pass2Txt;

        public void initialize(URL url, ResourceBundle resourceBundle)
        {
            File UserImgFile = new File("images/download.png");
            Image UserImg = new Image(UserImgFile.toURI().toString());
            UserImageView.setImage(UserImg);
        }
        public  void setRegisterBtnOnAction(ActionEvent event)throws Exception
        {
            String name = NameTxt.getText();
            String email = EmailTxt.getText();
            String phone = PhoneTxt.getText();
            String pass1 = Pass1Txt.getText();
            String pass2 = Pass2Txt.getText();

            if (name.isBlank() == true || email.isBlank()==true|| phone.isBlank()==true||pass1.isBlank()==true|| pass2.isBlank()==true)
            {
                FinalMsgsLbl.setText("Please fill all the details");
            }
            else {
                    if (RegisterUser())
                    {
                        FinalMsgsLbl.setText("Registration SuccessFull");
                    }
                    else
                    {
                        FinalMsgsLbl.setText("Check your passwords");
                    }
                Stage stage = (Stage) RegisterBtn.getScene().getWindow();
                stage.close();
                Stage Login = new Stage();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
                Login.setTitle("Electronic Fellows");
                Login.initStyle(StageStyle.TRANSPARENT);
                Login.setScene(new Scene(root, 600, 400));
                Login.show();
            }
        }
        public Boolean RegisterUser() {
            String name = NameTxt.getText();
            String email = EmailTxt.getText();
            String phone = PhoneTxt.getText();
            String pass1 = Pass1Txt.getText();
            String pass2 = Pass2Txt.getText();
            if (pass1.compareTo(pass2) == 0) {
                String registerUser = "Insert into consumer values (" + ThreadLocalRandom.current().nextInt() + ",'" + name + "'," + phone + ",'" + email + "','" + pass1 + "')";
                try {
                    DatabaseConnection con = new DatabaseConnection();
                    Connection conDb = con.getConnection();
                    Statement statement = conDb.createStatement();
                    ResultSet query = statement.executeQuery(registerUser);
                    while (query.next()) {
                        statement.executeQuery("commit");
                        return true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            return false;
        }
        public void setLoginBtnOnAction(ActionEvent event)throws Exception
        {
            Stage stage = (Stage) LoginBtn.getScene().getWindow();
            stage.close();
            Stage LoginStage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
            LoginStage.setTitle("Electronic Fellows");
            LoginStage.initStyle(StageStyle.TRANSPARENT);
            LoginStage.setScene(new Scene(root, 600, 400));
            LoginStage.show();
        }
        public void checkPassOnAction()
        {
            if (Pass1Txt.getText().compareTo(Pass2Txt.getText()) != 0)
            {
                passwordMsgLbl.setText("Passwords don't match");
            }
            else
            {
                passwordMsgLbl.setText("You're good to go");
            }
        }

}
