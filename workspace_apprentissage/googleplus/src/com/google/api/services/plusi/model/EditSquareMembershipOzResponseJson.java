package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EditSquareMembershipOzResponseJson extends EsJson<EditSquareMembershipOzResponse>
{
  static final EditSquareMembershipOzResponseJson INSTANCE = new EditSquareMembershipOzResponseJson();

  private EditSquareMembershipOzResponseJson()
  {
    super(EditSquareMembershipOzResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "isViewerBlockingModerator", ViewerSquareJson.class, "viewerSquare" });
  }

  public static EditSquareMembershipOzResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EditSquareMembershipOzResponseJson
 * JD-Core Version:    0.6.2
 */