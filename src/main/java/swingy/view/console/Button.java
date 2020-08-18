package swingy.view.console;

public enum Button {
	UP(119),
	DOWN(115),
	LEFT(97),
	RIGHT(100),
	ESC(27),
	YES(1),
	NO(1);

	private int code;

	Button(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static boolean containCode(int code) {
		for (Button button : Button.values()) {
			if (code == button.getCode())
				return true;
		}
		return false;
	}

	public static boolean isStep(int code) {
		return code == Button.DOWN.getCode() ||
				code == Button.UP.getCode() ||
				code == Button.LEFT.getCode() ||
				code == Button.RIGHT.getCode();
	}
}
