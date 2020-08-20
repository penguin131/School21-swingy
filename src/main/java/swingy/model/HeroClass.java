package swingy.model;

public enum HeroClass {
	MAN(1, "MAN"),
	WOMAN(2, "WOMAN");

	private int id;
	private String name;

	HeroClass(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static boolean containsCode(int code) {
		for (HeroClass heroClass : HeroClass.values()) {
			if (code == heroClass.getId())
				return true;
		}
		return false;
	}

	public static String getNameForId(int id) {
		for (HeroClass heroClass1 : HeroClass.values()) {
			if (heroClass1.getId() == id)
				return heroClass1.getName();
		}
		return null;
	}
}
