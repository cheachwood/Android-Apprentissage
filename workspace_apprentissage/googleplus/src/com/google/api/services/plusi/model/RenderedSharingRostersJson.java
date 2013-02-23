package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class RenderedSharingRostersJson extends EsJson<RenderedSharingRosters>
{
  static final RenderedSharingRostersJson INSTANCE = new RenderedSharingRostersJson();

  private RenderedSharingRostersJson()
  {
    super(RenderedSharingRosters.class, new Object[] { ApplicationSharingPolicyJson.class, "applicationPolicies", DasherDomainJson.class, "domain", ResourceSharingRosterJson.class, "resourceSharingRosters", SharingTargetJson.class, "targets" });
  }

  public static RenderedSharingRostersJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RenderedSharingRostersJson
 * JD-Core Version:    0.6.2
 */