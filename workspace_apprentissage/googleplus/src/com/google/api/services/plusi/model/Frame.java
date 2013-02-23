package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class Frame extends GenericJson
{
  public String domain;
  public EmbedClientItem embed;
  public FrameEmbedDuplicateFields embedDupes;
  public String iconUrl;
  public Boolean isPrivate;
  public Source source;
  public Long timestampMsec;
  public Verb verb;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.Frame
 * JD-Core Version:    0.6.2
 */