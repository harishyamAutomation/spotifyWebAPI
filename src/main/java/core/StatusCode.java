package core;

public enum StatusCode {
	SUCCESS(200, "OK"),
	CREATED(201, "CREATED"),
	NO_CONTENT(204, "NO CONTENT"),
	UNAUTHORIZED(401, "UNAUTHORIZED"),
	FORBIDDEN(403, "FORBIDDEN");
	
	public final int code;
	public final String message;
	
	private StatusCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
