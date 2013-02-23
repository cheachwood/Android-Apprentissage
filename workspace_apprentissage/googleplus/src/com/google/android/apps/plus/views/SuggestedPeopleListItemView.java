package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.content.EsPeopleData;

public class SuggestedPeopleListItemView extends RelativeLayout
{
  private AvatarView mAvatarView;
  private View mCheckIndicator;
  private TextView mNameTextView;
  private String mPersonId;
  private int mPosition;

  public SuggestedPeopleListItemView(Context paramContext)
  {
    this(paramContext, null);
  }

  public SuggestedPeopleListItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public void onFinishInflate()
  {
    this.mAvatarView = ((AvatarView)findViewById(R.id.avatar));
    this.mNameTextView = ((TextView)findViewById(R.id.name));
    this.mCheckIndicator = findViewById(R.id.check_indicator);
  }

  public void setChecked(boolean paramBoolean)
  {
    View localView = this.mCheckIndicator;
    if (paramBoolean);
    for (int i = 0; ; i = 4)
    {
      localView.setVisibility(i);
      return;
    }
  }

  public void setParticipantName(String paramString)
  {
    if (paramString == null)
      this.mNameTextView.setText(null);
    while (true)
    {
      return;
      this.mNameTextView.setText(paramString);
    }
  }

  public void setPersonId(String paramString)
  {
    this.mPersonId = paramString;
    this.mAvatarView.setGaiaId(EsPeopleData.extractGaiaId(paramString));
  }

  public void setPosition(int paramInt)
  {
    this.mPosition = paramInt;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.SuggestedPeopleListItemView
 * JD-Core Version:    0.6.2
 */