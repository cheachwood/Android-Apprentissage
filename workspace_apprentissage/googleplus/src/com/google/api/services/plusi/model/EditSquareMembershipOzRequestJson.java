package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EditSquareMembershipOzRequestJson extends EsJson<EditSquareMembershipOzRequest>
{
  static final EditSquareMembershipOzRequestJson INSTANCE = new EditSquareMembershipOzRequestJson();

  private EditSquareMembershipOzRequestJson()
  {
    super(EditSquareMembershipOzRequest.class, new Object[] { "action", ApiaryFieldsJson.class, "commonFields", "enableTracing", "obfuscatedSquareId", "targetObfuscatedGaiaId" });
  }

  public static EditSquareMembershipOzRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EditSquareMembershipOzRequestJson
 * JD-Core Version:    0.6.2
 */