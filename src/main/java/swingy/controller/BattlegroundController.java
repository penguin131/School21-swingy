package swingy.controller;

import swingy.Exceptions.GenerateMapException;
import swingy.helper.Config;
import swingy.helper.GameMode;
import swingy.helper.GameStatus;
import swingy.model.*;
import swingy.model.dao.DAOFactory;
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
	private Hero.HeroBuilder heroBuilder;

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	/**
	 * Генерация карты, в зависимости от уровня игрока
	 */
	public void generateMap(Hero hero) throws GenerateMapException {
		status = GameStatus.PLAY;
		characters = null;
		characters = new HashMap<>();
		mapSize = (hero.getCurrentLvl() - 1) * 5 + 9;
		heroCoordinates = new Coordinate(mapSize / 2, mapSize / 2);
		characters.put(heroCoordinates, hero);
		for (int i = 0; i < VILLAIN_AMOUNT; i++) {
			characters.put(generateNewCoordinate(mapSize), generateVillain(hero.getCurrentLvl()));
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

	private Villain generateVillain(int heroLvl) {
		return new Villain(heroLvl / 2, heroLvl / 4, heroLvl * 2);
	}

	/**
	 * Цикл игры
	 */
	public GameStatus playGame() throws IOException, InterruptedException {
		if (Config.getMode().equals(GameMode.SWING)) {
			view = new SwingBattlePage(characters, mapSize, this);
		} else {
			view = new ConsoleBattlePage(characters, mapSize, this);
		}
		view.printMap();
		while (status.equals(GameStatus.PLAY)) {
			Thread.sleep(100);
		}
		view.destroy();
		if (status.equals(GameStatus.LOOSE))
			return GameStatus.LOOSE;
		else
			return GameStatus.WIN;
	}

	public void pressButton(Button button) throws IOException {
		if (button == null)
			return;
		if (Button.isStep(button.getCode())) {
			getStep(button);
		}
		if (button.equals(ESC)) {
			Hero hero = (Hero) characters.get(heroCoordinates);
			DAOFactory.getHeroDAO().update(hero);
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
		if (view instanceof SwingBattlePage)
			view.printMap();
	}

	private void replaceCoordinates(Coordinate newCoordinates) {
		GameCharacter character = characters.remove(heroCoordinates);
		heroCoordinates.setX(newCoordinates.getX());
		heroCoordinates.setY(newCoordinates.getY());
		characters.put(heroCoordinates, character);
	}

	private void battle(Coordinate villainCoordinates) {
		GameCharacter enemy = characters.get(villainCoordinates);
		if (!(enemy instanceof Villain))
			return;
		Villain villain = (Villain) enemy;
		Hero hero = (Hero) characters.get(heroCoordinates);
		int heroHp = hero.getHitPoint();
		int villainHp = villain.getHitPoint();
		boolean heroStep = true;
		int counter = 0;
		while (heroHp > 0 && villainHp > 0 && counter < 50) {
			if (heroStep) {
				heroStep = false;
				villainHp = villainHp - (hero.getAttack() - villain.getDefence());
			} else {
				heroStep = true;
				heroHp = heroHp - (villain.getAttack() - hero.getDefence());
			}
			counter++;
		}
		if (heroHp <= 0) {
			status = GameStatus.LOOSE;
		} else {
			characters.remove(villainCoordinates);
			replaceCoordinates(villainCoordinates);
			hero.updateExp();
			heroBuilder.setCharacter(hero);
			heroBuilder.takeArtifact(villain.generateArtifact(hero.getCurrentLvl()));
		}
	}

	public BattlegroundController() {
		VILLAIN_AMOUNT = Integer.parseInt(Config.getConfig().getProperty("villain.amount"));
		random = new Random();
		heroBuilder = new Hero.HeroBuilder(null);
	}
}
