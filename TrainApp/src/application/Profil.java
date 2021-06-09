package application;

import java.io.Serializable;
import java.time.LocalDate;

public class Profil implements Serializable {

	private String imie;
	private String nazwisko;
	private String waga;
	private String wzrost;
	private LocalDate data;
	private String plec;
	private String zdjecie;
	private String radio;
	private Boolean BMI;
	private String ulubCw1;
	private Boolean ulubCw1Check;
	
	Profil(String imie, String nazwisko, String waga, String wzrost,LocalDate data,String plec,
			String zdjecie,String radio,Boolean BMI,String ulubCw1,Boolean ulubCw1Check) {
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.waga = waga;
		this.wzrost = wzrost;
		this.data = data;
		this.plec = plec;
		this.zdjecie = zdjecie;
		this.radio = radio;
		this.BMI = BMI;
		this.ulubCw1 = ulubCw1;
		this.ulubCw1Check = ulubCw1Check;
	}
	
	@Override
	public String toString() {
		return imie + "\n" + nazwisko + "\n" + waga + "\n" + wzrost + "\n" + data + "\n" + plec + "\n" + zdjecie 
				+ "\n" + radio + "\n" + BMI + "\n" + ulubCw1
				+ "\n" + ulubCw1Check;
	}
}
