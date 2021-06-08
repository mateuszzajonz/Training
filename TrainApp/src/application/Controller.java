package application;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.sqlite.SQLiteDataSource;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;

public class Controller {
	@FXML

	public AnchorPane Settings_App;
	public AnchorPane Training_App;
	public AnchorPane Main_App;
	public ImageView Main_Image;
	public ImageView img;
	public Button Side_Menu_btn;
	public AnchorPane Side_Menu_App;

	// settings app
	public ComboBox plec, comboCw2, comboCw1;
	public TextField imie, nazwisko, waga, wzrost;
	public DatePicker data;
	public Label label1;
	public Button Save_Click, Edit_Click;
	public RadioButton ostatnitrening, radioGraf;
	public ScrollPane scrollPane;
	public ToggleGroup radioBtns;
	public CheckBox checkBMI, checkCW1, checkCW2;
	public ImageView i1, i2, i3, i4, i5, i6;

	// main app
	public Label Main_Imie, Main_Nazwisko, Main_Waga, Main_Wzrost, Main_BMI, lblBMI;
	public LineChart<String, String> Main_Graf;
	public TextArea Main_OstatniTrening;

	//Side
//	public TableView<Trainings> table;
//	public ObservableList<Trainings> obslist = FXCollections.observableArrayList();
//	public TableColumn<Trainings,String> idColumn = new TableColumn<>("ID");
//	public TableColumn<Trainings,String> dateColumn = new TableColumn<>("Data");
//	public TableColumn<Trainings,String> nameColumn = new TableColumn<>("Nazwa");
	
	// training app
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

	String radioGrp, imageId;
	Image image;
	String[] dane;
	SQLiteDataSource ds = null;

	public void SaveClick(ActionEvent event) {

		if (radioGraf.isSelected()) {
			radioGrp = "Graf";
		} else {
			radioGrp = "ostatnitrening";
		}

		Profil p1 = new Profil(imie.getText(), nazwisko.getText(), waga.getText(), wzrost.getText(), data.getValue(),
				plec.getValue().toString(), imageId, radioGrp, checkBMI.isSelected(), comboCw1.getValue().toString(),
				comboCw2.getValue().toString(), checkCW1.isSelected(), checkCW2.isSelected());

		try {
			FileOutputStream f = new FileOutputStream(new File("Profil.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(p1);

			o.close();
			f.close();

			label1.setDisable(true);
			imie.setDisable(true);
			nazwisko.setDisable(true);
			wzrost.setDisable(true);
			waga.setDisable(true);
			data.setDisable(true);
			plec.setDisable(true);
			img.setDisable(true);
			radioGraf.setDisable(true);
			ostatnitrening.setDisable(true);
			checkBMI.setDisable(true);
			comboCw1.setDisable(true);
			comboCw2.setDisable(true);
			checkCW1.setDisable(true);
			checkCW2.setDisable(true);

			Edit_Click.setVisible(true);
			Save_Click.setVisible(false);

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}
	}

	public void loadDB() {
		try {
			ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:sample.dbTraining.db");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

	}

	public void loadOnStart() {
		loadProfil();
		loadDB();
		loadMain();
		plec.getItems().addAll("Mê¿czyzna", "Kobieta");
		comboCw1.getItems().addAll("Plank", "V-ups", "Hollow Hold");
		comboCw2.getItems().addAll("Plank", "V-ups", "Hollow Hold");

		label1.setDisable(true);
		imie.setDisable(true);
		nazwisko.setDisable(true);
		wzrost.setDisable(true);
		waga.setDisable(true);
		data.setDisable(true);
		plec.setDisable(true);
		img.setDisable(true);
		radioGraf.setDisable(true);
		ostatnitrening.setDisable(true);
		checkBMI.setDisable(true);
		comboCw1.setDisable(true);
		comboCw2.setDisable(true);
		checkCW1.setDisable(true);
		checkCW2.setDisable(true);

		Save_Click.setVisible(false);
		ToggleGroup radioTrainGroup = new ToggleGroup();
		radioBtn_strength_CT.setToggleGroup(radioTrainGroup);
		radioBtn_endurance_CT.setToggleGroup(radioTrainGroup);
		radioBtn_muscle_CT.setToggleGroup(radioTrainGroup);
		radioBtn_strength_CT.setSelected(true);
		Training train = new Training();
		train.AddExerciseFromDatabase();
	}

	private void loadMain() {
		Main_Image.setImage(image);
		Main_Imie.setText(dane[0]);
		Main_Nazwisko.setText(dane[1]);
		Main_Waga.setText(dane[2] + " kg");
		Main_Wzrost.setText(dane[3] + " cm");

		if (radioGraf.isSelected()) {
			Main_Graf.setVisible(true);
			Main_OstatniTrening.setVisible(false);
		} else {
			Main_OstatniTrening.setVisible(true);
			Main_Graf.setVisible(false);
		}
		if (!checkBMI.isSelected()) {
			Main_BMI.setVisible(false);
			lblBMI.setVisible(false);
		} else {
			Main_BMI.setVisible(true);
			lblBMI.setVisible(true);
		}
		Main_OstatniTrening.clear();
		if (checkCW1.isSelected()) {
			Main_OstatniTrening
					.appendText(dane[9] + "\nOstatni trening" + "" + "\nMax" + "" + "\nIloœæ powtórzeñ" + "");
		}
		if (checkCW1.isSelected() && checkCW1.isSelected()) {
			Main_OstatniTrening.appendText("\n\n");
		}
		if (checkCW2.isSelected()) {
			Main_OstatniTrening
					.appendText(dane[10] + "\nOstatni trening" + "" + "\nMax" + "" + "\nIloœæ powtórzeñ" + "");
		}
		Double BMI = (Double.parseDouble(dane[3]) / Double.parseDouble(dane[2]) * 10);
		Double a = BMI;
		a *= 100; // pi = pi * 100;
		a = (double) Math.round(a);
		a /= 100; // pi = pi / 100;
		Main_BMI.setText(a.toString());
	}

	private void loadProfil() {
		try {
			FileInputStream fi = new FileInputStream(new File("Profil.txt"));
			ObjectInputStream oi = new ObjectInputStream(fi);
			Profil pr1 = (Profil) oi.readObject();

			dane = pr1.toString().split("\n");

			imie.setText(dane[0]);
			nazwisko.setText(dane[1]);
			waga.setText(dane[2]);
			wzrost.setText(dane[3]);
			data.setValue(LocalDate.parse(dane[4]));
			plec.setValue(dane[5]);
			SetImage(dane[6]);

			if (dane[7].equals("Graf")) {
				radioGraf.setSelected(true);
			} else {
				ostatnitrening.setSelected(true);
			}

			checkBMI.setSelected(Boolean.parseBoolean(dane[8]));
			comboCw1.setValue(dane[9]);
			comboCw2.setValue(dane[10]);
			checkCW1.setSelected(Boolean.parseBoolean(dane[11]));
			checkCW2.setSelected(Boolean.parseBoolean(dane[12]));

			oi.close();
			fi.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void EditClick(ActionEvent event) {
		label1.setDisable(false);
		imie.setDisable(false);
		nazwisko.setDisable(false);
		wzrost.setDisable(false);
		waga.setDisable(false);
		data.setDisable(false);
		plec.setDisable(false);
		img.setDisable(false);
		radioGraf.setDisable(false);
		ostatnitrening.setDisable(false);
		checkBMI.setDisable(false);
		comboCw1.setDisable(false);
		comboCw2.setDisable(false);
		checkCW1.setDisable(false);
		checkCW2.setDisable(false);

		Edit_Click.setVisible(false);
		Save_Click.setVisible(true);
	}

	public void ChooseImage(MouseEvent event) {
		scrollPane.setVisible(true);
	}

	public void SetImage(String imageID) {
		switch (imageID) {
		case "i1":
			image = new Image(getClass().getResourceAsStream("1.png"));
			break;
		case "i2":
			image = new Image(getClass().getResourceAsStream("2.png"));
			break;
		case "i3":
			image = new Image(getClass().getResourceAsStream("3.png"));
			break;
		case "i4":
			image = new Image(getClass().getResourceAsStream("4.png"));
			break;
		case "i5":
			image = new Image(getClass().getResourceAsStream("5.png"));
			break;
		case "i6":
			image = new Image(getClass().getResourceAsStream("6.png"));
			break;
		default:
			image = new Image(getClass().getResourceAsStream("profilowe.png"));
		}
		img.setImage(image);
	}

	public void ChooseImageExit(MouseEvent event) {

		scrollPane.setVisible(false);

		String s = event.getTarget().toString();
		imageId = s.substring(13, 15);
		SetImage(imageId);
	}

	public void MainClick(ActionEvent event) {
		Main_App.setVisible(true);
		Settings_App.setVisible(false);
		Training_App.setVisible(false);
		scrollPane.setVisible(false);
		loadMain();
	}

	public void Create_Training(ActionEvent event) {
		Training trainCreate = new Training();

		if (txtbox_minute_CT.getText().equals("")) {
			txtbox_minute_CT.setText("0");
		}
		if (txtbox_hour_CT.getText().equals("")) {
			txtbox_hour_CT.setText("0");
		}

		try {
			int time = Integer.parseInt(txtbox_hour_CT.getText()) * 60 + Integer.parseInt(txtbox_minute_CT.getText());
			int count = trainCreate.CheckBoxCountTraining();
			if (count > 0) {
				if (time >= 20 * count)
					trainCreate.Generate_Training_Main(time, true, count);
				else
					trainCreate.Generate_Training_Main(time, false, count);
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Informacja dla U¯YTKOWNIKA");
				alert.setHeaderText(null);
				alert.setContentText("Trudno zrobiæ trening bez æwiczeñ. \nProszê wybierz co chcesz dzisiaj trenowaæ.");
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Informacja dla U¯YTKOWNIKA");
			alert.setHeaderText(null);
			alert.setContentText("Przepraszamy. Coœ posz³o nie tak:\n\n" + e + "\n\nSpróbuj jeszcze raz.");
			alert.showAndWait();
		}
	}

	public void Open_Side_Menu(ActionEvent event) {
		Side_Menu_App.setVisible(true);
		Settings_App.setVisible(false);
		Main_App.setVisible(false);
		Training_App.setVisible(false);
		scrollPane.setVisible(false);
//		try {
//			Connection con = ds.getConnection();
//			ResultSet rs = con.createStatement().executeQuery("Select * from Trainings");
//			while(rs.next()) {
//				obslist.add(new Trainings(rs.getString("ID_training"),rs.getString("Date"),rs.getString("Name")));
//			}
//		}catch(SQLException e){
//			System.out.print("B³¹d");
//		}
//
//		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
//		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//
//		
//
//		table.setItems(obslist);
////		table.getColumns().addAll(idColumn,dateColumn,nameColumn);					
	}

	public void ProfilClick(ActionEvent event) {
		Side_Menu_App.setVisible(false);
		Settings_App.setVisible(true);
		Main_App.setVisible(false);
		Training_App.setVisible(false);
		scrollPane.setVisible(false);
	}

	public void TrainingClick(ActionEvent event) {
		Settings_App.setVisible(false);
		Main_App.setVisible(false);
		Training_App.setVisible(true);
		scrollPane.setVisible(false);
		Side_Menu_App.setVisible(false);
	}

	public void Help_CT(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Pomoc");
		alert.setHeaderText(null);
		alert.setContentText(
				"1)\tWybór partii miêœniowych wp³ywa na rodzaj treningu.\n2)\tProgram potrzebuje minimum 20 minut na ka¿d¹ partiê cia³a do poprawnego dzia³ania."
						+ "\n3)\tWybór rodzaju treningu wp³ywa na liczbê powtórzeñ jak i d³ugoœæ przerwy.\n4)\t Je¿eli wyskakuje b³¹d, upewnij siê, ¿e dobrze wpisa³eœ czas."
						+ "\n5)\tPrzy braku czasu na trening, wyœwietli siê komunikat, albo zostanie stworzony krótki trening wzmacniaj¹cy");
		alert.showAndWait();
	}
}
