package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.network.ApiaryActivity;
import com.google.android.apps.plus.network.ApiaryPhotoAlbumActivity;
import java.util.Iterator;
import java.util.List;

public class PhotoAlbumActivityPreviewView extends ActivityPreviewView
{
  private static final int PREVIEW_PHOTOALBUM_LAYOUT = R.layout.share_preview_photoalbum_view;

  public PhotoAlbumActivityPreviewView(Context paramContext)
  {
    super(paramContext, null, 0);
  }

  public PhotoAlbumActivityPreviewView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet, 0);
  }

  public PhotoAlbumActivityPreviewView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public void setActivity(ApiaryActivity paramApiaryActivity)
  {
    super.setActivity(paramApiaryActivity);
    ApiaryPhotoAlbumActivity localApiaryPhotoAlbumActivity = (ApiaryPhotoAlbumActivity)getActivity();
    removeAllViews();
    View localView = LayoutInflater.from(getContext()).inflate(PREVIEW_PHOTOALBUM_LAYOUT, null);
    addView(localView);
    TextView localTextView = (TextView)localView.findViewById(R.id.photo_title);
    EsImageView[] arrayOfEsImageView = new EsImageView[3];
    arrayOfEsImageView[0] = ((EsImageView)localView.findViewById(R.id.photo_image1));
    arrayOfEsImageView[1] = ((EsImageView)localView.findViewById(R.id.photo_image2));
    arrayOfEsImageView[2] = ((EsImageView)localView.findViewById(R.id.photo_image3));
    List localList = localApiaryPhotoAlbumActivity.getImages();
    String str1 = localApiaryPhotoAlbumActivity.getDisplayName();
    if ((str1 != null) && (!str1.equals("")))
    {
      localTextView.setVisibility(0);
      localTextView.setText(str1);
    }
    while (true)
    {
      Iterator localIterator = localList.iterator();
      int i = arrayOfEsImageView.length;
      for (int j = 0; j < i; j++)
      {
        EsImageView localEsImageView = arrayOfEsImageView[j];
        localEsImageView.setVisibility(8);
        if (localIterator.hasNext())
        {
          String str2 = (String)localIterator.next();
          if (!str2.equals(""))
          {
            localEsImageView.setVisibility(0);
            localEsImageView.setUrl(str2);
          }
        }
      }
      localTextView.setVisibility(8);
    }
    localView.setVisibility(0);
    invalidate();
    requestLayout();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PhotoAlbumActivityPreviewView
 * JD-Core Version:    0.6.2
 */