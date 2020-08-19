package swingy;

import swingy.controller.BattlegroundController;
import swingy.controller.SelectHeroController;
import swingy.helper.Config;
import swingy.helper.GameMode;
import swingy.helper.GameResult;
import swingy.model.Hero;
import swingy.view.BattleView;
import swingy.view.SelectHeroView;
import swingy.view.console.BattleConsolePage;
import swingy.view.console.SelectHeroConsolePage;
import swingy.view.swing.BattleSwingPage;
import swingy.view.swing.SelectHeroSwingPage;

public class Game {
	private static final int MAX_LVL = Integer.parseInt(Config.getConfig().getProperty("maxLvl"));
	private static final String USAGE = "Usage: <mode>\n		mode: console, swing";

	public static void main(String[] args) {
//		if (args.length != 1 || !"console".equals(args[0]) && !"swing".equals(args[0])) {
//			System.out.println(USAGE);
//			System.exit(1);
//		} else {
//			Config.setMode("console".equals(args[0]) ? GameMode.CONSOLE : GameMode.SWING);
//		}
		Config.setMode(GameMode.CONSOLE);
		SelectHeroView selectHeroView;
		BattleView battleView;

		if (Config.getMode().equals(GameMode.CONSOLE)) {
			selectHeroView = new SelectHeroConsolePage();
			battleView = new BattleConsolePage();
		} else {
			selectHeroView = new SelectHeroSwingPage();
			battleView = new BattleSwingPage();
		}

		Hero hero = new SelectHeroController().selectHero();

		BattlegroundController battleground = new BattlegroundController(battleView);
		if (Config.getMode().equals(GameMode.CONSOLE))
			initConsole();
		while (hero.getCurrentLvl() <= MAX_LVL) {
			battleground.generateMap(hero);
			if (battleground.playGame().equals(GameResult.WIN))
				hero.increaseLvl();
		}
	}

	private static void initConsole() {
		try {
			String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
			Runtime.getRuntime().exec(cmd).waitFor();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
