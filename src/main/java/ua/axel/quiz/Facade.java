package ua.axel.quiz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.PollOption;
import ua.axel.quiz.entity.UserState;
import ua.axel.quiz.service.UserStateService;
import ua.axel.quiz.state.State;
import ua.axel.quiz.state.States;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Component
@Slf4j
public class Facade {
	@Autowired
	private UserStateService userStateService;
	@Autowired
	private States states;

	public Optional<BotApiMethod<Message>> handle(Update update) {
		if (update.hasPoll()) {
			List<PollOption> options = update.getPoll().getOptions();
			int answerNumber = IntStream.range(0, options.size()).filter(i -> options.get(i).getVoterCount() == 1).findFirst().orElse(-1);
			if (update.getPoll().getCorrectOptionId() == answerNumber) {
				log.info("Right!");
			} else {
				log.info("Wrong!");

			}
			return Optional.empty();
		}
		if (update.hasPollAnswer()) {
			return Optional.of(SendMessage.builder().chatId(update.getPollAnswer().getUser().getId().toString()).text("hi").build());
		}
		var userId = update.getMessage().getFrom().getId();
		UserState userState = userStateService.findById(userId);
		if (userState == null) {
			userStateService.setUserStateName(userId, States.MAIN_STATE);
			return states.getState(States.MAIN_STATE).start(update);
		}
		var state = userState.getState();
		var currUserState = getState(state);
		return currUserState.handle(this, update);
	}

	public State getState(String state) {
		return states.getState(state);
	}
}
