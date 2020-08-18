package swingy.controller;

import swingy.helper.Config;
import swingy.helper.GameMode;
import swingy.helper.GameResult;
import swingy.model.Coordinate;
import swingy.model.GameCharacter;
import swingy.model.Hero;
import swingy.model.Villain;
import swingy.view.View;
import swingy.view.console.Button;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BattlegroundController {

	private Map<Coordinate, GameCharacter> characters;
	private View view;
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
	public GameResult playGame() throws IOException {
		if (Config.getMode().equals(GameMode.CONSOLE)) {
			view.printMap(characters, mapSize);
			while (true) {
				char ch = (char) System.in.read();
				if (ch == Button.ESC.getCode()) view.destroy();
				if (Button.isStep(ch)) {
					//todo логику движения, сражения или перехода на некст лвл. Доп окошки могут вылезти внутри этого блока, для них отдельный цикл?
				}
				view.printMap(characters, mapSize);
			}
		}
		return GameResult.LOSE;
	}

	public BattlegroundController(View view) {
		this.view = view;
		VILLAIN_AMOUNT = Integer.parseInt(Config.getConfig().getProperty("villain.amount"));
	}
}
