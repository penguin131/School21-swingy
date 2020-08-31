package swingy.view.swing;

import swingy.controller.SelectHeroController;
import swingy.model.Hero;
import swingy.view.SelectHeroView;

import javax.swing.*;
import java.util.List;

public class SelectHeroSwingPage  extends JFrame implements SelectHeroView {
	private SelectHeroController controller;

	@Override
	public void welcome() {

	}

	@Override
	public void showAllHeroes(List<Hero> heroes) {

	}

	@Override
	public boolean booleanQuestion(String text, String errorText) {
		return true;
	}

	@Override
	public Hero selectHero(List<Hero> heroes) {
		return null;
	}

	@Override
	public Hero createHero() {
		return null;
	}

	@Override
	public void destroy() {

	}

	public SelectHeroSwingPage(SelectHeroController controller) {
		this.controller = controller;
	}
}
