package swingy.model.dao;

import swingy.model.CharacterEntityConverter;
import swingy.model.Hero;
import swingy.model.entity.Character;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class HeroDAO implements Dao<Hero> {
	@SuppressWarnings("unchecked")
	@Override
	public List<Hero> getAll() {
		Query q = DAOFactory.getEntityManager().createQuery("SELECT c FROM Character c");
		List<Character> characters = (List<Character>) q.getResultList();
		List<Hero> heroes = new ArrayList<>();
		for (Character character : characters) {
			heroes.add(CharacterEntityConverter.entityToHero(character));
		}
		return heroes;
	}

	@Override
	public void save(Hero hero) {
		Character character = CharacterEntityConverter.heroToEntity(hero);
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
			Character character1 = CharacterEntityConverter.heroToEntity(hero);
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
			return CharacterEntityConverter.entityToHero(character);
		}
	}

}
