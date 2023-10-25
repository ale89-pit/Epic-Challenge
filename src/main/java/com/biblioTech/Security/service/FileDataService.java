package com.biblioTech.Security.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;


import com.biblioTech.Security.repository.FileDataLocal;

@Service
public class FileDataService implements FileDataLocal {

	
	
	private final Path FOLDER_PATH = Paths.get("src","main","resources","csvFiles");
	
	
	//CREA LA DIRECTORY SE NON ESISTE
	@Override
	public void init()  {
		   
			      try {
					Files.createDirectories(FOLDER_PATH);
				} catch (java.io.IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException("Could not initialize folder for upload!");
				}	  
	}
	
	
	@Override
	public void save(MultipartFile file) {
	
		System.out.println(FOLDER_PATH);
	    try {
	      Files.copy(file.getInputStream(), this.FOLDER_PATH.resolve(file.getOriginalFilename()));
	    } catch (Exception e) {
	      if (e instanceof FileAlreadyExistsException) {
	        throw new RuntimeException("A file of that name already exists.");
	      }

	      throw new RuntimeException(e.getMessage());
	    }
	  }
	
	//RITORNA IL PATH ASSOLUTO DAL SERVER
	@Override
	 public Resource load(String filename) {
		    try {
		      Path file = FOLDER_PATH.resolve(filename);
		      Resource resource = new UrlResource(file.toUri());

		      if (resource.exists() || resource.isReadable()) {
		        return resource;
		      } else {
		        throw new RuntimeException("Could not read the file!");
		      }
		    } catch (MalformedURLException e) {
		      throw new RuntimeException("Error: " + e.getMessage());
		    }
		  }
	  public void deleteAll() {
		    FileSystemUtils.deleteRecursively(FOLDER_PATH.toFile());
		  }
	  
	  //TODO PROVARE A CANCELLARE DOPO
	  @Override
	  public Stream<Path> loadAll()  {
		   
		      try {
				return Files.walk(this.FOLDER_PATH, 1).filter(path -> !path.equals(this.FOLDER_PATH)).map(this.FOLDER_PATH::relativize);
			} catch (java.io.IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("Could not load the files!");
			}
		  
		   
		  }
//	public String uploadImageFromFileSystem(MultipartFile file) throws IOException, IllegalStateException, java.io.IOException{
//		String filePath = FOLDER_PATH + file.getOriginalFilename();
//		
//		FileData fileData = fileDataRepository.save(FileData.builder()
//							.nome(file.getOriginalFilename())
//							.type(file.getContentType())
//							.filePath(filePath).build());
//		file.transferTo(new File(filePath));
//			if(fileData != null) {
//				return filePath;
//			}
//		return null;
//	}

	
//	public byte[] downloadImageFromFileSystem(String fileName) throws java.io.IOException {
//		Optional<FileData>  fileData = fileDataRepository.findByNome(fileName);
//		 String filePath = fileData.get().getFilePath();
//		 
//		byte [] image = Files.readAllBytes(new File(filePath).toPath());
////		String encodeImage = Base64.getEncoder().encodeToString(image);
//		return image;
//	}
}