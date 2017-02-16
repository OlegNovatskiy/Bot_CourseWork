package com.bot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for work with VK API
 * 
 * @author olegnovatskiy
 *
 */
@Service
public class VKService {

	@Autowired
	private NewsService newsService;
	
	@Autowired
	private DesignResponseNews designResponseNews;

	/**
	 * Method return it news
	 * 
	 * @return
	 */
	public List<String> getItNews() {
		return designResponseNews.designItNews(newsService.searchItNews());
	}

}
