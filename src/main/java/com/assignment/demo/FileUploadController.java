package com.assignment.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.demo.model.FileMetadata;
import com.assignment.demo.properties.FileDirectory;
import com.assignment.demo.service.FileMetadataService;
import com.sun.jersey.api.client.ClientResponse.Status;



@RestController
public class FileUploadController {
	
	//change the directory in fileDirectory.properties file
	@Autowired
	private FileDirectory fileDirectory;
	
	FileMetadata fileData;
	
	@Autowired
	private FileMetadataService fileDataService;
	
		
	//Testing if all the files are saved or not
	@GetMapping(value = "/get")
	public List getAllFiles()
	{
		List list =  fileDataService.getAllFiles();
		return list;
	}
	
	//post request will come here along with the file
	@PostMapping(value = "/upload")
	public Response test(@RequestParam("file")  MultipartFile file, @RequestParam("author") String Author, @RequestParam("fileName") String Name)
	{
		Status status = null;
		
		//if no content send the 204 response to the client
		if (file.isEmpty()) {
			status = Status.NO_CONTENT;
			return Response.status(status).type(MediaType.TEXT_HTML_TYPE).build();
			
        }
		
		try {
			 
			byte[] bytes = file.getBytes();
			
			//create a file with file name in a specified directory
			File fileName = new File(fileDirectory.getDirectory() + file.getOriginalFilename());
			FileOutputStream outputStream = new FileOutputStream(fileName);
		    outputStream.write(bytes);
		    outputStream.close();
		    
		    //store the metadata in an object to persist in DB
		    fileData = new FileMetadata();
		    fileData.setFilePath(fileName.toString());			
			
		} catch (IOException e) {
			e.printStackTrace();
			status = Status.NO_CONTENT;
			return Response.status(status).type(MediaType.TEXT_HTML_TYPE).build();
		}
		 
		 
		//save the metadata and file location in DB		   
		fileData.setAuthorName(Author);
		fileData.setFileName(Name);
		
		fileDataService.saveFileMetaData(fileData);
		
		//if file along with the metadata is saved send the success response
		status = Status.OK;
		return Response.status(status).type(MediaType.TEXT_HTML_TYPE).build();

	}

}
