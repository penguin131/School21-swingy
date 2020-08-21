package swingy.view;

import swingy.model.Hero;

import java.util.List;

public interface SelectHeroView extends View {
	void welcome();
	void showAllHeroes(List<Hero> heroes);
	void clearConsole();
	void question(String text);
	void warning(String text);
}
