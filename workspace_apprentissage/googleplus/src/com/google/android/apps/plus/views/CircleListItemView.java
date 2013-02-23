package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.TextView;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.styleable;
import com.google.android.apps.plus.content.AvatarRequest;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.service.ImageCache.ImageConsumer;
import com.google.android.apps.plus.service.ImageCache.OnAvatarChangeListener;
import com.google.android.apps.plus.util.SpannableUtils;
import java.util.ArrayList;

public class CircleListItemView extends CheckableListItemView
  implements ImageCache.OnAvatarChangeListener
{
  private static final SparseArray<Drawable> sCircleTypeIcons = new SparseArray();
  private static Bitmap sDefaultAvatarBitmap;
  private static Drawable sDefaultCircleDrawable;
  private final ImageCache mAvatarCache;
  private int mAvatarCount;
  private final ArrayList<AvatarHolder> mAvatarHolders = new ArrayList();
  private final int mAvatarSize;
  private final int mAvatarSpacing;
  private int mAvatarStripLeft;
  private int mAvatarStripTop;
  private boolean mAvatarStripVisible;
  private final Rect mCircleIconBounds;
  private Drawable mCircleIconDrawable;
  private final int mCircleIconSizeLarge;
  private final int mCircleIconSizeSmall;
  private String mCircleId;
  private String mCircleName;
  private int mCircleType;
  private final TextView mCountTextView;
  private final String[] mGaiaIds = new String[16];
  private final int mGapBetweenCountAndCheckBox;
  private final int mGapBetweenIconAndText;
  private final int mGapBetweenNameAndCount;
  private String mHighlightedText;
  private int mMemberCount;
  private boolean mMemberCountShown;
  private boolean mMemberCountVisible = true;
  private final SpannableStringBuilder mNameTextBuilder = new SpannableStringBuilder();
  private final TextView mNameTextView;
  private final int mPaddingBottom;
  private final int mPaddingLeft;
  private final int mPaddingRight;
  private final int mPaddingTop;
  private final Paint mPhotoPaint;
  private final int mPreferredHeight;
  private final Rect mSourceRect = new Rect();
  private final Rect mTargetRect = new Rect();
  private int mVisibleAvatarCount;

  public CircleListItemView(Context paramContext)
  {
    this(paramContext, null);
  }

  public CircleListItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mAvatarCache = ImageCache.getInstance(paramContext);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.CircleListItemView);
    this.mPreferredHeight = localTypedArray.getDimensionPixelSize(0, 0);
    this.mPaddingTop = localTypedArray.getDimensionPixelOffset(1, 0);
    this.mPaddingBottom = localTypedArray.getDimensionPixelOffset(2, 0);
    this.mPaddingLeft = localTypedArray.getDimensionPixelOffset(3, 0);
    this.mPaddingRight = localTypedArray.getDimensionPixelOffset(4, 0);
    this.mAvatarSize = localTypedArray.getDimensionPixelSize(8, 0);
    this.mAvatarSpacing = localTypedArray.getDimensionPixelSize(9, 0);
    float f = localTypedArray.getFloat(6, 0.0F);
    boolean bool = localTypedArray.getBoolean(7, false);
    this.mGapBetweenNameAndCount = localTypedArray.getDimensionPixelOffset(12, 0);
    this.mGapBetweenIconAndText = localTypedArray.getDimensionPixelOffset(5, 0);
    this.mCircleIconSizeSmall = localTypedArray.getDimensionPixelSize(10, 0);
    this.mCircleIconSizeLarge = localTypedArray.getDimensionPixelSize(11, 0);
    int i = localTypedArray.getColor(14, 0);
    this.mGapBetweenCountAndCheckBox = localTypedArray.getDimensionPixelOffset(13, 0);
    localTypedArray.recycle();
    this.mNameTextView = new TextView(paramContext);
    this.mNameTextView.setSingleLine(true);
    this.mNameTextView.setEllipsize(TextUtils.TruncateAt.END);
    this.mNameTextView.setTextAppearance(paramContext, 16973892);
    this.mNameTextView.setTextSize(f);
    if (bool)
      this.mNameTextView.setTypeface(this.mNameTextView.getTypeface(), 1);
    this.mNameTextView.setGravity(16);
    this.mNameTextView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    addView(this.mNameTextView);
    this.mCountTextView = new TextView(paramContext);
    this.mCountTextView.setSingleLine(true);
    this.mCountTextView.setEllipsize(TextUtils.TruncateAt.END);
    this.mCountTextView.setTextAppearance(paramContext, 16973892);
    this.mCountTextView.setTextSize(f);
    this.mCountTextView.setTextColor(i);
    this.mCountTextView.setGravity(16);
    this.mCountTextView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    addView(this.mCountTextView);
    this.mPhotoPaint = new Paint(2);
    this.mCircleIconBounds = new Rect();
    if (sDefaultAvatarBitmap == null)
    {
      Resources localResources = paramContext.getApplicationContext().getResources();
      sDefaultAvatarBitmap = EsAvatarData.getTinyDefaultAvatar(getContext());
      sDefaultCircleDrawable = localResources.getDrawable(R.drawable.list_public);
    }
    this.mCircleIconDrawable = sDefaultCircleDrawable;
  }

  public void dispatchDraw(Canvas paramCanvas)
  {
    if (this.mCircleType != -3)
    {
      this.mCircleIconDrawable.setBounds(this.mCircleIconBounds);
      this.mCircleIconDrawable.draw(paramCanvas);
    }
    if ((this.mAvatarStripVisible) && (this.mMemberCountShown) && (this.mMemberCount != 0));
    for (int i = 1; i != 0; i = 0)
    {
      int j = this.mAvatarStripLeft;
      for (int k = 0; k < this.mVisibleAvatarCount; k++)
      {
        this.mTargetRect.left = j;
        this.mTargetRect.top = this.mAvatarStripTop;
        this.mTargetRect.right = (j + this.mAvatarSize);
        this.mTargetRect.bottom = (this.mAvatarStripTop + this.mAvatarSize);
        AvatarHolder localAvatarHolder = (AvatarHolder)this.mAvatarHolders.get(k);
        localAvatarHolder.refreshIfNecessary();
        Bitmap localBitmap = localAvatarHolder.getBitmap();
        if (localBitmap == null)
          localBitmap = sDefaultAvatarBitmap;
        if ((localAvatarHolder.isAvatarVisible()) && (localBitmap != null))
        {
          this.mSourceRect.right = localBitmap.getWidth();
          this.mSourceRect.bottom = localBitmap.getHeight();
          paramCanvas.drawBitmap(localBitmap, this.mSourceRect, this.mTargetRect, this.mPhotoPaint);
        }
        j += this.mAvatarSize + this.mAvatarSpacing;
      }
    }
    super.dispatchDraw(paramCanvas);
  }

  public final String getCircleId()
  {
    return this.mCircleId;
  }

  public final String getCircleName()
  {
    return this.mCircleName;
  }

  public final int getCircleType()
  {
    return this.mCircleType;
  }

  public final int getMemberCount()
  {
    return this.mMemberCount;
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    ImageCache.registerAvatarChangeListener(this);
  }

  public void onAvatarChanged(String paramString)
  {
    for (int i = 0; ; i++)
      if (i < this.mAvatarHolders.size())
      {
        AvatarHolder localAvatarHolder = (AvatarHolder)this.mAvatarHolders.get(i);
        if (String.valueOf(localAvatarHolder.mGaiaId).equals(paramString))
        {
          localAvatarHolder.reloadAvatar();
          invalidate();
        }
      }
      else
      {
        return;
      }
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    ImageCache.unregisterAvatarChangeListener(this);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = this.mPaddingLeft;
    int j = this.mPaddingTop;
    int k = paramInt4 - paramInt2;
    int m = this.mNameTextView.getMeasuredHeight();
    int n = m;
    if (this.mMemberCountShown)
      n = Math.max(m, this.mCountTextView.getMeasuredHeight());
    int i1;
    int i2;
    label89: int i3;
    int i4;
    label136: int i9;
    int i10;
    if (this.mAvatarStripVisible)
    {
      i1 = this.mCircleIconSizeSmall;
      if ((!this.mAvatarStripVisible) || (!this.mMemberCountShown) || (this.mMemberCount == 0))
        break label533;
      i2 = 1;
      if (i2 == 0)
        break label539;
      int i23 = Math.max(n, i1);
      i3 = j + (i23 - i1) / 2;
      n = Math.max(n, i23);
      i4 = j + (i23 - n) / 2;
      int i5 = this.mCircleIconDrawable.getIntrinsicWidth();
      int i6 = this.mCircleIconDrawable.getIntrinsicHeight();
      int i7 = i3 + (i1 - i6) / 2;
      int i8 = i + (i1 - i5) / 2;
      this.mCircleIconBounds.set(i8, i7, i8 + i5, i7 + i6);
      i9 = i + (i1 + this.mGapBetweenIconAndText);
      i10 = paramInt3 - this.mPaddingRight;
      if (this.mCheckBoxVisible)
      {
        int i18 = this.mCheckBox.getMeasuredWidth();
        int i19 = this.mCheckBox.getMeasuredHeight();
        int i20 = (k - i19) / 2;
        CheckBox localCheckBox = this.mCheckBox;
        int i21 = i10 - i18;
        int i22 = i20 + i19;
        localCheckBox.layout(i21, i20, i10, i22);
        i10 -= i18 + this.mGapBetweenCountAndCheckBox;
      }
      if (!this.mMemberCountShown)
        break label560;
    }
    label533: label539: label560: for (int i11 = this.mCountTextView.getMeasuredWidth(); ; i11 = 0)
    {
      int i12 = Math.min(this.mNameTextView.getMeasuredWidth(), i10 - i9 - i11 - this.mGapBetweenNameAndCount);
      TextView localTextView1 = this.mNameTextView;
      int i13 = i9 + i12;
      int i14 = i4 + n;
      localTextView1.layout(i9, i4, i13, i14);
      if (this.mMemberCountShown)
      {
        int i16 = i9 + i12 + this.mGapBetweenNameAndCount;
        TextView localTextView2 = this.mCountTextView;
        int i17 = i4 + n;
        localTextView2.layout(i16, i4, i10, i17);
      }
      if (i2 == 0)
        return;
      this.mAvatarStripTop = (k - this.mPaddingBottom - this.mAvatarSize);
      this.mAvatarStripLeft = i9;
      this.mVisibleAvatarCount = ((paramInt3 - paramInt1 - i9 - this.mPaddingRight + this.mAvatarSpacing) / (this.mAvatarSize + this.mAvatarSpacing));
      this.mVisibleAvatarCount = Math.min(this.mVisibleAvatarCount, this.mAvatarCount);
      for (int i15 = 0; i15 < this.mVisibleAvatarCount; i15++)
        ((AvatarHolder)this.mAvatarHolders.get(i15)).loadAvatar();
      i1 = this.mCircleIconSizeLarge;
      break;
      i2 = 0;
      break label89;
      i3 = (k - i1) / 2;
      i4 = (k - n) / 2;
      break label136;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    if (this.mAvatarStripVisible);
    for (int j = this.mCircleIconSizeSmall; ; j = this.mCircleIconSizeLarge)
    {
      int k = i - this.mPaddingLeft - this.mPaddingRight - j - this.mGapBetweenIconAndText;
      boolean bool1 = this.mCheckBoxVisible;
      int m = 0;
      if (bool1)
      {
        this.mCheckBox.measure(0, paramInt2);
        m = Math.max(0, this.mCheckBox.getMeasuredHeight());
        k -= this.mCheckBox.getMeasuredWidth() + this.mGapBetweenCountAndCheckBox;
      }
      this.mNameTextView.measure(0, 0);
      int n = this.mNameTextView.getMeasuredHeight();
      if (this.mMemberCountShown)
      {
        this.mCountTextView.measure(0, 0);
        n = Math.max(n, this.mCountTextView.getMeasuredHeight());
        k -= this.mCountTextView.getMeasuredWidth() + this.mGapBetweenNameAndCount;
      }
      int i1 = Math.max(Math.max(m, n), j);
      boolean bool2 = this.mAvatarStripVisible;
      int i2 = 0;
      if (bool2)
      {
        boolean bool3 = this.mMemberCountShown;
        i2 = 0;
        if (bool3)
        {
          int i4 = this.mMemberCount;
          i2 = 0;
          if (i4 != 0)
            i2 = 1;
        }
      }
      if (i2 != 0)
        i1 = Math.max(i1, n + this.mAvatarSize);
      int i3 = Math.min(this.mNameTextView.getMeasuredWidth(), k);
      this.mNameTextView.measure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mNameTextView.getMeasuredHeight(), 1073741824));
      setMeasuredDimension(i, Math.max(i1 + (this.mPaddingTop + this.mPaddingBottom), this.mPreferredHeight));
      return;
    }
  }

  public void setAvatarStripVisible(boolean paramBoolean)
  {
    this.mAvatarStripVisible = paramBoolean;
  }

  public void setCircle(String paramString1, int paramInt1, String paramString2, int paramInt2, boolean paramBoolean)
  {
    int i = 1;
    this.mCircleId = paramString1;
    this.mCircleType = paramInt1;
    this.mMemberCount = paramInt2;
    label137: int k;
    if ((this.mMemberCountVisible) && ((paramInt1 == i) || (paramInt1 == 5) || (paramInt1 == 10)))
    {
      this.mMemberCountShown = i;
      int j = paramInt1;
      if ((paramInt1 == -1) && ("v.whatshot".equals(paramString1)))
        j = -2;
      this.mCircleIconDrawable = ((Drawable)sCircleTypeIcons.get(j));
      switch (j)
      {
      default:
        this.mCircleName = paramString2;
        SpannableUtils.setTextWithHighlight$5cdafd0b(this.mNameTextView, this.mCircleName, this.mNameTextBuilder, this.mHighlightedText, sBoldSpan, sColorSpan);
        if (this.mCircleIconDrawable == null);
        switch (j)
        {
        default:
          k = R.drawable.list_circle;
          label241: this.mCircleIconDrawable = getContext().getApplicationContext().getResources().getDrawable(k);
          sCircleTypeIcons.put(j, this.mCircleIconDrawable);
          if (this.mMemberCountShown)
          {
            this.mCountTextView.setText("(" + paramInt2 + ")");
            this.mCountTextView.setVisibility(0);
          }
          break;
        case 9:
        case 8:
        case 7:
        case 10:
        case 5:
        case 101:
        case -2:
        }
        break;
      case 9:
      case 7:
      case 5:
      case -2:
      }
    }
    while (true)
    {
      return;
      i = 0;
      break;
      this.mCircleName = getResources().getString(R.string.acl_public);
      break label137;
      this.mCircleName = getResources().getString(R.string.acl_extended_network);
      break label137;
      this.mCircleName = getResources().getString(R.string.acl_your_circles);
      break label137;
      this.mCircleName = getResources().getString(R.string.stream_whats_hot);
      break label137;
      if (paramBoolean);
      for (k = R.drawable.list_public_red; ; k = R.drawable.list_public)
        break;
      k = R.drawable.list_domain;
      break label241;
      if (paramBoolean);
      for (k = R.drawable.list_extended_red; ; k = R.drawable.list_extended)
        break;
      k = R.drawable.list_circle_blocked;
      break label241;
      k = R.drawable.ic_circles_active;
      break label241;
      k = R.drawable.ic_private;
      break label241;
      k = R.drawable.list_whats_hot;
      break label241;
      this.mCountTextView.setVisibility(8);
    }
  }

  public void setHighlightedText(String paramString)
  {
    if (paramString == null);
    for (this.mHighlightedText = null; ; this.mHighlightedText = paramString.toUpperCase())
      return;
  }

  public void setMemberCountVisible(boolean paramBoolean)
  {
    this.mMemberCountVisible = paramBoolean;
  }

  public void setPackedMemberIds(String paramString)
  {
    this.mAvatarCount = Math.min(16, this.mMemberCount);
    int i = 0;
    if (paramString != null)
    {
      int i3;
      for (int i2 = 0; (i < this.mAvatarCount) && (i2 < paramString.length()); i2 = i3 + 1)
      {
        i3 = paramString.indexOf('|', i2);
        if (i3 == -1)
          i3 = paramString.length();
        String str3 = EsPeopleData.extractGaiaId(paramString.substring(i2, i3));
        if (str3 != null)
        {
          String[] arrayOfString = this.mGaiaIds;
          int i4 = i + 1;
          arrayOfString[i] = str3;
          i = i4;
        }
      }
    }
    while (this.mAvatarHolders.size() > this.mAvatarCount)
      this.mAvatarHolders.remove(-1 + this.mAvatarHolders.size());
    for (int j = 0; j < this.mAvatarCount; j++)
    {
      if (this.mAvatarHolders.size() <= j)
        this.mAvatarHolders.add(new AvatarHolder((byte)0));
      ((AvatarHolder)this.mAvatarHolders.get(j)).setAvatarVisible(false);
    }
    int k = 0;
    if (k < i)
    {
      String str2 = this.mGaiaIds[k];
      for (int i1 = 0; ; i1++)
        if (i1 < this.mAvatarCount)
        {
          AvatarHolder localAvatarHolder2 = (AvatarHolder)this.mAvatarHolders.get(i1);
          if (TextUtils.equals(localAvatarHolder2.getGaiaId(), str2))
          {
            localAvatarHolder2.setAvatarVisible(true);
            this.mGaiaIds[k] = null;
          }
        }
        else
        {
          k++;
          break;
        }
    }
    int m = 0;
    if (m < i)
    {
      String str1 = this.mGaiaIds[m];
      if (str1 != null);
      for (int n = 0; ; n++)
        if (n < this.mAvatarCount)
        {
          AvatarHolder localAvatarHolder1 = (AvatarHolder)this.mAvatarHolders.get(n);
          if (!localAvatarHolder1.isAvatarVisible())
          {
            localAvatarHolder1.setGaiaId(str1);
            localAvatarHolder1.setAvatarVisible(true);
          }
        }
        else
        {
          m++;
          break;
        }
    }
  }

  public final void updateContentDescription()
  {
    Resources localResources = getResources();
    int i = R.plurals.circle_entry_content_description;
    int j = this.mMemberCount;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.mCircleName;
    arrayOfObject[1] = Integer.valueOf(this.mMemberCount);
    setContentDescription(localResources.getQuantityString(i, j, arrayOfObject));
  }

  private final class AvatarHolder
    implements ImageCache.ImageConsumer
  {
    private boolean mAvatarInvalidated;
    private Bitmap mBitmap;
    private String mGaiaId;
    private AvatarRequest mRequest = new AvatarRequest();
    private boolean mVisible;

    private AvatarHolder()
    {
    }

    public final Bitmap getBitmap()
    {
      return this.mBitmap;
    }

    public final String getGaiaId()
    {
      return this.mGaiaId;
    }

    public final boolean isAvatarVisible()
    {
      return this.mVisible;
    }

    public final void loadAvatar()
    {
      if (this.mBitmap != null);
      while (true)
      {
        return;
        if ((this.mGaiaId == null) || (!this.mVisible))
        {
          this.mBitmap = null;
          CircleListItemView.this.invalidate();
        }
        else
        {
          CircleListItemView.this.mAvatarCache.loadImage(this, this.mRequest);
        }
      }
    }

    public final void refreshIfNecessary()
    {
      if ((this.mAvatarInvalidated) && (this.mVisible) && (this.mRequest != null))
      {
        this.mAvatarInvalidated = false;
        CircleListItemView.this.mAvatarCache.refreshImage(this, this.mRequest);
      }
    }

    public final void reloadAvatar()
    {
      if ((this.mGaiaId != null) && (this.mVisible))
        this.mAvatarInvalidated = true;
    }

    public final void setAvatarVisible(boolean paramBoolean)
    {
      this.mVisible = paramBoolean;
    }

    public final void setBitmap(Bitmap paramBitmap, boolean paramBoolean)
    {
      if (this.mVisible)
      {
        this.mBitmap = paramBitmap;
        CircleListItemView.this.invalidate();
      }
    }

    public final void setGaiaId(String paramString)
    {
      if (!TextUtils.equals(this.mGaiaId, paramString))
      {
        this.mGaiaId = paramString;
        this.mRequest = new AvatarRequest(paramString, 0);
        this.mBitmap = null;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.CircleListItemView
 * JD-Core Version:    0.6.2
 */