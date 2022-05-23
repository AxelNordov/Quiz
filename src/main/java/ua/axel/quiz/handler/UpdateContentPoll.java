package ua.axel.quiz.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.polls.PollOption;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@Component
public class UpdateContentPoll implements UpdateContent {
	private Poll poll;

	@Override
	public void setContent(BotApiObject botApiObject) {
		this.poll = (Poll) botApiObject;
	}

	@Override
	public List<BotApiMethod<Message>> handle() {
		List<PollOption> options = poll.getOptions();
		int answerNumber = IntStream.range(0, options.size()).filter(i -> options.get(i).getVoterCount() == 1).findFirst().orElse(-1);
		if (poll.getCorrectOptionId() == answerNumber) {
			log.info("Right!");
		} else {
			log.info("Wrong!");
		}
		return List.of();
	}
}
