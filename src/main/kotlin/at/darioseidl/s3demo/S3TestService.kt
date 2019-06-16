package at.darioseidl.s3demo

import at.darioseidl.s3demo.S3Config.Companion.S3_PROTOCOL_PREFIX
import com.amazonaws.services.s3.AmazonS3
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.cloud.aws.core.io.s3.SimpleStorageResource
import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.WritableResource
import org.springframework.stereotype.Service
import readToString
import writeString
import java.time.Instant
import java.time.format.DateTimeFormatter

/**
 * Service to test the connection to AWS S3 on startup.
 */
@Service
class S3TestService(
        private val amazonS3: AmazonS3,
        private val s3Properties: S3Properties,
        private val resourceLoader: ResourceLoader
) : ApplicationRunner {

    private val bucketName = s3Properties.bucketName

    override fun run(args: ApplicationArguments?) {
        if (s3Properties.testOnStartup) {
            try {
                testOnStartup()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun testOnStartup() {
        val region = amazonS3.regionName

        println( "S3 client using region: $region" )

        if (amazonS3.doesBucketExistV2(bucketName))
            println( "S3 bucket exists: $bucketName" )
        else
            throw IllegalStateException( "S3 bucket doesn't exist: $bucketName" )

        testGetResource()
//        testWriteAndReadResource()
    }

    private fun testGetResource() {
        val location = "$S3_PROTOCOL_PREFIX$bucketName/test.txt"

        val resource = resourceLoader.getResource(location)

        if (resource !is SimpleStorageResource)
            throw IllegalStateException("Expected a SimpleStorageResource, but got $resource")
    }

    private fun testWriteAndReadResource() {
        val location = "$S3_PROTOCOL_PREFIX$bucketName/test.txt"
        val content = DateTimeFormatter.ISO_INSTANT.format(Instant.now())

        val resource = resourceLoader.getResource(location)

        if (resource !is SimpleStorageResource)
            throw IllegalStateException("Expected a SimpleStorageResource, but got $resource")

        println( "Write to $location: $content" )
        val writeResource = resource as WritableResource
        writeResource.outputStream.writeString(content)

        val readResource = resourceLoader.getResource(location)
        val result = readResource.inputStream.readToString()
        println( "Read from $location: $result" )

        if (content != result)
            throw IllegalStateException("Failed to read or write from S3.")
    }
}
