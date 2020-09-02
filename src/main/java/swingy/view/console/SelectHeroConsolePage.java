package swingy.view.console;

import swingy.controller.SelectHeroController;
import swingy.model.Hero;
import swingy.model.HeroClass;
import swingy.view.SelectHeroView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class SelectHeroConsolePage extends ConsolePage implements SelectHeroView {
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	public static final String ANSI_WHITE = "\u001B[37m";
	public static final String ANSI_RED = "\u001B[31m";
	private SelectHeroController controller;

	@Override
	public void welcome() throws IOException {
		clearConsole();
		System.out.println("Hello! Welcome to swingy game!\nPress enter to continue...");
		reader.readLine();
		clearConsole();
	}

	public void showAllHeroes(List<Hero> heroes) {
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
	}

	@Override
	public boolean booleanQuestion(String text, String errorText) throws IOException {
		System.out.println(text);
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.equals("y")) {
				return true;
			} else if (line.equals("n")) {
				return false;
			}
			warning(errorText);
		}
		return false;
	}

	private void warning(String text) {
		System.out.println(ANSI_RED + text + ANSI_WHITE);
	}

	@Override
	public Hero selectHero(List<Hero> heroes) throws IOException {
		String line;
		Hero result;
		while ((result = findHeroForName(line = reader.readLine(), heroes)) == null) {
			warning(String.format("Name %s does not exists! Please write correct name.", line));
		}
		return result;
	}

	@Override
	public Hero createHero() throws IOException {
		Hero hero = new Hero();
		Hero.HeroBuilder builder = new Hero.HeroBuilder(hero);
		clearConsole();
		while (true) {
			System.out.println("Please write hero Name!:");
			builder.setName(reader.readLine());
			boolean isWoman = booleanQuestion("Do you want to play as a woman(y) or a man(n)?", "Please write y or n!");
			builder.setHeroClass(isWoman ? HeroClass.WOMAN : HeroClass.MAN);

			List<String> errors = controller.validate(builder);
			if (errors == null || errors.size() == 0) {
				break;
			}
			warning("Parse errors:");
			for (String error : errors) {
				warning(error);
			}
		}
		return hero;
	}

	public SelectHeroConsolePage(SelectHeroController controller) {
		clearConsole();
		this.controller = controller;
	}

	private Hero findHeroForName(String heroName, List<Hero> heroes) {
		for (Hero hero : heroes) {
			if (hero.getName().equals(heroName))
				return hero;
		}
		return null;
	}
}
