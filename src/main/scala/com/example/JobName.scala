package com.example

import java.io.InputStream
import java.util.Date
import java.util.zip.GZIPInputStream

import com.google.common.base.Function
import com.treasuredata.client.{ExponentialBackOff, TDClient}
import com.treasuredata.client.model._
import org.msgpack.core.MessagePack

object JobName {
  def main(args: Array[String]): Unit = {
    val client: TDClient = TDClient.newClient()
    val date: Date = new Date()
    val jobId: String = client.startSavedQuery("saved_query", date) // Specify SavedQuery Name

    val backOff: ExponentialBackOff = new ExponentialBackOff()
    var job: TDJobSummary = client.jobStatus(jobId)
    while(!job.getStatus().isFinished()) {
      Thread.sleep(backOff.nextWaitTimeMillis())
      job = client.jobStatus(jobId)
    }

    val jobInfo: TDJob = client.jobInfo(jobId)
    println(jobInfo.getCmdOut())
    println(jobInfo.getStdErr())

    client.jobResult(jobId, TDResultFormat.MESSAGE_PACK_GZ, new Function[InputStream, Int]{
      def apply(input: InputStream): Int = {
        var count = 0
        try {
          val unpacker = MessagePack.newDefaultUnpacker(new GZIPInputStream(input))
          while (unpacker.hasNext()) {
            val array = unpacker.unpackValue().asArrayValue()
            println(array)
            count += 1
          }
        }
        count
      }
    })

    // Never forget to close the TDClient.
    // Program won't stop if client.close doesn't exist.
    client.close()
  }
}
