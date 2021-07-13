package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MyOrdersController implements Initializable
{
    @FXML
    private  ImageView MyOrdersImage;
    @FXML
    private Button CloseBtn;
    @Override
    public void initialize( URL url, ResourceBundle resourceBundle)
    {
        File MyOrdersImgFile = new File("images/MY ORDER.png");
        Image MyOrdersImg = new Image(MyOrdersImgFile.toURI().toString());
        MyOrdersImage.setImage(MyOrdersImg);
    }
    public void CloseBtnOnAction(ActionEvent event)
    {
        Stage stage = (Stage) CloseBtn.getScene().getWindow();
        stage.close();
    }
}
