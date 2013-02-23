package com.google.android.apps.plus.fragments;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class PeopleSearchResults
  implements Parcelable
{
  public static final Parcelable.Creator<PeopleSearchResults> CREATOR = new Parcelable.Creator()
  {
  };
  private static final String[] PROJECTION = { "_id", "person_id", "lookup_key", "gaia_id", "name", "profile_type", "avatar", "packed_circle_ids", "matched_email", "email", "phone", "phone_type", "snippet" };
  private final ArrayList<Contact> mContacts = new ArrayList();
  private EsMatrixCursor mCursor;
  private boolean mCursorValid;
  private final HashMap<String, String> mGaiaIdsAndCircles = new HashMap();
  private boolean mGaiaIdsAndCirclesLoaded = false;
  private boolean mHasMoreResults;
  private boolean mIncludePeopleInCircles;
  private final ArrayList<LocalProfile> mLocalProfiles = new ArrayList();
  private boolean mLocalProfilesLoaded = false;
  private String mMyPersonId;
  private long mNextId;
  private final ArrayList<PublicProfile> mPublicProfiles = new ArrayList();
  private String mQuery;
  private String mToken;

  public PeopleSearchResults()
  {
    this.mIncludePeopleInCircles = true;
  }

  public PeopleSearchResults(Parcel paramParcel)
  {
    this.mIncludePeopleInCircles = bool1;
    this.mMyPersonId = paramParcel.readString();
    this.mQuery = paramParcel.readString();
    this.mToken = paramParcel.readString();
    boolean bool2;
    if (paramParcel.readInt() != 0)
    {
      bool2 = bool1;
      this.mHasMoreResults = bool2;
      if (paramParcel.readInt() == 0)
        break label205;
    }
    while (true)
    {
      this.mIncludePeopleInCircles = bool1;
      int i = paramParcel.readInt();
      for (int j = 0; j < i; j++)
      {
        String str1 = paramParcel.readString();
        String str2 = paramParcel.readString();
        String str3 = paramParcel.readString();
        int k = paramParcel.readInt();
        String str4 = paramParcel.readString();
        String str5 = paramParcel.readString();
        this.mPublicProfiles.add(new PublicProfile(str1, str2, str3, k, str4, str5));
      }
      bool2 = false;
      break;
      label205: bool1 = false;
    }
  }

  public static void onFinishContacts()
  {
  }

  public final void addContact(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    this.mContacts.add(new Contact(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6));
  }

  public final void addGaiaIdAndCircles(String paramString1, String paramString2)
  {
    this.mGaiaIdsAndCircles.put(paramString1, paramString2);
  }

  public final void addLocalProfile(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    if (paramString1.equals(this.mMyPersonId));
    while (true)
    {
      return;
      this.mLocalProfiles.add(new LocalProfile(paramString1, paramString2, paramString3, paramInt, paramString4, paramString5, paramString6, null, null));
    }
  }

  public final void addPublicProfile(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5)
  {
    if (paramString1.equals(this.mMyPersonId));
    while (true)
    {
      return;
      this.mPublicProfiles.add(new PublicProfile(paramString1, paramString2, paramString3, paramInt, paramString4, paramString5));
      this.mCursorValid = false;
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public final int getCount()
  {
    return getCursor().getCount();
  }

  public final Cursor getCursor()
  {
    EsMatrixCursor localEsMatrixCursor1;
    if (this.mCursorValid)
      localEsMatrixCursor1 = this.mCursor;
    while (true)
    {
      return localEsMatrixCursor1;
      this.mCursor = new EsMatrixCursor(PROJECTION);
      this.mCursorValid = true;
      if ((!this.mLocalProfilesLoaded) || (!this.mGaiaIdsAndCirclesLoaded))
      {
        localEsMatrixCursor1 = this.mCursor;
      }
      else
      {
        HashSet localHashSet1 = new HashSet();
        HashSet localHashSet2 = new HashSet();
        HashSet localHashSet3 = new HashSet();
        if (this.mIncludePeopleInCircles)
        {
          Iterator localIterator3 = this.mLocalProfiles.iterator();
          while (localIterator3.hasNext())
          {
            LocalProfile localLocalProfile = (LocalProfile)localIterator3.next();
            String str6 = localLocalProfile.gaiaId;
            String str7 = localLocalProfile.email;
            EsMatrixCursor localEsMatrixCursor4 = this.mCursor;
            Object[] arrayOfObject5 = new Object[13];
            long l4 = this.mNextId;
            this.mNextId = (1L + l4);
            arrayOfObject5[0] = Long.valueOf(l4);
            arrayOfObject5[1] = localLocalProfile.personId;
            arrayOfObject5[2] = null;
            arrayOfObject5[3] = str6;
            arrayOfObject5[4] = localLocalProfile.name;
            arrayOfObject5[5] = Integer.valueOf(localLocalProfile.profileType);
            arrayOfObject5[6] = localLocalProfile.avatarUrl;
            arrayOfObject5[7] = localLocalProfile.packedCircleIds;
            arrayOfObject5[8] = str7;
            arrayOfObject5[9] = null;
            arrayOfObject5[10] = localLocalProfile.phoneNumber;
            arrayOfObject5[11] = localLocalProfile.phoneType;
            arrayOfObject5[12] = null;
            localEsMatrixCursor4.addRow(arrayOfObject5);
            localHashSet1.add(str6);
            localHashSet2.add(localLocalProfile.name);
            if (str7 != null)
              localHashSet3.add(str7);
          }
          Iterator localIterator4 = this.mPublicProfiles.iterator();
          while (localIterator4.hasNext())
          {
            PublicProfile localPublicProfile2 = (PublicProfile)localIterator4.next();
            String str4 = localPublicProfile2.gaiaId;
            if (!localHashSet1.contains(str4))
            {
              String str5 = (String)this.mGaiaIdsAndCircles.get(str4);
              if (!TextUtils.isEmpty(str5))
              {
                EsMatrixCursor localEsMatrixCursor3 = this.mCursor;
                Object[] arrayOfObject4 = new Object[13];
                long l3 = this.mNextId;
                this.mNextId = (1L + l3);
                arrayOfObject4[0] = Long.valueOf(l3);
                arrayOfObject4[1] = localPublicProfile2.personId;
                arrayOfObject4[2] = null;
                arrayOfObject4[3] = str4;
                arrayOfObject4[4] = localPublicProfile2.name;
                arrayOfObject4[5] = Integer.valueOf(localPublicProfile2.profileType);
                arrayOfObject4[6] = localPublicProfile2.avatarUrl;
                arrayOfObject4[7] = str5;
                arrayOfObject4[8] = null;
                arrayOfObject4[9] = null;
                arrayOfObject4[10] = null;
                arrayOfObject4[11] = null;
                arrayOfObject4[12] = localPublicProfile2.snippet;
                localEsMatrixCursor3.addRow(arrayOfObject4);
                localHashSet1.add(str4);
                localHashSet2.add(localPublicProfile2.name);
              }
            }
          }
        }
        if (!this.mContacts.isEmpty())
        {
          HashMap localHashMap = new HashMap();
          Iterator localIterator1 = this.mContacts.iterator();
          while (localIterator1.hasNext())
          {
            Contact localContact = (Contact)localIterator1.next();
            if (!localHashSet2.contains(localContact.name))
            {
              String str2 = localContact.email;
              if (!localHashSet3.contains(str2))
              {
                Object[] arrayOfObject2 = (Object[])localHashMap.get(str2);
                if (arrayOfObject2 != null)
                {
                  String str3 = (String)arrayOfObject2[4];
                  if (((TextUtils.isEmpty(str3)) || (str3.equalsIgnoreCase(str2))) && (!TextUtils.isEmpty(localContact.name)))
                  {
                    arrayOfObject2[1] = localContact.personId;
                    arrayOfObject2[2] = localContact.lookupKey;
                    arrayOfObject2[4] = localContact.name;
                    if (arrayOfObject2[10] == null)
                      arrayOfObject2[10] = localContact.phoneNumber;
                    if (arrayOfObject2[11] == null)
                      arrayOfObject2[11] = localContact.phoneType;
                  }
                }
                else
                {
                  Object[] arrayOfObject3 = new Object[13];
                  long l2 = this.mNextId;
                  this.mNextId = (1L + l2);
                  arrayOfObject3[0] = Long.valueOf(l2);
                  arrayOfObject3[1] = localContact.personId;
                  arrayOfObject3[2] = localContact.lookupKey;
                  arrayOfObject3[3] = null;
                  arrayOfObject3[4] = localContact.name;
                  arrayOfObject3[5] = Integer.valueOf(1);
                  arrayOfObject3[6] = null;
                  arrayOfObject3[7] = null;
                  arrayOfObject3[8] = null;
                  arrayOfObject3[9] = localContact.email;
                  arrayOfObject3[10] = localContact.phoneNumber;
                  arrayOfObject3[11] = localContact.phoneType;
                  arrayOfObject3[12] = null;
                  localHashMap.put(str2, arrayOfObject3);
                  this.mCursor.addRow(arrayOfObject3);
                }
              }
            }
          }
        }
        Iterator localIterator2 = this.mPublicProfiles.iterator();
        while (localIterator2.hasNext())
        {
          PublicProfile localPublicProfile1 = (PublicProfile)localIterator2.next();
          String str1 = localPublicProfile1.gaiaId;
          if ((!localHashSet1.contains(str1)) && (!this.mGaiaIdsAndCircles.containsKey(str1)))
          {
            EsMatrixCursor localEsMatrixCursor2 = this.mCursor;
            Object[] arrayOfObject1 = new Object[13];
            long l1 = this.mNextId;
            this.mNextId = (1L + l1);
            arrayOfObject1[0] = Long.valueOf(l1);
            arrayOfObject1[1] = localPublicProfile1.personId;
            arrayOfObject1[2] = null;
            arrayOfObject1[3] = str1;
            arrayOfObject1[4] = localPublicProfile1.name;
            arrayOfObject1[5] = Integer.valueOf(localPublicProfile1.profileType);
            arrayOfObject1[6] = localPublicProfile1.avatarUrl;
            arrayOfObject1[7] = null;
            arrayOfObject1[8] = null;
            arrayOfObject1[9] = null;
            arrayOfObject1[10] = null;
            arrayOfObject1[11] = null;
            arrayOfObject1[12] = localPublicProfile1.snippet;
            localEsMatrixCursor2.addRow(arrayOfObject1);
          }
        }
        localEsMatrixCursor1 = this.mCursor;
      }
    }
  }

  public final int getPublicProfileCount()
  {
    return this.mPublicProfiles.size();
  }

  public final String getQuery()
  {
    return this.mQuery;
  }

  public final String getToken()
  {
    return this.mToken;
  }

  public final boolean hasMoreResults()
  {
    return this.mHasMoreResults;
  }

  public final boolean isParcelable()
  {
    if (this.mLocalProfiles.size() + this.mPublicProfiles.size() <= 1000);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onFinishGaiaIdsAndCircles()
  {
    this.mGaiaIdsAndCirclesLoaded = true;
  }

  public final void onFinishLocalProfiles()
  {
    this.mLocalProfilesLoaded = true;
  }

  public final void onStartContacts()
  {
    this.mContacts.clear();
    this.mCursorValid = false;
  }

  public final void onStartGaiaIdsAndCircles()
  {
    this.mGaiaIdsAndCircles.clear();
    this.mCursorValid = false;
  }

  public final void onStartLocalProfiles()
  {
    this.mLocalProfiles.clear();
    this.mLocalProfilesLoaded = false;
    this.mCursorValid = false;
  }

  public final void setHasMoreResults(boolean paramBoolean)
  {
    this.mHasMoreResults = paramBoolean;
  }

  public final void setIncludePeopleInCircles(boolean paramBoolean)
  {
    this.mIncludePeopleInCircles = paramBoolean;
  }

  public final void setMyProfile(String paramString)
  {
    this.mMyPersonId = paramString;
  }

  public final void setQueryString(String paramString)
  {
    if (TextUtils.equals(this.mQuery, paramString));
    while (true)
    {
      return;
      this.mQuery = paramString;
      this.mLocalProfiles.clear();
      this.mPublicProfiles.clear();
      this.mLocalProfilesLoaded = false;
      this.mCursorValid = false;
      this.mToken = null;
    }
  }

  public final void setToken(String paramString)
  {
    this.mToken = paramString;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = 1;
    paramParcel.writeString(this.mMyPersonId);
    paramParcel.writeString(this.mQuery);
    paramParcel.writeString(this.mToken);
    int j;
    if (this.mHasMoreResults)
    {
      j = i;
      paramParcel.writeInt(j);
      if (!this.mIncludePeopleInCircles)
        break label159;
    }
    while (true)
    {
      paramParcel.writeInt(i);
      int k = this.mPublicProfiles.size();
      paramParcel.writeInt(k);
      for (int m = 0; m < k; m++)
      {
        PublicProfile localPublicProfile = (PublicProfile)this.mPublicProfiles.get(m);
        paramParcel.writeString(localPublicProfile.personId);
        paramParcel.writeString(localPublicProfile.gaiaId);
        paramParcel.writeString(localPublicProfile.name);
        paramParcel.writeInt(localPublicProfile.profileType);
        paramParcel.writeString(localPublicProfile.avatarUrl);
        paramParcel.writeString(localPublicProfile.snippet);
      }
      j = 0;
      break;
      label159: i = 0;
    }
  }

  private static final class Contact extends PeopleSearchResults.Profile
  {
    String email;
    String lookupKey;
    String phoneNumber;
    String phoneType;

    Contact(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
    {
      super(null, paramString3, 1, null);
      this.lookupKey = paramString2;
      this.email = paramString4;
      this.phoneNumber = paramString5;
      this.phoneType = paramString6;
    }
  }

  private static final class LocalProfile extends PeopleSearchResults.Profile
  {
    String email;
    String packedCircleIds;
    String phoneNumber;
    String phoneType;

    LocalProfile(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
    {
      super(paramString2, paramString3, paramInt, paramString4);
      this.packedCircleIds = paramString5;
      this.email = paramString6;
      this.phoneNumber = paramString7;
      this.phoneType = paramString8;
    }
  }

  private static abstract class Profile
  {
    String avatarUrl;
    String gaiaId;
    String name;
    String personId;
    int profileType;

    Profile(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4)
    {
      this.personId = paramString1;
      this.gaiaId = paramString2;
      this.name = paramString3;
      this.profileType = paramInt;
      this.avatarUrl = paramString4;
    }
  }

  private static final class PublicProfile extends PeopleSearchResults.Profile
  {
    String snippet;

    PublicProfile(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5)
    {
      super(paramString2, paramString3, paramInt, paramString4);
      this.snippet = paramString5;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PeopleSearchResults
 * JD-Core Version:    0.6.2
 */