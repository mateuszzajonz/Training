package application;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

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
	List<String> exerciseABS = new LinkedList<>();
	HashMap<String, Integer> GeneratedExercises = new HashMap<String, Integer>();
	ExerciseComparator ec = new ExerciseComparator(GeneratedExercises);
	TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(ec);
	static Controller myController;
	static boolean enoughTimeForAllExercises = false;

	public void AddExerciseFromDatabase() {
		listOfExercises.add("LEGS,BACK SQUAT");
		listOfExercises.add("LEGS,BULGARIAN SQUAT");
		listOfExercises.add("LEGS,DEADLIFT");
		listOfExercises.add("LEGS,FRONT SQUAT");
		listOfExercises.add("BACK,PENDLAY ROW");
		listOfExercises.add("BACK,DEADLIFT");
		listOfExercises.add("BACK,LAT PULLDOWN");
		listOfExercises.add("BACK,ROMANIAN DEADLIFT");
		listOfExercises.add("CHEST,BENCH PRESS");
		listOfExercises.add("CHEST,BUTTERFLY");
		listOfExercises.add("CHEST,DIPS");
		listOfExercises.add("CHEST,INCLINE BENCH PRESS");
		listOfExercises.add("SHOULDERS,OVER HEAD PRESS");
		listOfExercises.add("SHOULDERS,LU RISES");
		listOfExercises.add("SHOULDERS,PUSH PRESS");
		listOfExercises.add("SHOULDERS,BARBELL SHRUG");
		listOfExercises.add("ARMS,PREACHER CURLS");
		listOfExercises.add("ARMS,SKULL CRUSHERS");
		listOfExercises.add("ARMS,KICKBACKS");
		listOfExercises.add("ARMS,HAMMER CURLS");
		listOfExercises.add("ABS,PLANK");
		listOfExercises.add("ABS,V-UPS");
		listOfExercises.add("ABS,RUSSIAN TWIST");
		listOfExercises.add("ABS,CRUNCHES");
	}

	public Controller Main_CT_Function() // CT - Create Training
	{
		return Main.myConrollerToPass;
	}

	public void Generate_Training_Main(int time, boolean enoughTime, int count) {
		myController = Main_CT_Function();
		ClearExerciseLists();
		AddExerciseFromDatabase(); // in settings
		AddExercisesToLists(myController);
		enoughTimeForAllExercises = false;
		if (enoughTime) {
			Generate_Training_EnoughTime(time, count);
			enoughTimeForAllExercises = true;
		} else
			Generate_Training_NoTime(time, count, false);
	}

	public void ClearExerciseLists() {
		exerciseLegs.clear();
		exerciseBack.clear();
		exerciseChest.clear();
		exerciseShoulders.clear();
		exerciseArms.clear();
		exerciseABS.clear();
	}

	public void Generate_Training_EnoughTime(int time, int count) {
		GeneratedExercises.clear();
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
		if (!exerciseABS.isEmpty()) {
			rand = rnd.nextInt(exerciseABS.size());
			String[] words = exerciseABS.get(rand).split(",");
			GeneratedExercises.put(words[1], 6);
			count--;
		}

		while (exercises != 0) {
			exercises = AddRandomExercise(exercises);
		}

		Generate_Training_NoTime(timeleft, count, true);
	}

	public int AddRandomExercise(int count) {
		String exerciseToAdd = "";
		switch (rnd.nextInt(6)) {
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
		case 5:
			if (!exerciseABS.isEmpty()) {
				exerciseToAdd = exerciseABS.get(rnd.nextInt(exerciseABS.size()));
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
		float timeForExercise = ((float)time) / 20; // zakres 0-1 czyli % czasu potrzebny na æwiczenie np. 80% z oryginalnych 20 minut to 16 minut
											
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
		} else if (timeForExercise / count >= 0.8 && time>30) {
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
		
		System.out.println("--------------------");
		System.out.println("results: " + sorted_map);
		
		if (enoughTime) {
			count = 1;
			while (count != 0) {
				count = AddRandomExercise(count);
			}
		}
		sorted_map.putAll(GeneratedExercises);
		
		switch (type) {
		case "strength":
			
			break;
		case "muscle":
			
			break;
		case "endurance":
			
			break;
		}
		
		System.out.println("--------------------");
		System.out.println("results: " + sorted_map);
	}

	public void AddExercisesToLists(Controller myController) {
		if (myController.chbox_legs_CT.isSelected()) {
			for (String name : listOfExercises) {
				String[] words = name.split(",");
				if (words[0].equals("LEGS")) {
					exerciseLegs.add(name);
				}
			}
		}
		if (myController.chbox_back_CT.isSelected()) {
			for (String name : listOfExercises) {
				String[] words = name.split(",");
				if (words[0].equals("BACK")) {
					exerciseBack.add(name);
				}
			}
		}
		if (myController.chbox_chest_CT.isSelected()) {
			for (String name : listOfExercises) {
				String[] words = name.split(",");
				if (words[0].equals("CHEST")) {
					exerciseChest.add(name);
				}
			}
		}
		if (myController.chbox_shoulders_CT.isSelected()) {
			for (String name : listOfExercises) {
				String[] words = name.split(",");
				if (words[0].equals("SHOULDERS")) {
					exerciseShoulders.add(name);
				}
			}
		}
		if (myController.chbox_arms_CT.isSelected()) {
			for (String name : listOfExercises) {
				String[] words = name.split(",");
				if (words[0].equals("ARMS")) {
					exerciseArms.add(name);
				}
			}
		}
		if (myController.chbox_abs_CT.isSelected()) {
			for (String name : listOfExercises) {
				String[] words = name.split(",");
				if (words[0].equals("ABS")) {
					exerciseABS.add(name);
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
