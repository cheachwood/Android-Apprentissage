package com.google.android.apps.plus.content;

import android.text.TextUtils;
import com.google.api.services.plusi.model.MusicGroup;
import com.google.api.services.plusi.model.PlayMusicAlbum;
import com.google.api.services.plusi.model.PlayMusicTrack;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public final class DbEmbedSkyjam extends DbSerializer
{
  protected String mAlbum;
  protected String mArtist;
  protected String mImageUrl;
  protected String mMarketUrl;
  protected String mPreviewUrl;
  protected String mSong;

  protected DbEmbedSkyjam()
  {
  }

  public DbEmbedSkyjam(PlayMusicAlbum paramPlayMusicAlbum)
  {
    this.mArtist = paramPlayMusicAlbum.byArtist.name;
    this.mAlbum = paramPlayMusicAlbum.name;
    this.mImageUrl = paramPlayMusicAlbum.imageUrl;
    this.mMarketUrl = paramPlayMusicAlbum.offerUrlWithSessionIndex;
    this.mPreviewUrl = paramPlayMusicAlbum.audioUrlWithSessionIndex;
  }

  public DbEmbedSkyjam(PlayMusicTrack paramPlayMusicTrack)
  {
    this.mSong = paramPlayMusicTrack.name;
    this.mArtist = paramPlayMusicTrack.byArtist.name;
    this.mAlbum = paramPlayMusicTrack.inAlbum.name;
    this.mImageUrl = paramPlayMusicTrack.inAlbum.imageUrl;
    this.mMarketUrl = paramPlayMusicTrack.offerUrlWithSessionIndex;
    this.mPreviewUrl = paramPlayMusicTrack.audioEmbedUrlWithSessionIndex;
  }

  public static DbEmbedSkyjam deserialize(byte[] paramArrayOfByte)
  {
    DbEmbedSkyjam localDbEmbedSkyjam;
    if (paramArrayOfByte == null)
      localDbEmbedSkyjam = null;
    while (true)
    {
      return localDbEmbedSkyjam;
      ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
      localDbEmbedSkyjam = new DbEmbedSkyjam();
      localDbEmbedSkyjam.mSong = getShortString(localByteBuffer);
      localDbEmbedSkyjam.mArtist = getShortString(localByteBuffer);
      localDbEmbedSkyjam.mAlbum = getShortString(localByteBuffer);
      localDbEmbedSkyjam.mImageUrl = getShortString(localByteBuffer);
      localDbEmbedSkyjam.mMarketUrl = getShortString(localByteBuffer);
      localDbEmbedSkyjam.mPreviewUrl = getShortString(localByteBuffer);
    }
  }

  private static byte[] serialize(DbEmbedSkyjam paramDbEmbedSkyjam)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(256);
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    putShortString(localDataOutputStream, paramDbEmbedSkyjam.mSong);
    putShortString(localDataOutputStream, paramDbEmbedSkyjam.mArtist);
    putShortString(localDataOutputStream, paramDbEmbedSkyjam.mAlbum);
    putShortString(localDataOutputStream, paramDbEmbedSkyjam.mImageUrl);
    putShortString(localDataOutputStream, paramDbEmbedSkyjam.mMarketUrl);
    putShortString(localDataOutputStream, paramDbEmbedSkyjam.mPreviewUrl);
    byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
    localDataOutputStream.close();
    return arrayOfByte;
  }

  public static byte[] serialize(PlayMusicAlbum paramPlayMusicAlbum)
    throws IOException
  {
    return serialize(new DbEmbedSkyjam(paramPlayMusicAlbum));
  }

  public static byte[] serialize(PlayMusicTrack paramPlayMusicTrack)
    throws IOException
  {
    return serialize(new DbEmbedSkyjam(paramPlayMusicTrack));
  }

  public final String getAlbum()
  {
    return this.mAlbum;
  }

  public final String getArtist()
  {
    return this.mArtist;
  }

  public final String getImageUrl()
  {
    return this.mImageUrl;
  }

  public final String getMarketUrl()
  {
    return this.mMarketUrl;
  }

  public final String getPreviewUrl()
  {
    return this.mPreviewUrl;
  }

  public final String getSong()
  {
    return this.mSong;
  }

  public final boolean isAlbum()
  {
    return TextUtils.isEmpty(this.mSong);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.DbEmbedSkyjam
 * JD-Core Version:    0.6.2
 */