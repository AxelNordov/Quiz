package ua.axel.quiz.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;

import java.util.List;

@Component
public class UpdateContentPollAnswer implements UpdateContent {
	private PollAnswer pollAnswer;

	@Override
	public void setContent(BotApiObject botApiObject) {
		this.pollAnswer = (PollAnswer) botApiObject;
	}

	@Override
	public List<BotApiMethod<Message>> handle() {
		return List.of(
				SendMessage.builder()
						.chatId(pollAnswer.getUser().getId().toString())
						.text("hi")
						.build());
	}
}
