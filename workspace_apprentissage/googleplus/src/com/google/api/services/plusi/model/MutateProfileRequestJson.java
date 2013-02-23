package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MutateProfileRequestJson extends EsJson<MutateProfileRequest>
{
  static final MutateProfileRequestJson INSTANCE = new MutateProfileRequestJson();

  private MutateProfileRequestJson()
  {
    super(MutateProfileRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", SimpleProfileJson.class, "profile" });
  }

  public static MutateProfileRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MutateProfileRequestJson
 * JD-Core Version:    0.6.2
 */