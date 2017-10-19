package by.htp.devteam.service.validation;

/**
 * Validation pages parameters.
 * @author julia
 * Singleton
 */
public final class PagingValidation {
	
	private static final PagingValidation instance = new PagingValidation();
	
	private PagingValidation() {
		super();
	}
	
	public static PagingValidation getInstance() {
		return instance;
	}
	
	/**
	 * Check if a page number has a correct value
	 * @param pageNumber Input string
	 * @return if page number is int value
	 */
	public boolean validatePage(String pageNumber) {
		return Validator.isInt(pageNumber) && Integer.valueOf(pageNumber) > 0;
	}
}
