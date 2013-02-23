package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedLocalWriteReviewInfoJson extends EsJson<LoggedLocalWriteReviewInfo>
{
  static final LoggedLocalWriteReviewInfoJson INSTANCE = new LoggedLocalWriteReviewInfoJson();

  private LoggedLocalWriteReviewInfoJson()
  {
    super(LoggedLocalWriteReviewInfo.class, new Object[] { "alsoShared", "clusterId", "newReview", "source", "textCommentLength" });
  }

  public static LoggedLocalWriteReviewInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedLocalWriteReviewInfoJson
 * JD-Core Version:    0.6.2
 */