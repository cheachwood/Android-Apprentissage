package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ResourceSharingRosterJson extends EsJson<ResourceSharingRoster>
{
  static final ResourceSharingRosterJson INSTANCE = new ResourceSharingRosterJson();

  private ResourceSharingRosterJson()
  {
    super(ResourceSharingRoster.class, new Object[] { SharedResourceIdJson.class, "id", SharingRosterJson.class, "sharingRoster" });
  }

  public static ResourceSharingRosterJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ResourceSharingRosterJson
 * JD-Core Version:    0.6.2
 */