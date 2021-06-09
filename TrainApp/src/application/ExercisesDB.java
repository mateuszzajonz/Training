package application;

public class ExercisesDB {
	String id;
	String exercises;
	String type;
	String max;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getExercises() {
		return exercises;
	}

	public void setExercises(String exercises) {
		this.exercises = exercises;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}
	
	public ExercisesDB(String id, String type, String exercises,String max) {
		super();
		this.id = id;
		this.type = type;
		this.exercises = exercises;
		this.max = max;
	}
}
