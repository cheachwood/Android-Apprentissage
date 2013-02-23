package com.google.android.apps.plus.views;

import android.graphics.Rect;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;

public class PositionedStaticLayout extends StaticLayout
{
  protected Rect mContentArea = new Rect();

  public PositionedStaticLayout(CharSequence paramCharSequence, TextPaint paramTextPaint, int paramInt, Layout.Alignment paramAlignment, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    super(paramCharSequence, paramTextPaint, paramInt, paramAlignment, paramFloat1, paramFloat2, paramBoolean);
  }

  public final int getBottom()
  {
    return this.mContentArea.bottom;
  }

  public final int getLeft()
  {
    return this.mContentArea.left;
  }

  public final int getRight()
  {
    return this.mContentArea.right;
  }

  public final int getTop()
  {
    return this.mContentArea.top;
  }

  public final void setPosition(int paramInt1, int paramInt2)
  {
    this.mContentArea.set(paramInt1, paramInt2, paramInt1 + getWidth(), paramInt2 + getHeight());
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PositionedStaticLayout
 * JD-Core Version:    0.6.2
 */