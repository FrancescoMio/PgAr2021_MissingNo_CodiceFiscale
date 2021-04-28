package codice_fiscale;

import java.util.ArrayList;
import javax.xml.stream.XMLStreamException;

public class CodiceFiscaleMain {
	static ArrayList<Persona> persone = new ArrayList<Persona>();
	
	public static void main(String[] args) throws XMLStreamException /*, ParserConfigurationException, TransformerFactoryConfigurationError, SAXException, IOException, TransformerException*/ {
		InputOutput.creaPersone(persone);
		
		/*for(int i = 0; i < 100; i++) {
			System.out.println(persone.get(i).toString());
		}*/
		InputOutput.scritturaXML(persone);
	}
}

