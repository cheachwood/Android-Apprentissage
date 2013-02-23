package com.google.android.apps.plus.content;

import com.google.api.services.plusi.model.ClientOzEvent;
import com.google.api.services.plusi.model.ClientOzEventJson;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class DbAnalyticsEvents extends DbSerializer
{
  public static List<ClientOzEvent> deserializeClientOzEventList(byte[] paramArrayOfByte)
  {
    Object localObject;
    if (paramArrayOfByte == null)
      localObject = null;
    label96: 
    while (true)
    {
      return localObject;
      ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
      localObject = new ArrayList();
      ClientOzEventJson localClientOzEventJson = ClientOzEventJson.getInstance();
      int i = localByteBuffer.getInt();
      for (int j = 0; ; j++)
      {
        if (j >= i)
          break label96;
        int k = localByteBuffer.getInt();
        byte[] arrayOfByte = new byte[k];
        localByteBuffer.get(arrayOfByte, 0, k);
        if ((arrayOfByte == null) || (arrayOfByte.length == 0))
        {
          localObject = null;
          break;
        }
        ((List)localObject).add(localClientOzEventJson.fromByteArray(arrayOfByte));
      }
    }
  }

  public static byte[] serializeClientOzEventList(List<ClientOzEvent> paramList)
    throws IOException
  {
    byte[] arrayOfByte1;
    if (paramList == null)
      arrayOfByte1 = null;
    while (true)
    {
      return arrayOfByte1;
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
      ClientOzEventJson localClientOzEventJson = ClientOzEventJson.getInstance();
      localDataOutputStream.writeInt(paramList.size());
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        byte[] arrayOfByte2 = localClientOzEventJson.toByteArray((ClientOzEvent)localIterator.next());
        if (arrayOfByte2 == null)
        {
          localDataOutputStream.writeInt(0);
        }
        else
        {
          localDataOutputStream.writeInt(arrayOfByte2.length);
          localDataOutputStream.write(arrayOfByte2);
        }
      }
      arrayOfByte1 = localByteArrayOutputStream.toByteArray();
      localDataOutputStream.close();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.DbAnalyticsEvents
 * JD-Core Version:    0.6.2
 */