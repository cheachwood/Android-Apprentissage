package com.google.android.apps.plus.network;

import com.google.android.apps.plus.util.StringUtils;
import com.google.api.services.plusi.model.MediaItem;
import com.google.api.services.plusi.model.MediaLayout;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ApiarySkyjamActivity extends ApiaryActivity
{
  private String mAlbumName;
  private String mArtistName;
  private String mImage;
  private String mTrackName;

  public final String getAlbumName()
  {
    return this.mAlbumName;
  }

  public final String getArtistName()
  {
    return this.mArtistName;
  }

  public final String getImage()
  {
    return this.mImage;
  }

  public final String getTrackName()
  {
    return this.mTrackName;
  }

  public final ApiaryActivity.Type getType()
  {
    return ApiaryActivity.Type.AUDIO;
  }

  protected final void update(MediaLayout paramMediaLayout)
    throws IOException
  {
    super.update(paramMediaLayout);
    Iterator localIterator = paramMediaLayout.media.iterator();
    while (localIterator.hasNext())
    {
      MediaItem localMediaItem = (MediaItem)localIterator.next();
      if (localMediaItem.albumArtistHtml != null)
      {
        this.mImage = localMediaItem.thumbnailUrl;
        this.mTrackName = StringUtils.unescape(localMediaItem.caption);
        this.mAlbumName = StringUtils.unescape(localMediaItem.albumHtml);
        this.mArtistName = StringUtils.unescape(localMediaItem.albumArtistHtml);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.ApiarySkyjamActivity
 * JD-Core Version:    0.6.2
 */