package application;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javafx.beans.property.BooleanProperty;

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
	private String ulubCw2;
	private Boolean ulubCw1Check;
	private Boolean ulubCw2Check;
	
	Profil(String imie, String nazwisko, String waga, String wzrost,LocalDate data,String plec,
			String zdjecie,String radio,Boolean BMI,String ulubCw1,String ulubCw2,Boolean ulubCw1Check,Boolean ulubCw2Check) {
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
		this.ulubCw2 = ulubCw2;
		this.ulubCw1Check = ulubCw1Check;
		this.ulubCw2Check = ulubCw2Check;
	}
	
	@Override
	public String toString() {
		return imie + "\n" + nazwisko + "\n" + waga + "\n" + wzrost + "\n" + data + "\n" + plec + "\n" + zdjecie 
				+ "\n" + radio + "\n" + BMI + "\n" + ulubCw1 + "\n" + ulubCw2
				+ "\n" + ulubCw1Check + "\n" + ulubCw2Check;
	}
}
