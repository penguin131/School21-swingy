package swingy.model.dao;

import swingy.model.Artifact;
import swingy.model.ArtifactType;
import swingy.model.Hero;
import swingy.model.HeroClass;
import swingy.model.entity.Character;
import swingy.model.entity.CharacterClass;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HeroDAO implements Dao<Hero> {
	@SuppressWarnings("unchecked")
	@Override
	public List<Hero> getAll() {

		Query q = DAOFactory.getEntityManager().createQuery("SELECT c FROM Character c");
		List<Character> characters = (List<Character>) q.getResultList();
		List<Hero> heroes = new ArrayList<>();
		for (Character character : characters) {
			Hero hero = new Hero();
			Hero.HeroBuilder builder = new Hero.HeroBuilder(hero);
			builder.setName(character.getName());
			builder.setLevel(character.getLevel());
			builder.setAttack(character.getAttack());
			builder.setDefence(character.getDefence());
			builder.setHitPoint(character.getHitPoint());
			builder.setHeroClass(HeroClass.getClassForId(character.getCharacterClass().getCharacterClassId()));
			heroes.add(hero);
		}
		return heroes;
	}

	@Override
	public void save(Hero hero) {
		Character character = new Character();
		character.setName(hero.getName());
		character.setAttack(hero.getAttack());
		character.setDefence(hero.getDefence());
		character.setHitPoint(hero.getHitPoint());
		//class
		CharacterClass chClass = new CharacterClass();
		chClass.setCharacterClassId(hero.getHeroClass().getId());
		chClass.setClassName(HeroClass.getNameForId(hero.getHeroClass().getId()));
		character.setCharacterClass(chClass);
		//artifacts
		if (hero.getArtifacts() != null) {
			List<swingy.model.entity.Artifact> artifacts = new ArrayList<>();
			for (Map.Entry<ArtifactType, Artifact> entry : hero.getArtifacts().entrySet()) {
				swingy.model.entity.Artifact newArt = new swingy.model.entity.Artifact();
				newArt.setArtifactType(entry.getValue().getType().toEntity());
				newArt.setPower(entry.getValue().getPower());
				newArt.setCharacter(character);
				artifacts.add(newArt);
			}
			character.setArtifacts(artifacts);
		}
		DAOFactory.getEntityManager().getTransaction().begin();
		DAOFactory.getEntityManager().persist(character);
		DAOFactory.getEntityManager().getTransaction().commit();
	}

	@Override
	public void update(Hero hero) {

	}

	@Override
	public void delete(Hero hero) {

	}
}
