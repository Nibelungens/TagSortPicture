package org.nibelungen.tagsortpictures.job;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestPictureTSP {
	private static final String VALID_JPG_FILE = "src/test/resources/TestPicture/jpegTest.jpg";
	private static final String INVALID_FORMAT_JPG_FILE = "src/test/resources/TestPicture/jpegTest.jpg";
	private static final String INVALID_PATH_JPG_FILE = "src/test/resources/TestPicture/jpegInexist.jpg";

	@Test
	public void test_constructor() {
		PictureTSP img = new PictureTSP(VALID_JPG_FILE);
		
		assertTrue(img.exists());
	}

	@Test
	public void test_checkTags() {
		PictureTSP img = new PictureTSP(VALID_JPG_FILE);
		List<String> listTag = img.getTags();
		boolean ok = false;
		
		if(!img.isError()) {
			for (String string : listTag) {
				if (string.equals("firsttag") || string.equals("secondtag")) {
					ok = true;
					break;
				}
			}
		}
		
		assertTrue(ok);
	}

	@Test
	public void test_checkError0x1() {
		PictureTSP img = new PictureTSP(INVALID_FORMAT_JPG_FILE);
		assertNotNull(img.getTags());
		
		if(img.isError()) {
			assertEquals(PictureTSP.ERR_0x1, img.getError());
		}
	}

	@Test
	public void test_checkError0x2() {
		PictureTSP img = new PictureTSP(INVALID_PATH_JPG_FILE);
		assertNotNull(img.getTags());
		
		if(img.isError()) {
			assertEquals(PictureTSP.ERR_0x2, img.getError());
		}
	}
}
