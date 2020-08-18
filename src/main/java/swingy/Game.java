package swingy;

import swingy.controller.Battleground;
import swingy.helper.Config;
import swingy.helper.GameMode;
import swingy.helper.GameResult;
import swingy.model.Hero;
import swingy.view.console.ConsolePage;

import java.io.IOException;

public class Game {
	private static final int MAX_LVL = Integer.parseInt(Config.getConfig().getProperty("maxLvl"));

	public static void main(String[] args) throws IOException, InterruptedException {
		if (args.length != 1 || !"console".equals(args[0]) && !"swing".equals(args[0])) {
			System.out.println("Usage: <mode>\n" +
					"		mode: console, swing");
			System.exit(1);
		} else {
			Config.setMode("console".equals(args[0]) ? GameMode.CONSOLE : GameMode.SWING);
		}
		initConsole();
		Hero hero = new Hero();
		hero.downloadOrSetHero();
		//todo swing view with common interface
		Battleground battleground = new Battleground(new ConsolePage());
		while (hero.getCurrentLvl() <= MAX_LVL) {
			battleground.generateMap(hero);
			if (battleground.playGame().equals(GameResult.WIN))
				hero.increaseLvl();
		}
		printWinner();
	}

	private static void printWinner() {
		//послать на вьюху победку
	}

	public static void closeConsole() throws IOException, InterruptedException {
		String[] cmd = new String[] {"/bin/sh", "-c", "stty sane </dev/tty"};
		Runtime.getRuntime().exec(cmd).waitFor();
		System.exit(0);
	}

	private static void initConsole() throws IOException, InterruptedException {
		String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
		Runtime.getRuntime().exec(cmd).waitFor();
	}
}
