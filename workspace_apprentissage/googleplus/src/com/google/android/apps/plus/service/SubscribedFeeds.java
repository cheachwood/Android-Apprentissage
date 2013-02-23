package com.google.android.apps.plus.service;

import android.net.Uri;
import android.provider.BaseColumns;

final class SubscribedFeeds
{
  public static final class Feeds
    implements BaseColumns
  {
    public static final Uri CONTENT_URI = Uri.parse("content://subscribedfeeds/feeds");
    public static final Uri DELETED_CONTENT_URI = Uri.parse("content://subscribedfeeds/deleted_feeds");
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.SubscribedFeeds
 * JD-Core Version:    0.6.2
 */