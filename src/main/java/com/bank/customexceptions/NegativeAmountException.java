package com.bank.customexceptions;

public class NegativeAmountException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NegativeAmountException(String message) {
		super(message);
	}
	
}
