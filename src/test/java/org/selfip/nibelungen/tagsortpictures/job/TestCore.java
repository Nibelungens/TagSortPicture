package org.selfip.nibelungen.tagsortpictures.job;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

import org.junit.Test;
import org.selfip.nibelungen.tagsortpictures.core.TagSortPicturesCore;
import org.selfip.nibelungen.tagsortpictures.core.interfaces.TemplateGuiInterface;

/**
 * 
 * @author Mickaël
 *
 */
public class TestCore {
	
	/**
	 * 
	 */
	public static final String FIRST_TAG = "firsttag";
	
	/**
	 * 
	 */
	public static final String SECOND_TAG = "secondtag";

	/**
	 * 
	 */
	public static final String THIRD_TAG = "thirdtag";
	
	/**
	 * 
	 */
	public static final String SOURCE_FOLDER_OK_SERACHTAG = "src/test/resources/TestCore/source_2";
	
	/**
	 * 
	 */
	public static final String SOURCE_FOLDER_OK = "src/test/resources/TestCore/source";
	
	/**
	 * 
	 */
	public static final String DEST_FOLDER_OK = "src/test/resources/TestCore/dest";

	/**
	 * 
	 */
	@Test
	public void test_core_ok() {
		Boolean retour = false;
		
		TemplateGuiInterface templateGui = new TemplateGuiInterface() {
			
			@Override
			public void setProgressBarTotal(Integer hundredPercent) {}
			
			@Override
			public void addMessage(MessageRowTSP msg) {
//				System.out.print(msg.getErrorType().toString() + "\t- " + msg.getErrorMessage() + "\n");
				
			}

			@Override
			public void addIncrementProgressBar() {}
		};
		
		List<String> excludeList = new ArrayList<String>();
		excludeList.add("thirdtag");
		File dt_ft = new File(DEST_FOLDER_OK+"/firsttag");
		File dt_sd = new File(DEST_FOLDER_OK+"/secondtag");		
		File dt_th = new File(DEST_FOLDER_OK+"/thirdtag");
		File dt_sd_jpg = new File(DEST_FOLDER_OK+"/secondtag/jpegTest.jpg");
		File dt_ft_jpg = new File(DEST_FOLDER_OK+"/firsttag/jpegTest.jpg");
		
		assertFalse(dt_ft.exists() || dt_sd.exists() || dt_th.exists() || dt_sd_jpg.exists() || dt_ft_jpg.exists());
		
		TagSortPicturesCore tagSortPictures = new TagSortPicturesCore(SOURCE_FOLDER_OK, DEST_FOLDER_OK, excludeList, templateGui);
		tagSortPictures.run();
		
		retour = dt_ft.exists() || dt_sd.exists() || !dt_th.exists() || dt_sd_jpg.exists() || dt_ft_jpg.exists();
		
		dt_sd_jpg.delete();
		dt_ft_jpg.delete();
		dt_ft.delete();
		dt_sd.delete();
		dt_th.delete();			
		
		assertTrue(retour);
	}
	
	/**
	 * 
	 */
	@Test
	public void test_existTagInFolder() {

		TemplateGuiInterface templateGui = new TemplateGuiInterface() {
			
			@Override
			public void setProgressBarTotal(Integer hundredPercent) {}
			
			@Override
			public void addMessage(MessageRowTSP msg) {
//				System.out.print(msg.getErrorType().toString() + "\t- " + msg.getErrorMessage() + "\n");
				
			}

			@Override
			public void addIncrementProgressBar() {}
		};
			
		TagSortPicturesCore tagSortPictures = new TagSortPicturesCore(SOURCE_FOLDER_OK_SERACHTAG, null, null, templateGui);
		Map<String, Integer> mr = tagSortPictures.getExistTagsInSourceFolder();
	
		
		assertTrue(mr.get(FIRST_TAG)==2 && mr.get(SECOND_TAG)==1 && mr.get(THIRD_TAG)==1);
	}
	
	/**
	 * 
	 */
	@Test
	public void test_nbFiles() {

		TemplateGuiInterface templateGui = new TemplateGuiInterface() {
			
			@Override
			public void setProgressBarTotal(Integer hundredPercent) {}
			
			@Override
			public void addMessage(MessageRowTSP msg) {
				System.out.print(msg.getErrorType().toString() + "\t- " + msg.getErrorMessage() + "\n");
				
			}

			@Override
			public void addIncrementProgressBar() {}
		};
			
		TagSortPicturesCore tagSortPictures = new TagSortPicturesCore(SOURCE_FOLDER_OK_SERACHTAG, null, null, templateGui);
		Integer mr = tagSortPictures.getNbFiles();
	
		assertEquals(mr, new Integer(2));
	}
}
