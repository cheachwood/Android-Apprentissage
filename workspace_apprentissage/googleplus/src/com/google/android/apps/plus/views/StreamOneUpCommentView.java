package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.DbPlusOneData;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.service.Resource;
import com.google.android.apps.plus.service.ResourceConsumer;
import com.google.android.apps.plus.util.Dates;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.TextPaintUtils;
import com.google.android.apps.plus.util.ViewUtils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class StreamOneUpCommentView extends OneUpBaseView
  implements ResourceConsumer, Recyclable
{
  private static int sAvatarMarginRight;
  private static int sAvatarMarginTop;
  private static Bitmap sAvatarOverlayBitmap;
  private static int sAvatarSize;
  private static Paint sBackgroundFadePaint;
  private static Paint sBackgroundPaint;
  private static TextPaint sContentPaint;
  private static TextPaint sDatePaint;
  private static Bitmap sDefaultAvatarBitmap;
  private static Paint sDividerPaint;
  private static int sDividerThickness;
  private static Rect sFlaggedCommentFadeArea;
  private static float sFontSpacing;
  private static int sMarginBottom;
  private static int sMarginLeft;
  private static int sMarginRight;
  private static int sMarginTop;
  private static int sNameMarginRight;
  private static TextPaint sNamePaint;
  private static int sPlusOneColor;
  private static int sPlusOneInverseColor;
  private static TextPaint sPlusOnePaint;
  protected static Drawable sPressedStateBackground;
  private static Paint sResizePaint;
  private String mAuthorAvatarUrl;
  private String mAuthorId;
  private ClickableAvatar mAuthorImage;
  private String mAuthorName;
  private Set<ClickableItem> mClickableItems = new HashSet();
  private String mCommentContent;
  private String mCommentId;
  private boolean mContentDescriptionDirty = true;
  private ClickableStaticLayout mContentLayout;
  private ClickableItem mCurrentClickableItem;
  private String mDate;
  private PositionedStaticLayout mDateLayout;
  private boolean mIsFlagged;
  private PositionedStaticLayout mNameLayout;
  private OneUpListener mOneUpListener;
  private boolean mPlusOneByMe;
  private int mPlusOneCount;
  private String mPlusOneId;
  private boolean mPressed;
  private SetPressedRunnable mSetPressedRunnable;

  public StreamOneUpCommentView(Context paramContext)
  {
    this(paramContext, null);
  }

  public StreamOneUpCommentView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public StreamOneUpCommentView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (sNamePaint == null)
    {
      Resources localResources = paramContext.getResources();
      sPressedStateBackground = localResources.getDrawable(R.drawable.list_selected_holo);
      sFontSpacing = localResources.getDimension(R.dimen.stream_one_up_font_spacing);
      sAvatarSize = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_avatar_size);
      sMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_comment_margin_top);
      sMarginLeft = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_comment_margin_left);
      sMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_comment_margin_right);
      sMarginBottom = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_comment_margin_bottom);
      sAvatarMarginTop = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_comment_avatar_margin_top);
      sAvatarMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_comment_avatar_margin_right);
      sNameMarginRight = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_comment_name_margin_right);
      sDividerThickness = localResources.getDimensionPixelOffset(R.dimen.stream_one_up_comment_divider_thickness);
      sPlusOneColor = localResources.getColor(R.color.stream_one_up_comment_plus_one);
      sPlusOneInverseColor = localResources.getColor(R.color.stream_one_up_comment_plus_one_inverse);
      sDefaultAvatarBitmap = EsAvatarData.getMediumDefaultAvatar(getContext(), true);
      sAvatarOverlayBitmap = ImageUtils.decodeResource(localResources, R.drawable.bg_taco_avatar);
      TextPaint localTextPaint1 = new TextPaint();
      sNamePaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sNamePaint.setTypeface(Typeface.DEFAULT_BOLD);
      sNamePaint.setColor(localResources.getColor(R.color.stream_one_up_comment_name));
      sNamePaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_comment_name_text_size));
      TextPaintUtils.registerTextPaint(sNamePaint, R.dimen.stream_one_up_comment_name_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sDatePaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sDatePaint.setColor(localResources.getColor(R.color.stream_one_up_comment_date));
      sDatePaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_comment_date_text_size));
      TextPaintUtils.registerTextPaint(sDatePaint, R.dimen.stream_one_up_comment_date_text_size);
      TextPaint localTextPaint3 = new TextPaint();
      sContentPaint = localTextPaint3;
      localTextPaint3.setAntiAlias(true);
      sContentPaint.setColor(localResources.getColor(R.color.stream_one_up_comment_body));
      sContentPaint.linkColor = localResources.getColor(R.color.stream_one_up_link);
      sContentPaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_comment_content_text_size));
      TextPaintUtils.registerTextPaint(sContentPaint, R.dimen.stream_one_up_comment_content_text_size);
      TextPaint localTextPaint4 = new TextPaint();
      sPlusOnePaint = localTextPaint4;
      localTextPaint4.setTypeface(Typeface.DEFAULT_BOLD);
      sPlusOnePaint.setTextSize(localResources.getDimension(R.dimen.stream_one_up_comment_plus_one_text_size));
      TextPaintUtils.registerTextPaint(sPlusOnePaint, R.dimen.stream_one_up_comment_plus_one_text_size);
      Paint localPaint1 = new Paint();
      sBackgroundPaint = localPaint1;
      localPaint1.setColor(localResources.getColor(R.color.stream_one_up_list_background));
      sBackgroundPaint.setStyle(Paint.Style.FILL);
      Paint localPaint2 = new Paint();
      sBackgroundFadePaint = localPaint2;
      localPaint2.setColor(localResources.getColor(R.color.stream_one_up_list_background_fade));
      sBackgroundFadePaint.setStyle(Paint.Style.FILL);
      sFlaggedCommentFadeArea = new Rect();
      Paint localPaint3 = new Paint();
      sDividerPaint = localPaint3;
      localPaint3.setColor(localResources.getColor(R.color.stream_one_up_comment_divider));
      sDividerPaint.setStyle(Paint.Style.STROKE);
      sDividerPaint.setStrokeWidth(sDividerThickness);
      sResizePaint = new Paint(2);
    }
  }

  private void removeSetPressedRunnable()
  {
    if (this.mSetPressedRunnable != null)
      removeCallbacks(this.mSetPressedRunnable);
  }

  public final void bind(Cursor paramCursor, boolean paramBoolean)
  {
    setAuthor(paramCursor.getString(2), paramCursor.getString(3), EsAvatarData.uncompressAvatarUrl(paramCursor.getString(4)));
    setComment(paramCursor.getString(5), paramCursor.getString(6), paramBoolean);
    setDate(paramCursor.getLong(7));
    setPlusOne(paramCursor.getBlob(8));
    invalidate();
    requestLayout();
  }

  public final void bindResources()
  {
    if ((ViewUtils.isViewAttached(this)) && (this.mAuthorImage != null))
      this.mAuthorImage.bindResources();
  }

  public final void cancelPressedState()
  {
    if (this.mPressed)
    {
      this.mPressed = false;
      invalidate();
    }
    removeSetPressedRunnable();
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
        boolean bool;
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
      if (this.mSetPressedRunnable == null)
        this.mSetPressedRunnable = new SetPressedRunnable((byte)0);
      postDelayed(this.mSetPressedRunnable, ViewConfiguration.getTapTimeout());
      i = 0;
      continue;
      this.mCurrentClickableItem = null;
      this.mPressed = false;
      removeSetPressedRunnable();
      localIterator1 = this.mClickableItems.iterator();
      while (localIterator1.hasNext())
        ((ClickableItem)localIterator1.next()).handleEvent(k, m, i);
      invalidate();
      j = 0;
      continue;
      bool = this.mPressed;
      this.mPressed = false;
      removeSetPressedRunnable();
      if (this.mCurrentClickableItem != null)
      {
        this.mCurrentClickableItem.handleEvent(k, m, 3);
        this.mCurrentClickableItem = null;
        invalidate();
      }
      else
      {
        if (bool)
          invalidate();
        j = 0;
        continue;
        if ((k < 0) || (k >= getWidth()) || (m < 0) || (m >= getHeight()))
          removeSetPressedRunnable();
        j = 0;
      }
    }
  }

  public final String getAuthorId()
  {
    return this.mAuthorId;
  }

  public final String getCommentContent()
  {
    return this.mCommentContent;
  }

  public final String getCommentId()
  {
    return this.mCommentId;
  }

  public final boolean getPlusOneByMe()
  {
    return this.mPlusOneByMe;
  }

  public final int getPlusOneCount()
  {
    return this.mPlusOneCount;
  }

  public final String getPlusOneId()
  {
    return this.mPlusOneId;
  }

  public void invalidate()
  {
    super.invalidate();
    if (this.mContentDescriptionDirty)
    {
      StringBuffer localStringBuffer = new StringBuffer(256);
      if (this.mAuthorName != null)
        localStringBuffer.append(this.mAuthorName).append('\n');
      if (this.mCommentContent != null)
        localStringBuffer.append(this.mCommentContent).append('\n');
      if (this.mDate != null)
        localStringBuffer.append(this.mDate).append('\n');
      if (this.mPlusOneCount > 0)
        localStringBuffer.append('+').append(this.mPlusOneCount);
      setContentDescription(localStringBuffer.toString());
      this.mContentDescriptionDirty = false;
    }
  }

  public final boolean isFlagged()
  {
    return this.mIsFlagged;
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

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = getWidth();
    int j = getHeight();
    paramCanvas.drawRect(0.0F, 0.0F, i, j, sBackgroundPaint);
    if (this.mNameLayout != null)
      if (this.mAuthorImage.getBitmap() == null)
        break label377;
    label377: for (Bitmap localBitmap = this.mAuthorImage.getBitmap(); ; localBitmap = sDefaultAvatarBitmap)
    {
      paramCanvas.drawBitmap(localBitmap, null, this.mAuthorImage.getRect(), sResizePaint);
      paramCanvas.drawBitmap(sAvatarOverlayBitmap, null, this.mAuthorImage.getRect(), sResizePaint);
      if (this.mAuthorImage.isClicked())
        this.mAuthorImage.drawSelectionRect(paramCanvas);
      int k = this.mNameLayout.getLeft();
      int m = this.mNameLayout.getTop();
      paramCanvas.translate(k, m);
      this.mNameLayout.draw(paramCanvas);
      paramCanvas.translate(-k, -m);
      int n = this.mDateLayout.getLeft();
      int i1 = this.mDateLayout.getTop();
      paramCanvas.translate(n, i1);
      this.mDateLayout.draw(paramCanvas);
      paramCanvas.translate(-n, -i1);
      int i2 = this.mContentLayout.getLeft();
      int i3 = this.mContentLayout.getTop();
      paramCanvas.translate(i2, i3);
      this.mContentLayout.draw(paramCanvas);
      paramCanvas.translate(-i2, -i3);
      if (this.mIsFlagged)
      {
        Rect localRect = this.mAuthorImage.getRect();
        int i4 = Math.max(localRect.bottom, this.mContentLayout.getBottom());
        sFlaggedCommentFadeArea.set(localRect.left, localRect.top, this.mContentLayout.getRight(), i4);
        paramCanvas.drawRect(sFlaggedCommentFadeArea, sBackgroundFadePaint);
      }
      if (this.mPressed)
      {
        sPressedStateBackground.setBounds(0, 0, i, j - sDividerThickness);
        sPressedStateBackground.draw(paramCanvas);
      }
      paramCanvas.drawLine(sMarginLeft, j - sDividerThickness, i - sMarginRight, j - sDividerThickness, sDividerPaint);
      return;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = getPaddingLeft() + sMarginLeft;
    int j = getPaddingTop() + sMarginTop;
    int k = getMeasuredWidth();
    int m = k - i - getPaddingRight() - sMarginRight;
    this.mClickableItems.clear();
    this.mCurrentClickableItem = null;
    unbindResources();
    int n = j + sAvatarMarginTop;
    this.mAuthorImage = new ClickableAvatar(this, this.mAuthorId, this.mAuthorAvatarUrl, this.mAuthorName, this.mOneUpListener, 2);
    this.mClickableItems.add(this.mAuthorImage);
    this.mAuthorImage.setRect(i, n, i + sAvatarSize, n + sAvatarSize);
    int i1 = i + (sAvatarSize + sAvatarMarginRight);
    int i2 = n - sAvatarMarginTop;
    int i3 = (int)sDatePaint.measureText(this.mDate);
    this.mDateLayout = new PositionedStaticLayout(this.mDate, sDatePaint, i3, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false);
    int i4 = m - sAvatarSize - sAvatarMarginRight - i3;
    CharSequence localCharSequence = TextUtils.ellipsize(this.mAuthorName, sNamePaint, i4, TextUtils.TruncateAt.END);
    int i5 = Math.min(m - sAvatarSize - sAvatarMarginRight - i3, (int)sNamePaint.measureText(localCharSequence, 0, localCharSequence.length()));
    this.mNameLayout = new PositionedStaticLayout(localCharSequence, sNamePaint, i5, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false);
    this.mNameLayout.setPosition(i1, i2);
    int i6 = i1 + (this.mNameLayout.getWidth() + sNameMarginRight);
    int i7 = i2 + (sDatePaint.getFontMetricsInt().ascent - sNamePaint.getFontMetricsInt().ascent);
    this.mDateLayout.setPosition(i6, i7);
    int i8 = m - sAvatarSize - sAvatarMarginRight;
    int i9 = i + sAvatarSize + sAvatarMarginRight;
    int i10 = j + this.mNameLayout.getHeight();
    SpannableStringBuilder localSpannableStringBuilder2;
    int i13;
    if (this.mPlusOneCount > 0)
    {
      Resources localResources = getResources();
      int i12 = R.string.stream_plus_one_count_with_plus;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(Math.max(this.mPlusOneCount, 1));
      String str = localResources.getString(i12, arrayOfObject);
      localSpannableStringBuilder2 = ClickableStaticLayout.buildStateSpans(this.mCommentContent + " &nbsp; " + str);
      TextAppearanceSpan localTextAppearanceSpan = new TextAppearanceSpan(null, 1, (int)sPlusOnePaint.getTextSize(), null, null);
      if (this.mPlusOneByMe)
      {
        i13 = sPlusOneInverseColor;
        ForegroundColorSpan localForegroundColorSpan = new ForegroundColorSpan(i13);
        int i14 = localSpannableStringBuilder2.length() - str.length();
        int i15 = localSpannableStringBuilder2.length();
        localSpannableStringBuilder2.setSpan(localTextAppearanceSpan, i14, i15, 33);
        localSpannableStringBuilder2.setSpan(localForegroundColorSpan, i14, i15, 33);
      }
    }
    for (SpannableStringBuilder localSpannableStringBuilder1 = localSpannableStringBuilder2; ; localSpannableStringBuilder1 = ClickableStaticLayout.buildStateSpans(this.mCommentContent))
    {
      this.mClickableItems.remove(this.mContentLayout);
      this.mContentLayout = new ClickableStaticLayout(localSpannableStringBuilder1, sContentPaint, i8, Layout.Alignment.ALIGN_NORMAL, sFontSpacing, 0.0F, false, this.mOneUpListener);
      this.mContentLayout.setPosition(i9, i10);
      this.mClickableItems.add(this.mContentLayout);
      int i11 = i10 + this.mContentLayout.getHeight();
      bindResources();
      setMeasuredDimension(k, Math.max(this.mAuthorImage.getRect().height(), i11) + sMarginBottom + sDividerThickness + getPaddingBottom());
      if (this.mOnMeasuredListener != null)
        this.mOnMeasuredListener.onMeasured(this);
      return;
      i13 = sPlusOneColor;
      break;
    }
  }

  public void onRecycle()
  {
    unbindResources();
    this.mNameLayout = null;
    this.mDateLayout = null;
    this.mContentLayout = null;
    this.mAuthorImage = null;
    this.mClickableItems.clear();
    this.mCurrentClickableItem = null;
    this.mCommentContent = null;
    this.mPlusOneId = null;
    this.mPlusOneByMe = false;
    this.mPlusOneCount = 0;
    this.mOneUpListener = null;
    this.mPressed = false;
  }

  public final void onResourceStatusChange$1574fca0(Resource paramResource)
  {
  }

  public void setAuthor(String paramString1, String paramString2, String paramString3)
  {
    if (TextUtils.equals(paramString1, this.mAuthorId));
    while (true)
    {
      return;
      this.mAuthorId = paramString1;
      this.mAuthorName = paramString2;
      this.mAuthorAvatarUrl = paramString3;
      if (this.mAuthorName == null)
      {
        this.mAuthorName = "";
        Log.w("StreamOneUp", "===> Author name was null for gaia id: " + this.mAuthorId);
      }
      if (this.mAuthorImage != null)
      {
        this.mAuthorImage.unbindResources();
        this.mAuthorImage = null;
      }
      this.mNameLayout = null;
      this.mAuthorImage = null;
      this.mContentDescriptionDirty = true;
    }
  }

  public void setComment(String paramString1, String paramString2, boolean paramBoolean)
  {
    this.mCommentId = paramString1;
    this.mCommentContent = paramString2;
    this.mIsFlagged = paramBoolean;
    this.mContentLayout = null;
    this.mContentDescriptionDirty = true;
  }

  public void setDate(long paramLong)
  {
    this.mDate = Dates.getAbbreviatedRelativeTimeSpanString(getContext(), paramLong).toString();
    this.mDateLayout = null;
    this.mContentDescriptionDirty = true;
  }

  public void setOneUpClickListener(OneUpListener paramOneUpListener)
  {
    this.mOneUpListener = paramOneUpListener;
  }

  public void setPlusOne(byte[] paramArrayOfByte)
  {
    DbPlusOneData localDbPlusOneData;
    if (paramArrayOfByte != null)
    {
      localDbPlusOneData = DbPlusOneData.deserialize(paramArrayOfByte);
      this.mPlusOneId = localDbPlusOneData.getId();
      this.mPlusOneByMe = localDbPlusOneData.isPlusOnedByMe();
    }
    for (this.mPlusOneCount = localDbPlusOneData.getCount(); ; this.mPlusOneCount = 0)
    {
      this.mContentDescriptionDirty = true;
      return;
      this.mPlusOneId = null;
      this.mPlusOneByMe = false;
    }
  }

  public final void unbindResources()
  {
    if (this.mAuthorImage != null)
      this.mAuthorImage.unbindResources();
  }

  private final class SetPressedRunnable
    implements Runnable
  {
    private SetPressedRunnable()
    {
    }

    public final void run()
    {
      StreamOneUpCommentView.access$002(StreamOneUpCommentView.this, true);
      StreamOneUpCommentView.this.invalidate();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.StreamOneUpCommentView
 * JD-Core Version:    0.6.2
 */