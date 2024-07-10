package br.itb.projeto.vitalususPlus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Startup {

	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
		String pessoa = System.getProperty("user.name");
		pessoa = pessoa.toUpperCase();
		System.out.println("TRABALHA, " + pessoa + "!");
	}}
