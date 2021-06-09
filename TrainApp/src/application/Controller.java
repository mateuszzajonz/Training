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
	public CheckBox checkBMI, checkCW1;
	public ImageView i1, i2, i3, i4, i5, i6;

	// main app
	public Label Main_Imie, Main_Nazwisko, Main_Waga, Main_Wzrost, Main_BMI, lblBMI;
	public LineChart<String, String> Main_Graf;
	public Button Main_DeleteOne, Main_DeleteAll;
	public TableView<finishedTrainings> table;
	public TableColumn<finishedTrainings, String> dateCol;
	public TableColumn<finishedTrainings, String> nameCol;
	ObservableList<finishedTrainings> obslist = FXCollections.observableArrayList();
	ObservableList<String> obslist2 = FXCollections.observableArrayList();

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
	Training trainCreate = new Training();
	static String trainingToDB;

	//create own training
	public Button Train_Add;
	public Button Train_Del;
	public Button Train_Save;
	public ComboBox<String> Train_ex;
	public ComboBox<String> Train_time;
	public ComboBox<String> Train_series;
	public ListView<String> Train_list;
	String training;
	ArrayList<String> Train_AList = new ArrayList<String>();
	
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
				checkCW1.isSelected());

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
			checkCW1.setDisable(true);

			Edit_Click.setVisible(true);
			Save_Click.setVisible(false);

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}
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
			System.out.print("B��d");
		}

		dateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
		nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

		table.setItems(obslist);
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
		loadDB();
		loadProfil();
		loadMain();
		LoadTableView();
		try {
			Connection con = ds.getConnection();
			ResultSet rs = con.createStatement().executeQuery("Select exercises from Exercises");
			while (rs.next()) {
				obslist2.add(rs.getString("exercises"));
			}
		} catch (SQLException e) {
			System.out.print("B��d" + e);
		} catch (Exception e) {

		}

		plec.getItems().addAll("M�czyzna", "Kobieta");
		comboCw1.getItems().addAll(obslist2);
		Train_ex.getItems().addAll(obslist2);
		Train_series.getItems().addAll("3x3", "5x5");
		Train_time.getItems().addAll("1:00","1:30","2:00","2:30","3:00","3:30","4:00","4:30","5:00");

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
		checkCW1.setDisable(true);

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
		LoadTableView();
		if (radioGraf.isSelected()) {
			Main_Graf.setVisible(true);
			table.setVisible(false);
			Main_DeleteAll.setVisible(false);
			Main_DeleteOne.setVisible(false);

		} else {
			table.setVisible(true);
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

			if (!pr1.equals(null)) {
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
				checkCW1.setSelected(Boolean.parseBoolean(dane[10]));
			} else {
				imie.setText("");
				nazwisko.setText("");
				waga.setText("");
				wzrost.setText("");
//			data.setValue("");
				plec.setValue("");
				SetImage("");

				if ("Graf".equals("Graf")) {
					radioGraf.setSelected(true);
				} else {
					ostatnitrening.setSelected(true);
				}

				checkBMI.setSelected(Boolean.parseBoolean(""));
				comboCw1.setValue("");
				checkCW1.setSelected(Boolean.parseBoolean(""));
			}
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
		checkCW1.setDisable(false);

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
				alert.setTitle("Informacja dla U�YTKOWNIKA");
				alert.setHeaderText(null);
				alert.setContentText("Trudno zrobi� trening bez �wicze�. \nProsz� wybierz co chcesz dzisiaj trenowa�.");
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Informacja dla U�YTKOWNIKA");
			alert.setHeaderText(null);
			alert.setContentText("Przepraszamy. Co� posz�o nie tak:\n\n" + e + "\n\nSpr�buj jeszcze raz.");
			alert.showAndWait();
		}
	}

	public void Help_CT(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Pomoc");
		alert.setHeaderText(null);
		alert.setContentText(
				"1)\tWyb�r partii mi�niowych wp�ywa na rodzaj treningu.\n2)\tProgram potrzebuje minimum 20 minut na ka�d� parti� cia�a do poprawnego dzia�ania."
						+ "\n3)\tWyb�r rodzaju treningu wp�ywa na liczb� powt�rze� jak i d�ugo�� przerwy.\n4)\t Je�eli wyskakuje b��d, upewnij si�, �e dobrze wpisa�e� czas."
						+ "\n5)\tPrzy braku czasu na trening, wy�wietli si� komunikat, albo zostanie stworzony kr�tki trening wzmacniaj�cy");
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
			System.out.print("B��d" + e);
		}
	}

	public void Delete_Training() {
		try {
			Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM Trainings WHERE ID_training = ?;");
			finishedTrainings train = table.getSelectionModel().getSelectedItem();
			ps.setString(1, train.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.print("B��d" + e);
		}
		LoadTableView();
	}

	public void Delete_TrainingAll() {
		try {
			Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM Trainings;");
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.print("B��d" + e);
		}
		LoadTableView();
	}
	
	public void AddExercise(ActionEvent event) {
		if(!(Train_ex.getValue() == null) && !(Train_series.getValue() == null)&& !(Train_time.getValue() == null)) {
		Train_list.getItems().clear();
		Train_AList.add(Train_ex.getValue()+", "+ Train_series.getValue() +", "+ Train_time.getValue());
		Train_list.getItems().addAll(Train_AList);
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("");
			alert.setHeaderText(null);
			alert.setContentText("Nie wype�niono wszystkich danych");
			alert.showAndWait();
		}
	}
	
	public void DelExercise(ActionEvent event) {
		int a = Train_list.getSelectionModel().getSelectedIndex();
		if(a>=0) {
		Train_AList.remove(a);
		Train_list.getItems().remove(a);
		}
	}
	
	public void SaveExercise(ActionEvent event) {
		try {
			
			for(int i=0;i<Train_AList.size();i++) {
				if(i==0) {
					training = Train_AList.get(0);
				}else
				training = training +"; "+ Train_AList.get(i);
			}
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDateTime now = LocalDateTime.now();
			Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT INTO Trainings(ID_training,Date,Name) VALUES(?,?,?)");
			ps.setString(2, dtf.format(now));
			ps.setString(3, training);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.print("B��d" + e);
		}
	}
	//Zmiana okna
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
