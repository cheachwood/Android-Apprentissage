package com.google.android.apps.plus.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import java.util.ArrayList;
import java.util.HashMap;

public final class AndroidContactSearchLoader extends EsCursorLoader
{
  private static final String[] EMAIL_PROJECTION = { "lookup", "display_name", "data1" };
  private static final String[] PHONE_PROJECTION = { "lookup", "display_name", "data1", "data2", "data3" };
  private boolean mIncludePhoneNumbers;
  private final int mMinQueryLength;
  private final String[] mProjection;
  private final String mQuery;

  public AndroidContactSearchLoader(Context paramContext, String[] paramArrayOfString, String paramString, int paramInt, boolean paramBoolean)
  {
    super(paramContext);
    this.mProjection = paramArrayOfString;
    this.mQuery = paramString;
    this.mMinQueryLength = 2;
    this.mIncludePhoneNumbers = paramBoolean;
  }

  private void addPhoneNumberRows(EsMatrixCursor paramEsMatrixCursor, HashMap<String, ArrayList<PhoneNumber>> paramHashMap, String paramString)
  {
    ArrayList localArrayList = (ArrayList)paramHashMap.get(paramString);
    if (localArrayList == null);
    while (true)
    {
      return;
      for (int i = 0; i < localArrayList.size(); i++)
      {
        PhoneNumber localPhoneNumber = (PhoneNumber)localArrayList.get(i);
        Object[] arrayOfObject = new Object[this.mProjection.length];
        int j = 0;
        if (j < this.mProjection.length)
        {
          String str = this.mProjection[j];
          if ("person_id".equals(str))
            arrayOfObject[j] = ("p:" + localPhoneNumber.phoneNumber);
          while (true)
          {
            j++;
            break;
            if ("lookup_key".equals(str))
              arrayOfObject[j] = localPhoneNumber.lookupKey;
            else if ("name".equals(str))
              arrayOfObject[j] = localPhoneNumber.name;
            else if ("phone".equals(str))
              arrayOfObject[j] = localPhoneNumber.phoneNumber;
            else if ("phone_type".equals(str))
              arrayOfObject[j] = localPhoneNumber.phoneType;
          }
        }
        paramEsMatrixCursor.addRow(arrayOfObject);
      }
      paramHashMap.remove(paramString);
    }
  }

  private Object[] buildEmailRow(Cursor paramCursor)
  {
    String str1 = paramCursor.getString(2);
    Object[] arrayOfObject = new Object[this.mProjection.length];
    int i = 0;
    if (i < this.mProjection.length)
    {
      String str2 = this.mProjection[i];
      if ("person_id".equals(str2))
        arrayOfObject[i] = ("e:" + str1);
      while (true)
      {
        i++;
        break;
        if ("lookup_key".equals(str2))
          arrayOfObject[i] = paramCursor.getString(0);
        else if ("name".equals(str2))
          arrayOfObject[i] = paramCursor.getString(1);
        else if ("email".equals(str2))
          arrayOfObject[i] = str1;
      }
    }
    return arrayOfObject;
  }

  private Cursor findEmailAddresses()
  {
    EsMatrixCursor localEsMatrixCursor = new EsMatrixCursor(this.mProjection);
    Uri localUri = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Email.CONTENT_FILTER_URI, Uri.encode(this.mQuery));
    Cursor localCursor = getContext().getContentResolver().query(localUri, EMAIL_PROJECTION, null, null, null);
    try
    {
      while (localCursor.moveToNext())
        if (!TextUtils.isEmpty(localCursor.getString(2)))
          localEsMatrixCursor.addRow(buildEmailRow(localCursor));
    }
    finally
    {
      localCursor.close();
    }
    return localEsMatrixCursor;
  }

  private Cursor findEmailAddressesAndPhoneNumbers()
  {
    EsMatrixCursor localEsMatrixCursor = new EsMatrixCursor(this.mProjection);
    ContentResolver localContentResolver = getContext().getContentResolver();
    Resources localResources = getContext().getResources();
    ArrayList localArrayList1 = new ArrayList();
    HashMap localHashMap = new HashMap();
    Cursor localCursor1 = localContentResolver.query(Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, Uri.encode(this.mQuery)), PHONE_PROJECTION, null, null, null);
    try
    {
      while (localCursor1.moveToNext())
      {
        String str2 = localCursor1.getString(2);
        if (!TextUtils.isEmpty(str2))
        {
          String str3 = localCursor1.getString(0);
          PhoneNumber localPhoneNumber = new PhoneNumber((byte)0);
          localPhoneNumber.lookupKey = str3;
          localPhoneNumber.name = localCursor1.getString(1);
          localPhoneNumber.phoneNumber = str2;
          CharSequence localCharSequence = ContactsContract.CommonDataKinds.Phone.getTypeLabel(localResources, localCursor1.getInt(3), localCursor1.getString(4));
          if (localCharSequence != null)
            localPhoneNumber.phoneType = localCharSequence.toString();
          ArrayList localArrayList2 = (ArrayList)localHashMap.get(str3);
          if (localArrayList2 == null)
          {
            localArrayList2 = new ArrayList();
            localHashMap.put(str3, localArrayList2);
            localArrayList1.add(str3);
          }
          localArrayList2.add(localPhoneNumber);
        }
      }
    }
    finally
    {
      localCursor1.close();
    }
    Cursor localCursor2 = localContentResolver.query(Uri.withAppendedPath(ContactsContract.CommonDataKinds.Email.CONTENT_FILTER_URI, Uri.encode(this.mQuery)), EMAIL_PROJECTION, null, null, null);
    Object localObject2 = null;
    try
    {
      while (localCursor2.moveToNext())
      {
        String str1 = localCursor2.getString(0);
        if (!str1.equals(localObject2))
        {
          addPhoneNumberRows(localEsMatrixCursor, localHashMap, localObject2);
          localObject2 = str1;
        }
        if (!TextUtils.isEmpty(localCursor2.getString(2)))
          localEsMatrixCursor.addRow(buildEmailRow(localCursor2));
      }
    }
    finally
    {
      localCursor2.close();
    }
    if (localObject2 != null)
      addPhoneNumberRows(localEsMatrixCursor, localHashMap, localObject2);
    localCursor2.close();
    for (int i = 0; i < localArrayList1.size(); i++)
      addPhoneNumberRows(localEsMatrixCursor, localHashMap, (String)localArrayList1.get(i));
    return localEsMatrixCursor;
  }

  public final Cursor esLoadInBackground()
  {
    Object localObject;
    if ((TextUtils.isEmpty(this.mQuery)) || (this.mQuery.length() < this.mMinQueryLength))
      localObject = new EsMatrixCursor(this.mProjection);
    while (true)
    {
      return localObject;
      if (this.mIncludePhoneNumbers)
        localObject = findEmailAddressesAndPhoneNumbers();
      else
        localObject = findEmailAddresses();
    }
  }

  private static final class PhoneNumber
  {
    String lookupKey;
    String name;
    String phoneNumber;
    String phoneType;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.AndroidContactSearchLoader
 * JD-Core Version:    0.6.2
 */