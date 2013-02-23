package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.DbPlusOneData;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.service.Resource;
import com.google.android.apps.plus.service.ResourceConsumer;
import com.google.android.apps.plus.util.AccessibilityUtils;
import com.google.android.apps.plus.util.Dates;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.PlusBarUtils;
import com.google.android.apps.plus.util.TextPaintUtils;
import com.google.android.apps.plus.util.ViewUtils;
import com.google.api.services.plusi.model.DataPlusOne;
import com.google.api.services.plusi.model.DataPlusOneJson;
import com.googlecode.eyesfree.utils.TouchExplorationHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class PhotoOneUpInfoView extends OneUpBaseView
  implements ResourceConsumer, ClickableButton.ClickableButtonListener, Recyclable
{
  private static Paint sActionBarBackgroundPaint;
  private static int sAvatarMarginLeft;
  private static int sAvatarMarginRight;
  private static int sAvatarMarginTop;
  private static Bitmap sAvatarOverlayBitmap;
  private static int sAvatarSize;
  private static Paint sBackgroundPaint;
  private static int sCaptionMarginTop;
  private static TextPaint sContentPaint;
  private static TextPaint sDatePaint;
  private static Bitmap sDefaultAvatarBitmap;
  private static float sFontSpacing;
  private static int sMarginBottom;
  private static int sMarginLeft;
  private static int sMarginRight;
  private static int sNameMarginTop;
  private static TextPaint sNamePaint;
  private static int sPlusOneButtonMarginLeft;
  private static int sPlusOneButtonMarginRight;
  private static Paint sResizePaint;
  private String mAlbumId;
  private ClickableAvatar mAuthorImage;
  private PositionedStaticLayout mAuthorLayout;
  private int mBackgroundOffset;
  private Spannable mCaption;
  private ClickableStaticLayout mCaptionLayout;
  private Set<ClickableItem> mClickableItems = new HashSet();
  private boolean mContentDescriptionDirty = true;
  private ClickableItem mCurrentClickableItem;
  private String mDate;
  private ClickableStaticLayout mDateLayout;
  private OneUpListener mOneUpListener;
  private String mOwnerId;
  private String mOwnerName;
  protected ClickableButton mPlusOneButton;
  private DbPlusOneData mPlusOneData;
  private OneUpActivityTouchExplorer mTouchExplorer;

  public PhotoOneUpInfoView(Context paramContext)
  {
    super(paramContext);
    if (sNamePaint == null)
    {
      Resources localResources = getContext().getResources();
      sFontSpacing = localResources.getDimension(R.dimen.stream_one_up_font_spacing);
      sAvatarSize = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_size);
      sMarginBottom = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_bottom);
      sMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_left);
      sMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_right);
      sAvatarMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_top);
      sAvatarMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_left);
      sAvatarMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_right);
      sNameMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_name_margin_top);
      sCaptionMarginTop = localResources.getDimensionPixelOffset(R.dimen.photo_one_up_caption_margin_top);
      int i = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_plus_one_button_margin_right);
      sPlusOneButtonMarginLeft = i;
      sPlusOneButtonMarginRight = i;
      sDefaultAvatarBitmap = EsAvatarData.getMediumDefaultAvatar(getContext(), true);
      sAvatarOverlayBitmap = ImageUtils.decodeResource(localResources, R.drawable.bg_taco_avatar);
      TextPaint localTextPaint1 = new TextPaint();
      sNamePaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sNamePaint.setTypeface(Typeface.DEFAULT_BOLD);
      sNamePaint.setColor(localResources.getColor(R.color.stream_one_up_name));
      sNamePaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_name_text_size));
      TextPaintUtils.registerTextPaint(sNamePaint, R.dimen.stream_one_up_name_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sDatePaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sDatePaint.setColor(localResources.getColor(R.color.stream_one_up_date));
      sDatePaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sDatePaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_date_text_size));
      TextPaintUtils.registerTextPaint(sDatePaint, R.dimen.stream_one_up_date_text_size);
      TextPaint localTextPaint3 = new TextPaint();
      sContentPaint = localTextPaint3;
      localTextPaint3.setAntiAlias(true);
      sContentPaint.setColor(localResources.getColor(R.color.stream_one_up_content));
      sContentPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sContentPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_content_text_size));
      TextPaintUtils.registerTextPaint(sContentPaint, R.dimen.stream_one_up_content_text_size);
      Paint localPaint1 = new Paint();
      sBackgroundPaint = localPaint1;
      localPaint1.setColor(localResources.getColor(R.color.stream_one_up_list_background));
      sBackgroundPaint.setStyle(Paint.Style.FILL);
      Paint localPaint2 = new Paint();
      sActionBarBackgroundPaint = localPaint2;
      localPaint2.setColor(localResources.getColor(R.color.stream_one_up_action_bar_background));
      sActionBarBackgroundPaint.setStyle(Paint.Style.FILL);
      sResizePaint = new Paint(2);
    }
    setupAccessibility(getContext());
  }

  public PhotoOneUpInfoView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (sNamePaint == null)
    {
      Resources localResources = getContext().getResources();
      sFontSpacing = localResources.getDimension(R.dimen.stream_one_up_font_spacing);
      sAvatarSize = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_size);
      sMarginBottom = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_bottom);
      sMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_left);
      sMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_right);
      sAvatarMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_top);
      sAvatarMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_left);
      sAvatarMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_right);
      sNameMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_name_margin_top);
      sCaptionMarginTop = localResources.getDimensionPixelOffset(R.dimen.photo_one_up_caption_margin_top);
      int i = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_plus_one_button_margin_right);
      sPlusOneButtonMarginLeft = i;
      sPlusOneButtonMarginRight = i;
      sDefaultAvatarBitmap = EsAvatarData.getMediumDefaultAvatar(getContext(), true);
      sAvatarOverlayBitmap = ImageUtils.decodeResource(localResources, R.drawable.bg_taco_avatar);
      TextPaint localTextPaint1 = new TextPaint();
      sNamePaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sNamePaint.setTypeface(Typeface.DEFAULT_BOLD);
      sNamePaint.setColor(localResources.getColor(R.color.stream_one_up_name));
      sNamePaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_name_text_size));
      TextPaintUtils.registerTextPaint(sNamePaint, R.dimen.stream_one_up_name_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sDatePaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sDatePaint.setColor(localResources.getColor(R.color.stream_one_up_date));
      sDatePaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sDatePaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_date_text_size));
      TextPaintUtils.registerTextPaint(sDatePaint, R.dimen.stream_one_up_date_text_size);
      TextPaint localTextPaint3 = new TextPaint();
      sContentPaint = localTextPaint3;
      localTextPaint3.setAntiAlias(true);
      sContentPaint.setColor(localResources.getColor(R.color.stream_one_up_content));
      sContentPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sContentPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_content_text_size));
      TextPaintUtils.registerTextPaint(sContentPaint, R.dimen.stream_one_up_content_text_size);
      Paint localPaint1 = new Paint();
      sBackgroundPaint = localPaint1;
      localPaint1.setColor(localResources.getColor(R.color.stream_one_up_list_background));
      sBackgroundPaint.setStyle(Paint.Style.FILL);
      Paint localPaint2 = new Paint();
      sActionBarBackgroundPaint = localPaint2;
      localPaint2.setColor(localResources.getColor(R.color.stream_one_up_action_bar_background));
      sActionBarBackgroundPaint.setStyle(Paint.Style.FILL);
      sResizePaint = new Paint(2);
    }
    setupAccessibility(getContext());
  }

  public PhotoOneUpInfoView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (sNamePaint == null)
    {
      Resources localResources = getContext().getResources();
      sFontSpacing = localResources.getDimension(R.dimen.stream_one_up_font_spacing);
      sAvatarSize = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_size);
      sMarginBottom = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_bottom);
      sMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_left);
      sMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_margin_right);
      sAvatarMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_top);
      sAvatarMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_left);
      sAvatarMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_margin_right);
      sNameMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_name_margin_top);
      sCaptionMarginTop = localResources.getDimensionPixelOffset(R.dimen.photo_one_up_caption_margin_top);
      int i = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_plus_one_button_margin_right);
      sPlusOneButtonMarginLeft = i;
      sPlusOneButtonMarginRight = i;
      sDefaultAvatarBitmap = EsAvatarData.getMediumDefaultAvatar(getContext(), true);
      sAvatarOverlayBitmap = ImageUtils.decodeResource(localResources, R.drawable.bg_taco_avatar);
      TextPaint localTextPaint1 = new TextPaint();
      sNamePaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sNamePaint.setTypeface(Typeface.DEFAULT_BOLD);
      sNamePaint.setColor(localResources.getColor(R.color.stream_one_up_name));
      sNamePaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_name_text_size));
      TextPaintUtils.registerTextPaint(sNamePaint, R.dimen.stream_one_up_name_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sDatePaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sDatePaint.setColor(localResources.getColor(R.color.stream_one_up_date));
      sDatePaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sDatePaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_date_text_size));
      TextPaintUtils.registerTextPaint(sDatePaint, R.dimen.stream_one_up_date_text_size);
      TextPaint localTextPaint3 = new TextPaint();
      sContentPaint = localTextPaint3;
      localTextPaint3.setAntiAlias(true);
      sContentPaint.setColor(localResources.getColor(R.color.stream_one_up_content));
      sContentPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sContentPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_content_text_size));
      TextPaintUtils.registerTextPaint(sContentPaint, R.dimen.stream_one_up_content_text_size);
      Paint localPaint1 = new Paint();
      sBackgroundPaint = localPaint1;
      localPaint1.setColor(localResources.getColor(R.color.stream_one_up_list_background));
      sBackgroundPaint.setStyle(Paint.Style.FILL);
      Paint localPaint2 = new Paint();
      sActionBarBackgroundPaint = localPaint2;
      localPaint2.setColor(localResources.getColor(R.color.stream_one_up_action_bar_background));
      sActionBarBackgroundPaint.setStyle(Paint.Style.FILL);
      sResizePaint = new Paint(2);
    }
    setupAccessibility(getContext());
  }

  private void setupAccessibility(Context paramContext)
  {
    if ((Build.VERSION.SDK_INT >= 16) && (AccessibilityUtils.isAccessibilityEnabled(paramContext)) && (this.mTouchExplorer == null))
    {
      this.mTouchExplorer = new OneUpActivityTouchExplorer(paramContext);
      this.mTouchExplorer.install(this);
    }
  }

  private void updateAccessibility()
  {
    if (this.mTouchExplorer != null)
    {
      this.mTouchExplorer.invalidateItemCache();
      this.mTouchExplorer.invalidateParent();
    }
  }

  public final void bindResources()
  {
    if ((ViewUtils.isViewAttached(this)) && (this.mAuthorImage != null))
      this.mAuthorImage.bindResources();
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = 1;
    int k = (int)paramMotionEvent.getX();
    int m = (int)paramMotionEvent.getY();
    switch (paramMotionEvent.getAction())
    {
    case 2:
    default:
      i = 0;
    case 0:
    case 1:
    case 3:
    }
    while (true)
    {
      return i;
      Iterator localIterator2 = this.mClickableItems.iterator();
      while (localIterator2.hasNext())
      {
        ClickableItem localClickableItem = (ClickableItem)localIterator2.next();
        if (localClickableItem.handleEvent(k, m, 0))
        {
          this.mCurrentClickableItem = localClickableItem;
          invalidate();
        }
      }
      this.mCurrentClickableItem = null;
      Iterator localIterator1 = this.mClickableItems.iterator();
      while (localIterator1.hasNext())
        ((ClickableItem)localIterator1.next()).handleEvent(k, m, i);
      invalidate();
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
      }
    }
  }

  public void invalidate()
  {
    super.invalidate();
    StringBuilder localStringBuilder;
    if (this.mContentDescriptionDirty)
      if (Build.VERSION.SDK_INT < 16)
      {
        localStringBuilder = new StringBuilder();
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mOwnerName);
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mDate);
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mCaption);
        if ((this.mPlusOneData == null) || (!this.mPlusOneData.isPlusOnedByMe()))
          break label93;
      }
    label93: 
    while (true)
    {
      localStringBuilder.append(null);
      setContentDescription(localStringBuilder.toString());
      setFocusable(true);
      this.mContentDescriptionDirty = false;
      return;
    }
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    bindResources();
    updateAccessibility();
  }

  public final void onClickableButtonListenerClick(ClickableButton paramClickableButton)
  {
    int i;
    if ((this.mOneUpListener != null) && (paramClickableButton == this.mPlusOneButton))
    {
      this.mOneUpListener.onPlusOne(this.mAlbumId, this.mPlusOneData);
      if (AccessibilityUtils.isAccessibilityEnabled(getContext()))
      {
        if ((this.mPlusOneData == null) || (!this.mPlusOneData.isPlusOnedByMe()))
          break label122;
        i = 1;
        if (i == 0)
          break label127;
      }
    }
    label122: label127: for (int j = R.string.plus_one_removed_confirmation; ; j = R.string.plus_one_added_confirmation)
    {
      AccessibilityEvent localAccessibilityEvent = AccessibilityEvent.obtain(16384);
      localAccessibilityEvent.getText().add(getResources().getString(j));
      onInitializeAccessibilityEvent(localAccessibilityEvent);
      localAccessibilityEvent.setContentDescription(null);
      getParent().requestSendAccessibilityEvent(this, localAccessibilityEvent);
      return;
      i = 0;
      break;
    }
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    unbindResources();
    if (this.mTouchExplorer != null)
    {
      this.mTouchExplorer.uninstall();
      this.mTouchExplorer = null;
    }
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    paramCanvas.drawRect(0.0F, this.mBackgroundOffset, getWidth(), getHeight(), sBackgroundPaint);
    if (this.mAuthorImage.getBitmap() != null);
    for (Bitmap localBitmap = this.mAuthorImage.getBitmap(); ; localBitmap = sDefaultAvatarBitmap)
    {
      paramCanvas.drawBitmap(localBitmap, null, this.mAuthorImage.getRect(), sResizePaint);
      paramCanvas.drawBitmap(sAvatarOverlayBitmap, null, this.mAuthorImage.getRect(), sResizePaint);
      if (this.mAuthorImage.isClicked())
        this.mAuthorImage.drawSelectionRect(paramCanvas);
      this.mPlusOneButton.draw(paramCanvas);
      if (this.mDateLayout != null)
      {
        int n = this.mDateLayout.getLeft();
        int i1 = this.mDateLayout.getTop();
        paramCanvas.translate(n, i1);
        this.mDateLayout.draw(paramCanvas);
        paramCanvas.translate(-n, -i1);
      }
      int i = this.mAuthorLayout.getLeft();
      int j = this.mAuthorLayout.getTop();
      paramCanvas.translate(i, j);
      this.mAuthorLayout.draw(paramCanvas);
      paramCanvas.translate(-i, -j);
      if (this.mCaptionLayout != null)
      {
        int k = this.mCaptionLayout.getLeft();
        int m = this.mCaptionLayout.getTop();
        paramCanvas.translate(k, m);
        this.mCaptionLayout.draw(paramCanvas);
        paramCanvas.translate(-k, -m);
      }
      updateAccessibility();
      return;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = getPaddingLeft() + sMarginLeft;
    int j = getPaddingTop() - sAvatarMarginTop;
    int k = getMeasuredWidth();
    int m = k - i - getPaddingRight() - sMarginRight;
    this.mBackgroundOffset = j;
    Context localContext = getContext();
    int n = i + sAvatarMarginLeft;
    int i1 = j + sAvatarMarginTop;
    this.mAuthorImage.setRect(n, i1, n + sAvatarSize, i1 + sAvatarSize);
    int i2;
    int i3;
    label126: TextPaint localTextPaint;
    label209: NinePatchDrawable localNinePatchDrawable1;
    label219: NinePatchDrawable localNinePatchDrawable2;
    label229: int i11;
    if ((this.mPlusOneData != null) && (this.mPlusOneData.isPlusOnedByMe()))
    {
      i2 = 1;
      if (this.mPlusOneData != null)
        break label609;
      i3 = 1;
      Resources localResources = getResources();
      int i4 = R.string.stream_plus_one_count_with_plus;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(Math.max(i3, 1));
      String str = localResources.getString(i4, arrayOfObject);
      int i5 = i + m - sPlusOneButtonMarginRight;
      int i6 = j + sNameMarginTop;
      this.mClickableItems.remove(this.mPlusOneButton);
      if (i2 == 0)
        break label621;
      localTextPaint = PlusBarUtils.sPlusOnedTextPaint;
      if (i2 == 0)
        break label629;
      localNinePatchDrawable1 = PlusBarUtils.sPlusOnedDrawable;
      if (i2 == 0)
        break label637;
      localNinePatchDrawable2 = PlusBarUtils.sPlusOnedPressedDrawable;
      this.mPlusOneButton = new ClickableButton(localContext, str, localTextPaint, localNinePatchDrawable1, localNinePatchDrawable2, this, 0, 0);
      int i7 = i5 - this.mPlusOneButton.getRect().width();
      this.mPlusOneButton.getRect().offsetTo(i7, i6);
      this.mClickableItems.add(this.mPlusOneButton);
      int i8 = i + sAvatarMarginLeft + sAvatarSize + sAvatarMarginRight;
      int i9 = j + sNameMarginTop;
      int i10 = m - i8 - this.mPlusOneButton.getRect().width() - sPlusOneButtonMarginLeft;
      this.mAuthorLayout = new PositionedStaticLayout(TextUtils.ellipsize(this.mOwnerName, sNamePaint, i10, TextUtils.TruncateAt.END), sNamePaint, i10, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false);
      this.mAuthorLayout.setPosition(i8, i9);
      i11 = i9 + this.mAuthorLayout.getHeight();
      this.mClickableItems.remove(this.mDateLayout);
      if (this.mDate == null)
        break label735;
      Locale localLocale = getContext().getResources().getConfiguration().locale;
      SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(this.mDate.toUpperCase(localLocale));
      localSpannableStringBuilder.append(" ");
      this.mDateLayout = new ClickableStaticLayout(localSpannableStringBuilder, sDatePaint, i10, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false, null);
      this.mDateLayout.setPosition(i8, i11);
      this.mClickableItems.add(this.mDateLayout);
    }
    label735: for (int i12 = i11 + this.mDateLayout.getHeight(); ; i12 = i11)
    {
      int i13 = Math.max(sAvatarSize, i12 - j);
      if (TextUtils.isEmpty(this.mCaption));
      while (true)
      {
        setMeasuredDimension(k, i13 + sMarginBottom + getPaddingBottom());
        if (this.mOnMeasuredListener != null)
          this.mOnMeasuredListener.onMeasured(this);
        if (this.mTouchExplorer != null)
          this.mTouchExplorer.invalidateItemCache();
        return;
        i2 = 0;
        break;
        label609: i3 = this.mPlusOneData.getCount();
        break label126;
        label621: localTextPaint = PlusBarUtils.sNotPlusOnedTextPaint;
        break label209;
        label629: localNinePatchDrawable1 = PlusBarUtils.sButtonDrawable;
        break label219;
        label637: localNinePatchDrawable2 = PlusBarUtils.sButtonPressedDrawable;
        break label229;
        int i14 = i13 + sCaptionMarginTop;
        this.mClickableItems.remove(this.mCaptionLayout);
        this.mCaptionLayout = new ClickableStaticLayout(this.mCaption, sContentPaint, m, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false, this.mOneUpListener);
        this.mCaptionLayout.setPosition(i, i14);
        this.mClickableItems.add(this.mCaptionLayout);
        i13 = this.mCaptionLayout.getBottom();
      }
    }
  }

  public void onRecycle()
  {
    unbindResources();
    this.mAuthorLayout = null;
    this.mDateLayout = null;
    this.mCaptionLayout = null;
    this.mAuthorImage = null;
    this.mClickableItems.clear();
    this.mCurrentClickableItem = null;
    this.mPlusOneData = null;
    this.mCaption = null;
    this.mOneUpListener = null;
  }

  public final void onResourceStatusChange$1574fca0(Resource paramResource)
  {
  }

  public void setAlbum(String paramString)
  {
    this.mAlbumId = paramString;
  }

  public void setCaption(String paramString)
  {
    this.mCaption = null;
    if (!TextUtils.isEmpty(paramString))
      this.mCaption = ClickableStaticLayout.buildStateSpans(paramString);
    this.mContentDescriptionDirty = true;
  }

  public void setDate(long paramLong)
  {
    this.mDate = Dates.getAbbreviatedRelativeTimeSpanString(getContext(), paramLong).toString();
    this.mContentDescriptionDirty = true;
  }

  public void setOneUpClickListener(OneUpListener paramOneUpListener)
  {
    this.mOneUpListener = paramOneUpListener;
  }

  public void setOwner(String paramString1, String paramString2, String paramString3)
  {
    if (TextUtils.equals(this.mOwnerId, paramString1));
    while (true)
    {
      return;
      unbindResources();
      this.mOwnerId = paramString1;
      this.mOwnerName = paramString2;
      if (this.mOwnerName == null)
      {
        this.mOwnerName = "";
        Log.w("PhotoOneUp", "===> Author name was null for gaia id: " + paramString1);
      }
      if (this.mAuthorImage != null)
        this.mClickableItems.remove(this.mAuthorImage);
      this.mAuthorImage = new ClickableAvatar(this, this.mOwnerId, paramString3, this.mOwnerName, this.mOneUpListener, 2);
      this.mClickableItems.add(this.mAuthorImage);
      this.mContentDescriptionDirty = true;
      bindResources();
    }
  }

  public void setPlusOne(byte[] paramArrayOfByte)
  {
    this.mPlusOneData = null;
    if (paramArrayOfByte != null)
    {
      DataPlusOne localDataPlusOne = (DataPlusOne)DataPlusOneJson.getInstance().fromByteArray(paramArrayOfByte);
      if ((localDataPlusOne.globalCount != null) && (localDataPlusOne.isPlusonedByViewer != null))
        this.mPlusOneData = new DbPlusOneData(null, localDataPlusOne.globalCount.intValue(), localDataPlusOne.isPlusonedByViewer.booleanValue());
    }
    this.mContentDescriptionDirty = true;
  }

  public final void unbindResources()
  {
    if (this.mAuthorImage != null)
      this.mAuthorImage.unbindResources();
  }

  private final class OneUpActivityTouchExplorer extends TouchExplorationHelper<ClickableItem>
  {
    private boolean mIsItemCacheStale = true;
    private ArrayList<ClickableItem> mItemCache = new ArrayList(PhotoOneUpInfoView.this.mClickableItems.size());

    public OneUpActivityTouchExplorer(Context arg2)
    {
      super();
    }

    private void refreshItemCache()
    {
      if (this.mIsItemCacheStale)
      {
        this.mItemCache.clear();
        this.mItemCache.addAll(PhotoOneUpInfoView.this.mClickableItems);
        Collections.sort(this.mItemCache, ClickableItem.sComparator);
        this.mIsItemCacheStale = false;
      }
    }

    protected final void getVisibleItems(List<ClickableItem> paramList)
    {
      refreshItemCache();
      int i = 0;
      int j = this.mItemCache.size();
      while (i < j)
      {
        paramList.add((ClickableItem)this.mItemCache.get(i));
        i++;
      }
    }

    public final void invalidateItemCache()
    {
      this.mIsItemCacheStale = true;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PhotoOneUpInfoView
 * JD-Core Version:    0.6.2
 */