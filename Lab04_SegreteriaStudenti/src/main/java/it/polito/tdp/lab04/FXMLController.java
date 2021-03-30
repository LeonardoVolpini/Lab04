package it.polito.tdp.lab04;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Corso> ChBoxCorsi;

    @FXML
    private TextField txtMatricola;
    
    @FXML
    private Button btnRiempimento;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCercaCorsi(ActionEvent event) {
    	String matric= this.txtMatricola.getText();
    	int matricola;
    	try {
    		matricola= Integer.parseInt(matric);
    	} catch(NumberFormatException fne) {
    		this.txtResult.setText("inserire un numero di matricola");
    		return;
    	}
    	List<Corso> result = this.model.getCorsiPerStudente(matricola);
    	if (result!=null) {
	    	this.txtResult.clear();
	    	for (Corso c : result)
	    		this.txtResult.appendText(c.toString()+"\n");
    	} else {
    		this.txtResult.setText("numero di matricola non presente nel database");
    	}
    }

    @FXML
    void handleCercaIscrittiCorso(ActionEvent event) {
    	Corso corso=this.ChBoxCorsi.getValue();
    	if (corso==null || corso.getNome().isEmpty()) {
    		this.txtResult.setText("scegliere un corso di cui avere la lista degli iscritti");
    		return;
    	}
    	List<Studente> result=this.model.getStudentiPerCorso(corso);
    	this.txtResult.clear();
    	for (Studente s : result)
    		this.txtResult.appendText(s.toString()+"\n");
    }

    @FXML
    void handleIscrivi(ActionEvent event) {
    	String matric= this.txtMatricola.getText();
    	int matricola;
    	try {
    		matricola= Integer.parseInt(matric);
    	} catch(NumberFormatException fne) {
    		this.txtResult.setText("inserire un numero di matricola");
    		return;
    	}
    }
    
    @FXML
    void handleRiempi(ActionEvent event) {
    	String matric= this.txtMatricola.getText();
    	int matricola;
    	try {
    		matricola=Integer.parseInt(matric);
    	} catch(NumberFormatException nfe) {
    		this.txtResult.setText("inseire un numero di matricola");
    		return;
    	}
    	Studente s=this.model.getStudente(matricola);
    	if (s!=null) {
    		this.txtNome.setText(s.getNome());
    		this.txtCognome.setText(s.getCognome());
    	}else {
    		this.txtResult.setText("nessun studente con quel numero di matricola");
    	}
    }

    @FXML
    void handleReset(ActionEvent event) {
    	this.txtResult.clear();
    	this.txtCognome.clear();
    	this.txtNome.clear();
    	this.txtMatricola.clear();
    }

    @FXML
    void initialize() {
        assert ChBoxCorsi != null : "fx:id=\"ChBoxCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRiempimento != null : "fx:id=\"btnRiempimento\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model=model;
    	List<Corso> corsi= this.model.getCorsi();
    	Collections.sort(corsi);
    	corsi.add(new Corso("",-1,"",-1));
    	this.ChBoxCorsi.getItems().addAll(corsi);
    } 
}
