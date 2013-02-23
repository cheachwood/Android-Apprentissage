package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import android.database.Cursor;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.CircleData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.PersonData;
import com.google.wireless.realtimechat.proto.Data.Participant;
import com.google.wireless.realtimechat.proto.Data.Participant.Builder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class ParticipantUtils
{
  public static String getParticipantIdFromPerson(PersonData paramPersonData)
  {
    String str1 = paramPersonData.getObfuscatedId();
    String str2;
    if (!TextUtils.isEmpty(str1))
      str2 = "g:" + str1;
    while (true)
    {
      return str2;
      boolean bool = TextUtils.isEmpty(paramPersonData.getEmail());
      str2 = null;
      if (!bool)
      {
        String str3 = paramPersonData.getEmail();
        if (str3.startsWith("p:"))
          str2 = "p:" + PhoneNumberUtils.stripSeparators(str3.substring(2));
        else
          str2 = "e:" + str3;
      }
    }
  }

  public static List<Data.Participant> getParticipantListFromAudience(Context paramContext, EsAccount paramEsAccount, AudienceData paramAudienceData)
  {
    HashSet localHashSet = new HashSet();
    ArrayList localArrayList = new ArrayList();
    PersonData[] arrayOfPersonData = paramAudienceData.getUsers();
    int i = arrayOfPersonData.length;
    int j = 0;
    if (j < i)
    {
      PersonData localPersonData = arrayOfPersonData[j];
      String str2 = getParticipantIdFromPerson(localPersonData);
      String str3;
      if (str2 != null)
      {
        str3 = localPersonData.getName();
        String[] arrayOfString2 = str3.split(" ");
        if (arrayOfString2.length > 0)
          str3 = arrayOfString2[0];
      }
      for (Data.Participant localParticipant2 = Data.Participant.newBuilder().setFullName(localPersonData.getName()).setFirstName(str3).setParticipantId(str2).build(); ; localParticipant2 = null)
      {
        if ((localParticipant2 != null) && (!localHashSet.contains(localParticipant2.getParticipantId())))
        {
          localHashSet.add(localParticipant2.getParticipantId());
          localArrayList.add(localParticipant2);
        }
        j++;
        break;
      }
    }
    for (CircleData localCircleData : paramAudienceData.getCircles())
    {
      Cursor localCursor = null;
      try
      {
        localCursor = EsPeopleData.getPeople(paramContext, paramEsAccount, localCircleData.getId(), null, new String[] { "name", "person_id" }, null, null);
        while (localCursor.moveToNext())
        {
          String str1 = localCursor.getString(0);
          String[] arrayOfString1 = str1.split(" ");
          if (arrayOfString1.length > 0)
            str1 = arrayOfString1[0];
          Data.Participant localParticipant1 = Data.Participant.newBuilder().setFullName(localCursor.getString(0)).setFirstName(str1).setParticipantId(localCursor.getString(1)).build();
          if (!localHashSet.contains(localParticipant1.getParticipantId()))
          {
            localHashSet.add(localParticipant1.getParticipantId());
            localArrayList.add(localParticipant1);
          }
        }
      }
      finally
      {
        localCursor.close();
      }
    }
    return localArrayList;
  }

  public static PersonData makePersonFromParticipant(Data.Participant paramParticipant)
  {
    String str;
    if (paramParticipant != null)
    {
      str = paramParticipant.getParticipantId();
      if (!str.startsWith("g:"))
        throw new IllegalArgumentException();
    }
    for (PersonData localPersonData = new PersonData(str.substring(2), paramParticipant.getFullName(), null); ; localPersonData = null)
      return localPersonData;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.ParticipantUtils
 * JD-Core Version:    0.6.2
 */