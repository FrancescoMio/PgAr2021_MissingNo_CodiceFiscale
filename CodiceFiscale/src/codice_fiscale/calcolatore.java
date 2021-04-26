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
	static String nome , cognome, comuneNascita, dataNascita;
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
						 comuneNascita=trovaComune(xmlr.getText());
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
	
	/*public static String generazioneCodiceFiscale(Persona persone) {
		String Vocali = "AEIOUaeiou";
		String CodiceFiscale= "";
		int contatore=0;
		
		for (int i=0;i< persone.getCognome().length()-1; i++) {
			String lettera= String.valueOf(persone.getCognome().charAt(i));
			if (!Vocali.contains(lettera)){
				CodiceFiscale = CodiceFiscale.concat(lettera);
				contatore++;
				}
		}
		
		if (contatore !=3) {
			for (int i=0;i< persone.getCognome().length()-1; i++) {
				String lettera2= String.valueOf(persone.getCognome().charAt(i));
				if (Vocali.contains(lettera2)){
					CodiceFiscale = CodiceFiscale.concat(lettera2);
					contatore++;
					}
			}
		}
			
		if (contatore !=3){
			while(contatore!=3) {
				CodiceFiscale = CodiceFiscale.concat("X");
				contatore++;
			}
		}
		
		return CodiceFiscale;
	}*/
	
	
}