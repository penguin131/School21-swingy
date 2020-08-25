package swingy;

import swingy.Exceptions.GenerateMapException;
import swingy.controller.BattlegroundController;
import swingy.controller.SelectHeroController;
import swingy.helper.Config;
import swingy.helper.GameMode;
import swingy.helper.GameStatus;
import swingy.model.Hero;
import swingy.view.SelectHeroView;
import swingy.view.console.SelectHeroConsolePage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
	private static final int MAX_LVL = Integer.parseInt(Config.getConfig().getProperty("maxLvl"));
	private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {
		Config.setMode(GameMode.CONSOLE);
		SelectHeroView selectHeroView;
		Hero hero;

		selectHeroView = new SelectHeroConsolePage();
		try {
			hero = new SelectHeroController(bufferedReader, selectHeroView).selectHero();
//			hero = new Hero(HeroClass.MAN);
			selectMode(selectHeroView);
//			Config.setMode(GameMode.SWING);
			BattlegroundController battleground = new BattlegroundController();
			while (hero.getCurrentLvl() <= MAX_LVL) {//после каждого цикла открывается новая карта
				battleground.generateMap(hero);
				GameStatus result = battleground.playGame();
				if (result.equals(GameStatus.LOOSE)) {
					System.out.println("YOU LOOSE");
					System.exit(0);
				}
			}
		} catch (IOException | InterruptedException | GenerateMapException ex) {
			ex.printStackTrace();
		}
	}

	private static void selectMode(SelectHeroView view) {
		try {
			String line;
			while (true) {
				view.question("Please select game mode(swing or console):");
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
