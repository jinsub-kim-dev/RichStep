package com.erving.richstep

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class RichstepApplication

fun main(args: Array<String>) {
	runApplication<RichstepApplication>(*args)
}
