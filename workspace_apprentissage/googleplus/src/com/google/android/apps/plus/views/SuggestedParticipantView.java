package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;

public class SuggestedParticipantView extends RelativeLayout
{
  private AvatarView mAvatarView;
  private View mHeaderView;
  private TextView mParticipantName;

  public SuggestedParticipantView(Context paramContext)
  {
    super(paramContext);
  }

  public SuggestedParticipantView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public final void clear()
  {
    this.mParticipantName.setText(null);
    this.mAvatarView.setGaiaId(null);
  }

  public void onFinishInflate()
  {
    this.mAvatarView = ((AvatarView)findViewById(R.id.avatar));
    this.mParticipantName = ((TextView)findViewById(R.id.participantName));
    this.mHeaderView = findViewById(R.id.sectionHeader);
  }

  public void setHeaderVisible(boolean paramBoolean)
  {
    View localView = this.mHeaderView;
    if (paramBoolean);
    for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      return;
    }
  }

  public void setParticipantId(String paramString)
  {
    this.mAvatarView.setGaiaId(paramString);
  }

  public void setParticipantName(CharSequence paramCharSequence)
  {
    this.mParticipantName.setText(paramCharSequence);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.SuggestedParticipantView
 * JD-Core Version:    0.6.2
 */