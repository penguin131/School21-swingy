package swingy.controller;

import swingy.Exceptions.ParseException;
import swingy.model.Hero;
import swingy.model.HeroClass;
import swingy.model.dao.DAOFactory;
import swingy.view.SelectHeroView;

import javax.validation.ConstraintViolation;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class SelectHeroController {
	private List<Hero> heroes;
	private BufferedReader buffer;
	private SelectHeroView view;

	public Hero selectHero() throws IOException {
		String line;
		view.clearConsole();
		view.welcome();
		buffer.readLine();
		Hero hero;
		heroes = DAOFactory.getHeroDAO().getAll();
		view.clearConsole();
		if (heroes != null && heroes.size() > 0) {
			view.question("You can choose old character. Do it?(y/n)");
			while (true) {
				line = buffer.readLine();
				if (!line.equals("y") && !line.equals("n")) {
					view.warning("Please write y or n!");
				} else
					break;
			}
			if ("y".equals(line))
				hero = selectHeroFromBase();
			else
				hero = createHero();
		} else
			hero = createHero();
		view.clearConsole();
		return hero;
	}

	public SelectHeroController(BufferedReader buffer, SelectHeroView view) {
		this.heroes = null;
		this.buffer = buffer;
		this.view = view;
	}

	private Hero selectHeroFromBase() throws IOException {
		view.clearConsole();
		view.showAllHeroes(heroes);
		String line;
		Hero result;
		while ((result = findHeroForName(line = buffer.readLine())) == null) {
			view.warning(String.format("Name %s does not exists! Please write correct name.", line));
		}
		return result;
	}

	private Hero findHeroForName(String heroName) {
		for (Hero hero : heroes) {
			if (hero.getName().equals(heroName))
				return hero;
		}
		return null;
	}

	private Hero createHero() throws IOException {
		String line;
		Hero hero = new Hero();
		Hero.HeroBuilder builder = new Hero.HeroBuilder(hero);
		Set<ConstraintViolation<Hero>> errors;
		view.clearConsole();
		while (true) {
			String parseError = null;
			view.question("Please write hero Name!:");
			while (DAOFactory.getHeroDAO().getForName(line = buffer.readLine()) != null) {
				view.warning("This name already exist!");
			}
			builder.setName(line);
			view.question("Please write hero class!(1(MAN) or 2(WOMAN)):");
			line = buffer.readLine();
			try {
				int code = Integer.parseInt(line);
				if (!HeroClass.containsCode(code))
					throw new ParseException("Non-existent class");
				builder.setHeroClass(HeroClass.values()[code - 1]);
			} catch (NumberFormatException | ParseException ex) {
				parseError = ex.getMessage();
			}
			errors = builder.validate();
			if (errors.size() == 0 && parseError == null)
				break;
			if (parseError != null)
				view.warning("Parse error: " + parseError);
			for (ConstraintViolation<Hero> error : errors) {
				view.warning(error.getMessage());
			}
		}
		view.clearConsole();
		System.out.println("OK HERO");//remove
		DAOFactory.getHeroDAO().save(hero);
		return hero;
	}
}
