package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class n extends FrameLayout
{
  private final TextView A;
  private String[] y = null;
  private final ImageView z;

  public n(Context paramContext)
  {
    super(paramContext);
    this.z = new ImageView(paramContext);
    addView(this.z, new FrameLayout.LayoutParams(-2, -2, 17));
    this.A = new TextView(paramContext);
    addView(this.A, new FrameLayout.LayoutParams(-2, -2, 17));
    bringChildToFront(this.A);
  }

  public final void a(Uri paramUri)
  {
    this.z.setImageURI(paramUri);
  }

  public final void a(String[] paramArrayOfString)
  {
    this.y = paramArrayOfString;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    Paint localPaint = new Paint();
    localPaint.setTextSize(this.A.getTextSize());
    localPaint.setTypeface(this.A.getTypeface());
    int j;
    int k;
    int m;
    String str;
    label62: int n;
    if (this.y != null)
    {
      j = this.y.length;
      k = 0;
      m = 0;
      str = null;
      if (k >= j)
        break label132;
      if (this.y[k] == null)
        break label168;
      n = (int)localPaint.measureText(this.y[k]);
      if ((n > i) || (n < m))
        break label168;
      str = this.y[k];
    }
    while (true)
    {
      k++;
      m = n;
      break label62;
      j = 0;
      break;
      label132: if ((str == null) || (!str.equals(this.A.getText())))
        this.A.setText(str);
      super.onMeasure(paramInt1, paramInt2);
      return;
      label168: n = m;
    }
  }

  public void setGravity(int paramInt)
  {
    this.A.setGravity(paramInt);
  }

  public void setSingleLine()
  {
    this.A.setSingleLine();
  }

  public void setTextColor(int paramInt)
  {
    this.A.setTextColor(paramInt);
  }

  public void setTextSize(int paramInt, float paramFloat)
  {
    this.A.setTextSize(paramInt, paramFloat);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.n
 * JD-Core Version:    0.6.2
 */