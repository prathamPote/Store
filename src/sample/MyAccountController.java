package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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

public class MyAccountController implements Initializable {
    @FXML
    private ImageView ProfileImage,MyOrdersImage, MyAccountImageView, PasswordImage,PrivacyPolicyImage;
    @FXML
    private Label ProfileLabel,MyOrdersLabel,PasswordLabel,PrivacyPolicyLabel,NameLabel,DateofBirthLabel,PhoneLabel,EmailLabel,ProfileSettingsLabel,ProfileDetailsLabel,ContactInfoLabel,FinalMsgsLbl;
    @FXML
    private Button EditProfileButton,SaveChangesButton;
    @FXML
    private VBox Accountinfo,ProfileSettingsInfo;
    @FXML
    private TextField NameTxt,DOBTxt,PhoneTxt,EmailTxt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File ProfileImgFile = new File("images/Sample_User_Icon.png");
        Image ProfileImg = new Image(ProfileImgFile.toURI().toString());

        File MyOrdersImgFile = new File("images/MY ORDER.png");
        Image MyOrdersImg = new Image(MyOrdersImgFile.toURI().toString());

        File MyAccountImgFile = new File("images/accountincon.png");
        Image MyAccountImg = new Image(MyAccountImgFile.toURI().toString());

        File PasswordImgFile = new File("images/passwordicon.png");
        Image PasswordImg = new Image(PasswordImgFile.toURI().toString());

    }
    public  void setSaveChangesBtnOnAction(ActionEvent event)throws Exception
    {
        String name = NameTxt.getText();
        String DOB = DOBTxt.getText();
        String phone = PhoneTxt.getText();
        String Email = EmailTxt.getText();


        if (name.isBlank() == true || DOB.isBlank()==true|| phone.isBlank()==true||Email.isBlank()==true)
        {
            FinalMsgsLbl.setText("No Changes Done");
        }
        else {
            if (AccountHolder())
            {
                FinalMsgsLbl.setText("Changes Saved");
            }
            else
            {
                FinalMsgsLbl.setText("No Changes Done");
            }
            Stage stage = (Stage) SaveChangesButton.getScene().getWindow();
            stage.close();
            Stage MyAccount = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MyAccount.fxml")));
            MyAccount.setTitle("Profile Settings");
            MyAccount.initStyle(StageStyle.TRANSPARENT);
            MyAccount.setScene(new Scene(root, 555, 595));
            MyAccount.show();
        }
    }
    public Boolean AccountHolder() {
        String name = NameTxt.getText();
        String DOB = DOBTxt.getText();
        String phone = PhoneTxt.getText();
        String Email = EmailTxt.getText();
        String registerUser = "update consumer set (" + ThreadLocalRandom.current().nextInt() + ",'" + name + "'," + DOB + ",'" +"'," + phone + ",'" + Email + "')"+ " where CID=+";
        if(name.isBlank() != true || DOB.isBlank() !=true|| phone.isBlank() !=true||Email.isBlank() !=true)
        {
            try {
                DatabaseConnection con = new DatabaseConnection();
                Connection conDb = con.getConnection();
                Statement statement = conDb.createStatement();
                ResultSet query = statement.executeQuery(registerUser);
                while (query.next()) {
                    statement.executeQuery("commit");
                    return true;
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return false;
    }
    public void setEditProfileBtnOnAction(ActionEvent event)throws Exception
    {

    }
}