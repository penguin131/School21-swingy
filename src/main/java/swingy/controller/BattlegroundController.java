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

import static swingy.helper.GeneratorFactory.*;
import static swingy.view.utils.Button.*;

public class BattlegroundController {

	private Map<Coordinate, GameCharacter> characters;
	private Coordinate heroCoordinates;
	private int mapSize;
	private static int VILLAIN_AMOUNT;
	private BattleView view;
	private GameStatus status;
	private Hero.HeroBuilder heroBuilder;

	public GameStatus getStatus() {
		return status;
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
			characters.put(generateNewCoordinate(mapSize, characters),
					generateVillain(hero.getCurrentLvl()));
		}
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

	public void pressButton(Button button) throws IOException, InterruptedException {
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

	private void getStep(Button button) throws IOException, InterruptedException {
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

	private void battle(Coordinate villainCoordinates) throws IOException, InterruptedException {
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
			if (view.fightChoice()) {
				characters.remove(villainCoordinates);
				replaceCoordinates(villainCoordinates);
				hero.updateExp();
				if (hero.getCurrentLvl() > Config.getMaxLvl()) {
					System.out.println("YOU WIN GAME!");
					System.exit(0);
				}
				heroBuilder.setCharacter(hero);
				Artifact artifact = generateArtifact(hero.getCurrentLvl());
				if (artifact != null && view.artifactChoice(artifact)) {
					heroBuilder.takeArtifact(artifact);
				}
			}
		}
	}

	public BattlegroundController() {
		VILLAIN_AMOUNT = Integer.parseInt(Config.getConfig().getProperty("villain.amount"));
		heroBuilder = new Hero.HeroBuilder(null);
	}
}
