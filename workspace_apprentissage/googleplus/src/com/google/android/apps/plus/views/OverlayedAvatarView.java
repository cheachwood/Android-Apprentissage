package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;

public class OverlayedAvatarView extends RelativeLayout
{
  private AvatarView mAvatarView;
  private ImageView mOverlay;
  private ImageView mTypeOverlay;

  public OverlayedAvatarView(Context paramContext)
  {
    this(paramContext, null);
  }

  public OverlayedAvatarView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public OverlayedAvatarView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public static OverlayedAvatarView create(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
  {
    return (OverlayedAvatarView)paramLayoutInflater.inflate(R.layout.participant_tray_avatar_view, paramViewGroup, false);
  }

  public void onFinishInflate()
  {
    this.mAvatarView = ((AvatarView)findViewById(R.id.avatar_image));
    this.mTypeOverlay = ((ImageView)findViewById(R.id.type_overlay));
    this.mOverlay = ((ImageView)findViewById(R.id.overlay));
  }

  public void setBorderResource(int paramInt)
  {
    setBackgroundResource(paramInt);
    invalidate();
  }

  public void setOverlayResource(int paramInt)
  {
    if (paramInt == 0)
      this.mOverlay.setVisibility(8);
    while (true)
    {
      invalidate();
      return;
      this.mOverlay.setVisibility(0);
      this.mOverlay.setBackgroundResource(paramInt);
    }
  }

  public void setParticipantGaiaId(String paramString)
  {
    this.mAvatarView.setGaiaId(paramString);
  }

  public void setParticipantType(int paramInt)
  {
    int i;
    switch (paramInt)
    {
    default:
      i = 4;
    case 1:
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      this.mTypeOverlay.setVisibility(i);
      return;
      this.mTypeOverlay.setImageResource(R.drawable.ic_profile_invited);
      i = 0;
      continue;
      this.mTypeOverlay.setImageResource(R.drawable.ic_profile_sms);
      i = 0;
      continue;
      i = 4;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.OverlayedAvatarView
 * JD-Core Version:    0.6.2
 */