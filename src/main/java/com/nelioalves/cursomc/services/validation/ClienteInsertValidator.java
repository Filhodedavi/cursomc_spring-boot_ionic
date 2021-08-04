package com.nelioalves.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.nelioalves.cursomc.domain.enums.TipCliente;
import com.nelioalves.cursomc.dto.ClienteNewDTO;
import com.nelioalves.cursomc.resources.exception.FieldMessage;
import com.nelioalves.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		//isValid acima verifica se o tipo (ClienteNewDTO) vai ser valido ou nao, retornando true or false
		//incluir testes abaixo, inserindo erros na lista
		List<FieldMessage> list = new ArrayList<>();
		
		if(objDto.getTipo().equals(TipCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfoucnpj())) {
			list.add(new FieldMessage("cpfoucnpj", "CPF invalido"));
		}
		if(objDto.getTipo().equals(TipCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfoucnpj())) {
			list.add(new FieldMessage("cpfoucnpj", "CNPJ invalido"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
			.addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}
}
