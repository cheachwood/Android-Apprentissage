package com.google.android.apps.plus.content;

import android.net.Uri;
import com.google.android.apps.plus.api.MediaRef;

public final class LocalImageRequest extends ImageRequest
{
  private int mHashCode;
  private final int mHeight;
  private final Uri mUri;
  private final int mWidth;

  public LocalImageRequest(MediaRef paramMediaRef, int paramInt1, int paramInt2)
  {
    if ((paramMediaRef == null) || (!paramMediaRef.hasLocalUri()))
      throw new IllegalArgumentException("MediaRef must have a local URI");
    this.mUri = paramMediaRef.getLocalUri();
    this.mWidth = paramInt1;
    this.mHeight = paramInt2;
  }

  public final boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this);
    while (true)
    {
      return bool;
      if (!(paramObject instanceof LocalImageRequest))
      {
        bool = false;
      }
      else
      {
        LocalImageRequest localLocalImageRequest = (LocalImageRequest)paramObject;
        if ((!this.mUri.equals(localLocalImageRequest.mUri)) || (this.mWidth != localLocalImageRequest.mWidth) || (this.mHeight != localLocalImageRequest.mHeight))
          bool = false;
      }
    }
  }

  public final int getHeight()
  {
    return this.mHeight;
  }

  public final Uri getUri()
  {
    return this.mUri;
  }

  public final String getUriForLogging()
  {
    return this.mUri.toString();
  }

  public final int getWidth()
  {
    return this.mWidth;
  }

  public final int hashCode()
  {
    if (this.mHashCode == 0)
      this.mHashCode = (31 * (31 * (527 + this.mUri.hashCode()) + this.mWidth) + this.mHeight);
    return this.mHashCode;
  }

  public final boolean isEmpty()
  {
    if (this.mUri == null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final String toString()
  {
    return "LocalImageRequest: " + this.mUri.toString() + " (" + this.mWidth + ", " + this.mHeight + ")";
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.LocalImageRequest
 * JD-Core Version:    0.6.2
 */