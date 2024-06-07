package com.jimd.LiterAluraChallengeJava;

import com.jimd.LiterAluraChallengeJava.repositorio.AutoresRepositorio;
import com.jimd.LiterAluraChallengeJava.repositorio.LibrosRerpositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraChallengeJavaApplication implements CommandLineRunner {

	@Autowired
	LibrosRerpositorio librosRerpositorio;

	@Autowired
	AutoresRepositorio autoresRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraChallengeJavaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(librosRerpositorio,autoresRepositorio);
		principal.mostrarInformacion();
	}
}
