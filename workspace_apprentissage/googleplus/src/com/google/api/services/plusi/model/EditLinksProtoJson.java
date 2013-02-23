package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EditLinksProtoJson extends EsJson<EditLinksProto>
{
  static final EditLinksProtoJson INSTANCE = new EditLinksProtoJson();

  private EditLinksProtoJson()
  {
    super(EditLinksProto.class, new Object[] { "isLbcClaimed", "isPluspageVerificationBlocked", "isRapEnabled", PlacePageLinkJson.class, "lbcClaimLink", PlacePageLinkJson.class, "sesameEditLink", "useCombinedPeppy" });
  }

  public static EditLinksProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EditLinksProtoJson
 * JD-Core Version:    0.6.2
 */