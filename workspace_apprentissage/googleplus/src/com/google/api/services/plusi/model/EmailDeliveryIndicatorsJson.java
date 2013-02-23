package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EmailDeliveryIndicatorsJson extends EsJson<EmailDeliveryIndicators>
{
  static final EmailDeliveryIndicatorsJson INSTANCE = new EmailDeliveryIndicatorsJson();

  private EmailDeliveryIndicatorsJson()
  {
    super(EmailDeliveryIndicators.class, new Object[] { "email", "obfuscatedGaiaUserId" });
  }

  public static EmailDeliveryIndicatorsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EmailDeliveryIndicatorsJson
 * JD-Core Version:    0.6.2
 */