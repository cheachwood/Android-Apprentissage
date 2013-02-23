package com.google.android.apps.plus.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.content.EventThemeImageRequest;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.api.services.plusi.model.Theme;

public class EventThemeView extends EsImageView
{
  private boolean mImageRequested;
  private String mThemeImageUrl;

  public EventThemeView(Context paramContext)
  {
    super(paramContext);
    setResizeable(true);
  }

  public EventThemeView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setResizeable(true);
  }

  public EventThemeView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setResizeable(true);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if ((paramInt4 - paramInt2 > 0) && (!this.mImageRequested))
    {
      this.mImageRequested = true;
      if (this.mThemeImageUrl != null)
        break label43;
      setRequest(null);
    }
    while (true)
    {
      return;
      label43: if (this.mThemeImageUrl.toLowerCase().endsWith(".gif"))
        setRequest(new EventThemeImageRequest(this.mThemeImageUrl));
      else
        setRequest(new EventThemeImageRequest(ImageUtils.getCenterCroppedAndResizedUrl(getMeasuredWidth(), getMeasuredHeight(), this.mThemeImageUrl)));
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j;
    if (i == 1073741824)
      j = View.MeasureSpec.getSize(paramInt1);
    while (true)
    {
      setMeasuredDimension(j, (int)(j / 3.36F));
      return;
      j = 0;
      if (i == -2147483648)
        j = Math.min(0, View.MeasureSpec.getSize(paramInt1));
    }
  }

  public void onRecycle()
  {
    super.onRecycle();
    this.mThemeImageUrl = null;
  }

  public void setEventTheme(Theme paramTheme)
  {
    setImageUrl(EsEventData.getImageUrl(paramTheme));
  }

  public void setImageUrl(String paramString)
  {
    if (!TextUtils.equals(paramString, this.mThemeImageUrl))
    {
      this.mThemeImageUrl = paramString;
      this.mImageRequested = false;
      requestLayout();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventThemeView
 * JD-Core Version:    0.6.2
 */