package it.polito.tdp.poweroutages.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutageEvent;

public class PowerOutageDAO {

	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}

	public List<PowerOutageEvent> getOutages(Nerc nercScelto) {

		String sql = "SELECT id, event_type_id, nerc_id, customers_affected, " + 
				"date_event_finished, date_event_began, " + 
				"DATEDIFF(date_event_finished, date_event_began) AS DATEDIFF, " + 
				"TIMEDIFF(date_event_finished, date_event_began) AS TIMEDIFF " + 
				"FROM poweroutages " + 
				"WHERE nerc_id= ? " + 
				"ORDER BY date_event_began ASC ";
		
		List<PowerOutageEvent> outagesNerc = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, nercScelto.getId());
			
			ResultSet res = st.executeQuery();

			while (res.next()) {
				PowerOutageEvent p = new PowerOutageEvent(res.getInt("id"),
						nercScelto, res.getInt("event_type_id"), res.getTimestamp("date_event_began").toLocalDateTime(),
						res.getTimestamp("date_event_finished").toLocalDateTime(), res.getInt("customers_affected"));
//						res.getInt("DATEDIFF"), res.getTimestamp("TIMEDIFF").toLocalDateTime());
				//In outagesNerc salvo tutti i powerotuage event in un certo nerc in ordine decrescente di durata del fenomeno
				outagesNerc.add(p);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return outagesNerc;
	}

}
