package com.dev.megaloma.mvvmapplication.ui.main

import android.arch.lifecycle.ViewModel
import android.databinding.BindingAdapter
import android.databinding.ObservableField
import android.widget.ImageView

class MainViewModel : ViewModel() {
    var name: ObservableField<String> = ObservableField()
    fun setName(name :String){
        this.name.set(name)
    }
    var cityName: ObservableField<String> = ObservableField()
    fun setCityName(cityName :String){
        this.cityName.set(cityName)
    }

    @BindingAdapter("android:src")
    fun setImageViewResource(imageView: ImageView, res: Int){
        imageView.setImageResource(res)
    }


//    private var _cityName: MutableLiveData<String> = MutableLiveData()
//    val cityName: LiveData<String>
//        get() = _cityName
//    fun setCityName(cityName: String){
//        val update = cityName
//        if(_cityName.value == (update)){
//            return
//        }
//        _cityName.value = update
//    }
}
