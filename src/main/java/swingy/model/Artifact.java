package swingy.model;

public class Artifact {
	private ArtifactType type;
	private int power;

	public Artifact(ArtifactType type, int power) {
		this.type = type;
		this.power = power;
	}

	public ArtifactType getType() {
		return type;
	}

	public int getPower() {
		return power;
	}
}
