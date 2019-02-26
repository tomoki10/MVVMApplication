package com.dev.megaloma.mvvmapplication.http

import okhttp3.*

class SimpleHttp{
    companion object Factory{
        // 引数のURLでHTTPリクエストを実行し、レスポンスを返す
        fun doSimpleHttp(httpsStr: String): String{
            val client = OkHttpClient()
            val request: Request = Request.Builder().url(httpsStr).get().build()
            val call: Call = client.newCall(request)
            val res: Response = call.execute()
            val resBody: ResponseBody = res.body()!!
            return resBody.string()
        }
    }
}