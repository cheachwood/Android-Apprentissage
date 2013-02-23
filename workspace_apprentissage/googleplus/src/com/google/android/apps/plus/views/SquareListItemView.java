package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;

public class SquareListItemView extends FrameLayout
  implements View.OnClickListener, Recyclable
{
  private TextView mMemberCountView;
  private ConstrainedTextView mNameView;
  protected OnItemClickListener mOnItemClickListener;
  private EsImageView mPhotoView;
  protected String mSquareId;
  private TextView mUnreadCountView;
  private TextView mVisibilityView;

  public SquareListItemView(Context paramContext)
  {
    super(paramContext);
  }

  public SquareListItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public SquareListItemView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public void init(Cursor paramCursor, OnItemClickListener paramOnItemClickListener, boolean paramBoolean1, boolean paramBoolean2)
  {
    initChildViews();
    this.mOnItemClickListener = paramOnItemClickListener;
    this.mSquareId = paramCursor.getString(1);
    this.mNameView.setText(paramCursor.getString(2));
    String str1 = paramCursor.getString(3);
    if (TextUtils.isEmpty(str1))
      str1 = null;
    this.mPhotoView.setUrl(str1);
    int i;
    int j;
    label97: int k;
    if (paramBoolean1)
    {
      i = paramCursor.getInt(4);
      setSquareVisibility(i);
      if (!paramBoolean2)
        break label146;
      j = paramCursor.getInt(7);
      if (j != 0)
        break label152;
      this.mUnreadCountView.setVisibility(8);
      k = paramCursor.getInt(5);
      if (k != 0)
        break label201;
      this.mMemberCountView.setVisibility(8);
    }
    while (true)
    {
      setOnClickListener(this);
      return;
      i = 0;
      break;
      label146: j = 0;
      break label97;
      label152: this.mUnreadCountView.setVisibility(0);
      if (j > 99);
      for (String str2 = getResources().getString(R.string.ninety_nine_plus); ; str2 = Integer.toString(j))
      {
        this.mUnreadCountView.setText(str2);
        break;
      }
      label201: this.mMemberCountView.setVisibility(0);
      Resources localResources = getResources();
      int m = R.plurals.square_members_count;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(k);
      String str3 = localResources.getQuantityString(m, k, arrayOfObject);
      this.mMemberCountView.setText(str3);
    }
  }

  public void initChildViews()
  {
    if (this.mNameView == null)
    {
      this.mNameView = ((ConstrainedTextView)findViewById(R.id.square_name));
      this.mPhotoView = ((EsImageView)findViewById(R.id.square_photo));
      this.mVisibilityView = ((TextView)findViewById(R.id.square_visibility));
      this.mMemberCountView = ((TextView)findViewById(R.id.member_count));
      this.mUnreadCountView = ((TextView)findViewById(R.id.unread_count));
    }
  }

  public void onClick(View paramView)
  {
    if (this.mOnItemClickListener != null)
      this.mOnItemClickListener.onClick(this.mSquareId);
  }

  protected void onFinishInflate()
  {
    super.onFinishInflate();
    initChildViews();
  }

  public void onRecycle()
  {
    if (this.mPhotoView != null)
      this.mPhotoView.onRecycle();
    setOnClickListener(null);
  }

  public void setSquareVisibility(int paramInt)
  {
    int i;
    int j;
    int k;
    switch (paramInt)
    {
    default:
      i = 0;
      j = 0;
      k = 0;
      if (i != 0)
      {
        this.mVisibilityView.setVisibility(0);
        this.mVisibilityView.setText(j);
        this.mVisibilityView.setCompoundDrawablesWithIntrinsicBounds(k, 0, 0, 0);
      }
      break;
    case 0:
    case 1:
    }
    while (true)
    {
      return;
      i = 1;
      j = R.string.square_public;
      k = R.drawable.ic_public_small;
      break;
      i = 1;
      j = R.string.square_private;
      k = R.drawable.ic_private_small;
      break;
      this.mVisibilityView.setVisibility(8);
    }
  }

  public static abstract interface OnItemClickListener
  {
    public abstract void onClick(String paramString);

    public abstract void onInvitationDismissed(String paramString);

    public abstract void onInviterImageClick(String paramString);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.SquareListItemView
 * JD-Core Version:    0.6.2
 */