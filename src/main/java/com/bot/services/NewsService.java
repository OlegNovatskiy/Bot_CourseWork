package com.bot.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import com.vk.api.sdk.queries.wall.WallGetFilter;

/**
 * Service searching news through VK API
 * 
 * @author olegnovatskiy
 *
 */
@Service
public class NewsService {

	private static Logger log = Logger.getLogger(NewsService.class);

	private static final Integer COUNT_POST= 10;
	private static final Integer APP_ID = 62316054;
	private static final Integer NEWS_IT_GROUP_ID = -101965347;
	private static final String ACCESS_TOKEN = "811b59e0ab028508f219a5e21ec6faaa38165df8647aae17c76ba4fe49bc300aa191c63a024e8f3008a5c";

	/**
	 * Method search IT news from vk group 'ITc'
	 * 
	 * @return
	 */
	public GetResponse searchItNews() throws RuntimeException{

		VkApiClient vkApiClient = new VkApiClient(new HttpTransportClient());
		UserActor actor = new UserActor(APP_ID, ACCESS_TOKEN);
		GetResponse getResponse = new GetResponse();

		try {
			getResponse = vkApiClient.wall().get(actor).ownerId(NEWS_IT_GROUP_ID).count(COUNT_POST)
					.filter(WallGetFilter.ALL).execute();
		} catch (ApiException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} catch (ClientException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return getResponse;
	}

}
