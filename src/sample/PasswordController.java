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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class PasswordController
{
    @FXML private Button ResetBtn;
    @FXML private Label ChangePasswordLbl,OldPasswordLbl,NewPasswordLbl,ConfirmpasswordLbl,FinalMsgsLbl;
    @FXML private TextField OldPasswordTxt,NewPasswordTxt,ConfirmpasswordTxt;
}

public  void setResetBtnOnAction(ActionEvent event)throws Exception
    {
        String OldPassword = OldPasswordLbl.getText();
        String NewPassword = NewPasswordLbl.getText();
        String Confirmpassword = ConfirmpasswordLbl.getText();

        if (OldPassword != PassTxt.getText())
        {
            FinalMsgsLbl.setText("Old password is incorrect");
        }
        else {
            if (NewPassword() != Confirmpassword())
            {
                FinalMsgsLbl.setText("Please make sure both passwords match.");
            }
            else{
            if(validateUser()) {
                FinalMsgsLbl.setText("Password Changed");
                Stage stage = (Stage) ResetBtn.getScene().getWindow();
                stage.close();
                Stage StoreStage = new Stage();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MyAccount.fxml")));
                StoreStage.setTitle("Electronic Fellows");
                StoreStage.initStyle(StageStyle.DECORATED);
                StoreStage.setScene(new Scene(root, 600, 400));
                StoreStage.show();
            }

            }
    }

    public boolean validateUser() {

             String getdeatils = "Select * from consumer where password = '"+PassTxt.getText()+"'";
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
                    LoggedinConsumer.setCid(Consumer.getInt(1));
                    LoggedinConsumer.setName(Consumer.getString(2));
                    LoggedinConsumer.setPhoneno(Consumer.getInt(3));
                    LoggedinConsumer.setEmailid(Consumer.getString(4));
                    LoggedinConsumer.setPassword(Consumer.getString(5));
                    return true;
                }
                else {
                    return false;
                }
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return false;

    }

