package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MutateProfileResponseJson extends EsJson<MutateProfileResponse>
{
  static final MutateProfileResponseJson INSTANCE = new MutateProfileResponseJson();

  private MutateProfileResponseJson()
  {
    super(MutateProfileResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "errorCode", "errorMessage", SimpleProfileJson.class, "updatedProfile" });
  }

  public static MutateProfileResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MutateProfileResponseJson
 * JD-Core Version:    0.6.2
 */