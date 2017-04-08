/**
 * 
 */
package org.selfip.nibelungen.tagsortpictures.job;

import java.io.File;
import java.io.IOException;
import static org.junit.Assert.*;

import org.junit.Test;
import org.selfip.nibelungen.tagsortpictures.utils.FileUtils;


/**
 * @author Mickaël
 *
 */
public class TestFileUtils {
	
	/**
	 * 
	 */
	private static final String FILE_SOURCE = "src/test/resources/TestFileUtils/file.txt";
	
	/**
	 * 
	 */
	private static final String FILE_DEST = "src/test/resources/TestFileUtils/copy.txt";
	

	@Test
	public void copyFile() {
		File sourceFile = new File(FILE_SOURCE);
		File destinationFile = new File(FILE_DEST);
		Boolean exist = false;
		Long fileSize = null;
		
		if(destinationFile.exists()) destinationFile.delete();
		
		
		try {
			FileUtils.copyFile(sourceFile, destinationFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(destinationFile.exists()) {
			exist = true;
			fileSize = destinationFile.length();
			destinationFile.delete();
		}

		assertFalse(!exist);
		assertEquals(Long.valueOf(sourceFile.length()), fileSize);
	}
}
