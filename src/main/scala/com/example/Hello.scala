package com.example

import com.treasuredata.client.TDClient
import com.treasuredata.client.model.TDDatabase
import scala.collection.JavaConversions._

object Hello {
  def main(args: Array[String]): Unit = {
    val client: TDClient = TDClient.newClient()
    val list: java.util.List[TDDatabase] = client.listDatabases()
    for(databases <- list) {
      println("database: " + databases.getName)
      for (tables <- client.listTables(databases.getName)) {
        println("table: " + tables)
      }
    }

    // Never forget to close the TDClient.
    // Program won't stop if client.close doesn't exist.
    client.close()
  }
}
