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
		var mainButtonText = localeMessageService.getMessage("menu.main-button.name");
		var categoryButtonText = localeMessageService.getMessage("menu.category-button.name");
		var chatId = message.getChatId().toString();
		var userId = message.getFrom().getId();
		var sendMessage = SendMessageUtil
				.getSendMessageWithMainMenuKeyboard(chatId,
						userService.getCurrentSettingsInfo(userId),
						List.of(categoryButtonText, mainButtonText));
		return List.of(sendMessage);
	}

	@Override
	public List<BotApiMethod<Message>> handle(Message message) {
		var mainButtonText = localeMessageService.getMessage("menu.main-button.name");
		var categoryButtonText = localeMessageService.getMessage("menu.category-button.name");
		var allCategoriesButtonText = localeMessageService.getMessage("menu.all-categories-button.name");
		var allAuthorsButtonText = localeMessageService.getMessage("menu.all-authors-button.name");
		var botApiMethods = new ArrayList<BotApiMethod<Message>>();
		var userId = message.getFrom().getId();
		var chatId = message.getChatId().toString();
		var text = message.getText();
		if (text.equals(mainButtonText)) {
			botApiMethods.addAll(changeState(message, States.Name.MAIN_STATE));
		} else if (text.equals(categoryButtonText)) {
			var buttonsTexts = new ArrayList<String>();
			buttonsTexts.add(mainButtonText);
			buttonsTexts.add(allCategoriesButtonText);
			buttonsTexts.addAll(categoryService.getAllTitles());
			botApiMethods.add(SendMessageUtil.getSendMessageWithMainMenuKeyboard(chatId,
					localeMessageService.getMessage("message.category.choose"),
					buttonsTexts));
		} else if (text.equals(allCategoriesButtonText)) {
			userService.setCategory(userId, null);
			userService.setAuthors(userId, null);
			botApiMethods.add(SendMessageUtil.getSendMessage(chatId, userService.getCurrentSettingsInfo(userId)));
		} else if (categoryService.getAllTitles().contains(text)) {
			userService.setCategory(userId, categoryService.findByTitle(text));
			var buttonsTexts = new ArrayList<String>();
			buttonsTexts.add(mainButtonText);
			buttonsTexts.add(allAuthorsButtonText);
			buttonsTexts.addAll(authorService.getAllHasQuizzesWithRightAnswerTitlesByCategory(categoryService.findByTitle(text)));
			botApiMethods.add(SendMessageUtil.getSendMessageWithMainMenuKeyboard(chatId,
					userService.getCurrentSettingsInfo(userId),
					buttonsTexts));
		} else if (text.equals(allAuthorsButtonText)) {
			userService.setAuthors(userId, null);
			botApiMethods.add(SendMessageUtil.getSendMessage(chatId, userService.getCurrentSettingsInfo(userId)));
		} else if (authorService.getAllHasQuizzesWithRightAnswerTitlesByCategory(
				userService.findById(userId).orElseThrow().getCategory()).contains(text)) {
			userService.toggleAuthor(userId, authorService.findByTitle(text));
			botApiMethods.add(SendMessageUtil.getSendMessage(chatId, userService.getCurrentSettingsInfo(userId)));
		}
		return botApiMethods;
	}
}
