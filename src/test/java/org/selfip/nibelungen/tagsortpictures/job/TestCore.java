package org.selfip.nibelungen.tagsortpictures.job;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

import org.junit.Test;
import org.selfip.nibelungen.tagsortpictures.core.TagSortPicturesCore;
import org.selfip.nibelungen.tagsortpictures.core.interfaces.TemplateGuiInterface;

public class TestCore {
	public static final String FIRST_TAG = "firsttag";
	public static final String SECOND_TAG = "secondtag";
	public static final String THIRD_TAG = "thirdtag";
	public static final String SOURCE_FOLDER_OK_SERACHTAG = "src/test/resources/TestCore/source_2";
	public static final String SOURCE_FOLDER_OK = "src/test/resources/TestCore/source";
	public static final String DEST_FOLDER_OK = "src/test/resources/TestCore/dest";

	@Test
	public void test_core_ok() {
		List<String> excludeList = new ArrayList<>();
		excludeList.add(THIRD_TAG);
		File dt_ft = new File(DEST_FOLDER_OK.concat("/").concat(FIRST_TAG));
		File dt_sd = new File(DEST_FOLDER_OK.concat("/").concat(SECOND_TAG));
		File dt_th = new File(DEST_FOLDER_OK.concat("/").concat(THIRD_TAG));
		File dt_sd_jpg = new File(DEST_FOLDER_OK.concat("/").concat(SECOND_TAG).concat("/jpegTest.jpg"));
		File dt_ft_jpg = new File(DEST_FOLDER_OK.concat("/").concat(FIRST_TAG).concat("/jpegTest.jpg"));

		assertFalse(dt_ft.exists());
		assertFalse(dt_sd.exists());
		assertFalse(dt_th.exists() );
		assertFalse(dt_sd_jpg.exists());
		assertFalse(dt_ft_jpg.exists());
		
		TagSortPicturesCore tagSortPictures = new TagSortPicturesCore(SOURCE_FOLDER_OK, DEST_FOLDER_OK, excludeList, this.getTemplateGuiInterface());
		tagSortPictures.run();
		
		assertTrue(dt_ft.exists());
		assertTrue(dt_sd.exists());
		assertTrue(dt_sd_jpg.exists());
		assertTrue(dt_ft_jpg.exists());
		assertFalse(dt_th.exists());

		assertTrue(dt_sd_jpg.delete());
		assertTrue(dt_ft_jpg.delete());
		assertTrue(dt_ft.delete());
		assertTrue(dt_sd.delete());
	}

	@Test
	public void test_existTagInFolder() {
		TagSortPicturesCore tagSortPictures = new TagSortPicturesCore(SOURCE_FOLDER_OK_SERACHTAG, null, null, this.getTemplateGuiInterface());
		Map<String, Integer> mr = tagSortPictures.getExistTagsInSourceFolder();

		assertTrue(mr.get(FIRST_TAG)==2 && mr.get(SECOND_TAG)==1 && mr.get(THIRD_TAG)==1);
	}

	@Test
	public void test_nbFiles() {
		TagSortPicturesCore tagSortPictures = new TagSortPicturesCore(SOURCE_FOLDER_OK_SERACHTAG, null, null, this.getTemplateGuiInterface());
		Integer mr = tagSortPictures.getNbFiles();

		assertEquals(mr, Integer.valueOf(2));
	}

	TemplateGuiInterface getTemplateGuiInterface() {
		return new TemplateGuiInterface() {

			@Override
			public void setProgressBarTotal(Integer hundredPercent) {}

			@Override
			public void addMessage(MessageRowTSP msg) {
				System.out.print(msg.getErrorType().toString() + "\t- " + msg.getErrorMessage() + "\n");

			}

			@Override
			public void addIncrementProgressBar() {}
		};
	}
}
