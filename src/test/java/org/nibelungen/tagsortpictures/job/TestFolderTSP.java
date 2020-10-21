package org.nibelungen.tagsortpictures.job;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;
import org.junit.Test;

public class TestFolderTSP {
	private static final String VALID_FILE = "src/test/resources/TestFolder/test/jpegTest.jpg";
	private static final String INVALID_FILE = "src/test/resources/TestFolder/test/jpegTestInexist.jpg";
	private static final String VALID_FULL_FOLDER = "src/test/resources/TestFolder/full";
	private static final String INVALID_FOLDER = "src/test/resources/TestFolder/inexist";
	private static final String VALID_EMPTY_FOLDER = "src/test/resources/TestFolder/empty";
	private static final String EXIST_FOLDER = "src/test/resources/TestFolder/test";

	@Test
	public void test_constructor() {
		FolderTSP folder = new FolderTSP(VALID_FULL_FOLDER);
		
		assertTrue(folder.exists());
	}

	@Test
	public void test_isThisExtension_ok() {
		FolderTSP folder = new FolderTSP(VALID_FULL_FOLDER);
		String nameFile = VALID_FULL_FOLDER + "/jpeg.jpeg";
		String[] extensions = {"jpeg"};
		
		assertEquals(nameFile, folder.isThisExtension(nameFile, extensions));
	}

	@Test
	public void test_isThisExtension_null() {
		FolderTSP folder = new FolderTSP(VALID_FULL_FOLDER);
		String nameFile = VALID_FULL_FOLDER + "/txt.txt";
		String[] extensions = {"jpg"};
		
		assertNull(folder.isThisExtension(nameFile, extensions));
	}

	@Test
	public void test_getFiles() {
		FolderTSP folder = new FolderTSP(VALID_FULL_FOLDER);
		String expectedResult = "[jpeg.jpeg, jpg.jpg, png.png]";

		List<String> files = folder.getFiles();

		assertEquals(files.toString(), expectedResult);
	}

	@Test
	public void test_getNumberFileInFolder() {
		FolderTSP folder = new FolderTSP(VALID_FULL_FOLDER);

		List<String> files = folder.getFiles();

		assertNotNull(files);
		assertEquals(folder.getNumberFileInFolder(), Integer.valueOf(3));
	}

	@Test
	public void test_getFiles_empty_1x2() {
		FolderTSP folder = new FolderTSP(VALID_EMPTY_FOLDER);

		if (!folder.exists()) {
			assertTrue(folder.mkdir());
		}

		List<String> files = folder.getFiles();

		assertNotNull(files);
		assertTrue(folder.isError());
		assertEquals(folder.getError(), FolderTSP.ERR_1x2);
	}

	@Test
	public void test_getFiles_invalid_1x1() {
		FolderTSP folder = new FolderTSP(INVALID_FOLDER);

		List<String> files = folder.getFiles();

		assertNotNull(files);
		assertTrue(folder.isError());
		assertEquals(folder.getError(), FolderTSP.ERR_1x1);
	}

	@Test
	public void test_checkSubFolderAndCreateIfNotExist_not_exist() {
		FolderTSP folder = new FolderTSP(EXIST_FOLDER+"/");
		File test = new File(INVALID_FOLDER);
		boolean ok = false;
		
		if(test.exists()) {
			assertTrue(test.delete());
		}
		
		test = folder.checkSubFolderAndCreateIfNotExist("inexist");

		if(test.exists() && test.isDirectory()) {
			ok = true;
		}

		assertTrue(test.delete());
		assertTrue(ok);
	}

	@Test
	public void test_checkSubFolderAndCreateIfNotExist_exist() {
		FolderTSP folder = new FolderTSP(EXIST_FOLDER);
		File test = null;
		
		test = folder.checkSubFolderAndCreateIfNotExist("exist");

		assertNotNull(test);
		assertTrue(test.exists());
		assertTrue(test.isDirectory());
	}

	@Test
	public void test_movePictureTSP() {
		FolderTSP folder = new FolderTSP(EXIST_FOLDER);
		PictureTSP picture = new PictureTSP(VALID_FILE);
		boolean ok = false;
		File pic = new File(EXIST_FOLDER + "/exist/" + picture.getName());
				
		if(pic.exists()) {
			assertTrue(pic.delete());
		}
		
		folder.movePictureTSP(picture, "exist");

		if(pic.exists()) {
			ok = true;
			assertTrue(pic.delete());
		}
				
		assertTrue(ok);
	}

	@Test
	public void test_movePictureTSP_not_exist_file_1x3() {
		FolderTSP folder = new FolderTSP(EXIST_FOLDER);
		PictureTSP picture = new PictureTSP(INVALID_FILE);
		
		folder.movePictureTSP(picture, "exist");

		assertTrue(folder.isError());
		assertEquals(folder.getError(), FolderTSP.ERR_1x3);
	}
}
