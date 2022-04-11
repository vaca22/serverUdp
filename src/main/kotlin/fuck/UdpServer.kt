package fuck

import fuck.UdpServer.channel
import fuck.UdpServer.initUdp
import fuck.UdpServer.startListen
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

object UdpServer {
    private val buf: ByteBuffer = ByteBuffer.allocate(600)
    private val bufReceive: ByteBuffer = ByteBuffer.allocate(600)
    val byteArray=ByteArray(500){
        0.toByte()
    }
    val localPort =53
    lateinit var channel: DatagramChannel
    fun initUdp() {
        try {
            channel = DatagramChannel.open();
            channel.socket().bind(InetSocketAddress(localPort));
        } catch (e: IOException) {

            e.printStackTrace();
        }
    }


    fun bytebuffer2ByteArray(buffer: ByteBuffer): ByteArray? {
        buffer.flip()
        val len = buffer.limit() - buffer.position()
        val bytes = ByteArray(len)
        for (i in bytes.indices) {
            bytes[i] = buffer.get()
        }
        return bytes
    }



    fun ip2String(s: InetAddress):String{
        var ip=s.toString()
        ip=ip.substring(ip.lastIndexOf("/")+1)
        return ip
    }

    fun byteArray2String(byteArray: ByteArray): String {
        var fuc = ""
        for (b in byteArray) {
            val st = String.format("%02X", b)
            fuc += ("$st  ");
        }
        return fuc
    }
    fun startListen() {
        while (true) {
            try {
                bufReceive.clear()
                val sourceAddress=channel.receive(bufReceive) as InetSocketAddress
                val sip=ip2String(sourceAddress.address)
                val sport=sourceAddress.port
              //  System.out.println("fuckgaga"+"$sip    $sport")

                val gg= bytebuffer2ByteArray(bufReceive)!!
                val gg2=DNSHeader(gg)
                System.out.println(byteArray2String(gg))
//                if(gg2.qr== DNS_QR_QUERY&&gg2.opcode== 0){
                    System.out.println("fuc")
                    gg[2]=129.toByte();
                    gg[6]=gg[4]
                    gg[7]=gg[5]
                    val ll=gg.size
                    val ggx=ByteArray(gg.size+16){
                        0
                    }
                    for(k in gg.indices){
                        ggx[k]=gg[k]
                    }

                    ggx[ll]= 192.toByte()
                    ggx[ll+1]=12.toByte()
                    ggx[ll+2]=0.toByte()
                    ggx[ll+3]=1.toByte()
                    ggx[ll+4]=0.toByte()
                    ggx[ll+5]=1.toByte()
                    ggx[ll+6]=60.toByte()
                    ggx[ll+7]=0.toByte()
                    ggx[ll+8]=0.toByte()
                    ggx[ll+9]=0.toByte()
                    ggx[ll+10]=0.toByte()
                    ggx[ll+11]=4.toByte()
                    ggx[ll+12]=127.toByte()
                    ggx[ll+13]=0.toByte()
                    ggx[ll+14]=0.toByte()
                    ggx[ll+15]=1.toByte()
                    send2Destination2(ggx,sip,sport)
//                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



    fun send2Destination(message: String, ip: String, port: Int) {
        try {
            val buf: ByteBuffer = ByteBuffer.allocate(600)
            val configInfo = message.toByteArray()
            buf.clear()
            buf.put(configInfo)
            buf.flip()
            channel.send(buf, InetSocketAddress(ip, port))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun send2Destination2(message: ByteArray, ip: String, port: Int) {
        try {
            val buf: ByteBuffer = ByteBuffer.allocate(600)
            buf.clear()
            buf.put(message)
            buf.flip()
            channel.send(buf, InetSocketAddress(ip, port))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(InterruptedException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        System.out.println("fuck")
        initUdp()
        Thread{
            startListen()
        }.start()

        while (true) {
            Thread.sleep(100)
         send2Destination("好吃又好玩 夫人", "192.168.6.112", 8888)
        }
    }
}