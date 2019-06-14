package com.jhj.wesocket.utils

import okhttp3.*
import java.util.*

object OKWebSocketUtils {

    var msgCount = 0
    var mWebSocket: WebSocket? = null

    fun connect(wsUrl: String) {
        val client = OkHttpClient.Builder()
            .build()
        //构造request对象
        val request = Request.Builder()
            .url(wsUrl)
            .build()
        //建立连接
        client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                mWebSocket = webSocket
                println("client onOpen")
                System.out.println("client request header:" + response.request().headers())
                System.out.println("client response header:" + response.headers())
                println("client response:$response")
                //开启消息定时发送
                startTask()
            }

            override fun onMessage(webSocket: WebSocket?, text: String?) {
                super.onMessage(webSocket, text)
                println("client onMessage")
                println("message:" + text!!)
            }

            override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
                super.onClosing(webSocket, code, reason)
                println("client onClosing")
                println("code:$code reason:$reason")
            }

            override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
                super.onClosed(webSocket, code, reason)
                println("client onClosed")
                println("code:$code reason:$reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                //出现异常会进入此回调
                println("client onFailure")
                println("throwable:$t")
                println("response:$response")
            }
        })
        client.dispatcher().executorService().shutdown()
    }


    private fun startTask() {
        val mTimer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                if (mWebSocket == null) return
                msgCount++
                val isSuccessed = mWebSocket?.send("msg" + msgCount + "-" + System.currentTimeMillis())
                //除了文本内容外，还可以将如图像，声音，视频等内容转为ByteString发送
                //boolean send(ByteString bytes);
            }
        }
        mTimer.schedule(timerTask, 0, 1000)
    }

}