package swingy.controller;

import swingy.Exceptions.GenerateMapException;
import swingy.helper.Config;
import swingy.helper.GameMode;
import swingy.helper.GameResult;
import swingy.helper.GameStatus;
import swingy.model.Coordinate;
import swingy.model.GameCharacter;
import swingy.model.Hero;
import swingy.model.Villain;
import swingy.view.BattleView;
import swingy.view.console.ConsoleBattlePage;
import swingy.view.swing.SwingBattlePage;
import swingy.view.utils.Button;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static swingy.view.utils.Button.*;

public class BattlegroundController {

	private Map<Coordinate, GameCharacter> characters;
	private Coordinate heroCoordinates;
	private int mapSize;
	private static int VILLAIN_AMOUNT;
	private Random random;
	private static final int MAX_ITERATIONS = 100;
	private BattleView view;
	private GameStatus status;

	/**
	 * Генерация карты, в зависимости от уровня игрока
	 */
	public void generateMap(Hero hero) {
		status = GameStatus.PLAY;
		characters = null;
		characters = new HashMap<>();
		mapSize = (hero.getCurrentLvl() - 1) * 5 + 9;
		heroCoordinates = new Coordinate(mapSize / 2, mapSize / 2);
		characters.put(heroCoordinates, hero);
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
	public GameResult playGame() throws IOException, InterruptedException {
		if (Config.getMode().equals(GameMode.SWING)) {
			view = new SwingBattlePage(characters, mapSize, this);
		} else {
			view = new ConsoleBattlePage();
		}
		view.printMap();
		while (status.equals(GameStatus.PLAY)) {
			Thread.sleep(100);
		}
		view.destroy();
		if (status.equals(GameStatus.LOOSE))
			return GameResult.LOSE;
		else
			return GameResult.WIN;
	}

	public BattlegroundController() {
		VILLAIN_AMOUNT = Integer.parseInt(Config.getConfig().getProperty("villain.amount"));
		random = new Random();
	}

	public void pressButton(Button button) throws IOException {
		if (button == null)
			return;
		if (Button.isStep(button.getCode())) {
			getStep(button);
		}
		if (button.equals(ESC)) {
			System.exit(0);
		}
	}

	private void getStep(Button button) throws IOException {
		Coordinate coordinate;
		if (button.equals(LEFT)) {
			if (heroCoordinates.getX() == 0) {
				status = GameStatus.WIN;
			} else {
				coordinate = new Coordinate(heroCoordinates.getX() - 1, heroCoordinates.getY());
				if (characters.containsKey(coordinate)) {
					battle(coordinate);
				} else {
					replaceCoordinates(coordinate);
				}
			}

		} else if (button.equals(RIGHT)) {
			if (heroCoordinates.getX() == mapSize - 1) {
				status = GameStatus.WIN;
			} else {
				coordinate = new Coordinate(heroCoordinates.getX() + 1, heroCoordinates.getY());
				if (characters.containsKey(coordinate)) {
					battle(coordinate);
				} else {
					replaceCoordinates(coordinate);
				}
			}

		} else if (button.equals(UP)) {
			if (heroCoordinates.getY() == 0) {
				status = GameStatus.WIN;
			} else {
				coordinate = new Coordinate(heroCoordinates.getX(), heroCoordinates.getY() - 1);
				if (characters.containsKey(coordinate)) {
					battle(coordinate);
				} else {
					replaceCoordinates(coordinate);
				}
			}

		} else if (button.equals(DOWN)) {
			if (heroCoordinates.getY() == mapSize - 1) {
				status = GameStatus.WIN;
			} else {
				coordinate = new Coordinate(heroCoordinates.getX(), heroCoordinates.getY() + 1);
				if (characters.containsKey(coordinate)) {
					battle(coordinate);
				} else {
					replaceCoordinates(coordinate);
				}
			}
		}
		view.printMap();
	}

	private void replaceCoordinates(Coordinate newCoordinates) {
		GameCharacter character = characters.remove(heroCoordinates);
		heroCoordinates.setX(newCoordinates.getX());
		heroCoordinates.setY(newCoordinates.getY());
		characters.put(heroCoordinates, character);
	}

	private void battle(Coordinate villainCoordinates) {

	}
}
