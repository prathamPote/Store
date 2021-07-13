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
import javafx.scene.input.MouseEvent;
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
    private VBox Accountinfo,ProfileSettingsInfo,ProfileEditColumn;
    @FXML
    private TextField NameTxt,DOBTxt,PhoneTxt,EmailTxt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProfileEditColumn.setVisible(false);
        File ProfileImgFile = new File("images/Sample_User_Icon.png");
        Image ProfileImg = new Image(ProfileImgFile.toURI().toString());
        ProfileImage.setImage(ProfileImg);
        File MyOrdersImgFile = new File("images/MY ORDER.png");
        Image MyOrdersImg = new Image(MyOrdersImgFile.toURI().toString());
        MyOrdersImage.setImage(MyOrdersImg);
        File MyAccountImgFile = new File("images/accountincon.png");
        Image MyAccountImg = new Image(MyAccountImgFile.toURI().toString());
        MyAccountImageView.setImage(MyAccountImg);
        File PasswordImgFile = new File("images/passwordicon.png");
        Image PasswordImg = new Image(PasswordImgFile.toURI().toString());
        PasswordImage.setImage(PasswordImg);
        File privacyfile = new File("images/download (1).png");
        Image PrivacyImage = new Image(privacyfile.toURI().toString());
        PrivacyPolicyImage.setImage(PrivacyImage);
        NameTxt.setText(LoginController.LoggedinConsumer.getName());
        EmailTxt.setText(LoginController.LoggedinConsumer.getEmailid());
        PhoneTxt.setText(Integer.toString(LoginController.LoggedinConsumer.getPhoneno()));
    }
    public  void setSaveChangesBtnOnAction(ActionEvent event)throws Exception
    {
        String name = NameTxt.getText();
        String DOB = DOBTxt.getText();
        String phone = PhoneTxt.getText();
        String Email = EmailTxt.getText();


        if (name.isBlank() && DOB.isBlank() && phone.isBlank() && Email.isBlank())
        {
            FinalMsgsLbl.setText("All fields are blank!");
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

        }
    }
    public Boolean AccountHolder() {
        String name = NameTxt.getText();
        String DOB = DOBTxt.getText();
        String phone = PhoneTxt.getText();
        String Email = EmailTxt.getText();
        String registerUser = "update consumer set cname = '"+name+"',cdob = to_date('"+ DOB + "','dd/mm/yyyy'), phoneno = '"+phone+"',Emailid='"+Email+"' "+" where CID= '"+LoginController.LoggedinConsumer.getCid()+"'";
        if(!name.isBlank() || !DOB.isBlank() || !phone.isBlank() || !Email.isBlank())
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
        ProfileEditColumn.setVisible(true);
    }
    public void OnOrdersAction(MouseEvent event)throws Exception
    {
        Stage registerStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MyOrders.fxml")));
        registerStage.setTitle("Electronic Fellows");
        registerStage.initStyle(StageStyle.TRANSPARENT);
        registerStage.setScene(new Scene(root, 720, 600));
        registerStage.show();
    }
}