package com.bot.services;

import org.assertj.core.api.exception.RuntimeIOException;
import org.springframework.stereotype.Service;

import com.weatherlibrary.datamodel.WeatherModel;
import com.weatherlibraryjava.Repository;
import com.weatherlibraryjava.RequestBlocks;

/**
 * The service for work with APIXU
 * 
 * @author olegnovatskiy
 */

@Service
public class ApixuService {

	private static final String ACCESS_KEY = "7cf1bcd8d03543c9a3a75349170302";
	private static final RequestBlocks.GetBy METHOD_LOCATION_NAME = RequestBlocks.GetBy.CityName;

	/**
	 * Method search a current weather for a location
	 * 
	 * @param locationWeather
	 * @return WeatherModel
	 * @throws Exception 
	 * @throws RuntimeIOException
	 */
	public WeatherModel getCurrentWeather(String locationWeather) throws Exception {

		Repository repository = new Repository();
		
		return repository.GetWeatherData(ACCESS_KEY, METHOD_LOCATION_NAME, locationWeather);
	}

	/**
	 * Method search a forecast weather for a location
	 * 
	 * @param locationWeather
	 * @param countShowDay
	 * @return the weather for location in few day
	 * @throws Exception
	 */
	public WeatherModel getForecastWeather(String locationWeather, RequestBlocks.Days countShowDay) throws Exception {

		Repository repository = new Repository();
		
		return repository.GetWeatherData(ACCESS_KEY, METHOD_LOCATION_NAME, locationWeather, RequestBlocks.Days.Five);
	}

}
