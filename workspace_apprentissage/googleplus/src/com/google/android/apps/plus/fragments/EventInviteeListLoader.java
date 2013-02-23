package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.content.EsEventData.InviteeList;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.json.EsJson;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import com.google.android.apps.plus.util.PrimitiveUtils;
import com.google.api.services.plusi.model.EmbedsPerson;
import com.google.api.services.plusi.model.Invitee;
import com.google.api.services.plusi.model.InviteeSummary;
import com.google.api.services.plusi.model.PlusEvent;
import com.google.api.services.plusi.model.PlusEventJson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class EventInviteeListLoader extends EsCursorLoader
{
  private static final String[] INVITEE_PROJECTION = { "is_header", "_id", "person_id", "gaia_id", "name", "email", "packed_circle_ids", "numaddguests", "blacklisted", "rsvp", "is_past", "invitee_count" };
  private final EsAccount mAccount;
  private final String mEventId;
  private final String mOwnerId;

  public EventInviteeListLoader(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2)
  {
    super(paramContext, EsProvider.EVENTS_ALL_URI);
    this.mEventId = paramString1;
    this.mOwnerId = paramString2;
    this.mAccount = paramEsAccount;
  }

  private void insertInviteeGroup(PlusEvent paramPlusEvent, String paramString, List<Invitee> paramList, EsMatrixCursor paramEsMatrixCursor, HashMap<String, String> paramHashMap)
  {
    InviteeSummary localInviteeSummary = EsEventData.getInviteeSummary(paramPlusEvent, paramString);
    int i = 0;
    for (int j = 0; j < paramList.size(); j++)
    {
      Invitee localInvitee2 = (Invitee)paramList.get(j);
      if (isPersonVisible(localInvitee2.invitee))
        i += 1 + PrimitiveUtils.safeInt(localInvitee2.numAdditionalGuests);
    }
    int n;
    int i1;
    label151: label180: int i3;
    label253: Object[] arrayOfObject3;
    String str2;
    label306: int i4;
    if (localInviteeSummary != null)
    {
      n = localInviteeSummary.count.intValue();
      if (n <= 0)
        return;
      boolean bool = EsEventData.isEventOver(paramPlusEvent, System.currentTimeMillis());
      Object[] arrayOfObject1 = new Object[INVITEE_PROJECTION.length];
      arrayOfObject1[0] = Integer.valueOf(0);
      arrayOfObject1[1] = Integer.valueOf(paramEsMatrixCursor.getCount());
      arrayOfObject1[9] = paramString;
      arrayOfObject1[11] = Integer.valueOf(n);
      if (!bool)
        break label469;
      i1 = 1;
      arrayOfObject1[10] = Integer.valueOf(i1);
      paramEsMatrixCursor.addRow(arrayOfObject1);
      if (paramList == null)
        break label499;
      Iterator localIterator = paramList.iterator();
      Invitee localInvitee1;
      do
      {
        if (!localIterator.hasNext())
          break;
        localInvitee1 = (Invitee)localIterator.next();
      }
      while ((localInvitee1.invitee == null) || (!isPersonVisible(localInvitee1.invitee)));
      String str1 = localInvitee1.invitee.ownerObfuscatedId;
      if ((localInvitee1.isAdminBlacklisted == null) || (!localInvitee1.isAdminBlacklisted.booleanValue()))
        break label475;
      i3 = 1;
      arrayOfObject3 = new Object[INVITEE_PROJECTION.length];
      arrayOfObject3[0] = Integer.valueOf(1);
      arrayOfObject3[1] = Integer.valueOf(paramEsMatrixCursor.getCount());
      if (str1 == null)
        break label481;
      str2 = "g:" + str1;
      arrayOfObject3[2] = str2;
      arrayOfObject3[3] = str1;
      arrayOfObject3[4] = localInvitee1.invitee.name;
      arrayOfObject3[5] = localInvitee1.invitee.email;
      if (!TextUtils.equals(localInvitee1.rsvpType, "ATTENDING"))
        break label487;
      i4 = PrimitiveUtils.safeInt(localInvitee1.numAdditionalGuests);
      label365: arrayOfObject3[7] = Integer.valueOf(i4);
      arrayOfObject3[6] = paramHashMap.get(str1);
      if (i3 == 0)
        break label493;
    }
    label469: label475: label481: label487: label493: for (int i5 = 1; ; i5 = 0)
    {
      arrayOfObject3[8] = Integer.valueOf(i5);
      paramEsMatrixCursor.addRow(arrayOfObject3);
      break label180;
      int k = 0;
      for (int m = 0; m < paramList.size(); m++)
        k += 1 + PrimitiveUtils.safeInt(((Invitee)paramList.get(m)).numAdditionalGuests);
      n = k;
      break;
      i1 = 0;
      break label151;
      i3 = 0;
      break label253;
      str2 = null;
      break label306;
      i4 = 0;
      break label365;
    }
    label499: int i2 = n - i;
    if (i2 > 0)
    {
      Object[] arrayOfObject2 = new Object[INVITEE_PROJECTION.length];
      arrayOfObject2[0] = Integer.valueOf(2);
      arrayOfObject2[1] = Integer.valueOf(paramEsMatrixCursor.getCount());
      arrayOfObject2[11] = Integer.valueOf(i2);
      paramEsMatrixCursor.addRow(arrayOfObject2);
    }
  }

  private static boolean isPersonVisible(EmbedsPerson paramEmbedsPerson)
  {
    if ((paramEmbedsPerson != null) && ((!TextUtils.isEmpty(paramEmbedsPerson.email)) || (!TextUtils.isEmpty(paramEmbedsPerson.name))));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private HashMap<String, String> queryCirclesForPeople(List<Invitee> paramList)
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Invitee localInvitee = (Invitee)localIterator.next();
      if ((localInvitee.invitee != null) && (localInvitee.invitee.ownerObfuscatedId != null))
        localHashMap.put(localInvitee.invitee.ownerObfuscatedId, null);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("gaia_id IN(");
    for (int i = 0; i < localHashMap.size(); i++)
    {
      if (i > 0)
        localStringBuilder.append(',');
      localStringBuilder.append('?');
    }
    localStringBuilder.append(')');
    String str = localStringBuilder.toString();
    String[] arrayOfString = (String[])localHashMap.keySet().toArray(new String[0]);
    Cursor localCursor = EsPeopleData.getPeople(getContext(), this.mAccount, null, null, CircleQuery.PROJECTION, str, arrayOfString);
    if (localCursor != null)
      try
      {
        if (localCursor.moveToNext())
          localHashMap.put(localCursor.getString(0), localCursor.getString(1));
      }
      finally
      {
        localCursor.close();
      }
    return localHashMap;
  }

  public final Cursor esLoadInBackground()
  {
    Object localObject1;
    if ((this.mEventId == null) || (this.mOwnerId == null))
      localObject1 = null;
    while (true)
    {
      return localObject1;
      Cursor localCursor = EsEventData.getEvent(getContext(), this.mAccount, this.mEventId, InviteeQuery.PROJECTION);
      PlusEvent localPlusEvent;
      EsEventData.InviteeList localInviteeList;
      ArrayList localArrayList1;
      try
      {
        boolean bool = localCursor.moveToFirst();
        localPlusEvent = null;
        localInviteeList = null;
        if (bool)
        {
          localPlusEvent = (PlusEvent)PlusEventJson.getInstance().fromByteArray(localCursor.getBlob(0));
          byte[] arrayOfByte = localCursor.getBlob(1);
          localInviteeList = null;
          if (arrayOfByte != null)
            localInviteeList = (EsEventData.InviteeList)EsEventData.INVITEE_LIST_JSON.fromByteArray(arrayOfByte);
        }
        localCursor.close();
        if (localInviteeList == null)
          localObject1 = null;
      }
      finally
      {
        localCursor.close();
      }
      ArrayList localArrayList2 = new ArrayList();
      ArrayList localArrayList3 = new ArrayList();
      ArrayList localArrayList4 = new ArrayList();
      ArrayList localArrayList5 = new ArrayList();
      Iterator localIterator = localInviteeList.invitees.iterator();
      while (localIterator.hasNext())
      {
        Invitee localInvitee = (Invitee)localIterator.next();
        if ((localInvitee.isAdminBlacklisted != null) && (localInvitee.isAdminBlacklisted.booleanValue()))
        {
          localArrayList5.add(localInvitee);
        }
        else
        {
          String str = localInvitee.rsvpType;
          if (("ATTENDING".equals(str)) || ("CHECKIN".equals(str)))
            localArrayList1.add(localInvitee);
          else if ("NOT_ATTENDING".equals(str))
            localArrayList3.add(localInvitee);
          else if ("MAYBE".equals(str))
            localArrayList2.add(localInvitee);
          else
            localArrayList4.add(localInvitee);
        }
      }
      HashMap localHashMap = queryCirclesForPeople(localInviteeList.invitees);
      localObject1 = new EsMatrixCursor(INVITEE_PROJECTION);
      insertInviteeGroup(localPlusEvent, "ATTENDING", localArrayList1, (EsMatrixCursor)localObject1, localHashMap);
      insertInviteeGroup(localPlusEvent, "MAYBE", localArrayList2, (EsMatrixCursor)localObject1, localHashMap);
      insertInviteeGroup(localPlusEvent, "NOT_ATTENDING", localArrayList3, (EsMatrixCursor)localObject1, localHashMap);
      insertInviteeGroup(localPlusEvent, "NOT_RESPONDED", localArrayList4, (EsMatrixCursor)localObject1, localHashMap);
      insertInviteeGroup(localPlusEvent, "REMOVED", localArrayList5, (EsMatrixCursor)localObject1, localHashMap);
    }
  }

  private static abstract interface CircleQuery
  {
    public static final String[] PROJECTION = { "gaia_id", "packed_circle_ids" };
  }

  private static abstract interface InviteeQuery
  {
    public static final String[] PROJECTION = { "event_data", "invitee_roster" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.EventInviteeListLoader
 * JD-Core Version:    0.6.2
 */