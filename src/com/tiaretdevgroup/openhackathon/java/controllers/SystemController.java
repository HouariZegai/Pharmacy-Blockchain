package com.tiaretdevgroup.openhackathon.java.controllers;

import com.jfoenix.controls.JFXDialog;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.octicons.OctIconView;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SystemController implements Initializable {

    @FXML
    private HBox parent;
    @FXML
    private AnchorPane holderPane;
    @FXML
    private VBox sidebar; // Menu Left of my System

    /* Start Home Part */
    @FXML
    private VBox homePane;
    @FXML
    private VBox sellBox, tracBox, manageAccountBox;

    /* End Home Part */
    
    @FXML // this pane using for the Dialog of about
    private StackPane rightPane;
    
    private StackPane sellPane, tracPane;
    private VBox guidePane, manageAccountPane;

    /* Start Icon Option */
    @FXML
    private HBox boxHome, boxSell, boxTrac, boxAccount, boxGuide;
    @FXML
    private FontAwesomeIconView iconHome, iconAccount;
    @FXML
    private MaterialDesignIconView iconGuide, iconAbout;
    @FXML
    private OctIconView iconSell, iconTrac;
    
    /* End Icon Option */

    public static JFXDialog aboutDialog; // this for show the about Dialog

    @FXML
    private ImageView imgSlider;
    @FXML
    private Label dateLabel;
    // counter Number of image using in slider
    private final byte NUMBER_IMAGE_SLIDER = 3;
    private int counter = 1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        styleBox(0); // for changing the color of Home Icon

        // Initialize the image (to fill parent)
        imgSlider.fitWidthProperty().bind(holderPane.widthProperty());
        imgSlider.fitHeightProperty().bind(holderPane.heightProperty());

        // Make auto change the slider in duration
        sliderAutoChangePictures();

        // Load FXML in variable
        try {
            manageAccountPane = FXMLLoader.load(getClass().getResource("/com/tiaretdevgroup/openhackathon/resources/views/ManageAccount.fxml"));
            guidePane = FXMLLoader.load(getClass().getResource("/com/tiaretdevgroup/openhackathon/resources/views/Guide.fxml"));
            // load Dialog
            AnchorPane aboutPane = FXMLLoader.load(getClass().getResource("/com/tiaretdevgroup/openhackathon/resources/views/About.fxml"));
            aboutDialog = new JFXDialog(rightPane, aboutPane, JFXDialog.DialogTransition.TOP);

        } catch (IOException ex) {
            Logger.getLogger(SystemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sliderAutoChangePictures() {
        // Make auto change the slider in duration

        Timeline sliderTimer = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            FadeTransition ft = new FadeTransition();
            ft.setNode(imgSlider);
            ft.setDuration(new Duration(4000));
            ft.setFromValue(1.0);
            ft.setToValue(0.3);
            ft.setCycleCount(0);
            ft.setAutoReverse(true);
            ft.play();
            imgSlider.setImage(new Image("com/tiaretdevgroup/openhackathon/resources/images/slider/" + counter + ".png"));
            if (++counter > NUMBER_IMAGE_SLIDER) {
                counter = 1;
            }
        }),
                new KeyFrame(Duration.seconds(4))
        );
        sliderTimer.setCycleCount(Animation.INDEFINITE);
        sliderTimer.play();

        // initialize Clock Showing in home
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy      HH:mm:ss");
            Date date = new Date();
            dateLabel.setText(dateFormat.format(date));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @FXML
    private void expandSidebar() {
        sidebar.setPrefWidth((sidebar.getWidth() == 50) ? 200 : 50);
    }

    private void setNode(Node node) {
        homePane.setVisible(false);
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);
        sidebar.setPrefWidth(50);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    private void homeClicked() {
        styleBox(0);
        homePane.setVisible(true);
        sidebar.setPrefWidth(50);
    }

    @FXML
    private void sellClicked() {
        try {
            sellPane = FXMLLoader.load(getClass().getResource("/com/tiaretdevgroup/openhackathon/resources/views/Sell.fxml"));
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        styleBox(1);
        setNode(sellPane);
    }

    @FXML
    private void traceabilityClicked() {
        styleBox(2);
        try {
            tracPane = FXMLLoader.load(getClass().getResource("/com/tiaretdevgroup/openhackathon/resources/views/Trac.fxml"));
            setNode(tracPane);
        } catch (IOException ex) {
            Logger.getLogger(SystemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accountClicked() {
        styleBox(3);
        setNode(manageAccountPane);
    }

    @FXML
    private void logoutClicked() {
        Stage stage;
        Parent root = null;
        //get reference - stage
        stage = (Stage) holderPane.getScene().getWindow();
        try {
            //load up other FXML document
            root = FXMLLoader.load(getClass().getResource("/com/tiaretdevgroup/openhackathon/resources/views/Authontification.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(SystemController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(475.0);
        stage.setY(33.0);
        stage.show();
    }

    @FXML
    private void aboutClicked() {
        if (aboutDialog.isVisible()) {
            return;
        }

        aboutDialog.show();

    }

    @FXML
    private void guideClicked() {
        styleBox(4);
        setNode(guidePane);
    }

    private void styleBox(int index) {
        // This function change the style+color of the menu (Menu Item Selected)
        iconHome.setFill(Paint.valueOf("#4a4949"));
        iconSell.setFill(Paint.valueOf("#4a4949"));
        iconTrac.setFill(Paint.valueOf("#4a4949"));
        iconAccount.setFill(Paint.valueOf("#4a4949"));
        iconGuide.setFill(Paint.valueOf("#4a4949"));

        boxHome.setStyle("-fx-border: 0");
        boxSell.setStyle("-fx-border: 0");
        boxTrac.setStyle("-fx-border: 0");
        boxAccount.setStyle("-fx-border: 0");
        boxGuide.setStyle("-fx-border: 0");

        switch (index) {
            case 0:
                boxHome.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconHome.setFill(Paint.valueOf("#2196f3"));
                break;
            case 1:
                boxSell.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconSell.setFill(Paint.valueOf("#2196f3"));
                break;
            case 2:
                boxTrac.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconTrac.setFill(Paint.valueOf("#2196f3"));
                break;
            case 3:
                boxAccount.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconAccount.setFill(Paint.valueOf("#2196f3"));
                break;
            case 4:
                boxGuide.setStyle("-fx-background-color: #f2f2f2;-fx-border-color: #0078D7;-fx-border-width: 0px 0px 0px 3px;-fx-border-style: solid;");
                iconGuide.setFill(Paint.valueOf("#2196f3"));
                break;
        }
    }

}
