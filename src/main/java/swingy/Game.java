package swingy;

import swingy.controller.BattlegroundController;
import swingy.controller.CreateHeroController;
import swingy.helper.Config;
import swingy.helper.GameMode;
import swingy.helper.GameResult;
import swingy.model.Hero;
import swingy.view.View;
import swingy.view.console.ConsolePage;
import swingy.view.swing.SwingPage;

import java.io.IOException;

public class Game {
	private static final int MAX_LVL = Integer.parseInt(Config.getConfig().getProperty("maxLvl"));
	private static final String USAGE = "Usage: <mode>\n		mode: console, swing";

	public static void main(String[] args) throws IOException {
		if (args.length != 1 || !"console".equals(args[0]) && !"swing".equals(args[0])) {
			System.out.println(USAGE);
			System.exit(1);
		} else {
			Config.setMode("console".equals(args[0]) ? GameMode.CONSOLE : GameMode.SWING);
			View view = Config.getMode().equals(GameMode.CONSOLE) ? new ConsolePage() : new SwingPage();
			view.init();
			Hero hero = new Hero();
			hero.init();
			if (hero.isNew()) {
				new CreateHeroController(view, hero).createHero();
			}
			BattlegroundController battleground = new BattlegroundController(view);
			while (hero.getCurrentLvl() <= MAX_LVL) {
				battleground.generateMap(hero);
				if (battleground.playGame().equals(GameResult.WIN))
					hero.increaseLvl();
			}
		}
	}
}
