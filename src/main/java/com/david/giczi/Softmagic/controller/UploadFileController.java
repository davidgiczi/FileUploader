package com.david.giczi.Softmagic.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.david.giczi.Softmagic.util.FileUtility;

@RestController
@RequestMapping("/softmagic")
public class UploadFileController {

	
	@PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile[] files) {
		
		MultipartFile mistakedFile = null;
		int fileCounter = 0;
        
		try {
        	for (MultipartFile file : files) {
        	mistakedFile = file;
            createDirIfNotExist();
            byte[] bytes = new byte[0];
            bytes = file.getBytes();
            Files.write(Paths.get(FileUtility.folderPath + file.getOriginalFilename()), bytes);
            fileCounter++;
        	}
        	
        } catch (Exception e) {
            return new ResponseEntity<String>("Fájl feltöltése sikertelen: " + mistakedFile.getOriginalFilename(), 
            		HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<String>(files.length + " db elküldött fájlból " +  fileCounter +  " db fájl feltöltve.", HttpStatus.OK);
    } 
 	
	private void createDirIfNotExist() {
        File directory = new File(FileUtility.folderPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

}
