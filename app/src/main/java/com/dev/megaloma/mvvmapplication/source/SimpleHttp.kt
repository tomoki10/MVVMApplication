package com.dev.megaloma.mvvmapplication.source

import android.util.Log
import okhttp3.*
import org.json.JSONObject

class SimpleHttp{
    companion object SimpleHttp{
        private const val searchCode = "SOKUTEI_KYOKU_CODE"
        private val JSON = MediaType.parse("application/json;charset=utf-8")

        /*
         * 引数のURLでHTTPリクエストを実行し、レスポンスを返す
         * @httpsStr アクセスしたいAPIのURL
         * @apiKey アクセスしたいAPIのKey
         * @requestKeyInfo <取得したい属性名、取得したい値>
         *
         * @return サーバから取得したBodyを返却
         */
        fun doSimpleHttp(httpsStr: String, apiKey: String,
                         requestKeyInfo: Map<String,String>): String {
            val client = OkHttpClient()
            //POSTメソッドの実行を実装
            val jsonObj = JSONObject()
            jsonObj.put(searchCode, requestKeyInfo[searchCode])
            val requestBody: RequestBody = RequestBody
                    .create(JSON,jsonObj.toString())

            val request: Request = Request.Builder()
                    .addHeader("x-api-key",apiKey)
                    .url(httpsStr)
                    .post(requestBody)
                    .build()
            Log.d("Http URL", request.url().toString())
            Log.d("Http Header", request.headers().toString())
            Log.d("Http Body", request.body().toString())

            val res: Response = client.newCall(request).execute()
            val resBody: ResponseBody = res.body()!!
            Log.d("Http Response", res.toString())
            Log.d("Http ResHeader", res.headers().toString())
            Log.d("Http ResBody", res.body().toString())
            return resBody.string()
        }
    }
}