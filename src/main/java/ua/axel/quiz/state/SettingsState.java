package ua.axel.quiz.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.axel.quiz.service.AuthorService;
import ua.axel.quiz.service.CategoryService;
import ua.axel.quiz.service.UserService;
import ua.axel.quiz.util.SendMessageUtil;

import java.util.ArrayList;
import java.util.List;

@Component
public class SettingsState extends State {
	@Autowired
	private UserService userService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private CategoryService categoryService;

	@Override
	public List<BotApiMethod<Message>> start(Message message) {
		var chatId = message.getChatId().toString();
		var sendMessage = SendMessageUtil
				.getSendMessageWithMainMenuKeyboard(chatId,
						localeMessageService.getMessage(
								"message.you-are-in", localeMessageService.getMessage("menu.settings-button.name")),
						List.of(localeMessageService.getMessage("menu.category-button.name"),
								localeMessageService.getMessage("menu.main-button.name")));
		return List.of(sendMessage);
	}

	@Override
	public List<BotApiMethod<Message>> handle(Message message) {
		var botApiMethods = new ArrayList<BotApiMethod<Message>>();
		var userId = message.getFrom().getId();
		var chatId = message.getChatId().toString();
		var text = message.getText();
		if (text.equals(localeMessageService.getMessage("menu.main-button.name"))) {
			botApiMethods.addAll(changeState(message, States.Name.MAIN_STATE));
		} else if (text.equals(localeMessageService.getMessage("menu.category-button.name"))) {
			botApiMethods.add(SendMessageUtil.getSendMessageWithMainMenuKeyboard(chatId,
					localeMessageService.getMessage("message.category.choose"),
					categoryService.getAllTitles()));
		} else if (categoryService.getAllTitles().contains(text)) {
			userService.setCategory(userId, categoryService.findByTitle(text));
			botApiMethods.add(SendMessageUtil.getSendMessageWithMainMenuKeyboard(chatId,
					text + ": choose subcategory",
					authorService.getAllHasQuizzesWithRightAnswerTitlesByCategory(categoryService.findByTitle(text))));
		} else if (authorService.getAllHasQuizzesWithRightAnswerTitlesByCategory(
				userService.findById(userId).orElseThrow().getCategory()).contains(text)) {
			userService.toggleAuthor(userId, authorService.findByTitle(text));
			botApiMethods.addAll(changeState(message, States.Name.GAME_STATE));
		}
		return botApiMethods;
	}
}
