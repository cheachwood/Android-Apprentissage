package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.phone.ConversationActivity;

public class HangoutTileEventMessageListItemView extends RelativeLayout
{
  private static boolean sInitialized = false;
  private static int sMessageColor;
  private static int sTimestampColor;
  private TextView mText;
  private TextView mTimestamp;

  public HangoutTileEventMessageListItemView(Context paramContext)
  {
    this(paramContext, null);
  }

  public HangoutTileEventMessageListItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        ((ConversationActivity)HangoutTileEventMessageListItemView.this.getContext()).toggleTiles();
      }
    });
    if (!sInitialized)
    {
      Resources localResources = paramContext.getApplicationContext().getResources();
      sMessageColor = localResources.getColor(R.color.realtimechat_system_information_foreground);
      sTimestampColor = localResources.getColor(R.color.realtimechat_system_information_timestamp);
      sInitialized = true;
    }
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
    if (paramInt == 6)
    {
      this.mText.setTextColor(sMessageColor);
      this.mTimestamp.setTextColor(sTimestampColor);
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
      localStringBuilder.append(localResources.getString(R.string.realtimechat_message_description_message, new Object[] { localCharSequence2 }));
    setContentDescription(localStringBuilder.toString());
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.HangoutTileEventMessageListItemView
 * JD-Core Version:    0.6.2
 */