package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.network.ApiaryActivity;
import com.google.android.apps.plus.network.ApiarySkyjamActivity;

public class AudioActivityPreviewView extends ActivityPreviewView
{
  private static final int PREVIEW_AUDIO_LAYOUT = R.layout.share_preview_music_view;

  public AudioActivityPreviewView(Context paramContext)
  {
    super(paramContext, null, 0);
  }

  public AudioActivityPreviewView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 0);
  }

  public AudioActivityPreviewView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public void setActivity(ApiaryActivity paramApiaryActivity)
  {
    super.setActivity(paramApiaryActivity);
    ApiarySkyjamActivity localApiarySkyjamActivity = (ApiarySkyjamActivity)getActivity();
    removeAllViews();
    View localView = LayoutInflater.from(getContext()).inflate(PREVIEW_AUDIO_LAYOUT, null);
    addView(localView);
    EsImageView localEsImageView = (EsImageView)localView.findViewById(R.id.album);
    TextView localTextView1 = (TextView)localView.findViewById(R.id.skyjam_header);
    TextView localTextView2 = (TextView)localView.findViewById(R.id.skyjam_subheader1);
    TextView localTextView3 = (TextView)localView.findViewById(R.id.skyjam_subheader2);
    String str1 = localApiarySkyjamActivity.getArtistName();
    String str2 = localApiarySkyjamActivity.getTrackName();
    String str3 = localApiarySkyjamActivity.getAlbumName();
    String str4 = localApiarySkyjamActivity.getImage();
    if (str2 != null)
      if ((str2 != null) && (!str2.equals("")))
      {
        localTextView1.setVisibility(0);
        localTextView1.setText(str2);
        if ((str1 == null) || (str1.equals("")))
          break label286;
        localTextView2.setVisibility(0);
        localTextView2.setText(str1.toUpperCase());
        label173: if ((str3 == null) || (str3.equals("")))
          break label296;
        localTextView3.setVisibility(0);
        Context localContext = getContext();
        int i = R.string.skyjam_from_the_album;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = str3.toUpperCase();
        localTextView3.setText(localContext.getString(i, arrayOfObject));
        label234: if ((str4 == null) || (str4.equals("")))
          break label388;
        localEsImageView.setVisibility(0);
        localEsImageView.setUrl(str4);
      }
    while (true)
    {
      localView.setVisibility(0);
      invalidate();
      requestLayout();
      return;
      localTextView1.setVisibility(8);
      break;
      label286: localTextView2.setVisibility(8);
      break label173;
      label296: localTextView3.setVisibility(8);
      break label234;
      if ((str3 != null) && (!str3.equals("")))
      {
        localTextView1.setVisibility(0);
        localTextView1.setText(str3);
      }
      while (true)
      {
        if ((str1 == null) || (str1.equals("")))
          break label378;
        localTextView2.setVisibility(0);
        localTextView2.setText(str1.toUpperCase());
        break;
        localTextView1.setVisibility(8);
      }
      label378: localTextView2.setVisibility(8);
      break label234;
      label388: localEsImageView.setVisibility(8);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.AudioActivityPreviewView
 * JD-Core Version:    0.6.2
 */