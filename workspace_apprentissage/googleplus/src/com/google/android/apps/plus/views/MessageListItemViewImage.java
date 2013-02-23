package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.content.ImageRequest;

public class MessageListItemViewImage extends RelativeLayout
  implements View.OnClickListener
{
  private static Bitmap sDefaultUserImage;
  private static int sFailedAuthorColor;
  private static int sNormalAuthorColor;
  private ImageView mAuthorArrow;
  private TextView mAuthorNameTextView;
  private AvatarView mAvatarView;
  private TextView mCancelButton;
  private String mFullResUrl;
  private String mGaiaId;
  private View mImageFrame;
  private Integer mImageHeight = null;
  private Integer mImageWidth = null;
  private OnMeasuredListener mMeasuredListener;
  private MessageClickListener mMessageClickListener;
  private EsImageView mMessageImageView;
  private long mMessageRowId;
  private int mMessageStatus;
  private int mPosition;
  private TextView mRetryButton;
  private boolean mShowAuthor;
  private boolean mShowStatus;
  private ImageView mStatusImage;
  private TextView mTimeSinceTextView;

  public MessageListItemViewImage(Context paramContext)
  {
    this(paramContext, null);
  }

  public MessageListItemViewImage(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (sDefaultUserImage == null)
    {
      Resources localResources = getContext().getApplicationContext().getResources();
      sDefaultUserImage = EsAvatarData.getMediumDefaultAvatar(paramContext);
      sNormalAuthorColor = localResources.getColor(R.color.realtimechat_message_author);
      sFailedAuthorColor = localResources.getColor(R.color.realtimechat_message_author_failed);
    }
  }

  private void updateStatusImages()
  {
    if (this.mShowStatus)
      switch (this.mMessageStatus)
      {
      case 6:
      default:
        this.mStatusImage.setVisibility(8);
        this.mRetryButton.setVisibility(8);
        this.mCancelButton.setVisibility(8);
        this.mTimeSinceTextView.setVisibility(8);
      case 0:
      case 1:
      case 7:
      case 3:
      case 4:
      case 2:
      case 8:
      case 5:
      }
    while ((this.mMessageStatus == 2) || (this.mMessageStatus == 8))
    {
      this.mTimeSinceTextView.setVisibility(8);
      this.mRetryButton.setVisibility(0);
      this.mCancelButton.setVisibility(0);
      this.mAuthorNameTextView.setTextColor(sFailedAuthorColor);
      this.mAuthorNameTextView.setVisibility(0);
      return;
      this.mStatusImage.setImageResource(R.drawable.ic_huddle_sending);
      this.mStatusImage.setVisibility(0);
      continue;
      this.mStatusImage.setImageResource(R.drawable.ic_huddle_sent);
      this.mStatusImage.setVisibility(0);
      continue;
      this.mStatusImage.setVisibility(8);
      continue;
      this.mStatusImage.setImageResource(R.drawable.ic_huddle_read);
      this.mStatusImage.setVisibility(0);
      continue;
      this.mStatusImage.setVisibility(8);
    }
    this.mAuthorNameTextView.setTextColor(sNormalAuthorColor);
    if (this.mShowAuthor)
      this.mTimeSinceTextView.setVisibility(0);
    while (true)
    {
      this.mRetryButton.setVisibility(8);
      this.mCancelButton.setVisibility(8);
      break;
      this.mTimeSinceTextView.setVisibility(8);
    }
  }

  public final void clear()
  {
    this.mAuthorNameTextView.setText(null);
    this.mTimeSinceTextView.setText(null);
    this.mMessageImageView.onRecycle();
    this.mGaiaId = null;
    this.mStatusImage.setVisibility(8);
    this.mRetryButton.setVisibility(8);
  }

  public final String getFullResUrl()
  {
    return this.mFullResUrl;
  }

  public final Integer getImageHeight()
  {
    return this.mImageHeight;
  }

  public final Integer getImageWidth()
  {
    return this.mImageWidth;
  }

  public final void hideAuthor()
  {
    this.mShowAuthor = false;
    this.mAvatarView.setVisibility(8);
    this.mAuthorNameTextView.setVisibility(8);
    this.mAuthorArrow.setVisibility(8);
    updateStatusImages();
  }

  public void onClick(View paramView)
  {
    if ((paramView == this.mAvatarView) && (this.mMessageClickListener != null))
      this.mMessageClickListener.onUserImageClicked(null);
    while (true)
    {
      return;
      if ((paramView == this.mRetryButton) && (this.mMessageClickListener != null))
        this.mMessageClickListener.onRetryButtonClicked(this.mMessageRowId);
      else if ((paramView == this.mCancelButton) && (this.mMessageClickListener != null))
        this.mMessageClickListener.onCancelButtonClicked(this.mMessageRowId);
      else if ((paramView == this.mMessageImageView) && (this.mMessageClickListener != null))
        this.mMessageClickListener.onMediaImageClick(this.mFullResUrl, this.mAvatarView.getGaiaId());
    }
  }

  public void onFinishInflate()
  {
    this.mAvatarView = ((AvatarView)findViewById(R.id.avatar_image));
    this.mAuthorArrow = ((ImageView)findViewById(R.id.authorArrow));
    this.mAvatarView.setOnClickListener(this);
    this.mAuthorNameTextView = ((TextView)findViewById(R.id.authorName));
    this.mMessageImageView = ((EsImageView)findViewById(R.id.messageImage));
    this.mMessageImageView.setOnClickListener(this);
    this.mImageFrame = findViewById(R.id.image_frame);
    this.mTimeSinceTextView = ((TextView)findViewById(R.id.timeSince));
    this.mStatusImage = ((ImageView)findViewById(R.id.message_status));
    this.mRetryButton = ((TextView)findViewById(R.id.retry_send));
    this.mRetryButton.setOnClickListener(this);
    this.mCancelButton = ((TextView)findViewById(R.id.cancel_send));
    this.mCancelButton.setOnClickListener(this);
    this.mShowStatus = true;
    this.mShowAuthor = true;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    this.mImageWidth = Integer.valueOf(this.mMessageImageView.getMeasuredWidth());
    this.mImageHeight = Integer.valueOf(this.mMessageImageView.getMeasuredHeight());
    if (this.mMeasuredListener != null)
      this.mMeasuredListener.onMeasured(this);
  }

  public void setAuthorName(CharSequence paramCharSequence)
  {
    this.mAuthorNameTextView.setText(paramCharSequence);
  }

  public void setGaiaId(String paramString)
  {
    this.mAvatarView.setGaiaId(paramString);
  }

  public void setImage(String paramString, ImageRequest paramImageRequest)
  {
    this.mFullResUrl = paramString;
    this.mMessageImageView.setRequest(paramImageRequest);
    this.mMessageImageView.setVisibility(0);
    this.mImageFrame.setVisibility(0);
  }

  public void setMessage(CharSequence paramCharSequence)
  {
    this.mMessageImageView.setVisibility(8);
    this.mImageFrame.setVisibility(8);
    setBackgroundResource(R.color.clear);
  }

  public void setMessageClickListener(MessageClickListener paramMessageClickListener)
  {
    this.mMessageClickListener = paramMessageClickListener;
  }

  public void setMessageRowId(long paramLong)
  {
    this.mMessageRowId = paramLong;
  }

  public void setMessageStatus(int paramInt, boolean paramBoolean)
  {
    this.mMessageStatus = paramInt;
    this.mShowStatus = paramBoolean;
    updateStatusImages();
  }

  public void setOnMeasureListener(OnMeasuredListener paramOnMeasuredListener)
  {
    this.mMeasuredListener = paramOnMeasuredListener;
  }

  public void setPosition(int paramInt)
  {
    this.mPosition = paramInt;
  }

  public void setTimeSince(CharSequence paramCharSequence)
  {
    this.mTimeSinceTextView.setText(paramCharSequence);
  }

  public final void showAuthor()
  {
    this.mShowAuthor = true;
    this.mAvatarView.setVisibility(0);
    this.mAuthorNameTextView.setVisibility(0);
    this.mAuthorArrow.setVisibility(0);
    updateStatusImages();
  }

  public final void updateContentDescription()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Resources localResources = getResources();
    if (this.mShowAuthor)
    {
      CharSequence localCharSequence2 = this.mAuthorNameTextView.getText();
      if ((localCharSequence2 != null) && (localCharSequence2.length() > 0))
      {
        localStringBuilder.append(localResources.getString(R.string.realtimechat_message_description_author, new Object[] { localCharSequence2 }));
        localStringBuilder.append(" ");
      }
    }
    CharSequence localCharSequence1 = this.mTimeSinceTextView.getText();
    if ((localCharSequence1 != null) && (localCharSequence1.length() > 0))
    {
      localStringBuilder.append(localResources.getString(R.string.realtimechat_message_description_time_since, new Object[] { localCharSequence1 }));
      localStringBuilder.append(" ");
    }
    localStringBuilder.append(localResources.getString(R.string.realtimechat_message_description_image));
    setContentDescription(localStringBuilder.toString());
  }

  public static abstract interface OnMeasuredListener
  {
    public abstract void onMeasured(View paramView);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.MessageListItemViewImage
 * JD-Core Version:    0.6.2
 */