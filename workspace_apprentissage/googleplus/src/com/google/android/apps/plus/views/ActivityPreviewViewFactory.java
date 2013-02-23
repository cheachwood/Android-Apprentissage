package com.google.android.apps.plus.views;

import android.content.Context;
import com.google.android.apps.plus.network.ApiaryActivity;

public final class ActivityPreviewViewFactory
{
  public static ActivityPreviewView createViewFromActivity(Context paramContext, ApiaryActivity paramApiaryActivity)
  {
    Object localObject;
    if (paramApiaryActivity == null)
      localObject = null;
    label126: 
    while (true)
    {
      return localObject;
      int i = 1.$SwitchMap$com$google$android$apps$plus$network$ApiaryActivity$Type[paramApiaryActivity.getType().ordinal()];
      localObject = null;
      switch (i)
      {
      default:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      }
      while (true)
      {
        if (localObject == null)
          break label126;
        ((ActivityPreviewView)localObject).setActivity(paramApiaryActivity);
        break;
        localObject = new VideoActivityPreviewView(paramContext);
        continue;
        localObject = new AudioActivityPreviewView(paramContext);
        continue;
        localObject = new ArticleActivityPreviewView(paramContext);
        continue;
        localObject = new PhotoAlbumActivityPreviewView(paramContext);
        continue;
        localObject = new ThingActivityPreviewView(paramContext);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ActivityPreviewViewFactory
 * JD-Core Version:    0.6.2
 */