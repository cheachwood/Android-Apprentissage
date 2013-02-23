package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class MediaOverlaySurfaceView extends SurfaceView
{
  public MediaOverlaySurfaceView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setZOrderMediaOverlay(true);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.MediaOverlaySurfaceView
 * JD-Core Version:    0.6.2
 */