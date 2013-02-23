package com.google.android.apps.plus.phone;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.util.PrimitiveUtils;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.ColumnGridView.LayoutParams;
import com.google.android.apps.plus.views.ItemClickListener;
import com.google.android.apps.plus.views.SquareLandingView;
import com.google.android.apps.plus.views.SquareLandingView.OnClickListener;
import com.google.android.apps.plus.views.StreamCardView;
import com.google.android.apps.plus.views.StreamCardView.StreamMediaClickListener;
import com.google.android.apps.plus.views.StreamCardView.StreamPlusBarClickListener;

public final class SquareStreamAdapter extends StreamAdapter
{
  private boolean mCanJoin;
  private boolean mCanRequestToJoin;
  private boolean mCanSeeMembers;
  private boolean mDisableSubscription;
  private boolean mIsMember;
  private int mJoinability;
  private int mMemberCount;
  private int mMembershipStatus;
  private boolean mNotificationsEnabled;
  private String mSquareAboutText;
  private SquareLandingView.OnClickListener mSquareDetailsViewOnClickListener;
  private String mSquareName;
  private String mSquarePhotoUrl;
  private Boolean mViewIsExpanded;
  private int mVisibility;

  public SquareStreamAdapter(Context paramContext, ColumnGridView paramColumnGridView, EsAccount paramEsAccount, View.OnClickListener paramOnClickListener, ItemClickListener paramItemClickListener, StreamAdapter.ViewUseListener paramViewUseListener, StreamCardView.StreamPlusBarClickListener paramStreamPlusBarClickListener, StreamCardView.StreamMediaClickListener paramStreamMediaClickListener, ComposeBarController paramComposeBarController)
  {
    super(paramContext, paramColumnGridView, paramEsAccount, paramOnClickListener, paramItemClickListener, paramViewUseListener, paramStreamPlusBarClickListener, paramStreamMediaClickListener, paramComposeBarController);
    this.mVisibleIndex = 0;
  }

  private ColumnGridView.LayoutParams getLayoutParams()
  {
    int i;
    int j;
    label64: int k;
    if (this.mLandscape)
    {
      i = 1;
      if (!this.mLandscape)
        break label129;
      int n = (int)(0.7F * sScreenMetrics.longDimension);
      j = Math.max(getContext().getResources().getDimensionPixelSize(R.dimen.square_card_min_width), Math.min(getContext().getResources().getDimensionPixelSize(R.dimen.square_card_max_width), n));
      switch (sScreenMetrics.screenDisplayType)
      {
      default:
        k = 1;
      case 1:
      }
    }
    for (int m = 1; ; m = 2)
    {
      ColumnGridView.LayoutParams localLayoutParams = new ColumnGridView.LayoutParams(i, j, k, m);
      if (!this.mLandscape)
        localLayoutParams.height = -2;
      return localLayoutParams;
      i = 2;
      break;
      label129: j = -3;
      break label64;
      k = 2;
    }
  }

  public final void bindStreamHeaderView(View paramView, Cursor paramCursor)
  {
    int i = 5;
    SquareLandingView localSquareLandingView;
    if (paramCursor.getPosition() == 0)
    {
      if (Log.isLoggable("SquareStreamAdapter", 3))
        Log.d("SquareStreamAdapter", "bindView(); " + paramView);
      localSquareLandingView = (SquareLandingView)paramView;
      localSquareLandingView.setLayoutParams(getLayoutParams());
      localSquareLandingView.init(PrimitiveUtils.safeBoolean(this.mViewIsExpanded), this.mLandscape);
      if (!TextUtils.isEmpty(this.mSquareName))
      {
        localSquareLandingView.setSquareName(this.mSquareName);
        localSquareLandingView.setSquarePhoto(this.mSquarePhotoUrl);
        localSquareLandingView.setSquareMemberCount(this.mMemberCount);
        localSquareLandingView.setSquareAboutText(this.mSquareAboutText);
        localSquareLandingView.setSquareVisibility(this.mVisibility);
        localSquareLandingView.setMemberVisibility(this.mCanSeeMembers);
        if (this.mMembershipStatus != 4)
          break label212;
        localSquareLandingView.updateJoinLeaveButton(i);
        if ((!this.mIsMember) || (this.mDisableSubscription))
          break label280;
        localSquareLandingView.showNotificationSwitch(this.mNotificationsEnabled);
        label174: if ((this.mIsMember) || (this.mVisibility != 1) || (this.mJoinability != 1))
          break label288;
        localSquareLandingView.showBlockingExplanation();
        label202: localSquareLandingView.setOnClickListener(this.mSquareDetailsViewOnClickListener);
      }
    }
    while (true)
    {
      return;
      label212: if (this.mMembershipStatus == 1)
      {
        i = 6;
        break;
      }
      if (this.mMembershipStatus == i)
      {
        i = 2;
        break;
      }
      if (this.mCanJoin)
      {
        i = 1;
        break;
      }
      if (this.mCanRequestToJoin)
      {
        i = 3;
        break;
      }
      if (this.mIsMember)
      {
        i = 4;
        break;
      }
      i = 0;
      break;
      label280: localSquareLandingView.hideNotificationSwitch();
      break label174;
      label288: localSquareLandingView.hideBlockingExplanation();
      break label202;
      paramView.setLayoutParams(getLayoutParams());
    }
  }

  public final void bindStreamView(View paramView, Cursor paramCursor)
  {
    ((StreamCardView)paramView).setSquareMode(true, isSquareAdmin());
    super.bindStreamView(paramView, paramCursor);
  }

  public final int getStreamHeaderViewType(int paramInt)
  {
    switch (paramInt)
    {
    default:
    case 0:
    }
    for (int i = 11; ; i = 10)
      return i;
  }

  public final int getViewTypeCount()
  {
    return 2 + super.getViewTypeCount();
  }

  public final int getVisibility()
  {
    return this.mVisibility;
  }

  public final boolean hasSquareData()
  {
    if (!TextUtils.isEmpty(this.mSquareName));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isEmpty()
  {
    if ((super.isEmpty()) && (!hasSquareData()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isSquareAdmin()
  {
    int i = 1;
    if ((this.mMembershipStatus == 2) || (this.mMembershipStatus == i));
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  public final View newStreamHeaderView$4b8874c5(Context paramContext, Cursor paramCursor)
  {
    LayoutInflater localLayoutInflater = (LayoutInflater)paramContext.getSystemService("layout_inflater");
    Object localObject;
    if (paramCursor.getPosition() == 0)
    {
      localObject = (SquareLandingView)localLayoutInflater.inflate(R.layout.square_landing_view, null);
      if (Build.VERSION.SDK_INT >= 11)
        ((SquareLandingView)localObject).setLayoutTransition(new LayoutTransition());
      if (Log.isLoggable("SquareStreamAdapter", 3))
        Log.d("SquareStreamAdapter", "newView() -> " + localObject);
    }
    while (true)
    {
      return localObject;
      localObject = localLayoutInflater.inflate(R.layout.square_cant_see_posts, null);
    }
  }

  public final void resetAnimationState()
  {
    this.mVisibleIndex = 0;
  }

  public final void setOnClickListener(SquareLandingView.OnClickListener paramOnClickListener)
  {
    this.mSquareDetailsViewOnClickListener = paramOnClickListener;
  }

  public final void setSquareData(Cursor paramCursor)
  {
    int i = 1;
    this.mSquareName = paramCursor.getString(i);
    this.mSquarePhotoUrl = paramCursor.getString(3);
    this.mSquareAboutText = paramCursor.getString(4);
    this.mMemberCount = paramCursor.getInt(6);
    this.mMembershipStatus = paramCursor.getInt(7);
    if (paramCursor.getInt(8) != 0)
    {
      int k = i;
      this.mIsMember = k;
      this.mVisibility = paramCursor.getInt(10);
      this.mJoinability = paramCursor.getInt(5);
      if (paramCursor.getInt(13) == 0)
        break label232;
      int n = i;
      label114: this.mCanJoin = n;
      if (paramCursor.getInt(14) == 0)
        break label238;
      int i2 = i;
      label134: this.mCanRequestToJoin = i2;
      if (paramCursor.getInt(11) == 0)
        break label244;
      int i4 = i;
      label154: this.mCanSeeMembers = i4;
      if (paramCursor.getInt(17) == 0)
        break label250;
      int i6 = i;
      label174: this.mNotificationsEnabled = i6;
      if (paramCursor.getInt(24) == 0)
        break label256;
      int i8 = i;
      label194: this.mDisableSubscription = i8;
      if (this.mViewIsExpanded == null)
        if (this.mIsMember)
          break label262;
    }
    while (true)
    {
      this.mViewIsExpanded = Boolean.valueOf(i);
      notifyDataSetChanged();
      return;
      int m = 0;
      break;
      label232: int i1 = 0;
      break label114;
      label238: int i3 = 0;
      break label134;
      label244: int i5 = 0;
      break label154;
      label250: int i7 = 0;
      break label174;
      label256: int i9 = 0;
      break label194;
      label262: int j = 0;
    }
  }

  public final void setViewIsExpanded(Boolean paramBoolean)
  {
    this.mViewIsExpanded = paramBoolean;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.SquareStreamAdapter
 * JD-Core Version:    0.6.2
 */