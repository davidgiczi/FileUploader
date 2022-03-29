package com.david.giczi.Softmagic.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FileService {
	
	
	public static String folderPath =  "./incoming-files/";
	public static Path filePath = Paths.get(folderPath);


	public List<String> getFolderNames() throws IOException{
		
		BufferedReader reader = new BufferedReader(new FileReader(new File(folderPath + "mappanevek.txt")));
		List<String> folders = new ArrayList<>();
		String folder;
		while((folder = reader.readLine()) != null) {
			folders.add(folder);
		}
		reader.close();
		return folders;
	}
	
	public boolean isEnabled(String inputData) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(folderPath + "jelszo.txt")))) {
			return (reader.readLine()).equals(inputData);
		}
	}
}