package swingy.view.console;

import swingy.view.View;

public class ConsolePage implements View {

	@Override
	public void destroy() {
		clearConsole();
	}

	public void clearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
