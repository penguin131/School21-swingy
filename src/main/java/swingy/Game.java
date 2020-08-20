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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
	private static final int MAX_LVL = Integer.parseInt(Config.getConfig().getProperty("maxLvl"));
	private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {
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

		Hero hero = new SelectHeroController(bufferedReader).selectHero();//GOTOVO VRODE
		selectMode();
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

	private static void selectMode() {
		try {
			String line;
			while (true) {
				System.out.println("Please select game mode(swing or console):");
				line = bufferedReader.readLine();
				if ("swing".equals(line)) {
					Config.setMode(GameMode.SWING);
					break;
				} else if ("console".equals(line)) {
					Config.setMode(GameMode.CONSOLE);
					break;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
