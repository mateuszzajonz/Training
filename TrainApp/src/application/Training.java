package application;

import java.util.LinkedList;
import java.util.List;

public class Training extends Main {
	List<String> listOfExercises = new LinkedList<>();
	List<String> exerciseLegs = new LinkedList<>();
	List<String> exerciseBack = new LinkedList<>();
	List<String> exerciseChest = new LinkedList<>();
	List<String> exerciseShoulders = new LinkedList<>();
	List<String> exerciseArms = new LinkedList<>();
	List<String> exerciseABS = new LinkedList<>();

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

	public void Generate_Training_Main(int time, boolean enoughTime) {
		Controller myController = Main_CT_Function();
		ClearExerciseLists();
		AddExerciseFromDatabase(); // in settings
		AddExercisesToLists(myController);
		if (enoughTime)
			Generate_Training_EnoughTime();
		else
			Generate_Training_NoTime();
	}

	public void ClearExerciseLists() {
		exerciseLegs.clear();
		exerciseBack.clear();
		exerciseChest.clear();
		exerciseShoulders.clear();
		exerciseArms.clear();
		exerciseABS.clear();
	}

	public void Generate_Training_EnoughTime() {

	}

	public void Generate_Training_NoTime() {

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
