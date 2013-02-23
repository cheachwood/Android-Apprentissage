package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class LocalEntityInfo extends GenericJson
{
  public BusinessHoursField businessHours;
  public LocalCategories categories;
  public String cid;
  public LocalFieldsWithDiff localFieldsWithDiff;
  public SearchContext localSearchContext;
  public FrontendPaperProto paper;
  public PriceRangeField priceRange;
  public String type;
  public String verificationStatus;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LocalEntityInfo
 * JD-Core Version:    0.6.2
 */