package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class RenderedSharingRosters extends GenericJson
{
  public List<ApplicationSharingPolicy> applicationPolicies;
  public DasherDomain domain;
  public List<ResourceSharingRoster> resourceSharingRosters;
  public List<SharingTarget> targets;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RenderedSharingRosters
 * JD-Core Version:    0.6.2
 */