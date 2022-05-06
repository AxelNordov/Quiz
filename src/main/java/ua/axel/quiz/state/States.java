package ua.axel.quiz.state;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class States {

	private final Map<String, State> stateMap = new HashMap<>();

	public States(MainState mainState, MirrorState mirrorState) {
		stateMap.put("mainState", mainState);
		stateMap.put("mirrorState", mirrorState);
	}

	public State getState(String state) {
		return stateMap.get(state);
	}
}
