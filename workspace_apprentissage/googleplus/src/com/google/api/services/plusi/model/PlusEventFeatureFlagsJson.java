package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlusEventFeatureFlagsJson extends EsJson<PlusEventFeatureFlags>
{
  static final PlusEventFeatureFlagsJson INSTANCE = new PlusEventFeatureFlagsJson();

  private PlusEventFeatureFlagsJson()
  {
    super(PlusEventFeatureFlags.class, new Object[] { "broadcast", "canDuplicateInvitees", "canSendMessage", "hangout", "hideGuestList", "openEventAcl", "openPhotoAcl" });
  }

  public static PlusEventFeatureFlagsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusEventFeatureFlagsJson
 * JD-Core Version:    0.6.2
 */