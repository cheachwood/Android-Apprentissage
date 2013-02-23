package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientLoggedLocalWriteReviewInfoJson extends EsJson<ClientLoggedLocalWriteReviewInfo>
{
  static final ClientLoggedLocalWriteReviewInfoJson INSTANCE = new ClientLoggedLocalWriteReviewInfoJson();

  private ClientLoggedLocalWriteReviewInfoJson()
  {
    super(ClientLoggedLocalWriteReviewInfo.class, new Object[] { "alsoShared", "clusterId", "newReview", "source", "textCommentLength" });
  }

  public static ClientLoggedLocalWriteReviewInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedLocalWriteReviewInfoJson
 * JD-Core Version:    0.6.2
 */