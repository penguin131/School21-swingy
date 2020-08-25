package swingy.model;

import java.util.Random;

public class Villain extends GameCharacter {
	public Villain(int attack, int defence, int hitPoint) {
		super(attack, defence, hitPoint);
	}

	public Artifact generateArtifact(int heroLvl) {
		Random random = new Random();
		ArtifactType[] types = ArtifactType.values();
		return new Artifact(types[random.nextInt(3)], random.nextInt(heroLvl));
	}
}
