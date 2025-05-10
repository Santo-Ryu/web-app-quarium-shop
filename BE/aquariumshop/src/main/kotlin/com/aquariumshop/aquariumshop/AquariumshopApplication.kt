package com.aquariumshop.aquariumshop

import org.springframework.core.env.Environment
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class AquariumshopApplication {

	@Bean
	fun printEnvironment(env: Environment) = CommandLineRunner {
		println("========= APPLICATION CONFIG =========")
		println("App Name: ${env.getProperty("APP_NAME")}")
		println("Database Host: ${env.getProperty("DB_HOST")}")
		println("Database Port: ${env.getProperty("DB_PORT")}")
		println("Database Name: ${env.getProperty("DB_NAME")}")
		println("Database Username: ${env.getProperty("DB_USERNAME")}")
		println("JWT Secret: ${env.getProperty("JWT_SECRET")}")
		println("JWT Expiration: ${env.getProperty("JWT_EXPIRATION")}")
		println("Mail Host: ${env.getProperty("MAIL_HOST")}")
		println("Mail Port: ${env.getProperty("MAIL_PORT")}")
		println("Mail Username: ${env.getProperty("MAIL_USERNAME")}")
		println("Mail SMTP Auth: ${env.getProperty("MAIL_PROPERTIES_MAIL_SMTP_AUTH")}")
		println("Mail STARTTLS Enable: ${env.getProperty("MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE")}")
		println("======================================")
	}

}

fun main(args: Array<String>) {
	runApplication<AquariumshopApplication>(*args)
}
