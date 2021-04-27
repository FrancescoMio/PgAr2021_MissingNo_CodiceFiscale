/**
 * 
 */
package codice_fiscale;

import java.io.FileInputStream;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;



/**
 * @author FRANCESCO MIO
 *
 */
public class calcolatore {
	
	
	static String elementName;
	static String nome , cognome, comuneNascita, dataNascita, comuneCodice;
	static char sesso;
	static int giornoNascita = 0, meseNascita = 0,  annoNascita = 0;
	
	public static int calcolaGiornoNascita(String dataNascita) {
		String[] parts = dataNascita.split("-");
		String giorno = parts[2];
		int giornoNascita = Integer.parseInt(giorno);
		return giornoNascita;
	}
	
	public static int calcolaMeseNascita(String dataNascita) {
		String[] parts = dataNascita.split("-");
		String mese = parts[1];
		int meseNascita = Integer.parseInt(mese);
		return meseNascita;
	}
	
	public static int calcolaAnnoNascita(String dataNascita) {
		String[] parts = dataNascita.split("-");
		String anno = parts[0];
		int annoNascita = Integer.parseInt(anno);
		return annoNascita;
	}
	
	public static XMLStreamReader creaLettore(String filename) { //crea un Lettore xmlr
		XMLInputFactory xmlif = null;
		XMLStreamReader xmlr = null;
		try {
		 xmlif = XMLInputFactory.newInstance();
		 xmlr = xmlif.createXMLStreamReader(filename, new FileInputStream(filename));
		} catch (Exception e) {
		 System.out.println("Errore nell'inizializzazione del reader:");
		 System.out.println(e.getMessage());
		 return null;
		}
		return xmlr;
	}
	
	public static void creaPersone(ArrayList<Persona> persone) throws XMLStreamException {
		
		XMLStreamReader xmlr = creaLettore("CodiceFiscale/src/XML/inputPersone.xml");
		while (xmlr.hasNext()) { // continua a leggere finché ha eventi a disposizione
			 switch (xmlr.getEventType()) {
			 case XMLStreamConstants.START_ELEMENT:
				 elementName = xmlr.getLocalName();
				 break;
			 case XMLStreamConstants.END_ELEMENT: // fine di un elemento: stampa il nome del tag chiuso
				 if(xmlr.getLocalName().equals("persona")) {
				 Persona persona = new Persona(nome, cognome, comuneNascita, sesso, giornoNascita, meseNascita, annoNascita);
				 persona.setComuneCodice(comuneCodice);
				 persone.add(persona);
				 } 
				 break; 
			 case XMLStreamConstants.CHARACTERS:
				 if (xmlr.getText().trim().length() > 0){ // content all’interno di un elemento: stampa il testo					 
					 if(elementName.equals("nome")) {
						 nome = xmlr.getText();
					 }
					 else if(elementName.equals("cognome")) {
						 cognome = xmlr.getText();
					 }
					 else if(elementName.equals("sesso")) {
						 sesso = xmlr.getText().charAt(0);
					 }
					 else if(elementName.equals("comune_nascita")) {
						 comuneNascita=xmlr.getText();
						 comuneCodice=trovaComune(xmlr.getText());
					 }
					 else {
						 giornoNascita = calcolatore.calcolaGiornoNascita(xmlr.getText());
						 meseNascita = calcolatore.calcolaMeseNascita(xmlr.getText());
						 annoNascita = calcolatore.calcolaAnnoNascita(xmlr.getText());
					 }
				 }
				 break;
			 }
			 xmlr.next();
		}
	}
	
	private static String trovaComune(String comuneNascita ) throws XMLStreamException { //trova codice relativo al comune
		XMLStreamReader xmlr = creaLettore("CodiceFiscale/src/XML/comuni.xml");
		boolean check=false;
		 while (xmlr.hasNext()) { // continua a leggere finché ha eventi a disposizione
			 switch (xmlr.getEventType()) {
			 case XMLStreamConstants.START_ELEMENT:
				 if (xmlr.getLocalName().equals("codice")&&check) {
					 xmlr.next();
					 return xmlr.getText();
				 }
				 break;
			 case XMLStreamConstants.CHARACTERS:
			 		if (xmlr.getText().trim().length() > 0){
			 			if(xmlr.getText().equals(comuneNascita)) {
			 				check=true;
					 	}
			 		}
			 		break;
			 }
			 xmlr.next();
		 }
		 return "";
	}
	
	public static String generazioneCodiceFiscale(Persona persone) {
		String vocaliGlobali = "AEIOUaeiou";
		String codiceFiscale= "";
		String consonanti="";
		String vocali="";
		
		//Cognome
		for (int i=0;i< persone.getCognome().length(); i++) {
			String lettera= String.valueOf(persone.getCognome().charAt(i));
			if (!vocaliGlobali.contains(lettera)) 
				consonanti =consonanti.concat(lettera);
			
			else
				vocali = vocali.concat(lettera);
				
		}
		for (int i=0; i<consonanti.length()&&codiceFiscale.length()<3;i++) {
			codiceFiscale=codiceFiscale.concat(String.valueOf(consonanti.charAt(i)));
		}
		for (int i=0; codiceFiscale.length()<3&&i<vocali.length(); i++) {
			codiceFiscale=codiceFiscale.concat(String.valueOf(vocali.charAt(i)));
		}
		while(codiceFiscale.length()<3) {
			codiceFiscale=codiceFiscale.concat("x");
			}
		
		//Nome
		consonanti="";
		vocali="";
		for (int i=0;i< persone.getNome().length(); i++) {
			String lettera= String.valueOf(persone.getNome().charAt(i));
			if (!vocaliGlobali.contains(lettera)) 
				consonanti =consonanti.concat(lettera);
			
			else
				vocali = vocali.concat(lettera);
				
		}
		
		if (consonanti.length()>3) {
			codiceFiscale=codiceFiscale.concat(String.valueOf(consonanti.charAt(0)));
			codiceFiscale=codiceFiscale.concat(String.valueOf(consonanti.charAt(2)));
			codiceFiscale=codiceFiscale.concat(String.valueOf(consonanti.charAt(3)));
		}
		
		else  {
			for (int i=0; i<consonanti.length()&&codiceFiscale.length()<6;i++) {
				codiceFiscale=codiceFiscale.concat(String.valueOf(consonanti.charAt(i)));
			}
			for (int i=0; codiceFiscale.length()<6&&i<vocali.length(); i++) {
				codiceFiscale=codiceFiscale.concat(String.valueOf(vocali.charAt(i)));
			}
				while(codiceFiscale.length()<6) {
					codiceFiscale=codiceFiscale.concat("x");
					}
				}
		//Anno di nascita
		String anno= String.valueOf((persone.getAnnoNascita()%100));
		if (anno.length()==1) {
			anno = "0"+anno;
		}
		codiceFiscale= codiceFiscale.concat(anno);
		//Mese di nascita
		String mese="";
		switch (persone.getMeseNascita()) {
		case 1:
			mese="A";
			break;
		case 2:
			mese="B";
			break;
		case 3:
			mese="C";
			break;
		case 4:
			mese="D";
			break;
		case 5:
			mese="E";
			break;
		case 6:
			mese="H";
			break;
		case 7:
			mese="L";
			break;
		case 8:
			mese="M";
			break;
		case 9:
			mese="P";
			break;
		case 10:
			mese="R";
			break;
		case 11:
			mese="S";
			break;
		case 12:
			mese="T";
			break;
			}
		codiceFiscale= codiceFiscale.concat(mese);
		//Giorno di nascita
		if (persone.getSesso()=='F')
			codiceFiscale=codiceFiscale.concat(String.valueOf(persone.getGiornoNascita()+40));
		else {
			if (persone.getGiornoNascita()<9)
			codiceFiscale=  codiceFiscale.concat("0" + String.valueOf(persone.getGiornoNascita()));
			else
			codiceFiscale=codiceFiscale.concat(String.valueOf(persone.getGiornoNascita()));
		}
		//Comune di nascita
		codiceFiscale= codiceFiscale.concat(persone.getComuneCodice());
		//Carattere di Controllo
		
		//Questo è per te ;)
		
		return codiceFiscale;
		}
		
	}
