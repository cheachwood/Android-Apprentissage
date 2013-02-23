package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.MotionEvent;

public final class MotionEventCompat
{
  static final MotionEventVersionImpl IMPL;

  static
  {
    if (Build.VERSION.SDK_INT >= 5);
    for (IMPL = new EclairMotionEventVersionImpl(); ; IMPL = new BaseMotionEventVersionImpl())
      return;
  }

  public static int findPointerIndex(MotionEvent paramMotionEvent, int paramInt)
  {
    return IMPL.findPointerIndex(paramMotionEvent, paramInt);
  }

  public static int getActionIndex(MotionEvent paramMotionEvent)
  {
    return (0xFF00 & paramMotionEvent.getAction()) >> 8;
  }

  public static int getPointerId(MotionEvent paramMotionEvent, int paramInt)
  {
    return IMPL.getPointerId(paramMotionEvent, paramInt);
  }

  public static float getX(MotionEvent paramMotionEvent, int paramInt)
  {
    return IMPL.getX(paramMotionEvent, paramInt);
  }

  public static float getY(MotionEvent paramMotionEvent, int paramInt)
  {
    return IMPL.getY(paramMotionEvent, paramInt);
  }

  static final class BaseMotionEventVersionImpl
    implements MotionEventCompat.MotionEventVersionImpl
  {
    public final int findPointerIndex(MotionEvent paramMotionEvent, int paramInt)
    {
      if (paramInt == 0);
      for (int i = 0; ; i = -1)
        return i;
    }

    public final int getPointerId(MotionEvent paramMotionEvent, int paramInt)
    {
      if (paramInt == 0)
        return 0;
      throw new IndexOutOfBoundsException("Pre-Eclair does not support multiple pointers");
    }

    public final float getX(MotionEvent paramMotionEvent, int paramInt)
    {
      if (paramInt == 0)
        return paramMotionEvent.getX();
      throw new IndexOutOfBoundsException("Pre-Eclair does not support multiple pointers");
    }

    public final float getY(MotionEvent paramMotionEvent, int paramInt)
    {
      if (paramInt == 0)
        return paramMotionEvent.getY();
      throw new IndexOutOfBoundsException("Pre-Eclair does not support multiple pointers");
    }
  }

  static final class EclairMotionEventVersionImpl
    implements MotionEventCompat.MotionEventVersionImpl
  {
    public final int findPointerIndex(MotionEvent paramMotionEvent, int paramInt)
    {
      return paramMotionEvent.findPointerIndex(paramInt);
    }

    public final int getPointerId(MotionEvent paramMotionEvent, int paramInt)
    {
      return paramMotionEvent.getPointerId(paramInt);
    }

    public final float getX(MotionEvent paramMotionEvent, int paramInt)
    {
      return paramMotionEvent.getX(paramInt);
    }

    public final float getY(MotionEvent paramMotionEvent, int paramInt)
    {
      return paramMotionEvent.getY(paramInt);
    }
  }

  static abstract interface MotionEventVersionImpl
  {
    public abstract int findPointerIndex(MotionEvent paramMotionEvent, int paramInt);

    public abstract int getPointerId(MotionEvent paramMotionEvent, int paramInt);

    public abstract float getX(MotionEvent paramMotionEvent, int paramInt);

    public abstract float getY(MotionEvent paramMotionEvent, int paramInt);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.view.MotionEventCompat
 * JD-Core Version:    0.6.2
 */