package com.google.android.apps.plus.content;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.PrimitiveUtils;
import com.google.api.services.plusi.model.CommonConfig;
import com.google.api.services.plusi.model.SimpleProfile;
import com.google.api.services.plusi.model.SimpleProfileJson;
import com.google.api.services.plusi.model.SocialGraphData;

public final class PeopleData
{
  private static Factory sFactory = new Factory((byte)0);
  private final Context mContext;
  private final SQLiteDatabase mDb;
  private final ContentResolver mResolver;

  private PeopleData(Context paramContext, EsAccount paramEsAccount)
  {
    this.mContext = paramContext;
    this.mDb = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    this.mResolver = paramContext.getContentResolver();
  }

  public static Factory getFactory()
  {
    return sFactory;
  }

  public final void setBlockedState(String paramString1, String paramString2, boolean paramBoolean)
  {
    if (EsLog.isLoggable("PeopleData", 3))
      Log.d("PeopleData", "setBlockedState - User: " + paramString1 + "; isMuted: " + paramBoolean);
    this.mDb.beginTransaction();
    try
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("blocked", Boolean.valueOf(paramBoolean));
      if (paramBoolean)
        localContentValues.put("in_my_circles", Integer.valueOf(0));
      if ((this.mDb.update("contacts", localContentValues, "person_id=?", new String[] { paramString1 }) == 0) && (paramBoolean))
      {
        localContentValues.put("person_id", paramString1);
        if ((paramString1 != null) && (paramString1.startsWith("g:")))
        {
          str = paramString1.substring(2);
          localContentValues.put("gaia_id", str);
          localContentValues.put("name", paramString2);
          this.mDb.insert("contacts", null, localContentValues);
        }
      }
      else
      {
        if (paramBoolean)
        {
          this.mDb.delete("circle_contact", "link_person_id=?", new String[] { paramString1 });
          EsPeopleData.updateMemberCounts(this.mDb);
        }
        this.mDb.setTransactionSuccessful();
        this.mDb.endTransaction();
        this.mResolver.notifyChange(EsProvider.CONTACTS_URI, null);
        if (paramBoolean)
          this.mResolver.notifyChange(EsProvider.CIRCLES_URI, null);
        return;
      }
      String str = null;
    }
    finally
    {
      this.mDb.endTransaction();
    }
  }

  public final boolean setMuteState(String paramString, boolean paramBoolean)
  {
    if (EsLog.isLoggable("PeopleData", 3))
      Log.d("PeopleData", "setMuteState - User: " + paramString + "; isMuted: " + paramBoolean);
    String str = "g:" + paramString;
    this.mDb.beginTransaction();
    while (true)
    {
      try
      {
        Cursor localCursor = this.mDb.query("profiles", new String[] { "profile_proto" }, "profile_person_id=?", new String[] { str }, null, null, null);
        if (localCursor.moveToFirst())
        {
          arrayOfByte1 = localCursor.getBlob(0);
          localCursor.close();
          SimpleProfile localSimpleProfile;
          if (arrayOfByte1 == null)
          {
            localSimpleProfile = null;
            boolean bool1 = false;
            if (localSimpleProfile != null)
            {
              CommonConfig localCommonConfig = localSimpleProfile.config;
              bool1 = false;
              if (localCommonConfig != null)
              {
                SocialGraphData localSocialGraphData = localSimpleProfile.config.socialGraphData;
                bool1 = false;
                if (localSocialGraphData != null)
                {
                  boolean bool2 = PrimitiveUtils.safeBoolean(localSimpleProfile.config.socialGraphData.muted);
                  bool1 = false;
                  if (bool2 != paramBoolean)
                  {
                    localSimpleProfile.config.socialGraphData.muted = Boolean.valueOf(paramBoolean);
                    ContentValues localContentValues = new ContentValues();
                    if (localSimpleProfile != null)
                      continue;
                    localObject2 = null;
                    localContentValues.put("profile_proto", (byte[])localObject2);
                    this.mDb.update("profiles", localContentValues, "profile_person_id=?", new String[] { str });
                    bool1 = true;
                  }
                }
              }
            }
            this.mDb.setTransactionSuccessful();
            this.mDb.endTransaction();
            if (bool1)
              this.mResolver.notifyChange(Uri.withAppendedPath(EsProvider.CONTACT_BY_PERSON_ID_URI, str), null);
            return bool1;
          }
          else
          {
            if (arrayOfByte1 == null)
            {
              localSimpleProfile = null;
              continue;
            }
            localSimpleProfile = (SimpleProfile)SimpleProfileJson.getInstance().fromByteArray(arrayOfByte1);
            continue;
          }
          byte[] arrayOfByte2 = SimpleProfileJson.getInstance().toByteArray(localSimpleProfile);
          Object localObject2 = arrayOfByte2;
          continue;
        }
      }
      finally
      {
        this.mDb.endTransaction();
      }
      byte[] arrayOfByte1 = null;
    }
  }

  public static final class Factory
  {
    public static PeopleData getInstance(Context paramContext, EsAccount paramEsAccount)
    {
      return new PeopleData(paramContext, paramEsAccount, (byte)0);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.PeopleData
 * JD-Core Version:    0.6.2
 */