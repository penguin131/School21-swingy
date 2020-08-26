package swingy.view.console;

import swingy.controller.BattlegroundController;
import swingy.helper.GameStatus;
import swingy.model.Coordinate;
import swingy.model.GameCharacter;
import swingy.model.Hero;
import swingy.model.Villain;
import swingy.view.BattleView;
import swingy.view.utils.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Random;

public class ConsoleBattlePage extends ConsolePage implements BattleView {

	private Map<Coordinate, GameCharacter> characters;
	private int mapSize;
	private BattlegroundController controller;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public ConsoleBattlePage(Map<Coordinate, GameCharacter> characters, int mapSize, BattlegroundController controller) {
		this.characters = characters;
		this.mapSize = mapSize;
		this.controller = controller;
	}

	@Override
	public void printMap() {
		PrintThread printThread = new PrintThread();
		printThread.start();
	}

	@Override
	public boolean choice() throws IOException {
		System.out.println("Do you want to fight?");
		String line;
		while (!isTrueFalse(line = reader.readLine())) {
			System.out.println("Write true or false");
		}
		if (line.equals("true"))
			return true;
		else
			return new Random().nextBoolean();
	}

	private boolean isTrueFalse(String line) {
		return line.equals("true") || line.equals("false");
	}

	private class PrintThread extends Thread {

		@Override
		public void run() {
			while (GameStatus.PLAY.equals(controller.getStatus())) {
				//print
				clearConsole();
				for (int i = 0; i < mapSize; i++) {
					for (int j = 0; j < mapSize; j++) {
						GameCharacter field = characters.get(new Coordinate(j, i));
						if (field instanceof Hero) {
							System.out.print("H");
						} else if (field instanceof Villain) {
							System.out.print("X");
						} else {
							System.out.print("o");
						}
					}
					System.out.println();
				}
				//read commands
				try {
					Button button;
					while ((button = Button.getButton(reader.readLine())) == null) {
						System.out.println("please write w/s/a/d for movie or exit for exit.");
					}
					controller.pressButton(button);
				} catch (IOException | InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
