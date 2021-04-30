package codice_fiscale;

import java.util.ArrayList;
import javax.xml.stream.XMLStreamException;

public class CodiceFiscaleMain {
	static ArrayList<Persona> persone = new ArrayList<Persona>();
	public static String MESSAGGIO_XML = "Creazione file codiciPersone.xml andata a buon fine!";
	public static String MESSAGGIO_XML_ERRORE = "Creazione file codiciPersone.xml non riuscita";
	
	public static void main(String[] args) throws XMLStreamException {
		InputOutput.creaPersone(persone);
		/*for(int i = 0; i < 100; i++) {
			System.out.println(persone.get(i).toString());
		}*/
		if(InputOutput.scritturaXML(persone))
			System.out.println(MESSAGGIO_XML);
		else
			System.out.println(MESSAGGIO_XML_ERRORE);
	}
}