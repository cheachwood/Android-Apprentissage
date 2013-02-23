package com.google.android.apps.plus.content;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class DbAudienceData extends DbSerializer
{
  private static AudienceData deserialize(ByteBuffer paramByteBuffer)
  {
    int i = paramByteBuffer.getShort();
    ArrayList localArrayList1 = new ArrayList(i);
    for (int j = 0; j < i; j++)
      localArrayList1.add(new PersonData(getShortString(paramByteBuffer), getShortString(paramByteBuffer), getShortString(paramByteBuffer), getShortString(paramByteBuffer)));
    int k = paramByteBuffer.getShort();
    ArrayList localArrayList2 = new ArrayList(k);
    for (int m = 0; m < k; m++)
    {
      String str1 = getShortString(paramByteBuffer);
      String str2 = getShortString(paramByteBuffer);
      localArrayList2.add(new CircleData(str1, paramByteBuffer.getInt(), str2, paramByteBuffer.getInt()));
    }
    int n = paramByteBuffer.getShort();
    ArrayList localArrayList3 = new ArrayList(n);
    for (int i1 = 0; i1 < n; i1++)
      localArrayList3.add(new SquareTargetData(getShortString(paramByteBuffer), getShortString(paramByteBuffer), getShortString(paramByteBuffer), getShortString(paramByteBuffer)));
    return new AudienceData(localArrayList1, localArrayList2, localArrayList3, paramByteBuffer.getInt());
  }

  public static AudienceData deserialize(byte[] paramArrayOfByte)
  {
    return deserialize(ByteBuffer.wrap(paramArrayOfByte));
  }

  public static ArrayList<AudienceData> deserializeList(byte[] paramArrayOfByte)
  {
    ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
    int i = localByteBuffer.getInt();
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++)
      localArrayList.add(deserialize(localByteBuffer));
    return localArrayList;
  }

  public static byte[] serialize(AudienceData paramAudienceData)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    try
    {
      localDataOutputStream.writeShort(paramAudienceData.getUserCount());
      for (PersonData localPersonData : paramAudienceData.getUsers())
      {
        putShortString(localDataOutputStream, localPersonData.getObfuscatedId());
        putShortString(localDataOutputStream, localPersonData.getName());
        putShortString(localDataOutputStream, localPersonData.getEmail());
        putShortString(localDataOutputStream, localPersonData.getCompressedPhotoUrl());
      }
      localDataOutputStream.writeShort(paramAudienceData.getCircleCount());
      for (CircleData localCircleData : paramAudienceData.getCircles())
      {
        putShortString(localDataOutputStream, localCircleData.getId());
        putShortString(localDataOutputStream, localCircleData.getName());
        localDataOutputStream.writeInt(localCircleData.getType());
        localDataOutputStream.writeInt(localCircleData.getSize());
      }
      localDataOutputStream.writeShort(paramAudienceData.getSquareTargetCount());
      for (SquareTargetData localSquareTargetData : paramAudienceData.getSquareTargets())
      {
        putShortString(localDataOutputStream, localSquareTargetData.getSquareId());
        putShortString(localDataOutputStream, localSquareTargetData.getSquareName());
        putShortString(localDataOutputStream, localSquareTargetData.getSquareStreamId());
        putShortString(localDataOutputStream, localSquareTargetData.getSquareStreamName());
      }
      localDataOutputStream.writeInt(paramAudienceData.getUserCount() + paramAudienceData.getHiddenUserCount());
      return localByteArrayOutputStream.toByteArray();
    }
    finally
    {
      localDataOutputStream.close();
    }
  }

  public static byte[] serialize(ArrayList<AudienceData> paramArrayList)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    try
    {
      int i = paramArrayList.size();
      localDataOutputStream.writeInt(i);
      for (int j = 0; j < i; j++)
        localDataOutputStream.write(serialize((AudienceData)paramArrayList.get(j)));
      return localByteArrayOutputStream.toByteArray();
    }
    finally
    {
      localDataOutputStream.close();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.DbAudienceData
 * JD-Core Version:    0.6.2
 */