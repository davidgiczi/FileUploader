package com.david.giczi.Softmagic.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.david.giczi.Softmagic.service.FileService;


@RestController
@RequestMapping("/softmagic")
@CrossOrigin
public class UploadFileController {

	@Autowired
	private FileService fileService;
	
	@PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile[] files, @RequestParam("foldername") String folderName) {
	
		MultipartFile mistakedFile = null;
		int fileCounter = 0;
		
		try {
        	for (MultipartFile file : files) {
        	mistakedFile = file;
            createDirIfNotExist(folderName);
            byte[] bytes = new byte[0];
            bytes = file.getBytes();
            Files.write(Paths.get(FileService.folderPath + folderName  + "/" + file.getOriginalFilename()), bytes);
            fileCounter++;
        	}
        	
        } catch (Exception e) {
            return new ResponseEntity<String>("Fájl feltöltése sikertelen: " + mistakedFile.getOriginalFilename(), 
            		HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<String>(files.length + " db elküldött fájlból " +  fileCounter +  " db fájl feltöltve.", HttpStatus.OK);
    } 
 	
	private void createDirIfNotExist(String folderName) {
        File directory = new File(FileService.folderPath + folderName);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
	
	@GetMapping(value="/foldernames")
	public ResponseEntity<List<String>> getFolderNames(){
		List<String> folderNames;
		try {
			folderNames = fileService.getFolderNames();
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<List<String>>(Arrays.asList("Nem olvasható a \"mappanevek.txt\" fájl."), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<String>>(folderNames, HttpStatus.OK);
	}

	@GetMapping(value="/clients")
	public ResponseEntity<List<String>> getCilentNames(){
		List<String> clientNames;
		try {
			clientNames = fileService.getClientNames();
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<List<String>>(Arrays.asList("Nem olvasható az \"ugyfelek.txt\" fájl."), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<String>>(clientNames, HttpStatus.OK);
	}
	
	@PostMapping(value = "/getPermission")
	public ResponseEntity<Boolean> getPermission(@RequestParam("identifier") String identifier) throws IOException{
		

			if(fileService.isEnabled(identifier)) {
				return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
			}
				
		return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.OK);
	}
	
}
