package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.apps.plus.R.color;

public class StreamOneUpLeftoverView extends View
  implements Recyclable
{
  private static Paint sBackgroundPaint;
  private int mFixedHeight;

  public StreamOneUpLeftoverView(Context paramContext)
  {
    this(paramContext, null);
  }

  public StreamOneUpLeftoverView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, null, 0);
  }

  public StreamOneUpLeftoverView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (sBackgroundPaint == null)
    {
      Resources localResources = getContext().getResources();
      Paint localPaint = new Paint();
      sBackgroundPaint = localPaint;
      localPaint.setColor(localResources.getColor(R.color.stream_one_up_list_background));
      sBackgroundPaint.setStyle(Paint.Style.FILL);
    }
  }

  public final void bind(int paramInt)
  {
    this.mFixedHeight = 0;
    if (paramInt < 0)
      paramInt = 0;
    this.mFixedHeight = paramInt;
    invalidate();
    requestLayout();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = getWidth();
    int j = getHeight();
    paramCanvas.drawRect(0.0F, 0.0F, i, j, sBackgroundPaint);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    setMeasuredDimension(getMeasuredWidth(), this.mFixedHeight);
  }

  public void onRecycle()
  {
    this.mFixedHeight = 0;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.StreamOneUpLeftoverView
 * JD-Core Version:    0.6.2
 */