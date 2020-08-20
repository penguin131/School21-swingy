package swingy.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "T_CHARACTER_CLASS", schema = "SWINGY")
public class CharacterClass {
	@Id
	@Column(name = "CHARACTER_CLASS_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int characterClassId;
	@Column(name = "CLASS_NAME")
	private String className;

	public int getCharacterClassId() {
		return characterClassId;
	}

	public void setCharacterClassId(int characterClassId) {
		this.characterClassId = characterClassId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String name) {
		this.className = name;
	}

	@Override
	public String toString() {
		return "CharacterClass:{" +
				"characterClassId:" + characterClassId +
				", className:" + className +
				"}";
	}
}
