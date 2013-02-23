package com.google.android.apps.plus.content;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DbSerializer
{
  private static String decodeUtf8(byte[] paramArrayOfByte)
  {
    try
    {
      String str = new String(paramArrayOfByte, "UTF-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    throw new AssertionError();
  }

  protected static String getShortString(ByteBuffer paramByteBuffer)
  {
    int i = paramByteBuffer.getShort();
    byte[] arrayOfByte;
    if (i > 0)
    {
      arrayOfByte = new byte[i];
      paramByteBuffer.get(arrayOfByte);
    }
    for (String str = decodeUtf8(arrayOfByte); ; str = null)
      return str;
  }

  static List<String> getShortStringList(ByteBuffer paramByteBuffer)
  {
    ArrayList localArrayList = new ArrayList();
    int i = paramByteBuffer.getInt();
    for (int j = 0; j < i; j++)
      localArrayList.add(getShortString(paramByteBuffer));
    return localArrayList;
  }

  protected static void putShortString(DataOutputStream paramDataOutputStream, String paramString)
    throws IOException
  {
    if (paramString != null)
    {
      byte[] arrayOfByte = paramString.getBytes("UTF-8");
      paramDataOutputStream.writeShort(arrayOfByte.length);
      paramDataOutputStream.write(arrayOfByte);
    }
    while (true)
    {
      return;
      paramDataOutputStream.writeShort(0);
    }
  }

  protected static void putShortStringList(DataOutputStream paramDataOutputStream, List<String> paramList)
    throws IOException
  {
    paramDataOutputStream.writeInt(paramList.size());
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
      putShortString(paramDataOutputStream, (String)localIterator.next());
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.DbSerializer
 * JD-Core Version:    0.6.2
 */