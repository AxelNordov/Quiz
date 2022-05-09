package ua.axel.quiz.state;

import org.springframework.stereotype.Component;
import ua.axel.quiz.Constants;

import java.util.HashMap;
import java.util.Map;

@Component
public class States {

	private final Map<String, State> stateMap = new HashMap<>();

	public States(MainState mainState, MirrorState mirrorState, GameState gameState, SettingsState settingsState) {
		stateMap.put(Constants.MAIN_STATE, mainState);
		stateMap.put(Constants.MIRROR_STATE, mirrorState);
		stateMap.put(Constants.GAME_STATE, gameState);
		stateMap.put(Constants.SETTINGS_STATE, settingsState);
	}

	public State getState(String state) {
		return stateMap.get(state);
	}
}
