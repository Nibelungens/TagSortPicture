/**
 * 
 */
package org.selfip.nibelungen.tagsortpictures.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author Mickaël
 *
 */
public class FileUtils {

	public static void copyFile(File sourceFile, File destinationFile) throws IOException {
		Path source = Paths.get(sourceFile.getPath());
		Path destination = Paths.get(destinationFile.getPath());

		Files.copy(source , destination, StandardCopyOption.REPLACE_EXISTING);
	}
}
