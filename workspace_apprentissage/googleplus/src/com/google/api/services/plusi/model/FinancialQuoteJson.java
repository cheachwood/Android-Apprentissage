package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class FinancialQuoteJson extends EsJson<FinancialQuote>
{
  static final FinancialQuoteJson INSTANCE = new FinancialQuoteJson();

  private FinancialQuoteJson()
  {
    super(FinancialQuote.class, new Object[] { "dataSource", "dataSourceDisclaimerUrl", "description", "exchange", "exchangeTimezone", "imageUrl", "localizedQuoteTimestamp", "name", "price", "priceChange", "priceChangeNumber", "priceChangePercent", "priceCurrency", "quoteTime", "tickerSymbol", "url" });
  }

  public static FinancialQuoteJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FinancialQuoteJson
 * JD-Core Version:    0.6.2
 */