package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
				Corso c= new Corso(codins,numeroCrediti,nome,periodoDidattico);
				corsi.add(c);
				System.out.println(c.toString());
			}

			conn.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore DB", e);
		}
		return corsi;
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(String codins) {
		String sql= "SELECT crediti, nome, pd "
				+ "FROM corso "
				+ "WHERE codins=?";
		Corso c = null;
		try {
			Connection conn= ConnectDB.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setString(1, codins);
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) {
				Integer crediti=rs.getInt("crediti");
				String nome= rs.getString("nome");
				Integer pd= rs.getInt("pd");
				c= new Corso(codins,crediti,nome,pd);
			}
			conn.close();
		} catch(SQLException e) {
			throw new RuntimeException("Errore DB", e);
		}
		return c;
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		String sql = "SELECT s.matricola, s.nome, cognome, cds "
				+"FROM corso c, iscrizione i, studente s "
				+"WHERE c.codins=i.codins AND i.matricola=s.matricola AND i.codins=? "
				+"GROUP BY s.matricola, s.nome, cognome, cds";
		List<Studente> studenti = new LinkedList<>();
		try {
			Connection conn= ConnectDB.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) {
				Integer matricola= rs.getInt("matricola");
				String nome=rs.getString("nome");
				String cognome = rs.getString("cognome");
				String cds = rs.getString("cds");
				Studente s = new Studente(matricola,nome,cognome,cds);
				studenti.add(s);
			}
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException("Errore DB",e);
		}
		return studenti;
		
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(int matricola, String codins) {
		Corso c= this.getCorso(codins);
		String sql = "SELECT s.matricola, s.nome, cognome, cds "
				+ "FROM corso c, iscrizione i, studente s "
				+ "WHERE c.codins=i.codins AND s.matricola=i.matricola AND c.codins=? AND s.matricola=? "
				+ "GROUP BY s.matricola, s.nome, cognome, cds";
		Studente s= null;
		try {
			Connection conn= ConnectDB.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setString(1, codins);
			st.setInt(2, matricola);
			ResultSet rs= st.executeQuery();
			if (rs.next()==false) {
				
			}
			conn.close();
		}catch (SQLException e) {
			throw new RuntimeException("Errore DB",e);
		}
		// ritorna true se l'iscrizione e' avvenuta con successo
		return false;
	}

}
