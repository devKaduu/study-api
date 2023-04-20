package com.github.acnaweb.study_api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.acnaweb.study_api.controller.dto.PessoaRequestCreate;
import com.github.acnaweb.study_api.controller.dto.PessoaRequestUpdate;
import com.github.acnaweb.study_api.controller.dto.SearchedPessoa;
import com.github.acnaweb.study_api.model.Pessoa;
import com.github.acnaweb.study_api.repository.PessoaRepository;
import com.github.acnaweb.study_api.service.PessoaService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pessoas")
@Tag(name = "Pessoas", description = "Manutenção de informações sobre pessoas")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@GetMapping
	public List<SearchedPessoa> listAll() {
		List<SearchedPessoa> result = 
				pessoaService.list()
				.stream()
				.map(SearchedPessoa::toDto)
				.collect(Collectors.toList());
		return result;
	}

	@PostMapping	
	public Pessoa create(@RequestBody PessoaRequestCreate dto) {
		// DE PARAmapeamento entre dto e o model
		// DEPARA mapeamento entre PessoaCreated e Pessoa
		
		Pessoa pessoa = new Pessoa();
		
		// mapeamento
		pessoa.setIdade(dto.getIdade());
		pessoa.setNome(dto.getNome());		
		
		Pessoa result = pessoaService.save(pessoa);
		return result;
	}

	@PutMapping
	public Pessoa update(@RequestBody PessoaRequestUpdate dto) {
		// verificar se o id informado já existe
		
		boolean exists =
				pessoaRepository.existsById(dto.getId());
		
		if (!exists) {
			throw new RuntimeException("Id não encontrado " 
							+ dto.getId());			
		}
		Pessoa pessoa = new Pessoa();
		// mapeamento
		pessoa.setId(dto.getId());
		pessoa.setIdade(dto.getIdade());
		pessoa.setNome(dto.getNome());		

		Pessoa result = pessoaService.save(pessoa);
		return result;
	}

	@DeleteMapping(value = "{id}")
	public void delete(@PathVariable Long id) {
		pessoaRepository.deleteById(id);		
	}

}
