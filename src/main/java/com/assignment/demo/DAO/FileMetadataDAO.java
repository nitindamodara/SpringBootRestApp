package com.assignment.demo.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.assignment.demo.model.FileMetadata;


@Repository
public class FileMetadataDAO {
	
	@Autowired 
	private SessionFactory sessionFactory;
	
	public void saveFileMetaData(FileMetadata data)
	{
		Session session = sessionFactory.getCurrentSession(); 
		session.save(data);		

	}
	
	public List getAllFiles()
	{
		Session session = sessionFactory.getCurrentSession(); 
		List<FileMetadata> list = session.createCriteria(FileMetadata.class).list();
		return list;
	}

}
