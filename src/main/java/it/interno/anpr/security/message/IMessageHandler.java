package it.interno.anpr.security.message;

public interface IMessageHandler<T> {
	public T sendRequest();
	public boolean isResponseValid(T resp);
}
