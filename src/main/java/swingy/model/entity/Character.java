package swingy.model.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "T_CHARACTER", schema = "SWINGY")
public class Character {
	@Id
	@Column(name = "CHARACTER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int characterId;
	@Column(name = "NAME")
	private String name;
	@OneToOne
	@JoinColumn(name = "CHARACTER_CLASS", referencedColumnName = "CHARACTER_CLASS_ID")
	private CharacterClass characterClass;
	@Column(name = "ATTACK")
	private int attack;
	@Column(name = "DEFENCE")
	private int defence;
	@Column(name = "HITPOINT")
	private int hitPoint;
	@Column(name = "LEVEL")
	private int level;
	@Column(name = "EXP")
	private int exp;
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER, mappedBy="character")
	private Set<Artifact> artifacts;

	public int getCharacterId() {
		return characterId;
	}

	public void setCharacterId(int characterId) {
		this.characterId = characterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CharacterClass getCharacterClass() {
		return characterClass;
	}

	public void setCharacterClass(CharacterClass characterClass) {
		this.characterClass = characterClass;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public int getHitPoint() {
		return hitPoint;
	}

	public void setHitPoint(int hitPoint) {
		this.hitPoint = hitPoint;
	}

	public Set<Artifact> getArtifacts() {
		return artifacts;
	}

	public void setArtifacts(Set<Artifact> artifacts) {
		this.artifacts = artifacts;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	@Override
	public String toString () {
		return "UserCharacter:{" +
				"id: " + characterId +
				", name: " + name +
				", class: " + characterClass +
				", attack: " + attack +
				", defence: " + defence +
				", hitPoint: " + hitPoint +
				", level: " + level +
				", exp: " + exp +
				"}";
	}
}
