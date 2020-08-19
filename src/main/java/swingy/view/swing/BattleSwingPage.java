package swingy.view.swing;

import swingy.model.Coordinate;
import swingy.model.GameCharacter;
import swingy.view.BattleView;

import java.util.Map;

public class BattleSwingPage extends SwingPage implements BattleView {
	private void clearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public void printMap(Map<Coordinate, GameCharacter> characters, int mapSize) {
		clearConsole();
	}


}
