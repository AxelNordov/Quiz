package ua.axel.quiz.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class States {
	private static final Map<Name, State> stateMap = new EnumMap<>(Name.class);

	@Autowired
	States(MainState mainState,
	       GameState gameState,
	       SettingsState settingsState) {
		stateMap.put(Name.MAIN_STATE, mainState);
		stateMap.put(Name.GAME_STATE, gameState);
		stateMap.put(Name.SETTINGS_STATE, settingsState);
	}

	public static State getState(Name name) {
		return stateMap.get(name);
	}

	public enum Name {
		MAIN_STATE,
		SETTINGS_STATE,
		GAME_STATE
	}
}
