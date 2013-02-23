package com.google.android.apps.plus.util;

import android.text.TextUtils;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.CircleData;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.content.SquareTargetData;

public final class PeopleUtils
{
  public static boolean in(AudienceData paramAudienceData1, AudienceData paramAudienceData2)
  {
    PersonData[] arrayOfPersonData = paramAudienceData2.getUsers();
    int i = arrayOfPersonData.length;
    int j = 0;
    boolean bool1;
    if (j < i)
    {
      PersonData localPersonData = arrayOfPersonData[j];
      boolean bool4 = in(paramAudienceData1.getUsers(), localPersonData);
      bool1 = false;
      if (bool4);
    }
    while (true)
    {
      return bool1;
      j++;
      break;
      CircleData[] arrayOfCircleData = paramAudienceData2.getCircles();
      int k = arrayOfCircleData.length;
      for (int m = 0; ; m++)
      {
        if (m >= k)
          break label104;
        CircleData localCircleData = arrayOfCircleData[m];
        boolean bool3 = in(paramAudienceData1.getCircles(), localCircleData);
        bool1 = false;
        if (!bool3)
          break;
      }
      label104: SquareTargetData[] arrayOfSquareTargetData = paramAudienceData2.getSquareTargets();
      int n = arrayOfSquareTargetData.length;
      for (int i1 = 0; ; i1++)
      {
        if (i1 >= n)
          break label157;
        SquareTargetData localSquareTargetData = arrayOfSquareTargetData[i1];
        boolean bool2 = in(paramAudienceData1.getSquareTargets(), localSquareTargetData);
        bool1 = false;
        if (!bool2)
          break;
      }
      label157: bool1 = true;
    }
  }

  public static boolean in(CircleData[] paramArrayOfCircleData, CircleData paramCircleData)
  {
    int i = paramArrayOfCircleData.length;
    int j = 0;
    if (j < i)
      if (!paramArrayOfCircleData[j].getId().equals(paramCircleData.getId()));
    for (boolean bool = true; ; bool = false)
    {
      return bool;
      j++;
      break;
    }
  }

  public static boolean in(PersonData[] paramArrayOfPersonData, PersonData paramPersonData)
  {
    int i = paramArrayOfPersonData.length;
    int j = 0;
    if (j < i)
      if (!EsPeopleData.isSamePerson(paramArrayOfPersonData[j], paramPersonData));
    for (boolean bool = true; ; bool = false)
    {
      return bool;
      j++;
      break;
    }
  }

  public static boolean in(SquareTargetData[] paramArrayOfSquareTargetData, SquareTargetData paramSquareTargetData)
  {
    int i = paramArrayOfSquareTargetData.length;
    int j = 0;
    if (j < i)
    {
      SquareTargetData localSquareTargetData = paramArrayOfSquareTargetData[j];
      if ((!TextUtils.equals(localSquareTargetData.getSquareId(), paramSquareTargetData.getSquareId())) || (!TextUtils.equals(localSquareTargetData.getSquareStreamId(), paramSquareTargetData.getSquareStreamId())));
    }
    for (boolean bool = true; ; bool = false)
    {
      return bool;
      j++;
      break;
    }
  }

  public static boolean isEmpty(AudienceData paramAudienceData)
  {
    if ((paramAudienceData.getUserCount() == 0) && (paramAudienceData.getCircleCount() == 0) && (paramAudienceData.getSquareTargetCount() == 0));
    for (boolean bool = true; ; bool = false)
      return bool;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.PeopleUtils
 * JD-Core Version:    0.6.2
 */