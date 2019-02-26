package com.dev.megaloma.mvvmapplication.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.dev.megaloma.mvvmapplication.R
import com.dev.megaloma.mvvmapplication.databinding.MainFragmentBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // xmlリソースを利用（ここではmain_fragment
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        //
        val binding: MainFragmentBinding = MainFragmentBinding.bind(view)
        //ここから
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

            val wikiURL = "https://ja.wikipedia.org/wiki/HTTPS"

            GlobalScope.launch {
                val client = OkHttpClient()
                val request: Request = Request.Builder().url(wikiURL).get().build()
                val call: Call = client.newCall(request)
                val res: Response = call.execute()
                val resBody: ResponseBody = res.body()!!
                viewModel.setName(resBody.string())
            }
        }

    }
}
