package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import com.google.android.apps.plus.R.dimen;

public final class ClickableButton
  implements ClickableItem
{
  private static int sBitmapTextXSpacing;
  private static boolean sClickableButtonInitialized;
  private static int sExtraTextXPadding;
  private static int sTextXPadding;
  private Bitmap mBitmap;
  private boolean mClicked;
  private NinePatchDrawable mClickedBackground;
  private CharSequence mContentDescription;
  private Context mContext;
  private NinePatchDrawable mDefaultBackground;
  private ClickableButtonListener mListener;
  private Rect mRect;
  private StaticLayout mTextLayout;

  public ClickableButton(Context paramContext, Bitmap paramBitmap, NinePatchDrawable paramNinePatchDrawable1, NinePatchDrawable paramNinePatchDrawable2, ClickableButtonListener paramClickableButtonListener, int paramInt1, int paramInt2, CharSequence paramCharSequence)
  {
    this(paramContext, paramBitmap, null, null, paramNinePatchDrawable1, paramNinePatchDrawable2, paramClickableButtonListener, paramInt1, paramInt2, paramCharSequence, false);
  }

  public ClickableButton(Context paramContext, Bitmap paramBitmap, CharSequence paramCharSequence, TextPaint paramTextPaint, NinePatchDrawable paramNinePatchDrawable1, NinePatchDrawable paramNinePatchDrawable2, ClickableButtonListener paramClickableButtonListener, int paramInt1, int paramInt2)
  {
    this(paramContext, null, paramCharSequence, paramTextPaint, paramNinePatchDrawable1, paramNinePatchDrawable2, null, paramInt1, paramInt2, paramCharSequence, false);
  }

  public ClickableButton(Context paramContext, Bitmap paramBitmap, CharSequence paramCharSequence1, TextPaint paramTextPaint, NinePatchDrawable paramNinePatchDrawable1, NinePatchDrawable paramNinePatchDrawable2, ClickableButtonListener paramClickableButtonListener, int paramInt1, int paramInt2, CharSequence paramCharSequence2)
  {
    this(paramContext, paramBitmap, paramCharSequence1, paramTextPaint, paramNinePatchDrawable1, paramNinePatchDrawable2, paramClickableButtonListener, paramInt1, paramInt2, paramCharSequence2, false);
  }

  public ClickableButton(Context paramContext, Bitmap paramBitmap, CharSequence paramCharSequence1, TextPaint paramTextPaint, NinePatchDrawable paramNinePatchDrawable1, NinePatchDrawable paramNinePatchDrawable2, ClickableButtonListener paramClickableButtonListener, int paramInt1, int paramInt2, CharSequence paramCharSequence2, boolean paramBoolean)
  {
    initialize(paramContext);
    this.mContext = paramContext;
    this.mBitmap = paramBitmap;
    this.mDefaultBackground = paramNinePatchDrawable1;
    this.mClickedBackground = paramNinePatchDrawable2;
    this.mListener = paramClickableButtonListener;
    this.mContentDescription = paramCharSequence2;
    int i = paramNinePatchDrawable1.getMinimumWidth();
    int j = paramNinePatchDrawable1.getMinimumHeight();
    int k;
    int n;
    int m;
    label82: int i1;
    if ((this.mBitmap != null) && (paramCharSequence1 != null))
    {
      k = sBitmapTextXSpacing;
      if (paramCharSequence1 != null)
        break label157;
      n = 0;
      m = 0;
      if (paramBitmap != null)
        break label203;
      i1 = 0;
      label89: if (paramBitmap != null)
        break label212;
    }
    label157: label203: label212: for (int i2 = 0; ; i2 = paramBitmap.getHeight())
    {
      this.mRect = new Rect(paramInt1, paramInt2, paramInt1 + Math.max(i, k + (m + i1)) + 2 * getTextXPadding(paramBoolean), paramInt2 + Math.max(j, Math.max(n, i2)));
      return;
      k = 0;
      break;
      m = (int)paramTextPaint.measureText(paramCharSequence1.toString());
      this.mTextLayout = new StaticLayout(paramCharSequence1, paramTextPaint, m, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
      n = this.mTextLayout.getHeight();
      break label82;
      i1 = paramBitmap.getWidth();
      break label89;
    }
  }

  public ClickableButton(Context paramContext, CharSequence paramCharSequence, TextPaint paramTextPaint, NinePatchDrawable paramNinePatchDrawable1, NinePatchDrawable paramNinePatchDrawable2, ClickableButtonListener paramClickableButtonListener, int paramInt1, int paramInt2)
  {
    this(paramContext, null, paramCharSequence, paramTextPaint, paramNinePatchDrawable1, paramNinePatchDrawable2, paramClickableButtonListener, paramInt1, paramInt2, paramCharSequence, false);
  }

  private static int getTextXPadding(boolean paramBoolean)
  {
    if (paramBoolean);
    for (int i = sExtraTextXPadding; ; i = sTextXPadding)
      return i;
  }

  public static int getTotalPadding(Context paramContext, boolean paramBoolean1, boolean paramBoolean2)
  {
    initialize(paramContext);
    return 2 * getTextXPadding(true) + sBitmapTextXSpacing;
  }

  private static void initialize(Context paramContext)
  {
    if (!sClickableButtonInitialized)
    {
      sClickableButtonInitialized = true;
      Resources localResources = paramContext.getResources();
      sTextXPadding = (int)localResources.getDimension(R.dimen.clickable_button_horizontal_spacing);
      sExtraTextXPadding = (int)localResources.getDimension(R.dimen.clickable_button_extra_horizontal_spacing);
      sBitmapTextXSpacing = (int)localResources.getDimension(R.dimen.clickable_button_bitmap_text_x_spacing);
    }
  }

  public final ClickableButton createAbsoluteCoordinatesCopy(int paramInt1, int paramInt2)
  {
    Object localObject;
    if (this.mTextLayout == null)
      localObject = null;
    for (TextPaint localTextPaint = null; ; localTextPaint = this.mTextLayout.getPaint())
    {
      return new ClickableButton(this.mContext, this.mBitmap, (CharSequence)localObject, localTextPaint, this.mDefaultBackground, this.mClickedBackground, null, paramInt1 + this.mRect.left, paramInt2 + this.mRect.top, this.mContentDescription);
      localObject = this.mTextLayout.getText().toString();
    }
  }

  public final void draw(Canvas paramCanvas)
  {
    NinePatchDrawable localNinePatchDrawable;
    int i;
    if (this.mClicked)
    {
      localNinePatchDrawable = this.mClickedBackground;
      if (localNinePatchDrawable != null)
      {
        localNinePatchDrawable.setBounds(this.mRect);
        localNinePatchDrawable.draw(paramCanvas);
      }
      if (this.mBitmap != null)
        break label222;
      i = 0;
      label38: if (this.mTextLayout != null)
        break label233;
    }
    label222: label233: for (int j = 0; ; j = this.mTextLayout.getWidth())
    {
      int k = this.mRect.left + (this.mRect.width() - i - j) / 2;
      if (this.mBitmap != null)
      {
        int n = this.mRect.top + (this.mRect.height() - this.mBitmap.getHeight()) / 2;
        paramCanvas.drawBitmap(this.mBitmap, k, n, null);
        StaticLayout localStaticLayout = this.mTextLayout;
        int i1 = 0;
        if (localStaticLayout != null)
          i1 = sBitmapTextXSpacing;
        k += i1 + i;
      }
      if (this.mTextLayout != null)
      {
        int m = this.mRect.top + (this.mRect.height() - this.mTextLayout.getHeight()) / 2;
        paramCanvas.translate(k, m);
        this.mTextLayout.draw(paramCanvas);
        paramCanvas.translate(-k, -m);
      }
      return;
      localNinePatchDrawable = this.mDefaultBackground;
      break;
      i = this.mBitmap.getWidth();
      break label38;
    }
  }

  public final CharSequence getContentDescription()
  {
    return this.mContentDescription;
  }

  public final Rect getRect()
  {
    return this.mRect;
  }

  public final boolean handleEvent(int paramInt1, int paramInt2, int paramInt3)
  {
    ClickableButtonListener localClickableButtonListener = this.mListener;
    boolean bool = false;
    if (localClickableButtonListener == null);
    while (true)
    {
      return bool;
      if (paramInt3 == 3)
      {
        this.mClicked = false;
        bool = true;
      }
      else
      {
        if (this.mRect.contains(paramInt1, paramInt2))
          break;
        bool = false;
        if (paramInt3 == 1)
        {
          this.mClicked = false;
          bool = false;
        }
      }
    }
    switch (paramInt3)
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      bool = true;
      break;
      this.mClicked = true;
      continue;
      if (this.mClicked)
        this.mListener.onClickableButtonListenerClick(this);
      this.mClicked = false;
    }
  }

  public final void setListener(ClickableButtonListener paramClickableButtonListener)
  {
    this.mListener = paramClickableButtonListener;
  }

  public static abstract interface ClickableButtonListener
  {
    public abstract void onClickableButtonListenerClick(ClickableButton paramClickableButton);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ClickableButton
 * JD-Core Version:    0.6.2
 */