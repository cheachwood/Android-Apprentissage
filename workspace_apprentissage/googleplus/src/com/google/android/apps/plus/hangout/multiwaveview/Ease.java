package com.google.android.apps.plus.hangout.multiwaveview;

import android.animation.TimeInterpolator;

final class Ease
{
  static final class Quad
  {
    public static final TimeInterpolator easeIn = new TimeInterpolator()
    {
      public final float getInterpolation(float paramAnonymousFloat)
      {
        float f = paramAnonymousFloat / 1.0F;
        return 0.0F + f * (1.0F * f);
      }
    };
    public static final TimeInterpolator easeInOut = new TimeInterpolator()
    {
      public final float getInterpolation(float paramAnonymousFloat)
      {
        float f1 = paramAnonymousFloat / 0.5F;
        if (f1 < 1.0F);
        float f2;
        for (float f3 = 0.0F + f1 * (0.5F * f1); ; f3 = 0.0F + -0.5F * (f2 * (f2 - 2.0F) - 1.0F))
        {
          return f3;
          f2 = f1 - 1.0F;
        }
      }
    };
    public static final TimeInterpolator easeOut = new TimeInterpolator()
    {
      public final float getInterpolation(float paramAnonymousFloat)
      {
        float f = paramAnonymousFloat / 1.0F;
        return 0.0F + -1.0F * f * (f - 2.0F);
      }
    };
  }

  static final class Quart
  {
    public static final TimeInterpolator easeIn = new TimeInterpolator()
    {
      public final float getInterpolation(float paramAnonymousFloat)
      {
        float f = paramAnonymousFloat / 1.0F;
        return 0.0F + f * (f * (f * (1.0F * f)));
      }
    };
    public static final TimeInterpolator easeInOut = new TimeInterpolator()
    {
      public final float getInterpolation(float paramAnonymousFloat)
      {
        float f1 = paramAnonymousFloat / 0.5F;
        if (f1 < 1.0F);
        float f2;
        for (float f3 = 0.0F + f1 * (f1 * (f1 * (0.5F * f1))); ; f3 = 0.0F + -0.5F * (f2 * (f2 * (f2 * f2)) - 2.0F))
        {
          return f3;
          f2 = f1 - 2.0F;
        }
      }
    };
    public static final TimeInterpolator easeOut = new TimeInterpolator()
    {
      public final float getInterpolation(float paramAnonymousFloat)
      {
        float f = paramAnonymousFloat / 1.0F - 1.0F;
        return 0.0F + -1.0F * (f * (f * (f * f)) - 1.0F);
      }
    };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.multiwaveview.Ease
 * JD-Core Version:    0.6.2
 */