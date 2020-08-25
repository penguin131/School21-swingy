package swingy.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "T_ARTIFACT", schema = "SWINGY")
public class Artifact {
	@Id
	@Column(name = "ARTIFACT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int artifactId;
	@OneToOne
	@JoinColumn(name = "ARTIFACT_TYPE", referencedColumnName = "T_ARTIFACT_TYPE_ID")
	private ArtifactType artifactType;
	@Column(name = "POWER")
	private int power;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CHARACTER", referencedColumnName = "CHARACTER_ID")
	private Character character;

	public int getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(int artifactId) {
		this.artifactId = artifactId;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public ArtifactType getArtifactType() {
		return artifactType;
	}

	public void setArtifactType(ArtifactType artifactType) {
		this.artifactType = artifactType;
	}

	@Override
	public String toString() {
		return "Artifact:{" +
				"artifactId: " + artifactId +
				", characterClass: " + artifactType.toString() +
				", power: " + power +
				", character: " + character.getName() +
				"}";
	}
}
