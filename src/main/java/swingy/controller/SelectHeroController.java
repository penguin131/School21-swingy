package swingy.controller;

import swingy.Exceptions.ParseException;
import swingy.model.Hero;
import swingy.model.HeroClass;

import javax.validation.ConstraintViolation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

public class SelectHeroController {
	private List<Hero> heroes;
	BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

	//todo первоочередное
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
		heroes = Hero.downloadAll();
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
		return hero;
	}

	public SelectHeroController() {
		this.heroes = null;
	}

	private void clearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	private Hero selectHeroFromBase() {
		return null;
	}

	private Hero createHero() {
		clearConsole();
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
				System.out.println("Please write hero class!(0 or 1):");
				line = buffer.readLine();
				try {
					int i = Integer.parseInt(line);
					if (i < 0 || i >= HeroClass.values().length)
						throw new ParseException("Non-existent class");
					builder.setHeroClass(HeroClass.values()[i]);
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
		hero.save();
		return hero;
	}
}