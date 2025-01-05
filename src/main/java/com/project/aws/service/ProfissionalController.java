package com.project.aws.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.aws.model.Profissional;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ProfissionalController {

	
	@Autowired
	private ProfissionalService service;
	
	
	
	@GetMapping("/profissional/{id}")
	public Profissional findById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@PostMapping("/profissional")
	public ResponseEntity insert(@RequestBody Profissional prof) throws Exception {
		service.insert(prof);

		return ResponseEntity.ok(prof);
	}
	
	
	@PostMapping("/picture")
	public  ResponseEntity pictureprofile(@RequestParam(name = "file")MultipartFile file) {
		URI uri = service.uploadProfilePicture(file);
		
		return ResponseEntity.created(uri).build();
	}
	
}
