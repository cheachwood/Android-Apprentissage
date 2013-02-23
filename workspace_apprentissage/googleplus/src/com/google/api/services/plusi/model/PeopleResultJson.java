package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PeopleResultJson extends EsJson<PeopleResult>
{
  static final PeopleResultJson INSTANCE = new PeopleResultJson();

  private PeopleResultJson()
  {
    super(PeopleResult.class, new Object[] { "dominant", DataCircleMemberIdJson.class, "memberId", DataCircleMemberPropertiesJson.class, "memberProperties", "relativeUrl", "snippetHtml" });
  }

  public static PeopleResultJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PeopleResultJson
 * JD-Core Version:    0.6.2
 */