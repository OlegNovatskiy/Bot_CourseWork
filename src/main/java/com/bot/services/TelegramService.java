package com.bot.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.weatherlibraryjava.RequestBlocks;

/**
 * Service that manage a telegram bot
 * 
 * @author olegnovatskiy
 *
 */
@Service
public class TelegramService extends TelegramLongPollingBot {

	private static final String BOTTOKEN = "324721267:AAFaQu9D8vmcEMHZC1JFE2vOoKhgRLESEDM";
	private static final String CURRENT_WEATHER = "Current weather";
	private static final String FORECAST_WEATHER = "Forecast weather";
	private static final String NEWS = "News";
	private static final String BOTUSERNAME = "Nolehbot";
	private static final String HOROSCOPE = "Horoscope";

	private static Logger log = Logger.getLogger(TelegramService.class);

	private static Map<Long, String> lastMessages = new HashMap<Long, String>();

	static {
		ApiContextInitializer.init();
	}

	@Autowired
	private WeatherService weatherService;

	@Autowired
	private VKService vkService;

	/**
	 * Initialization a bot of telegram
	 */
	@PostConstruct
	public void initBot() throws TelegramApiException {
		log.info("Innitialization Bot)");
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(this);
		} catch (TelegramApiException e) {
			log.error("Error in getDomain ---  " + e.getMessage());
			throw new TelegramApiException(e);
		}
	}

	/**
	 * This method will be called when an Update is received by your bot
	 * 
	 * @param update
	 *            - updates that happened in telegram
	 */
	@Override
	public void onUpdateReceived(Update update) {

		Message receiveMessage = update.getMessage();

		switch (receiveMessage.getText()) {
		case NEWS:
			sendTextMessage(receiveMessage,vkService.getItNews());//  displayNews(receiveMessage);
			break;
		case HOROSCOPE:
			//displayHoroscope(receiveMessage);
			break;
		case CURRENT_WEATHER:
		case FORECAST_WEATHER:
			sendTextMessage(receiveMessage, "Enter location for search weather:");
			break;
		default:
			processingReceiveMessage(receiveMessage);
			break;
		}

		lastMessages.put(receiveMessage.getChatId(), receiveMessage.getText());
	}

	/**
	 * Processing message that is not in list of default values
	 * 
	 * @param receiveMessage
	 */
	private void processingReceiveMessage(Message receiveMessage) {

		String textMessage = receiveMessage.getText();
		Long chatIdMessage = receiveMessage.getChatId();

		if (!lastMessages.containsKey(chatIdMessage)) {
			sendTextMessage(receiveMessage, "I don't know what you want!!!");
			return;
		}

		switch (lastMessages.get(chatIdMessage)) {
		case CURRENT_WEATHER:
			sendTextMessage(receiveMessage, weatherService.getCurrentWeather(textMessage));
			return;
		case FORECAST_WEATHER:
			sendTextMessage(receiveMessage, weatherService.getForecastWeather(textMessage, RequestBlocks.Days.One));
			return;
		default:
			sendTextMessage(receiveMessage, "I don't know what you want!!!");
			break;
		}

	}


	/**
	 * Method send a text messages to telegram
	 * 
	 * @param receivedMessage
	 * @param textNewMessage
	 * @throws TelegramApiException
	 */
	private void sendTextMessage(Message receivedMessage, String textNewMessage) {

		SendMessage messageForSend = new SendMessage();
		messageForSend.setChatId(receivedMessage.getChatId().toString());
		messageForSend.setText(textNewMessage);

		try {
			sendMessage(messageForSend);
		} catch (TelegramApiException e) {
			log.error(e.getMessage());
		}

	}

	/**
	 * Method send a list of text messages to telegram
	 * 
	 * @param receivedMessage
	 * @param textNewMessage
	 * @throws TelegramApiException
	 */
	private void sendTextMessage(Message receivedMessage, List<String> messageBatch) {

		SendMessage messageForSend = new SendMessage();
		messageForSend.setChatId(receivedMessage.getChatId().toString());

		for (String message : messageBatch) {

			messageForSend.setText(message);

			try {
				sendMessage(messageForSend);
			} catch (TelegramApiException e) {
				log.error(e.getMessage());
			}

		}

	}

	@Override
	public String getBotUsername() {
		return BOTUSERNAME;
	}

	@Override
	public String getBotToken() {
		return BOTTOKEN;
	}

}
