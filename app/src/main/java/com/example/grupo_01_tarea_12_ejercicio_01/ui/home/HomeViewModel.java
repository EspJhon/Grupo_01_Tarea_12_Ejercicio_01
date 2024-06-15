package com.example.grupo_01_tarea_12_ejercicio_01.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("BIENVENIDO");
    }

    public LiveData<String> getText() {
        return mText;
    }
}