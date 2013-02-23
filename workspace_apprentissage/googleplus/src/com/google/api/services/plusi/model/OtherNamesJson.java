package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OtherNamesJson extends EsJson<OtherNames>
{
  static final OtherNamesJson INSTANCE = new OtherNamesJson();

  private OtherNamesJson()
  {
    super(OtherNames.class, new Object[] { MetadataJson.class, "metadata", OtherNameJson.class, "name" });
  }

  public static OtherNamesJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OtherNamesJson
 * JD-Core Version:    0.6.2
 */