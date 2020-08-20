package swingy.model;

public enum ArtifactType {
	WEAPON(1, "WEAPON"),
	ARMOR(2, "ARMOR"),
	HELM(3, "HELM");

	private int id;
	private String name;

	ArtifactType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public swingy.model.entity.ArtifactType toEntity() {
		swingy.model.entity.ArtifactType type = new swingy.model.entity.ArtifactType();
		type.setArtifactTypeId(this.getId());
		type.setName(this.getName());
		return type;
	}
}
