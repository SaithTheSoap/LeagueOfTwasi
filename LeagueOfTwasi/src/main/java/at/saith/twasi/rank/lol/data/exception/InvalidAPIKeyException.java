package at.saith.twasi.rank.lol.data.exception;

public class InvalidAPIKeyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1324769216445927543L;
	private String apikey;
	public InvalidAPIKeyException(String apikey){
		this.apikey = apikey;
	}
	public String getMessage() {
		return "The APIKEY("+apikey+") is invalid";
	}
}
