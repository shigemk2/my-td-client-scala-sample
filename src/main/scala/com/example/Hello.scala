package com.example

import com.treasuredata.client.TDClient
import com.treasuredata.client.model.TDDatabase
import scala.collection.JavaConversions._

object Hello {
  def main(args: Array[String]): Unit = {
    val client: TDClient = TDClient.newClient()
    val list: java.util.List[TDDatabase] = client.listDatabases()
    for(database <- list) println(database.getName)
    // Never forget to close the TDClient.
    // Program won't stop if client.close doesn't exist.
    client.close()
  }
}
