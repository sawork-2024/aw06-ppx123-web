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
    .repeat(1, "n") {
      exec(
        http("Products")
          .get("/product")
      ).
      exec(
        http("Carts")
          .get("/carts")
      ).
      exec(
        http("Create Cart")
          .post("/carts")
          .header("Content-Type", "application/json")
          .body(StringBody("""{
                             |           "id": 1,
                             |           "items": []
                             |         }""".stripMargin))
      )
      .exec(
        http("Add Item to Cart")
          .post("/carts/1")
          .header("Content-Type", "application/json")
          .body(StringBody("""{
                             |           "id": 1,
                             |           "amount": 3,
                             |           "product": {
                             |              "id":"10167771016",
                             |              "name":"【清华】Java从入门到精通(第7七版) java语言程序设计电脑编程基础计算机软件开发教程JAVA编程入门零基础自学书籍javascript Java入门经典","price":35.8,"img":"https://img14.360buyimg.com/n7/jfs/t1/176588/14/42623/190268/65defb2cFde50bf31/cef8c393c8e3a58b.jpg",
                             |              "price": 35.8,
                             |              "img":"https://img14.360buyimg.com/n7/jfs/t1/176588/14/42623/190268/65defb2cFde50bf31/cef8c393c8e3a58b.jpg",
                             |              "category":null,
                             |              "quantity":0,
                             |              "stock":0
                             |          }
                             |          }""".stripMargin)).check(bodyString.saveAs("cartResponse"))
      )
      .exec(
        http("Checkout Cart")
          .get("/carts/1/checkout")
          .header("User-Agent", "curl/8.4.0")
          .header("Accept", "*/*")
          .check(status.is(200))
          .check(bodyString.saveAs("checkoutResponse"))
      )

    }


  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpProtocol),
  )

}