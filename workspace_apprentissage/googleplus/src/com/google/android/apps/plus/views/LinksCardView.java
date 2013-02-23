package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.content.DbEmbedDeepLink;
import com.google.android.apps.plus.content.DbEmbedMedia;
import com.google.android.apps.plus.service.ImageResourceManager;
import com.google.android.apps.plus.service.Resource;
import com.google.android.apps.plus.util.LinksRenderUtils;

public class LinksCardView extends StreamCardView
{
  protected Rect mBackgroundDestRect = new Rect();
  protected Rect mBackgroundSrcRect = new Rect();
  protected String mCreationSource;
  protected DbEmbedDeepLink mDbEmbedAppInvite;
  protected DbEmbedMedia mDbEmbedMedia;
  protected ClickableButton mDeepLinkButton;
  protected Rect mImageBorderRect = new Rect();
  protected int mImageDimension = 0;
  protected Rect mImageRect = new Rect();
  protected Resource mImageResource;
  protected Rect mImageSourceRect = new Rect();
  protected boolean mIsReshare;
  protected StaticLayout mLinkTitleLayout;
  protected String mLinkUrl;
  protected StaticLayout mLinkUrlLayout;
  protected MediaRef mMediaRef;

  public LinksCardView(Context paramContext)
  {
    this(paramContext, null);
  }

  public LinksCardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public static String makeLinkUrl(String paramString)
  {
    String str2;
    if (!TextUtils.isEmpty(paramString))
    {
      str2 = Uri.parse(paramString).getHost();
      if (!TextUtils.isEmpty(str2))
        if (str2.startsWith("www."))
          str2 = str2.substring(4);
    }
    for (String str1 = str2.toLowerCase(); ; str1 = null)
      return str1;
  }

  protected final int draw(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Bitmap localBitmap;
    boolean bool;
    label25: label43: int i;
    int j;
    int k;
    label186: int m;
    label206: int n;
    label216: int i1;
    label226: int i2;
    if (this.mImageResource == null)
    {
      localBitmap = null;
      if (this.mMediaRef == null)
        break label333;
      if (localBitmap == null)
        break label327;
      bool = true;
      drawMediaTopAreaStage(paramCanvas, paramInt3, paramInt4, bool, this.mBackgroundDestRect, LinksRenderUtils.getLinksTopAreaBackgroundPaint());
      if (localBitmap != null)
      {
        if (this.mImageSourceRect.isEmpty())
        {
          LinksRenderUtils.createImageSourceRect(localBitmap, this.mImageSourceRect);
          LinksRenderUtils.createBackgroundSourceRect(localBitmap, this.mBackgroundDestRect, this.mBackgroundSrcRect);
        }
        Rect localRect1 = this.mImageSourceRect;
        Rect localRect2 = this.mBackgroundSrcRect;
        Rect localRect3 = this.mBackgroundDestRect;
        Rect localRect4 = this.mImageRect;
        Rect localRect5 = this.mImageBorderRect;
        LinksRenderUtils.drawBitmap(paramCanvas, localBitmap, localRect1, localRect2, localRect3, localRect4, localRect5);
      }
      i = sLeftBorderPadding + this.mImageRect.width();
      if ((this.mLinkTitleLayout == null) && (this.mDeepLinkButton == null) && (this.mLinkUrlLayout == null))
        break label433;
      j = (int)((paramInt4 + sYDoublePadding) * getMediaHeightPercentage());
      if (this.mPlusOneButton != null)
        break label347;
      k = j;
      if (this.mLinkTitleLayout == null)
        break label365;
      m = (int)this.mLinkTitleLayout.getPaint().descent();
      if (this.mLinkTitleLayout != null)
        break label394;
      n = 0;
      if (this.mDeepLinkButton != null)
        break label406;
      i1 = 0;
      if (this.mLinkUrlLayout != null)
        break label421;
      i2 = 0;
    }
    label236: for (int i3 = m + (k - n - i1 - i2) / 2; ; i3 = 0)
    {
      LinksRenderUtils.drawTitleDeepLinkAndUrl(paramCanvas, i, i3, this.mLinkTitleLayout, this.mDeepLinkButton, this.mLinkUrlLayout, sTagLinkBitmaps[0]);
      drawMediaTopAreaShadow(paramCanvas, paramInt3, paramInt4);
      drawPlusOneBar(paramCanvas);
      drawMediaBottomArea$1be95c43(paramCanvas, paramInt1, paramInt3, paramInt4);
      drawCornerIcon(paramCanvas);
      return paramInt4;
      localBitmap = (Bitmap)this.mImageResource.getResource();
      break;
      bool = false;
      break label25;
      paramCanvas.drawRect(this.mBackgroundDestRect, LinksRenderUtils.getAppInviteTopAreaBackgroundPaint());
      break label43;
      k = j - this.mPlusOneButton.getRect().height();
      break label186;
      label365: if (this.mDeepLinkButton != null)
      {
        m = 0;
        break label206;
      }
      m = (int)this.mLinkUrlLayout.getPaint().descent();
      break label206;
      n = this.mLinkTitleLayout.getHeight();
      break label216;
      i1 = this.mDeepLinkButton.getRect().height();
      break label226;
      i2 = this.mLinkUrlLayout.getHeight();
      break label236;
    }
  }

  public final String getDeepLinkLabel()
  {
    if (this.mDbEmbedAppInvite == null);
    for (String str = null; ; str = this.mDbEmbedAppInvite.getLabelOrDefault(getContext()))
      return str;
  }

  public final String getLinkTitle()
  {
    String str4;
    if (this.mDbEmbedMedia == null)
    {
      str4 = null;
      return str4;
    }
    Resources localResources = getResources();
    String str1 = this.mCreationSource;
    String str2 = this.mDbEmbedMedia.getTitle();
    String str3 = this.mDbEmbedMedia.getDescription();
    boolean bool1 = this.mIsReshare;
    if (this.mDbEmbedAppInvite != null);
    for (boolean bool2 = true; ; bool2 = false)
    {
      str4 = LinksRenderUtils.getLinkTitle(localResources, str1, str2, str3, bool1, bool2);
      break;
    }
  }

  public final String getLinkUrl()
  {
    return this.mLinkUrl;
  }

  public final String getMediaLinkUrl()
  {
    return this.mDbEmbedMedia.getContentUrl();
  }

  public final MediaRef getMediaRef()
  {
    return this.mMediaRef;
  }

  public final void init(Cursor paramCursor, int paramInt1, int paramInt2, View.OnClickListener paramOnClickListener, ItemClickListener paramItemClickListener, StreamCardView.ViewedListener paramViewedListener, StreamCardView.StreamPlusBarClickListener paramStreamPlusBarClickListener, StreamCardView.StreamMediaClickListener paramStreamMediaClickListener)
  {
    super.init(paramCursor, paramInt1, paramInt2, paramOnClickListener, paramItemClickListener, paramViewedListener, paramStreamPlusBarClickListener, paramStreamMediaClickListener);
    this.mCreationSource = paramCursor.getString(21);
    if (!TextUtils.isEmpty(paramCursor.getString(18)));
    for (boolean bool = true; ; bool = false)
    {
      this.mIsReshare = bool;
      byte[] arrayOfByte1 = paramCursor.getBlob(22);
      if (arrayOfByte1 != null)
      {
        this.mDbEmbedMedia = DbEmbedMedia.deserialize(arrayOfByte1);
        if (this.mDbEmbedMedia.getImageUrl() != null)
          this.mMediaRef = new MediaRef(null, 0L, this.mDbEmbedMedia.getImageUrl(), null, MediaRef.MediaType.IMAGE);
        this.mLinkUrl = makeLinkUrl(this.mDbEmbedMedia.getContentUrl());
      }
      if ((0x20000 & paramCursor.getLong(15)) != 0L)
      {
        byte[] arrayOfByte2 = paramCursor.getBlob(26);
        if (arrayOfByte2 != null)
          this.mDbEmbedAppInvite = DbEmbedDeepLink.deserialize(arrayOfByte2);
      }
      return;
    }
  }

  protected final int layoutElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt3 + sXDoublePadding;
    int j = (int)((paramInt4 + sYDoublePadding) * getMediaHeightPercentage());
    this.mBackgroundRect.set(0, j, getMeasuredWidth(), getMeasuredHeight());
    createPlusOneBar(paramInt1, j + sTopBorderPadding - sYPadding, paramInt3);
    createMediaBottomArea(paramInt1, paramInt2, paramInt3, paramInt4);
    int k;
    label162: int n;
    int i1;
    if (this.mPlusOneButton == null)
    {
      k = j;
      int m = LinksRenderUtils.getMaxImageDimension();
      if (this.mImageDimension == 0)
      {
        this.mImageDimension = Math.min((int)(i * LinksRenderUtils.getImageMaxWidthPercentage()), Math.min(m, k));
        bindResources();
      }
      LinksRenderUtils.createBackgroundDestRect(sLeftBorderPadding, sTopBorderPadding, i + sLeftBorderPadding, j + sTopBorderPadding, this.mBackgroundDestRect);
      if (this.mMediaRef != null)
        break label312;
      this.mImageRect.setEmpty();
      this.mImageBorderRect.setEmpty();
      n = i - 2 * sLeftBorderPadding - this.mImageRect.width();
      this.mLinkTitleLayout = LinksRenderUtils.createTitle(getLinkTitle(), this.mImageDimension, n);
      StaticLayout localStaticLayout = this.mLinkTitleLayout;
      i1 = 0;
      if (localStaticLayout != null)
        i1 = 0 + this.mLinkTitleLayout.getHeight();
      if (this.mDbEmbedAppInvite == null)
        break label338;
      Context localContext = getContext();
      this.mDeepLinkButton = LinksRenderUtils.createDeepLinkButton(localContext, this.mDbEmbedAppInvite.getLabelOrDefault(localContext), this.mImageRect.right + sLeftBorderPadding, i1 + this.mImageRect.top, n, null);
    }
    while (true)
    {
      this.mImageSourceRect.setEmpty();
      this.mBackgroundSrcRect.setEmpty();
      return paramInt4;
      k = j - this.mPlusOneButton.getRect().height();
      break;
      label312: LinksRenderUtils.createImageRects(k, this.mImageDimension, sLeftBorderPadding, sTopBorderPadding, this.mImageRect, this.mImageBorderRect);
      break label162;
      label338: this.mLinkUrlLayout = LinksRenderUtils.createUrl(this.mLinkUrl, this.mImageDimension, n - sTagLinkBitmaps[0].getWidth(), i1);
    }
  }

  protected final void onBindResources()
  {
    super.onBindResources();
    if ((this.mMediaRef != null) && (this.mImageDimension != 0))
      this.mImageResource = ImageResourceManager.getInstance(getContext()).getMedia(this.mMediaRef, this.mImageDimension, this.mImageDimension, 0, this);
  }

  public void onRecycle()
  {
    super.onRecycle();
    this.mCreationSource = null;
    this.mIsReshare = false;
    this.mDbEmbedMedia = null;
    this.mDbEmbedAppInvite = null;
    this.mLinkTitleLayout = null;
    this.mDeepLinkButton = null;
    this.mLinkUrl = null;
    this.mLinkUrlLayout = null;
    this.mMediaRef = null;
    this.mBackgroundSrcRect.setEmpty();
    this.mBackgroundDestRect.setEmpty();
    this.mImageSourceRect.setEmpty();
    this.mImageRect.setEmpty();
    this.mImageBorderRect.setEmpty();
  }

  protected final void onUnbindResources()
  {
    super.onUnbindResources();
    if (this.mImageResource != null)
    {
      this.mImageResource.unregister(this);
      this.mImageResource = null;
    }
    this.mImageSourceRect.setEmpty();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.LinksCardView
 * JD-Core Version:    0.6.2
 */