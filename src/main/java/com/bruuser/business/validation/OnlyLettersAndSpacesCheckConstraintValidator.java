package com.bruuser.business.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OnlyLettersAndSpacesCheckConstraintValidator implements ConstraintValidator<OnlyLettersAndSpacesCheck, ValidEntity> {

    @Override
    public void initialize(OnlyLettersAndSpacesCheck constraintAnnotation) {
    }

    @Override
    public boolean isValid(ValidEntity entity, ConstraintValidatorContext context) {
        return entity.isFullNameWithOnlyLettersAndSpaces();
    }
}
