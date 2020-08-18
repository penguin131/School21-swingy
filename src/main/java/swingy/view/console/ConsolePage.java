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

	@Override
	public void init() {
		try {
			String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
			Runtime.getRuntime().exec(cmd).waitFor();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void destroy() {
		try {
			String[] cmd = new String[] {"/bin/sh", "-c", "stty sane </dev/tty"};
			Runtime.getRuntime().exec(cmd).waitFor();
			System.exit(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	public void printMap(Map<Coordinate, GameCharacter> characters, int mapSize) {
		clearConsole();
	}


}
