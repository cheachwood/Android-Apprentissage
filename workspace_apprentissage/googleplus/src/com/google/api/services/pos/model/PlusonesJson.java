package com.google.api.services.pos.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlusonesJson extends EsJson<Plusones>
{
  static final PlusonesJson INSTANCE = new PlusonesJson();

  private PlusonesJson()
  {
    super(Plusones.class, new Object[] { "abtk", "aclJson", "id", "isSetByViewer", "kind", Plusones_MetadataJson.class, "metadata" });
  }

  public static PlusonesJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.pos.model.PlusonesJson
 * JD-Core Version:    0.6.2
 */