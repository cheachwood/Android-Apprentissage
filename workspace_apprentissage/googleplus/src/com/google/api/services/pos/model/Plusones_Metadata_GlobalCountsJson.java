package com.google.api.services.pos.model;

import com.google.android.apps.plus.json.EsJson;

public final class Plusones_Metadata_GlobalCountsJson extends EsJson<Plusones.Metadata.GlobalCounts>
{
  static final Plusones_Metadata_GlobalCountsJson INSTANCE = new Plusones_Metadata_GlobalCountsJson();

  private Plusones_Metadata_GlobalCountsJson()
  {
    super(Plusones.Metadata.GlobalCounts.class, new Object[] { "count", Plusones_Metadata_GlobalCounts_PersonJson.class, "person" });
  }

  public static Plusones_Metadata_GlobalCountsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.pos.model.Plusones_Metadata_GlobalCountsJson
 * JD-Core Version:    0.6.2
 */