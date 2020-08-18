package swingy.model;

public class Hero extends GameCharacter {
	private int currentLvl;
	public void downloadOrSetHero() {
		//todo download from database
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
}
