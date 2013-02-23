package com.google.android.apps.plus.network;

import com.google.android.apps.plus.util.StringUtils;
import com.google.api.services.plusi.model.MediaItem;
import com.google.api.services.plusi.model.MediaLayout;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ApiaryPhotoAlbumActivity extends ApiaryActivity
{
  private String mDisplayName;
  private LinkedList<String> mImageList = new LinkedList();

  public final String getDisplayName()
  {
    return this.mDisplayName;
  }

  public final List<String> getImages()
  {
    return Collections.unmodifiableList(this.mImageList);
  }

  public final ApiaryActivity.Type getType()
  {
    return ApiaryActivity.Type.PHOTOALBUM;
  }

  protected final void update(MediaLayout paramMediaLayout)
    throws IOException
  {
    super.update(paramMediaLayout);
    this.mDisplayName = null;
    this.mImageList.clear();
    List localList = paramMediaLayout.media;
    if ((localList == null) || (localList.isEmpty()))
      throw new IOException("empty media item");
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      MediaItem localMediaItem = (MediaItem)localIterator.next();
      this.mImageList.add(localMediaItem.thumbnailUrl);
    }
    this.mDisplayName = StringUtils.unescape(paramMediaLayout.title);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.ApiaryPhotoAlbumActivity
 * JD-Core Version:    0.6.2
 */