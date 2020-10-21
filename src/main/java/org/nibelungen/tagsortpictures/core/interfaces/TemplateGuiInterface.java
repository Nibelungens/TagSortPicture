package org.nibelungen.tagsortpictures.core.interfaces;

import org.nibelungen.tagsortpictures.job.MessageRowTSP;

public interface TemplateGuiInterface {
	void setProgressBarTotal(Integer hundredPercent);
	void addIncrementProgressBar();
	void addMessage(MessageRowTSP msg);
}
