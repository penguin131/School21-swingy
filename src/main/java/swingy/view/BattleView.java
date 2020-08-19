package swingy.view;

import swingy.model.Coordinate;
import swingy.model.GameCharacter;

import java.util.Map;

public interface BattleView extends View {
	void printMap(Map<Coordinate, GameCharacter> characters, int mapSize);
}
