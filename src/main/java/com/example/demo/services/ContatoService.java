package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Contato;
import com.example.demo.repositories.ContatoRepository;
import com.example.demo.resources.exception.DataIntegrityException;
import com.example.demo.services.exception.ObjectNotFoundException;

@Service
public class ContatoService {
	
	@Autowired
	private ContatoRepository repositorio;
	
	public Contato find(Integer id) {
		
		Optional<Contato> obj = repositorio.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"objeto não encontrado id: " + id + ", Tipo: " + Contato.class.getName()));
		
	}

	public Contato insert(Contato obj) {
		obj.setId(null);
		return repositorio.save(obj);
	}
	
	public Contato update(Contato obj) {
		find(obj.getId());
		return repositorio.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repositorio.deleteById(id);;
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel exlucir. Contato não encontrado.");
		}
	}
	
	public List<Contato> findAll(){
		return repositorio.findAll();
	}
	
}

