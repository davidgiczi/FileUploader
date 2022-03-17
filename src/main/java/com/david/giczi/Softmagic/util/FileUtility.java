package com.david.giczi.Softmagic.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtility {
	
	private FileUtility() {
	}
		    
	public static String folderPath =  "C:\\Dokumentumok\\incoming-files\\";
	public static Path filePath = Paths.get(folderPath);


	public static List<String> getFolderNames() throws IOException{
		
		BufferedReader reader = new BufferedReader(new FileReader(new File(folderPath + "mappanevek.txt")));
		List<String> folders = new ArrayList<>();
		String folder;
		while((folder = reader.readLine()) != null) {
			folders.add(folder);
		}
		reader.close();
		return folders;
	}
	
}