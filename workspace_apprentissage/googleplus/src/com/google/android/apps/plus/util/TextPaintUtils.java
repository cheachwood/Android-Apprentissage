package com.google.android.apps.plus.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings.System;
import android.text.Layout.Alignment;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.Pair;
import java.util.ArrayList;

public final class TextPaintUtils
{
  private static ContentObserver sFontSizeObserver;
  private static final ArrayList<Pair<TextPaint, Integer>> sTextPaintsAndSizeResIds = new ArrayList();

  public static StaticLayout createConstrainedStaticLayout(TextPaint paramTextPaint, CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    return createConstrainedStaticLayout(paramTextPaint, paramCharSequence, paramInt1, paramInt2, Layout.Alignment.ALIGN_NORMAL);
  }

  public static StaticLayout createConstrainedStaticLayout(TextPaint paramTextPaint, CharSequence paramCharSequence, int paramInt1, int paramInt2, Layout.Alignment paramAlignment)
  {
    int i = Math.max(paramInt1, 0);
    Object localObject;
    if (paramInt2 == 0)
      localObject = "";
    while (true)
    {
      StaticLayout localStaticLayout = new StaticLayout((CharSequence)localObject, paramTextPaint, i, paramAlignment, 1.0F, 0.0F, false);
      do
      {
        return localStaticLayout;
        if (paramInt2 == 1)
        {
          localObject = smartEllipsize(paramCharSequence, paramTextPaint, i, TextUtils.TruncateAt.END);
          break;
        }
        localStaticLayout = new StaticLayout(paramCharSequence, paramTextPaint, i, paramAlignment, 1.0F, 0.0F, false);
      }
      while (localStaticLayout.getLineCount() <= paramInt2);
      int j = localStaticLayout.getLineEnd(paramInt2 - 2);
      SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(paramCharSequence.subSequence(0, j));
      localSpannableStringBuilder.append(smartEllipsize(paramCharSequence.subSequence(j, paramCharSequence.length()), paramTextPaint, i, TextUtils.TruncateAt.END));
      localObject = localSpannableStringBuilder;
    }
  }

  public static void init(Context paramContext)
  {
    if (sFontSizeObserver == null)
    {
      sFontSizeObserver = new ContentObserver(new Handler(Looper.getMainLooper()))
      {
        public final void onChange(boolean paramAnonymousBoolean)
        {
          int i = 0;
          int j = TextPaintUtils.sTextPaintsAndSizeResIds.size();
          while (i < j)
          {
            Pair localPair = (Pair)TextPaintUtils.sTextPaintsAndSizeResIds.get(i);
            ((TextPaint)localPair.first).setTextSize(this.val$res.getDimension(((Integer)localPair.second).intValue()));
            i++;
          }
        }
      };
      paramContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("font_scale"), false, sFontSizeObserver);
    }
  }

  public static StaticLayout layoutBitmapTextLabel(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Bitmap paramBitmap, Rect paramRect, int paramInt5, CharSequence paramCharSequence, Point paramPoint, TextPaint paramTextPaint, boolean paramBoolean)
  {
    paramPoint.set(paramInt1, paramInt2);
    if (paramBitmap != null)
    {
      int n = paramInt5 + paramBitmap.getWidth();
      paramInt3 -= n;
      paramRect.set(paramInt1, paramInt2, paramInt1 + paramBitmap.getWidth(), paramInt2 + paramBitmap.getHeight());
      paramPoint.set(n + paramPoint.x, paramPoint.y);
    }
    CharSequence localCharSequence;
    Object localObject;
    label93: StaticLayout localStaticLayout;
    int i;
    if (paramBoolean)
    {
      localCharSequence = TextUtils.ellipsize(paramCharSequence, paramTextPaint, paramInt3, TextUtils.TruncateAt.END);
      if (paramInt3 > 0)
        break label216;
      localObject = "";
      localStaticLayout = new StaticLayout((CharSequence)localObject, paramTextPaint, Math.max(paramInt3, 0), Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
      i = Math.max(0, localStaticLayout.getHeight());
      if (paramBitmap == null)
        break label223;
    }
    label216: label223: for (int j = paramBitmap.getHeight(); ; j = 0)
    {
      int k = Math.max(i, j);
      if (paramBitmap != null)
        paramRect.offset(0, Math.abs(k - paramBitmap.getHeight()) / 2);
      int m = Math.abs(k - localStaticLayout.getHeight()) / 2;
      paramPoint.set(paramPoint.x, m + paramPoint.y);
      return localStaticLayout;
      localCharSequence = paramCharSequence;
      break;
      localObject = localCharSequence;
      break label93;
    }
  }

  public static void registerTextPaint(TextPaint paramTextPaint, int paramInt)
  {
    sTextPaintsAndSizeResIds.add(new Pair(paramTextPaint, Integer.valueOf(paramInt)));
  }

  public static CharSequence smartEllipsize(CharSequence paramCharSequence, TextPaint paramTextPaint, int paramInt, TextUtils.TruncateAt paramTruncateAt)
  {
    String str = paramCharSequence.toString();
    int i = str.indexOf('\r');
    int j = str.indexOf('\n');
    CharSequence localCharSequence;
    if ((i == -1) && (j == -1))
      localCharSequence = paramCharSequence;
    while (true)
    {
      return TextUtils.ellipsize(localCharSequence, paramTextPaint, paramInt, paramTruncateAt);
      if (i == -1)
        localCharSequence = paramCharSequence.subSequence(0, j);
      else if (j == -1)
        localCharSequence = paramCharSequence.subSequence(0, i);
      else
        localCharSequence = paramCharSequence.subSequence(0, Math.min(i, j));
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.TextPaintUtils
 * JD-Core Version:    0.6.2
 */