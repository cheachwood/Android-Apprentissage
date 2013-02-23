package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Layout.Alignment;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.MediaImageRequest;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.service.ImageCache.OnMediaImageChangeListener;
import com.google.android.apps.plus.service.SkyjamPlaybackService;
import com.google.android.apps.plus.service.SkyjamPlaybackService.SkyjamPlaybackListener;
import com.google.android.apps.plus.util.AccessibilityUtils;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.StringUtils;
import com.google.android.apps.plus.util.TextPaintUtils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class StreamOneUpSkyjamView extends View
  implements ImageCache.OnMediaImageChangeListener, SkyjamPlaybackService.SkyjamPlaybackListener, ClickableRect.ClickableRectListener
{
  private static int sActionBarHeight;
  protected static TextPaint sDefaultTextPaint;
  private static Bitmap sEmptyImage;
  private static Bitmap sGoogleMusic;
  private static ImageCache sImageCache;
  protected static int sMaxWidth;
  protected static int sNameMargin;
  private static Bitmap sPlayIcon;
  protected static int sPlayStopButtonPadding;
  private static Paint sPreviewPaint;
  private static Paint sResizePaint;
  private static CharSequence sSkyjamPlayButtonDesc;
  private static Bitmap sStopIcon;
  private static boolean sStreamOneUpSkyjamViewInitialized;
  private static Bitmap sTagIcon;
  private static Paint sTagPaint;
  protected static TextPaint sTagTextPaint;
  private Bitmap mActionIcon;
  private PointF mActionIconPoint;
  private ClickableRect mActionRect;
  private String mActivityId;
  private final Set<ClickableItem> mClickableItems = new HashSet();
  private ClickableItem mCurrentClickableItem;
  private Rect mGoogleMusicRect;
  private MediaImage mImage;
  private Rect mImageRect;
  private String mImageUrl;
  private boolean mIsAlbum;
  private String mMusicUrl;
  private RectF mPreviewBackground;
  private String mPreviewStatus;
  private PointF mPreviewStatusPoint;
  private RectF mTagBackground;
  private Rect mTagIconRect;
  private PositionedStaticLayout mTagLayout;
  private String mTagTitle;

  public StreamOneUpSkyjamView(Context paramContext)
  {
    super(paramContext);
    Context localContext = getContext();
    if (!sStreamOneUpSkyjamViewInitialized)
    {
      sStreamOneUpSkyjamViewInitialized = true;
      sImageCache = ImageCache.getInstance(localContext);
      Resources localResources = getResources();
      sTagIcon = ImageUtils.decodeResource(localResources, R.drawable.ic_metadata_music);
      sEmptyImage = ImageUtils.decodeResource(localResources, R.drawable.empty_thumbnail);
      sGoogleMusic = ImageUtils.decodeResource(localResources, R.drawable.google_music);
      sPlayIcon = ImageUtils.resizeToSquareBitmap(ImageUtils.decodeResource(localResources, R.drawable.ic_play), sGoogleMusic.getHeight());
      sStopIcon = ImageUtils.resizeToSquareBitmap(ImageUtils.decodeResource(localResources, R.drawable.ic_stop), sGoogleMusic.getHeight());
      sActionBarHeight = (int)localResources.getDimension(R.dimen.host_action_bar_height);
      sMaxWidth = (int)localResources.getDimension(R.dimen.stream_one_up_list_max_width);
      sNameMargin = (int)localResources.getDimension(R.dimen.stream_one_up_stage_hangout_name_margin);
      sPlayStopButtonPadding = (int)localResources.getDimension(R.dimen.stream_one_up_stage_skyjam_play_stop_padding);
      sResizePaint = new Paint(2);
      Paint localPaint1 = new Paint();
      sTagPaint = localPaint1;
      localPaint1.setAntiAlias(true);
      sTagPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_skyjam_tag_background));
      sTagPaint.setStyle(Paint.Style.FILL);
      Paint localPaint2 = new Paint();
      sPreviewPaint = localPaint2;
      localPaint2.setAntiAlias(true);
      sPreviewPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_skyjam_preview_background));
      sPreviewPaint.setStyle(Paint.Style.FILL);
      TextPaint localTextPaint1 = new TextPaint();
      sDefaultTextPaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sDefaultTextPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_skyjam_preview));
      sDefaultTextPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_stage_default_text_size));
      TextPaintUtils.registerTextPaint(sDefaultTextPaint, R.dimen.stream_one_up_stage_default_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sTagTextPaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sTagTextPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_tag));
      sTagTextPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_stage_default_text_size));
      TextPaintUtils.registerTextPaint(sTagTextPaint, R.dimen.stream_one_up_stage_default_text_size);
      sSkyjamPlayButtonDesc = localResources.getString(R.string.skyjam_content_play_button_description);
    }
  }

  public StreamOneUpSkyjamView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Context localContext = getContext();
    if (!sStreamOneUpSkyjamViewInitialized)
    {
      sStreamOneUpSkyjamViewInitialized = true;
      sImageCache = ImageCache.getInstance(localContext);
      Resources localResources = getResources();
      sTagIcon = ImageUtils.decodeResource(localResources, R.drawable.ic_metadata_music);
      sEmptyImage = ImageUtils.decodeResource(localResources, R.drawable.empty_thumbnail);
      sGoogleMusic = ImageUtils.decodeResource(localResources, R.drawable.google_music);
      sPlayIcon = ImageUtils.resizeToSquareBitmap(ImageUtils.decodeResource(localResources, R.drawable.ic_play), sGoogleMusic.getHeight());
      sStopIcon = ImageUtils.resizeToSquareBitmap(ImageUtils.decodeResource(localResources, R.drawable.ic_stop), sGoogleMusic.getHeight());
      sActionBarHeight = (int)localResources.getDimension(R.dimen.host_action_bar_height);
      sMaxWidth = (int)localResources.getDimension(R.dimen.stream_one_up_list_max_width);
      sNameMargin = (int)localResources.getDimension(R.dimen.stream_one_up_stage_hangout_name_margin);
      sPlayStopButtonPadding = (int)localResources.getDimension(R.dimen.stream_one_up_stage_skyjam_play_stop_padding);
      sResizePaint = new Paint(2);
      Paint localPaint1 = new Paint();
      sTagPaint = localPaint1;
      localPaint1.setAntiAlias(true);
      sTagPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_skyjam_tag_background));
      sTagPaint.setStyle(Paint.Style.FILL);
      Paint localPaint2 = new Paint();
      sPreviewPaint = localPaint2;
      localPaint2.setAntiAlias(true);
      sPreviewPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_skyjam_preview_background));
      sPreviewPaint.setStyle(Paint.Style.FILL);
      TextPaint localTextPaint1 = new TextPaint();
      sDefaultTextPaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sDefaultTextPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_skyjam_preview));
      sDefaultTextPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_stage_default_text_size));
      TextPaintUtils.registerTextPaint(sDefaultTextPaint, R.dimen.stream_one_up_stage_default_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sTagTextPaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sTagTextPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_tag));
      sTagTextPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_stage_default_text_size));
      TextPaintUtils.registerTextPaint(sTagTextPaint, R.dimen.stream_one_up_stage_default_text_size);
      sSkyjamPlayButtonDesc = localResources.getString(R.string.skyjam_content_play_button_description);
    }
  }

  public StreamOneUpSkyjamView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Context localContext = getContext();
    if (!sStreamOneUpSkyjamViewInitialized)
    {
      sStreamOneUpSkyjamViewInitialized = true;
      sImageCache = ImageCache.getInstance(localContext);
      Resources localResources = getResources();
      sTagIcon = ImageUtils.decodeResource(localResources, R.drawable.ic_metadata_music);
      sEmptyImage = ImageUtils.decodeResource(localResources, R.drawable.empty_thumbnail);
      sGoogleMusic = ImageUtils.decodeResource(localResources, R.drawable.google_music);
      sPlayIcon = ImageUtils.resizeToSquareBitmap(ImageUtils.decodeResource(localResources, R.drawable.ic_play), sGoogleMusic.getHeight());
      sStopIcon = ImageUtils.resizeToSquareBitmap(ImageUtils.decodeResource(localResources, R.drawable.ic_stop), sGoogleMusic.getHeight());
      sActionBarHeight = (int)localResources.getDimension(R.dimen.host_action_bar_height);
      sMaxWidth = (int)localResources.getDimension(R.dimen.stream_one_up_list_max_width);
      sNameMargin = (int)localResources.getDimension(R.dimen.stream_one_up_stage_hangout_name_margin);
      sPlayStopButtonPadding = (int)localResources.getDimension(R.dimen.stream_one_up_stage_skyjam_play_stop_padding);
      sResizePaint = new Paint(2);
      Paint localPaint1 = new Paint();
      sTagPaint = localPaint1;
      localPaint1.setAntiAlias(true);
      sTagPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_skyjam_tag_background));
      sTagPaint.setStyle(Paint.Style.FILL);
      Paint localPaint2 = new Paint();
      sPreviewPaint = localPaint2;
      localPaint2.setAntiAlias(true);
      sPreviewPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_skyjam_preview_background));
      sPreviewPaint.setStyle(Paint.Style.FILL);
      TextPaint localTextPaint1 = new TextPaint();
      sDefaultTextPaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sDefaultTextPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_skyjam_preview));
      sDefaultTextPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_stage_default_text_size));
      TextPaintUtils.registerTextPaint(sDefaultTextPaint, R.dimen.stream_one_up_stage_default_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sTagTextPaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sTagTextPaint.setColor(localResources.getColor(R.color.stream_one_up_stage_tag));
      sTagTextPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_stage_default_text_size));
      TextPaintUtils.registerTextPaint(sTagTextPaint, R.dimen.stream_one_up_stage_default_text_size);
      sSkyjamPlayButtonDesc = localResources.getString(R.string.skyjam_content_play_button_description);
    }
  }

  public final void bind(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    this.mClickableItems.clear();
    this.mCurrentClickableItem = null;
    this.mIsAlbum = TextUtils.isEmpty(paramString2);
    int i;
    label85: Context localContext;
    String str;
    if (this.mIsAlbum)
    {
      i = paramString5.indexOf("https://");
      if (i < 0)
        i = paramString5.indexOf("https://");
      if (i >= 0)
      {
        this.mIsAlbum = TextUtils.isEmpty(paramString2);
        if (!this.mIsAlbum)
          break label272;
        this.mMusicUrl = paramString5.substring(i);
        this.mPreviewStatus = SkyjamPlaybackService.getPlaybackStatus(getContext(), this.mMusicUrl);
        localContext = getContext();
        if (!this.mIsAlbum)
          break label346;
        str = StringUtils.unescape(paramString1);
        label119: if (!this.mIsAlbum)
          break label382;
      }
    }
    while (true)
    {
      this.mTagTitle = str;
      if (!TextUtils.isEmpty(paramString3))
      {
        this.mImageUrl = paramString3;
        this.mImage = new MediaImage(this, new MediaImageRequest(paramString3, 3, 300));
        this.mImage.load();
      }
      StringBuilder localStringBuilder = new StringBuilder(256);
      if (!TextUtils.isEmpty(paramString1))
        localStringBuilder.append(paramString1).append('\n');
      if (!TextUtils.isEmpty(paramString2))
        localStringBuilder.append(paramString2);
      setContentDescription(localStringBuilder.toString());
      this.mActivityId = paramString6;
      invalidate();
      requestLayout();
      return;
      i = paramString4.indexOf("https://");
      if (i >= 0)
        break;
      i = paramString4.indexOf("https://");
      break;
      label272: this.mMusicUrl = paramString4.substring(i);
      if (this.mMusicUrl.contains("mode=inline"))
      {
        this.mMusicUrl = this.mMusicUrl.replace("mode=inline", "mode=streaming");
        break label85;
      }
      this.mMusicUrl += "&mode=streaming";
      break label85;
      label346: int j = R.string.skyjam_from_the_album;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = StringUtils.unescape(paramString1).toUpperCase();
      str = localContext.getString(j, arrayOfObject);
      break label119;
      label382: str = paramString2;
    }
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
      Iterator localIterator2 = this.mClickableItems.iterator();
      while (true)
      {
        Iterator localIterator1;
        int j;
        if (localIterator2.hasNext())
        {
          ClickableItem localClickableItem = (ClickableItem)localIterator2.next();
          if (localClickableItem.handleEvent(k, m, 0))
          {
            this.mCurrentClickableItem = localClickableItem;
            invalidate();
            break;
          }
        }
      }
      i = 0;
      continue;
      this.mCurrentClickableItem = null;
      localIterator1 = this.mClickableItems.iterator();
      while (localIterator1.hasNext())
        ((ClickableItem)localIterator1.next()).handleEvent(k, m, i);
      invalidate();
      j = 0;
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

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    ImageCache.registerMediaImageChangeListener(this);
    SkyjamPlaybackService.registerListener(this);
  }

  public final void onClickableRectClick$598f98c1()
  {
    Context localContext = getContext();
    int i;
    Intent localIntent;
    if (!SkyjamPlaybackService.isPlaying(this.mMusicUrl))
    {
      i = 1;
      localIntent = new Intent(localContext, SkyjamPlaybackService.class);
      if (i == 0)
        break label105;
    }
    label105: for (String str = "com.google.android.apps.plus.service.SkyjamPlaybackService.PLAY"; ; str = "com.google.android.apps.plus.service.SkyjamPlaybackService.STOP")
    {
      localIntent.setAction(str);
      localIntent.putExtra("music_account", EsService.getActiveAccount(localContext));
      localIntent.putExtra("music_url", this.mMusicUrl);
      localIntent.putExtra("song", this.mTagTitle);
      localIntent.putExtra("activity_id", this.mActivityId);
      localContext.startService(localIntent);
      return;
      i = 0;
      break;
    }
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    ImageCache.unregisterMediaImageChangeListener(this);
    SkyjamPlaybackService.unregisterListener(this);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.drawRoundRect(this.mTagBackground, 5.0F, 5.0F, sTagPaint);
    paramCanvas.drawBitmap(sTagIcon, null, this.mTagIconRect, null);
    int i = this.mTagLayout.getLeft();
    int j = this.mTagLayout.getTop();
    paramCanvas.translate(i, j);
    this.mTagLayout.draw(paramCanvas);
    paramCanvas.translate(-i, -j);
    if (this.mImage != null)
    {
      this.mImage.refreshIfInvalidated();
      Bitmap localBitmap = this.mImage.getBitmap();
      if (localBitmap == null)
        localBitmap = sEmptyImage;
      paramCanvas.drawBitmap(localBitmap, null, this.mImageRect, sResizePaint);
    }
    if (this.mPreviewBackground != null)
    {
      paramCanvas.drawRect(this.mPreviewBackground, sPreviewPaint);
      paramCanvas.drawText(this.mPreviewStatus, this.mPreviewStatusPoint.x, this.mPreviewStatusPoint.y, sDefaultTextPaint);
      paramCanvas.drawBitmap(this.mActionIcon, this.mActionIconPoint.x, this.mActionIconPoint.y, null);
      paramCanvas.drawBitmap(sGoogleMusic, null, this.mGoogleMusicRect, null);
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j;
    int k;
    int n;
    int i16;
    int i19;
    int i18;
    label400: Bitmap localBitmap;
    label512: int i20;
    switch (View.MeasureSpec.getMode(paramInt1))
    {
    default:
      j = sMaxWidth;
      super.onMeasure(View.MeasureSpec.makeMeasureSpec(j, 1073741824), paramInt2);
      k = getPaddingLeft();
      int m = getPaddingTop();
      n = getMeasuredWidth();
      int i1 = getMeasuredHeight();
      int i2 = n - k - getPaddingRight();
      int i3 = i1 - m - getPaddingBottom();
      int i4 = k + 13;
      int i5 = m + 13;
      int i6 = sTagIcon.getWidth();
      int i7 = sTagIcon.getHeight();
      int i8 = (int)sDefaultTextPaint.measureText(this.mTagTitle);
      int i9 = i8 + (i6 + 15);
      this.mTagBackground = new RectF(i4, i5, i9 + i4, i5 + 39);
      this.mTagLayout = new PositionedStaticLayout(this.mTagTitle, sTagTextPaint, i8, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
      int i10 = this.mTagLayout.getHeight();
      int i11 = i4 + 5;
      int i12 = i5 + (39 - i7) / 2;
      this.mTagIconRect = new Rect(i11, i12, i11 + i6, i12 + i7);
      int i13 = i11 + (i6 + 5);
      int i14 = m + 13 + (39 - i10) / 2;
      this.mTagLayout.setPosition(i13, i14);
      int i15 = 13 + (m + 13 + (int)this.mTagBackground.height());
      i16 = i2 - 52;
      int i17 = m + (i3 - i15);
      if (this.mImage != null)
      {
        int i31 = Math.min(i17, i16);
        this.mImage.refreshIfInvalidated();
        i19 = k + (26 + (i16 - i31) / 2);
        this.mImageRect = new Rect(i19, i15, i19 + i31, i15 + i31);
        i18 = i31;
        if (!this.mIsAlbum)
        {
          this.mPreviewBackground = new RectF(i19, i15, i19 + i18, i15 + 66);
          this.mClickableItems.remove(this.mActionRect);
          this.mActionRect = new ClickableRect(i19, i15 + sActionBarHeight, i19 + i18, i15 + 66, this, sSkyjamPlayButtonDesc);
          this.mClickableItems.add(this.mActionRect);
          if (!SkyjamPlaybackService.isPlaying(this.mMusicUrl))
            break label797;
          localBitmap = sStopIcon;
          this.mActionIcon = localBitmap;
          int i21 = this.mActionIcon.getHeight();
          int i22 = 13 + (int)this.mPreviewBackground.left;
          int i23 = (int)this.mPreviewBackground.top + (66 - i21) / 2;
          this.mActionIconPoint = new PointF(i22, i23);
          int i24 = (int)(sDefaultTextPaint.descent() - sDefaultTextPaint.ascent());
          int i25 = i22 + (13 + this.mActionIcon.getWidth());
          int i26 = (int)this.mPreviewBackground.top + (66 - i24) / 2 - (int)sDefaultTextPaint.ascent();
          this.mPreviewStatusPoint = new PointF(i25, i26);
          int i27 = sGoogleMusic.getWidth();
          int i28 = sGoogleMusic.getHeight();
          int i29 = (int)this.mPreviewBackground.right - i27;
          int i30 = (int)this.mPreviewBackground.top + (66 - i28) / 2;
          this.mGoogleMusicRect = new Rect(i29, i30, i27 + i29, i28 + i30);
        }
        i20 = 13 + (m + 13 + (int)this.mTagBackground.height());
        if (this.mImage == null)
          break label805;
        i20 += i18;
      }
      break;
    case -2147483648:
    case 1073741824:
    }
    while (true)
    {
      setMeasuredDimension(n, i20 + getPaddingBottom());
      return;
      j = Math.min(i, sMaxWidth);
      break;
      j = i;
      break;
      i18 = i16;
      i19 = k;
      break label400;
      label797: localBitmap = sPlayIcon;
      break label512;
      label805: if (this.mPreviewBackground != null)
        i20 += 66;
    }
  }

  public final void onMediaImageChanged(String paramString)
  {
    if (MediaImageRequest.areCanonicallyEqual(this.mImageUrl, paramString))
      this.mImage.invalidate();
    invalidate();
  }

  public final void onPlaybackStatusUpdate(String paramString1, boolean paramBoolean, String paramString2)
  {
    Bitmap localBitmap = this.mActionIcon;
    if ((paramBoolean) && (paramString1 != null) && (paramString1.equals(this.mMusicUrl)))
    {
      this.mActionIcon = sStopIcon;
      if ((!this.mMusicUrl.equals(paramString1)) || (this.mPreviewStatus.equals(paramString2)))
        break label96;
    }
    label96: for (int i = 1; ; i = 0)
    {
      if (i != 0)
        this.mPreviewStatus = paramString2;
      if ((localBitmap != this.mActionIcon) || (i != 0))
        invalidate();
      return;
      this.mActionIcon = sPlayIcon;
      break;
    }
  }

  public final void processClick(float paramFloat1, float paramFloat2)
  {
    if (this.mActionRect != null)
    {
      Rect localRect = this.mActionRect.getRect();
      int[] arrayOfInt = new int[2];
      getLocationOnScreen(arrayOfInt);
      if ((localRect.contains((int)(paramFloat1 - arrayOfInt[0]), (int)(paramFloat2 - arrayOfInt[1]))) || (AccessibilityUtils.isAccessibilityEnabled(getContext())))
        onClickableRectClick$598f98c1();
    }
  }

  public final void startAutoPlay()
  {
    if (!SkyjamPlaybackService.isPlaying(this.mMusicUrl))
      onClickableRectClick$598f98c1();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.StreamOneUpSkyjamView
 * JD-Core Version:    0.6.2
 */