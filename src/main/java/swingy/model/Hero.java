package swingy.model;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Hero extends GameCharacter {
	@NotNull(message = "Name can not be null")
	@Size(min = 5, max = 30, message = "Name length must be between 5 and 30 characters")
	private String name;
	@Min(value = 1, message = "Level should not be less than 1")
	private int currentLvl;
	@NotNull(message = "Hero class can not be null")
	private HeroClass heroClass;
	private int exp;
	protected Map<ArtifactType, Artifact> artifacts;

	public Hero(HeroClass heroClass) {
		this.heroClass = heroClass;
		this.currentLvl = 1;
		this.hitPoint = 10;
		this.attack = 1;
		this.defence = 1;
		this.artifacts = new HashMap<>();
		this.exp = 0;
	}

	public Hero() {
		this.currentLvl = 1;
		this.hitPoint = 10;
		this.attack = 1;
		this.defence = 1;
		this.artifacts = new HashMap<>();
		this.heroClass = null;
		this.exp = 0;
	}

	public int getAttack() {
		int result = super.getAttack();
		if (artifacts != null && artifacts.get(ArtifactType.WEAPON) != null) {
			result += artifacts.get(ArtifactType.WEAPON).getPower();
		}
		return result;
	}

	public int getDefence() {
		int result = super.getDefence();
		if (artifacts != null && artifacts.get(ArtifactType.ARMOR) != null) {
			result += artifacts.get(ArtifactType.ARMOR).getPower();
		}
		return result;
	}

	public int getHitPoint() {
		int result = super.getHitPoint();
		if (artifacts != null && artifacts.get(ArtifactType.HELM) != null) {
			result += artifacts.get(ArtifactType.HELM).getPower();
		}
		return result;
	}

	public int getCurrentLvl() {
		return currentLvl;
	}

	public void updateExp() {
		exp += 500;
		if (currentLvl * 1000 + (currentLvl - 1) * (currentLvl - 1) * 450 <= exp) {
			currentLvl++;
		}
	}

	public Map<ArtifactType, Artifact> getArtifacts() {
		return artifacts;
	}

	public HeroClass getHeroClass() {
		return heroClass;
	}

	public String getName() {
		return name;
	}

	public int getExp() {
		return exp;
	}

	public static class HeroBuilder {
		private Hero character;
		ValidatorFactory factory;
		Validator validator;

		public Hero getCharacter() {
			return character;
		}

		public void setCharacter(Hero character) {
			this.character = character;
		}

		public void setArtifacts(Map<ArtifactType, Artifact> artifacts) {
			this.character.artifacts = artifacts;
		}

		public void takeArtifact(Artifact artifact) {
			if (artifact == null)
				return;
			Artifact oldArtifact = character.getArtifacts().get(artifact.getType());
			if (oldArtifact != null) {
				character.getArtifacts().remove(artifact.getType());
			}
			character.artifacts.put(artifact.getType(), artifact);
		}

		public void setName(String name) {
			character.name = name;
		}

		public void setHeroClass(HeroClass heroClass) {
			character.heroClass = heroClass;
		}

		public void setLevel(int currentLvl) {
			character.currentLvl = currentLvl;
		}

		public void setAttack(int attack) {
			character.attack = attack;
		}

		public void setDefence(int defence) {
			character.defence = defence;
		}

		public void setHitPoint(int hitPoint) {
			character.hitPoint = hitPoint;
		}

		public void setExp(int exp) {
			character.exp = exp;
		}

		public HeroBuilder(Hero character) {
			this.character = character;
			this.factory = Validation.buildDefaultValidatorFactory();
			this.validator = factory.getValidator();
		}

		public Set<ConstraintViolation<Hero>> validate() {
			return validator.validate(this.character);
		}
	}
}
