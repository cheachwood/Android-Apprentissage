package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class Comment extends GenericJson
{
  public String authorName;
  public String authorPhotoUrl;
  public String authorProfileUrl;
  public String commentId;
  public String humanReadableTimestamp;
  public Boolean isAuthorBlockedByViewer;
  public Boolean isContact;
  public Boolean isOwnedByViewer;
  public Boolean isSpam;
  public Boolean isTextLong;
  public Boolean isViewerFollowing;
  public String moderationState;
  public String obfuscatedId;
  public String originalText;
  public EditSegments originalTextSegments;
  public DataPlusOne plusone;
  public RelativeDateInfo relativeDateInfo;
  public String text;
  public ViewSegments textSegments;
  public Long timestamp;
  public String updateId;
  public Long updatedTimestampUsec;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.Comment
 * JD-Core Version:    0.6.2
 */