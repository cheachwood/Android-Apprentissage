package com.google.android.apps.plus.network;

import android.os.Bundle;
import android.util.Log;
import com.google.android.apps.plus.api.CallToActionData;
import com.google.android.apps.plus.util.EsLog;
import com.google.api.services.plusi.model.LinkPreviewResponse;
import com.google.api.services.plusi.model.LinkPreviewResponseJson;
import com.google.api.services.plusi.model.MediaLayout;
import java.io.IOException;
import java.util.List;

public final class ApiaryActivityFactory
{
  public static ApiaryActivity getApiaryActivity(Bundle paramBundle, CallToActionData paramCallToActionData)
  {
    if (paramBundle == null)
      throw new NullPointerException("Content deep-link metadata must not be null.");
    if (EsLog.isLoggable("ApiaryActivityFactory", 3))
      Log.d("ApiaryActivityFactory", paramBundle.toString());
    ApiaryActivity localApiaryActivity = new ApiaryActivity();
    try
    {
      localApiaryActivity.setContentDeepLinkMetadata(paramBundle);
      if (paramCallToActionData != null)
        localApiaryActivity.setCallToActionMetadata(paramCallToActionData);
      return localApiaryActivity;
    }
    catch (IOException localIOException)
    {
      while (true)
        localApiaryActivity = null;
    }
  }

  public static ApiaryActivity getApiaryActivity(LinkPreviewResponse paramLinkPreviewResponse)
  {
    if (paramLinkPreviewResponse == null)
      throw new NullPointerException("");
    if (EsLog.isLoggable("ApiaryActivityFactory", 3))
      Log.d("ApiaryActivityFactory", LinkPreviewResponseJson.getInstance().toPrettyString(paramLinkPreviewResponse));
    if ((paramLinkPreviewResponse.mediaLayout == null) || (paramLinkPreviewResponse.mediaLayout.isEmpty()))
      throw new IllegalArgumentException("Media layout must be specified");
    MediaLayout localMediaLayout = (MediaLayout)paramLinkPreviewResponse.mediaLayout.get(0);
    Object localObject;
    if ("WEBPAGE".equals(localMediaLayout.layoutType))
      localObject = new ApiaryArticleActivity();
    try
    {
      ((ApiaryActivity)localObject).setLinkPreview(paramLinkPreviewResponse);
      while (true)
      {
        return localObject;
        if ("VIDEO".equals(localMediaLayout.layoutType))
        {
          localObject = new ApiaryVideoActivity();
          break;
        }
        if ("SKYJAM_PREVIEW".equals(localMediaLayout.layoutType))
        {
          localObject = new ApiarySkyjamActivity();
          break;
        }
        if ("SKYJAM_PREVIEW_ALBUM".equals(localMediaLayout.layoutType))
        {
          localObject = new ApiarySkyjamActivity();
          break;
        }
        if ("IMAGE".equals(localMediaLayout.layoutType))
        {
          localObject = new ApiaryPhotoAlbumActivity();
          break;
        }
        localObject = null;
      }
    }
    catch (IOException localIOException)
    {
      while (true)
        localObject = null;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.ApiaryActivityFactory
 * JD-Core Version:    0.6.2
 */