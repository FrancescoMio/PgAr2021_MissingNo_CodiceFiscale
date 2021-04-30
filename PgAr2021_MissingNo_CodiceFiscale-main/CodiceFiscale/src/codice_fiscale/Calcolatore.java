package codice_fiscale;

import java.util.ArrayList;

import javax.xml.stream.XMLStreamException;

/**
 * Questa classe di occupa di generare e verificare codici fiscali
 * 
 */
public class Calcolatore {
	
	static String vocaliGlobali = "AEIOU";
	static String consonantiGlobali="BCDFGHJKLMNPQRSTVWXYZ";
	
	/**
	 * Metodo che ritorna il giorno di nascita di una persona a partire dalla data iniziale
	 * @param dataNascita
	 * @return
	 */
	public static int calcolaGiornoNascita(String dataNascita) {
		String[] parts = dataNascita.split("-");
		String giorno = parts[2];
		int giornoNascita = Integer.parseInt(giorno);
		return giornoNascita;
	}
	/**
	 * Metodo che ritorna il mese di nascita di una persona a partire dalla data iniziale
	 * @param dataNascita
	 * @return
	 */
	public static int calcolaMeseNascita(String dataNascita) {
		String[] parts = dataNascita.split("-");
		String mese = parts[1];
		int meseNascita = Integer.parseInt(mese);
		return meseNascita;
	}
	/**
	 * Metodo che ritorna l'anno di nascita di una persona a partire dalla data iniziale
	 * @param dataNascita
	 * @return
	 */
	public static int calcolaAnnoNascita(String dataNascita) {
		String[] parts = dataNascita.split("-");
		String anno = parts[0];
		int annoNascita = Integer.parseInt(anno);
		return annoNascita;
	}
	/**
	 * Metodo che calcola il codice fiscale di una persona
	 * @param persone
	 * @return
	 */
	public static String generazioneCodiceFiscale(Persona persone) {//Metodo adibito alla creazione del codicice fiscale
		
		String codiceFiscale= "";
		String consonanti="";
		String vocali="";
		char cin;
		
		//Cognome
		
		if (persone.getCognome()==null) //Se il cognome ha dimensione 0, return ERRORE
			return "ERROR";
		for (int i=0;i< persone.getCognome().length(); i++) {
			String lettera= String.valueOf(persone.getCognome().charAt(i));
			if (consonantiGlobali.contains(lettera)) 
				consonanti =consonanti.concat(lettera);
			else if (vocaliGlobali.contains(lettera))
				vocali = vocali.concat(lettera);
			else
				return "ERROR"; //se "lettera" non è ne una vocale, ne una consonante, return ERRORE
				
		}
		for (int i=0; i<consonanti.length()&&codiceFiscale.length()<3;i++) { //Scrivo nel codiceFiscale le consonanti
			codiceFiscale=codiceFiscale.concat(String.valueOf(consonanti.charAt(i)));
		}
		for (int i=0; codiceFiscale.length()<3&&i<vocali.length(); i++) { //Scrivo nel codiceFiscale le vocali (se c'è spazio)
			codiceFiscale=codiceFiscale.concat(String.valueOf(vocali.charAt(i)));
		}
		while(codiceFiscale.length()<3) { //Se il codiceFiscale non è ancora di dimensione 3, riempi gli spazi con 'X'
			codiceFiscale=codiceFiscale.concat("X");
			}
		
		//Nome
		
		consonanti="";
		vocali="";
		if (persone.getNome()==null) //Se il nome ha dimensione 0, return ERRORE
			return "ERROR";
		for (int i=0;i< persone.getNome().length(); i++) {
			String lettera= String.valueOf(persone.getNome().charAt(i));
			if (consonantiGlobali.contains(lettera)) 
				consonanti =consonanti.concat(lettera);
			else if (vocaliGlobali.contains(lettera))
				vocali = vocali.concat(lettera);
			else
				return "ERROR"; //se "lettera" non è ne una vocale, ne una consonante, return ERRORE
		}
		
		if (consonanti.length()>3) { //se ci sono 4 o + consonanti, scrivi nel codiceFiscale le consonanti opportune
			codiceFiscale=codiceFiscale.concat(String.valueOf(consonanti.charAt(0)));
			codiceFiscale=codiceFiscale.concat(String.valueOf(consonanti.charAt(2)));
			codiceFiscale=codiceFiscale.concat(String.valueOf(consonanti.charAt(3)));
		}
		
		else  {
			for (int i=0; i<consonanti.length()&&codiceFiscale.length()<6;i++) {	//Scrivo nel codiceFiscale le consonanti
				codiceFiscale=codiceFiscale.concat(String.valueOf(consonanti.charAt(i)));
			}
			for (int i=0; codiceFiscale.length()<6&&i<vocali.length(); i++) {	//Scrivo nel codiceFiscale le vocali (se c'è spazio)
				codiceFiscale=codiceFiscale.concat(String.valueOf(vocali.charAt(i)));
			}
				while(codiceFiscale.length()<6) {	//Se il codiceFiscale non è ancora di dimensione 3, riempi gli spazi con 'X'
					codiceFiscale=codiceFiscale.concat("x");
					}
				}
		//Anno di nascita
		if ((persone.getAnnoNascita()==-1)) 
			return "ERROR";
		String anno= String.valueOf((persone.getAnnoNascita()%100));//Prendo solo le ultime due cifre dell'anno
		if (anno.length()==1) { //Se l'anno ha dimensione 1, aggiungi uno zero prima
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
			return "ERROR"; //Se il mese non esiste, return ERROR
			}
		codiceFiscale= codiceFiscale.concat(mese);
		
		//Giorno di nascita
		
		if (persone.getGiornoNascita()<1)
			return "ERROR";
		
		if(controlGiorno(persone.getAnnoNascita(), codiceFiscale.charAt(8), persone.getGiornoNascita())) 
			return "ERROR";
		
		if (persone.getSesso()=='N')
			return "ERROR";
				
		if (persone.getSesso()=='F') //Se sei una donna, aggiungi 40 al giorno
			codiceFiscale=codiceFiscale.concat(String.valueOf(persone.getGiornoNascita()+40));
		
		else {
			if (persone.getGiornoNascita()<10)
			codiceFiscale=  codiceFiscale.concat("0" + String.valueOf(persone.getGiornoNascita())); //Se il giorno ha solo una cifra, aggiungi 0 prima
			else
			codiceFiscale=codiceFiscale.concat(String.valueOf(persone.getGiornoNascita()));
		}
		//Comune di nascita
		
		if (persone.getComuneCodice().equals("N"))
			return "ERROR";
		codiceFiscale= codiceFiscale.concat(persone.getComuneCodice());
		//Carattere di Controllo
		cin = creazioneCin(codiceFiscale);
		codiceFiscale = codiceFiscale.concat(String.valueOf(cin));
		return codiceFiscale;
	}
	
	/**
	 * Metodo che calcola il codice di controllo del codice fiscale di una persona
	 * @param codiceFiscale
	 * @return
	 */
	public static char creazioneCin(String codiceFiscale) {
		if (codiceFiscale.length()==16)
				codiceFiscale= codiceFiscale.substring(0, codiceFiscale.length() - 1);
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
				somma += ascii - '0';
			else 
				somma += ascii - 'A';
		}
		//String lettereDispari[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		int dispari[] = {1,0,5,7,9,13,15,17,19,21,2,4,18,20,11,3,6,8,12,14,16,10,22,25,24,23};
		int [] dispari2 = {1,0,5,7,9,13,15,17,19,21};
		for(int i = 0; i < charDispari.size(); i++) {
			int ascii = charDispari.get(i);
			if(ascii < 'A') {
				int numero = ascii - '0';
				int valore = dispari2[numero];
				somma += valore;
			}
			else {
				int numero = ascii - 'A';
				int valore = dispari[numero];
				somma += valore;
			}
		}
		int resto = somma % 26;
		int ascii = resto + 65;
		return ((char)ascii);
	}
	
	/**
	 * Controlla se un codice fiscale è invalido, e se si, return true
	 * @param codiceFiscale
	 * @return
	 * @throws XMLStreamException
	 */
	public static boolean codiceInvalido(String codiceFiscale) throws XMLStreamException {
		String mesi = "ABCDEHLMPRST";
		//Controllo validità generale
		
		if (codiceFiscale==null)
			return true;
		
		if (codiceFiscale.length()!=16)
			return true;
		
		
		//Controllo caratteri
		for (int i=0; i<codiceFiscale.length(); i++) {
			if (i==6 ||i==7 || i==9 || i==10 || i==12 ||i==13 ||i==14) { //Controllo posizione numeri
				if (codiceFiscale.charAt(i)<48 || codiceFiscale.charAt(i)>57) //Controllo se il numero è un numero
					return true;
					
				}
			else //Se non è un numero è una lettera
				if (codiceFiscale.charAt(i)<65 || codiceFiscale.charAt(i)>90) //Controllo se la lettera è una lettera 
					return true;
			}
		
		//Controllo validità mese
		if (!mesi.contains(String.valueOf(codiceFiscale.charAt(8)))) //Controllo se la lettera del mese sia giusta
			return true;
		
		//Controllo validità giorno
		
		int giorno = ((codiceFiscale.charAt(9)-48)*10 +(codiceFiscale.charAt(10)-48)); //Prendo il giorno
		
		if (giorno>31) //Controllo se maschio o femmina
			giorno = giorno-40;
		
		if (giorno<1 || giorno >31) //Controllo se il giorno è valido
			return true;
		
		int anno= ((codiceFiscale.charAt(6)-48)*10 +(codiceFiscale.charAt(7)-48)); //Prendo l'anno
		
		if (controlGiorno(anno, codiceFiscale.charAt(8), giorno)) //Verifico se il giorno esiste nel mese
			return true;
		
		//Controllo se il comune esiste
		
		String comune= "" + codiceFiscale.charAt(11) + codiceFiscale.charAt(12) + codiceFiscale.charAt(13) + codiceFiscale.charAt(14);
		
		if (!InputOutput.verificaComune(comune))
			return true;
		
		//Controllo se il Cin è corretto
		if (codiceFiscale.charAt(15)!=creazioneCin(codiceFiscale))
			return true;
		
		//Controllo validità Cognome
		String cognome = "" +codiceFiscale.charAt(0) + codiceFiscale.charAt(1) + codiceFiscale.charAt(2); 
		if (controlNomi(cognome))
			return true;
			
		
		//Controllo validità Nome
		String nome = "" +codiceFiscale.charAt(3) + codiceFiscale.charAt(4) + codiceFiscale.charAt(5);
		
		if (controlNomi(nome))
			return true;
		
		return false; //Se non ci sono problemi, return false
		
	}
	
	/**
	 * Metodo adibito a controllare se il nome/cognome (di un codice fiscale) è valido, return false se lo è
	 * @param nome
	 * @return
	 */
	public static boolean controlNomi(String nome){ //
		
		for (int i=0; i<2; i++) {
			if (vocaliGlobali.contains(String.valueOf(nome.charAt(i)))) {
				for (int j=i+1; j<3; j++) {
					if (!vocaliGlobali.contains(String.valueOf(nome.charAt(j)))) {
						if('X'==nome.charAt(j)) {
							if (j==1 && 'X'!=nome.charAt(j+1)) {
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
	
	/**
	 * Metodo adibito a verificare se un particolare giorno esiste, se esiste return false
	 * @param anno
	 * @param mese
	 * @param giorno
	 * @return
	 */
	public static boolean controlGiorno(int anno, char mese, int giorno) {//
		if (mese=='A' || mese=='C' || mese=='E' || mese=='L' || mese=='M' || mese=='R' || mese=='T') {
			if (giorno>31)
				return true;
		}
		
		if (mese=='D' || mese=='H' || mese=='P' || mese=='S') {
			if (giorno>30)
				return true;
		}
		
		if (mese=='B') {
			if (giorno>28)
				if (anno%4!=0)
				return true;
		}
		
		return false;
		
	}
	
}
