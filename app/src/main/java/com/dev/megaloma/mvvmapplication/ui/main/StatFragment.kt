package com.dev.megaloma.mvvmapplication.ui.main

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dev.megaloma.mvvmapplication.KahunData
import com.dev.megaloma.mvvmapplication.R
import com.dev.megaloma.mvvmapplication.source.SimpleHttp
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

class StatFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    //private lateinit var viewModel: StatViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.stat_fragment, container, false)

//        val binding: StatFragmentBinding = StatFragmentBinding.bind(view)
//          // Activityが生きている間ViewModelを保持する
//        val mViewModel = ViewModelProviders.of(this).get(StatViewModel::class.java)
//        binding.setLifecycleOwner(this)
//        binding.viewmodel = mViewModel
        val chart = view.findViewById<BarChart>(R.id.chart1)
        chart.setNoDataText(getString(R.string.ClickMe))

        //広告用
        val adView: AdView = view.findViewById(R.id.adView2)
        val adRequest = AdRequest.Builder()
                //.addTestDevice(getString(R.string.ad_test_device))
                .build()
        adView.loadAd(adRequest)

        //花粉データを設定
        //現在設定中の地域を取得(デフォルトは東京設定)
        val prefer = activity!!.getSharedPreferences("DataSave", AppCompatActivity.MODE_PRIVATE)
                .getInt("city_code",51320100)
        val statTitleText = view.findViewById<TextView>(R.id.stat_title_text)
        runBlocking {
            GlobalScope.launch {
                setChartData(prefer.toString(), chart, statTitleText)
            }.join()
        }
        return view
    }

    private fun setChartData(cityCode:String, chart: BarChart, statTitleText: TextView){

        //当日分の花粉データ取得
        val kahunApiUrl = getString(R.string.aws_lambda_url)
        val apiKey  = getString(R.string.aws_api_key)
        // 問い合わせ方法によって最後に調整
        val requestKeyInfo = HashMap<String,String>()
        requestKeyInfo["SOKUTEI_KYOKU_CODE"] = cityCode
        requestKeyInfo["DATE_TIME"] = AccessTimeUtils
                .getRequestDateTime(Calendar.getInstance(Locale.JAPAN))
        // 複数取得
        requestKeyInfo["SINGLE_MULTIPLE_FLAG"] = "1"

        // Httpレスポンスの受け取り
        val responsePair: Pair<String,String> = SimpleHttp.doSimpleHttp(kahunApiUrl, apiKey, requestKeyInfo)
        val response :String = responsePair.first
        val responseCode :String = responsePair.second
        //JSONオブジェクトの整形（Lambda問い合わせの際の余分な部分をカット
        Log.d("bar response",response)
        val jsonElement: JsonElement = Gson().fromJson(response, JsonObject::class.java)
                .get("body").asJsonObject.get("Items")
        Log.d("JsonObj",jsonElement.toString())

        val kahunJsonArray = jsonElement.asJsonArray

        //Y軸(左)
        val left = chart.axisLeft
        left.axisMinimum = 0f
        left.axisMaximum = 500f
        left.labelCount = Hour
        left.setDrawTopYLabelEntry(true)
        // 整数表記の記載がほしい

        //Y軸(右)
        val right = chart.axisRight
        right.setDrawLabels(false)
        right.setDrawGridLines(false)
        right.setDrawZeroLine(true)
        right.setDrawTopYLabelEntry(true)

        //X軸
        val xAxis = chart.xAxis
        //X軸に表示するLabelのリスト(最初の""は原点の位置)
        val labels= arrayOfNulls<String>(25)
        for (labelNum in 0 .. Hour){
            labels[labelNum] = (labelNum+1).toString()
        }

        val kahunBarData = Array(Hour) { 0.toString()}

        if(responseCode == "200"){
            for(value in kahunJsonArray) {
                //時刻ごとに格納(hhが1~24表記なので-1で補正)
                val kahunTmp = Gson().fromJson(value, KahunData::class.java)
                kahunBarData[kahunTmp.DATE_TIME.substring(8,10).toInt()-1]=kahunTmp.KAHUN_HISAN
            }

            //表示データ取得
            val data = BarData(getBarData(kahunBarData))
            Log.d("BarData",data.toString())
            chart.data = data
            //タイトルの設定
            val kahunData = Gson().fromJson(kahunJsonArray[0], KahunData::class.java)
            statTitleText.text =getString(R.string.stat_title,
                    kahunData.PREFECTURES,
                    kahunData.CITY,
                    kahunData.DATE_TIME.substring(0,4),
                    kahunData.DATE_TIME.substring(4,6),
                    kahunData.DATE_TIME.substring(6,8)
            )
        }else{
            statTitleText.text = "データ取得に失敗しました"
        }

        xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        val bottomAxis = chart.xAxis
        bottomAxis.position = XAxis.XAxisPosition.BOTTOM
        bottomAxis.setDrawLabels(true)
        bottomAxis.setDrawGridLines(false)
        bottomAxis.setDrawAxisLine(true)

        //グラフ上の表示
        chart.setDrawValueAboveBar(true)
        chart.description.isEnabled = false
        chart.isClickable = false

        //凡例
        chart.legend.isEnabled = false
        chart.setScaleEnabled(false)

    }

    //棒グラフのデータを取得
    private fun getBarData(kahunBarData: Array<String>):List<IBarDataSet> {
        val entries = ArrayList<BarEntry>()

        for((index, value) in kahunBarData.withIndex()) {
            entries.add(BarEntry(index.toFloat(),value.toFloat()))
        }
        val bars = ArrayList<IBarDataSet>()
        val dataSet = BarDataSet(entries, "bar")
        //ハイライトさせない
        dataSet.isHighlightEnabled = false

        //Barの色をセット
        val colors = intArrayOf(R.color.material_green)
        dataSet.setColors(colors,context)
        bars.add(dataSet)
        return bars
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private const val Hour = 24
    }
}
//整数表記用
//class MyAxis:IndexAxisValueFormatter {
//    override fun getFormattedValue(value: Float): String {
//        return value.toInt().toString()
//    }
//}
