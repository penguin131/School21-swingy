package swingy.model;

import java.util.Map;

public abstract class GameCharacter {
	protected int attack;
	protected int defence;
	protected int hitPoint;
	protected Map<ArtifactType, Artifact> artifacts;

	public GameCharacter(int attack, int defence, int hitPoint, Map<ArtifactType, Artifact> artifacts) {
		this.attack = attack;
		this.defence = defence;
		this.hitPoint = hitPoint;
		this.artifacts = artifacts;
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

	public Map<ArtifactType, Artifact> getArtifacts() {
		return artifacts;
	}
}
