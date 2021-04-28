/**
 * 
 */
package codice_fiscale;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;



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
				 persona.setCodiceFiscale(calcolatore.generazioneCodiceFiscale(persona));
				 if (!esisteCodice(persona))
					 persona.setAssenza("ASSENTE");
				else
					persona.setAssenza(persona.getCodiceFiscale());
						 
				 persone.add(persona);
				 } 
				 break; 
			 case XMLStreamConstants.CHARACTERS:
				 if (xmlr.getText().trim().length() > 0){ // content all’interno di un elemento: stampa il testo					 
					 if(elementName.equals("nome")) {
						 nome = xmlr.getText().toUpperCase();
					 }
					 else if(elementName.equals("cognome")) {
						 cognome = xmlr.getText().toUpperCase();
					 }
					 else if(elementName.equals("sesso")) {
						 sesso = xmlr.getText().toUpperCase().charAt(0);
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
		String vocaliGlobali = "AEIOU";
		String codiceFiscale= "";
		String consonanti="";
		String vocali="";
		char cin;
		
		//Cognome
		if (persone.getCognome().length()==0)
			return "ERROR";
		for (int i=0;i< persone.getCognome().length(); i++) {
			String lettera= String.valueOf(persone.getCognome().charAt(i));
			if (!vocaliGlobali.contains(lettera)) 
				consonanti =consonanti.concat(lettera);
			else if (vocaliGlobali.contains(lettera))
				vocali = vocali.concat(lettera);
			else
				return "ERROR";
				
		}
		for (int i=0; i<consonanti.length()&&codiceFiscale.length()<3;i++) {
			codiceFiscale=codiceFiscale.concat(String.valueOf(consonanti.charAt(i)));
		}
		for (int i=0; codiceFiscale.length()<3&&i<vocali.length(); i++) {
			codiceFiscale=codiceFiscale.concat(String.valueOf(vocali.charAt(i)));
		}
		while(codiceFiscale.length()<3) {
			codiceFiscale=codiceFiscale.concat("X");
			}
		
		//Nome
		consonanti="";
		vocali="";
		if (persone.getNome().length()==0)
			return "ERROR";
		for (int i=0;i< persone.getNome().length(); i++) {
			String lettera= String.valueOf(persone.getNome().charAt(i));
			if (!vocaliGlobali.contains(lettera)) 
				consonanti =consonanti.concat(lettera);
			else if (vocaliGlobali.contains(lettera))
				vocali = vocali.concat(lettera);
			else
				return "ERROR";
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
		default:
			return "ERROR";
			}
		codiceFiscale= codiceFiscale.concat(mese);
		
		//Giorno di nascita
		if (persone.getMeseNascita()==1 || persone.getMeseNascita()==3 || persone.getMeseNascita()==5 || persone.getMeseNascita()==7 || persone.getMeseNascita()==8 || persone.getMeseNascita()==10 || persone.getMeseNascita()==12) {
			if (persone.getGiornoNascita()>31)
				return "ERROR";
		}
		
		if (persone.getMeseNascita()==4 || persone.getMeseNascita()==6 || persone.getMeseNascita()==4 || persone.getMeseNascita()==9 || persone.getMeseNascita()==11) {
			if (persone.getGiornoNascita()>30)
				return "ERROR";
		}
		
		if (persone.getMeseNascita()==2) {
			if (persone.getGiornoNascita()>28)
				if (persone.getAnnoNascita()%4==0)
				return "ERROR";
		}
				
		if (persone.getSesso()=='F')
			codiceFiscale=codiceFiscale.concat(String.valueOf(persone.getGiornoNascita()+40));
		else {
			if (persone.getGiornoNascita()<10)
			codiceFiscale=  codiceFiscale.concat("0" + String.valueOf(persone.getGiornoNascita()));
			else
			codiceFiscale=codiceFiscale.concat(String.valueOf(persone.getGiornoNascita()));
		}
		//Comune di nascita
		codiceFiscale= codiceFiscale.concat(persone.getComuneCodice());
		//Carattere di Controllo
		if (codiceFiscale.length() == 15) {
			ArrayList<Character> charPari = new ArrayList<Character>();
			ArrayList<Character> charDispari = new ArrayList<Character>();
			for (int i=0; i<codiceFiscale.length();i++) {
				if (i % 2 == 0) 
					charDispari.add(codiceFiscale.charAt(i));
				else 
					charPari.add(codiceFiscale.charAt(i));
			}
			int somma = 0;
			for(int i = 0; i < charPari.size(); i++) {
				int ascii = charPari.get(i);
				if(ascii < 65) 
					somma += ascii - 48;
				else 
					somma += ascii - 65;
			}
			//String lettereDispari[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
			int dispari[] = {1,0,5,7,9,13,15,17,19,21,2,4,18,20,11,3,6,8,12,14,16,10,22,25,24,23};
			int [] dispari2 = {1,0,5,7,9,13,15,17,19,21};
			for(int i = 0; i < charDispari.size(); i++) {
				int ascii = charDispari.get(i);
				if(ascii < 65) {
					int numero = ascii - 48;
					int valore = dispari2[numero];
					somma += valore;
				}
				else {
					int numero = ascii - 65;
					int valore = dispari[numero];
					somma += valore;
				}
			}
			int resto = somma % 26;
			int ascii = resto + 65;
			cin = ((char)ascii);
			codiceFiscale = codiceFiscale.concat(String.valueOf(cin));
			return codiceFiscale;
		}
		else
			return "ERROR";
		
		
	}
	
	public static Boolean esisteCodice(Persona persone) throws XMLStreamException {
		if (persone.getCodiceFiscale().equals("ERROR")) {
			return false;
		}
		XMLStreamReader xmlr = creaLettore("CodiceFiscale/src/XML/codiciFiscali.xml");
		while (xmlr.hasNext()) { // continua a leggere finché ha eventi a disposizione
			 switch (xmlr.getEventType()) {
			 case XMLStreamConstants.CHARACTERS:
			 		if (xmlr.getText().trim().length() > 0){
			 			if(xmlr.getText().equals(persone.getCodiceFiscale())) {
			 				return true;
					 	}
			 		}
			 break;
			 }
			 xmlr.next();
			 }
		return false;
	}
	
	public static void scritturaXML(ArrayList<Persona> persone) /*throws XMLStreamException, ParserConfigurationException, TransformerFactoryConfigurationError, SAXException, IOException, TransformerException*/ {
		XMLOutputFactory xmlof = null;
		XMLStreamWriter xmlw = null;
		try {   
		xmlof = XMLOutputFactory.newInstance();
		xmlw = xmlof.createXMLStreamWriter(new FileOutputStream("CodiceFiscale/src/XML/codiciPersone.xml"), "utf-8");
		xmlw.writeStartDocument("utf-8", "1.0");
		xmlw.writeStartElement("output");
		xmlw.writeStartElement("persone");
		xmlw.writeAttribute("numero", Integer.toString(persone.size()));
		for (int i=0; i<persone.size();i++) {
			xmlw.writeStartElement("persona");
			xmlw.writeAttribute("id", Integer.toString(i));
			
			xmlw.writeStartElement("nome");
			xmlw.writeCharacters(persone.get(i).getNome());
			xmlw.writeEndElement();
			
			xmlw.writeStartElement("cognome");
			xmlw.writeCharacters(persone.get(i).getCognome());
			xmlw.writeEndElement();
			
			xmlw.writeStartElement("sesso");
			xmlw.writeCharacters(String.valueOf(persone.get(i).getSesso()));
			xmlw.writeEndElement();
			
			xmlw.writeStartElement("comune_nascita");
			xmlw.writeCharacters(persone.get(i).getComuneNascita());
			xmlw.writeEndElement();
			
			xmlw.writeStartElement("data_nascita");
			xmlw.writeCharacters(persone.get(i).data());
			xmlw.writeEndElement();
			
			xmlw.writeStartElement("codice_fiscale");
			xmlw.writeCharacters(persone.get(i).getAssenza());
			xmlw.writeEndElement();
			
			xmlw.writeEndElement();
			
		}
		xmlw.writeEndElement();
		xmlw.writeEndElement();
		xmlw.writeEndDocument();
		xmlw.flush();  
		xmlw.close();
		} catch (Exception e) {
			System.out.println("Errore nell'inizializzazione del writer:");
			System.out.println(e.getMessage());
			
		}
		//Questo qua sotto è per rendere l'xml leggibile
		
		/*DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder db = dbf.newDocumentBuilder();
         
        Document doc = db.parse(new FileInputStream(new File("CodiceFiscale/src/XML/codiciPersone.xml")));
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(doc), new StreamResult(out));
        File file = new File ("CodiceFiscale/src/XML/codiciPersone.xml");
		PrintStream stream = new PrintStream(file);
        System.setOut(stream);
        System.out.println(out.toString());*/
		
	}
	
	public static boolean codiceInvalido(String codiceFiscale) { //Controlla se un codice fiscale è invalido, e se si, retrun true
		
		String vocaliGlobali = "AEIOU";
		String mesi = "ABCDEHLMPRST";
		//Controllo validità generale
		if (codiceFiscale.length()!=16){
			return true;
		}
		for (int i=0; i<16; i++) {
			if (i==6 ||i==7 || i==9 || i==10 || i==12 ||i==13 ||i==14) {
				if (codiceFiscale.charAt(i)<48 || codiceFiscale.charAt(i)>57) {
					return true;
					}
				else
					if (codiceFiscale.charAt(i)<65 || codiceFiscale.charAt(i)>90)
						return true;
					
			}
			
		}
		
		//Controllo validità mese
		if (!mesi.contains(String.valueOf(codiceFiscale.charAt(8))))
			return true;
		
		
		
		//Controllo validità giorno
		
		int giorno = ((codiceFiscale.charAt(9)-48)*10 +(codiceFiscale.charAt(10)-48));
		
		if (giorno>31)
			giorno = giorno-40;
		
		if (giorno<1 || giorno >31)
			return true;
		
		//Qua bisogna verificare se il giorno può esistere all'interno del mese xyz
		
		
		
		//Controllo validità Cognome	
		for (int i=0; i<2; i++) {
			if (vocaliGlobali.contains(String.valueOf(codiceFiscale.charAt(i)))) {
				for (int j=i+1; j<3; j++) {
					if (!vocaliGlobali.contains(String.valueOf(codiceFiscale.charAt(j)))) {
						if('X'==codiceFiscale.charAt(j)) {
							if (j==1 && 'X'!=codiceFiscale.charAt(j+1)) {
								return true;
							}
						}
						else
							return true;
					}
				}
			}
			
		}
		
		//Controllo validità Nome
		
		for (int i=3; i<5; i++) {
			if (vocaliGlobali.contains(String.valueOf(codiceFiscale.charAt(i)))) {
				for (int j=i+1; j<6; j++) {
					if (!vocaliGlobali.contains(String.valueOf(codiceFiscale.charAt(j)))) {
						if('X'==codiceFiscale.charAt(j)) {
							if (j==1 && 'X'!=codiceFiscale.charAt(j+1)) {
								return true;
							}
						}
						else
							return true;
					}
				}
			}
			
		}
		
		
		
		return false;
		
	}
	
}
