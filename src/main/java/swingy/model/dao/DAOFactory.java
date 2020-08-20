package swingy.model.dao;

public abstract class DAOFactory {
	static {
		heroDAO = new HeroDAO();
	}
	private static HeroDAO heroDAO;
	public static HeroDAO getHeroDAO() {
		return heroDAO;
	}
}
