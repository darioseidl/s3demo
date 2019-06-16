package at.darioseidl.s3demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class S3demoApplication

fun main(args: Array<String>) {
	runApplication<S3demoApplication>(*args)
}
