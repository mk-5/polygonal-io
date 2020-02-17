package pl.mk5.polygonal.e2e

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest
class KotlinE2eApplicationTest(@Autowired val context: ApplicationContext) {
    @Test
    fun `should create context`() {
        assertTrue(context != null)
    }
}