package swingy.controller;

import swingy.helper.Config;
import swingy.helper.GameMode;
import swingy.model.Hero;
import swingy.model.dao.DAOFactory;
import swingy.view.SelectHeroView;
import swingy.view.console.SelectHeroConsolePage;
import swingy.view.swing.SelectHeroSwingPage;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectHeroController {
	private List<Hero> heroes;
	private SelectHeroView view;

	public Hero selectHero() throws IOException, InterruptedException {
		heroes = DAOFactory.getHeroDAO().getAll();
		view.welcome();
		Hero hero;
		if (heroes != null && heroes.size() > 0) {
			if (view.booleanQuestion("You can choose old character. Do it?(y/n)", "Please write y or n")) {
				view.showAllHeroes(heroes);
				hero = view.selectHero(heroes);
			} else {
				hero = view.createHero();
				DAOFactory.getHeroDAO().save(hero);
			}
		} else {
			hero = view.createHero();
			DAOFactory.getHeroDAO().save(hero);
		}
		return hero;
	}

	public SelectHeroController() {
		this.heroes = null;
		this.view = Config.getMode().equals(GameMode.CONSOLE) ? new SelectHeroConsolePage(this) : new SelectHeroSwingPage(this);
	}

	public List<String> validateHero(Hero.HeroBuilder builder) {
		ArrayList<String> result = new ArrayList<>();
		if (DAOFactory.getHeroDAO().getForName(builder.getCharacter().getName()) != null) {
			result.add("This name already exist!");
		}
		for (ConstraintViolation<Hero> error : builder.validate()) {
			result.add(error.getMessage());
		}
		return result;
	}
}
