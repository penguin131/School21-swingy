package swingy.model.dao;

import swingy.model.Artifact;
import swingy.model.ArtifactType;
import swingy.model.Hero;
import swingy.model.HeroClass;
import swingy.model.entity.Character;
import swingy.model.entity.CharacterClass;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

public class HeroDAO implements Dao<Hero> {
	@SuppressWarnings("unchecked")
	@Override
	public List<Hero> getAll() {
		Query q = DAOFactory.getEntityManager().createQuery("SELECT c FROM Character c");
		List<Character> characters = (List<Character>) q.getResultList();
		List<Hero> heroes = new ArrayList<>();
		for (Character character : characters) {
			heroes.add(convertCharacterToHero(character));
		}
		return heroes;
	}

	@Override
	public void save(Hero hero) {
		Character character = convertHeroToCharacter(hero);
		DAOFactory.getEntityManager().getTransaction().begin();
		DAOFactory.getEntityManager().persist(character);
		DAOFactory.getEntityManager().getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Hero hero) {
		EntityManager em = DAOFactory.getEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT c FROM Character c where name=:name").setParameter("name", hero.getName());
		List<Character> resultList = (List<Character>) q.getResultList();
		if (resultList.size() == 0) {
			save(hero);
		} else {
			Character character = resultList.get(0);
			Character character1 = convertHeroToCharacter(hero);
			character1.setCharacterId(character.getCharacterId());
			em.merge(character1);
			em.flush();
			em.getTransaction().commit();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Hero getForName(String name) {
		Query q = DAOFactory.getEntityManager().
				createQuery("SELECT c FROM Character c where name=:name").setParameter("name", name);
		List<Character> resultList = (List<Character>) q.getResultList();
		if (resultList.size() == 0) {
			return null;
		} else {
			Character character = resultList.get(0);
			return convertCharacterToHero(character);
		}
	}

	private Hero convertCharacterToHero(Character character) {
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

	private Character convertHeroToCharacter(Hero hero) {
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
}
