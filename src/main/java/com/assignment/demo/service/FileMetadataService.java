package com.assignment.demo.service;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.demo.DAO.FileMetadataDAO;
import com.assignment.demo.model.FileMetadata;

@Service
public class FileMetadataService {
	
	@Autowired
	private FileMetadataDAO fileMetadataDAO;
	
	@Transactional
	public void saveFileMetaData(FileMetadata data)
	{
		fileMetadataDAO.saveFileMetaData(data);	
	}
	
	@Transactional
	public List getAllFiles()
	{
		return fileMetadataDAO.getAllFiles();
	}

}
