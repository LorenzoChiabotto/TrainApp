package com.lorenzoch.trainapp;

import android.support.v4.app.Fragment;

import Data.Ejercicio;

/**
 * Created by loren on 16/04/2018.
 */

public interface INewRutina {

    void addEjercicioToLista(Ejercicio ejercicio);
    void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack, Ejercicio ejercicio);
    void guardarRutina(String nombre);
    void removeEjercicio(Ejercicio ejercicio);
}
