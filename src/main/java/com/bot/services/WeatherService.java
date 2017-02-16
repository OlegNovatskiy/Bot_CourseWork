package com.bot.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weatherlibrary.datamodel.WeatherModel;
import com.weatherlibraryjava.RequestBlocks;

/**
 * Service for reading weather and returning responses about weather
 * 
 * @author olegnovatskiy
 */
@Service
public class WeatherService {

	private static final Logger log = Logger.getLogger(WeatherService.class);

	@Autowired
	private ApixuService apixuService;

	@Autowired
	private DesignResponseWeather formatingStringWeather;

	/**
	 * Method return current weather
	 * 
	 * @param locationForSearch
	 * @return String response about current weather
	 */
	public List<String> getCurrentWeather(String locationForSearch) throws RuntimeException {

		try {

			WeatherModel weather = apixuService.getCurrentWeather(locationForSearch);

			return formatingStringWeather.designCurrentWeather(weather);

		} catch (Exception e) {

			log.error("Can not find weather for it location");
			ArrayList<String> messageToUser = new ArrayList<>();
			messageToUser.add("Cannot find weather for it location");

			return messageToUser;
		}
	}

	/**
	 * Method return forecast weather
	 * 
	 * @param locationForSearch
	 * @param countShowDay
	 * @return String response about Forecast weather
	 */
	public List<String> getForecastWeather(String locationForSearch, RequestBlocks.Days countShowDay) {

		try {
			WeatherModel weather = apixuService.getForecastWeather(locationForSearch, countShowDay);

			return formatingStringWeather.designForecastWeather(weather);

		} catch (Exception e) {

			log.error("Cannot find weather for it location");
			ArrayList<String> messageToUser = new ArrayList<>();
			messageToUser.add("Cannot find weather for it location");

			return messageToUser;
		}
	}

}
