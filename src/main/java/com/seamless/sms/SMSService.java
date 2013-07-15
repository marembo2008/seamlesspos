package com.seamless.sms;

import java.io.IOException;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class SMSService {

	private FacesContext context;
	private String /* baseURL */url;

	public SMSService() {
		context = FacesContext.getCurrentInstance();
		// baseURL = context.getExternalContext().getRequestContextPath();
		Map<String, String> param = context.getExternalContext().getRequestParameterMap();
		url = "http://localhost:8080/Gateway/SendSms?sender=" + param.get("sender") + "&;text=" + param.get("text");
	}

	public void sendSMSRequest() {
		try {
			context.getExternalContext().redirect(
					context.getExternalContext().encodeResourceURL(url));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			context.responseComplete();
		}
	}
}