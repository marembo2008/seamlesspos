package com.seamless.sms;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class TabRenderer {

	private boolean compose;
	private boolean inbox;
	private boolean sent;
	private boolean draft;
	private boolean outbox;

	public boolean isCompose() {
		return compose;
	}

	public void setCompose(boolean compose) {
		this.compose = compose;
	}

	public boolean isInbox() {
		return inbox;
	}

	public void setInbox(boolean inbox) {
		this.inbox = inbox;
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

	public boolean isDraft() {
		return draft;
	}

	public void setDraft(boolean draft) {
		this.draft = draft;
	}

	public boolean isOutbox() {
		return outbox;
	}

	public void setOutbox(boolean outbox) {
		this.outbox = outbox;
	}
}
