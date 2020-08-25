package swingy.model;

import swingy.model.entity.Character;
import swingy.model.entity.CharacterClass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CharacterEntityConverter {
	public static Character heroToEntity(Hero hero) {
		Character character = new Character();
		character.setName(hero.getName());
		int attack = 0, def = 0, hp = 0;
		//artifacts
		if (hero.getArtifacts() != null) {
			Set<swingy.model.entity.Artifact> artifacts = new HashSet<>();
			for (Map.Entry<ArtifactType, Artifact> entry : hero.getArtifacts().entrySet()) {
				swingy.model.entity.Artifact newArt = new swingy.model.entity.Artifact();
				newArt.setArtifactType(entry.getValue().getType().toEntity());
				newArt.setPower(entry.getValue().getPower());
				newArt.setCharacter(character);
				newArt.setArtifactId(entry.getValue().getId());
				artifacts.add(newArt);
				if (entry.getValue().getType().equals(ArtifactType.ARMOR))
					def += entry.getValue().getPower();
				else if (entry.getValue().getType().equals(ArtifactType.HELM))
					hp += entry.getValue().getPower();
				else if (entry.getValue().getType().equals(ArtifactType.WEAPON))
					attack += entry.getValue().getPower();
			}
			character.setArtifacts(artifacts);
		}
		character.setAttack(hero.getAttack() - attack);
		character.setDefence(hero.getDefence() - def);
		character.setHitPoint(hero.getHitPoint() - hp);
		character.setLevel(hero.getCurrentLvl());
		character.setExp(hero.getExp());
		//class
		CharacterClass chClass = new CharacterClass();
		chClass.setCharacterClassId(hero.getHeroClass().getId());
		chClass.setClassName(HeroClass.getNameForId(hero.getHeroClass().getId()));
		character.setCharacterClass(chClass);
		return character;
	}

	public static Hero entityToHero(Character character) {
		Hero hero = new Hero(HeroClass.getClassForId(character.getCharacterClass().getCharacterClassId()));
		Hero.HeroBuilder builder = new Hero.HeroBuilder(hero);
		builder.setName(character.getName());
		builder.setLevel(character.getLevel());
		builder.setAttack(character.getAttack());
		builder.setDefence(character.getDefence());
		builder.setHitPoint(character.getHitPoint());
		builder.setExp(character.getExp());
		//artifacts
		if (character.getArtifacts() != null) {
			Map<ArtifactType, Artifact> artifactMap = new HashMap<>();
			for (swingy.model.entity.Artifact art : character.getArtifacts()) {
				artifactMap.put(art.getArtifactType().toBean(),
						new Artifact(art.getArtifactType().toBean(), art.getPower(), art.getArtifactId()));
			}
			builder.setArtifacts(artifactMap);
		}
		return hero;
	}
}
