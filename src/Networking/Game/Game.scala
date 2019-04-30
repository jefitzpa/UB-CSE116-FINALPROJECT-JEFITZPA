package Networking.Game

import Networking._
import play.api.libs.json.{JsValue, Json}

class Game {

  var players: Set[Player] = Set()
  var coins: Set[Coin] = Set()

  def CoinFound(plyId: String, CoinId: Int): Unit = {

  }

  def AddUser(username: String, id: String): Player = {
    val location = List(400,300)
    var userName = username

    if (userName == ""){
      userName = "user" + id
    }

    val user = new Player(userName, location, id, 0)
    Database.AddPlayer(user)
    this.players += user
    user
  }

  def RemovePlayer(id: String): Unit = {
    Database.RemovePlayer(id)
  }

  def PopulateMap(): Unit = {
    val r = scala.util.Random

    //code to randomize coin locations

    val location: List[Int] = List(r.nextInt(750), r.nextInt(550))
    for (coin <- 0 to 100){
      Database.AddCoin(new Coin(location, coin.toString))
    }
  }

  def toJson(): JsValue = {
    val playerMap: Map[String, Map[String, String]] = Map()
    for (player <- players){
      playerMap(player.userId) -> Map("username" -> player.username,
        "coins" -> player.Coins,
        "x" -> player.location.head, "y" -> player.location.tail.head)
    }

    val coinMap: Map[String, Map[String, String]] = Map()
    for (coin <- coins){
      coinMap(coin.id) -> Map("x" -> coin.location.head,
      "y" -> coin.location.tail.head)
    }

    val gs: Map[String, JsValue] = Map(
      "players" -> Json.toJson(playerMap),
      "coins" -> Json.toJson(coinMap)
    )

    Json.toJsObject(gs)
  }


}
