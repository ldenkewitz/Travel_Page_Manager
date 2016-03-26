package com.lde.util.validation;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PastValidator implements ConstraintValidator<Past, Temporal> {

	@Override
	public void initialize(Past paramA) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(Temporal value, ConstraintValidatorContext paramConstraintValidatorContext) {
		if (value == null) {
      return true;
	  }
	  return LocalDate.from(value).isBefore(LocalDate.now());
	}
	
}
