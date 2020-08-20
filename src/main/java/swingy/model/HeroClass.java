package swingy.model;

public enum HeroClass {
	MAN(0),
	WOMAN(1);

	private int id;

	HeroClass(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
