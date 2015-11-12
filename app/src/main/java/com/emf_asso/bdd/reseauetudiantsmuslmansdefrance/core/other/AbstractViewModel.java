package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

/**
 * Created by Omar_Desk on 12/11/2015.
 */
public abstract class AbstractViewModel<T> extends BaseObservable {

    @Bindable
    public final ObservableField<T> data = new ObservableField<>();
}
