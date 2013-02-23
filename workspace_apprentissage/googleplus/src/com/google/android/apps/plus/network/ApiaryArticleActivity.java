package com.google.android.apps.plus.network;

import android.text.TextUtils;
import com.google.android.apps.plus.util.StringUtils;
import com.google.api.services.plusi.model.MediaItem;
import com.google.api.services.plusi.model.MediaLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApiaryArticleActivity extends ApiaryActivity
{
  private String mContent;
  private String mDisplayName;
  private String mFavIconUrl;
  private List<String> mImageList = new ArrayList();

  public final String getContent()
  {
    return this.mContent;
  }

  public final String getDisplayName()
  {
    return this.mDisplayName;
  }

  public final String getFavIconUrl()
  {
    return this.mFavIconUrl;
  }

  public final List<String> getImages()
  {
    return Collections.unmodifiableList(this.mImageList);
  }

  public final ApiaryActivity.Type getType()
  {
    return ApiaryActivity.Type.ARTICLE;
  }

  protected final void update(MediaLayout paramMediaLayout)
    throws IOException
  {
    super.update(paramMediaLayout);
    this.mDisplayName = null;
    this.mContent = null;
    this.mImageList.clear();
    List localList = paramMediaLayout.media;
    if ((localList != null) && (!localList.isEmpty()))
    {
      MediaItem localMediaItem = (MediaItem)localList.get(0);
      this.mImageList.add("https:" + localMediaItem.thumbnailUrl);
    }
    this.mDisplayName = StringUtils.unescape(paramMediaLayout.title);
    this.mFavIconUrl = paramMediaLayout.faviconUrl;
    if (!TextUtils.isEmpty(this.mFavIconUrl))
      this.mFavIconUrl = ("https:" + this.mFavIconUrl);
    this.mContent = StringUtils.unescape(paramMediaLayout.description);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.ApiaryArticleActivity
 * JD-Core Version:    0.6.2
 */