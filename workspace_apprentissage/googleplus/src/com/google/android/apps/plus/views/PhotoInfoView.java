package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Layout.Alignment;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.TextPaintUtils;

public class PhotoInfoView extends View
  implements ItemClickListener
{
  private static TextPaint sAlbumPaint;
  private static int sAlbumRightMargin;
  private static final Rect sAvatarRect = new Rect();
  private static int sAvatarRightMargin;
  private static Bitmap sCommentBitmap;
  private static int sCommentCountLeftMargin;
  private static TextPaint sCommentCountPaint;
  private static int sCommentCountTextWidth;
  private static TextPaint sDatePaint;
  private static int sDateRightMargin;
  private static Bitmap sDefaultAvatarBitmap;
  private static int sInfoAvatarHeight;
  private static int sInfoAvatarWidth;
  private static Drawable sInfoBackground;
  private static int sInfoRightPadding;
  private static boolean sInitialized;
  private static TextPaint sNamePaint;
  private static Bitmap sPlusOneBitmap;
  private static int sPlusOneBottomMargin;
  private static int sPlusOneCountLeftMargin;
  private static TextPaint sPlusOneCountPaint;
  private static int sPlusOneCountTextWidth;
  private static Bitmap sPlusoneByMeBitmap;
  private CharSequence mAlbumContent;
  private String mAlbumId;
  private ClickableStaticLayout mAlbumName;
  private String mAlbumStream;
  private ClickableUserImage mAvatar;
  private ItemClickListener mClickListener;
  private String mCommentText;
  private ClickableItem mCurrentClickableItem;
  private CharSequence mDate;
  private String mEventId;
  private int mFixedHeight = -1;
  private String mOwnerId;
  private CharSequence mOwnerName;
  private String mPlusOneText;

  public PhotoInfoView(Context paramContext)
  {
    super(paramContext);
    initialize();
  }

  public PhotoInfoView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initialize();
  }

  public PhotoInfoView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initialize();
  }

  private int getInfoBoxHeight()
  {
    if (getVisibility() == 8);
    for (int i = 0; ; i = sInfoAvatarHeight)
      return i;
  }

  private void initialize()
  {
    if (!sInitialized)
    {
      sInitialized = true;
      Context localContext = getContext();
      Resources localResources = localContext.getApplicationContext().getResources();
      sDefaultAvatarBitmap = EsAvatarData.getSmallDefaultAvatar(localContext, true);
      sCommentBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_photodetail_comment);
      sPlusOneBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_photodetail_plus);
      sPlusoneByMeBitmap = ImageUtils.decodeResource(localResources, R.drawable.ic_plus_one_by_me);
      sInfoBackground = localResources.getDrawable(R.drawable.photo_view_info_background);
      TextPaint localTextPaint1 = new TextPaint();
      sNamePaint = localTextPaint1;
      localTextPaint1.setAntiAlias(true);
      sNamePaint.setColor(localResources.getColor(R.color.photo_info_name_color));
      sNamePaint.setTextSize(localResources.getDimension(R.dimen.photo_info_name_text_size));
      sNamePaint.setTypeface(Typeface.DEFAULT_BOLD);
      TextPaintUtils.registerTextPaint(sNamePaint, R.dimen.photo_info_name_text_size);
      TextPaint localTextPaint2 = new TextPaint();
      sDatePaint = localTextPaint2;
      localTextPaint2.setAntiAlias(true);
      sDatePaint.setColor(localResources.getColor(R.color.photo_info_date_color));
      sDatePaint.setTextSize(localResources.getDimension(R.dimen.photo_info_date_text_size));
      TextPaintUtils.registerTextPaint(sDatePaint, R.dimen.photo_info_date_text_size);
      TextPaint localTextPaint3 = new TextPaint();
      sAlbumPaint = localTextPaint3;
      localTextPaint3.setAntiAlias(true);
      sAlbumPaint.setColor(localResources.getColor(R.color.photo_info_album_color));
      sAlbumPaint.setTextSize(localResources.getDimension(R.dimen.photo_info_album_text_size));
      sAlbumPaint.linkColor = localResources.getColor(R.color.photo_info_album_color);
      TextPaintUtils.registerTextPaint(sAlbumPaint, R.dimen.photo_info_album_text_size);
      TextPaint localTextPaint4 = new TextPaint();
      sCommentCountPaint = localTextPaint4;
      localTextPaint4.setAntiAlias(true);
      sCommentCountPaint.setColor(localResources.getColor(R.color.photo_info_comment_count_color));
      sCommentCountPaint.setTextSize(localResources.getDimension(R.dimen.photo_info_comment_text_size));
      TextPaintUtils.registerTextPaint(sCommentCountPaint, R.dimen.photo_info_comment_text_size);
      TextPaint localTextPaint5 = new TextPaint();
      sPlusOneCountPaint = localTextPaint5;
      localTextPaint5.setAntiAlias(true);
      sPlusOneCountPaint.setColor(localResources.getColor(R.color.photo_info_plusone_count_color));
      sPlusOneCountPaint.setTextSize(localResources.getDimension(R.dimen.photo_info_plusone_text_size));
      TextPaintUtils.registerTextPaint(sPlusOneCountPaint, R.dimen.photo_info_plusone_text_size);
      int i = (int)localResources.getDimension(R.dimen.photo_info_avatar_height);
      sInfoAvatarHeight = i;
      sInfoAvatarWidth = i;
      sAvatarRect.set(0, 0, sInfoAvatarWidth, sInfoAvatarHeight);
      sInfoRightPadding = (int)localResources.getDimension(R.dimen.photo_info_right_padding);
      sAvatarRightMargin = (int)localResources.getDimension(R.dimen.photo_info_avatar_right_margin);
      sDateRightMargin = (int)localResources.getDimension(R.dimen.photo_info_date_right_margin);
      sAlbumRightMargin = (int)localResources.getDimension(R.dimen.photo_info_album_right_margin);
      sCommentCountLeftMargin = (int)localResources.getDimension(R.dimen.photo_info_comment_count_left_margin);
      sPlusOneCountLeftMargin = (int)localResources.getDimension(R.dimen.photo_info_plusone_count_left_margin);
      sPlusOneBottomMargin = (int)localResources.getDimension(R.dimen.photo_info_plusone_bottom_margin);
      sCommentCountTextWidth = (int)localResources.getDimension(R.dimen.photo_info_comment_count_text_width);
      sPlusOneCountTextWidth = (int)localResources.getDimension(R.dimen.photo_info_plusone_count_text_width);
    }
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
      if ((this.mAvatar != null) && (this.mAvatar.handleEvent(k, m, 0)))
      {
        this.mCurrentClickableItem = this.mAvatar;
        invalidate();
      }
      else if ((this.mAlbumName != null) && (this.mAlbumName.handleEvent(k, m, 0)))
      {
        this.mCurrentClickableItem = this.mAlbumName;
        invalidate();
      }
      else
      {
        i = 0;
        continue;
        this.mCurrentClickableItem = null;
        if (this.mAvatar != null)
          this.mAvatar.handleEvent(k, m, i);
        if (this.mAlbumName != null)
          this.mAlbumName.handleEvent(k, m, i);
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
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = getInfoBoxHeight();
    Bitmap localBitmap;
    int i4;
    int i5;
    CharSequence localCharSequence1;
    int i9;
    String str2;
    String str3;
    String str4;
    StringBuilder localStringBuilder;
    String str5;
    if (i > 0)
    {
      int j = getHeight() - i;
      sInfoBackground.setBounds(0, j, getWidth(), getHeight());
      sInfoBackground.draw(paramCanvas);
      int k = sPlusOneBitmap.getHeight() + sPlusOneBottomMargin + sCommentBitmap.getHeight();
      int m = getWidth() - sInfoRightPadding;
      int n = j + (i - k) / 2;
      if (this.mPlusOneText != null)
      {
        int i12 = m - sPlusOneCountTextWidth;
        paramCanvas.drawText(this.mPlusOneText, i12, n - sPlusOneCountPaint.ascent(), sPlusOneCountPaint);
        int i13 = i12 - (sPlusOneCountLeftMargin + sPlusOneBitmap.getWidth());
        paramCanvas.drawBitmap(sPlusOneBitmap, i13, n, null);
        m = getWidth() - sInfoRightPadding;
        n += sPlusOneBitmap.getHeight() + sPlusOneBottomMargin;
      }
      if (this.mCommentText != null)
      {
        int i11 = m - sCommentCountTextWidth;
        paramCanvas.drawText(this.mCommentText, i11, n - sCommentCountPaint.ascent(), sCommentCountPaint);
        m = i11 - (sCommentCountLeftMargin + sCommentBitmap.getWidth());
        paramCanvas.drawBitmap(sCommentBitmap, m, n, null);
      }
      int i1 = m - sDateRightMargin;
      if ((this.mAvatar == null) || (this.mAvatar.getBitmap() == null))
        break label892;
      localBitmap = this.mAvatar.getBitmap();
      Rect localRect = sAvatarRect;
      paramCanvas.drawBitmap(localBitmap, null, localRect, null);
      if ((this.mAvatar != null) && (this.mAvatar.isClicked()))
        this.mAvatar.drawSelectionRect(paramCanvas);
      int i2 = (int)(sNamePaint.descent() - sNamePaint.ascent());
      int i3 = (int)(sAlbumPaint.descent() - sAlbumPaint.ascent());
      i4 = sInfoAvatarWidth + sAvatarRightMargin;
      i5 = j + (i - (i2 + i3)) / 2;
      if (this.mOwnerName != null)
      {
        int i10 = (int)sNamePaint.measureText(this.mOwnerName, 0, this.mOwnerName.length());
        CharSequence localCharSequence2 = this.mOwnerName;
        if (i4 + i10 > i1)
          localCharSequence2 = TextUtils.ellipsize(this.mOwnerName, sNamePaint, i1 - i4, TextUtils.TruncateAt.END);
        paramCanvas.drawText(localCharSequence2, 0, localCharSequence2.length(), i4, i5 - sNamePaint.ascent(), sNamePaint);
        i5 += i2;
      }
      if (this.mAlbumContent != null)
      {
        int i7 = i1 - i4;
        if (this.mDate != null)
        {
          float f = sDatePaint.measureText(this.mDate, 0, this.mDate.length());
          i7 = (int)(i7 - (f + sAlbumRightMargin));
        }
        int i8 = (int)sAlbumPaint.measureText(this.mAlbumContent, 0, this.mAlbumContent.length());
        localCharSequence1 = this.mAlbumContent;
        if (i8 > i7)
          localCharSequence1 = TextUtils.ellipsize(this.mAlbumContent, sAlbumPaint, i7, TextUtils.TruncateAt.END);
        i9 = Math.min(i8, i7);
        if ((this.mAlbumName == null) || (this.mAlbumName.getWidth() != i9))
        {
          getContext();
          String str1 = this.mEventId;
          str2 = this.mAlbumId;
          str3 = this.mAlbumStream;
          str4 = this.mOwnerId;
          localStringBuilder = new StringBuilder();
          if (str1 == null)
            break label900;
          str5 = "eid=" + Uri.encode(str1);
        }
      }
    }
    while (true)
    {
      localStringBuilder.append("<a href=\"#~loop:svt=album&amp;").append(str5).append("&amp;aname=").append(Uri.encode(localCharSequence1.toString())).append("&amp;oid=").append(str4).append("\">").append(localCharSequence1).append("</a>");
      this.mAlbumName = new ClickableStaticLayout(ClickableStaticLayout.buildStateSpans(localStringBuilder.toString()), sAlbumPaint, i9, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false, this);
      this.mAlbumName.setPosition(i4, i5);
      paramCanvas.translate(i4, i5);
      this.mAlbumName.draw(paramCanvas);
      paramCanvas.translate(-i4, -i5);
      i4 += this.mAlbumName.getWidth() + sAlbumRightMargin;
      if (this.mDate != null)
      {
        int i6 = (int)((int)(i5 - sAlbumPaint.ascent()) + sDatePaint.ascent());
        paramCanvas.drawText(this.mDate, 0, this.mDate.length(), i4, i6 - sDatePaint.ascent(), sDatePaint);
      }
      return;
      label892: localBitmap = sDefaultAvatarBitmap;
      break;
      label900: if (str3 != null)
        str5 = "sid=" + Uri.encode(str3);
      else
        str5 = "aid=" + str2;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.mFixedHeight = getInfoBoxHeight();
    if (this.mFixedHeight > 0)
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

  public final void onSpanClick(URLSpan paramURLSpan)
  {
    if (this.mClickListener != null)
      this.mClickListener.onSpanClick(paramURLSpan);
  }

  public final void onUserImageClick(String paramString1, String paramString2)
  {
    if (this.mClickListener != null)
      this.mClickListener.onUserImageClick(paramString1, paramString2);
  }

  public void setAlbum(String paramString1, String paramString2, CharSequence paramCharSequence)
  {
    this.mAlbumContent = paramCharSequence;
    this.mAlbumId = paramString1;
    this.mAlbumStream = paramString2;
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

  public void setEvent(String paramString)
  {
    this.mEventId = paramString;
  }

  public void setExternalClickListener(ItemClickListener paramItemClickListener)
  {
    this.mClickListener = paramItemClickListener;
  }

  public void setOwner(String paramString, CharSequence paramCharSequence)
  {
    this.mOwnerId = paramString;
    this.mOwnerName = paramCharSequence;
    if (this.mOwnerName == null);
    for (String str = null; ; str = this.mOwnerName.toString())
    {
      this.mAvatar = new ClickableUserImage(this, this.mOwnerId, null, str, this);
      this.mAvatar.setRect(0, 0, sInfoAvatarWidth, sInfoAvatarHeight);
      return;
    }
  }

  public void setPhotoDate(CharSequence paramCharSequence)
  {
    this.mDate = paramCharSequence;
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
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PhotoInfoView
 * JD-Core Version:    0.6.2
 */