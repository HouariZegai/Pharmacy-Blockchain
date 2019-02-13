package com.tiaretdevgroup.openhackathon.java.controllers;

import blockchain.blocks.MaladyStatus;
import blockchain.factory.BlockchainFactory;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.tiaretdevgroup.openhackathon.java.blockchain.chains.MaladyBlockChain;
import com.tiaretdevgroup.openhackathon.java.models.Disease;
import com.tiaretdevgroup.openhackathon.java.models.DiseaseTable;
import com.tiaretdevgroup.openhackathon.java.models.Malady;
import com.tiaretdevgroup.openhackathon.java.utils.Constants;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class SystemController implements Initializable {

    // Data of Table
    List<Disease> dataOfTable;
    Malady[] maladies;
    /* Main Part */
    @FXML
    private AnchorPane choosePane;

    /* Start Disease Part */
    @FXML
    private VBox salePane;
    @FXML
    private HBox diseasesPane;
    /* Table Part */
    @FXML
    private JFXTreeTableView tableDiseases;
    private JFXTreeTableColumn<DiseaseTable, String> colId, colFirstName, colLastName;
    @FXML
    private VBox rightPane;
    /* Error Msg */
    private JFXSnackbar toastErrorMsg;
    /* Search Part */
    @FXML
    private JFXComboBox<String> comboSearch;
    @FXML
    private JFXTextField fieldSearch;
    /* Personal information part */
    @FXML
    private JFXTextField fieldIdentifier, fieldFirstName, fieldLastName;
    /* Maladies Part */
    @FXML
    private JFXToggleButton toggleSelectAll;

    /* End Disease Part */
    @FXML
    private JFXCheckBox checkMaladies1, checkMaladies2, checkMaladies3, checkMaladies4, checkMaladies5;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load table
        loadJSONMaladies();

        dataOfTable = new ArrayList<>();

        toastErrorMsg = new JFXSnackbar(rightPane);

        initSearch(); // Initialize search
        initCheck();
        initTable(); // Initialize table
        loadTable(); // Load data to table from database

    }

    private void loadJSONMaladies() {
        String urls =  Constants.HOST + "/api/category";

        String productsJSON = null;
        try {
            HttpResponse<JsonNode> node = Unirest.get(urls).asJson();
            productsJSON = node.getBody().toString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        JSONArray arrayData = new JSONArray(productsJSON);

        maladies = new Malady[5];
        for (int i = 0; i < 5; i++) {
            JSONObject obj = arrayData.getJSONObject(i);
            maladies[i] = new Malady(obj.getInt("id"), obj.getString("name"));
        }
    }

    @FXML
    private void onDiseases() {
        diseasesPane.setVisible(true);
        choosePane.setVisible(false);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(diseasesPane);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    private void onSale() {
        salePane.setVisible(true);
        choosePane.setVisible(false);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(salePane);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    private void goToMain() {
        choosePane.setVisible(true);
        salePane.setVisible(false);
        diseasesPane.setVisible(false);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(choosePane);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    private void initSearch() {
        comboSearch.getItems().addAll("Identifier", "First Name", "Last Name");

        fieldSearch.textProperty().addListener(e -> {
            filterTable();
        });
        comboSearch.setOnAction(e -> {
            filterTable();
        });
    }

    private void initCheck() {
        checkMaladies1.setText(maladies[0].getName());
        checkMaladies2.setText(maladies[1].getName());
        checkMaladies3.setText(maladies[2].getName());
        checkMaladies4.setText(maladies[3].getName());
        checkMaladies5.setText(maladies[4].getName());

        toggleSelectAll.setOnAction(e -> {
            if (toggleSelectAll.isSelected()) {
                checkMaladies1.setSelected(true);
                checkMaladies2.setSelected(true);
                checkMaladies3.setSelected(true);
                checkMaladies4.setSelected(true);
                checkMaladies5.setSelected(true);
            } else {
                checkMaladies1.setSelected(false);
                checkMaladies2.setSelected(false);
                checkMaladies3.setSelected(false);
                checkMaladies4.setSelected(false);
                checkMaladies5.setSelected(false);
            }
        });
    }

    private void initTable() {
        colId = new JFXTreeTableColumn<>("IDNETIFIER");
        colId.setPrefWidth(250);
        colId.setCellValueFactory((TreeTableColumn.CellDataFeatures<DiseaseTable, String> param) -> param.getValue().getValue().getIdentifier());

        colFirstName = new JFXTreeTableColumn<>("FIRST NAME");
        colFirstName.setPrefWidth(250);
        colFirstName.setCellValueFactory((TreeTableColumn.CellDataFeatures<DiseaseTable, String> param) -> param.getValue().getValue().getFirstName());

        colLastName = new JFXTreeTableColumn<>("LAST NAME");
        colLastName.setPrefWidth(300);
        colLastName.setCellValueFactory((TreeTableColumn.CellDataFeatures<DiseaseTable, String> param) -> param.getValue().getValue().getLastName());

        tableDiseases.getColumns().addAll(colId, colFirstName, colLastName);
        tableDiseases.setShowRoot(false);

        tableDiseases.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int index = tableDiseases.getSelectionModel().getSelectedIndex(); // selected index
                String selectedIdentifier = colId.getCellData(index);

            }
        });
    }

    private void loadTable() {
        ObservableList<DiseaseTable> diseases = FXCollections.observableArrayList();

        for (Disease dis : dataOfTable) {
            diseases.add(new DiseaseTable(dis.getIdentifier(), dis.getFirstName(), dis.getLastName()));
        }

        final TreeItem<DiseaseTable> treeItem = new RecursiveTreeItem<>(diseases, RecursiveTreeObject::getChildren);
        try {
            tableDiseases.setRoot(treeItem);
        } catch (Exception ex) {
            System.out.println("Error catched !");
        }
    }

    private void filterTable() {
        tableDiseases.setPredicate(new Predicate<TreeItem<DiseaseTable>>() {
            @Override
            public boolean test(TreeItem<DiseaseTable> employee) {
                switch (comboSearch.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        return employee.getValue().getIdentifier().getValue().toLowerCase().contains(fieldSearch.getText().toLowerCase());
                    case 1:
                        return employee.getValue().getFirstName().getValue().toLowerCase().contains(fieldSearch.getText().toLowerCase());
                    case 2:
                        return employee.getValue().getLastName().getValue().toLowerCase().contains(fieldSearch.getText().toLowerCase());
                    default:
                        return employee.getValue().getIdentifier().getValue().toLowerCase().contains(fieldSearch.getText().toLowerCase())
                                || employee.getValue().getFirstName().getValue().toLowerCase().contains(fieldSearch.getText().toLowerCase())
                                || employee.getValue().getLastName().getValue().toLowerCase().contains(fieldSearch.getText().toLowerCase());
                }
            }
        });
    }

    @FXML
    private void onAdd() {

        if (fieldIdentifier.getText().isEmpty() || !fieldIdentifier.getText().matches("[0-9]+") ||
                Integer.parseInt(fieldIdentifier.getText()) < 1) {
            toastErrorMsg.show("Identifier wrong !", 2000);
            return;
        }
        if (fieldFirstName.getText().isEmpty() || !fieldFirstName.getText().trim().matches("[a-zA-Z ]{3,}")) {
            toastErrorMsg.show("First Name wrong !", 2000);
            return;
        }
        if (fieldLastName.getText().isEmpty() || !fieldLastName.getText().trim().matches("[a-zA-Z ]{2,}")) {
            toastErrorMsg.show("Last Name wrong !", 2000);
            return;
        }

        // Insert 
        if (!checkMaladies1.isSelected() && !checkMaladies2.isSelected() && !checkMaladies3.isSelected() &&
                !checkMaladies4.isSelected() && !checkMaladies5.isSelected()) {
            toastErrorMsg.show("Please, select atleast one malady !", 2000);
            return;
        }

        MaladyBlockChain salesBlockChain = BlockchainFactory.INSTANCE.readMaladyBlockChainFromJSONFile();

        if (checkMaladies1.isSelected()) {
            salesBlockChain.addBlock(fieldIdentifier.getText().trim(), String.valueOf(maladies[0].getId()), MaladyStatus.SICK);
            updateServer(fieldIdentifier.getText().trim()
                    , fieldFirstName.getText().trim(),
                    fieldLastName.getText().trim(),
                    String.valueOf(maladies[0].getId()));
        }
        if (checkMaladies2.isSelected()) {
            salesBlockChain.addBlock(fieldIdentifier.getText().trim(), String.valueOf(maladies[1].getId()), MaladyStatus.SICK);
            updateServer(fieldIdentifier.getText().trim()
                    , fieldFirstName.getText().trim(),
                    fieldLastName.getText().trim(),
                    String.valueOf(maladies[1].getId()));
        }
        if (checkMaladies3.isSelected()) {
            salesBlockChain.addBlock(fieldIdentifier.getText().trim(), String.valueOf(maladies[2].getId()), MaladyStatus.SICK);
            updateServer(fieldIdentifier.getText().trim()
                    , fieldFirstName.getText().trim(),
                    fieldLastName.getText().trim(),
                    String.valueOf(maladies[2].getId()));
        }
        if (checkMaladies4.isSelected()) {
            salesBlockChain.addBlock(fieldIdentifier.getText().trim(), String.valueOf(maladies[3].getId()), MaladyStatus.SICK);
            updateServer(fieldIdentifier.getText().trim()
                    , fieldFirstName.getText().trim(),
                    fieldLastName.getText().trim(),
                    String.valueOf(maladies[3].getId()));
        }
        if (checkMaladies5.isSelected()) {
            salesBlockChain.addBlock(fieldIdentifier.getText().trim(), String.valueOf(maladies[4].getId()), MaladyStatus.SICK);
            updateServer(fieldIdentifier.getText().trim()
                    , fieldFirstName.getText().trim(),
                    fieldLastName.getText().trim(),
                    String.valueOf(maladies[4].getId()));
        }


        BlockchainFactory.INSTANCE.saveBlockChainToJSONFile(salesBlockChain, Constants.FILE_MALADIES);

        Notifications notification = Notifications.create()
            .title("You Successfuly add Dicease !")
            .graphic(new ImageView(new Image("/com/tiaretdevgroup/openhackathon/resources/images/valid.png")))
            .hideAfter(Duration.millis(2000))
            .position(Pos.BOTTOM_RIGHT);
        notification.darkStyle();
        notification.show();

        fieldIdentifier.setText(null);
        fieldFirstName.setText(null);
        fieldLastName.setText(null);

        toggleSelectAll.setSelected(false);
        checkMaladies1.setSelected(false);
        checkMaladies2.setSelected(false);
        checkMaladies3.setSelected(false);
        checkMaladies4.setSelected(false);
        checkMaladies5.setSelected(false);

        loadTable();
    }

    private void updateServer(String id, String fName, String lName, String category) {
        try {
            Unirest.post(Constants.CLIENT).field("firstname", fName)
                    .field("lastname", lName)
                    .field("identifier", id)
                    .field("category", category)
                    .asJson();
        } catch (UnirestException e) {
            System.out.println("Error msg: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onDelete() {
        // Delete
    }

    @FXML
    private void onClear() {
        fieldIdentifier.setText(null);
        fieldFirstName.setText(null);
        fieldLastName.setText(null);

        toggleSelectAll.setSelected(false);
        checkMaladies1.setSelected(false);
        checkMaladies2.setSelected(false);
        checkMaladies3.setSelected(false);
        checkMaladies4.setSelected(false);
        checkMaladies5.setSelected(false);

        fieldSearch.setText(null);
        comboSearch.getSelectionModel().clearSelection();

        tableDiseases.getSelectionModel().clearSelection();
    }

}
