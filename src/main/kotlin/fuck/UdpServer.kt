package fuck

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
    val localPort = 8888
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
    fun startListen() {
        while (true) {
            try {
                bufReceive.clear()
                val sourceAddress=channel.receive(bufReceive) as InetSocketAddress
                val sip=ip2String(sourceAddress.address)
                val sport=sourceAddress.port
                System.out.println("fuckgaga"+"$sip    $sport")


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