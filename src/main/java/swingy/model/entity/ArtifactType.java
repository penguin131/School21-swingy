package swingy.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "T_ARTIFACT_TYPE", schema = "SWINGY")
public class ArtifactType {
	@Id
	@Column(name = "T_ARTIFACT_TYPE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int artifactTypeId;
	@Column(name = "NAME")
	private String name;

	public int getArtifactTypeId() {
		return artifactTypeId;
	}

	public void setArtifactTypeId(int artifactTypeId) {
		this.artifactTypeId = artifactTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ArtifactType: {" +
				"artifactTypeId: " + artifactTypeId +
				", name: " + name +
				"}";
	}
}
