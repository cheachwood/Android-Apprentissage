package com.google.android.apps.plus.settings;

import android.content.Context;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;

public class LabelPreference extends Preference
{
  private CharSequence mLabel;
  private int mLabelColor = -1;

  public LabelPreference(Context paramContext)
  {
    super(paramContext);
    setLayoutResource(R.layout.label_preference);
  }

  public LabelPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setLayoutResource(R.layout.label_preference);
  }

  public LabelPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setLayoutResource(R.layout.label_preference);
  }

  protected void onBindView(View paramView)
  {
    super.onBindView(paramView);
    TextView localTextView = (TextView)paramView.findViewById(R.id.label);
    if (localTextView != null)
    {
      if (!TextUtils.isEmpty(this.mLabel))
        break label37;
      localTextView.setVisibility(8);
    }
    while (true)
    {
      return;
      label37: localTextView.setVisibility(0);
      localTextView.setTextColor(this.mLabelColor);
      localTextView.setText(this.mLabel);
    }
  }

  public final void setLabel(CharSequence paramCharSequence)
  {
    if (((paramCharSequence == null) && (this.mLabel != null)) || ((paramCharSequence != null) && (!paramCharSequence.equals(this.mLabel))))
    {
      this.mLabel = paramCharSequence;
      notifyChanged();
    }
  }

  public final void setLabelColor(int paramInt)
  {
    if (paramInt != this.mLabelColor)
    {
      this.mLabelColor = paramInt;
      notifyChanged();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.settings.LabelPreference
 * JD-Core Version:    0.6.2
 */