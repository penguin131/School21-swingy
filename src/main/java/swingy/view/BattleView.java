package swingy.view;

import swingy.model.Artifact;

import java.io.IOException;

public interface BattleView extends View {
	void printMap() throws IOException;
	boolean fightChoice() throws IOException, InterruptedException;
	boolean artifactChoice(Artifact artifact) throws IOException, InterruptedException;
}
