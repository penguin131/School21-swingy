package swingy.controller;

import swingy.Exceptions.GenerateMapException;
import swingy.helper.Config;
import swingy.helper.GameMode;
import swingy.helper.GameResult;
import swingy.model.Coordinate;
import swingy.model.GameCharacter;
import swingy.model.Hero;
import swingy.model.Villain;
import swingy.view.BattleView;
import swingy.view.console.ConsoleBattlePage;
import swingy.view.swing.SwingBattlePage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BattlegroundController {

	private Map<Coordinate, GameCharacter> characters;
	private int mapSize;
	private static int VILLAIN_AMOUNT;
	private Random random;
	private static final int MAX_ITERATIONS = 100;

	/**
	 * Генерация карты, в зависимости от уровня игрока
	 */
	public void generateMap(Hero hero) {
		characters = null;
		characters = new HashMap<>();
		mapSize = (hero.getCurrentLvl() - 1) * 5 + 9;
		characters.put(new Coordinate(mapSize / 2, mapSize / 2), hero);
		try {
			for (int i = 0; i < VILLAIN_AMOUNT; i++) {
				characters.put(generateNewCoordinate(mapSize), new Villain());
			}
		} catch (GenerateMapException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	private Coordinate generateNewCoordinate(int mapSize) throws GenerateMapException {
		Coordinate result = new Coordinate();
		int i = 0;
		while (i < MAX_ITERATIONS) {
			result.setX(random.nextInt(mapSize));
			result.setY(random.nextInt(mapSize));
			if (!characters.containsKey(result))
				break;
			i++;
		}
		if (i == MAX_ITERATIONS) {
			throw new GenerateMapException("To many iterations.");
		}
		return result;
	}

	/**
	 * Цикл игры
	 */
	public GameResult playGame() throws IOException {
		BattleView view;
		if (Config.getMode().equals(GameMode.SWING)) {
			view = new SwingBattlePage(characters, mapSize);
//			swingBattlePage.setVisible(true);
		} else {
			view = new ConsoleBattlePage();
		}
		while (true) {
			view.printMap();

			break;
//			char ch = (char) System.in.read();
//			if (ch == Button.ESC.getCode()) battleView.destroy();
//			if (Button.isStep(ch)) {
//				//todo логику движения, сражения или перехода на некст лвл. Доп окошки могут вылезти внутри этого блока, для них отдельный цикл?
//			}
		}
		view.destroy();
		return GameResult.LOSE;
	}

	public BattlegroundController() {
		VILLAIN_AMOUNT = Integer.parseInt(Config.getConfig().getProperty("villain.amount"));
		random = new Random();
	}
}
