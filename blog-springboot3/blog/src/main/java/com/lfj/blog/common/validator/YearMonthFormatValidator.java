package com.lfj.blog.common.validator;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lfj.blog.common.validator.annotation.YearMonthFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class YearMonthFormatValidator implements ConstraintValidator<YearMonthFormat, String> {

	private boolean required;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (required && !StringUtils.isBlank(value)) {
			String regexp = "[1-9][0-9]{3}[-](0[1-9]|1[0-2])";
			return Pattern.matches(regexp, value);
		}
		return true;
	}

	@Override
	public void initialize(YearMonthFormat constraintAnnotation) {
		this.required = constraintAnnotation.required();
	}
}
