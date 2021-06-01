package application;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Profil implements Serializable {

	private String imie;
	private String nazwisko;
	private String waga;
	private String wzrost;
	private LocalDate data;
	private String plec;
	private int zdjecie;
	
	
	Profil(String imie, String nazwisko, String waga, String wzrost,LocalDate data,String plec,int zdjecie) {
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.waga = waga;
		this.wzrost = wzrost;
		this.data = data;
		this.plec = plec;
		this.zdjecie = zdjecie;
	}
	
	@Override
	public String toString() {
		return imie + "\n" + nazwisko + "\n" + waga + "\n" + wzrost + "\n" + data + "\n" + plec + "\n" + zdjecie;
	}
}
