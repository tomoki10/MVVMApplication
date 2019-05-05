package com.dev.megaloma.mvvmapplication.ui.main

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*



private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class StatFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var viewModel: StatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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

        //花粉データを設定
        //現在設定中の地域を取得(デフォルトは東京設定)
        val prefer = activity!!.getSharedPreferences("DataSave", AppCompatActivity.MODE_PRIVATE)
                .getInt("city_code",51320100)
        runBlocking {
            GlobalScope.launch {
                setChartData(prefer.toString(), chart)
            }.join()
        }
        return view
    }

    private fun setChartData(cityCode:String, chart: BarChart){

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
        val response: String = SimpleHttp.doSimpleHttp(kahunApiUrl, apiKey, requestKeyInfo)
        //JSONオブジェクトの整形（Lambda問い合わせの際の余分な部分をカット
        Log.d("bar response",response)
        val jsonElement: JsonElement = Gson().fromJson(response, JsonObject::class.java)
                .get("body").asJsonObject.get("Items")
        Log.d("JsonObj",jsonElement.toString())

        val kahunJsonArray = jsonElement.asJsonArray

        //Y軸(左)
        val left = chart.axisLeft
        left.axisMinimum = 0f
        left.axisMaximum = 50f
        left.labelCount = Hour
        left.setDrawTopYLabelEntry(true)

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
        for((index, value) in kahunJsonArray.withIndex()) {
            kahunBarData[index] = Gson().fromJson(value, KahunData::class.java).KAHUN_HISAN
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

        //表示データ取得
        val data = BarData(getBarData(kahunBarData))
        Log.d("BarData",data.toString())
        chart.data = data
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
//        dataSet.setColors(intArrayOf(com.dev.megaloma.mvvmapplication.R.color.material_blue,
//                com.dev.megaloma.mvvmapplication.R.color.material_green,
//                com.dev.megaloma.mvvmapplication.R.color.material_yellow,
//                com.dev.megaloma.mvvmapplication.R.color.material_blue), context)
        bars.add(dataSet)
        return bars
    }


    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                StatFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
        private const val Hour = 24
    }
}
