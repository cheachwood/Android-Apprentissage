package com.google.android.apps.plus.views;

import android.content.Context;
import android.database.Cursor;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAvatarData;

public class SquareListInvitationView extends SquareListItemView
{
  private ImageView mDismissButtonView;
  private TextView mInvitationTextView;
  private AvatarView mInviterAvatarView;
  protected String mInviterGaiaId;

  public SquareListInvitationView(Context paramContext)
  {
    super(paramContext);
  }

  public SquareListInvitationView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public SquareListInvitationView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public final void init(Cursor paramCursor, SquareListItemView.OnItemClickListener paramOnItemClickListener, boolean paramBoolean1, boolean paramBoolean2)
  {
    super.init(paramCursor, paramOnItemClickListener, paramBoolean1, paramBoolean2);
    this.mInviterGaiaId = paramCursor.getString(8);
    String str1 = paramCursor.getString(9);
    String str2 = paramCursor.getString(10);
    if (!TextUtils.isEmpty(str1))
    {
      String str4 = getContext().getString(R.string.square_invitation, new Object[] { str1 });
      this.mInvitationTextView.setText(Html.fromHtml(str4));
    }
    while (true)
    {
      this.mInviterAvatarView.setGaiaIdAndAvatarUrl(this.mInviterGaiaId, EsAvatarData.uncompressAvatarUrl(str2));
      this.mInviterAvatarView.setOnClickListener(this);
      this.mDismissButtonView.setOnClickListener(this);
      return;
      String str3 = getContext().getString(R.string.square_invitation_no_name);
      this.mInvitationTextView.setText(str3);
    }
  }

  public final void initChildViews()
  {
    super.initChildViews();
    if (this.mInvitationTextView == null)
    {
      this.mInvitationTextView = ((TextView)findViewById(R.id.invitation_text));
      this.mInviterAvatarView = ((AvatarView)findViewById(R.id.inviter_avatar));
      this.mDismissButtonView = ((ImageView)findViewById(R.id.dismiss));
    }
  }

  public void onClick(View paramView)
  {
    int i;
    if (this.mOnItemClickListener != null)
    {
      i = paramView.getId();
      if (i != R.id.inviter_avatar)
        break label33;
      this.mOnItemClickListener.onInviterImageClick(this.mInviterGaiaId);
    }
    while (true)
    {
      return;
      label33: if (i == R.id.dismiss)
        this.mOnItemClickListener.onInvitationDismissed(this.mSquareId);
      else
        super.onClick(paramView);
    }
  }

  public void onRecycle()
  {
    if (this.mInviterAvatarView != null)
      this.mInviterAvatarView.setOnClickListener(null);
    if (this.mDismissButtonView != null)
      this.mDismissButtonView.setOnClickListener(null);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.SquareListInvitationView
 * JD-Core Version:    0.6.2
 */