package com.google.android.apps.plus.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.util.LongSparseArray;
import android.text.TextUtils;
import com.google.android.apps.plus.R.string;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;

public final class TimeZoneHelper
{
  private static String sGenericTimeZonePrefix;
  private static String sTimeZoneFormat;
  private Calendar mCalendar;
  private LongSparseArray<TimeZoneGroup> mOffsetToTimeZonesMapping;
  private List<TimeZoneInfo> mOrderedTimeZoneInfoList;

  public TimeZoneHelper(Context paramContext)
  {
    initialize(paramContext);
  }

  public static boolean areTimeZoneIdsEquivalent(String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (paramString2 != null));
    for (boolean bool = TextUtils.equals(paramString1.toLowerCase(), paramString2.toLowerCase()); ; bool = false)
      return bool;
  }

  private static LongSparseArray<TimeZoneGroup> buildMapping(String[] paramArrayOfString, String paramString, Calendar paramCalendar)
  {
    LongSparseArray localLongSparseArray = new LongSparseArray();
    int i = -1 + paramArrayOfString.length;
    if (i >= 0)
    {
      TimeZone localTimeZone = getSystemTimeZone(paramArrayOfString[i]);
      long l = getOffset(localTimeZone, paramCalendar);
      TimeZoneGroup localTimeZoneGroup2 = (TimeZoneGroup)localLongSparseArray.get(l);
      if (localTimeZoneGroup2 == null)
      {
        localTimeZoneGroup2 = new TimeZoneGroup();
        localLongSparseArray.put(l, localTimeZoneGroup2);
      }
      TimeZoneInfo localTimeZoneInfo = new TimeZoneInfo(localTimeZone);
      localTimeZoneInfo.setOffset(l);
      String str = localTimeZone.getDisplayName();
      if (!localTimeZoneGroup2.mSeenDisplayNames.contains(str))
      {
        if ((paramString == null) || (!str.startsWith(paramString)))
          break label148;
        localTimeZoneGroup2.mExcludedTimeZoneInfoList.add(localTimeZoneInfo);
      }
      while (true)
      {
        localTimeZoneGroup2.mSeenDisplayNames.add(str);
        i--;
        break;
        label148: localTimeZoneGroup2.mTimeZoneInfoList.add(localTimeZoneInfo);
      }
    }
    for (int j = -1 + localLongSparseArray.size(); j >= 0; j--)
    {
      TimeZoneGroup localTimeZoneGroup1 = (TimeZoneGroup)localLongSparseArray.get(localLongSparseArray.keyAt(j));
      if ((localTimeZoneGroup1.mTimeZoneInfoList.isEmpty()) && (!localTimeZoneGroup1.mExcludedTimeZoneInfoList.isEmpty()))
      {
        Collections.sort(localTimeZoneGroup1.mExcludedTimeZoneInfoList);
        localTimeZoneGroup1.mTimeZoneInfoList.add(localTimeZoneGroup1.mExcludedTimeZoneInfoList.get(0));
      }
      localTimeZoneGroup1.mExcludedTimeZoneInfoList.clear();
      Collections.sort(localTimeZoneGroup1.mTimeZoneInfoList);
    }
    return localLongSparseArray;
  }

  public static String getDisplayString(String paramString, Calendar paramCalendar, boolean paramBoolean)
  {
    TimeZone localTimeZone1 = getSystemTimeZone(paramString);
    TimeZone localTimeZone2 = paramCalendar.getTimeZone();
    if (localTimeZone1 != null)
    {
      long l1 = getOffset(localTimeZone2, paramCalendar);
      long l2 = getOffset(localTimeZone1, paramCalendar);
      if ((!areTimeZoneIdsEquivalent(localTimeZone1.getID(), paramString)) || ((!paramBoolean) && (l1 == l2)));
    }
    for (String str = localTimeZone1.getDisplayName(); ; str = null)
      return str;
  }

  private static long getOffset(TimeZone paramTimeZone, Calendar paramCalendar)
  {
    int i = paramCalendar.get(0);
    int j = paramCalendar.get(1);
    int k = paramCalendar.get(5);
    int m = paramCalendar.get(2);
    int n = paramCalendar.get(11);
    int i1 = paramCalendar.get(12);
    return paramTimeZone.getOffset(i, j, m, k, paramCalendar.get(7), 60000 * (i1 + n * 60));
  }

  public static TimeZone getSystemTimeZone(String paramString)
  {
    TimeZone localTimeZone;
    if (TextUtils.isEmpty(paramString))
      localTimeZone = TimeZone.getDefault();
    while (true)
    {
      return localTimeZone;
      localTimeZone = TimeZone.getTimeZone(paramString);
      if (localTimeZone == null)
        localTimeZone = TimeZone.getDefault();
    }
  }

  private TimeZoneInfo getTimeZoneInfo(String paramString, Long paramLong)
  {
    int i = this.mOffsetToTimeZonesMapping.size();
    Object localObject2;
    if ((TextUtils.isEmpty(paramString)) && (paramLong == null))
      localObject2 = getCurrentTimeZoneInfo();
    while (true)
    {
      return localObject2;
      TimeZone localTimeZone = getSystemTimeZone(paramString);
      String str = null;
      label74: Object localObject1;
      if (localTimeZone != null)
      {
        if (areTimeZoneIdsEquivalent(localTimeZone.getID(), paramString))
        {
          str = localTimeZone.getDisplayName();
          paramLong = Long.valueOf(getOffset(localTimeZone, this.mCalendar));
        }
      }
      else
      {
        localObject1 = null;
        if (i <= 0)
          break label258;
        j = 0;
        localObject1 = null;
        if (paramLong != null)
        {
          j = this.mOffsetToTimeZonesMapping.indexOfKey(paramLong.longValue());
          localObject1 = null;
          if (j >= 0);
        }
      }
      for (int j = 0; ; j++)
      {
        if (j >= i)
          break label258;
        long l = this.mOffsetToTimeZonesMapping.keyAt(j);
        ArrayList localArrayList = ((TimeZoneGroup)this.mOffsetToTimeZonesMapping.get(l)).mTimeZoneInfoList;
        int k = localArrayList.size();
        int m = 0;
        while (true)
          if (m < k)
          {
            localObject2 = (TimeZoneInfo)localArrayList.get(m);
            boolean bool = TextUtils.equals(((TimeZoneInfo)localObject2).getTimeZone().getDisplayName(), str);
            if ((l == paramLong.longValue()) && ((bool) || (TextUtils.isEmpty(str))))
              break;
            if ((bool) || (m == 0))
              localObject1 = localObject2;
            m++;
            continue;
            str = null;
            if (paramLong != null)
              break label74;
            localObject2 = getCurrentTimeZoneInfo();
            break;
          }
      }
      label258: localObject2 = localObject1;
    }
  }

  public static void initialize(Context paramContext)
  {
    if (sTimeZoneFormat == null)
    {
      sTimeZoneFormat = paramContext.getResources().getString(R.string.time_zone_utc_format);
      sGenericTimeZonePrefix = paramContext.getResources().getString(R.string.time_zone_generic_system_prefix);
    }
  }

  public final void configure$622086bc(Calendar paramCalendar)
  {
    this.mCalendar = paramCalendar;
    this.mOrderedTimeZoneInfoList = new ArrayList();
    this.mOffsetToTimeZonesMapping = buildMapping(TimeZone.getAvailableIDs(), sGenericTimeZonePrefix, paramCalendar);
    int i = this.mOffsetToTimeZonesMapping.size();
    int j = 0;
    int k = 0;
    while (k < i)
    {
      long l = this.mOffsetToTimeZonesMapping.keyAt(k);
      ArrayList localArrayList = ((TimeZoneGroup)this.mOffsetToTimeZonesMapping.get(l)).mTimeZoneInfoList;
      int m = localArrayList.size();
      int n = 0;
      int i2;
      for (int i1 = j; n < m; i1 = i2)
      {
        TimeZoneInfo localTimeZoneInfo = (TimeZoneInfo)localArrayList.get(n);
        i2 = i1 + 1;
        localTimeZoneInfo.setPosition(i1);
        this.mOrderedTimeZoneInfoList.add(localTimeZoneInfo);
        n++;
      }
      k++;
      j = i1;
    }
  }

  public final TimeZoneInfo getCurrentTimeZoneInfo()
  {
    TimeZone localTimeZone = this.mCalendar.getTimeZone();
    return getTimeZoneInfo(localTimeZone.getID(), Long.valueOf(getOffset(localTimeZone, this.mCalendar)));
  }

  public final long getOffset(TimeZone paramTimeZone)
  {
    return getOffset(paramTimeZone, this.mCalendar);
  }

  public final TimeZone getTimeZone(String paramString, Long paramLong)
  {
    TimeZoneInfo localTimeZoneInfo = getTimeZoneInfo(paramString, null);
    TimeZone localTimeZone = null;
    if (localTimeZoneInfo != null)
      localTimeZone = localTimeZoneInfo.getTimeZone();
    return localTimeZone;
  }

  public final List<TimeZoneInfo> getTimeZoneInfos()
  {
    return this.mOrderedTimeZoneInfoList;
  }

  public final int getTimeZonePos(String paramString, Long paramLong)
  {
    TimeZoneInfo localTimeZoneInfo = getTimeZoneInfo(paramString, null);
    if (localTimeZoneInfo != null);
    for (int i = localTimeZoneInfo.getPosition(); ; i = -1)
      return i;
  }

  public static final class TimeZoneGroup
  {
    ArrayList<TimeZoneHelper.TimeZoneInfo> mExcludedTimeZoneInfoList = new ArrayList();
    HashSet<String> mSeenDisplayNames = new HashSet();
    ArrayList<TimeZoneHelper.TimeZoneInfo> mTimeZoneInfoList = new ArrayList();
  }

  public static final class TimeZoneInfo
    implements Comparable<TimeZoneInfo>
  {
    private long mOffset;
    private int mPosition;
    private TimeZone mTimeZone;

    public TimeZoneInfo(TimeZone paramTimeZone)
    {
      this.mTimeZone = paramTimeZone;
      this.mPosition = -1;
    }

    public final long getOffset()
    {
      return this.mOffset;
    }

    public final int getPosition()
    {
      return this.mPosition;
    }

    public final TimeZone getTimeZone()
    {
      return this.mTimeZone;
    }

    public final void setOffset(long paramLong)
    {
      this.mOffset = paramLong;
    }

    public final void setPosition(int paramInt)
    {
      this.mPosition = paramInt;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.TimeZoneHelper
 * JD-Core Version:    0.6.2
 */