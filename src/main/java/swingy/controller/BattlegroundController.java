package swingy.controller;

import swingy.helper.Config;
import swingy.helper.GameMode;
import swingy.helper.GameResult;
import swingy.model.Coordinate;
import swingy.model.GameCharacter;
import swingy.model.Hero;
import swingy.model.Villain;
import swingy.view.BattleView;
import swingy.view.utils.Button;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BattlegroundController {

	private Map<Coordinate, GameCharacter> characters;
	private BattleView battleView;
	private int mapSize;
	private static int VILLAIN_AMOUNT;

	/**
	 * Генерация карты, в зависимости от уровня игрока
	 */
	public void generateMap(Hero hero) {
		characters = null;
		characters = new HashMap<>();
		mapSize = (hero.getCurrentLvl() - 1) * 5 + 9;
		characters.put(new Coordinate(mapSize / 2, mapSize / 2), hero);
		for (int i = 0; i < VILLAIN_AMOUNT; i++) {
			characters.put(generateNewCoordinate(mapSize), new Villain());
		}
	}

	private Coordinate generateNewCoordinate(int mapSize) {
		//todo генерацию не повторяющихся координат
		return new Coordinate(mapSize, mapSize);
	}

	/**
	 * Цикл игры
	 */
	public GameResult playGame() {
		if (Config.getMode().equals(GameMode.CONSOLE)) {
			battleView.printMap(characters, mapSize);
			while (true) {
				try {
					char ch = (char) System.in.read();
					if (ch == Button.ESC.getCode()) battleView.destroy();
					if (Button.isStep(ch)) {
						//todo логику движения, сражения или перехода на некст лвл. Доп окошки могут вылезти внутри этого блока, для них отдельный цикл?
					}
					battleView.printMap(characters, mapSize);
				} catch (IOException ex) {
					ex.printStackTrace();
					System.exit(1);
				}
			}
		}
		return GameResult.LOSE;
	}

	public BattlegroundController(BattleView battleView) {
		this.battleView = battleView;
		VILLAIN_AMOUNT = Integer.parseInt(Config.getConfig().getProperty("villain.amount"));
	}
}
