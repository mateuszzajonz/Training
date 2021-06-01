package application;

import java.util.LinkedList;
import java.util.List;

public class Training
{
	List<String> listOfExercises = new LinkedList<>();

	public void AddExercise(){
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
}
