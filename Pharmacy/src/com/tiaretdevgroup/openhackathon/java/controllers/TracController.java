package com.tiaretdevgroup.openhackathon.java.controllers;

import blockchain.factory.BlockchainFactory;
import blockchain.models.Sale;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.tiaretdevgroup.openhackathon.java.blockchain.chains.SalesBlockChain;
import com.tiaretdevgroup.openhackathon.java.models.Disease;
import com.tiaretdevgroup.openhackathon.java.models.DiseaseTable;
import com.tiaretdevgroup.openhackathon.java.utils.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

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

    private List<Disease> getAndDisplaySales() {
        SalesBlockChain chain = BlockchainFactory.INSTANCE.readSalesBlockChainFromJSONFile();

        List<Sale> sales = chain.getSales();
        JSONArray array = new JSONArray();
        for (Sale sale : sales) {
            JSONObject object = new JSONObject();
            object.put("client_id", sale.getIdPatient())
                    .put("product_id", sale.getProductId());
            array.put(object);
        }
        try {
            String url = Constants.SALES;

            HttpResponse<JsonNode> call = Unirest.post(url).field("q", array.toString())
                    .asJson();

            JSONArray jsonArray = new JSONArray(call.getBody().toString());
            List<Disease> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject object = jsonArray.getJSONObject(i);
                int id = object.getInt("identifier");
                String fName = object.getString("firstname");
                String lName = object.getString("lastname");
                int category = object.getInt("category_id");
                String date = object.getString("humanDate");
                String product = object.getString("name");
                System.out.println(new Disease(id, fName, lName, product, String.valueOf(category), date).toString());
                list.add(new Disease(id, fName, lName, product, String.valueOf(category), date));
            }
            return list;
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return null;
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

        comboSearchBy.setOnAction(e -> {
            filterSearchTable();
        });

    }

    private void loadTable() {
        ObservableList<DiseaseTable> diseases = FXCollections.observableArrayList();

        List<Disease> diseasesData =  getAndDisplaySales();


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