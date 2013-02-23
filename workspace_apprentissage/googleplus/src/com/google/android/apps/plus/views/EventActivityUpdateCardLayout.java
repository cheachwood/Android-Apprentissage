package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.util.Dates;

public class EventActivityUpdateCardLayout extends CardViewLayout
  implements View.OnClickListener
{
  private static int sAvatarMarginLeft;
  private static int sAvatarMarginTop;
  private static int sAvatarSize;
  private static int sDescriptionMarginBottom;
  private static int sDescriptionMarginLeft;
  private static int sDescriptionMarginRight;
  private static float sDescriptionTopAvatarHeightPercentage;
  private static boolean sInitialized;
  private AvatarView mAvatarView;
  private EventActionListener mListener;
  private CardTitleDescriptionView mTextDescriptionView;
  private EventUpdate mUpdate;

  public EventActivityUpdateCardLayout(Context paramContext)
  {
    super(paramContext);
  }

  public EventActivityUpdateCardLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public EventActivityUpdateCardLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public final void bind(EventUpdate paramEventUpdate, EventActionListener paramEventActionListener, boolean paramBoolean)
  {
    if (paramEventUpdate == null);
    while (true)
    {
      return;
      this.mUpdate = paramEventUpdate;
      this.mTextDescriptionView.setText(this.mUpdate.ownerName, Dates.getRelativeTimeSpanString(getContext(), this.mUpdate.timestamp), this.mUpdate.comment, paramBoolean);
      if (!TextUtils.isEmpty(this.mUpdate.gaiaId))
        this.mAvatarView.setGaiaId(this.mUpdate.gaiaId);
      this.mListener = paramEventActionListener;
      this.mTextDescriptionView.setListener(this.mListener);
    }
  }

  protected final void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super.init(paramContext, paramAttributeSet, paramInt);
    if (!sInitialized)
    {
      Resources localResources = paramContext.getResources();
      sDescriptionMarginLeft = localResources.getDimensionPixelSize(R.dimen.event_card_activity_text_margin_left);
      sDescriptionMarginRight = localResources.getDimensionPixelSize(R.dimen.event_card_activity_text_margin_right);
      sDescriptionMarginBottom = localResources.getDimensionPixelSize(R.dimen.event_card_activity_padding_bottom);
      sAvatarMarginLeft = localResources.getDimensionPixelSize(R.dimen.event_card_activity_avatar_margin_left);
      sAvatarMarginTop = localResources.getDimensionPixelSize(R.dimen.event_card_activity_avatar_magin_top);
      sDescriptionTopAvatarHeightPercentage = localResources.getDimension(R.dimen.event_card_activity_text_top_avatar_percentage);
      sAvatarSize = localResources.getDimensionPixelSize(R.dimen.event_card_activity_avatar_size);
      sInitialized = true;
    }
    addPadding(sAvatarMarginLeft, sAvatarMarginTop, sDescriptionMarginRight, sDescriptionMarginBottom);
    this.mAvatarView = new AvatarView(paramContext, paramAttributeSet, paramInt);
    this.mAvatarView.setRounded(true);
    this.mAvatarView.setOnClickListener(this);
    addView(this.mAvatarView);
    this.mTextDescriptionView = new CardTitleDescriptionView(paramContext, paramAttributeSet, paramInt);
    addView(this.mTextDescriptionView);
    setOnClickListener(this);
  }

  protected void measureChildren(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    int k = i + 0;
    int m = j + 0;
    this.mAvatarView.measure(View.MeasureSpec.makeMeasureSpec(sAvatarSize, 1073741824), View.MeasureSpec.makeMeasureSpec(sAvatarSize, 1073741824));
    setCorner(this.mAvatarView, 0, 0);
    int n = 0 + Math.round(sDescriptionTopAvatarHeightPercentage * this.mAvatarView.getMeasuredHeight());
    int i1 = 0 + this.mAvatarView.getMeasuredWidth() + sDescriptionMarginLeft;
    int i2 = k - i1;
    int i3 = m - n;
    int i4;
    int i6;
    if (View.MeasureSpec.getMode(paramInt2) == 0)
    {
      i4 = 1;
      CardTitleDescriptionView localCardTitleDescriptionView = this.mTextDescriptionView;
      int i5 = View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
      if (i4 == 0)
        break label181;
      i6 = 0;
      label135: localCardTitleDescriptionView.measure(i5, View.MeasureSpec.makeMeasureSpec(i3, i6));
      setCorner(this.mTextDescriptionView, i1, n);
      if (i4 != 0)
        break label188;
    }
    label181: label188: for (boolean bool = true; ; bool = false)
    {
      setClickable(bool);
      return;
      i4 = 0;
      break;
      i6 = -2147483648;
      break label135;
    }
  }

  public void onClick(View paramView)
  {
    if (this.mListener != null)
    {
      if (!(paramView instanceof AvatarView))
        break label31;
      this.mListener.onAvatarClicked(((AvatarView)paramView).getGaiaId());
    }
    while (true)
    {
      return;
      label31: this.mListener.onUpdateCardClicked(this.mUpdate);
    }
  }

  public void onRecycle()
  {
    super.onRecycle();
    this.mListener = null;
    this.mTextDescriptionView.setText(null, null, null, false);
    this.mTextDescriptionView.setListener(null);
    this.mAvatarView.setGaiaId(null);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventActivityUpdateCardLayout
 * JD-Core Version:    0.6.2
 */