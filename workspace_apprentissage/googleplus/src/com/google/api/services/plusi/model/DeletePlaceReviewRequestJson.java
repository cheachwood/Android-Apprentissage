package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DeletePlaceReviewRequestJson extends EsJson<DeletePlaceReviewRequest>
{
  static final DeletePlaceReviewRequestJson INSTANCE = new DeletePlaceReviewRequestJson();

  private DeletePlaceReviewRequestJson()
  {
    super(DeletePlaceReviewRequest.class, new Object[] { "cid", ApiaryFieldsJson.class, "commonFields", "enableTracing", "reviewUrl" });
  }

  public static DeletePlaceReviewRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DeletePlaceReviewRequestJson
 * JD-Core Version:    0.6.2
 */