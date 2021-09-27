package no.kristiania.cardgame

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.common.ConsoleNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import no.kristiania.WrappedResponse
import no.kristiania.cardgame.db.UserRepository
import no.kristiania.cardgame.db.UserService
import no.kristiania.cardgame.dto.CardCommand
import no.kristiania.cardgame.dto.PatchUserDto
import no.kristiania.cardgame.model.Collection
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import wiremock.com.fasterxml.jackson.databind.ObjectMapper
import javax.annotation.PostConstruct

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [(RestAPITest.Companion.Initializer::class)])
internal class RestAPITest {

    @LocalServerPort
    protected var port = 0

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userRepository: UserRepository

    @PostConstruct
    fun init() {
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
        RestAssured.basePath = "/api/user-collections"
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    companion object {
        private lateinit var wiremockServer: WireMockServer

        @BeforeAll
        @JvmStatic
        fun initClass() {
            wiremockServer = WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort().notifier(ConsoleNotifier(true)))
            wiremockServer.start()


            val dto = WrappedResponse(code = 200, data = FakeData.getCollectionDto()).validated()
            val json = ObjectMapper().writeValueAsString(dto)

            wiremockServer.stubFor(
                    WireMock.get(WireMock.urlMatching("/api/cards/collection_.*"))
                            .willReturn(WireMock.aResponse()
                                    .withStatus(200)
                                    .withHeader("Content-Type", "application/json; charset=utf-8")
                                    .withBody(json)))
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            wiremockServer.stop()
        }

        class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
            override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
                TestPropertyValues.of("cardServiceAddress: localhost:${wiremockServer.port()}")
                        .applyTo(configurableApplicationContext.environment)
            }
        }
    }

    @BeforeEach
    fun initTest() {
        userRepository.deleteAll()
    }

    @Test
    fun testGetUser() {
        val id = "1"
        userService.registerNewUser(id)

        given().get("/$id")
                .then()
                .statusCode(200)
    }

    @Test
    fun testCreateUser() {
        val id = "2"

        given().put("/$id")
                .then()
                .statusCode(201)

        assertTrue(userRepository.existsById(id))
    }

    @Test
    fun testBuyCard() {

        val userId = "foo"
        val cardId = "c00"

        given().put("/$userId").then().statusCode(201)

        given().contentType(ContentType.JSON)
                .body(PatchUserDto(CardCommand.BUY_CARD, cardId))
                .patch("/$userId")
                .then()
                .statusCode(200)

        val user = userService.findByIdEager(userId)!!
        assertTrue(user.ownedCards.any { it.cardId == cardId })
    }


    @Test
    fun testOpenPack() {

        val userId = "foo"
        given().auth().basic(userId, "123").put("/$userId").then().statusCode(201)

        val before = userService.findByIdEager(userId)!!
        val totCards = before.ownedCards.sumBy { it.numberOfCopies }
        val totPacks = before.cardPacks
        assertTrue(totPacks > 0)

        given().contentType(ContentType.JSON)
                .body(PatchUserDto(CardCommand.OPEN_PACK))
                .patch("/$userId")
                .then()
                .statusCode(200)

        val after = userService.findByIdEager(userId)!!
        Assertions.assertEquals(totPacks - 1, after.cardPacks)
        Assertions.assertEquals(totCards + UserService.CARDS_PER_PACK,
                after.ownedCards.sumBy { it.numberOfCopies })
    }


    @Test
    fun testMillCard() {

        val userId = "foo"
        given().put("/$userId").then().statusCode(201)

        val before = userRepository.findById(userId).get()
        val coins = before.coins

        given().contentType(ContentType.JSON)
                .body(PatchUserDto(CardCommand.OPEN_PACK))
                .patch("/$userId")
                .then()
                .statusCode(200)

        val between = userService.findByIdEager(userId)!!
        val n = between.ownedCards.sumBy { it.numberOfCopies }


        val cardId = between.ownedCards[0].cardId!!
        given().contentType(ContentType.JSON)
                .body(PatchUserDto(CardCommand.MILL_CARD, cardId))
                .patch("/$userId")
                .then()
                .statusCode(200)

        val after = userService.findByIdEager(userId)!!
        assertTrue(after.coins > coins)
        Assertions.assertEquals(n - 1, after.ownedCards.sumBy { it.numberOfCopies })
    }

}