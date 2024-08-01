package com.guilherme.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.guilherme.workshopmongo.domain.Post;
import com.guilherme.workshopmongo.domain.User;
import com.guilherme.workshopmongo.dto.AuthorDTO;
import com.guilherme.workshopmongo.dto.CommentDTO;
import com.guilherme.workshopmongo.repository.PostRepository;
import com.guilherme.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GTM"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));

		Post post1 = new Post(null, sdf.parse("21/02/2018"), "Partiu viagem", "Vou viajar para São Paulo, abracos.", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("23/02/2018"), "Bom dia", "Acordei feliz hoje", new AuthorDTO(maria));
		
		CommentDTO c1 = new CommentDTO("Boa Viagem mano", sdf.parse("21/08/2018"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite", sdf.parse("22/08/2018"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Tenha um otimo dia", sdf.parse("23/08/2018"), new AuthorDTO(alex));
		
		post1.getComment().addAll(Arrays.asList(c1, c2));
		post2.getComment().addAll(Arrays.asList(c3));
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userRepository.save(maria);
	}

}
