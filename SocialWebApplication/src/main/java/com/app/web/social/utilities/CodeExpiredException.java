package com.app.web.social.utilities;

public class CodeExpiredException extends Exception {

	private static final long serialVersionUID = -6091360266870230117L;

	public CodeExpiredException(String message) {
        super(message);
    }

}