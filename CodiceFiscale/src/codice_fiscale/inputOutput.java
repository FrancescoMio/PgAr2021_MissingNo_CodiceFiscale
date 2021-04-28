package codice_fiscale;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class inputOutput {
	
	
	static String elementName;
	static String nome , cognome, comuneNascita, dataNascita, comuneCodice;
	static char sesso;
	static int giornoNascita = 0, meseNascita = 0,  annoNascita = 0;
	
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
	
	public static void creaPersone(ArrayList<Persona> persone) throws XMLStreamException { //Questo metodo crea Persone e le mette nell'arraylist persone
		
		XMLStreamReader xmlr = creaLettore("CodiceFiscale/src/XML/inputPersone.xml");
		while (xmlr.hasNext()) { // continua a leggere finch� ha eventi a disposizione
			 switch (xmlr.getEventType()) {
			 case XMLStreamConstants.START_ELEMENT:
				 elementName = xmlr.getLocalName();
				 break;
			 case XMLStreamConstants.END_ELEMENT: // 
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
				 if (xmlr.getText().trim().length() > 0){ 					 
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
		 while (xmlr.hasNext()) { 
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
	
	public static boolean esisteCodice(Persona persone) throws XMLStreamException { //Questo metodo controlla che il codice fiscale di una persona sia presente in codiciFiscali.xml
		if (persone.getCodiceFiscale().equals("ERROR")) {
			return false;
		}
		XMLStreamReader xmlr = creaLettore("CodiceFiscale/src/XML/codiciFiscali.xml");
		while (xmlr.hasNext()) { 
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
	
	public static ArrayList<String> getCodici() throws XMLStreamException{ //Crea un arraylist di tutti i codici fiscali presenti in codiciFiscali.xml
		ArrayList<String> codici2 = new ArrayList<String>();
		XMLStreamReader xmlr = creaLettore("CodiceFiscale/src/XML/codiciFiscali.xml");
		while (xmlr.hasNext()) { 
			 switch (xmlr.getEventType()) {
			 case XMLStreamConstants.CHARACTERS:
				 if (xmlr.getText().trim().length() > 0)
				 codici2.add(xmlr.getText());
				 break;
					 	}
			 xmlr.next();
			 		}
		return codici2;
		}
			 
	
		
	//Metodo che crea CodiciPersone.xml
	
	public static void scritturaXML(ArrayList<Persona> persone) throws XMLStreamException /*ParserConfigurationException, TransformerFactoryConfigurationError, SAXException, IOException, TransformerException*/ {
		ArrayList<String> codici =inputOutput.getCodici(); //Insieme dei codici Fiscali di codiciFiscali.xml
		ArrayList<String> invalidi = new ArrayList<String>();
		ArrayList<String> spaiati= new ArrayList<String>();
		boolean check = true;
		XMLOutputFactory xmlof = null;
		XMLStreamWriter xmlw = null;
		try {   
		xmlof = XMLOutputFactory.newInstance();
		xmlw = xmlof.createXMLStreamWriter(new FileOutputStream("CodiceFiscale/src/XML/codiciPersone.xml"), "utf-8");
		xmlw.writeStartDocument("utf-8", "1.0");
		xmlw.writeStartElement("output"); //Inizio output
		xmlw.writeStartElement("persone");//Inizio persone
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
		xmlw.writeEndElement();//Fine persone
		xmlw.writeStartElement("codici");//Inizio codici
		
		for (int i=0; i<codici.size(); i++) {
			if (calcolatore.codiceInvalido(codici.get(i))) { //Se un codice fiscale di codici � invalido, aggiungilo ad invalidi
				invalidi.add(codici.get(i));
			}
				for (int j=0; j<persone.size(); j++) {
					if (codici.get(i).equals(persone.get(j).getCodiceFiscale())) //Se il codice esiste, metti check false
						check = false;
					}
				if (check)
					spaiati.add(codici.get(i)); //Se un codice fiscale � spaiato, aggiungilo a spaiati
				check = true;
			
		}
		xmlw.writeStartElement("invalidi");//Inizio invalidi
		xmlw.writeAttribute("numero", Integer.toString(invalidi.size()));
		
		for (int i=0; i<invalidi.size(); i++) {
			xmlw.writeStartElement("codice");
			xmlw.writeCharacters(invalidi.get(i));
			xmlw.writeEndElement();
		}
		xmlw.writeEndElement();//Fine Invalidi
		
		xmlw.writeStartElement("spaiati");//Inizio spaiati
		xmlw.writeAttribute("numero", Integer.toString(spaiati.size()));
		
		for (int i=0; i<spaiati.size(); i++) {
			xmlw.writeStartElement("codice");
			xmlw.writeCharacters(spaiati.get(i));
			xmlw.writeEndElement();
		}
		
		xmlw.writeEndElement();//Fine spaiati
		xmlw.writeEndElement();//Fine codici
		
		xmlw.writeEndElement();//Fine output
		xmlw.writeEndDocument();//Fine documento
		xmlw.flush();  
		xmlw.close();
		} catch (Exception e) {
			System.out.println("Errore nell'inizializzazione del writer:");
			System.out.println(e.getMessage());
			
		}
		//Questo qua sotto � per rendere l'xml leggibile
		
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
	

}