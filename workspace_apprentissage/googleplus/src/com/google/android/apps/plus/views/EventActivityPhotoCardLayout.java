package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.util.Dates;
import com.google.android.apps.plus.util.PrimitiveUtils;
import com.google.api.services.plusi.model.DataImage;
import com.google.api.services.plusi.model.DataPhoto;
import com.google.api.services.plusi.model.DataPhotoJson;
import com.google.api.services.plusi.model.DataUser;
import com.google.api.services.plusi.model.DataVideo;

public class EventActivityPhotoCardLayout extends CardViewLayout
  implements View.OnClickListener
{
  private static int sAvatarMarginBottom;
  private static int sAvatarMarginLeft;
  private static int sAvatarSize;
  private static int sImageMarginBottom;
  private static boolean sInitialized;
  private static int sTextMarginLeft;
  private static int sTextMarginRight;
  private AvatarView mAvatarView;
  private DataPhoto mDataPhoto;
  private String mGaiaId;
  private ImageResourceView mImageResourceView;
  private EventActionListener mListener;
  private MediaRef mMediaRef;
  private boolean mPending = false;
  private TextView mPendingTextView;
  private byte[] mPhotoData;
  private CardTitleDescriptionView mTextDescriptionView;

  public EventActivityPhotoCardLayout(Context paramContext)
  {
    super(paramContext);
  }

  public EventActivityPhotoCardLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public EventActivityPhotoCardLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public final void bind(String paramString1, String paramString2, long paramLong, String paramString3, byte[] paramArrayOfByte, EventActionListener paramEventActionListener, String paramString4)
  {
    this.mPhotoData = paramArrayOfByte;
    this.mDataPhoto = ((DataPhoto)DataPhotoJson.getInstance().fromByteArray(this.mPhotoData));
    this.mPending = false;
    MediaRef.MediaType localMediaType;
    int i;
    label97: TextView localTextView;
    int j;
    if (PrimitiveUtils.safeBoolean(this.mDataPhoto.isPanorama))
    {
      localMediaType = MediaRef.MediaType.PANORAMA;
      if (!this.mPending)
      {
        this.mMediaRef = new MediaRef(paramString3, localMediaType);
        this.mImageResourceView.setMediaRef(this.mMediaRef, true);
      }
      ImageResourceView localImageResourceView = this.mImageResourceView;
      if (!this.mPending)
        break label263;
      i = 8;
      localImageResourceView.setVisibility(i);
      localTextView = this.mPendingTextView;
      boolean bool = this.mPending;
      j = 0;
      if (!bool)
        break label269;
    }
    while (true)
    {
      localTextView.setVisibility(j);
      this.mTextDescriptionView.setText(paramString1, Dates.getRelativeTimeSpanString(getContext(), paramLong), null, true);
      if (!TextUtils.isEmpty(paramString2))
        this.mAvatarView.setGaiaId(paramString2);
      this.mGaiaId = paramString2;
      this.mListener = paramEventActionListener;
      if ((this.mPending) && (this.mListener != null))
        this.mListener.onPhotoUpdateNeeded(this.mDataPhoto.owner.id, this.mDataPhoto.id, paramString4);
      return;
      if (this.mDataPhoto.video != null)
      {
        localMediaType = MediaRef.MediaType.VIDEO;
        this.mPending = TextUtils.equals(this.mDataPhoto.video.status, "PENDING");
        break;
      }
      localMediaType = MediaRef.MediaType.IMAGE;
      break;
      label263: i = 0;
      break label97;
      label269: j = 8;
    }
  }

  public final void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super.init(paramContext, paramAttributeSet, paramInt);
    if (!sInitialized)
    {
      Resources localResources2 = paramContext.getResources();
      sTextMarginLeft = localResources2.getDimensionPixelSize(R.dimen.event_card_activity_text_margin_left);
      sTextMarginRight = localResources2.getDimensionPixelSize(R.dimen.event_card_activity_text_margin_right);
      sImageMarginBottom = localResources2.getDimensionPixelSize(R.dimen.event_card_activity_photo_margin_bottom);
      sAvatarSize = localResources2.getDimensionPixelSize(R.dimen.event_card_activity_avatar_size);
      sAvatarMarginLeft = localResources2.getDimensionPixelSize(R.dimen.event_card_activity_avatar_margin_left);
      sAvatarMarginBottom = localResources2.getDimensionPixelSize(R.dimen.event_card_activity_photo_avatar_margin_bottom);
      sInitialized = true;
    }
    this.mImageResourceView = new ImageResourceView(paramContext, paramAttributeSet, paramInt);
    this.mImageResourceView.setScaleMode(1);
    this.mImageResourceView.setSizeCategory(3);
    this.mImageResourceView.setOnClickListener(this);
    addView(this.mImageResourceView);
    this.mTextDescriptionView = new CardTitleDescriptionView(paramContext, paramAttributeSet, paramInt);
    addView(this.mTextDescriptionView);
    Resources localResources1 = paramContext.getResources();
    this.mPendingTextView = new TextView(paramContext, paramAttributeSet, paramInt);
    this.mPendingTextView.setBackgroundColor(localResources1.getColor(R.color.event_card_photo_pending_background_color));
    this.mPendingTextView.setTextColor(localResources1.getColor(R.color.event_card_photo_pending_text_color));
    this.mPendingTextView.setGravity(17);
    this.mPendingTextView.setText(localResources1.getString(R.string.card_event_photo_missing_video));
    addView(this.mPendingTextView);
    this.mAvatarView = new AvatarView(paramContext, paramAttributeSet, paramInt);
    this.mAvatarView.setOnClickListener(this);
    this.mAvatarView.setRounded(true);
    this.mAvatarView.setAvatarSize(2);
    addView(this.mAvatarView);
  }

  protected void measureChildren(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    int k = j - sImageMarginBottom;
    int m = k + 0;
    if (this.mPending)
    {
      setCorner(this.mPendingTextView, 0, 0);
      this.mPendingTextView.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(k, 1073741824));
    }
    while (true)
    {
      int n = 0 + sAvatarMarginLeft;
      int i1 = n + sAvatarSize;
      int i2 = j + 0 - sAvatarMarginBottom - sAvatarSize;
      setCorner(this.mAvatarView, n, i2);
      this.mAvatarView.measure(View.MeasureSpec.makeMeasureSpec(sAvatarSize, 1073741824), View.MeasureSpec.makeMeasureSpec(sAvatarSize, 1073741824));
      int i3 = i1 + sTextMarginLeft;
      int i4 = i - i3 - sTextMarginRight;
      int i5 = j - k;
      this.mTextDescriptionView.measure(View.MeasureSpec.makeMeasureSpec(i4, 1073741824), View.MeasureSpec.makeMeasureSpec(i5, -2147483648));
      int i6 = m + Math.max(0, (i5 - this.mTextDescriptionView.getMeasuredHeight()) / 2);
      setCorner(this.mTextDescriptionView, i3, i6);
      return;
      setCorner(this.mImageResourceView, 0, 0);
      this.mImageResourceView.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(k, 1073741824));
    }
  }

  public void onClick(View paramView)
  {
    if ((paramView == this.mAvatarView) && (this.mListener != null))
      this.mListener.onAvatarClicked(((AvatarView)paramView).getGaiaId());
    while (true)
    {
      return;
      if ((paramView == this.mImageResourceView) && (this.mListener != null) && (this.mPhotoData != null))
        this.mListener.onPhotoClicked(this.mDataPhoto.id, this.mDataPhoto.original.url, this.mGaiaId);
    }
  }

  public void onRecycle()
  {
    super.onRecycle();
    this.mImageResourceView.onRecycle();
    this.mListener = null;
    this.mGaiaId = null;
    this.mPhotoData = null;
    this.mTextDescriptionView.clear();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventActivityPhotoCardLayout
 * JD-Core Version:    0.6.2
 */