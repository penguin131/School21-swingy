package swingy;

import swingy.controller.BattlegroundController;
import swingy.controller.SelectHeroController;
import swingy.helper.Config;
import swingy.helper.GameMode;
import swingy.helper.GameResult;
import swingy.model.Hero;
import swingy.model.HeroClass;
import swingy.view.SelectHeroView;
import swingy.view.console.ConsoleBattlePage;
import swingy.view.console.SelectHeroConsolePage;
import swingy.view.swing.SwingBattlePage;

import javax.swing.*;
import java.awt.*;
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
//			hero = new SelectHeroController(bufferedReader, selectHeroView).selectHero();
			hero = new Hero(HeroClass.MAN);
//			selectMode(selectHeroView);
			Config.setMode(GameMode.SWING);
			BattlegroundController battleground = new BattlegroundController();
			if (Config.getMode().equals(GameMode.CONSOLE))
				initConsole();
			while (hero.getCurrentLvl() <= MAX_LVL) {
				battleground.generateMap(hero);
				GameResult result = battleground.playGame();
				if (result.equals(GameResult.LVL_UP))
					hero.increaseLvl();
				if (result.equals(GameResult.LOSE)) {
					System.out.println("YOU LOOSE");
					System.exit(0);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
//	public static void main(String[] args) {
//		JFrame frame = new JFrame("Demo Frame");
//		JPanel panel = new JPanel();
//		JLabel label = new JLabel("SUBJECT ");
//		label.setIcon(new ImageIcon("/Users/bootcamp/Desktop/swingy/target/classes/images/hero.jpg"));
//		JTextArea text = new JTextArea();
//		text.setText("Add subject here...");
//		panel.setLayout(new GridBagLayout());
//		panel.add(label);
//		panel.add(text);
//		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		frame.add(panel);
//		frame.setSize(500, 300);
//		frame.setVisible(true);
//	}

	private static void initConsole() {
		try {
			String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
			Runtime.getRuntime().exec(cmd).waitFor();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
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
