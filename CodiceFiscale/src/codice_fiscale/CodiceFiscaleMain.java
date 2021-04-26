package codice_fiscale;

import java.util.ArrayList;
import javax.xml.stream.XMLStreamException;

//import jdk.internal.util.xml.impl.Input;

public class CodiceFiscaleMain {
	static ArrayList<Persona> persone = new ArrayList<Persona>();
	
	public static void main(String[] args) throws XMLStreamException {
		calcolatore.creaPersone(persone);
		for(int i = 0; i < 10; i++) {
			System.out.println(persone.get(i).toString());
			//System.out.println(calcolatore.generazioneCodiceFiscale(persone.get(i)));
			
	}
}
}

