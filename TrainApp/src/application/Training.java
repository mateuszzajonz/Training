package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import org.sqlite.SQLiteDataSource;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Training extends Main {

	Random rnd = new Random();
	List<String> listOfExercises = new LinkedList<>();
	List<String> exerciseLegs = new LinkedList<>();
	List<String> exerciseBack = new LinkedList<>();
	List<String> exerciseChest = new LinkedList<>();
	List<String> exerciseShoulders = new LinkedList<>();
	List<String> exerciseArms = new LinkedList<>();
	List<String> exerciseMAX = new LinkedList<>();
	HashMap<String, Integer> GeneratedExercises = new HashMap<String, Integer>();
	ExerciseComparator ec = new ExerciseComparator(GeneratedExercises);
	TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(ec);
	static Controller myController;
	static boolean enoughTimeForAllExercises;
	static String doneTraining = "";

	SQLiteDataSource ds = null;
	ArrayList<ExercisesDB> obslist = new ArrayList<ExercisesDB>();

	public void loadDB() {
		try {
			ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:sample.dbTraining.db");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void AddExerciseFromDatabase() {
		loadDB();
		try {
			Connection con = ds.getConnection();
			ResultSet rs = con.createStatement().executeQuery("Select * from Exercises");
			while (rs.next()) {
				obslist.add(new ExercisesDB(rs.getString("id"), rs.getString("type"), rs.getString("exercises"),
						rs.getString("max")));
			}
		} catch (SQLException e) {
			System.out.print("B³¹d");
		}
		for (ExercisesDB name : obslist) {
			listOfExercises.add(name.type + "," + name.exercises);
			exerciseMAX.add(name.exercises + "," + name.max);
		}
	}

	public Controller Main_CT_Function() // CT - Create Training
	{
		return Main.myConrollerToPass;
	}

	public String Generate_Training_Main(int time, boolean enoughTime, int count) {
		listOfExercises.clear();
		GeneratedExercises.clear();
		myController = Main_CT_Function();
		ClearExerciseLists();
		AddExerciseFromDatabase(); // in settings
		AddExercisesToLists(myController);
		if (count * 20 * 4 > time) { // poprawiæ do wielkoœci listy
			if (enoughTime) {
				Generate_Training_EnoughTime(time, count);
				enoughTimeForAllExercises = true;
			} else {
				enoughTimeForAllExercises = false;
				Generate_Training_NoTime(time, count, false);
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Informacja dla U¯YTKOWNIKA");
			alert.setHeaderText(null);
			alert.setContentText(
					"Przepraszamy. Czas przeznaczony na trening okaza³ siê zbyt d³ugi (na nasz¹ bazê æwiczeñ).\nZobacz czy nie wpisa³eœ minut w miejsce godzin.");
			alert.showAndWait();
		}

		return doneTraining;
	}

	public void ClearExerciseLists() {
		exerciseLegs.clear();
		exerciseBack.clear();
		exerciseChest.clear();
		exerciseShoulders.clear();
		exerciseArms.clear();
	}

	public void Generate_Training_EnoughTime(int time, int count) {
		int exercises = time / 20;
		int timeleft = time - exercises * 20;
		exercises -= count;
		int rand = 0;

		if (!exerciseLegs.isEmpty()) {
			rand = rnd.nextInt(exerciseLegs.size());
			String[] words = exerciseLegs.get(rand).split(",");
			GeneratedExercises.put(words[1], 1);
			count--;
		}
		if (!exerciseChest.isEmpty()) {
			rand = rnd.nextInt(exerciseChest.size());
			String[] words = exerciseChest.get(rand).split(",");
			GeneratedExercises.put(words[1], 2);
			count--;
		}
		if (!exerciseBack.isEmpty()) {
			rand = rnd.nextInt(exerciseBack.size());
			String[] words = exerciseBack.get(rand).split(",");
			GeneratedExercises.put(words[1], 3);
			count--;
		}
		if (!exerciseShoulders.isEmpty()) {
			rand = rnd.nextInt(exerciseShoulders.size());
			String[] words = exerciseShoulders.get(rand).split(",");
			GeneratedExercises.put(words[1], 4);
			count--;
		}
		if (!exerciseArms.isEmpty()) {
			rand = rnd.nextInt(exerciseArms.size());
			String[] words = exerciseArms.get(rand).split(",");
			GeneratedExercises.put(words[1], 5);
			count--;
		}

		while (exercises > 0) {
			exercises = AddRandomExercise(exercises);
		}

		Generate_Training_NoTime(timeleft, 0, true);
	}

	public int AddRandomExercise(int count) {
		String exerciseToAdd = "";
		switch (rnd.nextInt(5)) {
		case 0:
			if (!exerciseLegs.isEmpty()) {
				exerciseToAdd = exerciseLegs.get(rnd.nextInt(exerciseLegs.size()));
			}
			break;
		case 1:
			if (!exerciseChest.isEmpty()) {
				exerciseToAdd = exerciseChest.get(rnd.nextInt(exerciseChest.size()));
			}
			break;
		case 2:
			if (!exerciseBack.isEmpty()) {
				exerciseToAdd = exerciseBack.get(rnd.nextInt(exerciseBack.size()));
			}
			break;
		case 3:
			if (!exerciseShoulders.isEmpty()) {
				exerciseToAdd = exerciseShoulders.get(rnd.nextInt(exerciseShoulders.size()));
			}
			break;
		case 4:
			if (!exerciseArms.isEmpty()) {
				exerciseToAdd = exerciseArms.get(rnd.nextInt(exerciseArms.size()));
			}
			break;
		}
		if (!exerciseToAdd.equals("")) {
			String[] words = exerciseToAdd.split(",");
			if (!GeneratedExercises.containsKey(words[1])) {
				GeneratedExercises.put(words[1], AddValueOfHashMap(words[0]));
				count--;
			}
		}
		return count;
	}

	public int AddValueOfHashMap(String type) {
		int value = 0;
		switch (type) {
		case "LEGS":
			value = 1;
			break;
		case "CHEST":
			value = 2;
			break;
		case "BACK":
			value = 3;
			break;
		case "SHOULDERS":
			value = 4;
			break;
		case "ARMS":
			value = 5;
			break;
		case "ABS":
			value = 6;
			break;
		}
		return value;
	}

	public void Generate_Training_NoTime(int time, int count, boolean pass) {
		float timeForExercise = ((float) time) / 20; // zakres 0-1 czyli % czasu potrzebny na æwiczenie np. 80% z
														// oryginalnych 20 minut to 16 minut

		if (pass) {
			if (myController.radioBtn_strength_CT.isSelected()) {
				if (timeForExercise >= 0.80)
					FinalTraining("strength", true);
				else
					FinalTraining("strength", false);
			} else if (myController.radioBtn_muscle_CT.isSelected()) {
				if (timeForExercise >= 0.80)
					FinalTraining("muscle", true);
				else
					FinalTraining("muscle", false);
			} else if (myController.radioBtn_endurance_CT.isSelected()) {
				if (timeForExercise >= 0.80)
					FinalTraining("endurance", true);
				else
					FinalTraining("endurance", false);
			}
		} else if (timeForExercise / count >= 0.8 && time > 30) {
			Generate_Training_EnoughTime(time, count);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Informacja dla U¯YTKOWNIKA");
			alert.setHeaderText(null);
			alert.setContentText(
					"Przepraszamy. Czas przeznaczony na trening okaza³ siê zbyt krótki.\nProszê poœwiêæ wiêcej czasu.");
			alert.showAndWait();
		}
	}

	public void FinalTraining(String type, boolean enoughTime) {
		int count = 1;
		String weight = "";

		if (enoughTime && enoughTimeForAllExercises) {
			count = 1;
			while (count > 0) {
				count = AddRandomExercise(count);
			}
		}
		sorted_map.clear();
		sorted_map.putAll(GeneratedExercises);
		listOfExercises.clear();
		Set<String> keys = sorted_map.keySet();
		for (String key : keys) {
			listOfExercises.add(key);
		}
		myController.txtArea_acceptGrid_CT.clear();
		doneTraining = "";
		myController.txtArea_acceptGrid_CT.appendText("Twój auto-wygenerowany trening to:\n\n");
		switch (type) {
		case "strength":
			for (int i = 0; i < listOfExercises.size(); i++) {
				weight = getMax(i, 5);
				doneTraining += listOfExercises.get(i) + ",5x5" + "," + weight + ",3:00;";
				myController.txtArea_acceptGrid_CT.appendText((i + 1) + ")  " + listOfExercises.get(i)
						+ "\n5x5, weight: " + weight + ", 3:00 rest." + "\n\n");
			}
			break;
		case "muscle":
			for (int i = 0; i < listOfExercises.size(); i++) {
				weight = getMax(i, 8);
				doneTraining += listOfExercises.get(i) + ",6x8" + "," + weight + ",2:00;";
				myController.txtArea_acceptGrid_CT.appendText((i + 1) + ")  " + listOfExercises.get(i)
						+ "\n6x8, weight: " + weight + ", 2:00 rest." + "\n\n");
			}
			break;
		case "endurance":
			for (int i = 0; i < listOfExercises.size(); i++) {
				weight = getMax(i, 12);
				doneTraining += listOfExercises.get(i) + ", 10x12" + "," + weight + ",1:00;";
				myController.txtArea_acceptGrid_CT.appendText((i + 1) + ")  " + listOfExercises.get(i)
						+ "\n10x12, weight: " + weight + ", 1:00 rest." + "\n\n");
			}
			break;
		}

		myController.Accept_grid_CT.setVisible(true);
		myController.Create_grid_CT.setVisible(false);
	}

	public String getMax(int i, int countMax) {
		double weight = 0;
		for (String name : exerciseMAX) {
			String[] word = name.split("\\,");
			if (word[0].equals(listOfExercises.get(i).toString())) {
				if (word[1].equals(null) || word[1].equals("null"))
					weight = 0.0;
				else {
					switch (countMax) {
					case 5:
						weight = Double.parseDouble(word[1])*0.80;
						break;
					case 8:
						weight = Double.parseDouble(word[1])*0.70;
						break;
					case 12:
						weight = Double.parseDouble(word[1])*0.60;
						break;
					}				
				}
				break;
			}
		}

		return weight + "";
	}

	public void AddExercisesToLists(Controller myController) {
		if (myController.chbox_legs_CT.isSelected()) {
			for (String name : listOfExercises) {
				String[] words = name.split(",");
				if (words[0].equals("1")) {
					exerciseLegs.add(name);
				}
			}
		}
		if (myController.chbox_back_CT.isSelected()) {
			for (String name : listOfExercises) {
				String[] words = name.split(",");
				if (words[0].equals("2")) {
					exerciseBack.add(name);
				}
			}
		}
		if (myController.chbox_chest_CT.isSelected()) {
			for (String name : listOfExercises) {
				String[] words = name.split(",");
				if (words[0].equals("3")) {
					exerciseChest.add(name);
				}
			}
		}
		if (myController.chbox_shoulders_CT.isSelected()) {
			for (String name : listOfExercises) {
				String[] words = name.split(",");
				if (words[0].equals("4")) {
					exerciseShoulders.add(name);
				}
			}
		}
		if (myController.chbox_arms_CT.isSelected()) {
			for (String name : listOfExercises) {
				String[] words = name.split(",");
				if (words[0].equals("5")) {
					exerciseArms.add(name);
				}
			}
		}
	}

	public int CheckBoxCountTraining() {
		Controller myController = Main_CT_Function();
		int count = 0;

		if (myController.chbox_legs_CT.isSelected())
			count++;
		if (myController.chbox_back_CT.isSelected())
			count++;
		if (myController.chbox_chest_CT.isSelected())
			count++;
		if (myController.chbox_shoulders_CT.isSelected())
			count++;
		if (myController.chbox_arms_CT.isSelected())
			count++;
		if (myController.chbox_abs_CT.isSelected())
			count++;

		return count;
	}

}
