package com.dev.megaloma.mvvmapplication.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.BindingAdapter
import android.widget.ImageView

class MainViewModel : ViewModel() {

    private var mKahunRyouText: MutableLiveData<String> = MutableLiveData()
    var kahunRyouText: LiveData<String> = mKahunRyouText
    fun setKahunRyouText(kahunRyouText :String){
        mKahunRyouText.postValue(kahunRyouText)
    }


    private var mDate: MutableLiveData<String> = MutableLiveData()
    var date: LiveData<String> = mDate
    fun setDate(date :String){
        mDate.postValue(date)
    }

    private var mPrefectureAndCity: MutableLiveData<String> = MutableLiveData()
    var prefectureAndCityName: LiveData<String> = mPrefectureAndCity
    fun setPrefectureAndCityNameName(prefectureAndCityName :String){
        mPrefectureAndCity.postValue(prefectureAndCityName)
    }

    private var mKahunHisanData: MutableLiveData<String> = MutableLiveData()
    var kahunHisanData: LiveData<String> = mKahunHisanData
    fun setKahunHisanData(kahunHisanData :String){
        mKahunHisanData.postValue(kahunHisanData)
    }

    @BindingAdapter("android:src")
    fun setImageViewResource(imageView: ImageView, res: Int){
        imageView.setImageResource(res)
    }
}
