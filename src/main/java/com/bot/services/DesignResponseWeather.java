package com.bot.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.weatherlibrary.datamodel.Forecastday;
import com.weatherlibrary.datamodel.Hour;
import com.weatherlibrary.datamodel.WeatherModel;

/**
 * The service for create string response about weather
 * 
 * @author olegnovatskiy
 */
@Service
public class DesignResponseWeather {

	/**
	 * @param weather
	 *            - WeatherModel
	 * @return formatted current string of response
	 */
	public List<String> designCurrentWeather(WeatherModel weather) {
		
		ArrayList<String> responseBatch = new ArrayList<>();
		StringBuilder weatehrString = new StringBuilder();
		weatehrString.append("--------------  Location  --------------\n");
		weatehrString.append(String.format("|     Country: %s %n", weather.getLocation().getCountry()));
		weatehrString.append(String.format("|       Region: %s %n", weather.getLocation().getRegion()));
		weatehrString.append(String.format("|             City: %s %n", weather.getLocation().getName()));
		weatehrString.append(String.format("| Lacal time: %s %n", weather.getLocation().getLocaltime()));
		weatehrString.append("--------------   Weather   ---------------\n");
		weatehrString.append(String.format("|  Temperature: %s C %n", weather.getCurrent().getTempC()));
		weatehrString.append(String.format("| 	         Fills like: %s C %n", weather.getCurrent().getFeelslikeC()));
		weatehrString.append(String.format("|              Precip: %s mm %n", weather.getCurrent().getPrecipMm()));
		weatehrString.append(String.format("|         Pressure: %s mb %n", weather.getCurrent().getPressureMb()));
		weatehrString.append(String.format("|               Wind: %s kph %n", weather.getCurrent().getWindKph()));
		weatehrString.append(String.format("|        Humidity: %s %%  %n", weather.getCurrent().getHumidity()));
		weatehrString.append("--------------------------------------------");
		
		responseBatch.add(weatehrString.toString());
		
		return responseBatch;
	}

	/**
	 * @param weather
	 *            - WeatherModel
	 * @return formatted forecast string of response
	 */
	public List<String> designForecastWeather(WeatherModel weather) {
		
		ArrayList<String> responseBatch = new ArrayList<>();
		StringBuilder weatehrString = new StringBuilder();
		
		weatehrString.append("--------------  Location  --------------\n");
		weatehrString.append(String.format("|    Country: %s %n", weather.getLocation().getCountry()));
		weatehrString.append(String.format("|     Region: %s %n", weather.getLocation().getRegion()));
		weatehrString.append(String.format("|       City: %s %n", weather.getLocation().getName()));
		weatehrString.append(String.format("| Lacal time: %s %n", weather.getLocation().getLocaltime()));
		
		responseBatch.add(weatehrString.toString());
		weatehrString = new StringBuilder();
		
		weatehrString.append("-------------   Weather   --------------\n");
		ArrayList<Forecastday> arrayForecastDay = weather.getForecast().getForecastday();
		Forecastday forecastDay = arrayForecastDay.get(0);
		weatehrString.append(String.format("|----------   %s   -----------%n", forecastDay.getDate()));
		weatehrString.append(String.format("|  Max Temp: %s C %n", forecastDay.getDay().getMaxtempC()));
		weatehrString.append(String.format("|  Min Temp: %s C %n", forecastDay.getDay().getMintempC()));
		weatehrString.append(String.format("|  Max Wind: %s kph %n", forecastDay.getDay().getMaxwindKph()));
		weatehrString.append(String.format("|    Precip: %s mm %n", forecastDay.getDay().getMaxwindKph()));
		weatehrString.append("|------------   Hours weather   -----------\n");
		
		responseBatch.add(weatehrString.toString());
		
		for (Hour currentHour : forecastDay.getHour()) {
			
			weatehrString = new StringBuilder();
			weatehrString.append(String.format("|----------   %s   -----------%n", currentHour.getTime()));
			weatehrString.append(String.format("| |   Temperature: %s C %n", currentHour.getTempC()));
			weatehrString.append(String.format("| |    Fills like: %s C %n", currentHour.getFeelslikeC()));
			weatehrString.append(String.format("| |        Precip: %s mm %n", currentHour.getPrecipMm()));
			weatehrString.append(String.format("| |      Pressure: %s mb %n", currentHour.getPressureMb()));
			weatehrString.append(String.format("| |          Wind: %s kph %n", currentHour.getWindKph()));
			weatehrString.append(String.format("| |      Humidity: %s %%  %n", currentHour.getHumidity()));
			responseBatch.add(weatehrString.toString());
			
		}
		return responseBatch;
	}

}
