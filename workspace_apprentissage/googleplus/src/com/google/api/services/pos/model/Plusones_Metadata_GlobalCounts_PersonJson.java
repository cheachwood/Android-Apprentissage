package com.google.api.services.pos.model;

import com.google.android.apps.plus.json.EsJson;

public final class Plusones_Metadata_GlobalCounts_PersonJson extends EsJson<Plusones.Metadata.GlobalCounts.Person>
{
  static final Plusones_Metadata_GlobalCounts_PersonJson INSTANCE = new Plusones_Metadata_GlobalCounts_PersonJson();

  private Plusones_Metadata_GlobalCounts_PersonJson()
  {
    super(Plusones.Metadata.GlobalCounts.Person.class, new Object[] { "displayName", "id", "profileUrl", "thumbnailUrl" });
  }

  public static Plusones_Metadata_GlobalCounts_PersonJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.pos.model.Plusones_Metadata_GlobalCounts_PersonJson
 * JD-Core Version:    0.6.2
 */