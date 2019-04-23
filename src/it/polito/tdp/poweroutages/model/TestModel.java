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
		System.out.println(dao.getOutages(nercList.get(19)));
		
		System.out.println(model.trovaWorstCase(nercList.get(19), 3, 1));
//		System.out.println(model.trovaWorstCase(nercList.get(18), 3, 1));
//		System.out.println(model.trovaWorstCase(nercList.get(17), 3, 1));
		System.out.println(model.trovaWorstCase(nercList.get(16), 3, 5));
		System.out.println(model.trovaWorstCase(nercList.get(16), 3, 5));
	}
}
