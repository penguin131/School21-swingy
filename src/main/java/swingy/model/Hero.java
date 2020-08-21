package swingy.model;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Set;

public class Hero extends GameCharacter {
	@NotNull(message = "Name can not be null")
	@Size(min = 5, max = 30, message = "Name length must be between 5 and 30 characters")
	private String name;
	@Min(value = 1, message = "Level should not be less than 1")
	private int currentLvl;
	@NotNull(message = "Hero class can not be null")
	private HeroClass heroClass;

	public Hero(HeroClass heroClass) {
		this.heroClass = heroClass;
		this.currentLvl = 1;
		this.hitPoint = 10;
		this.attack = 1;
		this.defence = 1;
		this.hitPoint = 1;
		this.artifacts = new HashMap<>();
	}

	public Hero() {
		this.currentLvl = 1;
		this.hitPoint = 10;
		this.attack = 0;
		this.defence = 0;
		this.artifacts = new HashMap<>();
		this.heroClass = null;
	}

	public int getCurrentLvl() {
		return currentLvl;
	}

	public void increaseLvl() {
		currentLvl++;
	}

	public HeroClass getHeroClass() {
		return heroClass;
	}

	public String getName() {
		return name;
	}

	public static class HeroBuilder {
		private Hero character;
		ValidatorFactory factory;
		Validator validator;

		private void dropArtifact(Artifact artifact) {
			switch (artifact.getType()) {
				case HELM:
					this.character.hitPoint -= artifact.getPower();
					break;
				case ARMOR:
					this.character.defence -= artifact.getPower();
					break;
				case WEAPON:
					this.character.attack -= artifact.getPower();
					break;
			}
			character.artifacts.remove(artifact.getType());
		}

		public void takeArtifact(Artifact artifact) {
			Artifact oldArtifact = character.getArtifacts().get(artifact.getType());
			if (oldArtifact != null) {
				dropArtifact(oldArtifact);
			}
			character.artifacts.put(artifact.getType(), artifact);
			switch (artifact.getType()) {
				case HELM:
					this.character.hitPoint += artifact.getPower();
					break;
				case ARMOR:
					this.character.defence += artifact.getPower();
					break;
				case WEAPON:
					this.character.attack += artifact.getPower();
					break;
			}
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
