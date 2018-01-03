package com.app.web.social.dao.validations;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
 
import com.app.web.social.model.FileWrapper;
 
@Component
public class FileValidator implements Validator {
         
    public boolean supports(Class<?> clazz) {
        return FileWrapper.class.isAssignableFrom(clazz);
    }
 
    public void validate(Object obj, Errors errors) {
    	FileWrapper file = (FileWrapper) obj;
             
        if(file.getFile()!=null){
            if (file.getFile().getSize() == 0) {
                errors.rejectValue("file", "missing.file");
            }
        }
    }
}