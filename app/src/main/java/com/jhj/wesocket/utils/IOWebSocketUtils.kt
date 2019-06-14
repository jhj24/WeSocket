package com.jhj.wesocket.utils

import io.socket.client.IO
import io.socket.client.Socket
import java.util.*

object IOWebSocketUtils {

    fun connect(url: String) {
        //连接
        val socket = IO.socket(url)
        socket
            .on(Socket.EVENT_CONNECT) {
                //登录
                socket.emit("login", "")
                startTask()
            }
            .on("new_msg") {
            }
            .on(Socket.EVENT_MESSAGE) {
                //接收消息，字段需要和后台约定
            }
            .on(Socket.EVENT_DISCONNECT) {
            }
        socket.connect()
    }

    private fun startTask() {
        val mTimer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                if (OKWebSocketUtils.mWebSocket == null) return
                OKWebSocketUtils.msgCount++
                val isSuccessed =
                    OKWebSocketUtils.mWebSocket?.send("msg" + OKWebSocketUtils.msgCount + "-" + System.currentTimeMillis())
                //除了文本内容外，还可以将如图像，声音，视频等内容转为ByteString发送
                //boolean send(ByteString bytes);
            }
        }
        mTimer.schedule(timerTask, 0, 1000)
    }
}