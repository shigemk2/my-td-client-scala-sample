package com.example

import com.treasuredata.client.TDClient
import com.treasuredata.client.model.{TDJobRequest, TDDatabase}
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

    val jobId: String = client.submit(TDJobRequest.newHiveQuery("sample_db", "SELECT v['code'] AS code, COUNT(1) AS cnt FROM www_access GROUP BY v['code']"))
    println(jobId)

    // Never forget to close the TDClient.
    // Program won't stop if client.close doesn't exist.
    client.close()
  }
}
