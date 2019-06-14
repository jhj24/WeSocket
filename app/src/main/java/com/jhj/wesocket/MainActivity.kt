package com.jhj.wesocket

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jhj.wesocket.utils.IOWebSocketUtils
import com.jhj.wesocket.utils.OKWebSocketUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_socket_io.setOnClickListener {
            // val client = IOWebSocketUtils(URI("ws://echo.websocket.org"))
            //val client = IOWebSocketUtils(URI("ws://121.40.165.18:8088"))
            //http://192.168.1.2:2120
            IOWebSocketUtils.connect("http://192.168.1.2:2120")
        }

        btn_socket_ok.setOnClickListener {
            OKWebSocketUtils.connect("ws://echo.websocket.org")
        }
    }
}
