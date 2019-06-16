package at.darioseidl.s3demo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated

/**
 * S3 properties.
 */
@Component
@Validated
@ConfigurationProperties(prefix = "aws.s3")
class S3Properties {

    /** Region name. */
    var regionName: String = "eu-central-1"

    /** S3 bucket name. */
    var bucketName: String = ""

    /** Whether to do a test read/write on S3 on startup. */
    var testOnStartup: Boolean = false
}
