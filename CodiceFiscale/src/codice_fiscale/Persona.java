package codice_fiscale;

/**
 * Classe rappresentante una generica persona con i relativi dati
 *
 */
public class Persona {
	private String nome;
	private String cognome;
	private String comuneNascita;
	private String comuneCodice;
	private char sesso;
	private int giornoNascita;
	private int meseNascita;
	private int annoNascita;
	private String codiceFiscale;
	private String assenza;
	
	/**
	 * Costruttore della classe Persona
	 * 
	 * @param nome
	 * @param cognome
	 * @param comuneNascita
	 * @param sesso
	 * @param giornoNascita
	 * @param meseNascita
	 * @param annoNascita
	 */
	public Persona(String nome, String cognome, String comuneNascita, char sesso, int giornoNascita, int meseNascita, int annoNascita) {
		this.nome = nome;
		this.cognome = cognome;
		this.comuneNascita = comuneNascita;
		this.sesso = sesso;
		this.giornoNascita = giornoNascita;
		this.meseNascita = meseNascita;
		this.annoNascita = annoNascita;
	}
	/**
	 * Metodo che ritorna l'attributo codiceFiscale di una persona
	 * @return
	 */
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	/**
	 * Metodo che setta l'attributo codiceFiscale di una persona
	 * @param codice
	 */
	public void setCodiceFiscale(String codice) {
		codiceFiscale = codice;
	}
	
	/**
	 * Metodo che ritorna l'attributo nome di una persona
	 * @return
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * Metodo che setta l'attributo nome di una persona
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * Metodo che ritorna l'attributo cognome di una persona
	 * @return
	 */
	public String getCognome() {
		return cognome;
	}
	/**
	 * Metodo che setta l'attributo cognome di una persona
	 * @return
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	/**
	 * Metodo che ritorna l'attributo comuneNascita di una persona
	 * @return
	 */
	public String getComuneNascita() {
		return comuneNascita;
	}
	/**
	 * Metodo che setta l'attributo comuneNascita di una persona
	 * @return
	 */
	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}
	/**
	 * Metodo che ritorna l'attributo comuneCodice di una persona
	 * @return
	 */
	public String getComuneCodice() {
		return comuneCodice;
	}
	
	/**
	 * Metodo che setta l'attributo comuneCodice di una persona
	 * @param comuneCodice
	 */
	public void setComuneCodice(String comuneCodice) {
		this.comuneCodice = comuneCodice;
	}
	/**
	 * Metodo che ritorna l'attributo sesso di una persona
	 * @return
	 */
	public char getSesso() {
		return sesso;
	}
	/**
	 * Metodo che setta l'attributo sesso di una persona
	 * @param
	 */
	public void setSesso(char sesso) {
		this.sesso = sesso;
	}
	/**
	 * Metodo che ritorna l'attributo giornoNascita di una persona
	 * @param
	 */
	public int getGiornoNascita() {
		return giornoNascita;
	}
	/**
	 * Metodo che setta l'attributo giornoNascita di una persona
	 * @param
	 */
	public void setGiornoNascita(int giornoNascita) {
		this.giornoNascita = giornoNascita;
	}
	/**
	 * Metodo che ritorna l'attributo meseNascita di una persona
	 * @param
	 */
	public int getMeseNascita() {
		return meseNascita;
	}
	/**
	 * Metodo che setta l'attributo meseNascita di una persona
	 * @param
	 */
	public void setMeseNascita(int meseNascita) {
		this.meseNascita = meseNascita;
	}
	/**
	 * Metodo che ritorna l'attributo annoNascita di una persona
	 * @param
	 */
	public int getAnnoNascita() {
		return annoNascita;
	}
	/**
	 * Metodo che setta l'attributo annoNascita di una persona
	 * @param
	 */
	public void setAnnoNascita(int annoNascita) {
		this.annoNascita = annoNascita;
	}
	/**
	 * Metodo che ritorna l'attributo assenza di una persona
	 * @param
	 */
	public String getAssenza(){
		return assenza;
	}
	/**
	 * Metodo che setta l'attributo assenza di una persona
	 * @param
	 */
	public void setAssenza(String assenza) {
		this.assenza=assenza;
	}
	/**
	 * Metodo che ritorna la stringa relativa alla data di nascita della persona
	 * @param
	 */
	public String data() {
		return getAnnoNascita()+"-"+getMeseNascita()+"-"+getGiornoNascita();
	}
	
	/**
	 * Metodo toString() che stampa le informazioni relative ad una persona
	 */
	@Override
	public String toString() {
		return "Persona [nome = " + nome + ", cognome = " + cognome + ", comuneNascita = " + comuneNascita + ", sesso = "
				+ sesso + ", giornoNascita = " + giornoNascita + ", meseNascita = " + meseNascita + ", annoNascita = "
				+ annoNascita + ", codiceFiscale = " + codiceFiscale + ", assente? = " + assenza + "]";
	}
	
}
