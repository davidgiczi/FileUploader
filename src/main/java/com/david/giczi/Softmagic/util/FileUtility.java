package com.david.giczi.Softmagic.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtility {

	private FileUtility() {
	}
		    
	public static final String folderPath =  "./incoming-files/";
	public static final Path filePath = Paths.get(folderPath);

}
