package application;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
	List<String> GeneratedExercises = new LinkedList<>();

	public void AddExerciseFromDatabase() {
		listOfExercises.add("LEGS,BACK SQUAT");
		listOfExercises.add("LEGS,BULGARIAN SQUAT");
		listOfExercises.add("LEGS,DEADLIFT");
		listOfExercises.add("BACK,PENDLAY ROW");
		listOfExercises.add("BACK,DEADLIFT");
		listOfExercises.add("CHEST,BENCH PRESS");
		listOfExercises.add("CHEST,BUTTERFLY");
		listOfExercises.add("SHOULDERS,OVER HEAD PRESS");
		listOfExercises.add("SHOULDERS,LU RISES");
		listOfExercises.add("ARMS,PREACHER CURL");
		listOfExercises.add("ARMS,SKULL CRUSHERS");
		listOfExercises.add("ABS,PLANK");
		listOfExercises.add("ABS,CRUNCHES");
	}

	public Controller Main_CT_Function() // CT - Create Training
	{
		return Main.myConrollerToPass;
	}

	public void Generate_Training_Main(int time, boolean enoughTime, int count) {
		Controller myController = Main_CT_Function();
		ClearExerciseLists();
		AddExerciseFromDatabase(); // in settings
		AddExercisesToLists(myController);
		if (enoughTime)
			Generate_Training_EnoughTime(time, count);
		else
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
		int timeleft = time - exercises;
		exercises -= count;

		if (!exerciseLegs.isEmpty()) {
			GeneratedExercises.add(exerciseLegs.get(rnd.nextInt(exerciseLegs.size())));
			count--;
		}
		if (!exerciseChest.isEmpty()) {
			GeneratedExercises.add(exerciseChest.get(rnd.nextInt(exerciseChest.size())));
			count--;
		}
		if (!exerciseBack.isEmpty()) {
			GeneratedExercises.add(exerciseBack.get(rnd.nextInt(exerciseBack.size())));
			count--;
		}
		if (!exerciseArms.isEmpty()) {
			GeneratedExercises.add(exerciseArms.get(rnd.nextInt(exerciseArms.size())));
			count--;
		}
		if (!exerciseShoulders.isEmpty()) {
			GeneratedExercises.add(exerciseShoulders.get(rnd.nextInt(exerciseLegs.size())));
			count--;
		}
		if (!exerciseABS.isEmpty()) {
			GeneratedExercises.add(exerciseABS.get(rnd.nextInt(exerciseLegs.size())));
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
			if (!exerciseArms.isEmpty()) {
				exerciseToAdd = exerciseArms.get(rnd.nextInt(exerciseArms.size()));
			}
			break;
		case 4:
			if (!exerciseShoulders.isEmpty()) {
				exerciseToAdd = exerciseShoulders.get(rnd.nextInt(exerciseLegs.size()));
			}
			break;
		case 5:
			if (!exerciseABS.isEmpty()) {
				exerciseToAdd = exerciseABS.get(rnd.nextInt(exerciseLegs.size()));
			}
			break;
		}
		if (!GeneratedExercises.contains(exerciseToAdd)) {
			GeneratedExercises.add(exerciseToAdd);
			count--;
		}
		return count;
	}

	public void Generate_Training_NoTime(int time, int count, boolean pass) {
		if (time > 4 || pass) {
			int exercises = time / 20;
			if (count != 0) {
				count -= exercises;
				time /= count;
			} else {

			}
		} else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Informacja dla U¯YTKOWNIKA");
			alert.setHeaderText(null);
			alert.setContentText(
					"Przepraszamy. Czas przeznaczony na trening okaza³ siê zbyt krótki.\nProszê poœwiêæ wiêcej czasu.");
			alert.showAndWait();
		}
	}

	public void AddExercisesToLists(Controller myController) {
		if (myController.chbox_legs_CT.isSelected()) {
			for (String name : listOfExercises) {
				String[] words = name.split(",");
				if (words[0].equals("LEGS")) {
					exerciseLegs.add(words[1]);
				}
			}
		}
		if (myController.chbox_back_CT.isSelected()) {
			for (String name : listOfExercises) {
				String[] words = name.split(",");
				if (words[0].equals("BACK")) {
					exerciseBack.add(words[1]);
				}
			}
		}
		if (myController.chbox_chest_CT.isSelected()) {
			for (String name : listOfExercises) {
				String[] words = name.split(",");
				if (words[0].equals("CHEST")) {
					exerciseChest.add(words[1]);
				}
			}
		}
		if (myController.chbox_shoulders_CT.isSelected()) {
			for (String name : listOfExercises) {
				String[] words = name.split(",");
				if (words[0].equals("SHOULDERS")) {
					exerciseShoulders.add(words[1]);
				}
			}
		}
		if (myController.chbox_arms_CT.isSelected()) {
			for (String name : listOfExercises) {
				String[] words = name.split(",");
				if (words[0].equals("ARMS")) {
					exerciseArms.add(words[1]);
				}
			}
		}
		if (myController.chbox_abs_CT.isSelected()) {
			for (String name : listOfExercises) {
				String[] words = name.split(",");
				if (words[0].equals("ABS")) {
					exerciseABS.add(words[1]);
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
