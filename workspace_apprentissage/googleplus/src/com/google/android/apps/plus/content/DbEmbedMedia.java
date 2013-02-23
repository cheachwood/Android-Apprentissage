package com.google.android.apps.plus.content;

import android.text.TextUtils;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.util.PrimitiveUtils;
import com.google.api.services.plusi.model.ImageObject;
import com.google.api.services.plusi.model.PlusPhoto;
import com.google.api.services.plusi.model.PlusPhotoAlbum;
import com.google.api.services.plusi.model.Thing;
import com.google.api.services.plusi.model.VideoObject;
import com.google.api.services.plusi.model.WebPage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

public final class DbEmbedMedia extends DbSerializer
{
  protected String mAlbumId;
  protected String mContentUrl;
  protected String mDescription;
  protected short mHeight;
  protected String mImageUrl;
  protected boolean mIsAlbum;
  protected boolean mIsPanorama;
  protected boolean mIsVideo;
  protected String mOwnerId;
  protected long mPhotoId;
  protected String mTitle;
  protected short mWidth;

  protected DbEmbedMedia()
  {
  }

  public DbEmbedMedia(PlusPhoto paramPlusPhoto)
  {
    initPlusPhoto(paramPlusPhoto);
  }

  public DbEmbedMedia(PlusPhotoAlbum paramPlusPhotoAlbum)
  {
    int i;
    if (paramPlusPhotoAlbum.associatedMedia != null)
    {
      i = paramPlusPhotoAlbum.associatedMedia.size();
      if (i > 0)
        initPlusPhoto((PlusPhoto)paramPlusPhotoAlbum.associatedMedia.get(0));
      if (i <= 1)
        break label60;
    }
    label60: for (boolean bool = true; ; bool = false)
    {
      this.mIsAlbum = bool;
      return;
      i = 0;
      break;
    }
  }

  public DbEmbedMedia(Thing paramThing)
  {
    if (TextUtils.isEmpty(paramThing.name))
      this.mTitle = paramThing.description;
    while (true)
    {
      this.mContentUrl = paramThing.url;
      this.mImageUrl = paramThing.imageUrl;
      return;
      this.mTitle = paramThing.name;
      this.mDescription = paramThing.description;
    }
  }

  public DbEmbedMedia(VideoObject paramVideoObject)
  {
    this.mContentUrl = paramVideoObject.url;
    this.mImageUrl = paramVideoObject.thumbnailUrl;
    this.mWidth = ((short)PrimitiveUtils.safeInt(paramVideoObject.widthPx));
    this.mHeight = ((short)PrimitiveUtils.safeInt(paramVideoObject.heightPx));
    if (!TextUtils.isEmpty(paramVideoObject.name))
    {
      this.mTitle = paramVideoObject.name;
      this.mDescription = paramVideoObject.description;
    }
    while (true)
    {
      this.mIsVideo = true;
      return;
      this.mTitle = paramVideoObject.description;
    }
  }

  public DbEmbedMedia(WebPage paramWebPage)
  {
    this.mTitle = paramWebPage.name;
    this.mDescription = paramWebPage.description;
    this.mContentUrl = paramWebPage.url;
    this.mImageUrl = paramWebPage.imageUrl;
  }

  public static DbEmbedMedia deserialize(byte[] paramArrayOfByte)
  {
    int i = 1;
    if (paramArrayOfByte == null)
    {
      localDbEmbedMedia = null;
      return localDbEmbedMedia;
    }
    ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
    DbEmbedMedia localDbEmbedMedia = new DbEmbedMedia();
    localDbEmbedMedia.mTitle = getShortString(localByteBuffer);
    localDbEmbedMedia.mDescription = getShortString(localByteBuffer);
    localDbEmbedMedia.mContentUrl = getShortString(localByteBuffer);
    localDbEmbedMedia.mImageUrl = getShortString(localByteBuffer);
    localDbEmbedMedia.mOwnerId = getShortString(localByteBuffer);
    localDbEmbedMedia.mAlbumId = getShortString(localByteBuffer);
    localDbEmbedMedia.mPhotoId = localByteBuffer.getLong();
    localDbEmbedMedia.mWidth = localByteBuffer.getShort();
    localDbEmbedMedia.mHeight = localByteBuffer.getShort();
    int j;
    label106: int k;
    if (localByteBuffer.get() == i)
    {
      j = i;
      localDbEmbedMedia.mIsPanorama = j;
      if (localByteBuffer.get() != i)
        break label151;
      k = i;
      label123: localDbEmbedMedia.mIsVideo = k;
      if (localByteBuffer.get() != i)
        break label157;
    }
    while (true)
    {
      localDbEmbedMedia.mIsAlbum = i;
      break;
      j = 0;
      break label106;
      label151: k = 0;
      break label123;
      label157: i = 0;
    }
  }

  private void initPlusPhoto(PlusPhoto paramPlusPhoto)
  {
    this.mImageUrl = paramPlusPhoto.originalMediaPlayerUrl;
    this.mOwnerId = paramPlusPhoto.ownerObfuscatedId;
    this.mAlbumId = paramPlusPhoto.albumId;
    String str = paramPlusPhoto.photoId;
    long l;
    if (str == null)
    {
      l = 0L;
      this.mPhotoId = l;
      if (paramPlusPhoto.thumbnail != null)
      {
        this.mWidth = ((short)PrimitiveUtils.safeInt(paramPlusPhoto.thumbnail.widthPx));
        this.mHeight = ((short)PrimitiveUtils.safeInt(paramPlusPhoto.thumbnail.heightPx));
      }
      if ((!PrimitiveUtils.safeBoolean(paramPlusPhoto.isVideo)) || (TextUtils.isEmpty(paramPlusPhoto.originalContentUrl)))
        break label146;
    }
    label146: for (boolean bool = true; ; bool = false)
    {
      this.mIsVideo = bool;
      this.mIsPanorama = "PHOTOSPHERE".equals(paramPlusPhoto.mediaType);
      if (this.mIsVideo)
        this.mContentUrl = paramPlusPhoto.originalContentUrl;
      return;
      l = PrimitiveUtils.safeLong(Long.valueOf(str));
      break;
    }
  }

  public static byte[] serialize(DbEmbedMedia paramDbEmbedMedia)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(64);
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    putShortString(localDataOutputStream, paramDbEmbedMedia.mTitle);
    putShortString(localDataOutputStream, paramDbEmbedMedia.mDescription);
    putShortString(localDataOutputStream, paramDbEmbedMedia.mContentUrl);
    putShortString(localDataOutputStream, paramDbEmbedMedia.mImageUrl);
    putShortString(localDataOutputStream, paramDbEmbedMedia.mOwnerId);
    putShortString(localDataOutputStream, paramDbEmbedMedia.mAlbumId);
    localDataOutputStream.writeLong(paramDbEmbedMedia.mPhotoId);
    localDataOutputStream.writeShort(paramDbEmbedMedia.mWidth);
    localDataOutputStream.writeShort(paramDbEmbedMedia.mHeight);
    localDataOutputStream.writeBoolean(paramDbEmbedMedia.mIsPanorama);
    localDataOutputStream.writeBoolean(paramDbEmbedMedia.mIsVideo);
    localDataOutputStream.writeBoolean(paramDbEmbedMedia.mIsAlbum);
    byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
    localDataOutputStream.close();
    return arrayOfByte;
  }

  public final String getAlbumId()
  {
    return this.mAlbumId;
  }

  public final String getContentUrl()
  {
    if (this.mIsVideo);
    for (String str = null; ; str = this.mContentUrl)
      return str;
  }

  public final String getDescription()
  {
    return this.mDescription;
  }

  public final short getHeight()
  {
    return this.mHeight;
  }

  public final String getImageUrl()
  {
    return this.mImageUrl;
  }

  public final MediaRef.MediaType getMediaType()
  {
    MediaRef.MediaType localMediaType;
    if (this.mIsVideo)
      localMediaType = MediaRef.MediaType.VIDEO;
    while (true)
    {
      return localMediaType;
      if (this.mIsPanorama)
        localMediaType = MediaRef.MediaType.PANORAMA;
      else
        localMediaType = MediaRef.MediaType.IMAGE;
    }
  }

  public final String getOwnerId()
  {
    return this.mOwnerId;
  }

  public final long getPhotoId()
  {
    return this.mPhotoId;
  }

  public final String getTitle()
  {
    return this.mTitle;
  }

  public final String getVideoUrl()
  {
    if (this.mIsVideo);
    for (String str = this.mContentUrl; ; str = null)
      return str;
  }

  public final short getWidth()
  {
    return this.mWidth;
  }

  public final boolean isAlbum()
  {
    return this.mIsAlbum;
  }

  public final boolean isPanorama()
  {
    return this.mIsPanorama;
  }

  public final boolean isVideo()
  {
    return this.mIsVideo;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.DbEmbedMedia
 * JD-Core Version:    0.6.2
 */