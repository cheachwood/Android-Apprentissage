package com.google.android.apps.plus.content;

import android.content.Context;
import com.google.android.apps.plus.phone.ScreenMetrics;
import com.google.android.apps.plus.util.ImageUtils;

public final class EventThemePlaceholderRequest extends CachedImageRequest
{
  private String mDownloadUrl;
  private String mUrl;

  public EventThemePlaceholderRequest(String paramString, Context paramContext)
  {
    this.mUrl = paramString;
    int i = Math.round(0.25F * ScreenMetrics.getInstance(paramContext).shortDimension);
    this.mDownloadUrl = ImageUtils.getResizedUrl(i, Math.round(i / 3.36F), this.mUrl);
  }

  protected final String getCacheFilePrefix()
  {
    return null;
  }

  public final String getCanonicalDownloadUrl()
  {
    return this.mDownloadUrl;
  }

  public final String getDownloadUrl()
  {
    return this.mDownloadUrl;
  }

  public final boolean isEmpty()
  {
    return false;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EventThemePlaceholderRequest
 * JD-Core Version:    0.6.2
 */