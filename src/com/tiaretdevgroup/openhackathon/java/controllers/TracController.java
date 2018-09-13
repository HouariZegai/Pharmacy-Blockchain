package com.tiaretdevgroup.openhackathon.java.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.tiaretdevgroup.openhackathon.java.models.DiseaseTable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
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
    }    
    
    public void initializeTable() {
        identifierCol = new JFXTreeTableColumn<>("Identifier");
        identifierCol.setPrefWidth(50);
        identifierCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<DiseaseTable, String> param) -> param.getValue().getValue().getIdentifier());

        firstNameCol = new JFXTreeTableColumn<>("First Name");
        firstNameCol.setPrefWidth(200);
        firstNameCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<DiseaseTable, String> param) -> param.getValue().getValue().getFirstName());

        lastNameCol = new JFXTreeTableColumn<>("Last Name");
        lastNameCol.setPrefWidth(150);
        lastNameCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<DiseaseTable, String> param) -> param.getValue().getValue().getLastName());

        produitCol = new JFXTreeTableColumn<>("Produit");
        produitCol.setPrefWidth(200);
        produitCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<DiseaseTable, String> param) -> param.getValue().getValue().getProduit());

        categoryCol = new JFXTreeTableColumn<>("Category");
        categoryCol.setPrefWidth(100);
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
        comboSearchBy.getSelectionModel().select(null);
        searchField.setText(null);
    }
    
}
