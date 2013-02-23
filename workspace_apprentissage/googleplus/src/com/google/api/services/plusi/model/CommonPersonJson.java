package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CommonPersonJson extends EsJson<CommonPerson>
{
  static final CommonPersonJson INSTANCE = new CommonPersonJson();

  private CommonPersonJson()
  {
    super(CommonPerson.class, new Object[] { "gender", "isContactSafe", "isViewerFollowing", "obfuscatedId", "photoUrl", "profileUrl", "userName" });
  }

  public static CommonPersonJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CommonPersonJson
 * JD-Core Version:    0.6.2
 */