package com.lorenzoch.trainapp;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

import Data.Ejercicio;
import Data.Estadistica;
import Data.EstadisticaEjercicio;

/**
 * Created by loren on 19/04/2018.
 */

public interface IStartRutina {

    void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack, EstadisticaEjercicio stats);
    void inflateFragment(int fragment, EstadisticaEjercicio stat, int i);
    void backToLista(EstadisticaEjercicio stat);
    void reloadToolBar();
}
