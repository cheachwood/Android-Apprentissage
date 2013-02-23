package com.google.api.services.pos.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class Plusones extends GenericJson
{
  public String abtk;
  public String aclJson;
  public String id;
  public Boolean isSetByViewer;
  public String kind;
  public Metadata metadata;

  public static final class Metadata extends GenericJson
  {
    public Long adgroupId;
    public Long creativeId;
    public GlobalCounts globalCounts;
    public String title;
    public String type;

    public static final class GlobalCounts extends GenericJson
    {
      public Double count;
      public List<Person> person;

      public static final class Person extends GenericJson
      {
        public String displayName;
        public String id;
        public String profileUrl;
        public String thumbnailUrl;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.pos.model.Plusones
 * JD-Core Version:    0.6.2
 */