package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class NavbarProtoJson extends EsJson<NavbarProto>
{
  static final NavbarProtoJson INSTANCE = new NavbarProtoJson();

  private NavbarProtoJson()
  {
    super(NavbarProto.class, new Object[] { "baseNavUrl", "nextPageUrl", "numItems", "numValidNeeded", "numValidResults", "prevPageUrl", "start" });
  }

  public static NavbarProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.NavbarProtoJson
 * JD-Core Version:    0.6.2
 */