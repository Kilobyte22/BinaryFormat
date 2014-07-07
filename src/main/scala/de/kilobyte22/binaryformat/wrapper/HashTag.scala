package de.kilobyte22.binaryformat.wrapper

import de.kilobyte22.{binaryformat => bf}
import java.io.{ByteArrayInputStream, DataInputStream, DataOutputStream, ByteArrayOutputStream}
import scala.collection.JavaConversions._

class HashTag extends bf.HashTag {
  def apply(data: (String, bf.Tag)*) = {
    data.foreach {
      case (key, value) => put(key, value)
    }
    this
  }

  def foreach[U](cb: ((String, bf.Tag)) => U) {
    data.foreach[U](cb)
  }
}

object HashTag {
  implicit def toByteArray(value: HashTag) = {
    val bos = new ByteArrayOutputStream
    value.save(new DataOutputStream(bos))
    bos.toByteArray
  }

  implicit def toHashTag(value: Array[Byte]) =
    bf.TagIo.readHashTag(new DataInputStream(new ByteArrayInputStream(value)))

  def apply(data: (String, bf.Tag)*) = new HashTag()(data)
}
