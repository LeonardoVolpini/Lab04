package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {

	/**
	 * ottengo uno studente, data la sua matricola
	 * @param matricola
	 * @return
	 */
	public Studente getStudente(Studente studente) {
		String sql= "SELECT nome, cognome, cds "
				+"FROM studente "
				+"WHERE matricola=?";
		Studente s=null;
		try{
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			ResultSet rs= st.executeQuery();
			
			while (rs.next()) {
				String nome= rs.getString("nome");
				String cognome= rs.getString("cognome");
				String cds= rs.getString("cds");
				s = new Studente(studente.getMatricola(),nome,cognome,cds);
			}
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException("Errore DB", e);
		}
		return s;
	}
	
	/**
	 * lista dei corsi a cui uno studente e' iscritto
	 * @param studente
	 * @return
	 */
	public List<Corso> getCorsiPerStudente (Studente studente) {
		String sql = "SELECT c.codins, crediti, c.nome, pd "
				+ "FROM corso c, iscrizione i, studente s "
				+ "WHERE c.codins=i.codins AND i.matricola=s.matricola AND i.matricola=? "
				+ "GROUP BY c.codins, crediti, c.nome, pd";
		List<Corso> corsi= new LinkedList<>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			ResultSet rs= st.executeQuery();
			
			while (rs.next()) {
				String codins = rs.getString("codins");
				Integer crediti = rs.getInt("crediti");
				String nome= rs.getString("nome");
				Integer pd= rs.getInt("pd");
				Corso c = new Corso(codins,crediti,nome,pd);
				corsi.add(c);
			}
			conn.close();
		}catch (SQLException e) {
			throw new RuntimeException("Errore nel DB",e);
		}
		return corsi;
	}
	
	/**
	 * ritorna -1 se studente non esiste, 1 se iscritto, 0 se non iscritto
	 * @param corso
	 * @param studente
	 * @return
	 */
	public int studenteIscrittoAlCorso (Corso corso, Studente studente) {
		Studente s = this.getStudente(studente);
		if (s==null)
			return -1;
		String sql= "SELECT codins FROM iscrizione WHERE codins=? AND matricola=?";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			st.setInt(2, studente.getMatricola());
			ResultSet rs= st.executeQuery();
			if(rs.next()) {
				rs.close();
				st.close();
				conn.close();
				return 1;
			}
			else {
				rs.close();
				st.close();
				String sql2= "INSERT INTO iscrizione VALUES (?,?)";
				PreparedStatement st2= conn.prepareStatement(sql2);
				st2.setString(1, corso.getCodins());
				st2.setInt(2, studente.getMatricola());
				ResultSet rs2 = st2.executeQuery();
				rs2.close();
				st2.close();
				conn.close();
				return 0;
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("Errore ne DB", e);
		}
	}
	
}
