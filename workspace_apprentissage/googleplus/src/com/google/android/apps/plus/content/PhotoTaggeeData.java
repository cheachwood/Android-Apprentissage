package com.google.android.apps.plus.content;

import android.text.TextUtils;
import com.google.android.apps.plus.api.MediaRef;
import com.google.api.services.plusi.model.DataActor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public final class PhotoTaggeeData
{
  public static List<DataActor> createDataActorList(Map<String, DataActor> paramMap, String paramString)
  {
    ArrayList localArrayList1 = new ArrayList();
    if (!TextUtils.isEmpty(paramString))
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "|");
      while (localStringTokenizer.hasMoreTokens())
      {
        String str = localStringTokenizer.nextToken();
        if (!TextUtils.isEmpty(str))
          localArrayList1.add(str);
      }
    }
    ArrayList localArrayList2 = new ArrayList();
    if ((paramMap != null) && (localArrayList1 != null))
    {
      Iterator localIterator = localArrayList1.iterator();
      while (localIterator.hasNext())
      {
        DataActor localDataActor = (DataActor)paramMap.get((String)localIterator.next());
        if (localDataActor != null)
          localArrayList2.add(localDataActor);
      }
    }
    return localArrayList2;
  }

  public static Map<MediaRef, List<PhotoTaggee>> createMediaRefUserMap(List<MediaRef> paramList, List<DataActor> paramList1, String paramString)
  {
    HashMap localHashMap1 = new HashMap();
    List localList1 = getPhotoIdList(paramString);
    if ((localList1 != null) && (paramList1 != null) && (localList1.size() == paramList1.size()))
      for (int i = 0; i < localList1.size(); i++)
      {
        List localList3 = (List)localList1.get(i);
        DataActor localDataActor2 = (DataActor)paramList1.get(i);
        Iterator localIterator3 = localList3.iterator();
        if (localIterator3.hasNext())
        {
          String str2 = (String)localIterator3.next();
          if (localHashMap1.containsKey(str2));
          ArrayList localArrayList2;
          for (Object localObject = (List)localHashMap1.get(str2); ; localObject = localArrayList2)
          {
            ((List)localObject).add(localDataActor2);
            break;
            localArrayList2 = new ArrayList();
            localHashMap1.put(str2, localArrayList2);
          }
        }
      }
    HashMap localHashMap2 = new HashMap();
    if (paramList != null)
    {
      Iterator localIterator1 = paramList.iterator();
      while (localIterator1.hasNext())
      {
        MediaRef localMediaRef = (MediaRef)localIterator1.next();
        if (localMediaRef != null)
        {
          String str1 = String.valueOf(localMediaRef.getPhotoId());
          if ((!TextUtils.isEmpty(str1)) && (localHashMap1.containsKey(str1)))
          {
            ArrayList localArrayList1 = new ArrayList();
            List localList2 = (List)localHashMap1.get(str1);
            if (localList2 != null)
            {
              Iterator localIterator2 = localList2.iterator();
              while (localIterator2.hasNext())
              {
                DataActor localDataActor1 = (DataActor)localIterator2.next();
                if (localDataActor1 != null)
                  localArrayList1.add(new PhotoTaggee(localDataActor1.obfuscatedGaiaId, localDataActor1.name));
              }
            }
            if (!localArrayList1.isEmpty())
              localHashMap2.put(localMediaRef, localArrayList1);
          }
        }
      }
    }
    return localHashMap2;
  }

  private static List<List<String>> getPhotoIdList(String paramString)
  {
    ArrayList localArrayList1 = new ArrayList();
    if (!TextUtils.isEmpty(paramString))
    {
      StringTokenizer localStringTokenizer1 = new StringTokenizer(paramString, "|");
      while (localStringTokenizer1.hasMoreTokens())
      {
        String str1 = localStringTokenizer1.nextToken();
        if (!TextUtils.isEmpty(str1))
        {
          ArrayList localArrayList2 = new ArrayList();
          StringTokenizer localStringTokenizer2 = new StringTokenizer(str1, ":");
          while (localStringTokenizer2.hasMoreTokens())
          {
            String str2 = localStringTokenizer2.nextToken();
            if (!TextUtils.isEmpty(str2))
              localArrayList2.add(str2);
          }
          if (!localArrayList2.isEmpty())
            localArrayList1.add(localArrayList2);
        }
      }
    }
    return localArrayList1;
  }

  public static final class PhotoTaggee
    implements Serializable
  {
    private String mId;
    private String mName;

    public PhotoTaggee(String paramString1, String paramString2)
    {
      this.mId = paramString1;
      this.mName = paramString2;
    }

    public final String getId()
    {
      return this.mId;
    }

    public final String getName()
    {
      return this.mName;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.PhotoTaggeeData
 * JD-Core Version:    0.6.2
 */