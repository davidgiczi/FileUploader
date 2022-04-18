package com.david.giczi.Softmagic.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.david.giczi.Softmagic.model.Doc;
import com.david.giczi.Softmagic.model.Link;

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
	
	public List<Doc> getDocs() throws IOException, ArrayIndexOutOfBoundsException{
		
		BufferedReader reader = new BufferedReader(new FileReader(new File(appFolder + "dokumentumtar.txt")));
		List<Doc> docs = new ArrayList<>();
		int id = 0;
		String docTitle;
		while((docTitle = reader.readLine()) != null) {
			String[] titleComponents = docTitle.split("\\.");
			Doc doc = new Doc();
			doc.setId(++id);
			doc.setTitle(titleComponents[0]);
			doc.setExtension(titleComponents[1]);
			docs.add(doc);
		}
		reader.close();
		return docs;
	}
	
	public List<Link> getLinks() throws IOException, ArrayIndexOutOfBoundsException{
		
		BufferedReader reader = new BufferedReader(new FileReader(new File(appFolder + "linkek.txt")));
		List<Link> links = new ArrayList<>();
		int id = 0;
		String row;
		while((row = reader.readLine()) != null) {
			String[] linkComponents = row.split("\\*");
			Link link = new Link();
			link.setId(++id);
			link.setTitle(linkComponents[0]);
			link.setHref(linkComponents[1]);
			links.add(link);
		}
		reader.close();
		return links;
	}
}