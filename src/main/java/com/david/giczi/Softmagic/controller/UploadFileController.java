package com.david.giczi.Softmagic.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.david.giczi.Softmagic.model.Doc;
import com.david.giczi.Softmagic.model.Link;
import com.david.giczi.Softmagic.service.FileService;


@RestController
@RequestMapping("/softmagic")
@CrossOrigin
public class UploadFileController {

	@Autowired
	private FileService fileService;
	
	@PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile[] files,
    		@RequestParam("clientname") String clientName,
    		@RequestParam("foldername") String folderName) {
	
		MultipartFile mistakedFile = null;
		int fileCounter = 0;
		
		try {
        	for (MultipartFile file : files) {
        	mistakedFile = file;
        	createDirIfNotExist(clientName, folderName);
            byte[] bytes = new byte[0];
            bytes = file.getBytes();
            Files.write(Paths.get(FileService.uploadingFileFolder + clientName  + "/" + folderName +"/" + file.getOriginalFilename()), bytes);
            fileCounter++;
        	}
        	
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<String>("F??jl felt??lt??se sikertelen: " + mistakedFile.getOriginalFilename(), 
            		HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<String>(files.length + " db elk??ld??tt f??jlb??l " +  fileCounter +  " db f??jl felt??ltve.", HttpStatus.OK);
    } 
 	
	private void createDirIfNotExist(String clientName, String folderName) {
		
		File uploadingDirectory = new File(FileService.uploadingFileFolder);
		if( !uploadingDirectory.exists() ) {
			uploadingDirectory.mkdir();
		}	
		File clientDirectory = new File(uploadingDirectory.getAbsolutePath() + "/" + clientName);
        if ( !clientDirectory.exists() ) {
            clientDirectory.mkdir();
       }
        File fileFolderDirectory = new File(clientDirectory.getAbsolutePath() + "/" + folderName);
        if( !fileFolderDirectory.exists()) {
            fileFolderDirectory.mkdir();
         }
   }
	
	@GetMapping(value="/foldernames")
	public ResponseEntity<List<String>> getFolderNames(){
		List<String> folderNames;
		try {
			folderNames = fileService.getFolderNames();
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<List<String>>(Arrays.asList("Nem olvashat?? a \"mappanevek.txt\" f??jl."), HttpStatus.INTERNAL_SERVER_ERROR);
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
			return new ResponseEntity<List<String>>(Arrays.asList("Nem olvashat?? az \"ugyfelek.txt\" f??jl."), HttpStatus.INTERNAL_SERVER_ERROR);
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
	
	@GetMapping(value = "/docs")
	public ResponseEntity<List<Doc>> getDocs(){
		List<Doc> docs = null;
		try {
			docs = fileService.getDocs();
		} catch (IOException | ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			new ResponseEntity<List<Doc>>(docs, HttpStatus.OK);
		}
		return new ResponseEntity<List<Doc>>(docs, HttpStatus.OK);
	}
	
	@GetMapping(value = "/links")
	public ResponseEntity<List<Link>> getLinks() {
		List<Link> links = null;
		try {
			links = fileService.getLinks();
		} catch (IOException | ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			return new ResponseEntity<List<Link>>(links, HttpStatus.OK);
		}
		return new ResponseEntity<List<Link>>(links, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "https://kapcsolat.softmagic.hu")
	@GetMapping(value = "/doc", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<InputStreamResource> getDoc(HttpServletRequest request, HttpServletResponse response) throws IOException {
	int id = Integer.parseInt(request.getParameter("docId"));
	Doc chosenDoc = fileService.getDocs().get(id - 1);
	String docName = chosenDoc.getTitle() + "." + chosenDoc.getExtension();
	InputStreamResource inputStreamResource = null;
	File doc =  new File("./softmagic-app/init/docs/" + docName);
	try {
    inputStreamResource = new InputStreamResource(new FileInputStream(doc));
	} catch (IOException e) {
		e.printStackTrace();
		return new ResponseEntity<InputStreamResource>(inputStreamResource, new HttpHeaders(), HttpStatus.OK);
	}
	long length = doc.length();
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentLength(length);
    httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());
    
    return new ResponseEntity<InputStreamResource>(inputStreamResource, httpHeaders, HttpStatus.OK);
}
}
