package codice_fiscale;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.xml.sax.SAXException;

//import jdk.internal.util.xml.impl.Input;

public class CodiceFiscaleMain {
	static ArrayList<Persona> persone = new ArrayList<Persona>();
	
	public static void main(String[] args) throws XMLStreamException /*, ParserConfigurationException, TransformerFactoryConfigurationError, SAXException, IOException, TransformerException*/ {
		inputOutput.creaPersone(persone);
		for(int i = 0; i < 100; i++) {
			System.out.println(persone.get(i).toString());
	}
		inputOutput.scritturaXML(persone);
}
}

