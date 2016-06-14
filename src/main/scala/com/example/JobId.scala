package com.example

import java.io.InputStream
import java.util.zip.GZIPInputStream

import com.google.common.base.Function
import com.treasuredata.client.TDClient
import com.treasuredata.client.model._
import org.msgpack.core.MessagePack

object JobId {
  def main(args: Array[String]): Unit = {
    val client: TDClient = TDClient.newClient()
    val jobId: String = "1234567890" // Specify Job Id

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
