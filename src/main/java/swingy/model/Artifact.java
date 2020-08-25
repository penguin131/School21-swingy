package swingy.model;

public class Artifact {
	private ArtifactType type;
	private int power;
	private int id;

	public Artifact(ArtifactType type, int power) {
		this.type = type;
		this.power = power;
	}

	public Artifact(ArtifactType type, int power, int id) {
		this.type = type;
		this.power = power;
		this.id = id;
	}

	public ArtifactType getType() {
		return type;
	}

	public int getPower() {
		return power;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
