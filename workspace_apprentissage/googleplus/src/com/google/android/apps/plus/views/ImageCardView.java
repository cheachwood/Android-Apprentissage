package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.content.DbEmbedMedia;
import com.google.android.apps.plus.service.ImageResourceManager;
import com.google.android.apps.plus.service.Resource;
import com.google.android.apps.plus.util.ImageUtils;

public class ImageCardView extends StreamCardView
  implements ClickableRect.ClickableRectListener
{
  private static Bitmap sAlbumBitmap;
  private static boolean sImageCardViewInitialized;
  private static ImageResourceManager sImageResourceManager;
  private static Bitmap sPanoramaBitmap;
  private static Bitmap sVideoBitmap;
  protected DbEmbedMedia mDbEmbedMedia;
  protected Rect mDestRect;
  protected Resource mImageResource;
  private int mImageSizeCategory;
  protected MediaRef mMediaRef;
  protected Rect mSrcRect;

  public ImageCardView(Context paramContext)
  {
    this(paramContext, null);
  }

  public ImageCardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (!sImageCardViewInitialized)
    {
      sImageCardViewInitialized = true;
      sImageResourceManager = ImageResourceManager.getInstance(paramContext);
      Resources localResources = paramContext.getResources();
      sPanoramaBitmap = ImageUtils.decodeResource(localResources, R.drawable.overlay_lightcycle);
      sVideoBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_overlay_play);
      sAlbumBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_overlay_album);
    }
    this.mDestRect = new Rect();
    this.mSrcRect = new Rect();
  }

  protected final int draw(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Bitmap localBitmap1;
    boolean bool;
    label18: Bitmap localBitmap2;
    if (this.mImageResource == null)
    {
      localBitmap1 = null;
      if (localBitmap1 == null)
        break label212;
      bool = true;
      drawMediaTopAreaStage(paramCanvas, paramInt3, paramInt4, bool, this.mDestRect, sMediaTopAreaBackgroundPaint);
      if (localBitmap1 != null)
      {
        if (this.mSrcRect.isEmpty())
          createSourceRectForMediaImage(this.mSrcRect, localBitmap1, paramInt3, paramInt4);
        paramCanvas.drawBitmap(localBitmap1, this.mSrcRect, this.mDestRect, sResizePaint);
      }
      if (!this.mDbEmbedMedia.isAlbum())
        break label218;
      localBitmap2 = sAlbumBitmap;
    }
    while (true)
    {
      if (localBitmap2 != null)
        paramCanvas.drawBitmap(localBitmap2, this.mDestRect.left + (this.mDestRect.width() - localBitmap2.getWidth()) / 2, this.mDestRect.top + (this.mDestRect.height() - localBitmap2.getHeight()) / 2, null);
      drawMediaTopAreaShadow(paramCanvas, paramInt3, paramInt4);
      drawTagBarIconAndBackground(paramCanvas, paramInt1, paramInt2);
      drawPlusOneBar(paramCanvas);
      drawMediaBottomArea$1be95c43(paramCanvas, paramInt1, paramInt3, paramInt4);
      drawCornerIcon(paramCanvas);
      return paramInt4;
      localBitmap1 = (Bitmap)this.mImageResource.getResource();
      break;
      label212: bool = false;
      break label18;
      label218: if (this.mDbEmbedMedia.isVideo())
        localBitmap2 = sVideoBitmap;
      else if (this.mDbEmbedMedia.isPanorama())
        localBitmap2 = sPanoramaBitmap;
      else
        localBitmap2 = null;
    }
  }

  public final String getAlbumId()
  {
    return this.mDbEmbedMedia.getAlbumId();
  }

  public final int getDesiredHeight()
  {
    return this.mDbEmbedMedia.getHeight();
  }

  public final int getDesiredWidth()
  {
    return this.mDbEmbedMedia.getWidth();
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
    byte[] arrayOfByte = paramCursor.getBlob(22);
    Uri localUri;
    if (arrayOfByte != null)
    {
      this.mDbEmbedMedia = DbEmbedMedia.deserialize(arrayOfByte);
      Object localObject = this.mDbEmbedMedia.getImageUrl();
      String str1 = this.mDbEmbedMedia.getVideoUrl();
      if (this.mDbEmbedMedia.isVideo())
      {
        String str2 = ImageUtils.rewriteYoutubeMediaUrl(str1);
        if (!TextUtils.equals(str1, str2))
          localObject = str2;
      }
      if (!this.mDbEmbedMedia.isVideo())
        break label229;
      localUri = Uri.parse(str1);
      this.mMediaRef = new MediaRef(this.mDbEmbedMedia.getOwnerId(), this.mDbEmbedMedia.getPhotoId(), (String)localObject, localUri, this.mDbEmbedMedia.getMediaType());
      this.mImageSizeCategory = 3;
      if (!this.mDbEmbedMedia.isVideo())
      {
        if (this.mDisplaySizeType != 2)
          break label235;
        this.mImageSizeCategory = 5;
      }
      label171: if ((this.mTag == null) && (!TextUtils.isEmpty(this.mDbEmbedMedia.getTitle())))
      {
        this.mTag = this.mDbEmbedMedia.getTitle().toUpperCase();
        if (!this.mDbEmbedMedia.isVideo())
          break label251;
      }
    }
    label229: label235: label251: for (Bitmap localBitmap = sTagVideoBitmaps[0]; ; localBitmap = sTagAlbumBitmaps[0])
    {
      this.mTagIcon = localBitmap;
      return;
      localUri = null;
      break;
      if (this.mDisplaySizeType != 1)
        break label171;
      this.mImageSizeCategory = 4;
      break label171;
    }
  }

  public final boolean isAlbum()
  {
    return this.mDbEmbedMedia.isAlbum();
  }

  protected final int layoutElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt3 + sXDoublePadding;
    int j = (int)((paramInt4 + sYDoublePadding) * getMediaHeightPercentage());
    this.mBackgroundRect.set(0, j, getMeasuredWidth(), getMeasuredHeight());
    createTagBar(paramInt1, paramInt2, paramInt3);
    createPlusOneBar(paramInt1, j + sTopBorderPadding - sYPadding, paramInt3);
    createMediaBottomArea(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mSrcRect.setEmpty();
    this.mDestRect.set(sLeftBorderPadding, sTopBorderPadding, i + sLeftBorderPadding, j + sTopBorderPadding);
    return paramInt4;
  }

  protected final void onBindResources()
  {
    super.onBindResources();
    if (this.mMediaRef != null)
      this.mImageResource = sImageResourceManager.getMedia(this.mMediaRef, this.mImageSizeCategory, this);
  }

  public final void onClickableRectClick$598f98c1()
  {
    if (this.mStreamMediaClickListener != null)
      this.mStreamMediaClickListener.onMediaClicked(this.mDbEmbedMedia.getAlbumId(), this.mDbEmbedMedia.getOwnerId(), this.mMediaRef, this.mDbEmbedMedia.isVideo(), this);
  }

  public void onRecycle()
  {
    super.onRecycle();
    this.mDbEmbedMedia = null;
    this.mMediaRef = null;
    this.mSrcRect.setEmpty();
    this.mDestRect.setEmpty();
  }

  protected final void onUnbindResources()
  {
    super.onUnbindResources();
    if (this.mImageResource != null)
    {
      this.mImageResource.unregister(this);
      this.mImageResource = null;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ImageCardView
 * JD-Core Version:    0.6.2
 */