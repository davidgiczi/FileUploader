package com.david.giczi.Softmagic.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.david.giczi.Softmagic.util.FileUtility;


@RestController
@RequestMapping("/softmagic")
@CrossOrigin
public class UploadFileController {


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
            Files.write(Paths.get(FileUtility.folderPath + folderName  + "/" + file.getOriginalFilename()), bytes);
            fileCounter++;
        	}
        	
        } catch (Exception e) {
            return new ResponseEntity<String>("Fájl feltöltése sikertelen: " + mistakedFile.getOriginalFilename(), 
            		HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<String>(files.length + " db elküldött fájlból " +  fileCounter +  " db fájl feltöltve.", HttpStatus.OK);
    } 
 	
	private void createDirIfNotExist(String folderName) {
        File directory = new File(FileUtility.folderPath + folderName);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
	
	@GetMapping(value="/foldernames")
	public ResponseEntity<List<String>> getFolderNames(){
		List<String> folderNames;
		try {
			folderNames = FileUtility.getFolderNames();
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<List<String>>(Arrays.asList("Nem olvasható a \"mappanevek.txt\" fájl."), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<String>>(folderNames, HttpStatus.OK);
	}

	
}
