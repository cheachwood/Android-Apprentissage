package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DeletePlaceReviewResponseJson extends EsJson<DeletePlaceReviewResponse>
{
  static final DeletePlaceReviewResponseJson INSTANCE = new DeletePlaceReviewResponseJson();

  private DeletePlaceReviewResponseJson()
  {
    super(DeletePlaceReviewResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "zipitVersionInfo" });
  }

  public static DeletePlaceReviewResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DeletePlaceReviewResponseJson
 * JD-Core Version:    0.6.2
 */