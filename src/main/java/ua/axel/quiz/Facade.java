package ua.axel.quiz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.axel.quiz.handler.UpdateContent;
import ua.axel.quiz.handler.UpdateContentMessage;
import ua.axel.quiz.handler.UpdateContentPoll;
import ua.axel.quiz.handler.UpdateContentPollAnswer;
import ua.axel.quiz.service.UserStateService;

import java.util.Optional;

@Component
@Slf4j
public class Facade {
	private boolean isFirstRun = true;
	@Autowired
	private UserStateService userStateService;
	@Autowired
	private ApplicationContext applicationContext;

	public Optional<BotApiMethod<Message>> handle(Update update) {
		if (isFirstRun) {
			userStateService.updateCache();
			isFirstRun = false;
		}
		return getUpdateContent(update).flatMap(UpdateContent::handle);
	}

	private Optional<UpdateContent> getUpdateContent(Update update) {
		UpdateContent updateContent = null;
		if (update.hasMessage()) {
			updateContent = applicationContext.getBean(UpdateContentMessage.class);
			updateContent.setContent(update.getMessage());
		} else if (update.hasPoll()) {
			updateContent = applicationContext.getBean(UpdateContentPoll.class);
			updateContent.setContent(update.getPoll());
		} else if (update.hasPollAnswer()) {
			updateContent = applicationContext.getBean(UpdateContentPollAnswer.class);
			updateContent.setContent(update.getPollAnswer());
		}
		return Optional.ofNullable(updateContent);
	}
}
