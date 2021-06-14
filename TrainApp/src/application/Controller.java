package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.sqlite.SQLiteDataSource;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.CheckBox;

public class Controller {
	@FXML

	public AnchorPane Settings_App;
	public AnchorPane Training_App;
	public AnchorPane Main_App;
	public AnchorPane OwnTrain_App;
	public ImageView Main_Image;
	public ImageView img;

	// settings app
	public ComboBox<String> plec, comboCw1;
	public TextField imie, nazwisko, waga, wzrost;
	public DatePicker data;
	public Label label1;
	public Button Save_Click, Edit_Click;
	public RadioButton ostatnitrening, radioGraf;
	public ScrollPane scrollPane;
	public ToggleGroup radioBtns;
	public CheckBox checkBMI;
	public ImageView i1, i2, i3, i4, i5, i6;
	public Boolean blad = false;
	public String raportBledu = "Proszê sprawdziæ poprawnoœæ danych w polach:\n";

	// main app
	public Label Main_Imie, Main_Nazwisko, Main_Waga, Main_Wzrost, Main_BMI, lblBMI;
	CategoryAxis xAxis = new CategoryAxis();
	NumberAxis yAxis = new NumberAxis();
	public LineChart<String, Number> Main_Graf = new LineChart<String, Number>(xAxis, yAxis);
	public TextArea Main_OstatniTrening;
	public Button Main_DeleteOne, Main_DeleteAll;
	public Button Main_Show;

	// Side
	public TableView<finishedTrainings> table;
	public TableColumn<finishedTrainings, String> dateCol;
	public TableColumn<finishedTrainings, String> nameCol;
	ObservableList<finishedTrainings> obslist = FXCollections.observableArrayList();
	ObservableList<String> obslist2 = FXCollections.observableArrayList();
	ObservableList<String> obslist3 = FXCollections.observableArrayList();

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
	public GridPane Create_grid_CT;
	public GridPane Accept_grid_CT;
	public Button goBack_acceptGrid_CT;
	public Button accept_acceptGrid_CT;
	public Button reroll_acceptGrid_CT;
	public TextArea txtArea_acceptGrid_CT;
	public Training trainCreate = new Training();
	public static String trainingToDB;
	public Button btn_hideTraining;
	public TextArea Show_Training_Table;

	// create own training
	public Button Train_Add;
	public Button Train_Del;
	public Button Train_Save;
	public ComboBox<String> Train_ex;
	public ComboBox<String> Train_time;
	public ComboBox<String> Train_series;
	public ComboBox<String> Train_series1;
	public TextField Train_Weight;
	public ListView<String> Train_list;
	String training;
	ArrayList<String> Train_AList = new ArrayList<String>();

	// change fix maxes and create training
	public AnchorPane Anchor_Fix_OT;
	public AnchorPane Anchor_Create_OT;
	public TextField fix_Max;
	public TableColumn<ExercisesDB, String> exerciseCol;
	public TableColumn<ExercisesDB, String> maxCol;
	public TableView<ExercisesDB> table_Maxes;
	ObservableList<ExercisesDB> obslist1 = FXCollections.observableArrayList();

	String radioGrp, imageId;
	Image image;
	String[] dane;
	SQLiteDataSource ds = null;

	
	//Data storage
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
		loadDB();
		try {
			Connection con = ds.getConnection();
			ResultSet rs = con.createStatement().executeQuery("Select exercises from Exercises");
			while (rs.next()) {
				obslist2.add(rs.getString("exercises"));
			}
		} catch (SQLException e) {
			System.out.print("B³¹d" + e);
		} catch (Exception e) {

		}
		plec.getItems().clear();
		plec.getItems().addAll("Mê¿czyzna", "Kobieta");
		comboCw1.getItems().clear();
		comboCw1.getItems().addAll(obslist2);
		Train_ex.getItems().clear();
		Train_ex.getItems().addAll(obslist2);
		Train_series.getItems().clear();
		Train_series.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
				"16", "17", "18", "19", "20");
		Train_series1.getItems().clear();
		Train_series1.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
				"16", "17", "18", "19", "20");
		Train_time.getItems().clear();
		Train_time.getItems().addAll("0:30", "1:00", "1:30", "2:00", "2:30", "3:00", "3:30", "4:00", "4:30", "5:00");
		loadProfil();
		if (!blad) {
			loadMain();
			LoadTableView();
			Maxes_Table();
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

			Save_Click.setVisible(false);
			ToggleGroup radioTrainGroup = new ToggleGroup();
			radioBtn_strength_CT.setToggleGroup(radioTrainGroup);
			radioBtn_endurance_CT.setToggleGroup(radioTrainGroup);
			radioBtn_muscle_CT.setToggleGroup(radioTrainGroup);
			radioBtn_strength_CT.setSelected(true);
			Training train = new Training();
			train.AddExerciseFromDatabase();
		}
	}

	private void loadMain() {
		Main_Image.setImage(image);
		Main_Imie.setText(dane[0]);
		Main_Nazwisko.setText(dane[1]);
		Main_Waga.setText(dane[2] + " kg");
		Main_Wzrost.setText(dane[3] + " cm");
		LoadTableView();
		if (radioGraf.isSelected()) {
			LineChart();
			Main_Graf.setVisible(true);
			table.setVisible(false);
			Main_Show.setVisible(false);
			Main_DeleteAll.setVisible(false);
			Main_DeleteOne.setVisible(false);

		} else {
			table.setVisible(true);
			Main_Show.setVisible(true);
			Main_Graf.setVisible(false);
			Main_DeleteAll.setVisible(true);
			Main_DeleteOne.setVisible(true);
		}
		if (!checkBMI.isSelected()) {
			Main_BMI.setVisible(false);
			lblBMI.setVisible(false);
		} else {
			Main_BMI.setVisible(true);
			lblBMI.setVisible(true);
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
			for (int i = 0; i < dane.length; i++) {
				if (dane[i].equals("")) {
					blad = true;
					break;
				}
			}
			if (!pr1.equals(null)) {
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
			}
			oi.close();
			fi.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			blad = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (blad) {
			System.out.println("Error initializing stream");
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("");
			alert.setHeaderText(null);
			alert.setContentText("Brak danych");
			alert.showAndWait();
			Settings_App.setVisible(true);
			Main_App.setVisible(false);
			Training_App.setVisible(false);
			scrollPane.setVisible(false);
			OwnTrain_App.setVisible(false);
			Save_Click.setVisible(true);
			Edit_Click.setVisible(false);
		}
	}

	// Change_Maxes
	public void Change_Max(ActionEvent event) {
		try {
			double max = Double.parseDouble(fix_Max.getText());
			Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement("UPDATE Exercises SET max = " + max + " WHERE id = ?;");
			ExercisesDB train = table_Maxes.getSelectionModel().getSelectedItem();
			ps.setString(1, train.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.print("B³¹d" + e);
		}catch (Exception e)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("");
			alert.setHeaderText(null);
			alert.setContentText("le wpisano maksa, sprawdŸ poprawnoœæ wprowadzonych danych.");
			alert.showAndWait();

		}
		Maxes_Table();
	}

	public void Change_Maxes_OT(ActionEvent event) {
		Anchor_Fix_OT.setVisible(true);
		Anchor_Create_OT.setVisible(false);
	}

	public void Create_Training_OT(ActionEvent event) {
		Anchor_Fix_OT.setVisible(false);
		Anchor_Create_OT.setVisible(true);
	}

	public void Maxes_Table() {
		table_Maxes.getItems().clear();
		try {
			Connection con = ds.getConnection();
			ResultSet rs = con.createStatement().executeQuery("Select * from Exercises");
			while (rs.next()) {
				obslist1.add(new ExercisesDB(rs.getString("id"), rs.getString("type"), rs.getString("exercises"),
						rs.getString("max")));
			}
		} catch (SQLException e) {
			System.out.print("B³¹d");
		}

		exerciseCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExercises()));
		maxCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMax()));

		table_Maxes.setItems(obslist1);
	}

	// OwnTraining_App
	public void SaveExercise(ActionEvent event) {
		try {
			for (int i = 0; i < Train_AList.size(); i++) {
				if (i == 0) {
					training = Train_AList.get(0);
				} else
					training = training + "; " + Train_AList.get(i);
			}
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDateTime now = LocalDateTime.now();
			Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT INTO Trainings(ID_training,Date,Name) VALUES(?,?,?)");
			ps.setString(2, dtf.format(now));
			ps.setString(3, training);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.print("B³¹d" + e);
		}
	}

	public void AddExercise(ActionEvent event) {
		try
		{
			
			@SuppressWarnings("unused")
			double d = Double.parseDouble(Train_Weight.getText());		
			if (!(Train_ex.getValue() == null) && !(Train_series.getValue() == null) && !(Train_series1.getValue() == null)
					&& !(Train_Weight.equals(null)) && !(Train_time.getValue() == null)) {
				Train_list.getItems().clear();
				Train_AList.add(Train_ex.getValue() + "," + Train_series.getValue() + "x" + Train_series1.getValue() + ","
						+ Train_Weight.getText() + "," + Train_time.getValue());
				Train_list.getItems().addAll(Train_AList);
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("");
				alert.setHeaderText(null);
				alert.setContentText("Nie wype³niono wszystkich danych");
				alert.showAndWait();
			}
		}catch (NumberFormatException e)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("");
			alert.setHeaderText(null);
			alert.setContentText("SprawdŸ poprawnoœæ wprowadzonej wagi w treningu.");
			alert.showAndWait();
		}		
	}

	public void DelExercise(ActionEvent event) {
		int a = Train_list.getSelectionModel().getSelectedIndex();
		if (a >= 0) {
			Train_AList.remove(a);
			Train_list.getItems().remove(a);
		}
	}
	
	// Training_App
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

	public void GoBack_CT_Action() {
		trainCreate.listOfExercises.clear();
		trainCreate.ClearExerciseLists();
		txtArea_acceptGrid_CT.clear();
		Accept_grid_CT.setVisible(false);
		Create_grid_CT.setVisible(true);
	}

	public void Reroll_CT_Action() {
		int time = Integer.parseInt(txtbox_hour_CT.getText()) * 60 + Integer.parseInt(txtbox_minute_CT.getText());
		int count = trainCreate.CheckBoxCountTraining();
		if (time >= 20 * count)
			trainCreate.Generate_Training_Main(time, true, count);
		else
			trainCreate.Generate_Training_Main(time, false, count);
	}

	public void Accept_CT_Action() {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDateTime now = LocalDateTime.now();
			Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT INTO Trainings(ID_training,Date,Name) VALUES(?,?,?)");
			ps.setString(2, dtf.format(now));
			ps.setString(3, trainingToDB);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.print("B³¹d" + e);
		}
	}

	public void Create_Training(ActionEvent event) {

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
					trainingToDB = trainCreate.Generate_Training_Main(time, true, count);
				else
					trainingToDB = trainCreate.Generate_Training_Main(time, false, count);
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

	// Setting_App
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

	public void SaveClick(ActionEvent event) {
		blad = false;
		raportBledu = "Proszê sprawdziæ poprawnoœæ danych w polach:\n";
		if (radioGraf.isSelected()) {
			radioGrp = "Graf";
		} else {
			radioGrp = "ostatnitrening";
		}
		if (checkValues()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("");
			alert.setHeaderText(null);
			alert.setContentText(raportBledu);
			alert.showAndWait();
		} else {
			Profil p1 = new Profil(imie.getText(), nazwisko.getText(), waga.getText(), wzrost.getText(),
					data.getValue(), plec.getValue().toString(), imageId, radioGrp, checkBMI.isSelected(),
					comboCw1.getValue().toString(), true);

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

				Edit_Click.setVisible(true);
				Save_Click.setVisible(false);

			} catch (FileNotFoundException e) {
				System.out.println("File not found");
			} catch (IOException e) {
				System.out.println("Error initializing stream");
			}
			loadOnStart();
		}
	}

	public boolean checkValues() {
		Boolean valueBlad = false;
		if (comboCw1.getValue().equals("") || imie.getText().equals("") || nazwisko.getText().equals("")
				|| plec.getValue().equals("") || data.getValue().equals(null)) {
			raportBledu += "Brak wszystkich danych \n";
			valueBlad = true;
		}
		for (int i = 0; i < imie.getText().length(); i++) {
			if (Character.isDigit(imie.getText().charAt(i))) {
				raportBledu += "Imie \n";
				valueBlad = true;
				break;
			}
		}
		for (int i = 0; i < nazwisko.getText().length(); i++) {
			if (Character.isDigit(nazwisko.getText().charAt(i))) {
				raportBledu += "Nazwisko \n";
				valueBlad = true;
				break;
			}
		}
		try {
			@SuppressWarnings("unused")
			double d = Double.parseDouble(wzrost.getText());
			@SuppressWarnings("unused")
			double a = Double.parseDouble(waga.getText());
		} catch (NumberFormatException nfe) {
			raportBledu += "Waga, Wzrost \n";
			valueBlad = true;
		}
		if (data.getValue().equals(null)) {
			raportBledu += "Data urodzenia \n";
			valueBlad = true;
		}
		return valueBlad;
	}
	
	// Main_App
	public void LineChart() {
		Main_Graf.getData().clear();
		String done = "";
		XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
		series.setName(comboCw1.getValue());
		for (int i = 0; i < obslist.size(); i++) {
			done = table.getItems().get(i).name;
			String[] words = done.split(";");
			String[] wordsInside2;
			for (int j = 0; j < words.length; j++) {
				wordsInside2 = words[j].split("\\,");
				if (wordsInside2[0].equals(comboCw1.getValue())) {
					series.getData().add(new XYChart.Data<String, Number>(table.getItems().get(i).date,
							Double.parseDouble(wordsInside2[2])));
					break;
				}
			}
		}
		Main_Graf.getData().add(series);
	}

	public void Delete_Training() {
		try {
			Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM Trainings WHERE ID_training = ?;");
			finishedTrainings train = table.getSelectionModel().getSelectedItem();
			ps.setString(1, train.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.print("B³¹d" + e);
		}
		LoadTableView();
	}

	public void Show_TrainingAll(ActionEvent event) {
		String done = "";
		try {
			done = table.getSelectionModel().getSelectedItem().name;
		} catch (Exception e) {
			System.out.print("B³¹d" + e);
		}
		String[] words = done.split(";");
		try {
			if (!done.equals("")) {
				Show_Training_Table.setVisible(true);
				btn_hideTraining.setVisible(true);
				Show_Training_Table.setText(
						"Twój trening z dnia \"" + table.getSelectionModel().getSelectedItem().date + "\" to:\n\n");
				for (int i = 0; i < words.length; i++) {
					String[] wordsInside = words[i].split("\\,");
					Show_Training_Table.appendText("Æwiczenie nr." + (i + 1) + ":\t\t\t" + wordsInside[0] + "\n");
					Show_Training_Table.appendText("Serie x powtórzenia:\t" + wordsInside[1] + "\n");
					Show_Training_Table.appendText("Ciê¿ar:\t\t\t\t" + wordsInside[2] + "\n");
					Show_Training_Table.appendText("Czas na przerwê:\t\t" + wordsInside[3] + "\n\n");
				}
			}
		} catch (Exception e) {
			System.out.print("B³¹d" + e);
		}
	}

	public void Hide_Training(ActionEvent event) {
		Show_Training_Table.setVisible(false);
		btn_hideTraining.setVisible(false);
	}

	public void LoadTableView() {
		table.getItems().clear();
		try {
			Connection con = ds.getConnection();
			ResultSet rs = con.createStatement().executeQuery("Select * from Trainings");
			while (rs.next()) {
				obslist.add(
						new finishedTrainings(rs.getString("ID_training"), rs.getString("Date"), rs.getString("Name")));
			}
		} catch (SQLException e) {
			System.out.print("B³¹d");
		}

		dateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
		nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

		table.setItems(obslist);
	}

	public void Delete_TrainingAll() {
		try {
			Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM Trainings;");
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.print("B³¹d" + e);
		}
		LoadTableView();
	}

	// Zmiana okna
	public void MainClick(ActionEvent event) {
		Main_App.setVisible(true);
		Settings_App.setVisible(false);
		Training_App.setVisible(false);
		scrollPane.setVisible(false);
		OwnTrain_App.setVisible(false);
		loadMain();
	}
	
	public void OwnTrainClick(ActionEvent event) {
		Main_App.setVisible(false);
		Settings_App.setVisible(false);
		Training_App.setVisible(false);
		scrollPane.setVisible(false);
		OwnTrain_App.setVisible(true);
	}
	
	public void ProfilClick(ActionEvent event) {
		Settings_App.setVisible(true);
		Main_App.setVisible(false);
		Training_App.setVisible(false);
		scrollPane.setVisible(false);
		OwnTrain_App.setVisible(false);
	}
	
	public void TrainingClick(ActionEvent event) {
		Settings_App.setVisible(false);
		Main_App.setVisible(false);
		Training_App.setVisible(true);
		scrollPane.setVisible(false);
		OwnTrain_App.setVisible(false);
	}

}
