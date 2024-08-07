package com.lfj.blog.common.validator;

import com.lfj.blog.common.validator.annotation.ListSize;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ListSizeValidator implements ConstraintValidator<ListSize, List> {

	private int min = -1;

	private int max = -1;

	private boolean required;


	@Override
	public boolean isValid(List value, ConstraintValidatorContext context) {
		int size = value.size();
		if (required) {
			if (min != -1 && max != -1) {
				return (size > min && size < max);
			} else if (min == -1) {
				return size <= max;
			}
			return size >= min;
		}
		return true;
	}


	@Override
	public void initialize(ListSize constraintAnnotation) {
		this.required = constraintAnnotation.required();
		this.min = constraintAnnotation.min();
		this.max = constraintAnnotation.max();
	}
}
