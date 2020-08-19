package swingy.view.console;

import swingy.view.View;

public class ConsolePage implements View {

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

	protected void clearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}


}
