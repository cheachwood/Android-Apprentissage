package com.google.android.apps.plus.content;

public abstract class CachedImageRequest extends ImageRequest
{
  private String mCacheDir;
  private String mCacheFileName;

  private void buildCacheFilePath()
  {
    String str = getCanonicalDownloadUrl();
    long l = 1125899906842597L;
    int i = str.length();
    for (int j = 0; j < i; j++)
      l = 31L * l + str.charAt(j);
    this.mCacheDir = Integer.toHexString(i % 16);
    this.mCacheFileName = (getCacheFilePrefix() + Long.toHexString(0xFFFFFFFF & l >> 4));
  }

  public final String getCacheDir()
  {
    if (this.mCacheDir == null)
      buildCacheFilePath();
    return this.mCacheDir;
  }

  public final String getCacheFileName()
  {
    if (this.mCacheFileName == null)
      buildCacheFilePath();
    return this.mCacheFileName;
  }

  protected abstract String getCacheFilePrefix();

  public abstract String getCanonicalDownloadUrl();

  public abstract String getDownloadUrl();

  public String getUriForLogging()
  {
    return getDownloadUrl();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.CachedImageRequest
 * JD-Core Version:    0.6.2
 */