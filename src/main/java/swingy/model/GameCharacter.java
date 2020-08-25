package swingy.model;

public abstract class GameCharacter {
	protected int attack;
	protected int defence;
	protected int hitPoint;

	public GameCharacter(int attack, int defence, int hitPoint) {
		this.attack = attack;
		this.defence = defence;
		this.hitPoint = hitPoint;
	}

	public GameCharacter() {
	}

	public int getAttack() {
		return attack;
	}

	public int getDefence() {
		return defence;
	}

	public int getHitPoint() {
		return hitPoint;
	}

}
