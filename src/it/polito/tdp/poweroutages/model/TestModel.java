package it.polito.tdp.poweroutages.model;

import java.util.List;
import it.polito.tdp.poweroutages.db.*;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		PowerOutageDAO dao = new PowerOutageDAO();
		
		List<Nerc> nercList = model.getNercList();
		System.out.println(nercList);
		
//		Funziona il metodo per estrarre dal db i poweroutages nel nerc
		System.out.println(dao.getOutages(nercList.get(2)));
		
		System.out.println(model.trovaWorstCase(nercList.get(3), 10, 200));
	}
}
