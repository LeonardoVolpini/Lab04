package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {

	private CorsoDAO corsoDAO;
	private StudenteDAO studenteDAO;
	
	public Model() {
		this.corsoDAO=new CorsoDAO();
		this.studenteDAO=new StudenteDAO();
	}
	
	public List<Corso> getCorsi() {
		return corsoDAO.getTuttiICorsi();
	}
	
	public Studente getStudente(int matricola) {
		return this.studenteDAO.getStudente(matricola);
	}
}
