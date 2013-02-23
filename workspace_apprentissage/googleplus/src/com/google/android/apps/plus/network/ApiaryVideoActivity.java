package com.google.android.apps.plus.network;

import com.google.android.apps.plus.util.StringUtils;
import com.google.api.services.plusi.model.MediaItem;
import com.google.api.services.plusi.model.MediaLayout;
import java.io.IOException;
import java.util.List;

public class ApiaryVideoActivity extends ApiaryActivity
{
  private String mDisplayName;
  private String mImage;

  public final String getDisplayName()
  {
    return this.mDisplayName;
  }

  public final String getImage()
  {
    return this.mImage;
  }

  public final ApiaryActivity.Type getType()
  {
    return ApiaryActivity.Type.VIDEO;
  }

  protected final void update(MediaLayout paramMediaLayout)
    throws IOException
  {
    super.update(paramMediaLayout);
    this.mDisplayName = null;
    this.mImage = null;
    List localList = paramMediaLayout.media;
    if ((localList == null) || (localList.isEmpty()))
      throw new IOException("empty media item");
    MediaItem localMediaItem = (MediaItem)localList.get(0);
    if (localMediaItem.thumbnailUrl == null)
      throw new IOException("missing image object");
    this.mImage = ("https:" + localMediaItem.thumbnailUrl);
    this.mDisplayName = StringUtils.unescape(paramMediaLayout.title);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.ApiaryVideoActivity
 * JD-Core Version:    0.6.2
 */