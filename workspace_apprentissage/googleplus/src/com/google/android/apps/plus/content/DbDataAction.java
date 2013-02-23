package com.google.android.apps.plus.content;

import com.google.api.services.plusi.model.DataAction;
import com.google.api.services.plusi.model.DataActor;
import com.google.api.services.plusi.model.DataItem;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class DbDataAction extends DbSerializer
{
  public static List<DataAction> deserializeDataActionList(byte[] paramArrayOfByte)
  {
    Object localObject;
    if (paramArrayOfByte == null)
      localObject = null;
    while (true)
    {
      return localObject;
      ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
      localObject = new ArrayList();
      int i = localByteBuffer.getInt();
      for (int j = 0; j < i; j++)
        ((List)localObject).add(getDataAction(localByteBuffer));
    }
  }

  public static List<DataActor> deserializeDataActorList(byte[] paramArrayOfByte)
  {
    Object localObject;
    if (paramArrayOfByte == null)
      localObject = null;
    while (true)
    {
      return localObject;
      ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
      localObject = new ArrayList();
      int i = localByteBuffer.getInt();
      for (int j = 0; j < i; j++)
        ((List)localObject).add(getDataActor(localByteBuffer));
    }
  }

  private static DataAction getDataAction(ByteBuffer paramByteBuffer)
  {
    DataAction localDataAction = new DataAction();
    localDataAction.type = getShortString(paramByteBuffer);
    ArrayList localArrayList = new ArrayList();
    int i = paramByteBuffer.getInt();
    for (int j = 0; j < i; j++)
    {
      DataItem localDataItem = new DataItem();
      localDataItem.id = getShortString(paramByteBuffer);
      localDataItem.notificationType = getShortString(paramByteBuffer);
      localDataItem.actor = getDataActor(paramByteBuffer);
      localArrayList.add(localDataItem);
    }
    localDataAction.item = localArrayList;
    return localDataAction;
  }

  private static DataActor getDataActor(ByteBuffer paramByteBuffer)
  {
    DataActor localDataActor = new DataActor();
    localDataActor.gender = getShortString(paramByteBuffer);
    localDataActor.name = getShortString(paramByteBuffer);
    localDataActor.obfuscatedGaiaId = getShortString(paramByteBuffer);
    localDataActor.photoUrl = getShortString(paramByteBuffer);
    localDataActor.profileType = getShortString(paramByteBuffer);
    localDataActor.profileUrl = getShortString(paramByteBuffer);
    return localDataActor;
  }

  public static List<DataActor> getDataActorList(List<DataAction> paramList)
  {
    HashSet localHashSet = new HashSet();
    ArrayList localArrayList = new ArrayList();
    label29: Iterator localIterator1;
    if ((paramList == null) || (paramList.isEmpty()))
    {
      break label38;
      return localArrayList;
    }
    else
    {
      localIterator1 = paramList.iterator();
    }
    while (true)
    {
      label38: if (!localIterator1.hasNext())
        break label29;
      DataAction localDataAction = (DataAction)localIterator1.next();
      if ((localDataAction == null) || (localDataAction.item == null))
        break;
      Iterator localIterator2 = localDataAction.item.iterator();
      while (localIterator2.hasNext())
      {
        DataItem localDataItem = (DataItem)localIterator2.next();
        if ((localDataItem != null) && (localDataItem.actor != null))
        {
          DataActor localDataActor = localDataItem.actor;
          String str = localDataActor.obfuscatedGaiaId;
          if (!localHashSet.contains(str))
          {
            localArrayList.add(localDataActor);
            localHashSet.add(str);
          }
        }
      }
    }
  }

  private static void putDataAction(DataOutputStream paramDataOutputStream, DataAction paramDataAction)
    throws IOException
  {
    putShortString(paramDataOutputStream, paramDataAction.type);
    List localList = paramDataAction.item;
    if (localList == null)
      paramDataOutputStream.writeInt(0);
    while (true)
    {
      return;
      paramDataOutputStream.writeInt(localList.size());
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        DataItem localDataItem = (DataItem)localIterator.next();
        putShortString(paramDataOutputStream, localDataItem.id);
        putShortString(paramDataOutputStream, localDataItem.notificationType);
        putDataActor(paramDataOutputStream, localDataItem.actor);
      }
    }
  }

  private static void putDataActor(DataOutputStream paramDataOutputStream, DataActor paramDataActor)
    throws IOException
  {
    putShortString(paramDataOutputStream, paramDataActor.gender);
    putShortString(paramDataOutputStream, paramDataActor.name);
    putShortString(paramDataOutputStream, paramDataActor.obfuscatedGaiaId);
    putShortString(paramDataOutputStream, paramDataActor.photoUrl);
    putShortString(paramDataOutputStream, paramDataActor.profileType);
    putShortString(paramDataOutputStream, paramDataActor.profileUrl);
  }

  public static byte[] serializeDataActionList(List<DataAction> paramList)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    byte[] arrayOfByte;
    if (paramList == null)
      arrayOfByte = null;
    while (true)
    {
      return arrayOfByte;
      localDataOutputStream.writeInt(paramList.size());
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
        putDataAction(localDataOutputStream, (DataAction)localIterator.next());
      arrayOfByte = localByteArrayOutputStream.toByteArray();
      localDataOutputStream.close();
    }
  }

  public static byte[] serializeDataActorList(List<DataActor> paramList)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    byte[] arrayOfByte;
    if (paramList == null)
      arrayOfByte = null;
    while (true)
    {
      return arrayOfByte;
      localDataOutputStream.writeInt(paramList.size());
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
        putDataActor(localDataOutputStream, (DataActor)localIterator.next());
      arrayOfByte = localByteArrayOutputStream.toByteArray();
      localDataOutputStream.close();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.DbDataAction
 * JD-Core Version:    0.6.2
 */