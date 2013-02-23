package com.google.android.apps.plus.content;

import android.text.TextUtils;

public final class AvatarRequest extends ImageRequest
{
  private final String mAvatarUrl;
  private final String mContactLookupKey;
  private final String mGaiaId;
  private int mHashCode;
  private final int mIdType = 0;
  private final boolean mRounded;
  private final int mSize;

  public AvatarRequest()
  {
    this(null, 0);
  }

  public AvatarRequest(String paramString, int paramInt)
  {
    this(paramString, paramInt, false);
  }

  public AvatarRequest(String paramString, int paramInt, boolean paramBoolean)
  {
    this(paramString, null, paramInt, paramBoolean);
  }

  public AvatarRequest(String paramString1, String paramString2, int paramInt)
  {
    this(paramString1, paramString2, paramInt, false);
  }

  public AvatarRequest(String paramString1, String paramString2, int paramInt, boolean paramBoolean)
  {
    this.mGaiaId = paramString1;
    this.mAvatarUrl = paramString2;
    this.mContactLookupKey = null;
    this.mSize = paramInt;
    this.mRounded = paramBoolean;
  }

  public final boolean equals(Object paramObject)
  {
    boolean bool2;
    if (paramObject == this)
      bool2 = true;
    while (true)
    {
      return bool2;
      boolean bool1 = paramObject instanceof AvatarRequest;
      bool2 = false;
      if (bool1)
      {
        AvatarRequest localAvatarRequest = (AvatarRequest)paramObject;
        int i = this.mSize;
        int j = localAvatarRequest.mSize;
        bool2 = false;
        if (i == j)
        {
          boolean bool3 = this.mRounded;
          boolean bool4 = localAvatarRequest.mRounded;
          bool2 = false;
          if (bool3 == bool4)
            bool2 = TextUtils.equals(this.mGaiaId, localAvatarRequest.mGaiaId);
        }
      }
    }
  }

  public final String getAvatarUrl()
  {
    return this.mAvatarUrl;
  }

  public final String getGaiaId()
  {
    return this.mGaiaId;
  }

  public final int getSize()
  {
    return this.mSize;
  }

  public final String getUriForLogging()
  {
    return "avatar:" + this.mGaiaId + "/size=" + this.mSize;
  }

  public final int hashCode()
  {
    if (this.mHashCode == 0)
      if (this.mGaiaId == null)
        break label52;
    label52: for (this.mHashCode = (this.mGaiaId.hashCode() ^ this.mSize); ; this.mHashCode = this.mSize)
    {
      if (this.mRounded)
        this.mHashCode = (1 + this.mHashCode);
      return this.mHashCode;
    }
  }

  public final boolean isEmpty()
  {
    if (this.mGaiaId == null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isRounded()
  {
    return this.mRounded;
  }

  public final String toString()
  {
    int i = this.mSize;
    String str1 = null;
    switch (i)
    {
    default:
      if (!this.mRounded)
        break;
    case 0:
    case 1:
    case 2:
    }
    for (String str2 = "(rounded)"; ; str2 = "")
    {
      return "AvatarRequest: " + this.mGaiaId + " (" + str1 + ")" + str2;
      str1 = "tiny";
      break;
      str1 = "small";
      break;
      str1 = "medium";
      break;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.AvatarRequest
 * JD-Core Version:    0.6.2
 */