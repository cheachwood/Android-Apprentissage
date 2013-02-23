package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class InputDataJson extends EsJson<InputData>
{
  static final InputDataJson INSTANCE = new InputDataJson();

  private InputDataJson()
  {
    super(InputData.class, new Object[] { "interestsAdded", "interestsRemoved", "invitesSent", ProfileCreationInfoJson.class, "profileCreationInfo", ProfileEditJson.class, "profileEdit", "socialCircleRawQuery", UpgradeInfoJson.class, "upgradeInfo" });
  }

  public static InputDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.InputDataJson
 * JD-Core Version:    0.6.2
 */