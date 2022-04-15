package com.david.giczi.Softmagic.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FileService {
	
	
	public static String appFolder =  "./softmagic-app/init/";
	public static String uploadingFileFolder = "./softmagic-app/uploading-files/";


	public List<String> getFolderNames() throws IOException{
		
		BufferedReader reader = new BufferedReader(new FileReader(new File(appFolder +"mappanevek.txt")));
		List<String> folders = new ArrayList<>();
		String folder;
		while((folder = reader.readLine()) != null) {
			folders.add(folder);
		}
		reader.close();
		return folders;
	}
	
	public List<String> getClientNames() throws IOException{
		
		BufferedReader reader = new BufferedReader(new FileReader(new File(appFolder + "ugyfelek.txt")));
		List<String> clients = new ArrayList<>();
		String folder;
		while((folder = reader.readLine()) != null) {
			clients.add(folder);
		}
		reader.close();
		return clients;
	}
	

	public boolean isEnabled(String inputData) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(appFolder + "jelszo.txt")))) {
			return (reader.readLine()).equals(inputData);
		}
	}
}