package ua.axel.quiz.state;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.axel.quiz.Facade;

import java.io.Serializable;
import java.util.Optional;

public class MainState extends State {

	public MainState(Facade facade) {
		super(facade);
	}

	@Override
	public Optional<BotApiMethod<? extends Serializable>> handle(Update update) {
		if (update.hasMessage() && "mirror".equals(update.getMessage().getText())) {
			long userId = update.getMessage().getFrom().getId();
			facade.setUserState(userId, new MirrorState(facade));
			return Optional.of(SendMessage.builder()
					.chatId(update.getMessage().getChatId().toString())
					.text("in Mirror State")
					.build());
		}
		return Optional.empty();
	}
}
