package ua.axel.quiz;

public class Constants {
	public static final String START_COMMAND = "/start";
	public static final String MAIN_STATE = "main";
	public static final String SETTINGS_STATE = "settings";
	public static final String GAME_STATE = "game";
	public static final String MIRROR_STATE = "mirror";

	private Constants() {
		throw new IllegalStateException("Utility class");
	}
}
