package com.nishant.spring.boot.ldap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@Autowired
	public DirectContextMapperRepo directContextMapperRepo;

	/// methods by contextmapper
	@GetMapping("/personsbycontext")
	public List<Person> getAllPersonsbycontext() {
		return directContextMapperRepo.findAll();
	}
	@GetMapping("/getname/{name}")
	public List<Person> getname(@PathVariable String name) {
		return directContextMapperRepo.findByName(name);
	}
	@GetMapping("/getbyname/{name}")
	public Person getbyname(@PathVariable String name) {
		return directContextMapperRepo.findByPrimaryKey(name);
	}
	@PostMapping("/addbycontext")
	public String addbycontext(@RequestBody Person p) {
		directContextMapperRepo.create(p);
		return "success";
	}
	@PutMapping("/updatebycontext")
	public String updatebycontext(@RequestBody Person p) {
		directContextMapperRepo.update(p);
		return "success";
	}
	@PostMapping("/delbycontext")
	public String deletebycontext(@RequestBody Person p) {
		directContextMapperRepo.delete(p);
		return "success";
	}

}