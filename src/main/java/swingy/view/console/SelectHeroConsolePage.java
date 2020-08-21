package swingy.view.console;

import swingy.model.Hero;
import swingy.view.SelectHeroView;

import java.util.List;

public class SelectHeroConsolePage extends ConsolePage implements SelectHeroView {
	public static final String ANSI_WHITE = "\u001B[37m";
	public static final String ANSI_RED = "\u001B[31m";

	@Override
	public void welcome() {
		System.out.println("Hello! Welcome to swingy game!\nPress enter to continue...");
	}

	@Override
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
	public void question(String text) {
		System.out.println(text);
	}

	@Override
	public void warning(String text) {
		System.out.println(ANSI_RED + text + ANSI_WHITE);
	}
}
