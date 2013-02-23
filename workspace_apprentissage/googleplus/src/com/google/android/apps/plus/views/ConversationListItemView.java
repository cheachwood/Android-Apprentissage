package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAvatarData;
import java.util.List;

public class ConversationListItemView extends RelativeLayout
  implements Checkable
{
  private static Drawable sBackgroundChecked;
  private static int sBackgroundUnchecked;
  private static Bitmap sDefaultUserImage;
  private static boolean sInitialized;
  private AvatarView mAvatarFullView;
  private AvatarView mAvatarLeftFullView;
  private AvatarView mAvatarLowerLeftView;
  private AvatarView mAvatarLowerRightView;
  private AvatarView mAvatarUpperLeftView;
  private AvatarView mAvatarUpperRightView;
  private boolean mChecked;
  private TextView mLastMessageTextView;
  private boolean mMuted;
  private ImageView mMutedIcon;
  private TextView mNameTextView;
  private int mPosition;
  private TextView mTimeSinceTextView;
  private int mUnreadCount;
  private TextView mUnreadCountTextView;

  public ConversationListItemView(Context paramContext)
  {
    this(paramContext, null);
  }

  public ConversationListItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (!sInitialized)
    {
      sInitialized = true;
      Resources localResources = paramContext.getApplicationContext().getResources();
      sBackgroundChecked = localResources.getDrawable(R.drawable.list_selected_holo);
      sBackgroundUnchecked = localResources.getColor(R.color.clear);
    }
    this.mUnreadCount = 0;
    this.mMuted = false;
    if (sDefaultUserImage == null)
      sDefaultUserImage = EsAvatarData.getMediumDefaultAvatar(paramContext);
  }

  private void refreshUnreadCountView()
  {
    if (this.mUnreadCount == 0)
      this.mUnreadCountTextView.setVisibility(8);
    while (true)
    {
      return;
      this.mUnreadCountTextView.setVisibility(0);
    }
  }

  public final void clear()
  {
    this.mNameTextView.setText(null);
    this.mLastMessageTextView.setText(null);
    this.mTimeSinceTextView.setText(null);
    this.mUnreadCountTextView.setText(null);
  }

  public boolean isChecked()
  {
    return this.mChecked;
  }

  public void onFinishInflate()
  {
    this.mAvatarFullView = ((AvatarView)findViewById(R.id.avatarFull));
    this.mAvatarLeftFullView = ((AvatarView)findViewById(R.id.avatarLeftFull));
    this.mAvatarUpperLeftView = ((AvatarView)findViewById(R.id.avatarUpperLeft));
    this.mAvatarLowerLeftView = ((AvatarView)findViewById(R.id.avatarLowerLeft));
    this.mAvatarUpperRightView = ((AvatarView)findViewById(R.id.avatarUpperRight));
    this.mAvatarLowerRightView = ((AvatarView)findViewById(R.id.avatarLowerRight));
    this.mNameTextView = ((TextView)findViewById(R.id.conversationName));
    this.mLastMessageTextView = ((TextView)findViewById(R.id.lastMessage));
    this.mTimeSinceTextView = ((TextView)findViewById(R.id.timeSince));
    this.mUnreadCountTextView = ((TextView)findViewById(R.id.unreadCount));
    this.mMutedIcon = ((ImageView)findViewById(R.id.mutedIcon));
  }

  public void setChecked(boolean paramBoolean)
  {
    if (paramBoolean != this.mChecked)
    {
      this.mChecked = paramBoolean;
      if (!paramBoolean)
        break label29;
      setBackgroundDrawable(sBackgroundChecked);
    }
    while (true)
    {
      invalidate();
      return;
      label29: setBackgroundColor(sBackgroundUnchecked);
    }
  }

  public void setConversationName(CharSequence paramCharSequence)
  {
    this.mNameTextView.setText(paramCharSequence);
  }

  public void setLastMessage(CharSequence paramCharSequence)
  {
    this.mLastMessageTextView.setText(paramCharSequence);
  }

  public void setMuted(boolean paramBoolean)
  {
    if (this.mMuted != paramBoolean)
    {
      this.mMuted = paramBoolean;
      if (!this.mMuted)
        break label33;
      this.mMutedIcon.setVisibility(0);
    }
    while (true)
    {
      refreshUnreadCountView();
      return;
      label33: this.mMutedIcon.setVisibility(8);
    }
  }

  public void setParticipantsId(List<String> paramList, String paramString)
  {
    if ((paramList == null) || (paramList.size() == 0))
    {
      this.mAvatarFullView.setVisibility(0);
      this.mAvatarFullView.setGaiaId(paramString);
      this.mAvatarLeftFullView.setVisibility(8);
      this.mAvatarUpperLeftView.setVisibility(8);
      this.mAvatarLowerLeftView.setVisibility(8);
      this.mAvatarUpperRightView.setVisibility(8);
      this.mAvatarLowerRightView.setVisibility(8);
    }
    while (true)
    {
      return;
      if (paramList.size() == 1)
      {
        this.mAvatarFullView.setVisibility(0);
        this.mAvatarFullView.setGaiaId((String)paramList.get(0));
        this.mAvatarLeftFullView.setVisibility(8);
        this.mAvatarUpperLeftView.setVisibility(8);
        this.mAvatarLowerLeftView.setVisibility(8);
        this.mAvatarUpperRightView.setVisibility(8);
        this.mAvatarLowerRightView.setVisibility(8);
      }
      else
      {
        if ((paramList.size() == 2) || (paramList.size() == 3))
        {
          this.mAvatarFullView.setVisibility(8);
          this.mAvatarLeftFullView.setGaiaId((String)paramList.get(0));
          this.mAvatarLeftFullView.setVisibility(0);
          this.mAvatarUpperLeftView.setVisibility(8);
          this.mAvatarLowerLeftView.setVisibility(8);
          this.mAvatarUpperRightView.setGaiaId((String)paramList.get(1));
          this.mAvatarUpperRightView.setVisibility(0);
          if (paramList.size() == 2)
            this.mAvatarLowerRightView.setGaiaId(paramString);
          while (true)
          {
            this.mAvatarLowerRightView.setVisibility(0);
            break;
            this.mAvatarLowerRightView.setGaiaId((String)paramList.get(2));
          }
        }
        if (paramList.size() >= 4)
        {
          this.mAvatarFullView.setVisibility(8);
          this.mAvatarLeftFullView.setVisibility(8);
          this.mAvatarUpperLeftView.setVisibility(0);
          this.mAvatarLowerLeftView.setVisibility(0);
          this.mAvatarUpperRightView.setVisibility(0);
          this.mAvatarLowerRightView.setVisibility(0);
          this.mAvatarUpperLeftView.setGaiaId((String)paramList.get(0));
          this.mAvatarLowerLeftView.setGaiaId((String)paramList.get(1));
          this.mAvatarUpperRightView.setGaiaId((String)paramList.get(2));
          this.mAvatarLowerRightView.setGaiaId((String)paramList.get(3));
        }
      }
    }
  }

  public void setPosition(int paramInt)
  {
    this.mPosition = paramInt;
  }

  public void setTimeSince(CharSequence paramCharSequence)
  {
    this.mTimeSinceTextView.setText(paramCharSequence);
  }

  public void setUnreadCount(int paramInt)
  {
    this.mUnreadCount = paramInt;
    this.mUnreadCountTextView.setText(String.valueOf(paramInt));
    refreshUnreadCountView();
  }

  public void toggle()
  {
    if (!this.mChecked);
    for (boolean bool = true; ; bool = false)
    {
      setChecked(bool);
      return;
    }
  }

  public final void updateContentDescription()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Resources localResources = getResources();
    if (this.mMuted)
    {
      localStringBuilder.append(localResources.getString(R.string.realtimechat_conversation_description_muted));
      localStringBuilder.append(" ");
    }
    if (this.mUnreadCount > 0)
    {
      int i = R.string.realtimechat_conversation_description_unread_count;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(this.mUnreadCount);
      localStringBuilder.append(localResources.getString(i, arrayOfObject));
      localStringBuilder.append(" ");
    }
    CharSequence localCharSequence1 = this.mNameTextView.getText();
    if ((localCharSequence1 != null) && (localCharSequence1.length() > 0))
    {
      localStringBuilder.append(localResources.getString(R.string.realtimechat_conversation_description_title, new Object[] { localCharSequence1 }));
      localStringBuilder.append(" ");
    }
    CharSequence localCharSequence2 = this.mTimeSinceTextView.getText();
    if ((localCharSequence2 != null) && (localCharSequence2.length() > 0))
    {
      localStringBuilder.append(localResources.getString(R.string.realtimechat_conversation_description_time_since, new Object[] { localCharSequence2 }));
      localStringBuilder.append(" ");
    }
    CharSequence localCharSequence3 = this.mLastMessageTextView.getText();
    if ((localCharSequence3 != null) && (localCharSequence3.length() > 0))
    {
      localStringBuilder.append(localResources.getString(R.string.realtimechat_conversation_description_message, new Object[] { localCharSequence3 }));
      localStringBuilder.append(" ");
    }
    setContentDescription(localStringBuilder.toString());
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ConversationListItemView
 * JD-Core Version:    0.6.2
 */