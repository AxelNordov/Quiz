package ua.axel.quiz.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.axel.quiz.service.*;
import ua.axel.quiz.util.SendMessageUtil;

import java.util.List;
import java.util.Optional;

@Component
public class SettingsState implements State {
	@Autowired
	private UserStateService userStateService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private LocaleMessageService localeMessageService;

	@Override
	public Optional<BotApiMethod<Message>> start(String chatId) {
		var sendMessage = SendMessageUtil.getSendMessageWithMainMenuKeyboard(chatId,
				localeMessageService.getMessage(
						"message.you-are-in", localeMessageService.getMessage("menu.settings-button.name")),
				List.of(localeMessageService.getMessage("menu.main-button.name"),
						localeMessageService.getMessage("menu.category-button.name")));
		return Optional.of(sendMessage);
	}

	@Override
	public Optional<BotApiMethod<Message>> handle(Message message) {
		var userId = message.getFrom().getId();
		var text = message.getText();
		var chatId = message.getChatId().toString();
		if (text.equals(localeMessageService.getMessage("menu.main-button.name"))) {
			var userState = userStateService.setUserState(userId, StateName.MAIN_STATE);
			return States.getState(userState.getState()).start(chatId);
		} else if (text.equals(localeMessageService.getMessage("menu.category-button.name"))) {
			var sendMessage = SendMessageUtil.getSendMessageWithMainMenuKeyboard(chatId,
					localeMessageService.getMessage("message.choose-the-category"),
					categoryService.getAllTitles());
			return Optional.of(sendMessage);
		} else if (categoryService.getAllTitles().contains(text)) {
			userService.setCategory(userId, categoryService.findByTitle(text));
			var sendMessage = SendMessageUtil.getSendMessageWithMainMenuKeyboard(chatId,
					text + ": choose subcategory",
					authorService.getAllHasQuizzesWithRightAnswerTitlesByCategory(categoryService.findByTitle(text)));
			return Optional.of(sendMessage);
		} else if (authorService.getAllHasQuizzesWithRightAnswerTitlesByCategory(
				userService.findById(userId).getCategory()).contains(text)) {
			userService.setAuthor(userId, authorService.findByTitle(text));
			return start(chatId);
		}
		return Optional.empty();
	}
}
