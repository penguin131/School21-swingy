package swingy.controller;

import swingy.Exceptions.ParseException;
import swingy.model.Hero;
import swingy.model.HeroClass;
import swingy.model.dao.DAOFactory;

import javax.validation.ConstraintViolation;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class SelectHeroController {
	private List<Hero> heroes;
	private BufferedReader buffer;

	public Hero selectHero() {
		String line;
		clearConsole();
		System.out.println("Hello! Welcome to swingy game!\nPress enter to continue...");
		try {
			buffer.readLine();
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
		Hero hero = null;
		heroes = DAOFactory.getHeroDAO().getAll();
		clearConsole();
		if (heroes != null && heroes.size() > 0) {
			System.out.println("You can choose old character. Do it?(y/n)");
			try {
				while (true) {
					line = buffer.readLine();
					if (!line.equals("y") && !line.equals("n")) {
						System.out.println("Please write y or n!");
					} else
						break;
				}
				if ("y".equals(line))
					hero = selectHeroFromBase();
				else
					hero = createHero();
			} catch (IOException ex) {
				ex.printStackTrace();
				System.exit(1);
			}
		} else
			hero = createHero();
		clearConsole();
		return hero;
	}

	public SelectHeroController(BufferedReader buffer) {
		this.heroes = null;
		this.buffer = buffer;
	}

	private void clearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	private Hero selectHeroFromBase() {
		clearConsole();
		for (Hero hero : heroes) {
			System.out.println(String.format(
					"=====================================\n" +
					"==================HERO:==============\n" +
					"===== NAME:%20s =====\n" +
					"===== LEVEL:%19d =====\n" +
					"===== CLASS:%19s =====\n" +
					"=====================================",
					hero.getName(),
					hero.getCurrentLvl(),
					hero.getHeroClass().getName()));
		}
		System.out.println("Please write hero name to select it:");
		String line;
		Hero result = null;
		try {
			while ((result = findHeroForName(line = buffer.readLine())) == null) {
				System.out.println(String.format("Name %s does not exists! Please write correct name.", line));
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
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

	private Hero createHero() {
		String line;
		Hero hero = new Hero();
		Hero.HeroBuilder builder = new Hero.HeroBuilder(hero);
		try {
			Set<ConstraintViolation<Hero>> errors;
			clearConsole();
			while (true) {
				String parseError = null;
				System.out.println("Please write hero Name!:");
				line = buffer.readLine();
				builder.setName(line);
				System.out.println("Please write hero class!(1(MAN) or 2(WOMAN)):");
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
					System.out.println("Parse error: " + parseError);
				for (ConstraintViolation<Hero> error : errors) {
					System.out.println(error.getMessage());
				}
			}
			clearConsole();
			System.out.println("OK HERO");
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
		DAOFactory.getHeroDAO().save(hero);
		return hero;
	}
}
