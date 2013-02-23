package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.service.ImageResourceManager;
import com.google.android.apps.plus.service.Resource;
import com.google.android.apps.plus.service.ResourceConsumer;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.LinksRenderUtils;
import com.google.android.apps.plus.util.ViewUtils;

public class OneUpLinkView extends View
  implements ResourceConsumer
{
  private static Interpolator sDecelerateInterpolator;
  protected static Bitmap sLinkBitmap;
  protected static int sMaxWidth;
  private static int sMinExposureLand;
  private static int sMinExposurePort;
  private static boolean sOneUpLinkViewInitialized;
  protected static final Paint sResizePaint = new Paint(2);
  protected int mAvailableContentHeight;
  protected Rect mBackgroundDestRect;
  protected Rect mBackgroundSrcRect;
  protected ClickableItem mCurrentClickableItem;
  protected ClickableButton mDeepLinkButton;
  protected String mDeepLinkLabel;
  protected ClickableButton.ClickableButtonListener mDeepLinkListener;
  protected boolean mHasSeenImage;
  protected Rect mImageBorderRect;
  protected int mImageDimension;
  protected Rect mImageRect;
  protected Resource mImageResource;
  protected Rect mImageSourceRect;
  protected String mLinkTitle;
  protected StaticLayout mLinkTitleLayout;
  protected String mLinkUrl;
  protected StaticLayout mLinkUrlLayout;
  protected BackgroundViewLoadedListener mListener;
  protected MediaRef mMediaRef;
  protected int mType;

  public OneUpLinkView(Context paramContext)
  {
    this(paramContext, null);
  }

  public OneUpLinkView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public OneUpLinkView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (!sOneUpLinkViewInitialized)
    {
      sOneUpLinkViewInitialized = true;
      Resources localResources = paramContext.getResources();
      sLinkBitmap = ImageUtils.decodeResource(paramContext.getResources(), R.drawable.ic_metadata_link);
      sMaxWidth = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_list_max_width);
      sMinExposureLand = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_list_min_height_land);
      sMinExposurePort = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_list_min_height_port);
    }
    this.mBackgroundSrcRect = new Rect();
    this.mBackgroundDestRect = new Rect();
    this.mImageRect = new Rect();
    this.mImageBorderRect = new Rect();
    this.mImageSourceRect = new Rect();
  }

  public static void onStart()
  {
  }

  public static void onStop()
  {
  }

  public final void bindResources()
  {
    if ((ViewUtils.isViewAttached(this)) && (this.mMediaRef != null) && (this.mImageDimension != 0))
      this.mImageResource = ImageResourceManager.getInstance(getContext()).getMedia(this.mMediaRef, this.mImageDimension, this.mImageDimension, 0, this);
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    int i;
    int j;
    int k;
    boolean bool;
    if ((this.mDeepLinkListener != null) && (this.mDeepLinkButton != null))
    {
      i = 1;
      j = (int)paramMotionEvent.getX();
      k = (int)paramMotionEvent.getY();
      int m = paramMotionEvent.getAction();
      bool = false;
      switch (m)
      {
      case 2:
      default:
      case 0:
      case 1:
      case 3:
      }
    }
    while (true)
    {
      return bool;
      i = 0;
      break;
      if ((i != 0) && (this.mDeepLinkButton.handleEvent(j, k, 0)))
      {
        this.mCurrentClickableItem = this.mDeepLinkButton;
        invalidate();
      }
      bool = true;
      continue;
      this.mCurrentClickableItem = null;
      if ((i != 0) && (this.mDeepLinkButton.handleEvent(j, k, 1)));
      for (int n = 1; ; n = 0)
      {
        invalidate();
        bool = false;
        if (n != 0)
          break;
        performClick();
        bool = false;
        break;
      }
      ClickableItem localClickableItem = this.mCurrentClickableItem;
      bool = false;
      if (localClickableItem != null)
      {
        this.mCurrentClickableItem.handleEvent(j, k, 3);
        this.mCurrentClickableItem = null;
        invalidate();
        bool = true;
      }
    }
  }

  protected int getMinExposureLand()
  {
    return sMinExposureLand;
  }

  protected int getMinExposurePort()
  {
    return sMinExposurePort;
  }

  public final void init(MediaRef paramMediaRef, int paramInt, BackgroundViewLoadedListener paramBackgroundViewLoadedListener, String paramString1, String paramString2, ClickableButton.ClickableButtonListener paramClickableButtonListener, String paramString3)
  {
    this.mLinkTitle = paramString1;
    this.mDeepLinkLabel = paramString2;
    this.mDeepLinkListener = paramClickableButtonListener;
    this.mLinkUrl = paramString3;
    this.mMediaRef = paramMediaRef;
    this.mType = paramInt;
    this.mListener = paramBackgroundViewLoadedListener;
    if ((this.mMediaRef != null) && (Build.VERSION.SDK_INT >= 12))
      setAlpha(0.001F);
    requestLayout();
    invalidate();
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    bindResources();
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    unbindResources();
  }

  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i;
    int j;
    label61: int k;
    label71: int m;
    label81: int n;
    if (this.mImageResource == null)
    {
      paramCanvas.drawPaint(LinksRenderUtils.getAppInviteTopAreaBackgroundPaint());
      i = this.mImageRect.width();
      if ((this.mLinkTitleLayout == null) && (this.mLinkUrlLayout == null))
        break label332;
      if (this.mLinkTitleLayout == null)
        break label264;
      j = (int)this.mLinkTitleLayout.getPaint().descent();
      if (this.mLinkTitleLayout != null)
        break label293;
      k = 0;
      if (this.mDeepLinkButton != null)
        break label305;
      m = 0;
      if (this.mLinkUrlLayout != null)
        break label320;
      n = 0;
    }
    label91: for (int i1 = j + (this.mAvailableContentHeight - k - m - n) / 2; ; i1 = 0)
    {
      LinksRenderUtils.drawTitleDeepLinkAndUrl(paramCanvas, i, i1, this.mLinkTitleLayout, this.mDeepLinkButton, this.mLinkUrlLayout, sLinkBitmap);
      return;
      Bitmap localBitmap = (Bitmap)this.mImageResource.getResource();
      if (localBitmap == null)
        break;
      if (!this.mHasSeenImage)
      {
        if (Build.VERSION.SDK_INT >= 12)
        {
          if (sDecelerateInterpolator == null)
            sDecelerateInterpolator = new DecelerateInterpolator();
          animate().alpha(1.0F).setDuration(500L).setInterpolator(sDecelerateInterpolator);
        }
        this.mHasSeenImage = true;
      }
      if (this.mImageSourceRect.isEmpty())
      {
        LinksRenderUtils.createImageSourceRect(localBitmap, this.mImageSourceRect);
        LinksRenderUtils.createBackgroundSourceRect(localBitmap, this.mBackgroundDestRect, this.mBackgroundSrcRect);
      }
      LinksRenderUtils.drawBitmap(paramCanvas, localBitmap, this.mImageSourceRect, this.mBackgroundSrcRect, this.mBackgroundDestRect, this.mImageRect, this.mImageBorderRect);
      break;
      if (this.mDeepLinkButton != null)
      {
        j = 0;
        break label61;
      }
      j = (int)this.mLinkUrlLayout.getPaint().descent();
      break label61;
      k = this.mLinkTitleLayout.getHeight();
      break label71;
      m = this.mDeepLinkButton.getRect().height();
      break label81;
      n = this.mLinkUrlLayout.getHeight();
      break label91;
    }
  }

  public void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    int i = getMeasuredWidth();
    int j = getMeasuredHeight();
    label118: int k;
    int m;
    if (i <= sMaxWidth)
    {
      this.mAvailableContentHeight = j;
      if (this.mMediaRef == null)
        break label280;
      int n = LinksRenderUtils.getMaxImageDimension();
      if (this.mImageDimension == 0)
      {
        this.mImageDimension = Math.min((int)(i * LinksRenderUtils.getImageMaxWidthPercentage()), Math.min(n, this.mAvailableContentHeight));
        bindResources();
      }
      LinksRenderUtils.createBackgroundDestRect(0, 0, i, j, this.mBackgroundDestRect);
      LinksRenderUtils.createImageRects(this.mAvailableContentHeight, this.mImageDimension, 0, 0, this.mImageRect, this.mImageBorderRect);
      k = i - this.mImageRect.width();
      this.mLinkTitleLayout = LinksRenderUtils.createTitle(this.mLinkTitle, this.mImageDimension, k);
      StaticLayout localStaticLayout = this.mLinkTitleLayout;
      m = 0;
      if (localStaticLayout != null)
        m = 0 + this.mLinkTitleLayout.getHeight();
      if (TextUtils.isEmpty(this.mDeepLinkLabel))
        break label291;
      this.mDeepLinkButton = LinksRenderUtils.createDeepLinkButton(getContext(), this.mDeepLinkLabel, this.mImageRect.right, m, k, this.mDeepLinkListener);
      this.mDeepLinkButton.getRect().height();
    }
    while (true)
    {
      this.mImageSourceRect.setEmpty();
      this.mBackgroundSrcRect.setEmpty();
      return;
      if (getResources().getConfiguration().orientation == 2)
      {
        this.mAvailableContentHeight = (j - getMinExposureLand());
        break;
      }
      this.mAvailableContentHeight = (j - getMinExposurePort());
      break;
      label280: this.mImageDimension = this.mAvailableContentHeight;
      break label118;
      label291: this.mLinkUrlLayout = LinksRenderUtils.createUrl(this.mLinkUrl, this.mImageDimension, k - sLinkBitmap.getWidth(), m);
    }
  }

  public final void onResourceStatusChange$1574fca0(Resource paramResource)
  {
    invalidate();
    if ((paramResource.getStatus() == 1) && (this.mListener != null))
      this.mListener.onBackgroundViewLoaded(this);
  }

  public final void unbindResources()
  {
    if (this.mImageResource != null)
    {
      this.mImageResource.unregister(this);
      this.mImageResource = null;
    }
    this.mImageSourceRect.setEmpty();
  }

  public static abstract interface BackgroundViewLoadedListener
  {
    public abstract void onBackgroundViewLoaded(OneUpLinkView paramOneUpLinkView);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.OneUpLinkView
 * JD-Core Version:    0.6.2
 */