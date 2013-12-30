package ci.gouv.budget.solde.sigdcp.model._validation;

import java.util.Date;
import java.util.logging.Level;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.extern.java.Log;

import org.apache.commons.beanutils.PropertyUtils;

@Log
public class OrderedDateValidator implements ConstraintValidator<OrderedDate, Object> {
	
	private String firstFieldName;
	private String secondFieldName;

	@Override
	public void initialize(final OrderedDate constraintAnnotation) {
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
	}

	@Override
	public boolean isValid(final Object value,final ConstraintValidatorContext context) {
		try {
			final Date firstDate = (Date) PropertyUtils.getProperty(value,firstFieldName);
			final Date secondDate = (Date) PropertyUtils.getProperty(value,secondFieldName);
			return firstDate!=null && secondDate!=null && firstDate.before(secondDate);
		} catch (final Exception e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return true;
	}
}