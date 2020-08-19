package swingy.view.console;

import swingy.model.Coordinate;
import swingy.model.GameCharacter;
import swingy.view.BattleView;

import java.util.Map;

public class BattleConsolePage extends ConsolePage implements BattleView {

	public void printMap(Map<Coordinate, GameCharacter> characters, int mapSize) {
		clearConsole();
	}


}
