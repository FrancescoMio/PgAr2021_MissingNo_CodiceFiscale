/**
 * 
 */
package codice_fiscale;

/**
 * @author FRANCESCO MIO
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
	/**
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
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	
	public void setCodiceFiscale(String codice) {
		codiceFiscale = codice;
	}
	
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the cognome
	 */
	public String getCognome() {
		return cognome;
	}
	/**
	 * @param cognome the cognome to set
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	/**
	 * @return the comuneNascita
	 */
	public String getComuneNascita() {
		return comuneNascita;
	}
	/**
	 * @param comuneNascita the comuneNascita to set
	 */
	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}
	public String getComuneCodice() {
		return comuneCodice;
	}
	
	public void setComuneCodice(String comuneCodice) {
		this.comuneCodice = comuneCodice;
	}
	/**
	 * @return the sesso
	 */
	public char getSesso() {
		return sesso;
	}
	/**
	 * @param sesso the sesso to set
	 */
	public void setSesso(char sesso) {
		this.sesso = sesso;
	}
	/**
	 * @return the giornoNascita
	 */
	public int getGiornoNascita() {
		return giornoNascita;
	}
	/**
	 * @param giornoNascita the giornoNascita to set
	 */
	public void setGiornoNascita(int giornoNascita) {
		this.giornoNascita = giornoNascita;
	}
	/**
	 * @return the meseNascita
	 */
	public int getMeseNascita() {
		return meseNascita;
	}
	/**
	 * @param meseNascita the meseNascita to set
	 */
	public void setMeseNascita(int meseNascita) {
		this.meseNascita = meseNascita;
	}
	/**
	 * @return the annoNascita
	 */
	public int getAnnoNascita() {
		return annoNascita;
	}
	/**
	 * @param annoNascita the annoNascita to set
	 */
	public void setAnnoNascita(int annoNascita) {
		this.annoNascita = annoNascita;
	}

	@Override
	public String toString() {
		return "Persona [nome = " + nome + ", cognome = " + cognome + ", comuneNascita = " + comuneNascita + ", sesso = "
				+ sesso + ", giornoNascita = " + giornoNascita + ", meseNascita = " + meseNascita + ", annoNascita = "
				+ annoNascita + ", codiceFiscale = " + codiceFiscale + "]";
	}
	
}
