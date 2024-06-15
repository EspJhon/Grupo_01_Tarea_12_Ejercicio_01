package com.example.grupo_01_tarea_12_ejercicio_01.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Fragmento de Pedido");
    }

    public LiveData<String> getText() {
        return mText;
    }
}