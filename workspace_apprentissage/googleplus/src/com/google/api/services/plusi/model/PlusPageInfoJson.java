package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlusPageInfoJson extends EsJson<PlusPageInfo>
{
  static final PlusPageInfoJson INSTANCE = new PlusPageInfoJson();

  private PlusPageInfoJson()
  {
    super(PlusPageInfo.class, new Object[] { MetadataJson.class, "metadata", "plusPageBlocked", "type" });
  }

  public static PlusPageInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusPageInfoJson
 * JD-Core Version:    0.6.2
 */