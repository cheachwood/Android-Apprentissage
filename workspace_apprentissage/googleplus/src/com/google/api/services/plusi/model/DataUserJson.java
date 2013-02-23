package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataUserJson extends EsJson<DataUser>
{
  static final DataUserJson INSTANCE = new DataUserJson();

  private DataUserJson()
  {
    super(DataUser.class, new Object[] { "deprecatedEmailAddress", "detectedFaceUrl", "displayName", "givenName", "id", "photoResultCount", "profilePhotoUrl", "profileUrl" });
  }

  public static DataUserJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataUserJson
 * JD-Core Version:    0.6.2
 */