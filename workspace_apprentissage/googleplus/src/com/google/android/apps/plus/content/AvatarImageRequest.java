package com.google.android.apps.plus.content;

import android.text.TextUtils;
import com.google.android.apps.plus.util.ImageUtils;

public final class AvatarImageRequest extends CachedImageRequest
{
  private String mDownloadUrl;
  private final String mGaiaId;
  private int mHashCode;
  private final int mSize;
  private final int mSizeInPx;
  private final String mUrl;

  public AvatarImageRequest(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    this.mGaiaId = paramString1;
    this.mUrl = paramString2;
    this.mSize = paramInt1;
    this.mSizeInPx = paramInt2;
  }

  public final boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof AvatarImageRequest;
    boolean bool2 = false;
    if (!bool1);
    while (true)
    {
      return bool2;
      AvatarImageRequest localAvatarImageRequest = (AvatarImageRequest)paramObject;
      int i = this.mSize;
      int j = localAvatarImageRequest.mSize;
      bool2 = false;
      if (i == j)
      {
        boolean bool3 = TextUtils.equals(this.mUrl, localAvatarImageRequest.mUrl);
        bool2 = false;
        if (bool3)
          bool2 = true;
      }
    }
  }

  protected final String getCacheFilePrefix()
  {
    String str;
    switch (this.mSize)
    {
    default:
      str = null;
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      return str;
      str = "AT";
      continue;
      str = "AS";
      continue;
      str = "AM";
    }
  }

  public final String getCanonicalDownloadUrl()
  {
    return getDownloadUrl();
  }

  public final String getDownloadUrl()
  {
    if (this.mDownloadUrl == null)
      this.mDownloadUrl = ImageUtils.getCroppedAndResizedUrl(this.mSizeInPx, this.mUrl);
    return this.mDownloadUrl;
  }

  public final String getGaiaId()
  {
    return this.mGaiaId;
  }

  public final String getUriForLogging()
  {
    return "avatar:" + this.mGaiaId + "/size=" + this.mSize;
  }

  public final int hashCode()
  {
    if (this.mHashCode == 0)
      if (this.mUrl == null)
        break label46;
    label46: for (this.mHashCode = this.mUrl.hashCode(); ; this.mHashCode = 1)
    {
      this.mHashCode = (31 * this.mHashCode + this.mSize);
      return this.mHashCode;
    }
  }

  public final boolean isEmpty()
  {
    return false;
  }

  public final String toString()
  {
    int i = this.mSize;
    String str = null;
    switch (i)
    {
    default:
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      return "AvatarImageRequest: " + this.mGaiaId + " (" + str + ")";
      str = "tiny";
      continue;
      str = "small";
      continue;
      str = "medium";
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.AvatarImageRequest
 * JD-Core Version:    0.6.2
 */