package com.google.android.apps.plus.content;

import android.text.TextUtils;
import com.google.android.apps.plus.api.ApiUtils;
import com.google.api.services.plusi.model.EmbedsSquare;
import com.google.api.services.plusi.model.SquareInvite;
import com.google.api.services.plusi.model.SquareUpdate;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public final class DbEmbedSquare extends DbSerializer
{
  protected String mAboutSquareId;
  protected String mAboutSquareName;
  protected String mImageUrl;
  protected boolean mIsInvitation;
  protected String mSquareId;
  protected String mSquareName;
  protected String mSquareStreamId;
  protected String mSquareStreamName;

  protected DbEmbedSquare()
  {
  }

  private DbEmbedSquare(SquareUpdate paramSquareUpdate)
  {
    if (paramSquareUpdate != null)
    {
      this.mSquareId = paramSquareUpdate.obfuscatedSquareId;
      this.mSquareName = paramSquareUpdate.squareName;
      this.mSquareStreamId = paramSquareUpdate.squareStreamId;
      this.mSquareStreamName = paramSquareUpdate.squareStreamName;
    }
  }

  private DbEmbedSquare(SquareUpdate paramSquareUpdate, EmbedsSquare paramEmbedsSquare)
  {
    this(paramSquareUpdate);
    this.mAboutSquareId = resolveSquareId(paramEmbedsSquare.communityId, paramEmbedsSquare.url);
    this.mAboutSquareName = paramEmbedsSquare.name;
    this.mImageUrl = ApiUtils.prependProtocol(paramEmbedsSquare.imageUrl);
  }

  private DbEmbedSquare(SquareUpdate paramSquareUpdate, SquareInvite paramSquareInvite)
  {
    this(paramSquareUpdate);
    this.mAboutSquareId = resolveSquareId(paramSquareInvite.communityId, paramSquareInvite.url);
    this.mAboutSquareName = paramSquareInvite.name;
    this.mImageUrl = ApiUtils.prependProtocol(paramSquareInvite.imageUrl);
    this.mIsInvitation = true;
  }

  public static DbEmbedSquare deserialize(byte[] paramArrayOfByte)
  {
    int i = 1;
    if (paramArrayOfByte == null)
    {
      localDbEmbedSquare = null;
      return localDbEmbedSquare;
    }
    ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
    DbEmbedSquare localDbEmbedSquare = new DbEmbedSquare();
    localDbEmbedSquare.mSquareId = getShortString(localByteBuffer);
    localDbEmbedSquare.mSquareName = getShortString(localByteBuffer);
    localDbEmbedSquare.mSquareStreamId = getShortString(localByteBuffer);
    localDbEmbedSquare.mSquareStreamName = getShortString(localByteBuffer);
    localDbEmbedSquare.mAboutSquareId = getShortString(localByteBuffer);
    localDbEmbedSquare.mAboutSquareName = getShortString(localByteBuffer);
    localDbEmbedSquare.mImageUrl = getShortString(localByteBuffer);
    if (localByteBuffer.get() == i);
    while (true)
    {
      localDbEmbedSquare.mIsInvitation = i;
      break;
      i = 0;
    }
  }

  private static String resolveSquareId(String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramString1));
    while (true)
    {
      return paramString1;
      if ((paramString2 != null) && (paramString2.startsWith("communities/")))
        paramString1 = paramString2.substring(12);
      else
        paramString1 = null;
    }
  }

  private static byte[] serialize(DbEmbedSquare paramDbEmbedSquare)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(128);
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    putShortString(localDataOutputStream, paramDbEmbedSquare.mSquareId);
    putShortString(localDataOutputStream, paramDbEmbedSquare.mSquareName);
    putShortString(localDataOutputStream, paramDbEmbedSquare.mSquareStreamId);
    putShortString(localDataOutputStream, paramDbEmbedSquare.mSquareStreamName);
    putShortString(localDataOutputStream, paramDbEmbedSquare.mAboutSquareId);
    putShortString(localDataOutputStream, paramDbEmbedSquare.mAboutSquareName);
    putShortString(localDataOutputStream, paramDbEmbedSquare.mImageUrl);
    localDataOutputStream.writeBoolean(paramDbEmbedSquare.mIsInvitation);
    byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
    localDataOutputStream.close();
    return arrayOfByte;
  }

  public static byte[] serialize(SquareUpdate paramSquareUpdate)
    throws IOException
  {
    return serialize(new DbEmbedSquare(paramSquareUpdate));
  }

  public static byte[] serialize(SquareUpdate paramSquareUpdate, EmbedsSquare paramEmbedsSquare)
    throws IOException
  {
    return serialize(new DbEmbedSquare(paramSquareUpdate, paramEmbedsSquare));
  }

  public static byte[] serialize(SquareUpdate paramSquareUpdate, SquareInvite paramSquareInvite)
    throws IOException
  {
    return serialize(new DbEmbedSquare(paramSquareUpdate, paramSquareInvite));
  }

  public final String getAboutSquareId()
  {
    return this.mAboutSquareId;
  }

  public final String getAboutSquareName()
  {
    return this.mAboutSquareName;
  }

  public final String getImageUrl()
  {
    return this.mImageUrl;
  }

  public final String getSquareId()
  {
    return this.mSquareId;
  }

  public final String getSquareName()
  {
    return this.mSquareName;
  }

  public final String getSquareStreamId()
  {
    return this.mSquareStreamId;
  }

  public final String getSquareStreamName()
  {
    return this.mSquareStreamName;
  }

  public final boolean isInvitation()
  {
    return this.mIsInvitation;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.DbEmbedSquare
 * JD-Core Version:    0.6.2
 */