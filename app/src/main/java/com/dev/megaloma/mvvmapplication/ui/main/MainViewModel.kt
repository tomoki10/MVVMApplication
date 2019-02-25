package com.dev.megaloma.mvvmapplication.ui.main

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField

class MainViewModel : ViewModel() {
    var name: ObservableField<String> = ObservableField()

    fun setName(name :String){
        this.name.set(name)
    }

}
