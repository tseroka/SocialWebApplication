package com.app.web.social.model.converter;

import java.util.Arrays;
import java.util.HashSet;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringSetConverter implements AttributeConverter<HashSet<String>, String>
{

	public String convertToDatabaseColumn(HashSet<String> set) 
	{
		  String result;
		  if(set==null) result="";
		  else result= String.join("-", set);
		  return result;
	}

	public HashSet<String> convertToEntityAttribute(String joined) 
	{
		  HashSet<String> set = new HashSet<String>();
		  if(joined==null || joined.equals("")) return set;
		  else  set = new HashSet<String>( Arrays.asList(joined.split("-") ) ) ; 
		  
		  return set;
	}
	
}
