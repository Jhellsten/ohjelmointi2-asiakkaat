package model;

public class Henkilo {
	private String etunimi, sukunimi, sposti, puhelin;
	public Henkilo() {
		super();		
	}
	
	public Henkilo(String etunimi, String sukunimi, String sposti, String puhelin) {
		super();
		this.etunimi = etunimi;
		this.sukunimi = sukunimi;
		this.sposti = sposti;
		this.puhelin = puhelin;
	}
	public String getEtunimi() {
		return etunimi;
	}
	public void setEtunimi(String etunimi) {
		this.etunimi = etunimi;
	}
	public String getSukunimi() {
		return sukunimi;
	}
	public void setSukunimi(String sukunimi) {
		this.sukunimi = sukunimi;
	}
	public String getSposti() {
		return sposti;
	}
	public void setSposti(String sposti) {
		this.sposti = sposti;
	}
	public String getPuhelin() {
		return puhelin;
	}
	public void setPuhelin(String puhelin) {
		this.puhelin = puhelin;
	}
	@Override
	public String toString() {
		return "Nimi: " + etunimi + " Sukunimi " + sukunimi + " Puhelin: " + puhelin + " sposti: " + sposti;
	}

	
	
}
