package it.polito.tdp.poweroutages.model;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.util.*;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	private PowerOutageDAO podao;
	private List<PowerOutageEvent> outages;
	private List<PowerOutageEvent> best;
	private int customersAffectedBest;
	
	public Model() {
		podao = new PowerOutageDAO();
		outages = new ArrayList<PowerOutageEvent>();
		best = new ArrayList<PowerOutageEvent>();
		customersAffectedBest = Integer.MIN_VALUE;
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public String trovaWorstCase(Nerc nercScelto, int anniMax, int oreMax) {
		
		String risultato = "";
		
		List<PowerOutageEvent>parziale = new ArrayList<PowerOutageEvent>();
		//Voglio considerare gli eventi all'interno SOLO del nerc selezionato
		//nercScelto.getId()
		outages.clear();
		//Devo considerare SOLO i blackout nel nerc selezionato
		outages=podao.getOutages(nercScelto);
		
		cerca(parziale, 0, oreMax, anniMax);
		
		risultato+="Il numero massimo di clienti colpiti per i parametri inseriti e': "+customersAffectedBest+"\nIl sottoinsieme di blackout e' il seguente:\n";
		for(PowerOutageEvent p: best) {
			risultato+=p.toString()+"\n";
		}
		
		
		return risultato;
	}
	
	private void cerca (List<PowerOutageEvent>parziale, int L, int oreMax, int anniMax) {
		
		//Soluzione: lista di eventi blackout (di cui non conosco la lunghezza) che rispetti i vincoli su anniMax e oreMax
		//Soluzione parziale = sequenza di livello <=15
		//Soluzione totale = sequenza completa di 15 elementi
		//Livello = giorno del calendario
		
		
		int outagesSize = outages.size();
		
		int customersAffectedParziale = calcolaCustomersAffected(parziale);
		
		
		//Casi terminali
		
		//1) Ho finito i powerOutagesEvent da controllare in outages
		if(L==outagesSize) {
			if(best==null || customersAffectedParziale>customersAffectedBest)
				best=new ArrayList<PowerOutageEvent>(parziale);
				customersAffectedBest = customersAffectedParziale;
		}
		
		//2)Se la soluzione aggiornata e' meglio di quella precedente mi conviene aggiornare il best
		if(customersAffectedParziale > customersAffectedBest){
			customersAffectedBest = customersAffectedParziale;
			best=new ArrayList<PowerOutageEvent>(parziale);
		}
		
		
		
		//Quando scagliona outages lo sta facendo in ordine decrescente di persone colpite
		for(PowerOutageEvent prova : outages) {
			
			if(!parziale.contains(prova)) {
				
				if(controllaAnniMax(prova, parziale, anniMax) && controllaOreMax(prova, parziale, oreMax)) {
					//Se passa entrambi i controlli vado al livello successivo
					parziale.add(prova);
					cerca(parziale, L+1, oreMax, anniMax);
				
					//Altrimenti rimuovo l'ultimo elemento inserito
					parziale.remove(parziale.size()-1);
				}
			}
		}
		
	}



	private int calcolaCustomersAffected(List<PowerOutageEvent> parziale) {
		// TODO Auto-generated method stub
		int customersAffectedParziale=0;
		
		for(PowerOutageEvent p: parziale) {
			customersAffectedParziale+=p.getCustomersAffected();
		}
		return customersAffectedParziale;
	}

	/**@author Prof. Corno
	 * Verifica se, data la soluzione {@code parziale} già definita, sia lecito
	 * aggiungere la città {@code prova}, rispettando i vincoli sui numeri giorni
	 * minimi e massimi di permanenza.
	 * 
	 * @param prova
	 *            il poweroutage che sto cercando di aggiungere
	 * @param parziale
	 *            la sequenza di poweroutage già composta
	 * @return {@code true} se {@code prova} è lecita, {@code false} se invece viola
	 *         qualche vincolo (e quindi non è lecita)
	 *         
	 * 1)(annoEventoPiuRecente-annoEventoPiuVecchio) deve essere < anniMax
	 */
		private boolean controllaAnniMax(PowerOutageEvent prova, List<PowerOutageEvent> parziale, int anniMax) {
			// TODO Auto-generated method stub
			//Io sto passando parziale che è in ordine crescente di data, poichè la lista da cui sto aggiungendo i powerOutageEvent è in tale ordine
			
			
			//Se sono al primo tentativo allora parziale.size==0 e devo aggiungere il powerOutageEvent di prova
			if(parziale.size()==0) {
				return true;
			}
			
			//Se invece parziale ha una dimensione maggiore di 0, allora devo controllare se la differenza tra gli anni di 
			//inizio primo evento e fine ultimo evento sono entro un certo numero di anni
			else {
				long diffAnni =prova.getYear() - parziale.get(0).getYear();
				
				if(anniMax-diffAnni>=0) {
					return true;
				}
				return false;
				
			}
			
			
			
			
			
			//Così non funziona
			
			//In teoria la  prima data viene salvata sia in piuVecchio che in piuRecente, ma dalla seconda viene salvata in piuVecchio se è prima di  quella aggiunta oppure in piùRecente se è successiva ad essa
//			for(PowerOutageEvent p: parziale){
//				if(p.getDateEventBegan().isBefore(piuVecchio) || piuVecchio==null) {
//					piuVecchio.of(p.getDateEventBegan().toLocalDate(), p.getDateEventBegan().toLocalTime());
//				}
//				if(p.getDateEventBegan().isAfter(piuRecente) || piuRecente==null) {
//					piuRecente.of(p.getDateEventBegan().toLocalDate(), p.getDateEventBegan().toLocalTime());
//				}
//			}
//			
//			
//			
//			
//			//Forse non conviene riordinarsi ogni volta il parziale, ma sarebbe meglio passare un parziale gia' ordinato in ordine crescente di data, cosi' da dover confrontare solo il poweroutageevent di prova
//			//Qui guardo se il powerOutage che sto provando ad aggiungere e' prima o dopo
//			if(prova.getDateEventBegan().isBefore(piuVecchio)) {
//				piuVecchio.of(prova.getDateEventBegan().toLocalDate(), prova.getDateEventBegan().toLocalTime());
//			}
//			if(prova.getDateEventBegan().isAfter(piuRecente)) {
//				piuRecente.of(prova.getDateEventBegan().toLocalDate(), prova.getDateEventBegan().toLocalTime());
//			}
//			
//			if(piuVecchio.until(piuRecente, ChronoUnit.YEARS)<anniMax) {
//				return true;
//			}
//			return false;
		}
	
	/*
	 * 2)totaleOreEvento deve essere < oreMax
	 * powerOutage.getEventTypeId
	 * Poi preso l'id dell'evento, bisogna sommare tutte le ore dell'evento e scalarle a oreMax 
	 */
	private boolean controllaOreMax(PowerOutageEvent prova, List<PowerOutageEvent> parziale, int oreMax) {
		// TODO Auto-generated method stub
		
		long oreParziale=0;
		
		//Alla prima prova, parziale e' vuoto---> posso evitare il ciclo for
		if(parziale.size()!=0) {
			for(PowerOutageEvent p: parziale){	
				oreParziale+=p.getDiffTime();
			}
		}
//		oreParziale+=prova.getDateEventBegan().until(prova.getDateEventEnd(), ChronoUnit.HOURS);
		oreParziale+=prova.getDiffTime();

		if(oreMax-oreParziale>=0) {
			return true;
		}
		
		return false;
	}
	
}
