/**
 * 
 */
package org.selfip.nibelungen.tagsortpictures.core.interfaces;

import org.selfip.nibelungen.tagsortpictures.job.MessageRowTSP;

/**
 * @author Mickaël
 *
 */
public interface TemplateGuiInterface {

	public void setProgressBarTotal(Integer hundredPercent);
	
	public void addIncrementProgressBar();
	
	public void addMessage(MessageRowTSP msg);
}
