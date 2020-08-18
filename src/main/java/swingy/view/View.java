package swingy.view;

import swingy.model.Coordinate;
import swingy.model.GameCharacter;

import java.util.Map;

public interface View {
	void init();
	void destroy();
	void printMap(Map<Coordinate, GameCharacter> characters, int mapSize);
}
