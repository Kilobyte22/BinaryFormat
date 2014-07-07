package de.kilobyte22.binaryformat.wrapper

import de.kilobyte22.{binaryformat => bf}

object Tag {
  implicit def toTag(value: Array[Byte]) = new bf.BlobTag(value)
  implicit def toTag(value: Int) = new bf.IntTag(value)
  implicit def toTag(value: String) = new bf.StringTag(value)
}
