package com.google.android.apps.plus.fragments;

import com.google.android.apps.plus.phone.StreamOneUpActivity.OnScreenListener;

public abstract interface StreamOneUpCallbacks
{
  public abstract void addScreenListener(StreamOneUpActivity.OnScreenListener paramOnScreenListener);

  public abstract void toggleFullScreen();
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.StreamOneUpCallbacks
 * JD-Core Version:    0.6.2
 */