package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.styleable;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.fragments.CircleNameResolver;
import com.google.android.apps.plus.fragments.CircleSpinnerAdapter;
import com.google.android.apps.plus.service.ImageResourceManager;
import com.google.android.apps.plus.service.Resource;
import com.google.android.apps.plus.service.ResourceConsumer;
import com.google.android.apps.plus.util.SpannableUtils;
import com.google.android.apps.plus.util.ViewUtils;

public class PersonCardView extends ViewGroup
  implements View.OnClickListener, ResourceConsumer
{
  private static Paint sAvatarPaint;
  private static final StyleSpan sBoldSpan = new StyleSpan(1);
  private static Bitmap sCircleIconBitmap;
  private static Paint sCircleIconPaint;
  private static ForegroundColorSpan sColorSpan;
  private static Bitmap sDefaultAvatar;
  private static Drawable sEmailIcon;
  private static boolean sInitialized;
  private static ImageResourceManager sResourceManager;
  private int mAction;
  private Button mActionButton;
  private int mActionButtonHeight;
  private int mActionButtonMinWidth;
  private int mActionButtonTextColor;
  private boolean mActionButtonVisible;
  private boolean mAutoWidth;
  private final Rect mAvatarBounds = new Rect();
  private final int mAvatarBoxHeight;
  private final Rect mAvatarOriginalBounds = new Rect();
  private Resource mAvatarResource;
  private final int mAvatarSize;
  private String mAvatarUrl;
  private final Drawable mBackground;
  private boolean mCircleChangePending;
  private int mCircleIconPaddingTop;
  private boolean mCircleIconVisible;
  private int mCircleIconX;
  private int mCircleIconY;
  private CircleNameResolver mCircleNameResolver;
  private CirclesButton mCirclesButton;
  private final int mCirclesButtonPadding;
  private String mContactLookupKey;
  private final TextView mDescriptionTextView;
  private boolean mDescriptionVisible;
  private ImageView mDismissButton;
  private Drawable mDismissButtonBackground;
  private final int mDismissButtonSize;
  private boolean mDismissButtonVisible;
  private String mDisplayName;
  private int mEmailIconPaddingRight;
  private int mEmailIconPaddingTop;
  private final SpannableStringBuilder mEmailTextBuilder = new SpannableStringBuilder();
  private boolean mForSharing;
  private boolean mForceAvatarDefault;
  private String mGaiaId;
  private final int mGapBetweenAvatarAndText;
  private final int mGapBetweenIconAndCircles;
  private final int mGapBetweenNameAndCircles;
  private String mHighlightedText;
  private final int mImageButtonMargin;
  private OnPersonCardClickListener mListener;
  private final int mMinHeight;
  private final int mMinWidth;
  private final SpannableStringBuilder mNameTextBuilder = new SpannableStringBuilder();
  private final TextView mNameTextView;
  private final int mNextCardPeekWidth;
  private int mOneClickMode = 0;
  private int mOptimalWidth;
  private final int mPaddingBottom;
  private final int mPaddingLeft;
  private final int mPaddingRight;
  private final int mPaddingTop;
  private String mPersonId;
  private boolean mPlusPage;
  private int mPosition;
  private final int mPreferredWidth;
  private final Drawable mSelector;
  private boolean mShowTooltip;
  private String mSuggestionId;
  private String mTooltipText;
  private String mWellFormedEmail;
  private boolean mWellFormedEmailMode;
  private int mWideLeftMargin;
  private boolean mWideMargin;

  public PersonCardView(Context paramContext)
  {
    this(paramContext, null);
  }

  public PersonCardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getApplicationContext().getResources();
    if (!sInitialized)
    {
      sInitialized = true;
      sResourceManager = ImageResourceManager.getInstance(paramContext);
      sDefaultAvatar = EsAvatarData.getMediumDefaultAvatar(paramContext, true);
      sCircleIconBitmap = ((BitmapDrawable)localResources.getDrawable(R.drawable.ic_circles)).getBitmap();
      Paint localPaint = new Paint();
      sAvatarPaint = localPaint;
      localPaint.setFilterBitmap(true);
      sCircleIconPaint = new Paint();
      sColorSpan = new ForegroundColorSpan(localResources.getColor(R.color.search_query_highlight_color));
      sEmailIcon = paramContext.getApplicationContext().getResources().getDrawable(R.drawable.profile_email);
    }
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.Theme);
    this.mDismissButtonBackground = localTypedArray.getDrawable(5);
    localTypedArray.recycle();
    this.mBackground = localResources.getDrawable(R.drawable.bg_tacos);
    this.mSelector = localResources.getDrawable(R.drawable.stream_list_selector);
    this.mSelector.setCallback(this);
    this.mPreferredWidth = localResources.getDimensionPixelSize(R.dimen.person_card_preferred_width);
    this.mMinWidth = localResources.getDimensionPixelSize(R.dimen.person_card_min_width);
    this.mMinHeight = localResources.getDimensionPixelSize(R.dimen.person_card_min_height);
    this.mPaddingTop = localResources.getDimensionPixelOffset(R.dimen.person_card_padding_top);
    this.mPaddingLeft = localResources.getDimensionPixelOffset(R.dimen.person_card_padding_left);
    int i = localResources.getDimensionPixelOffset(R.dimen.person_card_padding);
    this.mPaddingRight = i;
    this.mPaddingBottom = i;
    this.mWideLeftMargin = localResources.getDimensionPixelOffset(R.dimen.person_card_wide_left_margin);
    this.mAvatarSize = localResources.getDimensionPixelSize(R.dimen.person_card_avatar_size);
    this.mAvatarBoxHeight = localResources.getDimensionPixelOffset(R.dimen.person_card_avatar_box_height);
    this.mGapBetweenAvatarAndText = localResources.getDimensionPixelOffset(R.dimen.person_card_gap_between_avatar_and_text);
    this.mActionButtonMinWidth = localResources.getDimensionPixelSize(R.dimen.person_card_add_button_min_width);
    this.mActionButtonHeight = localResources.getDimensionPixelSize(R.dimen.person_card_add_button_height);
    this.mActionButtonTextColor = localResources.getColor(R.color.person_card_add_button_text_color);
    this.mCircleIconPaddingTop = localResources.getDimensionPixelOffset(R.dimen.person_card_circle_icon_padding_top);
    this.mGapBetweenIconAndCircles = localResources.getDimensionPixelOffset(R.dimen.person_card_gap_between_icon_and_circles);
    this.mGapBetweenNameAndCircles = localResources.getDimensionPixelOffset(R.dimen.person_card_gap_between_name_and_circles);
    this.mImageButtonMargin = localResources.getDimensionPixelOffset(R.dimen.person_card_dismiss_button_margin);
    this.mDismissButtonSize = localResources.getDimensionPixelOffset(R.dimen.person_card_dismiss_button_size);
    this.mCirclesButtonPadding = localResources.getDimensionPixelSize(R.dimen.person_card_circles_button_padding);
    this.mNextCardPeekWidth = localResources.getDimensionPixelSize(R.dimen.person_card_next_card_peek_width);
    this.mEmailIconPaddingTop = localResources.getDimensionPixelSize(R.dimen.person_card_email_icon_padding_top);
    this.mEmailIconPaddingRight = localResources.getDimensionPixelSize(R.dimen.person_card_email_icon_padding_right);
    float f1 = localResources.getDimension(R.dimen.person_card_name_text_size);
    float f2 = localResources.getDimension(R.dimen.person_card_circle_text_size);
    this.mNameTextView = new TextView(paramContext);
    this.mNameTextView.setMaxLines(2);
    this.mNameTextView.setEllipsize(TextUtils.TruncateAt.END);
    this.mNameTextView.setTextAppearance(paramContext, 16973892);
    this.mNameTextView.setTextSize(0, f1);
    this.mNameTextView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    this.mNameTextView.setTypeface(Typeface.DEFAULT_BOLD);
    addView(this.mNameTextView);
    this.mDescriptionTextView = new TextView(paramContext);
    this.mDescriptionTextView.setMaxLines(3);
    this.mDescriptionTextView.setGravity(48);
    this.mDescriptionTextView.setEllipsize(TextUtils.TruncateAt.END);
    this.mDescriptionTextView.setTextSize(0, f2);
    this.mDescriptionTextView.setTextColor(localResources.getColor(R.color.person_card_circle_text_color));
    this.mDescriptionTextView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    addView(this.mDescriptionTextView);
    setOnClickListener(this);
  }

  private void addCirclesButton()
  {
    this.mCirclesButton = new CirclesButton(getContext());
    this.mCirclesButton.setBackgroundResource(R.drawable.plusone_button);
    this.mCirclesButton.setPadding(this.mCirclesButtonPadding, 0, this.mCirclesButtonPadding, 0);
    this.mCirclesButton.setOnClickListener(this);
    addView(this.mCirclesButton);
  }

  public final void bindResources()
  {
    if ((ViewUtils.isViewAttached(this)) && (!this.mForceAvatarDefault) && (this.mAvatarUrl != null))
      this.mAvatarResource = sResourceManager.getAvatar(this.mAvatarUrl, 2, true, this);
  }

  public void dispatchDraw(Canvas paramCanvas)
  {
    this.mBackground.draw(paramCanvas);
    if (this.mCircleIconVisible)
      paramCanvas.drawBitmap(sCircleIconBitmap, this.mCircleIconX, this.mCircleIconY, sCircleIconPaint);
    Bitmap localBitmap = sDefaultAvatar;
    if ((this.mAvatarResource != null) && (this.mAvatarResource.getStatus() == 1))
      localBitmap = (Bitmap)this.mAvatarResource.getResource();
    this.mAvatarOriginalBounds.right = localBitmap.getWidth();
    this.mAvatarOriginalBounds.bottom = localBitmap.getHeight();
    paramCanvas.drawBitmap(localBitmap, this.mAvatarOriginalBounds, this.mAvatarBounds, sAvatarPaint);
    if (this.mContactLookupKey != null)
    {
      int i = sEmailIcon.getIntrinsicWidth();
      int j = sEmailIcon.getIntrinsicHeight();
      int k = this.mAvatarBounds.right - i + this.mEmailIconPaddingRight;
      int m = this.mAvatarBounds.top + this.mEmailIconPaddingTop;
      sEmailIcon.setBounds(k, m, k + i, m + j);
      sEmailIcon.draw(paramCanvas);
    }
    super.dispatchDraw(paramCanvas);
    if ((isPressed()) || (isFocused()))
      this.mSelector.draw(paramCanvas);
    if (this.mShowTooltip)
    {
      this.mShowTooltip = false;
      final int[] arrayOfInt = new int[2];
      this.mCirclesButton.getLocationOnScreen(arrayOfInt);
      post(new Runnable()
      {
        public final void run()
        {
          PersonCardView.access$000(PersonCardView.this, arrayOfInt[0], arrayOfInt[1], this.val$width, this.val$height);
        }
      });
    }
  }

  protected void drawableStateChanged()
  {
    this.mSelector.setState(getDrawableState());
    invalidate();
    super.drawableStateChanged();
  }

  public final String getContactName()
  {
    return this.mDisplayName;
  }

  public final String getPersonId()
  {
    return this.mPersonId;
  }

  public final int getPosition()
  {
    return this.mPosition;
  }

  public final String getSuggestionId()
  {
    return this.mSuggestionId;
  }

  public final String getWellFormedEmail()
  {
    return this.mWellFormedEmail;
  }

  public final boolean isForSharing()
  {
    return this.mForSharing;
  }

  public final boolean isOneClickAdd()
  {
    int i = 1;
    if (this.mOneClickMode == i);
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    bindResources();
  }

  public void onClick(View paramView)
  {
    if (this.mCircleChangePending);
    while (true)
    {
      return;
      if (this.mListener != null)
        if (paramView == this.mActionButton)
          this.mListener.onActionButtonClick(this, this.mAction);
        else if (paramView == this.mCirclesButton)
          this.mListener.onChangeCircles(this);
        else if (paramView == this.mDismissButton)
          this.mListener.onDismissButtonClick(this);
        else
          this.mListener.onItemClick(this);
    }
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    unbindResources();
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool1 = this.mWideMargin;
    int i = 0;
    if (bool1)
      i = this.mWideLeftMargin;
    this.mBackground.setBounds(i, 0, paramInt3 - paramInt1, paramInt4 - paramInt2);
    this.mSelector.setBounds(i, 0, paramInt3 - paramInt1, paramInt4 - paramInt2);
    int j = paramInt3 - paramInt1;
    int k = paramInt4 - paramInt2;
    int m = this.mPaddingTop;
    int n = i + this.mPaddingLeft;
    int i1 = j - this.mPaddingRight;
    if (this.mDismissButtonVisible)
    {
      int i10 = this.mDismissButton.getMeasuredWidth();
      int i11 = this.mDismissButton.getMeasuredHeight();
      this.mDismissButton.layout(j - this.mImageButtonMargin - i10, this.mImageButtonMargin, j - this.mImageButtonMargin, i11 + this.mImageButtonMargin);
    }
    this.mAvatarBounds.left = n;
    this.mAvatarBounds.right = (this.mAvatarBounds.left + this.mAvatarSize);
    this.mAvatarBounds.top = ((this.mAvatarBoxHeight - this.mAvatarSize) / 2);
    this.mAvatarBounds.bottom = (this.mAvatarBounds.top + this.mAvatarSize);
    int i2 = n + (this.mAvatarSize + this.mGapBetweenAvatarAndText);
    int i3 = this.mOneClickMode;
    Object localObject = null;
    switch (i3)
    {
    case 2:
    default:
      if (localObject != null)
      {
        int i7 = ((View)localObject).getMeasuredWidth();
        int i8 = ((View)localObject).getMeasuredHeight();
        int i9 = k - this.mPaddingBottom - i8;
        ((View)localObject).layout(i2, i9, i2 + i7, i9 + i8);
      }
      int i4 = this.mNameTextView.getMeasuredHeight();
      this.mNameTextView.layout(i2, m, i1, m + i4);
      int i5 = i2;
      int i6 = i4 + this.mPaddingTop + this.mGapBetweenNameAndCircles;
      if (this.mCircleIconVisible)
        i5 += sCircleIconBitmap.getWidth() + this.mGapBetweenIconAndCircles;
      if (this.mDescriptionVisible)
        this.mDescriptionTextView.layout(i5, i6, i5 + this.mDescriptionTextView.getMeasuredWidth(), i6 + this.mDescriptionTextView.getMeasuredHeight());
      if (this.mCircleIconVisible)
      {
        this.mCircleIconX = i2;
        this.mCircleIconY = i6;
        if (this.mDescriptionTextView.getLineCount() <= 1)
          break label504;
      }
      break;
    case 0:
    case 1:
    case 3:
    }
    label504: for (this.mCircleIconY += this.mCircleIconPaddingTop; ; this.mCircleIconY += (this.mDescriptionTextView.getMeasuredHeight() - sCircleIconBitmap.getHeight()) / 2)
    {
      return;
      boolean bool2 = this.mActionButtonVisible;
      localObject = null;
      if (!bool2)
        break;
      localObject = this.mActionButton;
      break;
      localObject = this.mCirclesButton;
      break;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i;
    int j;
    int n;
    int i1;
    Object localObject;
    if (this.mAutoWidth)
    {
      if (this.mOptimalWidth == 0)
      {
        int i7 = getContext().getResources().getDisplayMetrics().widthPixels - this.mNextCardPeekWidth;
        this.mOptimalWidth = Math.min(Math.max(i7 / Math.max((-1 + (i7 + this.mPreferredWidth)) / this.mPreferredWidth, 1), this.mMinWidth), this.mPreferredWidth);
      }
      i = resolveSize(this.mOptimalWidth, paramInt1);
      j = resolveSize(this.mMinHeight, paramInt2);
      boolean bool1 = this.mWideMargin;
      int k = 0;
      if (bool1)
      {
        i += this.mWideLeftMargin;
        k = this.mWideLeftMargin;
      }
      int m = i - this.mPaddingLeft - this.mPaddingRight - k;
      n = j - this.mPaddingTop - this.mPaddingBottom;
      i1 = m - (this.mAvatarSize + this.mGapBetweenAvatarAndText);
      int i2 = this.mOneClickMode;
      localObject = null;
      switch (i2)
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
      if (localObject != null)
      {
        ((View)localObject).measure(View.MeasureSpec.makeMeasureSpec(i1, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mActionButtonHeight, 1073741824));
        n -= ((View)localObject).getMeasuredHeight() + this.mGapBetweenNameAndCircles;
      }
      int i3 = i1;
      if (this.mDismissButtonVisible)
        i3 -= this.mImageButtonMargin;
      this.mNameTextView.measure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), View.MeasureSpec.makeMeasureSpec(n, -2147483648));
      int i4 = n - (this.mNameTextView.getMeasuredHeight() + this.mGapBetweenNameAndCircles);
      int i5 = i1;
      if (this.mCircleIconVisible)
        i5 -= sCircleIconBitmap.getWidth() + this.mGapBetweenIconAndCircles;
      if (this.mDescriptionVisible)
      {
        int i6 = this.mDescriptionTextView.getLineHeight();
        this.mDescriptionTextView.setMaxLines(i4 / i6);
        this.mDescriptionTextView.measure(View.MeasureSpec.makeMeasureSpec(i5, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mMinHeight, -2147483648));
        this.mDescriptionTextView.measure(View.MeasureSpec.makeMeasureSpec(this.mDescriptionTextView.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(this.mDescriptionTextView.getMeasuredHeight(), 1073741824));
      }
      if (this.mDismissButtonVisible)
        this.mDismissButton.measure(View.MeasureSpec.makeMeasureSpec(this.mDismissButtonSize, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mDismissButtonSize, 1073741824));
      setMeasuredDimension(i, j);
      return;
      i = resolveSize(this.mPreferredWidth, paramInt1);
      break;
      boolean bool2 = this.mActionButtonVisible;
      localObject = null;
      if (bool2)
      {
        localObject = this.mActionButton;
        continue;
        localObject = this.mCirclesButton;
      }
    }
  }

  public final void onResourceStatusChange$1574fca0(Resource paramResource)
  {
    invalidate();
  }

  public void setActionButtonVisible(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    if (this.mActionButtonVisible == paramBoolean)
      if (this.mActionButtonVisible)
      {
        this.mAction = paramInt2;
        this.mActionButton.setText(paramInt1);
      }
    while (true)
    {
      return;
      this.mActionButtonVisible = paramBoolean;
      if (this.mActionButtonVisible)
      {
        if (this.mActionButton == null)
        {
          this.mActionButton = new Button(getContext());
          this.mActionButton.setOnClickListener(this);
          this.mActionButton.setBackgroundResource(R.drawable.plusone_button);
          this.mActionButton.setFocusable(false);
          this.mActionButton.setTextColor(this.mActionButtonTextColor);
          this.mActionButton.setMinimumWidth(this.mActionButtonMinWidth);
          addView(this.mActionButton);
        }
        this.mAction = paramInt2;
        this.mActionButton.setText(paramInt1);
        this.mActionButton.setVisibility(0);
      }
      else if (this.mActionButton != null)
      {
        this.mActionButton.setVisibility(8);
      }
    }
  }

  public void setAutoWidthForHorizontalScrolling()
  {
    this.mAutoWidth = true;
  }

  public void setCircleNameResolver(CircleNameResolver paramCircleNameResolver)
  {
    this.mCircleNameResolver = paramCircleNameResolver;
  }

  public void setContactIdAndAvatarUrl(String paramString1, String paramString2, String paramString3)
  {
    if ((!TextUtils.equals(this.mGaiaId, paramString1)) || (!TextUtils.equals(this.mContactLookupKey, paramString2)))
    {
      unbindResources();
      this.mGaiaId = paramString1;
      this.mContactLookupKey = paramString2;
      this.mAvatarUrl = paramString3;
      bindResources();
    }
  }

  public void setContactName(String paramString)
  {
    this.mDisplayName = paramString;
    SpannableUtils.setTextWithHighlight$5cdafd0b(this.mNameTextView, paramString, this.mNameTextBuilder, this.mHighlightedText, sBoldSpan, sColorSpan);
  }

  public void setDescription(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      this.mDescriptionVisible = true;
      this.mCircleIconVisible = false;
      if (paramBoolean1)
        this.mDescriptionTextView.setText(Html.fromHtml(paramString));
    }
    while (true)
    {
      return;
      this.mDescriptionTextView.setText(paramString);
      continue;
      this.mDescriptionVisible = false;
      this.mCircleIconVisible = false;
      this.mDescriptionTextView.setText(null);
    }
  }

  public void setDismissActionButtonVisible(boolean paramBoolean)
  {
    if (this.mDismissButtonVisible == paramBoolean);
    while (true)
    {
      return;
      this.mDismissButtonVisible = paramBoolean;
      if (this.mDismissButtonVisible)
      {
        if (this.mDismissButton == null)
        {
          this.mDismissButton = new ImageView(getContext());
          this.mDismissButton.setBackgroundDrawable(this.mDismissButtonBackground);
          this.mDismissButton.setOnClickListener(this);
          this.mDismissButton.setFocusable(false);
          this.mDismissButton.setImageResource(R.drawable.ic_friend_dismiss);
          this.mDismissButton.setScaleType(ImageView.ScaleType.CENTER);
          this.mDismissButton.setContentDescription(getResources().getString(R.string.menu_dismiss_people));
          addView(this.mDismissButton);
        }
        this.mDismissButton.setVisibility(0);
      }
      else if (this.mDismissButton != null)
      {
        this.mDismissButton.setVisibility(8);
      }
    }
  }

  public void setForceAvatarDefault(boolean paramBoolean)
  {
    this.mForceAvatarDefault = paramBoolean;
    if (paramBoolean)
    {
      if (this.mAvatarResource != null)
        this.mAvatarResource.unregister(this);
      this.mAvatarResource = null;
    }
  }

  public void setGaiaIdAndAvatarUrl(String paramString1, String paramString2)
  {
    if (!TextUtils.equals(this.mGaiaId, paramString1))
    {
      unbindResources();
      this.mGaiaId = paramString1;
      this.mAvatarUrl = paramString2;
      bindResources();
    }
  }

  public void setHighlightedText(String paramString)
  {
    if (paramString == null);
    for (this.mHighlightedText = null; ; this.mHighlightedText = paramString.toUpperCase())
      return;
  }

  public void setOnPersonCardClickListener(OnPersonCardClickListener paramOnPersonCardClickListener)
  {
    this.mListener = paramOnPersonCardClickListener;
  }

  public void setOneClickCircles(String paramString, CircleSpinnerAdapter paramCircleSpinnerAdapter, boolean paramBoolean)
  {
    if (TextUtils.isEmpty(paramString))
    {
      this.mOneClickMode = 1;
      if (this.mCirclesButton == null)
        addCirclesButton();
      if (this.mOneClickMode != 1)
        break label123;
      if (!paramBoolean)
        break label103;
      this.mCirclesButton.setText(getContext().getString(R.string.add_to_circles));
      label52: this.mCirclesButton.setShowIcon(false);
      this.mCirclesButton.setHighlighted(true);
    }
    while (true)
    {
      this.mCirclesButton.setVisibility(0);
      this.mDescriptionVisible = true;
      this.mDescriptionTextView.setVisibility(0);
      this.mForSharing = paramBoolean;
      return;
      this.mOneClickMode = 3;
      break;
      label103: this.mCirclesButton.setText(getContext().getString(R.string.follow));
      break label52;
      label123: this.mCirclesButton.setCircles(this.mCircleNameResolver.getCircleNameListForPackedIds(paramString));
      this.mCirclesButton.setShowIcon(true);
      this.mCirclesButton.setHighlighted(false);
    }
  }

  public void setPackedCircleIdsEmailAndDescription(String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (!TextUtils.isEmpty(paramString1))
    {
      this.mDescriptionVisible = true;
      this.mCircleIconVisible = true;
      this.mDescriptionTextView.setText(this.mCircleNameResolver.getCircleNamesForPackedIds(paramString1));
    }
    while (true)
    {
      return;
      if (!TextUtils.isEmpty(paramString2))
      {
        this.mDescriptionVisible = true;
        this.mCircleIconVisible = false;
        SpannableUtils.setTextWithHighlight$5cdafd0b(this.mDescriptionTextView, paramString2, this.mEmailTextBuilder, this.mHighlightedText, sBoldSpan, sColorSpan);
      }
      else if (!TextUtils.isEmpty(paramString3))
      {
        this.mDescriptionVisible = true;
        this.mCircleIconVisible = false;
        if (paramBoolean1)
          this.mDescriptionTextView.setText(Html.fromHtml(paramString3));
        else
          this.mDescriptionTextView.setText(paramString3);
      }
      else
      {
        this.mDescriptionVisible = false;
        this.mCircleIconVisible = false;
        this.mDescriptionTextView.setText(null);
      }
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

  public void setPosition(int paramInt)
  {
    this.mPosition = paramInt;
  }

  public void setShowCircleChangePending(boolean paramBoolean)
  {
    if (this.mCircleChangePending != paramBoolean)
    {
      this.mCircleChangePending = paramBoolean;
      if (this.mCircleChangePending)
      {
        if (this.mCirclesButton == null)
        {
          this.mOneClickMode = 1;
          addCirclesButton();
        }
        this.mCirclesButton.setText("");
        this.mCirclesButton.setShowIcon(false);
        this.mCirclesButton.setHighlighted(false);
        this.mCirclesButton.setVisibility(0);
      }
      if (this.mCirclesButton != null)
        this.mCirclesButton.setShowProgressIndicator(paramBoolean);
    }
  }

  public void setShowTooltip(boolean paramBoolean, int paramInt)
  {
    this.mShowTooltip = paramBoolean;
    if (this.mShowTooltip)
    {
      this.mTooltipText = getContext().getString(paramInt);
      invalidate();
    }
  }

  public void setSuggestionId(String paramString)
  {
    this.mSuggestionId = paramString;
  }

  public void setWellFormedEmail(String paramString)
  {
    this.mWellFormedEmailMode = true;
    this.mWellFormedEmail = paramString;
    this.mNameTextView.setText(paramString);
  }

  public void setWideMargin(boolean paramBoolean)
  {
    if (this.mWideMargin != paramBoolean)
    {
      this.mWideMargin = paramBoolean;
      requestLayout();
    }
  }

  public final void unbindResources()
  {
    if (this.mAvatarResource != null)
    {
      this.mAvatarResource.unregister(this);
      this.mAvatarResource = null;
    }
  }

  public final void updateContentDescription()
  {
    Resources localResources = getResources();
    CharSequence localCharSequence = this.mDescriptionTextView.getText();
    if ((this.mDescriptionVisible) && (!TextUtils.isEmpty(localCharSequence)))
    {
      int j = R.string.person_with_subtitle_entry_content_description;
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = this.mDisplayName;
      arrayOfObject2[1] = localCharSequence;
      setContentDescription(localResources.getString(j, arrayOfObject2));
    }
    while (true)
    {
      return;
      int i = R.string.person_entry_content_description;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = this.mDisplayName;
      setContentDescription(localResources.getString(i, arrayOfObject1));
    }
  }

  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    if (paramDrawable == this.mSelector);
    for (boolean bool = true; ; bool = super.verifyDrawable(paramDrawable))
      return bool;
  }

  public static abstract interface OnPersonCardClickListener
  {
    public abstract void onActionButtonClick(PersonCardView paramPersonCardView, int paramInt);

    public abstract void onChangeCircles(PersonCardView paramPersonCardView);

    public abstract void onDismissButtonClick(PersonCardView paramPersonCardView);

    public abstract void onItemClick(PersonCardView paramPersonCardView);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PersonCardView
 * JD-Core Version:    0.6.2
 */