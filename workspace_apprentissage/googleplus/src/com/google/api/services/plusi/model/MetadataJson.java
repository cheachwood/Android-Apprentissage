package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MetadataJson extends EsJson<Metadata>
{
  static final MetadataJson INSTANCE = new MetadataJson();

  private MetadataJson()
  {
    super(Metadata.class, new Object[] { "aclModelJson", "editable", "focusGroup", "publicAcl", "scope", SharingRosterJson.class, "sharingRoster", "useDefaultAcl" });
  }

  public static MetadataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MetadataJson
 * JD-Core Version:    0.6.2
 */