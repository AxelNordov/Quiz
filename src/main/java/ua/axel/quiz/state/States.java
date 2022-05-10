package ua.axel.quiz.state;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class States {
	public static final String MAIN_STATE = "main";
	public static final String SETTINGS_STATE = "settings";
	public static final String GAME_STATE = "game";
	public static final String MIRROR_STATE = "mirror";
	private final Map<String, State> stateMap = new HashMap<>();

	public States(MainState mainState,
	              MirrorState mirrorState,
	              GameState gameState,
	              SettingsState settingsState) {
		stateMap.put(MAIN_STATE, mainState);
		stateMap.put(MIRROR_STATE, mirrorState);
		stateMap.put(GAME_STATE, gameState);
		stateMap.put(SETTINGS_STATE, settingsState);
	}

	public State getState(String state) {
		return stateMap.get(state);
	}
}
