package com.wil.auditoria;

import org.springframework.boot.SpringApplication;

public class TestAuditoriaApplication {

	public static void main(String[] args) {
		SpringApplication.from(AuditoriaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
