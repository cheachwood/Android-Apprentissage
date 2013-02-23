package com.google.api.services.pos.model;

import com.google.android.apps.plus.json.EsJson;

public final class Plusones_MetadataJson extends EsJson<Plusones.Metadata>
{
  static final Plusones_MetadataJson INSTANCE = new Plusones_MetadataJson();

  private Plusones_MetadataJson()
  {
    super(Plusones.Metadata.class, arrayOfObject);
  }

  public static Plusones_MetadataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.pos.model.Plusones_MetadataJson
 * JD-Core Version:    0.6.2
 */