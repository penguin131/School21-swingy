package swingy.view.console;

import swingy.model.Coordinate;
import swingy.model.GameCharacter;
import swingy.view.BattleView;

import java.util.Map;

public class ConsoleBattlePage extends ConsolePage implements BattleView {

	public void printMap() {
		clearConsole();
		System.out.println("ConsoleBattlePage");
	}
}
