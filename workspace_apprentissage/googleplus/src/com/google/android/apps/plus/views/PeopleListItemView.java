package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.telephony.PhoneNumberUtils;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.styleable;
import com.google.android.apps.plus.content.AvatarRequest;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.fragments.CircleNameResolver;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.service.ImageCache.ImageConsumer;
import com.google.android.apps.plus.service.ImageCache.OnAvatarChangeListener;
import com.google.android.apps.plus.util.SpannableUtils;
import java.lang.reflect.Constructor;

public class PeopleListItemView extends CheckableListItemView
  implements View.OnClickListener, ImageCache.ImageConsumer, ImageCache.OnAvatarChangeListener, Recyclable
{
  private static Drawable sAddButtonIcon;
  private static Bitmap sDefaultUserImage;
  private static int sMediumAvatarSize;
  private static Drawable sRemoveButtonIcon;
  private static int sSmallAvatarSize;
  private static int sTinyAvatarSize;
  private static Drawable sUnblockButtonIcon;
  private static Drawable sVerticalDivider;
  private static Drawable sWellFormedEmailIcon;
  private static Drawable sWellFormedSmsIcon;
  private TextView mActionButton;
  private final int mActionButtonResourceId;
  private boolean mActionButtonVisible;
  private final int mActionButtonWidth;
  private ImageView mAddButton;
  private boolean mAddButtonVisible;
  private Bitmap mAvatarBitmap;
  private final Rect mAvatarBounds = new Rect();
  private final ImageCache mAvatarCache;
  private boolean mAvatarInvalidated;
  private final Rect mAvatarOriginalBounds = new Rect();
  private final Paint mAvatarPaint;
  private AvatarRequest mAvatarRequest;
  private int mAvatarRequestSize;
  private final int mAvatarSize;
  private String mAvatarUrl;
  private boolean mAvatarVisible = true;
  private final Drawable mCircleIconDrawable;
  private final int mCircleIconSize;
  private boolean mCircleIconVisible;
  private int mCircleLineHeight;
  private boolean mCircleListVisible;
  private CircleNameResolver mCircleNameResolver;
  private final int mCirclesTextColor;
  private final float mCirclesTextSize;
  private final TextView mCirclesTextView;
  private String mContactLookupKey;
  private Bitmap mDefaultAvatarBitmap;
  private String mDisplayName;
  private final int mEmailIconPaddingLeft;
  private final int mEmailIconPaddingTop;
  private final SpannableStringBuilder mEmailTextBuilder = new SpannableStringBuilder();
  private boolean mFirstRow = true;
  private String mGaiaId;
  private final int mGapBetweenIconAndCircles;
  private final int mGapBetweenImageAndText;
  private final int mGapBetweenNameAndCircles;
  private final int mGapBetweenTextAndButton;
  private String mHighlightedText;
  private OnActionButtonClickListener mListener;
  private final SpannableStringBuilder mNameTextBuilder = new SpannableStringBuilder();
  private final TextView mNameTextView;
  private final int mPaddingBottom;
  private final int mPaddingLeft;
  private final int mPaddingRight;
  private final int mPaddingTop;
  private String mPersonId;
  private boolean mPlusPage;
  private final int mPreferredHeight;
  private ImageView mRemoveButton;
  private boolean mRemoveButtonVisible;
  protected SectionHeaderView mSectionHeader;
  protected int mSectionHeaderHeight;
  protected boolean mSectionHeaderVisible;
  private TextView mTypeTextView;
  private boolean mTypeTextViewVisible;
  private ImageView mUnblockButton;
  private boolean mUnblockButtonVisible;
  private int mVerticalDividerLeft;
  private final int mVerticalDividerPadding;
  private final int mVerticalDividerWidth;
  private String mWellFormedEmail;
  private boolean mWellFormedEmailMode;
  private String mWellFormedSms;
  private boolean mWellFormedSmsMode;

  public PeopleListItemView(Context paramContext)
  {
    this(paramContext, null);
  }

  public PeopleListItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mAvatarCache = ImageCache.getInstance(paramContext);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ContactListItemView);
    this.mPreferredHeight = localTypedArray.getDimensionPixelSize(0, 0);
    this.mPaddingTop = localTypedArray.getDimensionPixelOffset(1, 0);
    this.mPaddingBottom = localTypedArray.getDimensionPixelOffset(2, 0);
    this.mPaddingLeft = localTypedArray.getDimensionPixelOffset(3, 0);
    this.mPaddingRight = localTypedArray.getDimensionPixelOffset(4, 0);
    float f = localTypedArray.getFloat(6, 0.0F);
    this.mCirclesTextSize = localTypedArray.getFloat(9, 0.0F);
    this.mGapBetweenImageAndText = localTypedArray.getDimensionPixelOffset(5, 0);
    this.mCircleIconDrawable = localTypedArray.getDrawable(7);
    this.mCircleIconSize = localTypedArray.getDimensionPixelSize(8, 0);
    this.mGapBetweenNameAndCircles = localTypedArray.getDimensionPixelOffset(11, 0);
    this.mGapBetweenIconAndCircles = localTypedArray.getDimensionPixelOffset(12, 0);
    this.mGapBetweenTextAndButton = localTypedArray.getDimensionPixelOffset(13, 0);
    this.mActionButtonResourceId = localTypedArray.getResourceId(14, 0);
    this.mActionButtonWidth = localTypedArray.getDimensionPixelSize(15, 0);
    this.mVerticalDividerWidth = localTypedArray.getDimensionPixelSize(16, 0);
    this.mVerticalDividerPadding = localTypedArray.getDimensionPixelOffset(17, 0);
    this.mCirclesTextColor = localTypedArray.getColor(10, 0);
    this.mEmailIconPaddingTop = localTypedArray.getDimensionPixelOffset(18, 0);
    this.mEmailIconPaddingLeft = localTypedArray.getDimensionPixelOffset(19, 0);
    localTypedArray.recycle();
    this.mNameTextView = new TextView(paramContext);
    this.mNameTextView.setSingleLine(true);
    this.mNameTextView.setEllipsize(TextUtils.TruncateAt.END);
    this.mNameTextView.setTextAppearance(paramContext, 16973892);
    this.mNameTextView.setTextSize(f);
    this.mNameTextView.setGravity(16);
    this.mNameTextView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    addView(this.mNameTextView);
    this.mCirclesTextView = new TextView(paramContext);
    this.mCirclesTextView.setSingleLine(true);
    this.mCirclesTextView.setEllipsize(TextUtils.TruncateAt.END);
    this.mCirclesTextView.setTextAppearance(paramContext, 16973892);
    this.mCirclesTextView.setTextSize(this.mCirclesTextSize);
    this.mCirclesTextView.setTextColor(this.mCirclesTextColor);
    this.mCirclesTextView.setGravity(16);
    this.mCirclesTextView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    addView(this.mCirclesTextView);
    this.mAvatarPaint = new Paint();
    if (sDefaultUserImage == null)
      sDefaultUserImage = EsAvatarData.getMediumDefaultAvatar(paramContext);
    this.mDefaultAvatarBitmap = sDefaultUserImage;
    if (sVerticalDivider == null)
      sVerticalDivider = paramContext.getApplicationContext().getResources().getDrawable(R.drawable.divider);
    if (sWellFormedEmailIcon == null)
      sWellFormedEmailIcon = paramContext.getApplicationContext().getResources().getDrawable(R.drawable.profile_email);
    if (sWellFormedSmsIcon == null)
      sWellFormedSmsIcon = paramContext.getApplicationContext().getResources().getDrawable(R.drawable.profile_sms);
    this.mAvatarSize = Math.min(sDefaultUserImage.getWidth(), this.mPreferredHeight);
    if (sMediumAvatarSize == 0)
    {
      sMediumAvatarSize = EsAvatarData.getMediumAvatarSize(paramContext);
      sSmallAvatarSize = EsAvatarData.getSmallAvatarSize(paramContext);
      sTinyAvatarSize = EsAvatarData.getTinyAvatarSize(paramContext);
    }
    if (this.mAvatarSize > sMediumAvatarSize)
    {
      this.mAvatarRequestSize = 2;
      this.mAvatarPaint.setFilterBitmap(true);
    }
    while (true)
    {
      return;
      if (this.mAvatarSize == sMediumAvatarSize)
      {
        this.mAvatarRequestSize = 2;
      }
      else if (this.mAvatarSize > sSmallAvatarSize)
      {
        this.mAvatarRequestSize = 2;
        this.mAvatarPaint.setFilterBitmap(true);
      }
      else if (this.mAvatarSize == sSmallAvatarSize)
      {
        this.mAvatarRequestSize = 1;
      }
      else if (this.mAvatarSize > sTinyAvatarSize)
      {
        this.mAvatarRequestSize = 1;
        this.mAvatarPaint.setFilterBitmap(true);
      }
      else if (this.mAvatarSize == sTinyAvatarSize)
      {
        this.mAvatarRequestSize = 0;
      }
      else
      {
        this.mAvatarRequestSize = 0;
        this.mAvatarPaint.setFilterBitmap(true);
      }
    }
  }

  public static PeopleListItemView createInstance(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 11);
    while (true)
    {
      try
      {
        localPeopleListItemView = (PeopleListItemView)Class.forName("com.google.android.apps.plus.views.PeopleListItemViewV11").getConstructor(new Class[] { Context.class }).newInstance(new Object[] { paramContext });
        return localPeopleListItemView;
      }
      catch (Exception localException)
      {
        Log.e("PeopleListItemView", "Cannot instantiate", localException);
      }
      PeopleListItemView localPeopleListItemView = new PeopleListItemView(paramContext);
    }
  }

  private void updateDisplayName()
  {
    if (this.mDisplayName != null)
      SpannableUtils.setTextWithHighlight$5cdafd0b(this.mNameTextView, this.mDisplayName, this.mNameTextBuilder, this.mHighlightedText, sBoldSpan, sColorSpan);
    while (true)
    {
      return;
      this.mWellFormedEmailMode = true;
      this.mNameTextView.setText(this.mWellFormedEmail);
    }
  }

  public void dispatchDraw(Canvas paramCanvas)
  {
    if (this.mWellFormedEmailMode)
    {
      int i5 = sWellFormedEmailIcon.getIntrinsicWidth();
      int i6 = sWellFormedEmailIcon.getIntrinsicHeight();
      int i7 = this.mAvatarBounds.left + (this.mAvatarSize - i5) / 2;
      int i8 = this.mAvatarBounds.top + (this.mAvatarSize - i6) / 2;
      sWellFormedEmailIcon.setBounds(i7, i8, i7 + i5, i8 + i6);
      sWellFormedEmailIcon.draw(paramCanvas);
      if ((this.mActionButtonVisible) || (this.mAddButtonVisible) || (this.mRemoveButtonVisible) || (this.mUnblockButtonVisible))
        if (!this.mSectionHeaderVisible)
          break label458;
    }
    label449: label458: for (int n = this.mSectionHeaderHeight + this.mVerticalDividerPadding; ; n = this.mVerticalDividerPadding)
    {
      sVerticalDivider.setBounds(this.mVerticalDividerLeft, n, this.mVerticalDividerLeft + this.mVerticalDividerWidth, getHeight() - this.mVerticalDividerPadding);
      sVerticalDivider.draw(paramCanvas);
      super.dispatchDraw(paramCanvas);
      return;
      if (this.mWellFormedSmsMode)
      {
        int i1 = sWellFormedSmsIcon.getIntrinsicWidth();
        int i2 = sWellFormedSmsIcon.getIntrinsicHeight();
        int i3 = this.mAvatarBounds.left + (this.mAvatarSize - i1) / 2;
        int i4 = this.mAvatarBounds.top + (this.mAvatarSize - i2) / 2;
        sWellFormedSmsIcon.setBounds(i3, i4, i3 + i1, i4 + i2);
        sWellFormedSmsIcon.draw(paramCanvas);
        break;
      }
      if (!this.mFirstRow)
        break;
      if (this.mCircleIconVisible)
        this.mCircleIconDrawable.draw(paramCanvas);
      if (this.mAvatarVisible)
      {
        if ((this.mAvatarInvalidated) && (this.mAvatarRequest != null))
        {
          this.mAvatarInvalidated = false;
          this.mAvatarCache.refreshImage(this, this.mAvatarRequest);
        }
        if (this.mAvatarBitmap == null)
          break label449;
      }
      for (Bitmap localBitmap = this.mAvatarBitmap; ; localBitmap = this.mDefaultAvatarBitmap)
      {
        this.mAvatarOriginalBounds.set(0, 0, localBitmap.getWidth(), localBitmap.getHeight());
        paramCanvas.drawBitmap(localBitmap, this.mAvatarOriginalBounds, this.mAvatarBounds, this.mAvatarPaint);
        if (this.mContactLookupKey == null)
          break;
        int i = sWellFormedEmailIcon.getIntrinsicWidth();
        int j = sWellFormedEmailIcon.getIntrinsicHeight();
        int k = this.mNameTextView.getLeft() - i - this.mEmailIconPaddingLeft;
        int m = this.mEmailIconPaddingTop;
        sWellFormedEmailIcon.setBounds(k, m, k + i, m + j);
        sWellFormedEmailIcon.draw(paramCanvas);
        break;
      }
    }
  }

  protected final void drawBackground(Canvas paramCanvas, Drawable paramDrawable)
  {
    if (this.mSectionHeaderVisible);
    for (int i = this.mSectionHeaderHeight; ; i = 0)
    {
      paramDrawable.setBounds(0, i, getWidth(), getHeight());
      paramDrawable.draw(paramCanvas);
      return;
    }
  }

  public String getContactName()
  {
    return this.mDisplayName;
  }

  public String getGaiaId()
  {
    return this.mGaiaId;
  }

  public String getPersonId()
  {
    return this.mPersonId;
  }

  public String getWellFormedEmail()
  {
    return this.mWellFormedEmail;
  }

  public String getWellFormedSms()
  {
    return this.mWellFormedSms;
  }

  public boolean isPlusPage()
  {
    return this.mPlusPage;
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    ImageCache.registerAvatarChangeListener(this);
  }

  public void onAvatarChanged(String paramString)
  {
    if ((paramString != null) && (paramString.equals(this.mGaiaId)))
    {
      this.mAvatarInvalidated = true;
      invalidate();
    }
  }

  public void onClick(View paramView)
  {
    if (this.mListener != null)
    {
      if (paramView != this.mAddButton)
        break label27;
      this.mListener.onActionButtonClick(this, 0);
    }
    while (true)
    {
      return;
      label27: if (paramView == this.mRemoveButton)
        this.mListener.onActionButtonClick(this, 1);
      else if (paramView == this.mUnblockButton)
        this.mListener.onActionButtonClick(this, 2);
      else if (paramView == this.mActionButton)
        this.mListener.onActionButtonClick(this, 3);
    }
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    ImageCache.unregisterAvatarChangeListener(this);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt4 - paramInt2;
    boolean bool = this.mSectionHeaderVisible;
    int j = 0;
    if (bool)
    {
      this.mSectionHeader.layout(0, 0, paramInt3 - paramInt1, this.mSectionHeaderHeight);
      j = 0 + this.mSectionHeaderHeight;
    }
    int k = this.mPaddingLeft;
    if (this.mAvatarVisible)
    {
      this.mAvatarBounds.left = k;
      this.mAvatarBounds.top = (j + (i - j - this.mAvatarSize) / 2);
      this.mAvatarBounds.right = (this.mAvatarBounds.left + this.mAvatarSize);
      this.mAvatarBounds.bottom = (this.mAvatarBounds.top + this.mAvatarSize);
      k += this.mAvatarSize + this.mGapBetweenImageAndText;
    }
    int m = paramInt3 - paramInt1 - this.mPaddingRight;
    if (this.mActionButtonVisible)
    {
      int i35 = this.mActionButton.getMeasuredWidth();
      int i36 = m - i35;
      this.mVerticalDividerLeft = (i36 - this.mVerticalDividerWidth);
      TextView localTextView7 = this.mActionButton;
      int i37 = i36 + i35;
      localTextView7.layout(i36, j, i37, i);
      m -= i35;
    }
    if (this.mUnblockButtonVisible)
    {
      int i34 = this.mUnblockButton.getMeasuredWidth();
      this.mVerticalDividerLeft = (m - i34);
      this.mUnblockButton.layout(this.mVerticalDividerLeft + this.mVerticalDividerWidth, j, m, i);
      m -= i34;
    }
    if (this.mRemoveButtonVisible)
    {
      int i33 = this.mRemoveButton.getMeasuredWidth();
      this.mVerticalDividerLeft = (m - i33);
      this.mRemoveButton.layout(this.mVerticalDividerLeft + this.mVerticalDividerWidth, j, m, i);
      m -= i33;
    }
    int i30;
    int i31;
    int n;
    if (this.mAddButtonVisible)
    {
      i30 = this.mAddButton.getMeasuredWidth();
      if (!this.mRemoveButtonVisible)
      {
        this.mVerticalDividerLeft = (m - i30);
        i31 = this.mVerticalDividerLeft + this.mVerticalDividerWidth;
        ImageView localImageView = this.mAddButton;
        int i32 = i31 + i30;
        localImageView.layout(i31, j, i32, i);
        m -= i30;
      }
    }
    else
    {
      if (this.mCheckBoxVisible)
      {
        int i25 = this.mCheckBox.getMeasuredWidth();
        int i26 = this.mCheckBox.getMeasuredHeight();
        int i27 = j + (i - j - i26) / 2;
        CheckBox localCheckBox = this.mCheckBox;
        int i28 = m - i25;
        int i29 = i27 + i26;
        localCheckBox.layout(i28, i27, m, i29);
        m -= i25;
      }
      if ((this.mActionButtonVisible) || (this.mRemoveButtonVisible) || (this.mAddButtonVisible) || (this.mUnblockButtonVisible) || (this.mCheckBoxVisible))
        m -= this.mGapBetweenTextAndButton;
      if (!this.mTypeTextViewVisible)
        break label712;
      n = this.mTypeTextView.getMeasuredWidth();
      label543: if (this.mFirstRow)
        break label718;
      int i20 = this.mCirclesTextView.getMeasuredHeight();
      int i21 = j + (i - j - i20) / 2;
      if (this.mTypeTextViewVisible)
      {
        TextView localTextView6 = this.mTypeTextView;
        int i23 = m - n;
        int i24 = i21 + i20;
        localTextView6.layout(i23, i21, m, i24);
        m -= n + this.mGapBetweenIconAndCircles;
      }
      TextView localTextView5 = this.mCirclesTextView;
      int i22 = i21 + i20;
      localTextView5.layout(k, i21, m, i22);
    }
    while (true)
    {
      if ((this.mAvatarVisible) && (!this.mWellFormedEmailMode) && (!this.mWellFormedSmsMode) && (this.mAvatarBitmap == null) && (this.mAvatarRequest != null))
        this.mAvatarCache.loadImage(this, this.mAvatarRequest);
      return;
      i31 = this.mVerticalDividerLeft - i30;
      break;
      label712: n = 0;
      break label543;
      label718: if (this.mCircleListVisible)
      {
        int i4 = this.mNameTextView.getMeasuredHeight();
        int i5 = this.mCirclesTextView.getMeasuredHeight();
        int i6 = i5;
        if (this.mCircleIconVisible)
          i6 = Math.max(this.mCircleIconSize, i6);
        int i7 = i6 + i4;
        int i8 = j + (this.mPreferredHeight - i7) / 2;
        int i9 = k;
        if (this.mContactLookupKey != null)
          i9 += sWellFormedEmailIcon.getIntrinsicWidth() + this.mEmailIconPaddingLeft;
        TextView localTextView2 = this.mNameTextView;
        int i10 = i8 + i4;
        localTextView2.layout(i9, i8, m, i10);
        int i11 = i8 + (i4 + this.mGapBetweenNameAndCircles);
        int i12 = k;
        if (this.mCircleIconVisible)
        {
          int i17 = i11 + (this.mCircleLineHeight - this.mCircleIconSize) / 2;
          Drawable localDrawable = this.mCircleIconDrawable;
          int i18 = k + this.mCircleIconSize;
          int i19 = i17 + this.mCircleIconSize;
          localDrawable.setBounds(k, i17, i18, i19);
          i12 += this.mCircleIconSize + this.mGapBetweenIconAndCircles;
        }
        int i13 = i11 + (this.mCircleLineHeight - i5) / 2;
        if (this.mTypeTextViewVisible)
        {
          TextView localTextView4 = this.mTypeTextView;
          int i15 = m - n;
          int i16 = i13 + i5;
          localTextView4.layout(i15, i13, m, i16);
          m -= n + this.mGapBetweenIconAndCircles;
        }
        TextView localTextView3 = this.mCirclesTextView;
        int i14 = i13 + i5;
        localTextView3.layout(i12, i13, m, i14);
      }
      else
      {
        int i1 = this.mNameTextView.getMeasuredHeight();
        int i2 = j + (i - j - i1) / 2;
        TextView localTextView1 = this.mNameTextView;
        int i3 = i2 + i1;
        localTextView1.layout(k, i2, m, i3);
      }
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = resolveSize(0, paramInt1);
    int j = i - this.mPaddingLeft - this.mPaddingRight;
    if (this.mAvatarVisible)
      j -= this.mAvatarSize + this.mGapBetweenImageAndText;
    boolean bool = this.mActionButtonVisible;
    int k = 0;
    int m = 0;
    if (bool)
    {
      this.mActionButton.measure(0, paramInt2);
      k = this.mActionButton.getMeasuredWidth();
      m = Math.max(0, this.mActionButton.getMeasuredHeight());
      j -= k + this.mVerticalDividerWidth;
    }
    if (this.mAddButtonVisible)
    {
      this.mAddButton.measure(View.MeasureSpec.makeMeasureSpec(this.mActionButtonWidth, 1073741824), paramInt2);
      m = Math.max(m, this.mAddButton.getMeasuredHeight());
      j -= this.mActionButtonWidth + this.mVerticalDividerWidth;
    }
    if (this.mRemoveButtonVisible)
    {
      this.mRemoveButton.measure(View.MeasureSpec.makeMeasureSpec(this.mActionButtonWidth, 1073741824), paramInt2);
      m = Math.max(m, this.mRemoveButton.getMeasuredHeight());
      j -= this.mActionButtonWidth + this.mVerticalDividerWidth;
    }
    if (this.mUnblockButtonVisible)
    {
      this.mUnblockButton.measure(View.MeasureSpec.makeMeasureSpec(this.mActionButtonWidth, 1073741824), paramInt2);
      m = Math.max(m, this.mUnblockButton.getMeasuredHeight());
      j -= this.mActionButtonWidth + this.mVerticalDividerWidth;
    }
    if (this.mCheckBoxVisible)
    {
      this.mCheckBox.measure(0, paramInt2);
      Math.max(m, this.mCheckBox.getMeasuredHeight());
      j -= this.mCheckBox.getMeasuredWidth();
    }
    if ((this.mRemoveButtonVisible) || (this.mActionButtonVisible) || (this.mAddButtonVisible) || (this.mUnblockButtonVisible) || (this.mCheckBoxVisible))
      j -= this.mGapBetweenTextAndButton;
    int n = j;
    if (this.mContactLookupKey != null)
      n -= sWellFormedEmailIcon.getIntrinsicWidth() + this.mGapBetweenIconAndCircles;
    this.mNameTextView.measure(View.MeasureSpec.makeMeasureSpec(n, 1073741824), paramInt2);
    int i1 = this.mAvatarSize;
    int i2 = j;
    if (this.mCircleIconVisible)
      i2 -= this.mCircleIconSize + this.mGapBetweenIconAndCircles;
    if (this.mTypeTextViewVisible)
    {
      this.mTypeTextView.measure(0, 0);
      i2 -= this.mTypeTextView.getMeasuredWidth() + this.mGapBetweenIconAndCircles;
    }
    if (this.mCircleListVisible)
    {
      this.mCirclesTextView.measure(0, 0);
      int i4 = Math.min(this.mCirclesTextView.getMeasuredWidth(), i2);
      this.mCircleLineHeight = Math.max(this.mCircleIconSize, this.mCirclesTextView.getMeasuredHeight());
      this.mCirclesTextView.measure(View.MeasureSpec.makeMeasureSpec(i4, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mCircleLineHeight, 1073741824));
      i1 = Math.max(i1, this.mNameTextView.getMeasuredHeight() + this.mGapBetweenNameAndCircles + this.mCircleLineHeight);
    }
    int i3 = Math.max(i1 + (this.mPaddingTop + this.mPaddingBottom), this.mPreferredHeight);
    if (this.mActionButtonVisible)
      this.mActionButton.measure(View.MeasureSpec.makeMeasureSpec(k, 1073741824), View.MeasureSpec.makeMeasureSpec(i3, 1073741824));
    if (this.mSectionHeaderVisible)
    {
      this.mSectionHeader.measure(paramInt1, 0);
      this.mSectionHeaderHeight = this.mSectionHeader.getMeasuredHeight();
      i3 += this.mSectionHeaderHeight;
    }
    setMeasuredDimension(i, i3);
  }

  public void onRecycle()
  {
    this.mPersonId = null;
    this.mGaiaId = null;
    this.mContactLookupKey = null;
    this.mAvatarVisible = true;
    this.mAvatarRequest = null;
    this.mAvatarBitmap = null;
    this.mDisplayName = null;
    this.mPlusPage = false;
    this.mFirstRow = true;
    this.mWellFormedEmailMode = false;
    this.mWellFormedEmail = null;
    this.mWellFormedSmsMode = false;
    this.mWellFormedSms = null;
    this.mHighlightedText = null;
    this.mCirclesTextView.setText(null);
  }

  public void setActionButtonLabel(int paramInt)
  {
    if (this.mActionButton == null)
    {
      this.mActionButton = new TextView(getContext());
      this.mActionButton.setBackgroundResource(this.mActionButtonResourceId);
      this.mActionButton.setGravity(17);
      this.mActionButton.setPadding(this.mVerticalDividerPadding, 0, this.mVerticalDividerPadding, 0);
      this.mActionButton.setOnClickListener(this);
      addView(this.mActionButton);
    }
    String str = getResources().getString(paramInt);
    this.mActionButton.setText(str.toUpperCase());
  }

  public void setActionButtonVisible(boolean paramBoolean)
  {
    if (this.mActionButtonVisible == paramBoolean);
    while (true)
    {
      return;
      this.mActionButtonVisible = paramBoolean;
      if (this.mActionButtonVisible)
      {
        if (this.mActionButton == null)
          setActionButtonLabel(R.string.add);
        this.mActionButton.setVisibility(0);
      }
      else if (this.mActionButton != null)
      {
        this.mActionButton.setVisibility(8);
      }
    }
  }

  public void setAddButtonVisible(boolean paramBoolean)
  {
    if (this.mAddButtonVisible == paramBoolean);
    while (true)
    {
      return;
      this.mAddButtonVisible = paramBoolean;
      if (this.mAddButtonVisible)
      {
        if (this.mAddButton == null)
        {
          this.mAddButton = new ImageView(getContext());
          this.mAddButton.setBackgroundResource(this.mActionButtonResourceId);
          this.mAddButton.setOnClickListener(this);
          this.mAddButton.setFocusable(false);
          ImageView localImageView = this.mAddButton;
          if (sAddButtonIcon == null)
            sAddButtonIcon = getContext().getApplicationContext().getResources().getDrawable(R.drawable.ic_btn_add_member);
          localImageView.setImageDrawable(sAddButtonIcon);
          this.mAddButton.setScaleType(ImageView.ScaleType.CENTER);
          this.mAddButton.setContentDescription(getResources().getString(R.string.add_to_circles));
          addView(this.mAddButton);
        }
        this.mAddButton.setVisibility(0);
      }
      else if (this.mAddButton != null)
      {
        this.mAddButton.setVisibility(8);
      }
    }
  }

  public void setAvatarVisible(boolean paramBoolean)
  {
    this.mAvatarVisible = paramBoolean;
  }

  public void setBitmap(Bitmap paramBitmap, boolean paramBoolean)
  {
    if (this.mAvatarRequest != null)
    {
      this.mAvatarBitmap = paramBitmap;
      invalidate();
    }
  }

  public void setCircleNameResolver(CircleNameResolver paramCircleNameResolver)
  {
    this.mCircleNameResolver = paramCircleNameResolver;
  }

  public void setContactIdAndAvatarUrl(String paramString1, String paramString2, String paramString3)
  {
    if (((!TextUtils.equals(this.mGaiaId, paramString1)) || (!TextUtils.equals(this.mContactLookupKey, paramString2))) && (this.mAvatarVisible))
    {
      this.mGaiaId = paramString1;
      this.mContactLookupKey = paramString2;
      this.mAvatarUrl = paramString3;
      if (this.mContactLookupKey == null)
        break label66;
      this.mAvatarRequest = null;
    }
    while (true)
    {
      this.mAvatarBitmap = null;
      requestLayout();
      return;
      label66: if (this.mGaiaId == null)
        this.mAvatarRequest = null;
      else
        this.mAvatarRequest = new AvatarRequest(this.mGaiaId, this.mAvatarUrl, this.mAvatarRequestSize);
    }
  }

  public void setContactName(String paramString)
  {
    this.mDisplayName = paramString;
    updateDisplayName();
  }

  public void setCustomText(String paramString)
  {
    this.mCircleListVisible = true;
    this.mCircleIconVisible = false;
    this.mCirclesTextView.setText(paramString);
  }

  public void setDefaultAvatar(Bitmap paramBitmap)
  {
    if (paramBitmap != null);
    while (true)
    {
      this.mDefaultAvatarBitmap = paramBitmap;
      return;
      paramBitmap = sDefaultUserImage;
    }
  }

  public void setFirstRow(boolean paramBoolean)
  {
    this.mFirstRow = paramBoolean;
    TextView localTextView = this.mNameTextView;
    if (this.mFirstRow);
    for (int i = 0; ; i = 8)
    {
      localTextView.setVisibility(i);
      return;
    }
  }

  @Deprecated
  public void setGaiaId(String paramString)
  {
    setGaiaIdAndAvatarUrl(paramString, null);
  }

  public void setGaiaIdAndAvatarUrl(String paramString1, String paramString2)
  {
    if ((!TextUtils.equals(this.mGaiaId, paramString1)) && (this.mAvatarVisible))
    {
      this.mGaiaId = paramString1;
      if (this.mGaiaId == null)
        break label57;
    }
    label57: for (this.mAvatarRequest = new AvatarRequest(paramString1, paramString2, this.mAvatarRequestSize); ; this.mAvatarRequest = null)
    {
      this.mAvatarBitmap = null;
      requestLayout();
      return;
    }
  }

  public void setHighlightedText(String paramString)
  {
    if (paramString == null);
    for (this.mHighlightedText = null; ; this.mHighlightedText = paramString.toUpperCase())
      return;
  }

  public void setOnActionButtonClickListener(OnActionButtonClickListener paramOnActionButtonClickListener)
  {
    this.mListener = paramOnActionButtonClickListener;
  }

  public void setPackedCircleIds(String paramString)
  {
    boolean bool1 = true;
    boolean bool2;
    if (this.mCircleNameResolver != null)
    {
      if (paramString == null)
        break label48;
      bool2 = bool1;
      this.mCircleListVisible = bool2;
      if (TextUtils.isEmpty(paramString))
        break label53;
    }
    while (true)
    {
      this.mCircleIconVisible = bool1;
      this.mCirclesTextView.setText(this.mCircleNameResolver.getCircleNamesForPackedIds(paramString));
      return;
      label48: bool2 = false;
      break;
      label53: bool1 = false;
    }
  }

  public void setPackedCircleIdsAndEmailAddress(String paramString1, String paramString2, String paramString3)
  {
    setPackedCircleIdsEmailAddressAndPhoneNumber(paramString1, paramString2, paramString3, null, null);
  }

  public void setPackedCircleIdsEmailAddressAndPhoneNumber(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    setPackedCircleIdsEmailAddressPhoneNumberAndSnippet(paramString1, paramString2, paramString3, paramString4, paramString5, null);
  }

  public void setPackedCircleIdsEmailAddressPhoneNumberAndSnippet(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    this.mTypeTextViewVisible = false;
    int i;
    label171: TextView localTextView2;
    if (!TextUtils.isEmpty(paramString4))
    {
      this.mCircleListVisible = true;
      this.mCircleIconVisible = false;
      this.mCirclesTextView.setText(PhoneNumberUtils.formatNumber(paramString4));
      String str3 = EsPeopleData.getStringForPhoneType(getContext(), paramString5);
      if (!TextUtils.isEmpty(str3))
      {
        if (this.mTypeTextView == null)
        {
          Context localContext = getContext();
          this.mTypeTextView = new TextView(localContext);
          this.mTypeTextView.setSingleLine(true);
          this.mTypeTextView.setTextAppearance(localContext, 16973892);
          this.mTypeTextView.setTextSize(this.mCirclesTextSize);
          this.mTypeTextView.setTextColor(this.mCirclesTextColor);
          this.mTypeTextView.setGravity(16);
          addView(this.mTypeTextView);
        }
        this.mTypeTextView.setText(str3.toUpperCase());
        this.mTypeTextViewVisible = true;
      }
      TextView localTextView1 = this.mCirclesTextView;
      if (!this.mCircleListVisible)
        break label562;
      i = 0;
      localTextView1.setVisibility(i);
      if (this.mTypeTextView != null)
      {
        localTextView2 = this.mTypeTextView;
        if (!this.mTypeTextViewVisible)
          break label569;
      }
    }
    label562: label569: for (int j = 0; ; j = 8)
    {
      localTextView2.setVisibility(j);
      return;
      if (!TextUtils.isEmpty(paramString3))
      {
        this.mCircleListVisible = true;
        if (!TextUtils.isEmpty(paramString1))
        {
          int k = 1;
          int n;
          for (int m = 0; ; m = n + 1)
          {
            n = paramString1.indexOf('|', m);
            if (n == -1)
              break;
            k++;
          }
          String str1 = "|" + paramString3;
          Resources localResources1 = getResources();
          int i1 = R.plurals.circle_count_and_matched_email;
          Object[] arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = Integer.valueOf(k);
          arrayOfObject1[1] = str1;
          localResources1.getQuantityString(i1, k, arrayOfObject1).toUpperCase().indexOf(str1);
          Resources localResources2 = getResources();
          int i2 = R.plurals.circle_count_and_matched_email;
          Object[] arrayOfObject2 = new Object[2];
          arrayOfObject2[0] = Integer.valueOf(k);
          arrayOfObject2[1] = paramString3;
          String str2 = localResources2.getQuantityString(i2, k, arrayOfObject2);
          SpannableUtils.setTextWithHighlight$5cdafd0b(this.mCirclesTextView, str2, this.mEmailTextBuilder, this.mHighlightedText, sBoldSpan, sColorSpan);
          this.mCircleIconVisible = true;
          break;
        }
        SpannableUtils.setTextWithHighlight$5cdafd0b(this.mCirclesTextView, paramString3, this.mEmailTextBuilder, this.mHighlightedText, sBoldSpan, sColorSpan);
        this.mCircleIconVisible = false;
        break;
      }
      if (!TextUtils.isEmpty(paramString1))
      {
        this.mCircleListVisible = true;
        this.mCircleIconVisible = true;
        if (this.mCircleNameResolver == null)
          break;
        this.mCirclesTextView.setText(this.mCircleNameResolver.getCircleNamesForPackedIds(paramString1));
        break;
      }
      if (!TextUtils.isEmpty(paramString2))
      {
        this.mCircleListVisible = true;
        this.mCircleIconVisible = false;
        this.mCirclesTextView.setText(paramString2);
        break;
      }
      if (!TextUtils.isEmpty(paramString6))
      {
        this.mCircleListVisible = true;
        this.mCircleIconVisible = false;
        this.mCirclesTextView.setText(Html.fromHtml(paramString6));
        break;
      }
      this.mCircleListVisible = false;
      this.mCircleIconVisible = false;
      this.mCirclesTextView.setText(null);
      break;
      i = 8;
      break label171;
    }
  }

  public void setPersonId(String paramString)
  {
    this.mPersonId = paramString;
  }

  public void setPlusPage(boolean paramBoolean)
  {
    this.mPlusPage = paramBoolean;
  }

  public void setRemoveButtonVisible(boolean paramBoolean)
  {
    if (this.mRemoveButtonVisible == paramBoolean);
    while (true)
    {
      return;
      this.mRemoveButtonVisible = paramBoolean;
      if (this.mRemoveButtonVisible)
      {
        if (this.mRemoveButton == null)
        {
          this.mRemoveButton = new ImageView(getContext());
          this.mRemoveButton.setBackgroundResource(this.mActionButtonResourceId);
          this.mRemoveButton.setOnClickListener(this);
          this.mRemoveButton.setFocusable(false);
          ImageView localImageView = this.mRemoveButton;
          if (sRemoveButtonIcon == null)
            sRemoveButtonIcon = getContext().getApplicationContext().getResources().getDrawable(R.drawable.ic_btn_dismiss_person);
          localImageView.setImageDrawable(sRemoveButtonIcon);
          this.mRemoveButton.setScaleType(ImageView.ScaleType.CENTER);
          this.mRemoveButton.setContentDescription(getResources().getString(R.string.remove_from_circles));
          addView(this.mRemoveButton);
        }
        this.mRemoveButton.setVisibility(0);
      }
      else if (this.mRemoveButton != null)
      {
        this.mRemoveButton.setVisibility(8);
      }
    }
  }

  public void setSectionHeader(char paramChar)
  {
    setSectionHeaderVisible(true);
    this.mSectionHeader.setText(String.valueOf(paramChar));
  }

  protected void setSectionHeaderBackgroundColor()
  {
    this.mSectionHeader.setBackgroundColor(getContext().getResources().getColor(R.color.section_header_opaque_bg));
  }

  public void setSectionHeaderVisible(boolean paramBoolean)
  {
    this.mSectionHeaderVisible = paramBoolean;
    if (this.mSectionHeaderVisible)
      if (this.mSectionHeader == null)
      {
        this.mSectionHeader = ((SectionHeaderView)LayoutInflater.from(getContext()).inflate(R.layout.section_header, this, false));
        setSectionHeaderBackgroundColor();
        addView(this.mSectionHeader);
      }
    while (true)
    {
      return;
      this.mSectionHeader.setVisibility(0);
      continue;
      if (this.mSectionHeader != null)
        this.mSectionHeader.setVisibility(8);
    }
  }

  public void setUnblockButtonVisible(boolean paramBoolean)
  {
    if (this.mUnblockButtonVisible == paramBoolean);
    while (true)
    {
      return;
      this.mUnblockButtonVisible = paramBoolean;
      if (this.mUnblockButtonVisible)
      {
        if (this.mUnblockButton == null)
        {
          this.mUnblockButton = new ImageView(getContext());
          this.mUnblockButton.setBackgroundResource(this.mActionButtonResourceId);
          this.mUnblockButton.setOnClickListener(this);
          this.mUnblockButton.setFocusable(false);
          ImageView localImageView = this.mUnblockButton;
          if (sUnblockButtonIcon == null)
            sUnblockButtonIcon = getContext().getApplicationContext().getResources().getDrawable(R.drawable.list_unblock);
          localImageView.setImageDrawable(sUnblockButtonIcon);
          this.mUnblockButton.setScaleType(ImageView.ScaleType.CENTER);
          this.mUnblockButton.setContentDescription(getResources().getString(R.string.menu_item_unblock_person));
          addView(this.mUnblockButton);
        }
        this.mUnblockButton.setVisibility(0);
      }
      else if (this.mUnblockButton != null)
      {
        this.mUnblockButton.setVisibility(8);
      }
    }
  }

  public void setWellFormedEmail(String paramString)
  {
    this.mWellFormedEmail = paramString;
    updateDisplayName();
  }

  public void setWellFormedSms(String paramString)
  {
    this.mWellFormedSmsMode = true;
    this.mWellFormedSms = paramString;
    this.mNameTextView.setText(paramString);
  }

  public void updateContentDescription()
  {
    Resources localResources = getResources();
    CharSequence localCharSequence = this.mCirclesTextView.getText();
    if ((this.mCircleListVisible) && (localCharSequence != null) && (localCharSequence.length() > 0))
    {
      int k = R.string.person_with_subtitle_entry_content_description;
      Object[] arrayOfObject3 = new Object[2];
      arrayOfObject3[0] = this.mDisplayName;
      arrayOfObject3[1] = localCharSequence;
      setContentDescription(localResources.getString(k, arrayOfObject3));
    }
    while (true)
    {
      return;
      if (this.mDisplayName != null)
      {
        int j = R.string.person_entry_content_description;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = this.mDisplayName;
        setContentDescription(localResources.getString(j, arrayOfObject2));
      }
      else if (this.mWellFormedEmail != null)
      {
        int i = R.string.person_entry_email_content_description;
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = this.mWellFormedEmail;
        setContentDescription(localResources.getString(i, arrayOfObject1));
      }
    }
  }

  public static abstract interface OnActionButtonClickListener
  {
    public abstract void onActionButtonClick(PeopleListItemView paramPeopleListItemView, int paramInt);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PeopleListItemView
 * JD-Core Version:    0.6.2
 */