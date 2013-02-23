package com.google.android.apps.plus.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class DummyCardView extends StreamCardView
{
  public DummyCardView(Context paramContext)
  {
    this(paramContext, null);
  }

  public DummyCardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  protected final int draw(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    drawAuthorImage$494937f0(paramCanvas);
    int i = paramInt2 + sAvatarSize;
    return Math.max(drawAuthorName(paramCanvas, paramInt1 + sAvatarSize, paramInt2), i);
  }

  protected final int layoutElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    setAuthorImagePosition(paramInt1, paramInt2);
    return createNameLayout$4868d301(paramInt2, paramInt3 - sAvatarSize);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.DummyCardView
 * JD-Core Version:    0.6.2
 */