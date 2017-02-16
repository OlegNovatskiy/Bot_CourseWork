package com.bot.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import com.vk.api.sdk.objects.wall.WallpostAttachmentType;
import com.vk.api.sdk.objects.wall.WallpostFull;
import com.vk.api.sdk.objects.wall.responses.GetResponse;

/**
 * The service for create string response about it news
 * 
 * @author olegnovatskiy
 *
 */
@Service
public class DesignResponseNews {

	private static final String FORMAT_DATE_POST = "MM-dd HH:mm";

	/**
	 * 
	 * @param getResponse
	 * @return List<String>
	 */
	public List<String> designItNews(GetResponse getResponse) {

		List<WallpostFull> walls = getResponse.getItems();
		List<String> responseBatch = new ArrayList<>();

		for (WallpostFull wall : walls) {

			Date wallDate = new Date(wall.getDate() * 1000);
			String wallDateString = new SimpleDateFormat(FORMAT_DATE_POST).format(wallDate);
			responseBatch.add(String.format("####---- Post №%d, %s ----####", wall.getId(), wallDateString));

			if (StringUtils.isNoneBlank(wall.getText())) {
				responseBatch.add(wall.getText());
			}

			for (WallpostAttachment attachment : wall.getAttachments()) {
				WallpostAttachmentType typeAttachment = attachment.getType();

				switch (typeAttachment) {
				case VIDEO:
					responseBatch.add(attachment.getVideo().getAccessKey());
					break;
				case PHOTO:
					String photo = selectBiggestPhoto(attachment.getPhoto());
					if (!StringUtils.isBlank(selectBiggestPhoto(attachment.getPhoto()))) {
						responseBatch.add(photo);
					}
					break;
				case AUDIO:
					responseBatch.add(attachment.getAudio().getUrl());
					break;
				case DOC:
					responseBatch.add(attachment.getDoc().getUrl());
					break;
				case LINK:
					responseBatch.add(attachment.getLink().getUrl());
					break;
				case PAGE:
					responseBatch.add(attachment.getPage().getViewUrl());
					break;
				case GRAFFITI:
					responseBatch.add(attachment.getGraffiti().getPhoto200());
					break;

				default:

				}
			}

			responseBatch.add(String.format("#####------  End post №%d  ------#####", wall.getId()));
		}

		return responseBatch;
	}

	/**
	 * Method searching the biggest photo from the existing
	 * 
	 * @param photo
	 * @return String URL - link to biggest photo
	 */
	private String selectBiggestPhoto(Photo photo) {

		if (StringUtils.isNotBlank(photo.getPhoto2560())) {
			return photo.getPhoto2560();
		}

		if (StringUtils.isNotBlank(photo.getPhoto1280())) {
			return photo.getPhoto1280();
		}

		if (StringUtils.isNotBlank(photo.getPhoto807())) {
			return photo.getPhoto807();
		}

		if (StringUtils.isNotBlank(photo.getPhoto604())) {
			return photo.getPhoto604();
		}

		if (StringUtils.isNotBlank(photo.getPhoto130())) {
			return photo.getPhoto130();
		}

		if (StringUtils.isNotBlank(photo.getPhoto75())) {
			return photo.getPhoto75();
		} else {
			return "";
		}

	}

}
