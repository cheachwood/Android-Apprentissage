package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class NickNameJson extends EsJson<NickName>
{
  static final NickNameJson INSTANCE = new NickNameJson();

  private NickNameJson()
  {
    super(NickName.class, new Object[] { MetadataJson.class, "metadata", "value" });
  }

  public static NickNameJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.NickNameJson
 * JD-Core Version:    0.6.2
 */