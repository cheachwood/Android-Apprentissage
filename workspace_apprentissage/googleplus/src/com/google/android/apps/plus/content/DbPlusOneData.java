package com.google.android.apps.plus.content;

import com.google.api.services.plusi.model.DataPlusOne;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public final class DbPlusOneData extends DbSerializer
{
  private int mCount;
  private String mId;
  private boolean mPlusOnedByMe;

  public DbPlusOneData()
  {
  }

  private DbPlusOneData(DataPlusOne paramDataPlusOne)
  {
    this.mId = paramDataPlusOne.id;
    this.mCount = paramDataPlusOne.globalCount.intValue();
    this.mPlusOnedByMe = paramDataPlusOne.isPlusonedByViewer.booleanValue();
  }

  public DbPlusOneData(String paramString, int paramInt, boolean paramBoolean)
  {
    this.mId = paramString;
    this.mCount = paramInt;
    this.mPlusOnedByMe = paramBoolean;
  }

  public static DbPlusOneData deserialize(byte[] paramArrayOfByte)
  {
    int i = 1;
    DbPlusOneData localDbPlusOneData;
    if (paramArrayOfByte == null)
    {
      localDbPlusOneData = null;
      return localDbPlusOneData;
    }
    ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
    String str = getShortString(localByteBuffer);
    int k = localByteBuffer.getInt();
    if (localByteBuffer.get() == i);
    while (true)
    {
      localDbPlusOneData = new DbPlusOneData(str, k, i);
      break;
      int j = 0;
    }
  }

  public static byte[] serialize(DbPlusOneData paramDbPlusOneData)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(32);
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    putShortString(localDataOutputStream, paramDbPlusOneData.mId);
    localDataOutputStream.writeInt(paramDbPlusOneData.mCount);
    if (paramDbPlusOneData.mPlusOnedByMe);
    for (int i = 1; ; i = 0)
    {
      localDataOutputStream.write(i);
      byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
      localDataOutputStream.close();
      return arrayOfByte;
    }
  }

  public static byte[] serialize(DataPlusOne paramDataPlusOne)
    throws IOException
  {
    return serialize(new DbPlusOneData(paramDataPlusOne));
  }

  public final int getCount()
  {
    return this.mCount;
  }

  public final String getId()
  {
    return this.mId;
  }

  public final boolean isPlusOnedByMe()
  {
    return this.mPlusOnedByMe;
  }

  public final void setId(String paramString)
  {
    this.mId = paramString;
  }

  public final void updatePlusOnedByMe(boolean paramBoolean)
  {
    int i;
    if (this.mPlusOnedByMe != paramBoolean)
    {
      this.mPlusOnedByMe = paramBoolean;
      i = this.mCount;
      if (!paramBoolean)
        break label32;
    }
    label32: for (int j = 1; ; j = -1)
    {
      this.mCount = (j + i);
      return;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.DbPlusOneData
 * JD-Core Version:    0.6.2
 */