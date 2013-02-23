package com.google.android.apps.plus.views;

import android.graphics.Rect;

public final class ClickableRect
  implements ClickableItem
{
  private boolean mClicked;
  private CharSequence mContentDescription;
  private ClickableRectListener mListener;
  private Rect mRect;

  public ClickableRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ClickableRectListener paramClickableRectListener, CharSequence paramCharSequence)
  {
    this(new Rect(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4), paramClickableRectListener, paramCharSequence);
  }

  private ClickableRect(Rect paramRect, ClickableRectListener paramClickableRectListener, CharSequence paramCharSequence)
  {
    this.mRect = paramRect;
    this.mListener = paramClickableRectListener;
    this.mContentDescription = paramCharSequence;
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
    int i = 1;
    if (paramInt3 == 3)
      this.mClicked = false;
    while (true)
    {
      return i;
      if (!this.mRect.contains(paramInt1, paramInt2))
      {
        if (paramInt3 == i)
          this.mClicked = false;
        i = 0;
      }
      else
      {
        switch (paramInt3)
        {
        default:
          break;
        case 0:
          this.mClicked = i;
          break;
        case 1:
          if ((this.mClicked) && (this.mListener != null))
            this.mListener.onClickableRectClick$598f98c1();
          this.mClicked = false;
        }
      }
    }
  }

  public static abstract interface ClickableRectListener
  {
    public abstract void onClickableRectClick$598f98c1();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ClickableRect
 * JD-Core Version:    0.6.2
 */