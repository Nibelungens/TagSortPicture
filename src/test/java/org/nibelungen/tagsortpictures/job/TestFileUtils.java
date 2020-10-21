package org.nibelungen.tagsortpictures.job;

import java.io.File;
import java.io.IOException;
import static org.junit.Assert.*;

import org.junit.Test;
import org.nibelungen.tagsortpictures.utils.FileUtils;

public class TestFileUtils {
	private static final String FILE_SOURCE = "src/test/resources/TestFileUtils/file.txt";
	private static final String FILE_DEST = "src/test/resources/TestFileUtils/copy.txt";

	@Test
	public void copyFile() {
		File sourceFile = new File(FILE_SOURCE);
		File destinationFile = new File(FILE_DEST);
		boolean exist = false;
		Long fileSize = null;
		
		if(destinationFile.exists()) assertTrue(destinationFile.delete());
		
		
		try {
			FileUtils.copyFile(sourceFile, destinationFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(destinationFile.exists()) {
			exist = true;
			fileSize = destinationFile.length();
			assertTrue(destinationFile.delete());
		}

		assertTrue(exist);
		assertEquals(Long.valueOf(sourceFile.length()), fileSize);
	}
}
