package com.tiaretdevgroup.openhackathon.java.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.tiaretdevgroup.openhackathon.java.dao.UserDao;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AuthontificationController implements Initializable {

    /* Start Login Part */
    @FXML
    private AnchorPane authontificationPane;
    
    @FXML
    private JFXTextField usernameSignInField;
    @FXML
    private JFXPasswordField passwordSignInField;
    
    private JFXSnackbar toastErrorMsg;
    
    /* End Login Part */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        toastErrorMsg = new JFXSnackbar(authontificationPane);
    }
    
    @FXML
    private void onSignIn() {
        if (usernameSignInField.getText().trim().isEmpty()) {
            toastErrorMsg.show("Username is Empty !", 1500);
            return;
        }
        if (!usernameSignInField.getText().trim().matches("[A-Za-z0-9_]{4,}")) {
            toastErrorMsg.show("Username not valid !", 1500);
            return;
        }
        

        if (passwordSignInField.getText().trim().isEmpty()) {
            toastErrorMsg.show("Password is Empty !", 1500);
            return;
        }
        if (passwordSignInField.getText().trim().length() < 4) {
            toastErrorMsg.show("Password not valid !", 1500);
            return;
        }
        
        int status = new UserDao().checkUsernameAndPassword(usernameSignInField.getText().trim().toLowerCase(), passwordSignInField.getText().toLowerCase());
        switch (status) {
            case -1:
                toastErrorMsg.show("Connection Failed !", 1500);
                break;
            case 1:
                toastErrorMsg.show("Username and/or password incorrect !", 1500);
                break;
            default:
                Stage stage;
                HBox rootSystem = null;
                //get reference - stage
                stage = (Stage) usernameSignInField.getScene().getWindow();
                try {
                    //load up other FXML document
                    rootSystem = FXMLLoader.load(getClass().getResource("/com/tiaretdevgroup/openhackathon/resources/views/System.fxml"));
                } catch (IOException ex) {
                    System.out.println("Error msg: " + ex.getMessage());
                }
                //create a new scene with root and set the stage
                Scene scene = new Scene(rootSystem);
                stage.setScene(scene);
                stage.show();
                stage.setX(100);
                stage.setY(20);
                break;
        }
        
    }

}
