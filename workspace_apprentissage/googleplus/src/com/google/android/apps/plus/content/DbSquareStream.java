package com.google.android.apps.plus.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class DbSquareStream extends DbSerializer
  implements Parcelable
{
  public static final Parcelable.Creator<DbSquareStream> CREATOR = new Parcelable.Creator()
  {
  };
  private final String mDescription;
  private final String mId;
  private final String mName;

  private DbSquareStream(Parcel paramParcel)
  {
    this.mId = paramParcel.readString();
    this.mName = paramParcel.readString();
    this.mDescription = paramParcel.readString();
  }

  public DbSquareStream(String paramString1, String paramString2, String paramString3)
  {
    this.mId = paramString1;
    this.mName = paramString2;
    this.mDescription = paramString3;
  }

  public static DbSquareStream[] deserialize(byte[] paramArrayOfByte)
  {
    DbSquareStream[] arrayOfDbSquareStream;
    if (paramArrayOfByte == null)
      arrayOfDbSquareStream = null;
    while (true)
    {
      return arrayOfDbSquareStream;
      ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
      int i = localByteBuffer.getShort();
      arrayOfDbSquareStream = new DbSquareStream[i];
      for (int j = 0; j < i; j = (short)(j + 1))
        arrayOfDbSquareStream[j] = new DbSquareStream(getShortString(localByteBuffer), getShortString(localByteBuffer), getShortString(localByteBuffer));
    }
  }

  public static byte[] serialize(DbSquareStream[] paramArrayOfDbSquareStream)
    throws IOException
  {
    Object localObject2;
    if (paramArrayOfDbSquareStream.length == 0)
      localObject2 = null;
    while (true)
    {
      return localObject2;
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(32);
      DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
      try
      {
        localDataOutputStream.writeShort(paramArrayOfDbSquareStream.length);
        int i = paramArrayOfDbSquareStream.length;
        for (int j = 0; j < i; j++)
        {
          DbSquareStream localDbSquareStream = paramArrayOfDbSquareStream[j];
          putShortString(localDataOutputStream, localDbSquareStream.mId);
          putShortString(localDataOutputStream, localDbSquareStream.mName);
          putShortString(localDataOutputStream, localDbSquareStream.mDescription);
        }
        byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
        localObject2 = arrayOfByte;
        localDataOutputStream.close();
      }
      finally
      {
        localDataOutputStream.close();
      }
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public final String getDescription()
  {
    return this.mDescription;
  }

  public final String getName()
  {
    return this.mName;
  }

  public final String getStreamId()
  {
    return this.mId;
  }

  public String toString()
  {
    return "{SquareStream id=" + this.mId + " name=" + this.mName + " description=" + this.mDescription + "}";
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mId);
    paramParcel.writeString(this.mName);
    paramParcel.writeString(this.mDescription);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.DbSquareStream
 * JD-Core Version:    0.6.2
 */