package com.google.android.apps.plus.phone;

public abstract interface Pageable
{
  public abstract int getCurrentPage();

  public abstract boolean hasMore();

  public abstract boolean isDataSourceLoading();

  public abstract void loadMore();

  public abstract void setLoadingListener(LoadingListener paramLoadingListener);

  public static abstract interface LoadingListener
  {
    public abstract void onDataSourceLoading(boolean paramBoolean);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.Pageable
 * JD-Core Version:    0.6.2
 */