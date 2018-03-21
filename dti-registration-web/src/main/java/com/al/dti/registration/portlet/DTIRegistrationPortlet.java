package com.al.dti.registration.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

import com.al.dti.registration.constants.DTIRegistrationPortletKeys;
import com.al.dti.registration.util.DTIUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author aixtor
 * The Class DTIRegistrationPortlet.
 */
@Component(immediate = true, property = { "com.liferay.portlet.display-category=Registration",
		"com.liferay.portlet.instanceable=true", "javax.portlet.display-name=DTI Registration",
		"javax.portlet.init-param.template-path=/", "javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + DTIRegistrationPortletKeys.DTIRegistration,
		"javax.portlet.resource-bundle=content.Language", "javax.portlet.security-role-ref=power-user,user",
		"com.liferay.portlet.header-portlet-javascript=/js/webcam.js" }, service = Portlet.class)
public class DTIRegistrationPortlet extends MVCPortlet {

	private static final Log log = LogFactoryUtil.getLog(DTIRegistrationPortlet.class);

	/**
	 * Register DTI User.
	 *
	 * @param actionRequest the action request
	 * @param actionResponse the action response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws PortletException the portlet exception
	 */
	public void registerDTIUser(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {

		// get user information
		String firstName = ParamUtil.getString(actionRequest, "firstName", StringPool.BLANK);
		String middleName = ParamUtil.getString(actionRequest, "middleName", StringPool.BLANK);
		String lastName = ParamUtil.getString(actionRequest, "lastName", StringPool.BLANK);
		String email = ParamUtil.getString(actionRequest, "email", StringPool.BLANK);
		String mobileNo = ParamUtil.getString(actionRequest, "mobileNo", StringPool.BLANK);
		String imageData = ParamUtil.getString(actionRequest, "imageData", StringPool.BLANK);
		String fileName = firstName + StringPool.UNDERLINE + lastName + StringPool.UNDERLINE + "_photo" + ".jpg";
		try {
			
			//TODO
			log.info("::::: >>>>> firstName :::::" + firstName);
			log.info("::::: >>>>> middleName :::::" + middleName);
			log.info("::::: >>>>> lastName :::::" + lastName);
			log.info("::::: >>>>> email :::::" + email);
			log.info("::::: >>>>> mobileNo :::::" + mobileNo);
			log.info("::::: >>>>> imageData :::::" + imageData);
			log.info("::::: >>>>> fileName :::::" + fileName);
			
			if(Validator.isNotNull(imageData)){
				//save Image into Document & library
				DTIUtil.saveImage(imageData, actionRequest, fileName);
			}
			
		} catch (Exception e) {
			log.error("::::: Error while saving web cam image into Document & liberary :::::" + e);
		}
	}

};