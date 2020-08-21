package swingy.view;

import swingy.model.Coordinate;
import swingy.model.GameCharacter;

import java.io.IOException;
import java.util.Map;

public interface BattleView extends View {
	void printMap() throws IOException;
}
