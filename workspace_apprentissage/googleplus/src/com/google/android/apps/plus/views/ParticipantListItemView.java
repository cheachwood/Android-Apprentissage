package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.content.EsPeopleData;

public class ParticipantListItemView extends RelativeLayout
{
  private AvatarView mAvatarView;
  private TextView mNameTextView;
  private String mPersonId;
  private int mPosition;
  private SectionHeaderView mSectionHeader;

  public ParticipantListItemView(Context paramContext)
  {
    this(paramContext, null);
  }

  public ParticipantListItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public final void clear()
  {
    this.mNameTextView.setText(null);
  }

  public final String getPersonId()
  {
    return this.mPersonId;
  }

  public final void hideSectionHeader()
  {
    this.mSectionHeader.setVisibility(8);
  }

  public void onFinishInflate()
  {
    this.mAvatarView = ((AvatarView)findViewById(R.id.avatar));
    this.mNameTextView = ((TextView)findViewById(R.id.name));
    this.mSectionHeader = ((SectionHeaderView)findViewById(R.id.sectionHeader));
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

  public final void showSectionHeader(char paramChar)
  {
    this.mSectionHeader.setVisibility(0);
    this.mSectionHeader.setText(String.valueOf(paramChar));
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ParticipantListItemView
 * JD-Core Version:    0.6.2
 */