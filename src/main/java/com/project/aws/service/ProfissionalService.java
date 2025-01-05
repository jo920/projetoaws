package com.project.aws.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.applicationautoscaling.model.ObjectNotFoundException;
import com.google.gson.Gson;
import com.project.aws.model.Profissional;
import com.project.aws.repository.ProfissionalRepository;

@Service
public class ProfissionalService {

	
	@Autowired
	private ProfissionalRepository repo;
	
	@Autowired
	private S3Service S3;
	
	
	public List<Profissional> findAll() {
		return repo.findAll();
	}
	
	
	public Profissional findById(Long id) {
		Optional<Profissional> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente não encontrado! Id: " + id + ", Tipo: " + Profissional.class.getName()));
	}
	
	public Profissional insert(Profissional client) throws Exception {

		// ******************** Consumindo uma API Externa CEP
		// *******************************

		// Metodo para adquirir url da API
		URL url = new URL("https://viacep.com.br/ws/" + client.getCep() + "/json/");

		// Abrindo conexão
		URLConnection connection = url.openConnection();
		// Realizando o metodo de obter de dados
		InputStream is = connection.getInputStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		String cep = "";
		StringBuilder jsoncep = new StringBuilder();

		// Lendo as linhas da API EXTERNA e passando para a variável
		while ((cep = br.readLine()) != null) {

			jsoncep.append(cep);

		}

		// Estou convertendo os dados de String para Json para serem utilizados.
		Profissional cliaux = new Gson().fromJson(jsoncep.toString(), Profissional.class);

		// Estou trazendo os dados convertidos para o método.
		client.setCep(cliaux.getCep());
		client.setLogradouro(cliaux.getLogradouro());
		client.setComplemento(cliaux.getComplemento());
		client.setBairro(cliaux.getBairro());
		client.setLocalidade(cliaux.getLocalidade());
		client.setUf(cliaux.getUf());

		// ******************** Consumindo uma API Externa CEP
		// *****************************
		client.setId(null);
		
		return repo.save(client);
		
	}
	
	public void delete(Long id) {
		findById(id);
		repo.deleteById(id);
	}	
	
	
	public URI uploadProfilePicture(MultipartFile multi) {
		return S3.uploadFile(multi);
	}
	
	
}
