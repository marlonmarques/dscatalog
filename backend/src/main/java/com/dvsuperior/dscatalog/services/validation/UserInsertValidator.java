package com.dvsuperior.dscatalog.services.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dvsuperior.dscatalog.dto.UserInsertDTO;
import com.dvsuperior.dscatalog.repositories.UserRepository;
import com.dvsuperior.dscatalog.resources.exceptions.FieldMessage;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {
	
    @Autowired
    private UserRepository repository;
    
	@Override
	public void initialize(UserInsertValid ann) {
	}

	@Override
	public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
        if (repository.existsByEmail(dto.getEmail())) {
            list.add(new FieldMessage("email", "Email already exists"));
        }
		// Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}