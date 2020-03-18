package com.example.coroutines.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coroutines.views.UserDetailViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {
  @Binds
  @IntoMap
  @ViewModelKey(UserDetailViewModel::class)
  abstract fun bindUserDetailViewModel(view: UserDetailViewModel): ViewModel

  @Binds
  abstract fun bindUserDetailViewModelFactory(factory: UserDetailViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Singleton
class UserDetailViewModelFactory @Inject constructor(
  private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    val creator = creators[modelClass] ?: creators.entries.firstOrNull {
      modelClass.isAssignableFrom(it.key)
    }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
    return creator.get() as T
  }
}