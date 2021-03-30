package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;

public class Model {

	private CorsoDAO corsoDAO;
	
	public Model() {
		this.corsoDAO=new CorsoDAO();
	}
	
	public List<Corso> getCorsi() {
		return corsoDAO.getTuttiICorsi();
	}
}
