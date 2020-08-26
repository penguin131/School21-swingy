package swingy.view;

import java.io.IOException;

public interface BattleView extends View {
	void printMap() throws IOException;
	boolean choice() throws IOException, InterruptedException;
}
