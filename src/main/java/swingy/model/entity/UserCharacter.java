package swingy.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "T_CHARACTER", schema = "SWINGY")
public class UserCharacter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int character_id;
	private String name;
	private int character_class;
	private int attack;
	private int defence;
	private int hitpoint;

	public int getCharacter_id() {
		return character_id;
	}

	public void setCharacter_id(int character_id) {
		this.character_id = character_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCharacter_class() {
		return character_class;
	}

	public void setCharacter_class(int character_class) {
		this.character_class = character_class;
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

	public int getHitpoint() {
		return hitpoint;
	}

	public void setHitpoint(int hitpoint) {
		this.hitpoint = hitpoint;
	}


	@Override
	public String toString () {
		return "UserCharacter:{" +
				"id: " + character_id +
				", name: " + name +
				", class: " + character_class +
				", attack: " + attack +
				", defence: " + defence +
				", hitpoint: " + hitpoint +
				"}";
	}
}
