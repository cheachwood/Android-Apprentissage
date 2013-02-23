package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoadCircleMembersResponseJson extends EsJson<LoadCircleMembersResponse>
{
  static final LoadCircleMembersResponseJson INSTANCE = new LoadCircleMembersResponseJson();

  private LoadCircleMembersResponseJson()
  {
    super(LoadCircleMembersResponse.class, arrayOfObject);
  }

  public static LoadCircleMembersResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoadCircleMembersResponseJson
 * JD-Core Version:    0.6.2
 */