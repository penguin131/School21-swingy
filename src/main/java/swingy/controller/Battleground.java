package swingy.controller;

import swingy.Game;
import swingy.helper.Config;
import swingy.helper.GameMode;
import swingy.helper.GameResult;
import swingy.model.Coordinate;
import swingy.model.GameCharacter;
import swingy.model.Hero;
import swingy.model.Villain;
import swingy.view.console.ConsolePage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Battleground {

	private Map<Coordinate, GameCharacter> characters;
	private ConsolePage view;
	private static final int VILLAIN_AMOUNT = Integer.parseInt(Config.getConfig().getProperty("villain.amount"));

	/**
	 * Генерация карты, в зависимости от уровня игрока
	 */
	public void generateMap(Hero hero) {
		characters = null;
		characters = new HashMap<>();
		int mapSize = (hero.getCurrentLvl() - 1) * 5 + 9;
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
	public GameResult playGame() throws IOException, InterruptedException {
		if (Config.getMode().equals(GameMode.CONSOLE)) {
			while (true) {
				char ch = (char) System.in.read();
				System.out.println("\r" + (int)ch);

				if (ch == 27) {
					Game.closeConsole();
				}
			}

		}
		return GameResult.LOSE;
	}

	public Battleground(ConsolePage view) {
		this.view = view;
	}
}
