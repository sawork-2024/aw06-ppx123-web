package gatlingtest

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class GatlingTestSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080/")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = (scenario("Testing prduct"))
    .repeat(20, "n") {
      exec(
        http("products")
          .get("/product")
      ).
      exec(
        http("carts")
          .get("/carts")
      ).exec(http("Create Cart")
            .post("/carts")
            .body(StringBody("""{"id": 1, "items": []}"""))
            .check(status.is(200))
            .check(jsonPath("$.id").saveAs("cartId"))
        )
        // 向购物车添加商品项
        .exec(http("Add Item to Cart")
            .post("/carts/${cartId}/items")
            .body(StringBody("""{"id": 1, "amount": 1, "product": {"id": "1", "name": "Product 1", "price": 10.0}}"""))
            .check(status.is(200))
        )

    }


  setUp(
    scn.inject(atOnceUsers(200)).protocols(httpProtocol),
  )

}