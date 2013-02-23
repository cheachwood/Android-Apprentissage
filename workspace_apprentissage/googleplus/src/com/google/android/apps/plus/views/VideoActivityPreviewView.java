package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.network.ApiaryActivity;
import com.google.android.apps.plus.network.ApiaryVideoActivity;

public class VideoActivityPreviewView extends ActivityPreviewView
{
  private static final int PREVIEW_VIDEO_LAYOUT = R.layout.share_preview_video_view;

  public VideoActivityPreviewView(Context paramContext)
  {
    super(paramContext, null, 0);
  }

  public VideoActivityPreviewView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 0);
  }

  public VideoActivityPreviewView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public void setActivity(ApiaryActivity paramApiaryActivity)
  {
    super.setActivity(paramApiaryActivity);
    ApiaryVideoActivity localApiaryVideoActivity = (ApiaryVideoActivity)getActivity();
    removeAllViews();
    View localView = LayoutInflater.from(getContext()).inflate(PREVIEW_VIDEO_LAYOUT, null);
    addView(localView);
    TextView localTextView = (TextView)localView.findViewById(R.id.video_title);
    EsImageView localEsImageView = (EsImageView)localView.findViewById(R.id.video_image);
    String str1 = localApiaryVideoActivity.getDisplayName();
    String str2 = localApiaryVideoActivity.getImage();
    if ((str1 != null) && (!str1.equals("")))
    {
      localTextView.setVisibility(0);
      localTextView.setText(str1);
      if ((str2 == null) || (str2.equals("")))
        break label153;
      localEsImageView.setVisibility(0);
      localEsImageView.setUrl(str2);
    }
    while (true)
    {
      localView.setVisibility(0);
      invalidate();
      requestLayout();
      return;
      localTextView.setVisibility(8);
      break;
      label153: localEsImageView.setVisibility(8);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.VideoActivityPreviewView
 * JD-Core Version:    0.6.2
 */