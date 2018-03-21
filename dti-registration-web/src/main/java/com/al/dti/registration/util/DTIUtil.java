package com.al.dti.registration.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.HashMap;

import javax.portlet.PortletRequest;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author aixtor
 * The Class DTIUtil.
 */
public class DTIUtil {

	/**
	 * Save image.
	 *
	 * @param imageData the image data
	 * @param portletRequest the portlet request
	 * @param fileName the file name
	 * @throws Exception the exception
	 */
	public static void saveImage(String imageData, PortletRequest portletRequest, String fileName) throws Exception{
		
		String imageBytesString = imageData.split(StringPool.COMMA)[1];
		byte[] decodedBytes = Base64.getDecoder().decode(imageBytesString);
		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long groupId = themeDisplay.getScopeGroupId();
		long repositoryId = groupId;
		long userId = themeDisplay.getUserId();
		ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), portletRequest);
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		File file = FileUtil.createTempFile(decodedBytes);
		InputStream inputStream = new FileInputStream(file);
		String mimeType = MimeTypesUtil.getContentType(file);
		String description = StringPool.BLANK, changeLog = StringPool.BLANK;

		// save file into Document & Media
		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(userId, groupId, repositoryId,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName, mimeType, fileName, description, changeLog, 0,
				null, file, inputStream, file.length(), serviceContext);
		
		
		DLFileEntryLocalServiceUtil.updateStatus(userId, dlFileEntry.getFileVersion().getFileVersionId(),
				WorkflowConstants.STATUS_APPROVED, serviceContext, new HashMap<String, Serializable>());
	}
}
