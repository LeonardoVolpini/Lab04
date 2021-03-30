package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {

	
	public Studente getStudente(int matricola) {
		String sql= "SELECT nome, cognome, cds "
				+"FROM studente "
				+"WHERE matricola=?";
		Studente s=null;
		try{
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs= st.executeQuery();
			
			while (rs.next()) {
				String nome= rs.getString("nome");
				String cognome= rs.getString("cognome");
				String cds= rs.getString("cds");
				s = new Studente(matricola,nome,cognome,cds);
			}
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException("Errore DB", e);
		}
		return s;
	}
	
	
	
}
