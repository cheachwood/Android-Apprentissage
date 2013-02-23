package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ScrapBook extends GenericJson
{
  public String albumId;
  public String albumUrl;
  public ScrapBookEntry coverPhotoEntry;
  public String defaultCoverPhotoUrl;
  public Metadata metadata;
  public List<ScrapBookEntry> plusiEntry;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ScrapBook
 * JD-Core Version:    0.6.2
 */