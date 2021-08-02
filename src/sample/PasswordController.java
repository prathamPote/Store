package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class PasswordController
{
    @FXML private Button ResetBtn;
    @FXML private Label PassMsglbl;
    @FXML private PasswordField OldPasswordTxt,NewPasswordTxt,ConfirmpasswordTxt;

    public void setResetBtnOnAction(ActionEvent event) throws Exception {
        String OldPassword = OldPasswordTxt.getText();
        String NewPassword = NewPasswordTxt.getText();
        String Confirmpassword = ConfirmpasswordTxt.getText();
        DatabaseConnection con = new DatabaseConnection();
        Connection conn = con.getConnection();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("Select password from consumer where cid = "+LoginController.LoggedinConsumer.getCid());
        rs.next();
        if (OldPassword.compareTo(rs.getString(1))!=0) {
            PassMsglbl.setText("Old password is incorrect");
        } else {
            if (NewPassword.compareTo(Confirmpassword)!=0) {
                PassMsglbl.setText("Please make sure both passwords match.");
            }
            else if(NewPassword.compareTo(rs.getString(1))==0)
            {
                PassMsglbl.setText("New password cannot be same as the old");
            }
                else {
                if (validateUser()) {
                    PassMsglbl.setText("Password Changed");
                }

            }
        }
    }
    public void Passcheck()
    {
        if (NewPasswordTxt.getText().compareTo(ConfirmpasswordTxt.getText())!=0) {
            PassMsglbl.setText("Passwords do not match!");
        }
        else
        {
            PassMsglbl.setText("You're good to go");
            PassMsglbl.setTextFill(Color.web("#f58f8f"));
        }

    }

        public boolean validateUser() {
            String getdeatils = "update consumer set password = '"+ConfirmpasswordTxt.getText()+"'where cid ="+LoginController.LoggedinConsumer.getCid() ;
            try {
                DatabaseConnection con = new DatabaseConnection();
                Connection conDb = con.getConnection();
                Statement statement = conDb.createStatement();
                ResultSet Consumer = statement.executeQuery(getdeatils);
                if (Consumer.next()) {
                        return true;
                    }
                else
                    {
                        return false;
                    }
            }
            catch (Exception throwables) {
                throwables.printStackTrace();
            }
            return false;

        }
}

