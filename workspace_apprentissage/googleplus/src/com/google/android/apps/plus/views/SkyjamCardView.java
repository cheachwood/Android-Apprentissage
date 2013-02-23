package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.content.DbEmbedSkyjam;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.ImageResourceManager;
import com.google.android.apps.plus.service.Resource;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.TextPaintUtils;

public class SkyjamCardView extends StreamCardView
  implements ClickableImageButton.ClickableImageButtonListener
{
  protected static Paint sAlbumBorderPaint;
  protected static Bitmap sEmptyThumbnailBitmap;
  private static Bitmap sGoogleMusicBitmap;
  protected static TextPaint sListenBuyTextPaint;
  protected static TextPaint sNonTitleTextPaint;
  private static Bitmap sPlayOverlayBitmap;
  private static boolean sSkyjamCardViewInitialized;
  protected static int sSkyjamMediaBorderDimension;
  protected static int sSkyjamMediaDimension;
  protected static CharSequence sSkyjamPlayButtonDescription;
  protected static TextPaint sTitleTextPaint;
  protected String mAlbum;
  protected StaticLayout mAlbumLayout;
  protected String mArtist;
  protected StaticLayout mArtistLayout;
  protected ClickableImageButton mAutoPlayButton;
  protected Resource mImageResource;
  protected StaticLayout mListenBuyLayout;
  protected String mThumbnailUrl;
  protected String mTitle;
  protected StaticLayout mTitleLayout;

  public SkyjamCardView(Context paramContext)
  {
    this(paramContext, null);
  }

  public SkyjamCardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (!sSkyjamCardViewInitialized)
    {
      sSkyjamCardViewInitialized = true;
      Resources localResources = paramContext.getResources();
      TextPaint localTextPaint1 = new TextPaint();
      sTitleTextPaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sTitleTextPaint.setColor(localResources.getColor(R.color.card_skyjam_title));
      sTitleTextPaint.setTextSize(localResources.getDimension(R.dimen.card_skyjam_title_text_size));
      TextPaintUtils.registerTextPaint(sTitleTextPaint, R.dimen.card_skyjam_title_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sNonTitleTextPaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sNonTitleTextPaint.setColor(localResources.getColor(R.color.card_skyjam_nontitle));
      sNonTitleTextPaint.setTextSize(localResources.getDimension(R.dimen.card_skyjam_nontitle_text_size));
      TextPaintUtils.registerTextPaint(sNonTitleTextPaint, R.dimen.card_skyjam_nontitle_text_size);
      TextPaint localTextPaint3 = new TextPaint();
      sListenBuyTextPaint = localTextPaint3;
      localTextPaint3.setAntiAlias(true);
      sListenBuyTextPaint.setColor(localResources.getColor(R.color.card_skyjam_listen_buy));
      sListenBuyTextPaint.setTextSize(localResources.getDimension(R.dimen.card_skyjam_listen_buy_text_size));
      TextPaintUtils.registerTextPaint(sListenBuyTextPaint, R.dimen.card_skyjam_listen_buy_text_size);
      Paint localPaint = new Paint();
      sAlbumBorderPaint = localPaint;
      localPaint.setColor(localResources.getColor(R.color.card_skyjam_album_border));
      sAlbumBorderPaint.setStrokeWidth(localResources.getDimension(R.dimen.card_skyjam_album_border_size));
      sAlbumBorderPaint.setStyle(Paint.Style.STROKE);
      sEmptyThumbnailBitmap = ImageUtils.decodeResource(localResources, R.drawable.empty_thumbnail);
      sGoogleMusicBitmap = ImageUtils.decodeResource(localResources, R.drawable.google_music);
      sPlayOverlayBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_play_overlay);
      sSkyjamMediaDimension = (int)localResources.getDimension(R.dimen.card_skyjam_media_size);
      sSkyjamMediaBorderDimension = (int)localResources.getDimension(R.dimen.card_skyjam_media_border_size);
      sSkyjamPlayButtonDescription = localResources.getString(R.string.skyjam_content_play_button_description);
    }
  }

  protected final int draw(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = (int)((paramInt4 + sYDoublePadding) * getMediaHeightPercentage());
    drawMediaTopAreaStageWithTiledBackground(paramCanvas, paramInt3, i);
    int j = (sSkyjamMediaBorderDimension - sSkyjamMediaDimension) / 2;
    paramCanvas.drawRect(paramInt1, paramInt2, paramInt1 + sSkyjamMediaBorderDimension, paramInt2 + sSkyjamMediaBorderDimension, sAlbumBorderPaint);
    if (this.mImageResource == null);
    for (Bitmap localBitmap = null; ; localBitmap = (Bitmap)this.mImageResource.getResource())
    {
      if (localBitmap == null)
        localBitmap = sEmptyThumbnailBitmap;
      sDrawRect.set(paramInt1 + j, paramInt2 + j, paramInt1 + j + sSkyjamMediaDimension, paramInt2 + j + sSkyjamMediaDimension);
      paramCanvas.drawBitmap(localBitmap, null, sDrawRect, sResizePaint);
      if (this.mAutoPlayButton != null)
        this.mAutoPlayButton.draw(paramCanvas);
      drawMediaTopAreaShadow(paramCanvas, paramInt3, paramInt4);
      int k = paramInt1 + (sSkyjamMediaBorderDimension + sContentXPadding);
      int m = paramInt3 - (sSkyjamMediaBorderDimension + sContentXPadding);
      if (this.mTitleLayout != null)
      {
        paramCanvas.translate(k, paramInt2);
        this.mTitleLayout.draw(paramCanvas);
        paramCanvas.translate(-k, -paramInt2);
        paramInt2 += this.mTitleLayout.getHeight();
      }
      if (this.mArtistLayout != null)
      {
        paramCanvas.translate(k, paramInt2);
        this.mArtistLayout.draw(paramCanvas);
        paramCanvas.translate(-k, -paramInt2);
        paramInt2 += this.mArtistLayout.getHeight();
      }
      if (this.mAlbumLayout != null)
      {
        paramCanvas.translate(k, paramInt2);
        this.mAlbumLayout.draw(paramCanvas);
        paramCanvas.translate(-k, -paramInt2);
        paramInt2 += this.mAlbumLayout.getHeight();
      }
      if (this.mListenBuyLayout != null)
      {
        paramCanvas.translate(k, paramInt2);
        this.mListenBuyLayout.draw(paramCanvas);
        paramCanvas.translate(-k, -paramInt2);
        paramInt2 += this.mListenBuyLayout.getHeight();
      }
      if (i - paramInt2 >= sGoogleMusicBitmap.getHeight())
      {
        paramCanvas.drawBitmap(sGoogleMusicBitmap, k, paramInt2, null);
        sGoogleMusicBitmap.getHeight();
      }
      int n = k - (sSkyjamMediaBorderDimension + sContentXPadding);
      int i1 = m + (sSkyjamMediaBorderDimension + sContentXPadding);
      drawPlusOneBar(paramCanvas);
      drawMediaBottomArea$1be95c43(paramCanvas, n, i1, paramInt4);
      drawCornerIcon(paramCanvas);
      return paramInt4;
    }
  }

  public final void init(Cursor paramCursor, int paramInt1, int paramInt2, View.OnClickListener paramOnClickListener, ItemClickListener paramItemClickListener, StreamCardView.ViewedListener paramViewedListener, StreamCardView.StreamPlusBarClickListener paramStreamPlusBarClickListener, StreamCardView.StreamMediaClickListener paramStreamMediaClickListener)
  {
    super.init(paramCursor, paramInt1, paramInt2, paramOnClickListener, paramItemClickListener, paramViewedListener, paramStreamPlusBarClickListener, paramStreamMediaClickListener);
    byte[] arrayOfByte = paramCursor.getBlob(23);
    DbEmbedSkyjam localDbEmbedSkyjam;
    if (arrayOfByte != null)
    {
      localDbEmbedSkyjam = DbEmbedSkyjam.deserialize(arrayOfByte);
      if (localDbEmbedSkyjam != null)
      {
        if (!localDbEmbedSkyjam.isAlbum())
          break label80;
        this.mTitle = localDbEmbedSkyjam.getAlbum();
      }
    }
    while (true)
    {
      this.mArtist = localDbEmbedSkyjam.getArtist();
      this.mThumbnailUrl = localDbEmbedSkyjam.getImageUrl();
      return;
      label80: this.mTitle = localDbEmbedSkyjam.getSong();
      this.mAlbum = localDbEmbedSkyjam.getAlbum();
    }
  }

  protected final int layoutElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = (int)((paramInt4 + sYDoublePadding) * getMediaHeightPercentage());
    int j = paramInt2;
    this.mBackgroundRect.set(0, i, getMeasuredWidth(), getMeasuredHeight());
    int k = (sSkyjamMediaBorderDimension - sSkyjamMediaDimension) / 2;
    if (this.mAlbum != null)
    {
      removeClickableItem(this.mAutoPlayButton);
      this.mAutoPlayButton = new ClickableImageButton(getContext(), sPlayOverlayBitmap, null, this, sSkyjamPlayButtonDescription);
      this.mAutoPlayButton.setPosition(paramInt1 + k, paramInt2 + k + sSkyjamMediaDimension - sPlayOverlayBitmap.getHeight());
      addClickableItem(this.mAutoPlayButton);
    }
    int m = paramInt1 + (sSkyjamMediaBorderDimension + sContentXPadding);
    int n = paramInt3 - (sSkyjamMediaBorderDimension + sContentXPadding);
    if (!TextUtils.isEmpty(this.mTitle))
    {
      int i6 = (i - paramInt2) / (int)(sTitleTextPaint.descent() - sTitleTextPaint.ascent());
      if (i6 > 0)
      {
        this.mTitleLayout = TextPaintUtils.createConstrainedStaticLayout(sTitleTextPaint, this.mTitle, n, i6);
        paramInt2 += this.mTitleLayout.getHeight();
      }
    }
    if (!TextUtils.isEmpty(this.mArtist))
    {
      int i5 = (i - paramInt2) / (int)(sNonTitleTextPaint.descent() - sNonTitleTextPaint.ascent());
      if (i5 > 0)
      {
        this.mArtistLayout = TextPaintUtils.createConstrainedStaticLayout(sNonTitleTextPaint, this.mArtist, n, i5);
        paramInt2 += this.mArtistLayout.getHeight();
      }
    }
    if (!TextUtils.isEmpty(this.mAlbum))
    {
      int i4 = (i - paramInt2) / (int)(sNonTitleTextPaint.descent() - sNonTitleTextPaint.ascent());
      if (i4 > 0)
      {
        this.mAlbumLayout = TextPaintUtils.createConstrainedStaticLayout(sNonTitleTextPaint, this.mAlbum, n, i4);
        paramInt2 += this.mAlbumLayout.getHeight();
      }
    }
    int i1 = (i - paramInt2) / (int)(sListenBuyTextPaint.descent() - sListenBuyTextPaint.ascent());
    if (i1 > 0)
    {
      this.mListenBuyLayout = TextPaintUtils.createConstrainedStaticLayout(sListenBuyTextPaint, getResources().getString(R.string.skyjam_listen_buy), n, i1);
      this.mListenBuyLayout.getHeight();
    }
    int i2 = m - (sSkyjamMediaBorderDimension + sContentXPadding);
    int i3 = n + (sSkyjamMediaBorderDimension + sContentXPadding);
    createPlusOneBar(i2, i + sTopBorderPadding - sYPadding, i3);
    createMediaBottomArea(i2, j, i3, paramInt4);
    return paramInt4;
  }

  protected final void onBindResources()
  {
    super.onBindResources();
    if (this.mThumbnailUrl != null)
      this.mImageResource = ImageResourceManager.getInstance(getContext()).getMedia(new MediaRef(this.mThumbnailUrl, MediaRef.MediaType.IMAGE), 2, this);
  }

  public final void onClickableImageButtonClick(ClickableImageButton paramClickableImageButton)
  {
    if (paramClickableImageButton == this.mAutoPlayButton)
    {
      Context localContext = getContext();
      Intent localIntent = Intents.getStreamOneUpActivityIntent(localContext, EsService.getActiveAccount(localContext), this.mActivityId);
      localIntent.putExtra("auto_play_music", true);
      localContext.startActivity(localIntent);
    }
  }

  public void onRecycle()
  {
    super.onRecycle();
    this.mTitle = null;
    this.mArtist = null;
    this.mAlbum = null;
    this.mThumbnailUrl = null;
    this.mTitleLayout = null;
    this.mArtistLayout = null;
    this.mAlbumLayout = null;
    this.mListenBuyLayout = null;
    this.mAutoPlayButton = null;
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
 * Qualified Name:     com.google.android.apps.plus.views.SkyjamCardView
 * JD-Core Version:    0.6.2
 */