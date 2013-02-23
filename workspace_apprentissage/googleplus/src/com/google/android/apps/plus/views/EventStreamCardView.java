package com.google.android.apps.plus.views;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.service.EsService;
import com.google.api.services.plusi.model.PlusEvent;
import com.google.api.services.plusi.model.PlusEventJson;

public class EventStreamCardView extends StreamCardView
{
  private EventCardDrawer mDrawer = new EventCardDrawer(this);
  private boolean mIgnoreHeight;

  public EventStreamCardView(Context paramContext)
  {
    this(paramContext, null);
  }

  public EventStreamCardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setPaddingEnabled(false);
  }

  protected final int draw(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = this.mDrawer.draw$680d9d43(paramInt2, paramInt2 + paramInt4 - this.mPlusOneButton.getRect().height(), paramCanvas);
    drawPlusOneBar(paramCanvas);
    drawCornerIcon(paramCanvas);
    return i;
  }

  public final void init(Cursor paramCursor, int paramInt1, int paramInt2, View.OnClickListener paramOnClickListener, ItemClickListener paramItemClickListener, StreamCardView.ViewedListener paramViewedListener, StreamCardView.StreamPlusBarClickListener paramStreamPlusBarClickListener, StreamCardView.StreamMediaClickListener paramStreamMediaClickListener)
  {
    super.init(paramCursor, paramInt1, paramInt2, paramOnClickListener, paramItemClickListener, paramViewedListener, paramStreamPlusBarClickListener, paramStreamMediaClickListener);
    byte[] arrayOfByte = paramCursor.getBlob(13);
    if (arrayOfByte != null);
    for (PlusEvent localPlusEvent = (PlusEvent)PlusEventJson.getInstance().fromByteArray(arrayOfByte); ; localPlusEvent = null)
    {
      this.mDrawer.bind(EsService.getActiveAccount(getContext()), this, localPlusEvent, this.mAuthorGaiaId, this.mAttribution, this.mItemClickListener);
      return;
    }
  }

  protected final int layoutElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    createPlusOneBar(paramInt1 + sXPadding, paramInt4 - sBottomBorderPadding, paramInt3 - sXDoublePadding);
    Rect localRect1 = this.mPlusOneButton.getRect();
    int i = this.mDrawer.layout(paramInt1, paramInt2, this.mIgnoreHeight, paramInt3, paramInt4 - localRect1.height()) + sBottomBorderPadding;
    if (this.mIgnoreHeight)
    {
      int j = paramInt2 + i;
      localRect1.offsetTo(localRect1.left, j);
      if (this.mReshareButton != null)
      {
        Rect localRect3 = this.mReshareButton.getRect();
        localRect3.offsetTo(localRect3.left, j);
      }
      if (this.mCommentsButton != null)
      {
        Rect localRect2 = this.mCommentsButton.getRect();
        localRect2.offsetTo(localRect2.left, j);
      }
      i = localRect1.bottom + sBottomBorderPadding;
    }
    return i;
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mDrawer.attach();
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.mDrawer.detach();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.mIgnoreHeight = shouldWrapContent(paramInt2);
    int k;
    if (this.mIgnoreHeight)
    {
      k = i;
      boolean bool = this.mPaddingEnabled;
      int m = 0;
      int n = 0;
      int i1 = 0;
      int i2 = 0;
      if (bool)
      {
        n = sXPadding;
        i2 = sYPadding;
        m = sXDoublePadding;
        i1 = sYDoublePadding;
      }
      int i3 = layoutElements(n + sLeftBorderPadding, i2 + sTopBorderPadding, i - (m + sLeftBorderPadding + sRightBorderPadding), k - (i1 + sTopBorderPadding + sBottomBorderPadding));
      if (!this.mIgnoreHeight)
        break label181;
      setMeasuredDimension(i, i1 + (i3 + sTopBorderPadding) + sBottomBorderPadding);
    }
    while (true)
    {
      createGraySpamBar(getMeasuredWidth() - sLeftBorderPadding - sRightBorderPadding);
      this.mBackgroundRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
      return;
      k = j;
      break;
      label181: setMeasuredDimension(i, k);
    }
  }

  public void onRecycle()
  {
    super.onRecycle();
    this.mDrawer.clear();
    this.mIgnoreHeight = false;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventStreamCardView
 * JD-Core Version:    0.6.2
 */