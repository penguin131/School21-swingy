package swingy.view;

import swingy.model.Hero;

import java.io.IOException;
import java.util.List;

public interface SelectHeroView extends View {
	void welcome() throws IOException;
	void showAllHeroes(List<Hero> heroes);
	boolean booleanQuestion(String text, String errorText) throws IOException;
	Hero selectHero(List<Hero> heroes) throws IOException, InterruptedException;
	Hero createHero() throws IOException, InterruptedException;
}
