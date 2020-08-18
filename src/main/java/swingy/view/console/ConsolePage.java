package swingy.view.console;

import swingy.model.Coordinate;
import swingy.model.GameCharacter;
import swingy.view.View;

import java.util.Map;

public class ConsolePage implements View {
	private void clearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public void printMap(Map<Coordinate, GameCharacter> characters, int mapSize) {

	}
}
