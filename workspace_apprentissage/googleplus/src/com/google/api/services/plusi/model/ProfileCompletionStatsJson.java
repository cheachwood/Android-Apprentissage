package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ProfileCompletionStatsJson extends EsJson<ProfileCompletionStats>
{
  static final ProfileCompletionStatsJson INSTANCE = new ProfileCompletionStatsJson();

  private ProfileCompletionStatsJson()
  {
    super(ProfileCompletionStats.class, new Object[] { ProfileCompletionStatsFieldJson.class, "value" });
  }

  public static ProfileCompletionStatsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ProfileCompletionStatsJson
 * JD-Core Version:    0.6.2
 */