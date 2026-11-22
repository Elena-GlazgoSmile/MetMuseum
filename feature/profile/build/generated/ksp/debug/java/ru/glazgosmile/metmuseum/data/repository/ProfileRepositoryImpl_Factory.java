package ru.glazgosmile.metmuseum.data.repository;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class ProfileRepositoryImpl_Factory implements Factory<ProfileRepositoryImpl> {
  private final Provider<Context> contextProvider;

  private ProfileRepositoryImpl_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ProfileRepositoryImpl get() {
    return newInstance(contextProvider.get());
  }

  public static ProfileRepositoryImpl_Factory create(Provider<Context> contextProvider) {
    return new ProfileRepositoryImpl_Factory(contextProvider);
  }

  public static ProfileRepositoryImpl newInstance(Context context) {
    return new ProfileRepositoryImpl(context);
  }
}
