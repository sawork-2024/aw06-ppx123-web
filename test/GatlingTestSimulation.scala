package gatlingtest

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class GatlingTestSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("*/*")
    .userAgentHeader("curl/8.5.0") // 设置User-Agent
    .disableCaching // 禁用缓存以确保每次请求都发送

  val scn = (scenario("Testing prduct"))
    .repeat(10, "n") {
      exec(
        http("Checkout Cart")
          .get("/carts/checkout/1")
          .header("User-Agent", "curl/8.4.0")
          .header("Accept", "*/*")
          .check(status.is(200))
      )

    }


  setUp(
    scn.inject(atOnceUsers(20)).protocols(httpProtocol),
  )

}