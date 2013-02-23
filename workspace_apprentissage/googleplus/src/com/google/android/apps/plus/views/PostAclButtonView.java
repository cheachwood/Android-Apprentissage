package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;

public class PostAclButtonView extends FrameLayout
{
  private View mButton;
  private ImageView mCheck;
  private Integer mCheckId;
  private ImageView mIcon;
  private Integer mIconActiveId;
  private Integer mIconInactiveId;
  private String mLabel;
  private ConstrainedTextView mText;

  public PostAclButtonView(Context paramContext)
  {
    this(paramContext, null);
  }

  public PostAclButtonView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public PostAclButtonView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    addView(LayoutInflater.from(getContext()).inflate(R.layout.post_acl_button, this, false));
    this.mButton = findViewById(R.id.button);
    this.mText = ((ConstrainedTextView)findViewById(R.id.acl_text));
    this.mIcon = ((ImageView)findViewById(R.id.acl_icon));
    this.mCheck = ((ImageView)findViewById(R.id.acl_check));
  }

  private void initialize(String paramString, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3)
  {
    this.mIconActiveId = paramInteger1;
    this.mIconInactiveId = paramInteger2;
    this.mCheckId = paramInteger3;
    setLabelText(paramString);
    setInactive();
  }

  private static void setImageDrawable(ImageView paramImageView, Integer paramInteger)
  {
    if (paramInteger == null)
      paramImageView.setImageDrawable(null);
    while (true)
    {
      return;
      paramImageView.setImageResource(paramInteger.intValue());
    }
  }

  public final void initialize(String paramString, int paramInt)
  {
    initialize(paramString, null, null, Integer.valueOf(paramInt));
  }

  public final void initialize(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    initialize(paramString, new Integer(paramInt1), new Integer(paramInt2), new Integer(paramInt3));
  }

  public void setActive()
  {
    setImageDrawable(this.mIcon, this.mIconActiveId);
    setImageDrawable(this.mCheck, this.mCheckId);
  }

  public void setInactive()
  {
    setImageDrawable(this.mIcon, this.mIconInactiveId);
    setImageDrawable(this.mCheck, null);
  }

  public void setLabelText(String paramString)
  {
    if (paramString == null)
      paramString = "";
    this.mLabel = paramString;
    this.mText.setText(this.mLabel);
  }

  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mButton.setOnClickListener(paramOnClickListener);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PostAclButtonView
 * JD-Core Version:    0.6.2
 */