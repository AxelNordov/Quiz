package ua.axel.quiz.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class States {
	private static final Map<StateName, State> stateMap = new EnumMap<>(StateName.class);

	@Autowired
	States(MainState mainState,
	       GameState gameState,
	       SettingsState settingsState) {
		stateMap.put(StateName.MAIN_STATE, mainState);
		stateMap.put(StateName.GAME_STATE, gameState);
		stateMap.put(StateName.SETTINGS_STATE, settingsState);
	}

	public static State getState(StateName stateName) {
		return stateMap.get(stateName);
	}

}
