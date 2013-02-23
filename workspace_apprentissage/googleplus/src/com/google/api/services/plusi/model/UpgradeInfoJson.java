package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UpgradeInfoJson extends EsJson<UpgradeInfo>
{
  static final UpgradeInfoJson INSTANCE = new UpgradeInfoJson();

  private UpgradeInfoJson()
  {
    super(UpgradeInfo.class, new Object[] { "continueId", "errorType", "thirdPartyOptinCheckboxUnchecked", "unacceptableNameTermsCheckboxChecked", "upgradeCompleted", "upgradeType" });
  }

  public static UpgradeInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UpgradeInfoJson
 * JD-Core Version:    0.6.2
 */