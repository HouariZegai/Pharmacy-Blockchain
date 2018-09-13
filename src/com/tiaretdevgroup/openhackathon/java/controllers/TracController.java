package com.tiaretdevgroup.openhackathon.java.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.tiaretdevgroup.openhackathon.java.models.DiseaseTable;
import com.tiaretdevgroup.openhackathon.java.models.Disease;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

public class TracController implements Initializable {

    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXComboBox comboSearchBy;
    @FXML
    private JFXTreeTableView tableTrac;
    
    // Column of table Traceability
    private JFXTreeTableColumn<DiseaseTable, String> identifierCol, firstNameCol, lastNameCol, produitCol, categoryCol, dateCol;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboSearchBy.getItems().addAll(new String[]{"Identifier", "First Name", "Last Name", "Produit", "Category", "Date"});

        initializeTable();
        loadTable();
    }    
    
    private void initializeTable() {
        identifierCol = new JFXTreeTableColumn<>("Identifier");
        identifierCol.setPrefWidth(100);
        identifierCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<DiseaseTable, String> param) -> param.getValue().getValue().getIdentifier());

        firstNameCol = new JFXTreeTableColumn<>("First Name");
        firstNameCol.setPrefWidth(200);
        firstNameCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<DiseaseTable, String> param) -> param.getValue().getValue().getFirstName());

        lastNameCol = new JFXTreeTableColumn<>("Last Name");
        lastNameCol.setPrefWidth(150);
        lastNameCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<DiseaseTable, String> param) -> param.getValue().getValue().getLastName());

        produitCol = new JFXTreeTableColumn<>("Produit");
        produitCol.setPrefWidth(150);
        produitCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<DiseaseTable, String> param) -> param.getValue().getValue().getProduit());

        categoryCol = new JFXTreeTableColumn<>("Category");
        categoryCol.setPrefWidth(150);
        categoryCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<DiseaseTable, String> param) -> param.getValue().getValue().getCategory());

        dateCol = new JFXTreeTableColumn<>("Date");
        dateCol.setPrefWidth(100);
        dateCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<DiseaseTable, String> param) -> param.getValue().getValue().getDate());

        tableTrac.getColumns().addAll(identifierCol, firstNameCol, lastNameCol, produitCol, categoryCol, dateCol);
        tableTrac.setShowRoot(false);
        
        searchField.textProperty().addListener(e -> {
            filterSearchTable(); 
        });
        
        comboSearchBy.setOnAction(e-> {
            filterSearchTable();
        });
        
    }
    
    private void loadTable() {
        ObservableList<DiseaseTable> diseases = FXCollections.observableArrayList();

        List<Disease> diseasesData = new ArrayList<>(); // Get users from DB
        diseasesData.add(new Disease(1, "Houari", "ZEGAi", "Lirika", "Obs", "17/11/2015"));
        diseasesData.add(new Disease(2, "Mohammed", "Miloudi", "Basisa", "ind", "17/12/2016"));
        diseasesData.add(new Disease(3, "Younes", "Charfaoui", "hbhb", "ind", "15/08/2017"));
        diseasesData.add(new Disease(4, "Djamel", "Zerrouki", "karizma", "mala", "19/09/2018"));
        diseasesData.add(new Disease(5, "Fatima", "Chaib", "vivi", "Kolira", "17/01/2009"));
        diseasesData.add(new Disease(6, "Fares", "ZEGAi", "Lirika", "Obs", "09/01/2010"));
        diseasesData.add(new Disease(7, "Abdelkader", "ZEGAi", "hbhb", "Kolira", "01/01/2018"));
        diseasesData.add(new Disease(8, "Omar", "Khaled", "Lirika", "Obs", "17/11/2017"));
        
        if (diseasesData != null) {
            for (Disease disease : diseasesData) {
                diseases.add(new DiseaseTable(disease.getIdentifier(), disease.getFirstName(), disease.getLastName(),
                disease.getProduit(), disease.getCategory(), disease.getDate()));
            }
        }

        final TreeItem<DiseaseTable> treeItem = new RecursiveTreeItem<>(diseases, RecursiveTreeObject::getChildren);
        try {
            tableTrac.setRoot(treeItem);
        } catch (Exception ex) {
            System.out.println("Error catched !");
        }
    }
    
    public void filterSearchTable() {
        tableTrac.setPredicate(new Predicate<TreeItem<DiseaseTable>>() {
                @Override
                public boolean test(TreeItem<DiseaseTable> user) {
                    switch (comboSearchBy.getSelectionModel().getSelectedIndex()) {
                        case 0:
                            return user.getValue().getIdentifier().getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                        case 1:
                            return user.getValue().getFirstName().getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                        case 2:
                            return user.getValue().getLastName().getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                        case 3:
                            return user.getValue().getProduit().getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                        case 4:
                            return user.getValue().getCategory().getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                        case 5:
                            return user.getValue().getDate().getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                        default:
                            return user.getValue().getIdentifier().getValue().toLowerCase().contains(searchField.getText().toLowerCase())
                                    || user.getValue().getFirstName().getValue().toLowerCase().contains(searchField.getText().toLowerCase())
                                    || user.getValue().getLastName().getValue().toLowerCase().contains(searchField.getText().toLowerCase())
                                    || user.getValue().getProduit().getValue().toLowerCase().contains(searchField.getText().toLowerCase())
                                    || user.getValue().getCategory().getValue().toLowerCase().contains(searchField.getText().toLowerCase())
                                    || user.getValue().getDate().getValue().toLowerCase().contains(searchField.getText().toLowerCase());
                    }
                }
            });
    }
    
    @FXML
    private void btnReset() {
        comboSearchBy.getSelectionModel().clearSelection();
        searchField.setText("");
    }
    
}