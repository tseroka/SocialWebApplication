package com.app.web.social.model.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
	
  public String convertToDatabaseColumn(List<String> list) 
  {
	  String result;
	  if(list==null) result="";
	  else result= String.join(",", list);
	  return result;
  }


  public List<String> convertToEntityAttribute(String joined) 
  {
	  List<String> list = new ArrayList<String>();
	  if(joined==null || joined.equals("")) return list;
	  else  list = new ArrayList<String>( Arrays.asList(joined.split(",") ) ) ; 
	  
	  return list;
  }

}