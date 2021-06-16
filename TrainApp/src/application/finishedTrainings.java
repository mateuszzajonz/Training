package application;

public class finishedTrainings {

	String id;
	String date;
	String name;
	String exercise;
	String weight; 
	String SxR;
	String time;
	int exerciseID;
	int dateID;
	
	public finishedTrainings(String id, String date, String exercise, String weight, String sxR, String time) {
		super();
		this.id = id;
		this.date = date;
		this.exercise = exercise;
		this.weight = weight;
		this.SxR = sxR;
		this.time = time;
	}

	public String getExercise() {
		return exercise;
	}

	public void setExercise(String exercise) {
		this.exercise = exercise;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getExerciseID() {
		return exerciseID;
	}

	public void setExerciseID(int exerciseID) {
		this.exerciseID = exerciseID;
	}

	public int getDateID() {
		return dateID;
	}

	public void setDateID(int dateID) {
		this.dateID = dateID;
	}

	public finishedTrainings(String exercise, String weight, int dateID) {
		super();
		this.exercise = exercise;
		this.weight = weight;
		this.dateID = dateID;
	}

	public finishedTrainings(String id, String date, String name, int exerciseID) {
		super();
		this.id = id;
		this.date = date;
		this.name = name;
		this.exerciseID = exerciseID;
	}
	public finishedTrainings(String id, String date, String name) {
		super();
		this.id = id;
		this.date = date;
		this.name = name;
	}
}
