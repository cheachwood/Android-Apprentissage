package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.service.Resource;
import com.google.android.apps.plus.service.ResourceConsumer;
import com.google.android.apps.plus.util.TextPaintUtils;
import com.google.android.apps.plus.util.ViewUtils;
import java.util.ArrayList;
import java.util.List;

public abstract class CardView extends View
  implements ResourceConsumer, Recyclable
{
  protected static NinePatchDrawable sBackground;
  protected static int sBottomBorderPadding;
  private static boolean sCardViewInitialized;
  protected static TextPaint sDefaultTextPaint;
  protected static Rect sDrawRect;
  protected static int sLeftBorderPadding;
  protected static Drawable sPressedStateBackground;
  protected static final Paint sResizePaint = new Paint(2);
  protected static int sRightBorderPadding;
  protected static int sTopBorderPadding;
  protected static int sXDoublePadding;
  protected static int sXPadding;
  protected static int sYDoublePadding;
  protected static int sYPadding;
  protected Rect mBackgroundRect;
  private final List<ClickableItem> mClickableItems = new ArrayList();
  private ClickableItem mCurrentClickableItem;
  protected int mDisplaySizeType = -2;
  protected ItemClickListener mItemClickListener;
  private View.OnClickListener mOnClickListener;
  protected boolean mPaddingEnabled = true;

  public CardView(Context paramContext)
  {
    this(paramContext, null);
  }

  public CardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public CardView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (!sCardViewInitialized)
    {
      sCardViewInitialized = true;
      Resources localResources = paramContext.getResources();
      TextPaint localTextPaint = new TextPaint();
      sDefaultTextPaint = localTextPaint;
      localTextPaint.setAntiAlias(true);
      sDefaultTextPaint.setColor(localResources.getColor(R.color.card_default_text));
      sDefaultTextPaint.setTextSize(localResources.getDimension(R.dimen.card_default_text_size));
      sDefaultTextPaint.linkColor = localResources.getColor(R.color.card_link);
      TextPaintUtils.registerTextPaint(sDefaultTextPaint, R.dimen.card_default_text_size);
      sBackground = (NinePatchDrawable)localResources.getDrawable(R.drawable.bg_tacos);
      sPressedStateBackground = localResources.getDrawable(R.drawable.list_selected_holo);
      sLeftBorderPadding = (int)localResources.getDimension(R.dimen.card_border_left_padding);
      sRightBorderPadding = (int)localResources.getDimension(R.dimen.card_border_right_padding);
      sTopBorderPadding = (int)localResources.getDimension(R.dimen.card_border_top_padding);
      sBottomBorderPadding = (int)localResources.getDimension(R.dimen.card_border_bottom_padding);
      int i = (int)localResources.getDimension(R.dimen.card_x_padding);
      sXPadding = i;
      sXDoublePadding = i * 2;
      int j = (int)localResources.getDimension(R.dimen.card_y_padding);
      sYPadding = j;
      sYDoublePadding = j * 2;
      sDrawRect = new Rect();
    }
    this.mBackgroundRect = new Rect();
  }

  public static void onStart()
  {
  }

  public static void onStop()
  {
  }

  protected static boolean shouldWrapContent(int paramInt)
  {
    if (View.MeasureSpec.getMode(paramInt) == 0);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void addClickableItem(ClickableItem paramClickableItem)
  {
    if (paramClickableItem == null);
    while (true)
    {
      return;
      this.mClickableItems.remove(paramClickableItem);
      this.mClickableItems.add(paramClickableItem);
    }
  }

  public final void bindResources()
  {
    if (ViewUtils.isViewAttached(this))
      onBindResources();
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = 1;
    int k = (int)paramMotionEvent.getX();
    int m = (int)paramMotionEvent.getY();
    switch (paramMotionEvent.getAction())
    {
    default:
      i = 0;
    case 0:
    case 1:
    case 3:
    case 2:
    }
    while (true)
    {
      return i;
      for (int i1 = -1 + this.mClickableItems.size(); i1 >= 0; i1--)
      {
        ClickableItem localClickableItem = (ClickableItem)this.mClickableItems.get(i1);
        if (localClickableItem.handleEvent(k, m, 0))
        {
          this.mCurrentClickableItem = localClickableItem;
          invalidate();
          break;
        }
      }
      this.mCurrentClickableItem = null;
      boolean bool = false;
      for (int n = -1 + this.mClickableItems.size(); n >= 0; n--)
        bool |= ((ClickableItem)this.mClickableItems.get(n)).handleEvent(k, m, i);
      invalidate();
      if ((!bool) && (this.mOnClickListener != null))
        this.mOnClickListener.onClick(this);
      int j = 0;
      continue;
      if (this.mCurrentClickableItem != null)
      {
        this.mCurrentClickableItem.handleEvent(k, m, 3);
        this.mCurrentClickableItem = null;
        invalidate();
      }
      else
      {
        j = 0;
        continue;
        j = 0;
      }
    }
  }

  protected abstract int draw(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public void init(Cursor paramCursor, int paramInt1, int paramInt2, View.OnClickListener paramOnClickListener, ItemClickListener paramItemClickListener, StreamCardView.ViewedListener paramViewedListener, StreamCardView.StreamPlusBarClickListener paramStreamPlusBarClickListener, StreamCardView.StreamMediaClickListener paramStreamMediaClickListener)
  {
    this.mDisplaySizeType = paramInt1;
    this.mOnClickListener = paramOnClickListener;
    this.mItemClickListener = paramItemClickListener;
  }

  protected abstract int layoutElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    bindResources();
  }

  protected void onBindResources()
  {
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    onUnbindResources();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = getWidth();
    int j = getHeight();
    int m;
    int i1;
    int k;
    int n;
    if (this.mPaddingEnabled)
    {
      m = sXPadding;
      i1 = sYPadding;
      k = sXDoublePadding;
      n = sYDoublePadding;
    }
    while (true)
    {
      sBackground.setBounds(this.mBackgroundRect);
      sBackground.draw(paramCanvas);
      draw(paramCanvas, m + sLeftBorderPadding, i1 + sTopBorderPadding, i - (k + sLeftBorderPadding + sRightBorderPadding), j - (n + sTopBorderPadding + sBottomBorderPadding));
      return;
      k = 0;
      m = 0;
      n = 0;
      i1 = 0;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    int k;
    int n;
    int i2;
    int m;
    int i1;
    if (j <= 0)
    {
      k = i;
      if (!this.mPaddingEnabled)
        break label120;
      n = sXPadding;
      i2 = sYPadding;
      m = sXDoublePadding;
      i1 = sYDoublePadding;
    }
    while (true)
    {
      setMeasuredDimension(i, k);
      this.mBackgroundRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
      layoutElements(n + sLeftBorderPadding, i2 + sTopBorderPadding, i - (m + sLeftBorderPadding + sRightBorderPadding), k - (i1 + sTopBorderPadding + sBottomBorderPadding));
      return;
      k = j;
      break;
      label120: m = 0;
      n = 0;
      i1 = 0;
      i2 = 0;
    }
  }

  public void onRecycle()
  {
    onUnbindResources();
    this.mClickableItems.clear();
    this.mCurrentClickableItem = null;
    this.mOnClickListener = null;
    this.mItemClickListener = null;
    this.mBackgroundRect.setEmpty();
    clearAnimation();
  }

  public final void onResourceStatusChange$1574fca0(Resource paramResource)
  {
    invalidate();
  }

  protected void onUnbindResources()
  {
  }

  public final void removeClickableItem(ClickableItem paramClickableItem)
  {
    this.mClickableItems.remove(paramClickableItem);
  }

  public void setPaddingEnabled(boolean paramBoolean)
  {
    this.mPaddingEnabled = paramBoolean;
  }

  public final void unbindResources()
  {
    onUnbindResources();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.CardView
 * JD-Core Version:    0.6.2
 */