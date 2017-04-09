/**
 * 
 */
package org.selfip.nibelungen.tagsortpictures.job;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Mickaël
 *
 */
public class TestFolderTSP {
	
	/**
	 * 
	 */
	private static final String VALID_FILE = "src/test/resources/TestFolder/test/jpegTest.jpg";
	
	/**
	 * 
	 */
	private static final String INVALID_FILE = "src/test/resources/TestFolder/test/jpegTestInexist.jpg";
	
	/**
	 * 
	 */
	private static final String VALID_FULL_FOLDER = "src/test/resources/TestFolder/full";
	
	/**
	 * 
	 */
	private static final String INVALID_FOLDER = "src/test/resources/TestFolder/inexist";
	
	/**
	 * 
	 */
	private static final String VALID_EMPTY_FOLDER = "src/test/resources/TestFolder/empty";
	
	/**
	 * 
	 */
	private static final String EXIST_FOLDER = "src/test/resources/TestFolder/test";
	
	/**
	 * 
	 */
	@Test
	public void test_constructor() {
		FolderTSP folder = new FolderTSP(VALID_FULL_FOLDER);
		
		assertTrue(folder.exists());
	}
	
	
	/**
	 * 
	 */
	@Test
	public void test_isThisExtension_ok() {
		FolderTSP folder = new FolderTSP(VALID_FULL_FOLDER);
		String nameFile = VALID_FULL_FOLDER + "/jpeg.jpeg";
		String[] extensions = {"jpeg"};
		
		assertEquals(nameFile, folder.isThisExtension(nameFile, extensions));
	}
	
	/**
	 * 
	 */
	@Test
	public void test_isThisExtension_null() {
		FolderTSP folder = new FolderTSP(VALID_FULL_FOLDER);
		String nameFile = VALID_FULL_FOLDER + "/txt.txt";
		String[] extensions = {"jpg"};
		
		assertEquals(null, folder.isThisExtension(nameFile, extensions));
	}
	
	/**
	 * 
	 */
	@Test
	@Ignore
	public void test_getFiles() {
		FolderTSP folder = new FolderTSP(VALID_FULL_FOLDER);
		String expectedResult = "[jpeg.jpeg, jpg.jpg, png.png]";
		
		folder.getFiles();
		
		assertEquals(folder.getFiles().toString(), expectedResult);
	}
	
	/**
	 * 
	 */
	@Test
	@Ignore
	public void test_getNumberFileInFolder() {
		FolderTSP folder = new FolderTSP(VALID_FULL_FOLDER);
		
		folder.getFiles();
				
		assertEquals(folder.getNumberFileInFolder(), new Integer(3));
	}
	
	/**
	 * 
	 */
	@Test
	public void test_getFiles_empty_1x2() {
		FolderTSP folder = new FolderTSP(VALID_EMPTY_FOLDER);
			
		folder.mkdir();
		folder.getFiles();
		
		assertFalse(!folder.isError());
		assertEquals(folder.getError(), FolderTSP.ERR_1x2);
	}
	
	/**
	 * 
	 */
	@Test
	public void test_getFiles_invalid_1x1() {
		FolderTSP folder = new FolderTSP(INVALID_FOLDER);
				
		folder.getFiles();
		
		assertFalse(!folder.isError());
		assertEquals(folder.getError(), FolderTSP.ERR_1x1);
	}
	
	/**
	 * 
	 */
	@Test
	public void test_checkSubFolderAndCreateIfNotExist_notexist() {
		FolderTSP folder = new FolderTSP(EXIST_FOLDER+"/");
		File tets = new File("src/test/ressources/TestFolder/test/inexist");
		Boolean ok = false;
		
		if(tets.exists()) tets.delete();
		
		try {
			tets = folder.checkSubFolderAndCreateIfNotExist("inexist");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(tets.exists() && tets.isDirectory()) ok = true;		
		tets.delete();
		
		assertTrue(ok);
	}
	
	/**
	 * 
	 */
	@Test
	public void test_checkSubFolderAndCreateIfNotExist_exist() {
		FolderTSP folder = new FolderTSP(EXIST_FOLDER);
		File tets = null;
		
		try {
			tets = folder.checkSubFolderAndCreateIfNotExist("exist");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(tets.exists() && tets.isDirectory());
	}
	
	/**
	 * 
	 */
	@Test
	public void test_movePictureTSP() {
		FolderTSP folder = new FolderTSP(EXIST_FOLDER);
		PictureTSP picture = new PictureTSP(VALID_FILE);
		Boolean ok = false;
		File pic = new File(EXIST_FOLDER + "/exist/" + picture.getName());
				
		if(pic.exists()) pic.delete();
		
		folder.movePictureTSP(picture, "exist");

		if(pic.exists()) {
			ok = true;
			pic.delete();
		}
				
		assertTrue(ok);
	}
	
	/**
	 * 
	 */
	@Test
	public void test_movePictureTSP_notexistfile_1x3() {
		FolderTSP folder = new FolderTSP(EXIST_FOLDER);
		PictureTSP picture = new PictureTSP(INVALID_FILE);
		
		folder.movePictureTSP(picture, "exist");

		assertFalse(!folder.isError());
		assertEquals(folder.getError(), FolderTSP.ERR_1x3);
	}
}
