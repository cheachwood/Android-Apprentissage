package com.google.android.apps.plus.content;

import android.text.TextUtils;
import com.google.android.apps.plus.util.ImageUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MediaImageRequest extends CachedImageRequest
{
  private static Matcher sCanonicalMatcher = Pattern.compile("http://images\\d+-focus-opensocial.googleusercontent.com/gadgets/proxy").matcher("");
  private String mCanonicalUrl;
  private final boolean mCropAndResize;
  private String mDownloadUrl;
  private int mHashCode;
  private final int mHeight;
  private final int mMediaType;
  private final String mUrl;
  private final int mWidth;

  public MediaImageRequest()
  {
    this(null, 0, 0, 0, false);
  }

  public MediaImageRequest(String paramString, int paramInt1, int paramInt2)
  {
    this(paramString, 3, paramInt2, paramInt2, true);
  }

  public MediaImageRequest(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    if (paramString == null)
      throw new NullPointerException();
    this.mUrl = paramString;
    this.mMediaType = paramInt1;
    this.mWidth = paramInt2;
    this.mHeight = paramInt3;
    this.mCropAndResize = paramBoolean;
  }

  public static boolean areCanonicallyEqual(MediaImageRequest paramMediaImageRequest1, MediaImageRequest paramMediaImageRequest2)
  {
    return TextUtils.equals(paramMediaImageRequest1.getCanonicalUrl(), paramMediaImageRequest2.getCanonicalUrl());
  }

  public static boolean areCanonicallyEqual(MediaImageRequest paramMediaImageRequest, String paramString)
  {
    return TextUtils.equals(paramMediaImageRequest.getCanonicalUrl(), canonicalize(paramString));
  }

  public static boolean areCanonicallyEqual(String paramString1, String paramString2)
  {
    return TextUtils.equals(canonicalize(paramString1), canonicalize(paramString2));
  }

  private static String canonicalize(String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    while (true)
    {
      return paramString;
      synchronized (sCanonicalMatcher)
      {
        sCanonicalMatcher.reset(paramString);
        paramString = sCanonicalMatcher.replaceFirst("http://images1-focus-opensocial.googleusercontent.com/gadgets/proxy");
      }
    }
  }

  private String getCanonicalUrl()
  {
    if (this.mCanonicalUrl == null)
      this.mCanonicalUrl = canonicalize(this.mUrl);
    return this.mCanonicalUrl;
  }

  private String getDownloadUrl(String paramString)
  {
    if (this.mDownloadUrl == null)
    {
      this.mDownloadUrl = paramString.replace("&google_plus:card_type=nonsquare", "").replace("&google_plus:widget", "");
      if ((this.mCropAndResize) && (this.mWidth != 0))
        if (this.mWidth != this.mHeight)
          break label106;
    }
    label106: for (this.mDownloadUrl = ImageUtils.getCroppedAndResizedUrl(this.mWidth, this.mDownloadUrl); ; this.mDownloadUrl = ImageUtils.getCenterCroppedAndResizedUrl(this.mWidth, this.mHeight, this.mDownloadUrl))
    {
      if (this.mDownloadUrl.startsWith("//"))
        this.mDownloadUrl = ("http:" + this.mDownloadUrl);
      return this.mDownloadUrl;
    }
  }

  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof MediaImageRequest;
    boolean bool2 = false;
    if (!bool1);
    while (true)
    {
      return bool2;
      MediaImageRequest localMediaImageRequest = (MediaImageRequest)paramObject;
      int i = this.mWidth;
      int j = localMediaImageRequest.mWidth;
      bool2 = false;
      if (i == j)
      {
        int k = this.mHeight;
        int m = localMediaImageRequest.mHeight;
        bool2 = false;
        if (k == m)
        {
          int n = this.mMediaType;
          int i1 = localMediaImageRequest.mMediaType;
          bool2 = false;
          if (n == i1)
          {
            boolean bool3 = areCanonicallyEqual(this, localMediaImageRequest);
            bool2 = false;
            if (bool3)
              bool2 = true;
          }
        }
      }
    }
  }

  protected final String getCacheFilePrefix()
  {
    return "M";
  }

  public final String getCanonicalDownloadUrl()
  {
    return getDownloadUrl(getCanonicalUrl());
  }

  public String getDownloadUrl()
  {
    return getDownloadUrl(this.mUrl);
  }

  public final int getHeight()
  {
    return this.mHeight;
  }

  public final int getMediaType()
  {
    return this.mMediaType;
  }

  public final String getUrl()
  {
    return this.mUrl;
  }

  public final int getWidth()
  {
    return this.mWidth;
  }

  public int hashCode()
  {
    String str;
    if (this.mHashCode == 0)
    {
      str = getCanonicalUrl();
      if (str == null)
        break label29;
    }
    label29: for (this.mHashCode = str.hashCode(); ; this.mHashCode = 1)
      return this.mHashCode;
  }

  public final boolean isEmpty()
  {
    if (this.mUrl == null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public String toString()
  {
    return "MediaImageRequest: type=" + this.mMediaType + " " + this.mUrl + " (" + this.mWidth + ", " + this.mHeight + ")";
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.MediaImageRequest
 * JD-Core Version:    0.6.2
 */