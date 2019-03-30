package com.dev.megaloma.mvvmapplication.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.dev.megaloma.mvvmapplication.KahunData
import com.dev.megaloma.mvvmapplication.R
import com.dev.megaloma.mvvmapplication.databinding.MainFragmentBinding
import com.dev.megaloma.mvvmapplication.http.SimpleHttp
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    // 選択した場所名を保持
    private var prefectureName = "東京"
    private var cityName = "小平市"
    private val citySpinnerCode = arrayListOf(R.array.tokyo_city_names, R.array.chiba_city_names)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // xmlリソースを利用（ここではmain_fragment
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        val binding: MainFragmentBinding = MainFragmentBinding.bind(view)
        // Activityが生きている間ViewModelを保持する
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewmodel = viewModel
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val btn = activity!!.findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            Toast.makeText(context,"Click",Toast.LENGTH_SHORT).show()   //("ViewModel Clicked!!")

            val wikiURL = getString(R.string.aws_lambda_url)
            val apiKey  = getString(R.string.aws_api_key)
            // 問い合わせ方法によって最後に調整
            val requestKeyInfo = HashMap<String,String>()
            requestKeyInfo["SOKUTEI_KYOKU_CODE"] = "5131000"

            GlobalScope.launch {
                // Httpレスポンスの受け取り
                val response: String = SimpleHttp.doSimpleHttp(wikiURL, apiKey, requestKeyInfo)
                //JSONオブジェクトの整形（Lambda問い合わせの際の余分な部分をカット
                val json: JsonObject = Gson().fromJson(response,JsonObject::class.java)
                        .get("body").asJsonObject.get("Item").asJsonObject
                Log.d("Json return",json.toString())
                val kahunDataJson: KahunData = Gson().fromJson(json, KahunData::class.java)
                viewModel.setName(kahunDataJson.DATE_TIME)
                viewModel.setCityName(kahunDataJson.PREFECTURES)
            }
        }
    }
}
