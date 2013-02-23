package com.android.gallery3d.common;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.FloatMath;
import java.io.ByteArrayOutputStream;

public final class BitmapUtils
{
  public static byte[] compressToBytes(Bitmap paramBitmap, int paramInt)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(65536);
    paramBitmap.compress(Bitmap.CompressFormat.JPEG, 95, localByteArrayOutputStream);
    return localByteArrayOutputStream.toByteArray();
  }

  public static int computeSampleSizeLarger(float paramFloat)
  {
    int i = 1;
    int j = (int)FloatMath.floor(1.0F / paramFloat);
    if (j <= i);
    while (true)
    {
      return i;
      if (j <= 8)
        i = Utils.prevPowerOf2(j);
      else
        i = 8 * (j / 8);
    }
  }

  private static Bitmap.Config getConfig(Bitmap paramBitmap)
  {
    Bitmap.Config localConfig = paramBitmap.getConfig();
    if (localConfig == null)
      localConfig = Bitmap.Config.ARGB_8888;
    return localConfig;
  }

  public static Bitmap resizeAndCropCenter(Bitmap paramBitmap, int paramInt, boolean paramBoolean)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    if ((i == paramInt) && (j == paramInt));
    while (true)
    {
      return paramBitmap;
      float f = paramInt / Math.min(i, j);
      Bitmap localBitmap = Bitmap.createBitmap(paramInt, paramInt, getConfig(paramBitmap));
      int k = Math.round(f * paramBitmap.getWidth());
      int m = Math.round(f * paramBitmap.getHeight());
      Canvas localCanvas = new Canvas(localBitmap);
      localCanvas.translate((paramInt - k) / 2.0F, (paramInt - m) / 2.0F);
      localCanvas.scale(f, f);
      localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, new Paint(6));
      paramBitmap.recycle();
      paramBitmap = localBitmap;
    }
  }

  public static Bitmap resizeDownBySideLength(Bitmap paramBitmap, int paramInt, boolean paramBoolean)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    float f = Math.min(paramInt / i, paramInt / j);
    if (f >= 1.0F);
    while (true)
    {
      return paramBitmap;
      int k = Math.round(f * paramBitmap.getWidth());
      int m = Math.round(f * paramBitmap.getHeight());
      if ((k != paramBitmap.getWidth()) || (m != paramBitmap.getHeight()))
      {
        Bitmap localBitmap = Bitmap.createBitmap(k, m, getConfig(paramBitmap));
        Canvas localCanvas = new Canvas(localBitmap);
        localCanvas.scale(f, f);
        localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, new Paint(6));
        paramBitmap.recycle();
        paramBitmap = localBitmap;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.gallery3d.common.BitmapUtils
 * JD-Core Version:    0.6.2
 */