package br.com.sistema.exception;

public class StringInvalidaException extends Exception{
	public StringInvalidaException() {
		super();
	}

	public StringInvalidaException(String mensagem) {
		super(mensagem);
	}
}
