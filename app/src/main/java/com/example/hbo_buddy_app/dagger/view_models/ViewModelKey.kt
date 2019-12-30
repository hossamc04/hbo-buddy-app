package com.example.hbo_buddy_app.dagger.view_models

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

//Don't change
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value : KClass<out ViewModel>)