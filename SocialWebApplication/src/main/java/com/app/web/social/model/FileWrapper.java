package com.app.web.social.model;

import org.springframework.web.multipart.MultipartFile;

public class FileWrapper 
{
	private MultipartFile file;
	
	public MultipartFile getFile() {
        return file;
    }
 
    public void setFile(MultipartFile file) {
        this.file = file;
    }
    
}
 