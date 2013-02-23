package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.phone.FIFEUtil;
import com.google.android.apps.plus.service.ImageResourceManager;
import com.google.android.apps.plus.service.Resource;
import com.google.android.apps.plus.service.ResourceConsumer;
import com.google.android.apps.plus.util.GifDrawable;
import com.google.android.apps.plus.util.GifImage;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.TextPaintUtils;
import com.google.android.apps.plus.util.ViewUtils;
import com.google.api.services.plusi.model.DataVideo;
import com.google.api.services.plusi.model.DataVideoJson;

public class PhotoHeaderView extends View
  implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener, ResourceConsumer
{
  private static int sBackgroundColor;
  private static Bitmap sCommentBitmap;
  private static int sCommentCountLeftMargin;
  private static TextPaint sCommentCountPaint;
  private static int sCommentCountTextWidth;
  private static Paint sCropDimPaint;
  private static Paint sCropPaint;
  private static int sCropSizeCoverWidth;
  private static int sCropSizeProfile;
  private static boolean sHasMultitouchDistinct;
  private static ImageResourceManager sImageManager;
  private static boolean sInitialized;
  private static Bitmap sPanoramaImage;
  private static int sPhotoOverlayBottomPadding;
  private static int sPhotoOverlayRightPadding;
  private static Bitmap sPlusOneBitmap;
  private static int sPlusOneBottomMargin;
  private static int sPlusOneCountLeftMargin;
  private static TextPaint sPlusOneCountPaint;
  private static int sPlusOneCountTextWidth;
  private static Paint sProcessingMediaBackgroundPaint;
  private static String sProcessingMediaSubTitle;
  private static TextPaint sProcessingMediaSubTitleTextPaint;
  private static int sProcessingMediaSubTitleVerticalPosition;
  private static String sProcessingMediaTitle;
  private static TextPaint sProcessingMediaTitleTextPaint;
  private static int sProcessingMediaTitleVerticalPosition;
  private static Paint sTagPaint;
  private static Paint sTagTextBackgroundPaint;
  private static int sTagTextPadding;
  private static TextPaint sTagTextPaint;
  private static Bitmap sVideoImage;
  private static Bitmap sVideoNotReadyImage;
  private boolean mAllowCrop;
  private boolean mAnimate;
  private Resource mAnimatedResource;
  private int mBackgroundColor;
  private String mCommentText;
  private Integer mCoverPhotoOffset;
  private int mCropMode;
  private Rect mCropRect = new Rect();
  private int mCropSizeHeight;
  private int mCropSizeWidth;
  private boolean mDoubleTapDebounce;
  private boolean mDoubleTapToZoomEnabled = true;
  private Matrix mDrawMatrix;
  private Drawable mDrawable;
  private View.OnClickListener mExternalClickListener;
  private int mFixedHeight = -1;
  private boolean mFlingDebounce;
  private boolean mFullScreen;
  private GestureDetector mGestureDetector;
  private boolean mHaveLayout;
  private OnImageListener mImageListener;
  float mInitialTranslationY;
  private boolean mIsDoubleTouch;
  private boolean mIsPlaceHolder;
  private long mLastTwoFingerUp;
  private boolean mLoadAnimatedImage;
  private Matrix mMatrix = new Matrix();
  private float mMaxScale;
  MediaRef mMediaRef;
  private float mMinScale;
  float mOriginalAspectRatio;
  private Matrix mOriginalMatrix = new Matrix();
  private String mPlusOneText;
  private RotateRunnable mRotateRunnable;
  private float mRotation;
  private float mScaleFactor;
  private ScaleGestureDetector mScaleGetureDetector;
  private ScaleRunnable mScaleRunnable;
  private boolean mShouldTriggerViewLoaded = true;
  private boolean mShowTagShape;
  private SnapRunnable mSnapRunnable;
  private Resource mStaticResource;
  private CharSequence mTagName;
  private RectF mTagNameBackground = new RectF();
  private RectF mTagShape;
  private RectF mTempDst = new RectF();
  private RectF mTempSrc = new RectF();
  private boolean mTransformNoScaling;
  private boolean mTransformVerticalOnly;
  private boolean mTransformsEnabled;
  private RectF mTranslateRect = new RectF();
  private TranslateRunnable mTranslateRunnable;
  private float[] mValues = new float[9];
  private byte[] mVideoBlob;
  private boolean mVideoReady;

  public PhotoHeaderView(Context paramContext)
  {
    super(paramContext);
    if (sImageManager == null)
      sImageManager = ImageResourceManager.getInstance(getContext());
    initialize();
  }

  public PhotoHeaderView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (sImageManager == null)
      sImageManager = ImageResourceManager.getInstance(getContext());
    initialize();
  }

  public PhotoHeaderView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (sImageManager == null)
      sImageManager = ImageResourceManager.getInstance(getContext());
    initialize();
  }

  private void clearDrawable()
  {
    if (this.mDrawable != null)
      this.mDrawable.setCallback(null);
    if ((this.mDrawable instanceof Recyclable))
      ((Recyclable)this.mDrawable).onRecycle();
    this.mDrawable = null;
  }

  private void configureBounds(boolean paramBoolean)
  {
    if ((this.mDrawable == null) || (!this.mHaveLayout))
      return;
    int i = this.mDrawable.getIntrinsicWidth();
    int j = this.mDrawable.getIntrinsicHeight();
    this.mDrawable.setBounds(0, 0, i, j);
    int n;
    int i1;
    if ((paramBoolean) || ((this.mMinScale == 0.0F) && (this.mDrawable != null) && (this.mHaveLayout)))
    {
      int k = this.mDrawable.getIntrinsicWidth();
      int m = this.mDrawable.getIntrinsicHeight();
      n = this.mCropRect.right - this.mCropRect.left;
      i1 = this.mCropRect.bottom - this.mCropRect.top;
      this.mTempSrc.set(0.0F, 0.0F, k, m);
      if (!this.mAllowCrop)
        break label464;
      this.mOriginalAspectRatio = (m / k);
      float f2 = i1 / n;
      if (this.mOriginalAspectRatio <= f2)
        break label392;
      int i4 = (this.mCropRect.top + this.mCropRect.bottom) / 2;
      int i5 = Math.round(n * this.mOriginalAspectRatio) / 2;
      this.mTempDst.set(this.mCropRect.left, i4 - i5, this.mCropRect.right, i4 + i5);
    }
    while (true)
    {
      this.mMatrix.setRectToRect(this.mTempSrc, this.mTempDst, Matrix.ScaleToFit.CENTER);
      if (this.mCropMode == 2)
      {
        this.mMatrix.getValues(this.mValues);
        this.mInitialTranslationY = this.mValues[5];
        if (this.mCoverPhotoOffset != null)
        {
          float f1 = (this.mCoverPhotoOffset.intValue() - getCoverPhotoTopOffset(this.mMatrix)) * (n / 940.0F);
          this.mMatrix.postTranslate(0.0F, f1);
        }
      }
      this.mOriginalMatrix.set(this.mMatrix);
      this.mMinScale = getScale();
      this.mMaxScale = Math.max(2.0F * this.mMinScale, Math.min(8.0F * this.mMinScale, 8.0F));
      this.mDrawMatrix = this.mMatrix;
      break;
      label392: int i2 = (this.mCropRect.right + this.mCropRect.left) / 2;
      int i3 = Math.round(i1 / this.mOriginalAspectRatio) / 2;
      this.mTempDst.set(i2 - i3, this.mCropRect.top, i2 + i3, this.mCropRect.bottom);
      continue;
      label464: this.mTempDst.set(0.0F, 0.0F, getWidth(), getHeight());
    }
  }

  private float getCoverPhotoTopOffset(Matrix paramMatrix)
  {
    if (this.mCropMode != 2);
    int i;
    float f1;
    int j;
    int k;
    for (float f2 = -1.0F; ; f2 = -((j / 2.0F - k / 2.0F - i) / f1 * (940.0F / ((BitmapDrawable)this.mDrawable).getBitmap().getWidth())))
    {
      return f2;
      paramMatrix.getValues(this.mValues);
      i = Math.round(this.mValues[5] - this.mInitialTranslationY);
      f1 = this.mValues[4];
      j = Math.round(f1 * ((BitmapDrawable)this.mDrawable).getBitmap().getHeight());
      k = this.mCropRect.bottom - this.mCropRect.top;
    }
  }

  private float getScale()
  {
    this.mMatrix.getValues(this.mValues);
    return this.mValues[0];
  }

  private void initialize()
  {
    boolean bool = true;
    Context localContext = getContext();
    if (!sInitialized)
    {
      sInitialized = bool;
      Resources localResources = localContext.getApplicationContext().getResources();
      sCommentBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_photodetail_comment);
      sPlusOneBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_photodetail_plus);
      sVideoImage = ImageUtils.decodeResource(localResources, R.drawable.video_overlay);
      sVideoNotReadyImage = ImageUtils.decodeResource(localResources, R.drawable.ic_loading_video);
      sPanoramaImage = ImageUtils.decodeResource(localResources, R.drawable.overlay_lightcycle);
      sBackgroundColor = localResources.getColor(R.color.photo_background_color);
      TextPaint localTextPaint1 = new TextPaint();
      sPlusOneCountPaint = localTextPaint1;
      localTextPaint1.setAntiAlias(bool);
      sPlusOneCountPaint.setColor(localResources.getColor(R.color.photo_info_plusone_count_color));
      sPlusOneCountPaint.setTextSize(localResources.getDimension(R.dimen.photo_info_plusone_text_size));
      TextPaintUtils.registerTextPaint(sPlusOneCountPaint, R.dimen.photo_info_plusone_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sCommentCountPaint = localTextPaint2;
      localTextPaint2.setAntiAlias(bool);
      sCommentCountPaint.setColor(localResources.getColor(R.color.photo_info_comment_count_color));
      sCommentCountPaint.setTextSize(localResources.getDimension(R.dimen.photo_info_comment_text_size));
      TextPaintUtils.registerTextPaint(sCommentCountPaint, R.dimen.photo_info_comment_text_size);
      Paint localPaint1 = new Paint();
      sTagPaint = localPaint1;
      localPaint1.setAntiAlias(bool);
      sTagPaint.setColor(localResources.getColor(R.color.photo_tag_color));
      sTagPaint.setStyle(Paint.Style.STROKE);
      sTagPaint.setStrokeWidth(localResources.getDimension(R.dimen.photo_tag_stroke_width));
      sTagPaint.setShadowLayer(localResources.getDimension(R.dimen.photo_tag_shadow_radius), 0.0F, 0.0F, localResources.getColor(R.color.photo_tag_shadow_color));
      sCropSizeProfile = localResources.getDimensionPixelSize(R.dimen.photo_crop_profile_width);
      sCropSizeCoverWidth = localResources.getDimensionPixelSize(R.dimen.photo_crop_cover_width);
      Paint localPaint2 = new Paint();
      sCropDimPaint = localPaint2;
      localPaint2.setAntiAlias(bool);
      sCropDimPaint.setColor(localResources.getColor(R.color.photo_crop_dim_color));
      sCropDimPaint.setStyle(Paint.Style.FILL);
      Paint localPaint3 = new Paint();
      sCropPaint = localPaint3;
      localPaint3.setAntiAlias(bool);
      sCropPaint.setColor(localResources.getColor(R.color.photo_crop_highlight_color));
      sCropPaint.setStyle(Paint.Style.STROKE);
      sCropPaint.setStrokeWidth(localResources.getDimension(R.dimen.photo_crop_stroke_width));
      TextPaint localTextPaint3 = new TextPaint();
      sTagTextPaint = localTextPaint3;
      localTextPaint3.setAntiAlias(bool);
      sTagTextPaint.setColor(localResources.getColor(R.color.photo_tag_text_color));
      sTagTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
      sTagTextPaint.setTextSize(localResources.getDimension(R.dimen.photo_tag_text_size));
      sTagTextPaint.setShadowLayer(0.0F, 0.0F, 0.0F, -16777216);
      TextPaintUtils.registerTextPaint(sTagTextPaint, R.dimen.photo_tag_text_size);
      TextPaint localTextPaint4 = new TextPaint();
      sProcessingMediaTitleTextPaint = localTextPaint4;
      localTextPaint4.setColor(localResources.getColor(R.color.photo_processing_text_color));
      sProcessingMediaTitleTextPaint.setTextSize(localResources.getDimension(R.dimen.photo_processing_message_title_size));
      sProcessingMediaTitleTextPaint.setAntiAlias(bool);
      sProcessingMediaTitleTextPaint.setFakeBoldText(bool);
      sProcessingMediaTitleTextPaint.setStyle(Paint.Style.FILL);
      sProcessingMediaTitleTextPaint.setTextAlign(Paint.Align.CENTER);
      TextPaint localTextPaint5 = new TextPaint();
      sProcessingMediaSubTitleTextPaint = localTextPaint5;
      localTextPaint5.setColor(localResources.getColor(R.color.photo_processing_text_color));
      sProcessingMediaSubTitleTextPaint.setTextSize(localResources.getDimension(R.dimen.photo_processing_message_subtitle_size));
      sProcessingMediaSubTitleTextPaint.setAntiAlias(bool);
      sProcessingMediaSubTitleTextPaint.setFakeBoldText(bool);
      sProcessingMediaSubTitleTextPaint.setTextAlign(Paint.Align.CENTER);
      Paint localPaint4 = new Paint();
      sProcessingMediaBackgroundPaint = localPaint4;
      localPaint4.setColor(localResources.getColor(R.color.photo_processing_background_color));
      sProcessingMediaTitle = localResources.getString(R.string.media_processing_message);
      sProcessingMediaSubTitle = localResources.getString(R.string.media_processing_message_subtitle);
      sProcessingMediaTitleVerticalPosition = (int)localResources.getDimension(R.dimen.photo_processing_message_title_vertical_position);
      sProcessingMediaSubTitleVerticalPosition = (int)localResources.getDimension(R.dimen.photo_processing_message_subtitle_vertical_position);
      Paint localPaint5 = new Paint();
      sTagTextBackgroundPaint = localPaint5;
      localPaint5.setColor(localResources.getColor(R.color.photo_tag_text_background_color));
      sTagTextBackgroundPaint.setStyle(Paint.Style.FILL);
      sPhotoOverlayRightPadding = (int)localResources.getDimension(R.dimen.photo_overlay_right_padding);
      sPhotoOverlayBottomPadding = (int)localResources.getDimension(R.dimen.photo_overlay_bottom_padding);
      sCommentCountLeftMargin = (int)localResources.getDimension(R.dimen.photo_info_comment_count_left_margin);
      sCommentCountTextWidth = (int)localResources.getDimension(R.dimen.photo_info_comment_count_text_width);
      sPlusOneCountLeftMargin = (int)localResources.getDimension(R.dimen.photo_info_plusone_count_left_margin);
      sPlusOneCountTextWidth = (int)localResources.getDimension(R.dimen.photo_info_plusone_count_text_width);
      sPlusOneBottomMargin = (int)localResources.getDimension(R.dimen.photo_info_plusone_bottom_margin);
      sTagTextPadding = (int)localResources.getDimension(R.dimen.photo_tag_text_padding);
      sHasMultitouchDistinct = localContext.getPackageManager().hasSystemFeature("android.hardware.touchscreen.multitouch.distinct");
    }
    if (!sHasMultitouchDistinct);
    while (true)
    {
      this.mGestureDetector = new GestureDetector(localContext, this, null, bool);
      this.mScaleGetureDetector = new ScaleGestureDetector(localContext, this);
      this.mScaleRunnable = new ScaleRunnable(this);
      this.mTranslateRunnable = new TranslateRunnable(this);
      this.mSnapRunnable = new SnapRunnable(this);
      this.mRotateRunnable = new RotateRunnable(this);
      return;
      bool = false;
    }
  }

  public static void onStart()
  {
  }

  public static void onStop()
  {
  }

  private void scale(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.mMatrix.postRotate(-this.mRotation, getWidth() / 2, getHeight() / 2);
    float f1 = Math.min(Math.max(paramFloat1, this.mMinScale), this.mMaxScale);
    float f2 = f1 / getScale();
    this.mMatrix.postScale(f2, f2, paramFloat2, paramFloat3);
    snap();
    this.mMatrix.postRotate(this.mRotation, getWidth() / 2, getHeight() / 2);
    invalidate();
    if (this.mImageListener != null)
      this.mImageListener.onImageScaled(f1 / this.mMinScale);
  }

  private void snap()
  {
    this.mTranslateRect.set(this.mTempSrc);
    this.mMatrix.mapRect(this.mTranslateRect);
    float f1;
    float f2;
    label55: float f3;
    float f4;
    float f5;
    label100: float f6;
    label117: float f7;
    label134: float f8;
    float f9;
    float f10;
    if (this.mAllowCrop)
    {
      f1 = this.mCropRect.left;
      if (!this.mAllowCrop)
        break label226;
      f2 = this.mCropRect.right;
      f3 = this.mTranslateRect.left;
      f4 = this.mTranslateRect.right;
      if (f4 - f3 >= f2 - f1)
        break label235;
      f5 = f1 + (f2 - f1 - (f4 + f3)) / 2.0F;
      if (!this.mAllowCrop)
        break label273;
      f6 = this.mCropRect.top;
      if (!this.mAllowCrop)
        break label279;
      f7 = this.mCropRect.bottom;
      f8 = this.mTranslateRect.top;
      f9 = this.mTranslateRect.bottom;
      if (f9 - f8 >= f7 - f6)
        break label289;
      f10 = f6 + (f7 - f6 - (f9 + f8)) / 2.0F;
      label184: if ((Math.abs(f5) <= 20.0F) && (Math.abs(f10) <= 20.0F))
        break label331;
      this.mSnapRunnable.start(f5, f10);
    }
    while (true)
    {
      return;
      f1 = 0.0F;
      break;
      label226: f2 = getWidth();
      break label55;
      label235: if (f3 > f1)
      {
        f5 = f1 - f3;
        break label100;
      }
      if (f4 < f2)
      {
        f5 = f2 - f4;
        break label100;
      }
      f5 = 0.0F;
      break label100;
      label273: f6 = 0.0F;
      break label117;
      label279: f7 = getHeight();
      break label134;
      label289: if (f8 > f6)
      {
        f10 = f6 - f8;
        break label184;
      }
      if (f9 < f7)
      {
        f10 = f7 - f9;
        break label184;
      }
      f10 = 0.0F;
      break label184;
      label331: this.mMatrix.postTranslate(f5, f10);
      invalidate();
    }
  }

  private boolean translate(float paramFloat1, float paramFloat2)
  {
    this.mTranslateRect.set(this.mTempSrc);
    this.mMatrix.mapRect(this.mTranslateRect);
    float f1;
    float f2;
    label57: float f3;
    float f4;
    float f5;
    label111: float f6;
    label128: float f7;
    label145: float f8;
    float f9;
    float f10;
    if (this.mAllowCrop)
    {
      f1 = this.mCropRect.left;
      if (!this.mAllowCrop)
        break label241;
      f2 = this.mCropRect.right;
      f3 = this.mTranslateRect.left;
      f4 = this.mTranslateRect.right;
      if (!this.mAllowCrop)
        break label251;
      f5 = Math.max(f1 - this.mTranslateRect.right, Math.min(f2 - this.mTranslateRect.left, paramFloat1));
      if (!this.mAllowCrop)
        break label308;
      f6 = this.mCropRect.top;
      if (!this.mAllowCrop)
        break label314;
      f7 = this.mCropRect.bottom;
      f8 = this.mTranslateRect.top;
      f9 = this.mTranslateRect.bottom;
      if (!this.mAllowCrop)
        break label324;
      f10 = Math.max(f6 - this.mTranslateRect.bottom, Math.min(f7 - this.mTranslateRect.top, paramFloat2));
      label199: this.mMatrix.postTranslate(f5, f10);
      invalidate();
      if ((f5 != paramFloat1) || (f10 != paramFloat2))
        break label381;
    }
    label241: label251: label381: for (boolean bool = true; ; bool = false)
    {
      return bool;
      f1 = 0.0F;
      break;
      f2 = getWidth();
      break label57;
      if (f4 - f3 < f2 - f1)
      {
        f5 = f1 + (f2 - f1 - (f4 + f3)) / 2.0F;
        break label111;
      }
      f5 = Math.max(f2 - f4, Math.min(f1 - f3, paramFloat1));
      break label111;
      f6 = 0.0F;
      break label128;
      f7 = getHeight();
      break label145;
      if (f9 - f8 < f7 - f6)
      {
        f10 = f6 + (f7 - f6 - (f9 + f8)) / 2.0F;
        break label199;
      }
      f10 = Math.max(f7 - f9, Math.min(f6 - f8, paramFloat2));
      break label199;
    }
  }

  public final void bindResources()
  {
    MediaRef localMediaRef;
    if ((ViewUtils.isViewAttached(this)) && (this.mMediaRef != null))
    {
      localMediaRef = this.mMediaRef;
      String str = this.mMediaRef.getUrl();
      if ((FIFEUtil.isFifeHostedUrl(str)) && (!this.mAllowCrop))
      {
        this.mLoadAnimatedImage = true;
        localMediaRef = new MediaRef(this.mMediaRef.getOwnerGaiaId(), this.mMediaRef.getPhotoId(), str, this.mMediaRef.getLocalUri(), this.mMediaRef.getDisplayName(), this.mMediaRef.getType());
      }
      if (this.mCropMode != 2)
        break label116;
    }
    label116: for (this.mStaticResource = sImageManager.getMedia(localMediaRef, 940, 0, 0, this); ; this.mStaticResource = sImageManager.getMedia(localMediaRef, 3, this))
      return;
  }

  public final void bindTagData(RectF paramRectF, CharSequence paramCharSequence)
  {
    this.mTagShape = paramRectF;
    this.mTagName = paramCharSequence;
  }

  public final void destroy()
  {
    this.mGestureDetector = null;
    this.mScaleGetureDetector = null;
    this.mScaleRunnable.stop();
    this.mScaleRunnable = null;
    this.mTranslateRunnable.stop();
    this.mTranslateRunnable = null;
    this.mSnapRunnable.stop();
    this.mSnapRunnable = null;
    this.mRotateRunnable.stop();
    this.mRotateRunnable = null;
    setOnClickListener(null);
    this.mExternalClickListener = null;
    clearDrawable();
    unbindResources();
  }

  public final void doAnimate(boolean paramBoolean)
  {
    if (this.mAnimate != paramBoolean)
    {
      this.mAnimate = paramBoolean;
      if ((this.mDrawable instanceof GifDrawable))
        ((GifDrawable)this.mDrawable).setAnimationEnabled(this.mAnimate);
      invalidate();
    }
  }

  public final void enableImageTransforms(boolean paramBoolean)
  {
    this.mTransformsEnabled = paramBoolean;
    if (!this.mTransformsEnabled)
      resetTransformations();
  }

  public final Bitmap getBitmap()
  {
    if ((this.mDrawable instanceof BitmapDrawable));
    for (Bitmap localBitmap = ((BitmapDrawable)this.mDrawable).getBitmap(); ; localBitmap = null)
      return localBitmap;
  }

  public final int getCoverPhotoTopOffset()
  {
    return Math.round(getCoverPhotoTopOffset(this.mDrawMatrix));
  }

  public final Bitmap getCroppedPhoto()
  {
    Bitmap localBitmap;
    if (!this.mAllowCrop)
    {
      localBitmap = null;
      return localBitmap;
    }
    float f1;
    float f2;
    float f4;
    float f3;
    int j;
    int k;
    Matrix localMatrix1;
    switch (this.mCropMode)
    {
    default:
      f1 = 256.0F;
      f2 = f1;
      f4 = 256.0F / (this.mCropRect.right - this.mCropRect.left);
      f3 = f4;
      j = -this.mCropRect.left;
      k = -this.mCropRect.top;
      localMatrix1 = this.mDrawMatrix;
    case 2:
    }
    while (true)
    {
      localBitmap = Bitmap.createBitmap((int)f1, (int)f2, Bitmap.Config.ARGB_8888);
      Canvas localCanvas = new Canvas(localBitmap);
      Matrix localMatrix2 = new Matrix(localMatrix1);
      if (j + k != 0)
        localMatrix2.postTranslate(j, k);
      if (f4 + f3 != 0.0F)
        localMatrix2.postScale(f4, f3);
      localCanvas.drawColor(this.mBackgroundColor);
      if (this.mDrawable == null)
        break;
      localCanvas.concat(localMatrix2);
      this.mDrawable.draw(localCanvas);
      break;
      int i = ((BitmapDrawable)this.mDrawable).getBitmap().getWidth();
      if (i < 1024.0F)
      {
        localBitmap = ((BitmapDrawable)this.mDrawable).getBitmap();
        break;
      }
      f1 = 940.0F;
      f2 = 940.0F * this.mOriginalAspectRatio;
      f3 = 940.0F / i;
      f4 = f3;
      localMatrix1 = null;
      j = 0;
      k = 0;
    }
  }

  public final byte[] getVideoData()
  {
    return this.mVideoBlob;
  }

  public final void init(MediaRef paramMediaRef, int paramInt)
  {
    if (((this.mMediaRef != null) && (this.mMediaRef.equals(paramMediaRef))) || (this.mMediaRef == paramMediaRef));
    while (true)
    {
      return;
      unbindResources();
      clearDrawable();
      this.mMediaRef = paramMediaRef;
      this.mBackgroundColor = paramInt;
      bindResources();
      requestLayout();
      invalidate();
    }
  }

  public final void init(MediaRef paramMediaRef, boolean paramBoolean)
  {
    this.mIsPlaceHolder = paramBoolean;
    init(paramMediaRef, sBackgroundColor);
  }

  public void invalidateDrawable(Drawable paramDrawable)
  {
    if (paramDrawable == this.mDrawable)
      invalidate();
    while (true)
    {
      return;
      super.invalidateDrawable(paramDrawable);
    }
  }

  public final boolean isPanorama()
  {
    boolean bool = true;
    if ((this.mMediaRef != null) && (MediaRef.MediaType.PANORAMA == this.mMediaRef.getType()));
    while (true)
    {
      return bool;
      if ((this.mStaticResource == null) || (this.mStaticResource.getResourceType() != 2))
        bool = false;
    }
  }

  public final boolean isPhotoBound()
  {
    if (this.mDrawable != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isVideo()
  {
    if (this.mVideoBlob != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isVideoReady()
  {
    if ((this.mVideoBlob != null) && (this.mVideoReady));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public void jumpDrawablesToCurrentState()
  {
    super.jumpDrawablesToCurrentState();
    if (this.mDrawable != null)
      this.mDrawable.jumpToCurrentState();
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    bindResources();
    Drawable localDrawable;
    if (this.mDrawable != null)
    {
      localDrawable = this.mDrawable;
      if (getVisibility() != 0)
        break label37;
    }
    label37: for (boolean bool = true; ; bool = false)
    {
      localDrawable.setVisible(bool, false);
      return;
    }
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    unbindResources();
    if (this.mDrawable != null)
      this.mDrawable.setVisible(false, false);
  }

  public boolean onDoubleTap(MotionEvent paramMotionEvent)
  {
    if ((this.mDoubleTapToZoomEnabled) && (this.mTransformsEnabled))
    {
      if (!this.mDoubleTapDebounce)
      {
        float f1 = getScale();
        float f2 = f1 * 1.5F;
        float f3 = Math.max(this.mMinScale, f2);
        float f4 = Math.min(this.mMaxScale, f3);
        this.mScaleRunnable.start(f1, f4, paramMotionEvent.getX(), paramMotionEvent.getY());
      }
      this.mDoubleTapDebounce = false;
    }
    return true;
  }

  public boolean onDoubleTapEvent(MotionEvent paramMotionEvent)
  {
    return true;
  }

  public boolean onDown(MotionEvent paramMotionEvent)
  {
    if (this.mTransformsEnabled)
    {
      this.mTranslateRunnable.stop();
      this.mSnapRunnable.stop();
    }
    return true;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    paramCanvas.drawColor(this.mBackgroundColor);
    if (this.mIsPlaceHolder)
    {
      paramCanvas.drawRect(0.0F, 0.0F, getWidth(), getHeight(), sProcessingMediaBackgroundPaint);
      paramCanvas.drawText(sProcessingMediaTitle, getWidth() / 2, sProcessingMediaTitleVerticalPosition, sProcessingMediaTitleTextPaint);
      paramCanvas.drawText(sProcessingMediaSubTitle, getWidth() / 2, sProcessingMediaSubTitleVerticalPosition, sProcessingMediaSubTitleTextPaint);
    }
    while (this.mDrawable == null)
    {
      int n = getHeight() - sPhotoOverlayBottomPadding;
      if ((this.mFullScreen) && (this.mCommentText != null) && (!this.mAllowCrop))
      {
        int i6 = (int)(sCommentCountPaint.ascent() - sCommentCountPaint.descent());
        int i7 = Math.max(sCommentBitmap.getHeight(), i6);
        int i8 = getWidth() - sPhotoOverlayRightPadding - sCommentCountTextWidth;
        int i9 = n - i7;
        paramCanvas.drawText(this.mCommentText, i8, i9 - sCommentCountPaint.ascent(), sCommentCountPaint);
        int i10 = i8 - (sCommentCountLeftMargin + sCommentBitmap.getWidth());
        paramCanvas.drawBitmap(sCommentBitmap, i10, i9, null);
        n = i9 - sPlusOneBottomMargin;
      }
      if ((this.mFullScreen) && (this.mPlusOneText != null) && (!this.mAllowCrop))
      {
        int i1 = (int)(sPlusOneCountPaint.ascent() - sPlusOneCountPaint.descent());
        int i2 = Math.max(sPlusOneBitmap.getHeight(), i1);
        int i3 = getWidth() - sPhotoOverlayRightPadding - sPlusOneCountTextWidth;
        int i4 = n - i2;
        paramCanvas.drawText(this.mPlusOneText, i3, i4 - sPlusOneCountPaint.ascent(), sPlusOneCountPaint);
        int i5 = i3 - (sPlusOneCountLeftMargin + sPlusOneBitmap.getWidth());
        paramCanvas.drawBitmap(sPlusOneBitmap, i5, i4, null);
      }
      return;
    }
    int i = paramCanvas.getSaveCount();
    paramCanvas.save();
    if (this.mDrawMatrix != null)
      paramCanvas.concat(this.mDrawMatrix);
    this.mDrawable.draw(paramCanvas);
    paramCanvas.restoreToCount(i);
    Bitmap localBitmap;
    label412: label461: float f4;
    float f5;
    float f6;
    float f12;
    float f13;
    float f14;
    if (this.mVideoBlob != null)
      if (this.mVideoReady)
      {
        localBitmap = sVideoImage;
        int i11 = (getWidth() - localBitmap.getWidth()) / 2;
        int i12 = (getHeight() - localBitmap.getHeight()) / 2;
        float f18 = i11;
        float f19 = i12;
        paramCanvas.drawBitmap(localBitmap, f18, f19, null);
        this.mTranslateRect.set(this.mDrawable.getBounds());
        if (this.mDrawMatrix != null)
          this.mDrawMatrix.mapRect(this.mTranslateRect);
        if ((this.mShowTagShape) && (this.mTagShape != null))
        {
          float f1 = this.mTranslateRect.width();
          float f2 = this.mTranslateRect.height();
          float f3 = f1 * this.mTagShape.left + this.mTranslateRect.left;
          f4 = f2 * this.mTagShape.top + this.mTranslateRect.top;
          f5 = f1 * this.mTagShape.right + this.mTranslateRect.left;
          f6 = f2 * this.mTagShape.bottom + this.mTranslateRect.top;
          paramCanvas.drawRect(f3, f4, f5, f6, sTagPaint);
          if (this.mTagName != null)
          {
            float f7 = 2.0F * sTagTextPadding;
            float f8 = f3 + (f5 - f3) / 2.0F;
            float f9 = sTagTextPaint.measureText(this.mTagName, 0, this.mTagName.length());
            float f10 = sTagTextPaint.descent() - sTagTextPaint.ascent();
            float f11 = f9 + f7;
            f12 = f10 + f7;
            f13 = f8 - f11 / 2.0F;
            if (f13 < 0.0F)
              f13 = 0.0F;
            f14 = f13 + f11;
            if (f14 <= getWidth())
              break label1008;
            f13 = f5 - f11;
          }
        }
      }
    while (true)
    {
      float f15 = f6 + f12;
      if (f15 > getHeight())
        f6 = f4 - f12;
      while (true)
      {
        float f16 = f13 + sTagTextPadding;
        float f17 = f6 + sTagTextPadding;
        this.mTagNameBackground.set(f13, f6, f5, f4);
        paramCanvas.drawRoundRect(this.mTagNameBackground, 3.0F, 3.0F, sTagTextBackgroundPaint);
        paramCanvas.drawText(this.mTagName, 0, this.mTagName.length(), f16, f17 - sTagTextPaint.ascent(), sTagTextPaint);
        if (!this.mAllowCrop)
          break;
        int m = paramCanvas.getSaveCount();
        paramCanvas.drawRect(0.0F, 0.0F, getWidth(), getHeight(), sCropDimPaint);
        paramCanvas.save();
        paramCanvas.clipRect(this.mCropRect);
        if (this.mDrawMatrix != null)
          paramCanvas.concat(this.mDrawMatrix);
        this.mDrawable.draw(paramCanvas);
        paramCanvas.restoreToCount(m);
        paramCanvas.drawRect(this.mCropRect, sCropPaint);
        break;
        localBitmap = sVideoNotReadyImage;
        break label412;
        if (!isPanorama())
          break label461;
        int j = (getWidth() - sPanoramaImage.getWidth()) / 2;
        int k = (getHeight() - sPanoramaImage.getHeight()) / 2;
        paramCanvas.drawBitmap(sPanoramaImage, j, k, null);
        break label461;
        f4 = f15;
      }
      label1008: f5 = f14;
    }
  }

  public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    if (this.mTransformsEnabled)
    {
      if (this.mTransformVerticalOnly)
        paramFloat1 = 0.0F;
      if (!this.mFlingDebounce)
        this.mTranslateRunnable.start(paramFloat1, paramFloat2);
      this.mFlingDebounce = false;
    }
    return true;
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mHaveLayout = true;
    int i = getWidth();
    int j = getHeight();
    int k;
    int m;
    if (this.mAllowCrop)
      switch (this.mCropMode)
      {
      default:
        k = Math.min(i, j);
        m = sCropSizeProfile;
      case 2:
      }
    for (float f = 1.0F; ; f = 5.222222F)
    {
      this.mCropSizeWidth = Math.min(m, k);
      this.mCropSizeHeight = ((int)(this.mCropSizeWidth / f));
      int n = (i - this.mCropSizeWidth) / 2;
      int i1 = (j - this.mCropSizeHeight) / 2;
      int i2 = n + this.mCropSizeWidth;
      int i3 = i1 + this.mCropSizeHeight;
      this.mCropRect.set(n, i1, i2, i3);
      configureBounds(paramBoolean);
      return;
      k = i - (int)(0.1F * i);
      m = sCropSizeCoverWidth;
    }
  }

  public void onLongPress(MotionEvent paramMotionEvent)
  {
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mFixedHeight != -1)
    {
      super.onMeasure(paramInt1, View.MeasureSpec.makeMeasureSpec(this.mFixedHeight, -2147483648));
      setMeasuredDimension(getMeasuredWidth(), this.mFixedHeight);
    }
    while (true)
    {
      return;
      super.onMeasure(paramInt1, paramInt2);
    }
  }

  public final void onResourceStatusChange$1574fca0(Resource paramResource)
  {
    int i = paramResource.getStatus();
    int j = 0;
    Object localObject;
    Bitmap localBitmap;
    boolean bool;
    switch (i)
    {
    case 2:
    case 3:
    case 5:
    default:
      if ((j != 0) && (this.mShouldTriggerViewLoaded) && (this.mImageListener != null))
      {
        this.mImageListener.onImageLoadFinished(this);
        this.mShouldTriggerViewLoaded = false;
      }
      invalidate();
      return;
    case 1:
      clearDrawable();
      localObject = paramResource.getResource();
      if ((localObject instanceof Bitmap))
      {
        localBitmap = (Bitmap)localObject;
        if (this.mDrawable == null)
          break label370;
        this.mDrawable.setCallback(null);
        if ((!(this.mDrawable instanceof BitmapDrawable)) || (localBitmap != ((BitmapDrawable)this.mDrawable).getBitmap()))
        {
          if ((localBitmap == null) || ((this.mDrawable.getIntrinsicWidth() == localBitmap.getWidth()) && (this.mDrawable.getIntrinsicHeight() == localBitmap.getHeight())))
            break label306;
          bool = true;
          label189: this.mMinScale = 0.0F;
          clearDrawable();
        }
      }
      break;
    case 4:
    case 6:
    }
    while (true)
    {
      if ((this.mDrawable == null) && (localBitmap != null))
      {
        this.mDrawable = new BitmapDrawable(getResources(), localBitmap);
        this.mDrawable.setCallback(this);
      }
      configureBounds(bool);
      invalidate();
      while (true)
      {
        if (this.mLoadAnimatedImage)
        {
          if (this.mLoadAnimatedImage)
          {
            if (this.mAnimatedResource != null)
              this.mAnimatedResource.unregister(this);
            this.mLoadAnimatedImage = false;
            this.mAnimatedResource = sImageManager.getMedia(this.mMediaRef, 3, 4, this);
          }
          this.mLoadAnimatedImage = false;
        }
        j = 1;
        break;
        label306: bool = false;
        break label189;
        if ((localObject instanceof GifImage))
        {
          GifDrawable localGifDrawable = new GifDrawable((GifImage)localObject);
          localGifDrawable.setAnimationEnabled(this.mAnimate);
          this.mDrawable = localGifDrawable;
          this.mDrawable.setCallback(this);
          configureBounds(false);
        }
      }
      j = 1;
      break;
      label370: bool = false;
    }
  }

  public boolean onScale(ScaleGestureDetector paramScaleGestureDetector)
  {
    if (this.mTransformNoScaling);
    while (true)
    {
      return true;
      float f = paramScaleGestureDetector.getScaleFactor() - 1.0F;
      if (((f < 0.0F) && (this.mScaleFactor > 0.0F)) || ((f > 0.0F) && (this.mScaleFactor < 0.0F)))
        this.mScaleFactor = 0.0F;
      this.mScaleFactor = (f + this.mScaleFactor);
      if ((this.mTransformsEnabled) && (Math.abs(this.mScaleFactor) > 0.04F))
      {
        this.mIsDoubleTouch = false;
        scale(getScale() * paramScaleGestureDetector.getScaleFactor(), paramScaleGestureDetector.getFocusX(), paramScaleGestureDetector.getFocusY());
      }
    }
  }

  public boolean onScaleBegin(ScaleGestureDetector paramScaleGestureDetector)
  {
    if (this.mTransformsEnabled)
    {
      this.mScaleRunnable.stop();
      this.mIsDoubleTouch = true;
      this.mScaleFactor = 0.0F;
    }
    return true;
  }

  public void onScaleEnd(ScaleGestureDetector paramScaleGestureDetector)
  {
    if ((this.mTransformsEnabled) && (this.mIsDoubleTouch))
    {
      this.mDoubleTapDebounce = true;
      resetTransformations();
    }
    this.mFlingDebounce = true;
  }

  public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    long l = Math.abs(paramMotionEvent2.getEventTime() - this.mLastTwoFingerUp);
    if (this.mTransformVerticalOnly)
      paramFloat1 = 0.0F;
    if ((l > 400L) && (this.mTransformsEnabled))
      translate(-paramFloat1, -paramFloat2);
    return true;
  }

  public void onShowPress(MotionEvent paramMotionEvent)
  {
  }

  public boolean onSingleTapConfirmed(MotionEvent paramMotionEvent)
  {
    if ((this.mExternalClickListener != null) && (!this.mIsDoubleTouch))
      this.mExternalClickListener.onClick(this);
    this.mIsDoubleTouch = false;
    return true;
  }

  public boolean onSingleTapUp(MotionEvent paramMotionEvent)
  {
    return false;
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((this.mScaleGetureDetector == null) || (this.mGestureDetector == null))
      return true;
    this.mScaleGetureDetector.onTouchEvent(paramMotionEvent);
    this.mGestureDetector.onTouchEvent(paramMotionEvent);
    switch (paramMotionEvent.getActionMasked())
    {
    default:
    case 6:
    }
    while (true)
      switch (paramMotionEvent.getAction())
      {
      case 2:
      default:
        break;
      case 1:
      case 3:
        if (this.mTranslateRunnable.mRunning)
          break;
        snap();
        break;
        if (paramMotionEvent.getPointerCount() == 2)
          this.mLastTwoFingerUp = paramMotionEvent.getEventTime();
        else if (paramMotionEvent.getPointerCount() == 1)
          this.mLastTwoFingerUp = 0L;
        break;
      }
  }

  public final void resetTransformations()
  {
    this.mMatrix.set(this.mOriginalMatrix);
    invalidate();
  }

  public void setCommentCount(int paramInt)
  {
    String str = this.mCommentText;
    if (paramInt < 0);
    label69: 
    while (true)
    {
      return;
      if (paramInt == 0)
        this.mCommentText = null;
      while (true)
      {
        if (TextUtils.equals(str, this.mCommentText))
          break label69;
        invalidate();
        break;
        if (paramInt > 99)
          this.mCommentText = getResources().getString(R.string.ninety_nine_plus);
        else
          this.mCommentText = Integer.toString(paramInt);
      }
    }
  }

  public void setCropMode(int paramInt)
  {
    if (paramInt != 0);
    for (boolean bool = true; (bool) && (this.mHaveLayout); bool = false)
      throw new IllegalArgumentException("Cannot set crop after view has been laid out");
    if ((!bool) && (this.mAllowCrop))
      throw new IllegalArgumentException("Cannot unset crop mode");
    this.mAllowCrop = bool;
    this.mCropMode = paramInt;
    if (this.mCropMode == 2)
    {
      this.mTransformVerticalOnly = true;
      this.mTransformNoScaling = true;
      this.mDoubleTapToZoomEnabled = false;
    }
  }

  public void setCropModeCoverPhotoOffset(Integer paramInteger)
  {
    this.mCoverPhotoOffset = paramInteger;
  }

  public void setFixedHeight(int paramInt)
  {
    if (paramInt != this.mFixedHeight);
    for (int i = 1; ; i = 0)
    {
      this.mFixedHeight = paramInt;
      setMeasuredDimension(getMeasuredWidth(), this.mFixedHeight);
      if (i != 0)
      {
        configureBounds(true);
        requestLayout();
      }
      return;
    }
  }

  public void setFullScreen(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean1 != this.mFullScreen)
    {
      this.mFullScreen = paramBoolean1;
      if (!this.mFullScreen)
      {
        this.mScaleRunnable.stop();
        this.mTranslateRunnable.stop();
        this.mRotateRunnable.stop();
      }
      requestLayout();
      invalidate();
    }
  }

  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mExternalClickListener = paramOnClickListener;
  }

  public void setOnImageListener(OnImageListener paramOnImageListener)
  {
    this.mImageListener = paramOnImageListener;
  }

  public void setPlusOneCount(int paramInt)
  {
    String str = this.mPlusOneText;
    if (paramInt < 0);
    label69: 
    while (true)
    {
      return;
      if (paramInt == 0)
        this.mPlusOneText = null;
      while (true)
      {
        if (TextUtils.equals(str, this.mPlusOneText))
          break label69;
        invalidate();
        break;
        if (paramInt > 99)
          this.mPlusOneText = getResources().getString(R.string.ninety_nine_plus);
        else
          this.mPlusOneText = Integer.toString(paramInt);
      }
    }
  }

  public void setVideoBlob(byte[] paramArrayOfByte)
  {
    this.mVideoBlob = paramArrayOfByte;
    if (paramArrayOfByte != null)
    {
      DataVideo localDataVideo = (DataVideo)DataVideoJson.getInstance().fromByteArray(paramArrayOfByte);
      if ((!TextUtils.equals("FINAL", localDataVideo.status)) && (!TextUtils.equals("READY", localDataVideo.status)))
        break label54;
    }
    label54: for (boolean bool = true; ; bool = false)
    {
      this.mVideoReady = bool;
      return;
    }
  }

  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
    Drawable localDrawable;
    if (this.mDrawable != null)
    {
      localDrawable = this.mDrawable;
      if (paramInt != 0)
        break label31;
    }
    label31: for (boolean bool = true; ; bool = false)
    {
      localDrawable.setVisible(bool, false);
      return;
    }
  }

  public final void showTagShape()
  {
    this.mShowTagShape = true;
    invalidate();
  }

  public final void unbindResources()
  {
    if (this.mStaticResource != null)
    {
      this.mStaticResource.unregister(this);
      this.mStaticResource = null;
    }
    if (this.mAnimatedResource != null)
    {
      this.mAnimatedResource.unregister(this);
      this.mAnimatedResource = null;
    }
  }

  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    if ((this.mDrawable == paramDrawable) || (super.verifyDrawable(paramDrawable)));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static abstract interface OnImageListener
  {
    public abstract void onImageLoadFinished(PhotoHeaderView paramPhotoHeaderView);

    public abstract void onImageScaled(float paramFloat);
  }

  private static final class RotateRunnable
    implements Runnable
  {
    private float mAppliedRotation;
    private final PhotoHeaderView mHeader;
    private long mLastRuntime;
    private boolean mRunning;
    private boolean mStop;
    private float mTargetRotation;
    private float mVelocity;

    public RotateRunnable(PhotoHeaderView paramPhotoHeaderView)
    {
      this.mHeader = paramPhotoHeaderView;
    }

    public final void run()
    {
      if (this.mStop)
        return;
      long l1;
      if (this.mAppliedRotation != this.mTargetRotation)
      {
        l1 = System.currentTimeMillis();
        if (this.mLastRuntime == -1L)
          break label179;
      }
      label179: for (long l2 = l1 - this.mLastRuntime; ; l2 = 0L)
      {
        float f = this.mVelocity * (float)l2;
        if (((this.mAppliedRotation < this.mTargetRotation) && (f + this.mAppliedRotation > this.mTargetRotation)) || ((this.mAppliedRotation > this.mTargetRotation) && (f + this.mAppliedRotation < this.mTargetRotation)))
          f = this.mTargetRotation - this.mAppliedRotation;
        PhotoHeaderView.access$400(this.mHeader, f, false);
        this.mAppliedRotation = (f + this.mAppliedRotation);
        if (this.mAppliedRotation == this.mTargetRotation)
          stop();
        this.mLastRuntime = l1;
        if (this.mStop)
          break;
        this.mHeader.post(this);
        break;
      }
    }

    public final void start(float paramFloat)
    {
      if (this.mRunning);
      while (true)
      {
        return;
        this.mTargetRotation = paramFloat;
        this.mVelocity = (this.mTargetRotation / 500.0F);
        this.mAppliedRotation = 0.0F;
        this.mLastRuntime = -1L;
        this.mStop = false;
        this.mRunning = true;
        this.mHeader.post(this);
      }
    }

    public final void stop()
    {
      this.mRunning = false;
      this.mStop = true;
    }
  }

  private static final class ScaleRunnable
    implements Runnable
  {
    private float mCenterX;
    private float mCenterY;
    private final PhotoHeaderView mHeader;
    private boolean mRunning;
    private float mStartScale;
    private long mStartTime;
    private boolean mStop;
    private float mTargetScale;
    private float mVelocity;
    private boolean mZoomingIn;

    public ScaleRunnable(PhotoHeaderView paramPhotoHeaderView)
    {
      this.mHeader = paramPhotoHeaderView;
    }

    public final void run()
    {
      if (this.mStop)
        return;
      long l = System.currentTimeMillis() - this.mStartTime;
      float f = this.mStartScale + this.mVelocity * (float)l;
      this.mHeader.scale(f, this.mCenterX, this.mCenterY);
      boolean bool1;
      if (f != this.mTargetScale)
      {
        bool1 = this.mZoomingIn;
        if (f <= this.mTargetScale)
          break label122;
      }
      label122: for (boolean bool2 = true; ; bool2 = false)
      {
        if (bool1 == bool2)
        {
          this.mHeader.scale(this.mTargetScale, this.mCenterX, this.mCenterY);
          stop();
        }
        if (this.mStop)
          break;
        this.mHeader.post(this);
        break;
      }
    }

    public final boolean start(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      boolean bool1 = this.mRunning;
      boolean bool2 = false;
      if (bool1)
        return bool2;
      this.mCenterX = paramFloat3;
      this.mCenterY = paramFloat4;
      this.mTargetScale = paramFloat2;
      this.mStartTime = System.currentTimeMillis();
      this.mStartScale = paramFloat1;
      if (this.mTargetScale > this.mStartScale);
      for (boolean bool3 = true; ; bool3 = false)
      {
        this.mZoomingIn = bool3;
        this.mVelocity = ((this.mTargetScale - this.mStartScale) / 300.0F);
        this.mRunning = true;
        this.mStop = false;
        this.mHeader.post(this);
        bool2 = true;
        break;
      }
    }

    public final void stop()
    {
      this.mRunning = false;
      this.mStop = true;
    }
  }

  private static final class SnapRunnable
    implements Runnable
  {
    private final PhotoHeaderView mHeader;
    private boolean mRunning;
    private long mStartRunTime = -1L;
    private boolean mStop;
    private float mTranslateX;
    private float mTranslateY;

    public SnapRunnable(PhotoHeaderView paramPhotoHeaderView)
    {
      this.mHeader = paramPhotoHeaderView;
    }

    public final void run()
    {
      if (this.mStop)
        return;
      long l = System.currentTimeMillis();
      float f1;
      label31: float f2;
      if (this.mStartRunTime != -1L)
      {
        f1 = (float)(l - this.mStartRunTime);
        if (this.mStartRunTime == -1L)
          this.mStartRunTime = l;
        if (f1 < 100.0F)
          break label146;
        f2 = this.mTranslateX;
      }
      while (true)
      {
        label60: float f3 = this.mTranslateY;
        label146: 
        do
        {
          this.mHeader.translate(f2, f3);
          this.mTranslateX -= f2;
          this.mTranslateY -= f3;
          if ((this.mTranslateX == 0.0F) && (this.mTranslateY == 0.0F))
            stop();
          if (this.mStop)
            break;
          this.mHeader.post(this);
          break;
          f1 = 0.0F;
          break label31;
          f2 = 10.0F * (this.mTranslateX / (100.0F - f1));
          f3 = 10.0F * (this.mTranslateY / (100.0F - f1));
          if ((Math.abs(f2) > Math.abs(this.mTranslateX)) || (f2 == (0.0F / 0.0F)))
            f2 = this.mTranslateX;
          if (Math.abs(f3) > Math.abs(this.mTranslateY))
            break label60;
        }
        while (f3 != (0.0F / 0.0F));
      }
    }

    public final boolean start(float paramFloat1, float paramFloat2)
    {
      boolean bool1 = this.mRunning;
      boolean bool2 = false;
      if (bool1);
      while (true)
      {
        return bool2;
        this.mStartRunTime = -1L;
        this.mTranslateX = paramFloat1;
        this.mTranslateY = paramFloat2;
        this.mStop = false;
        this.mRunning = true;
        this.mHeader.postDelayed(this, 250L);
        bool2 = true;
      }
    }

    public final void stop()
    {
      this.mRunning = false;
      this.mStop = true;
    }
  }

  private static final class TranslateRunnable
    implements Runnable
  {
    private final PhotoHeaderView mHeader;
    private long mLastRunTime = -1L;
    private boolean mRunning;
    private boolean mStop;
    private float mVelocityX;
    private float mVelocityY;

    public TranslateRunnable(PhotoHeaderView paramPhotoHeaderView)
    {
      this.mHeader = paramPhotoHeaderView;
    }

    public final void run()
    {
      if (this.mStop)
        return;
      long l = System.currentTimeMillis();
      float f1;
      label34: boolean bool;
      float f2;
      if (this.mLastRunTime != -1L)
      {
        f1 = (float)(l - this.mLastRunTime) / 1000.0F;
        bool = this.mHeader.translate(f1 * this.mVelocityX, f1 * this.mVelocityY);
        this.mLastRunTime = l;
        f2 = 1000.0F * f1;
        if (this.mVelocityX <= 0.0F)
          break label192;
        this.mVelocityX -= f2;
        if (this.mVelocityX < 0.0F)
          this.mVelocityX = 0.0F;
        label100: if (this.mVelocityY <= 0.0F)
          break label220;
        this.mVelocityY -= f2;
        if (this.mVelocityY < 0.0F)
          this.mVelocityY = 0.0F;
      }
      while (true)
      {
        if (((this.mVelocityX == 0.0F) && (this.mVelocityY == 0.0F)) || (!bool))
        {
          stop();
          this.mHeader.snap();
        }
        if (this.mStop)
          break;
        this.mHeader.post(this);
        break;
        f1 = 0.0F;
        break label34;
        label192: this.mVelocityX = (f2 + this.mVelocityX);
        if (this.mVelocityX <= 0.0F)
          break label100;
        this.mVelocityX = 0.0F;
        break label100;
        label220: this.mVelocityY = (f2 + this.mVelocityY);
        if (this.mVelocityY > 0.0F)
          this.mVelocityY = 0.0F;
      }
    }

    public final boolean start(float paramFloat1, float paramFloat2)
    {
      boolean bool1 = this.mRunning;
      boolean bool2 = false;
      if (bool1);
      while (true)
      {
        return bool2;
        this.mLastRunTime = -1L;
        this.mVelocityX = paramFloat1;
        this.mVelocityY = paramFloat2;
        this.mStop = false;
        this.mRunning = true;
        this.mHeader.post(this);
        bool2 = true;
      }
    }

    public final void stop()
    {
      this.mRunning = false;
      this.mStop = true;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PhotoHeaderView
 * JD-Core Version:    0.6.2
 */