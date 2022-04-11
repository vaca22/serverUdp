package fuck
const val DNS_QR_QUERY =0
const val  DNS_QR_RESPONSE= 1
const val  DNS_OPCODE_QUERY= 0
class DNSHeader(byteArray: ByteArray) {
    var id=toUInt(byteArray.copyOfRange(0,2))
    val cc=toUInt(byteArray.copyOfRange(2,4))
//    var rd=toUInt(byteArray.copyOfRange(2,3))
//    var tc=toUInt(byteArray.copyOfRange(3,4))
//    var aa=toUInt(byteArray.copyOfRange(4,5))
//    var opcode=toUInt(byteArray.copyOfRange(5,6))
//    var qr=toUInt(byteArray.copyOfRange(6,7))
//    var rcode=toUInt(byteArray.copyOfRange(7,8))
//    var z=toUInt(byteArray.copyOfRange(8,9))
//    var ra=toUInt(byteArray.copyOfRange(9,10))
    var qdcount=toUInt(byteArray.copyOfRange(4,6))
    var ancount=toUInt(byteArray.copyOfRange(6,8))
    var nscount=toUInt(byteArray.copyOfRange(8,10))
    var arcount=toUInt(byteArray.copyOfRange(10,12))


//    var ancountb=byteArray.copyOfRange(12,14)
//    var qdcountb=byteArray.copyOfRange(10,12)

    fun toUInt(bytes: ByteArray): Int {
        var result = 0
        for ((i, v) in bytes.withIndex()) {
            result += v.unsigned().shl(i * 8)
        }
        return result
    }
    fun Byte.unsigned(): Int = when {
        (toInt() < 0) -> 255 + toInt() + 1
        else -> toInt()
    }
}