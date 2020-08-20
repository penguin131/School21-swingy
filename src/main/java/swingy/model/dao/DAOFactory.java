package swingy.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public abstract class DAOFactory {
	static {
		heroDAO = new HeroDAO();
		entityManager = Persistence.createEntityManagerFactory("UserCharacter").createEntityManager();
	}
	private static HeroDAO heroDAO;
	private static EntityManager entityManager;
	public static HeroDAO getHeroDAO() {
		return heroDAO;
	}

	public static EntityManager getEntityManager() {
		return entityManager;
	}
}
