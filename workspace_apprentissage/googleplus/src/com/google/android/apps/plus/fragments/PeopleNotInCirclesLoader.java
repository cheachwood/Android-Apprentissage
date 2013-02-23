package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public final class PeopleNotInCirclesLoader extends EsCursorLoader
{
  private static final String[] PROJECTION = { "person_id", "in_my_circles" };
  private final EsAccount mAccount;
  private final HashMap<String, PersonData> mPeopleMap;
  private final String[] mProjection;

  public PeopleNotInCirclesLoader(Context paramContext, EsAccount paramEsAccount, String[] paramArrayOfString, HashMap<String, PersonData> paramHashMap, boolean paramBoolean)
  {
    super(paramContext);
    setUri(EsProvider.CONTACTS_URI);
    this.mAccount = paramEsAccount;
    this.mProjection = paramArrayOfString;
    this.mPeopleMap = paramHashMap;
    if (paramBoolean);
    for (String str = "gaia_id IS NOT NULL"; ; str = null)
    {
      setSelection(str);
      return;
    }
  }

  private void buildSortedMatrixCursor(EsMatrixCursor paramEsMatrixCursor, HashMap<String, PersonData> paramHashMap)
  {
    int i = paramEsMatrixCursor.getColumnIndex("_id");
    int j = paramEsMatrixCursor.getColumnIndex("person_id");
    final int k = paramEsMatrixCursor.getColumnIndex("name");
    int m = paramEsMatrixCursor.getColumnIndex("gaia_id");
    ArrayList localArrayList = new ArrayList();
    int n = 0;
    Iterator localIterator1 = paramHashMap.entrySet().iterator();
    while (localIterator1.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator1.next();
      String str = (String)localEntry.getKey();
      PersonData localPersonData = (PersonData)localEntry.getValue();
      Object[] arrayOfObject = new Object[this.mProjection.length];
      int i1 = n + 1;
      arrayOfObject[i] = Integer.valueOf(n);
      arrayOfObject[j] = str;
      arrayOfObject[k] = localPersonData.getName();
      if (!TextUtils.isEmpty(localPersonData.getObfuscatedId()))
        arrayOfObject[m] = localPersonData.getObfuscatedId();
      localArrayList.add(arrayOfObject);
      n = i1;
    }
    Collections.sort(localArrayList, new Comparator()
    {
    });
    Iterator localIterator2 = localArrayList.iterator();
    while (localIterator2.hasNext())
      paramEsMatrixCursor.addRow((Object[])localIterator2.next());
  }

  private boolean removePeopleInMyCircles(HashMap<String, PersonData> paramHashMap)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("person_id IN(");
    for (int i = 0; i < this.mPeopleMap.size(); i++)
    {
      if (i > 0)
        localStringBuilder.append(',');
      localStringBuilder.append('?');
    }
    localStringBuilder.append(')');
    String str = localStringBuilder.toString();
    String[] arrayOfString = (String[])this.mPeopleMap.keySet().toArray(new String[0]);
    Cursor localCursor = EsPeopleData.getPeople(getContext(), this.mAccount, null, null, PROJECTION, str, arrayOfString);
    if (localCursor == null);
    for (boolean bool = false; ; bool = true)
    {
      return bool;
      try
      {
        while (localCursor.moveToNext())
          if (localCursor.getInt(1) != 0)
            paramHashMap.remove(localCursor.getString(0));
      }
      finally
      {
        localCursor.close();
      }
    }
  }

  public final Cursor esLoadInBackground()
  {
    EsMatrixCursor localEsMatrixCursor = new EsMatrixCursor(this.mProjection);
    if (this.mPeopleMap.size() == 0);
    while (true)
    {
      return localEsMatrixCursor;
      HashMap localHashMap = new HashMap(this.mPeopleMap);
      if (!removePeopleInMyCircles(localHashMap))
        localEsMatrixCursor = null;
      else if (!localHashMap.isEmpty())
        buildSortedMatrixCursor(localEsMatrixCursor, localHashMap);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PeopleNotInCirclesLoader
 * JD-Core Version:    0.6.2
 */