package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.CheckBox;

public class Controller {
	@FXML

	public AnchorPane Settings_App;
	public AnchorPane Training_App;
	public AnchorPane Main_App;
	public ImageView Main_Image;
	public ListView Main_ListView;
	public ImageView img;
	public Button Side_Menu_btn;
	public GridPane Side_Menu_App;

	public ComboBox plec;
	public TextField imie;
	public TextField nazwisko;
	public TextField waga;
	public TextField wzrost;
	public DatePicker data;
	public Button Save_Click;
	public Button Edit_Click;
	public ScrollPane scrollPane;

	public Button btn_CT;
	public TextField txtbox_minute_CT;
	public TextField txtbox_hour_CT;
	public CheckBox chbox_abs_CT;
	public CheckBox chbox_arms_CT;
	public CheckBox chbox_shoulders_CT;
	public CheckBox chbox_chest_CT;
	public CheckBox chbox_back_CT;
	public CheckBox chbox_legs_CT;
	public RadioButton radioBtn_muscle_CT;
	public RadioButton radioBtn_endurance_CT;
	public RadioButton radioBtn_strength_CT;

	public void start(ActionEvent event) {

	}

	public void SaveClick(ActionEvent event) {
		Profil p1 = new Profil(imie.getText(), nazwisko.getText(), waga.getText(), wzrost.getText(), data.getValue(),
				plec.getValue().toString(), 1);

		try {
			FileOutputStream f = new FileOutputStream(new File("Profil.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(p1);

			o.close();
			f.close();

			imie.setEditable(false);
			nazwisko.setEditable(false);
			wzrost.setEditable(false);
			waga.setEditable(false);
			data.setEditable(false);
			plec.setEditable(false);

			imie.setDisable(true);
			nazwisko.setDisable(true);
			wzrost.setDisable(true);
			waga.setDisable(true);
			data.setDisable(true);
			plec.setDisable(true);

			Edit_Click.setVisible(true);
			Save_Click.setVisible(false);

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}

	}

	public void loadOnStart() {
		loadProfil();

		plec.getItems().addAll("M�czyzna", "Kobieta");

		imie.setEditable(false);
		nazwisko.setEditable(false);
		wzrost.setEditable(false);
		waga.setEditable(false);
		data.setEditable(false);
		plec.setEditable(false);

		imie.setDisable(true);
		nazwisko.setDisable(true);
		wzrost.setDisable(true);
		waga.setDisable(true);
		data.setDisable(true);
		plec.setDisable(true);

		Save_Click.setVisible(false);

		ToggleGroup radioTrainGroup = new ToggleGroup();
		radioBtn_strength_CT.setToggleGroup(radioTrainGroup);
		radioBtn_endurance_CT.setToggleGroup(radioTrainGroup);
		radioBtn_muscle_CT.setToggleGroup(radioTrainGroup);
	}

	private void loadProfil() {
		try {
			FileInputStream fi = new FileInputStream(new File("Profil.txt"));
			ObjectInputStream oi = new ObjectInputStream(fi);
			Profil pr1 = (Profil) oi.readObject();

			String[] dane = pr1.toString().split("\n");

			imie.setText(dane[0]);
			nazwisko.setText(dane[1]);
			waga.setText(dane[2]);
			wzrost.setText(dane[3]);
			data.setValue(LocalDate.parse(dane[4]));
			plec.setValue(dane[5]);

			oi.close();
			fi.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void EditClick(ActionEvent event) {

		imie.setEditable(true);
		nazwisko.setEditable(true);
		wzrost.setEditable(true);
		waga.setEditable(true);
		data.setEditable(true);
		plec.setEditable(true);

		imie.setDisable(false);
		nazwisko.setDisable(false);
		wzrost.setDisable(false);
		waga.setDisable(false);
		data.setDisable(false);
		plec.setDisable(false);

		Edit_Click.setVisible(false);
		Save_Click.setVisible(true);
	}

	public void ChooseImage(MouseEvent event) {
		scrollPane.setVisible(true);
	}

	public void ChooseImageExit(MouseEvent event) {
		scrollPane.setVisible(false);
	}

	public void Create_Training() {
		Training_App.setVisible(true);
		Settings_App.setVisible(false);
		Main_App.setVisible(false);
		Training trainCreate = new Training();

		try {
			int time = Integer.parseInt(txtbox_hour_CT.getText()) + Integer.parseInt(txtbox_minute_CT.getText());
			if (trainCreate.CheckBoxCountTraining() > 0) {
				if (time > 30)
					trainCreate.Generate_Training_Main(time, true);
				else
					trainCreate.Generate_Training_Main(time, false);
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Informacja dla U�YTKOWNIKA");
				alert.setHeaderText(null);
				alert.setContentText("Trudno zrobi� trening bez �wicze�. \nProsz� wybierz co chcesz dzisiaj trenowa�.");
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Informacja dla U�YTKOWNIKA");
			alert.setHeaderText(null);
			alert.setContentText("Przepraszamy. Co� posz�o nie tak. Spr�buj jeszcze raz.");
			alert.showAndWait();
		}
	}

	public void Open_Side_Menu() {

	}
}
