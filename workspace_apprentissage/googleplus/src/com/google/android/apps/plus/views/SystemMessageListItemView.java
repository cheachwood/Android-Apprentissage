package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.string;

public class SystemMessageListItemView extends RelativeLayout
{
  private static int sErrorBackgroundColor;
  private static int sErrorMessageColor;
  private static int sErrorTimestampColor;
  private static int sInfoBackgroundColor;
  private static int sInfoMessageColor;
  private static int sInfoTimestampColor;
  private static boolean sInitialized = false;
  private TextView mText;
  private TextView mTimestamp;

  public SystemMessageListItemView(Context paramContext)
  {
    this(paramContext, null);
  }

  public SystemMessageListItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (!sInitialized)
    {
      Resources localResources = paramContext.getApplicationContext().getResources();
      sInfoMessageColor = localResources.getColor(R.color.realtimechat_system_information_foreground);
      sInfoTimestampColor = localResources.getColor(R.color.realtimechat_system_information_timestamp);
      sInfoBackgroundColor = localResources.getColor(R.color.realtimechat_system_information_background);
      sErrorMessageColor = localResources.getColor(R.color.realtimechat_system_error_foreground);
      sErrorTimestampColor = localResources.getColor(R.color.realtimechat_system_error_timestamp);
      sErrorBackgroundColor = localResources.getColor(R.color.realtimechat_system_error_background);
      sInitialized = true;
    }
  }

  public final CharSequence getText()
  {
    return this.mText.getText();
  }

  public void onFinishInflate()
  {
    this.mTimestamp = ((TextView)findViewById(R.id.timestamp));
    this.mText = ((TextView)findViewById(R.id.text));
  }

  public void setText(CharSequence paramCharSequence)
  {
    this.mText.setText(Html.fromHtml((String)paramCharSequence));
  }

  public void setTimeSince(CharSequence paramCharSequence)
  {
    this.mTimestamp.setText(paramCharSequence);
  }

  public void setType(int paramInt)
  {
    if (paramInt == 4)
    {
      setBackgroundColor(sErrorBackgroundColor);
      this.mText.setTextColor(sErrorMessageColor);
      this.mTimestamp.setTextColor(sErrorTimestampColor);
    }
    while (true)
    {
      return;
      setBackgroundColor(sInfoBackgroundColor);
      this.mText.setTextColor(sInfoMessageColor);
      this.mTimestamp.setTextColor(sInfoTimestampColor);
    }
  }

  public final void updateContentDescription()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Resources localResources = getResources();
    CharSequence localCharSequence1 = this.mTimestamp.getText();
    if ((localCharSequence1 != null) && (localCharSequence1.length() > 0))
    {
      localStringBuilder.append(localResources.getString(R.string.realtimechat_message_description_time_since, new Object[] { localCharSequence1 }));
      localStringBuilder.append(" ");
    }
    CharSequence localCharSequence2 = this.mText.getText();
    if ((localCharSequence2 != null) && (localCharSequence2.length() > 0))
      localStringBuilder.append(localResources.getString(R.string.realtimechat_message_description_system_message, new Object[] { localCharSequence2 }));
    setContentDescription(localStringBuilder.toString());
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.SystemMessageListItemView
 * JD-Core Version:    0.6.2
 */