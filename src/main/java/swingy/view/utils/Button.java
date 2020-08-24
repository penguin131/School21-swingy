package swingy.view.utils;

public enum Button {
	UP(87),
	DOWN(83),
	LEFT(65),
	RIGHT(68),
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

	public static Button getButton(int code) {
		for (Button button : Button.values()) {
			if (code == button.getCode())
				return button;
		}
		return null;
	}

	public static boolean isStep(int code) {
		return code == Button.DOWN.getCode() ||
				code == Button.UP.getCode() ||
				code == Button.LEFT.getCode() ||
				code == Button.RIGHT.getCode();
	}
}
