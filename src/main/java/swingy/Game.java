package swingy;

import swingy.Exceptions.GenerateMapException;
import swingy.controller.BattlegroundController;
import swingy.controller.SelectHeroController;
import swingy.helper.Config;
import swingy.helper.GameMode;
import swingy.helper.GameStatus;
import swingy.model.Hero;

import java.io.IOException;

public class Game {
	private static final int MAX_LVL = Integer.parseInt(Config.getConfig().getProperty("maxLvl"));

	public static void main(String[] args) {
		if (args.length != 1 || (!args[0].equals("console") && !args[0].equals("swing"))) {
			System.out.println("Please select game mode(swing or console)");
			System.exit(0);
		}
		if (args[0].equals("console")) {
			Config.setMode(GameMode.CONSOLE);
		} else {
			Config.setMode(GameMode.SWING);
		}
		Hero hero;
		try {
			hero = new SelectHeroController().selectHero();
			BattlegroundController battleground = new BattlegroundController();
			while (hero.getCurrentLvl() <= MAX_LVL) {
				battleground.generateMap(hero);
				GameStatus result = battleground.playGame();
				if (result.equals(GameStatus.LOOSE)) {
					System.out.println("YOU LOOSE");
					System.exit(0);
				}
			}
			System.out.println("YOU WIN!");
		} catch (IOException | InterruptedException | GenerateMapException ex) {
			ex.printStackTrace();
		}
	}
}
