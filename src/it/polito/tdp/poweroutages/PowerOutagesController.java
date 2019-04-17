package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PowerOutagesController {

	private Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Nerc> comboBoxMaac;

    @FXML
    private TextField txtMaxYears;

    @FXML
    private TextField txtMaxHours;

    @FXML
    private Button btnWorstCase;

    @FXML
    private TextArea txtResult;



    @FXML
    void doWorstCaseAnalysis(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert comboBoxMaac != null : "fx:id=\"comboBoxMaac\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtMaxYears != null : "fx:id=\"txtMaxYears\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtMaxHours != null : "fx:id=\"txtMaxHours\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnWorstCase != null : "fx:id=\"btnWorstCase\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";

    }

	public void setModel(Model model) {
		// TODO Auto-generated method stub
		this.model=model;
		comboBoxMaac.getItems().addAll(model.getNercList());
		
	}
}
