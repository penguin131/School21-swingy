package swingy.model;

public class Hero extends GameCharacter {
	private int currentLvl;
	private boolean isNew;
	public void init() {
		//todo download from database
		isNew = true;
	}

	public Hero() {
		this.currentLvl = 1;
	}

	public int getCurrentLvl() {
		return currentLvl;
	}

	public void increaseLvl() {
		currentLvl++;
	}

	public boolean isNew() {
		return isNew;
	}
}
