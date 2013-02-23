package com.google.android.apps.plus.content;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Binder;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.phone.ScreenMetrics;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.util.AccountsUtil;
import com.google.android.apps.plus.util.EsLog;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class EsProvider extends ContentProvider
{
  private static final HashMap<String, String> ACCOUNTS_PROJECTION_MAP;
  public static final Uri ACCOUNT_STATUS_URI;
  private static final Uri ACTIVITIES_BY_CIRCLE_ID_URI;
  private static final Uri ACTIVITIES_STREAM_VIEW_URI;
  public static final Uri ACTIVITIES_URI;
  private static final HashMap<String, String> ACTIVITIES_VIEW_PROJECTION_MAP;
  private static final HashMap<String, String> ACTIVITY_SUMMARY_PROJECTION_MAP;
  public static final Uri ACTIVITY_SUMMARY_URI;
  public static final Uri ACTIVITY_VIEW_BY_ACTIVITY_ID_URI;
  public static final Uri ACTIVITY_VIEW_URI;
  public static final Uri ALBUM_VIEW_BY_ALBUM_AND_OWNER_URI;
  public static final Uri ALBUM_VIEW_BY_OWNER_URI;
  private static final HashMap<String, String> ALBUM_VIEW_MAP;
  private static final HashMap<String, String> CIRCLES_PROJECTION_MAP;
  public static final Uri CIRCLES_URI;
  public static final Uri COMMENTS_VIEW_BY_ACTIVITY_ID_URI;
  private static final HashMap<String, String> COMMENTS_VIEW_PROJECTION_MAP;
  private static final Uri COMMENTS_VIEW_URI;
  public static final Uri CONTACTS_BY_CIRCLE_ID_URI;
  public static final Uri CONTACTS_BY_SQUARE_ID_URI;
  private static final HashMap<String, String> CONTACTS_PROJECTION_MAP;
  public static final Uri CONTACTS_QUERY_URI;
  private static final HashMap<String, String> CONTACTS_SEARCH_PROJECTION_MAP;
  private static final HashMap<String, String> CONTACTS_SEARCH_WITH_PHONES_PROJECTION_MAP;
  public static final Uri CONTACTS_URI;
  public static final Uri CONTACT_BY_PERSON_ID_URI;
  private static final HashMap<String, String> CONVERSATIONS_PROJECTION_MAP;
  public static final Uri CONVERSATIONS_URI;
  private static final HashMap<String, String> EMOTISHARE_PROJECTION_MAP;
  public static final Uri EMOTISHARE_URI;
  public static final Uri EVENTS_ALL_URI;
  private static final HashMap<String, String> EVENT_PEOPLE_VIEW_MAP;
  private static final HashMap<String, String> HANGOUT_SUGGESTIONS_PROJECTION_MAP;
  public static final Uri HANGOUT_SUGGESTIONS_URI;
  private static final HashMap<String, String> LOCATION_QUERIES_VIEW_PROJECTION_MAP;
  private static final Uri LOCATION_QUERIES_VIEW_URI;
  private static final Uri MESSAGES_BY_CONVERSATION_URI;
  private static final HashMap<String, String> MESSAGES_PROJECTION_MAP;
  private static final HashMap<String, String> MESSAGE_NOTIFICATIONS_PROJECTION_MAP;
  public static final Uri MESSAGE_NOTIFICATIONS_URI;
  private static final HashMap<String, String> MESSENGER_SUGGESTIONS_PROJECTION_MAP;
  public static final Uri MESSENGER_SUGGESTIONS_URI;
  private static final HashMap<String, String> NETWORK_DATA_STATS_PROJECTION_MAP;
  public static final Uri NETWORK_DATA_STATS_URI;
  private static final HashMap<String, String> NETWORK_DATA_TRANSACTIONS_PROJECTION_MAP;
  public static final Uri NETWORK_DATA_TRANSACTIONS_URI;
  private static final HashMap<String, String> NOTIFICATIONS_PROJECTION_MAP;
  public static final Uri NOTIFICATIONS_URI;
  public static final Uri PANORAMA_IMAGE_URI;
  private static final HashMap<String, String> PARTICIPANTS_PROJECTION_MAP;
  private static final Uri PARTICIPANTS_URI;
  private static final HashMap<String, String> PHOTOS_BY_ALBUM_VIEW_MAP;
  private static final String PHOTOS_BY_ALBUM_VIEW_SQL;
  private static final HashMap<String, String> PHOTOS_BY_EVENT_VIEW_MAP;
  private static final String PHOTOS_BY_EVENT_VIEW_SQL;
  private static final HashMap<String, String> PHOTOS_BY_STREAM_VIEW_MAP;
  private static final String PHOTOS_BY_STREAM_VIEW_SQL;
  private static final HashMap<String, String> PHOTOS_BY_USER_VIEW_MAP;
  private static final String PHOTOS_BY_USER_VIEW_SQL;
  public static final Uri PHOTO_BY_ALBUM_URI;
  public static final Uri PHOTO_BY_EVENT_ID_URI;
  public static final Uri PHOTO_BY_PHOTO_ID_URI;
  public static final Uri PHOTO_BY_STREAM_ID_AND_OWNER_ID_URI;
  public static final Uri PHOTO_COMMENTS_BY_PHOTO_ID_URI;
  private static final HashMap<String, String> PHOTO_COMMENTS_MAP;
  private static final HashMap<String, String> PHOTO_HOME_MAP;
  public static final Uri PHOTO_HOME_URI;
  public static final Uri PHOTO_NOTIFICATION_COUNT_URI;
  private static final HashMap<String, String> PHOTO_NOTIFICATION_MAP;
  public static final Uri PHOTO_OF_USER_ID_URI;
  public static final Uri PHOTO_SHAPES_BY_PHOTO_ID_URI;
  private static final HashMap<String, String> PHOTO_SHAPE_VIEW_MAP;
  public static final Uri PHOTO_URI;
  private static final HashMap<String, String> PHOTO_VIEW_MAP;
  private static final String PHOTO_VIEW_SQL = String.format("CREATE VIEW %s AS SELECT photo._id as _id, photo.action_state as action_state, photo.album_id as album_id, photo.comment_count as comment_count, photo.description as description, photo.downloadable as downloadable, photo.entity_version as entity_version, photo.height as height, photo.owner_id as owner_id, photo.photo_id as photo_id, photo.fingerprint as fingerprint, photo.timestamp as timestamp, photo.title as title, photo.upload_status as upload_status, photo.url as url, photo.video_data as video_data, photo.is_panorama as is_panorama, photo.width as width, photo_plusone.plusone_count as plusone_count, photo_plusone.plusone_data as plusone_data, photo_plusone.plusone_by_me as plusone_by_me, photo_plusone.plusone_id as plusone_id, album.title as album_name, album.stream_id as album_stream, contacts.name as owner_name, contacts.avatar as owner_avatar_url, %s (SELECT a.status FROM account_status,photo_shape as a WHERE a.photo_id=photo.photo_id AND a.subject_id=account_status.user_id AND a.status='PENDING' LIMIT 1) AS pending_status FROM photo LEFT JOIN photo_plusone ON photo.photo_id=photo_plusone.photo_id LEFT JOIN album ON photo.album_id=album.album_id LEFT JOIN contacts ON photo.owner_id=contacts.gaia_id %s", new Object[] { "photo_view", "", "" });
  private static final HashMap<String, String> PLATFORM_AUDIENCE_PROJECTION_MAP;
  public static final Uri PLATFORM_AUDIENCE_URI;
  private static final HashMap<String, String> PLUS_PAGES_PROJECTION_MAP;
  public static final Uri PLUS_PAGES_URI;
  private static final HashMap<String, String> SQUARES_PROJECTION_MAP;
  public static final Uri SQUARES_URI;
  private static final HashMap<String, String> SQUARE_CONTACTS_PROJECTION_MAP;
  private static final HashMap<String, String> SUGGESTED_PEOPLE_PROJECTION_MAP;
  public static final Uri SUGGESTED_PEOPLE_URI;
  private static final UriMatcher URI_MATCHER;
  private static int sActivitiesFirstPageSize;
  private static int sActivitiesPageSize;

  static
  {
    PHOTOS_BY_ALBUM_VIEW_SQL = String.format("CREATE VIEW %s AS SELECT photo._id as _id, photo.action_state as action_state, photo.album_id as album_id, photo.comment_count as comment_count, photo.description as description, photo.downloadable as downloadable, photo.entity_version as entity_version, photo.height as height, photo.owner_id as owner_id, photo.photo_id as photo_id, photo.fingerprint as fingerprint, photo.timestamp as timestamp, photo.title as title, photo.upload_status as upload_status, photo.url as url, photo.video_data as video_data, photo.is_panorama as is_panorama, photo.width as width, photo_plusone.plusone_count as plusone_count, photo_plusone.plusone_data as plusone_data, photo_plusone.plusone_by_me as plusone_by_me, photo_plusone.plusone_id as plusone_id, album.title as album_name, album.stream_id as album_stream, contacts.name as owner_name, contacts.avatar as owner_avatar_url, %s (SELECT a.status FROM account_status,photo_shape as a WHERE a.photo_id=photo.photo_id AND a.subject_id=account_status.user_id AND a.status='PENDING' LIMIT 1) AS pending_status FROM photo LEFT JOIN photo_plusone ON photo.photo_id=photo_plusone.photo_id LEFT JOIN album ON photo.album_id=album.album_id LEFT JOIN contacts ON photo.owner_id=contacts.gaia_id %s", new Object[] { "photos_by_album_view", "", "INNER JOIN photos_in_album ON photo.photo_id=photos_in_album.photo_id" });
    PHOTOS_BY_EVENT_VIEW_SQL = String.format("CREATE VIEW %s AS SELECT photo._id as _id, photo.action_state as action_state, photo.album_id as album_id, photo.comment_count as comment_count, photo.description as description, photo.downloadable as downloadable, photo.entity_version as entity_version, photo.height as height, photo.owner_id as owner_id, photo.photo_id as photo_id, photo.fingerprint as fingerprint, photo.timestamp as timestamp, photo.title as title, photo.upload_status as upload_status, photo.url as url, photo.video_data as video_data, photo.is_panorama as is_panorama, photo.width as width, photo_plusone.plusone_count as plusone_count, photo_plusone.plusone_data as plusone_data, photo_plusone.plusone_by_me as plusone_by_me, photo_plusone.plusone_id as plusone_id, album.title as album_name, album.stream_id as album_stream, contacts.name as owner_name, contacts.avatar as owner_avatar_url, %s (SELECT a.status FROM account_status,photo_shape as a WHERE a.photo_id=photo.photo_id AND a.subject_id=account_status.user_id AND a.status='PENDING' LIMIT 1) AS pending_status FROM photo LEFT JOIN photo_plusone ON photo.photo_id=photo_plusone.photo_id LEFT JOIN album ON photo.album_id=album.album_id LEFT JOIN contacts ON photo.owner_id=contacts.gaia_id %s", new Object[] { "photos_by_event_view", "photos_in_event.event_id as event_id, ", "INNER JOIN photos_in_event ON photo.photo_id=photos_in_event.photo_id" });
    PHOTOS_BY_STREAM_VIEW_SQL = String.format("CREATE VIEW %s AS SELECT photo._id as _id, photo.action_state as action_state, photo.album_id as album_id, photo.comment_count as comment_count, photo.description as description, photo.downloadable as downloadable, photo.entity_version as entity_version, photo.height as height, photo.owner_id as owner_id, photo.photo_id as photo_id, photo.fingerprint as fingerprint, photo.timestamp as timestamp, photo.title as title, photo.upload_status as upload_status, photo.url as url, photo.video_data as video_data, photo.is_panorama as is_panorama, photo.width as width, photo_plusone.plusone_count as plusone_count, photo_plusone.plusone_data as plusone_data, photo_plusone.plusone_by_me as plusone_by_me, photo_plusone.plusone_id as plusone_id, album.title as album_name, album.stream_id as album_stream, contacts.name as owner_name, contacts.avatar as owner_avatar_url, %s (SELECT a.status FROM account_status,photo_shape as a WHERE a.photo_id=photo.photo_id AND a.subject_id=account_status.user_id AND a.status='PENDING' LIMIT 1) AS pending_status FROM photo LEFT JOIN photo_plusone ON photo.photo_id=photo_plusone.photo_id LEFT JOIN album ON photo.album_id=album.album_id LEFT JOIN contacts ON photo.owner_id=contacts.gaia_id %s", new Object[] { "photos_by_stream_view", "photos_in_stream.stream_id as stream_id, ", "INNER JOIN photos_in_stream ON photo.photo_id=photos_in_stream.photo_id" });
    PHOTOS_BY_USER_VIEW_SQL = String.format("CREATE VIEW %s AS SELECT photo._id as _id, photo.action_state as action_state, photo.album_id as album_id, photo.comment_count as comment_count, photo.description as description, photo.downloadable as downloadable, photo.entity_version as entity_version, photo.height as height, photo.owner_id as owner_id, photo.photo_id as photo_id, photo.fingerprint as fingerprint, photo.timestamp as timestamp, photo.title as title, photo.upload_status as upload_status, photo.url as url, photo.video_data as video_data, photo.is_panorama as is_panorama, photo.width as width, photo_plusone.plusone_count as plusone_count, photo_plusone.plusone_data as plusone_data, photo_plusone.plusone_by_me as plusone_by_me, photo_plusone.plusone_id as plusone_id, album.title as album_name, album.stream_id as album_stream, contacts.name as owner_name, contacts.avatar as owner_avatar_url, %s (SELECT a.status FROM account_status,photo_shape as a WHERE a.photo_id=photo.photo_id AND a.subject_id=account_status.user_id AND a.status='PENDING' LIMIT 1) AS pending_status FROM photo LEFT JOIN photo_plusone ON photo.photo_id=photo_plusone.photo_id LEFT JOIN album ON photo.album_id=album.album_id LEFT JOIN contacts ON photo.owner_id=contacts.gaia_id %s", new Object[] { "photos_by_user_view", "photos_of_user.photo_of_user_id as photo_of_user_id, ", "INNER JOIN photos_of_user ON photo.photo_id=photos_of_user.photo_id" });
    ACCOUNT_STATUS_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/account_status");
    ACTIVITIES_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/activities");
    ACTIVITIES_STREAM_VIEW_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/activities_stream_view");
    ACTIVITIES_BY_CIRCLE_ID_URI = Uri.parse(ACTIVITIES_STREAM_VIEW_URI + "_by_circle");
    ACTIVITY_SUMMARY_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/activities/summary");
    ACTIVITY_VIEW_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/activity_view");
    ACTIVITY_VIEW_BY_ACTIVITY_ID_URI = Uri.parse(ACTIVITY_VIEW_URI + "/activity");
    COMMENTS_VIEW_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/comments_view");
    COMMENTS_VIEW_BY_ACTIVITY_ID_URI = Uri.parse(COMMENTS_VIEW_URI + "/activity");
    LOCATION_QUERIES_VIEW_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/location_queries_view");
    NOTIFICATIONS_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/notifications");
    CONVERSATIONS_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/conversations");
    MESSAGE_NOTIFICATIONS_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/message_notifications_view");
    MESSAGES_BY_CONVERSATION_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/messages/conversation");
    PARTICIPANTS_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/participants");
    MESSENGER_SUGGESTIONS_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/messenger_suggestions");
    HANGOUT_SUGGESTIONS_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/hangout_suggestions");
    CIRCLES_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/circles");
    CONTACTS_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/contacts");
    CONTACT_BY_PERSON_ID_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/contacts/id");
    CONTACTS_BY_CIRCLE_ID_URI = Uri.parse(CONTACTS_URI + "/circle");
    CONTACTS_BY_SQUARE_ID_URI = Uri.parse(CONTACTS_URI + "/square");
    CONTACTS_QUERY_URI = Uri.parse(CONTACTS_URI + "/query");
    SUGGESTED_PEOPLE_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/contacts/suggested");
    PHOTO_HOME_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/photo_home");
    ALBUM_VIEW_BY_OWNER_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/album_view_by_user");
    ALBUM_VIEW_BY_ALBUM_AND_OWNER_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/album_view_by_album_and_owner");
    PHOTO_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/photo");
    PHOTO_BY_PHOTO_ID_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/photos_by_photo");
    PHOTO_BY_ALBUM_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/photos_by_album");
    PHOTO_BY_EVENT_ID_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/photos_by_event");
    PHOTO_OF_USER_ID_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/photos_by_user");
    PHOTO_BY_STREAM_ID_AND_OWNER_ID_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/photos_by_stream_and_owner");
    PHOTO_NOTIFICATION_COUNT_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/photo_notification_count");
    PHOTO_COMMENTS_BY_PHOTO_ID_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/photo_comment_by_photo");
    PHOTO_SHAPES_BY_PHOTO_ID_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/photo_shape_by_photo");
    NETWORK_DATA_TRANSACTIONS_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/network_data_transactions");
    NETWORK_DATA_STATS_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/network_data_stats");
    PLATFORM_AUDIENCE_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/platform_audience");
    PLUS_PAGES_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/plus_pages");
    EVENTS_ALL_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/events");
    PANORAMA_IMAGE_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/panorama_image");
    SQUARES_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/squares");
    EMOTISHARE_URI = Uri.parse("content://com.google.android.apps.plus.content.EsProvider/emotishare_data");
    UriMatcher localUriMatcher = new UriMatcher(-1);
    URI_MATCHER = localUriMatcher;
    localUriMatcher.addURI("com.google.android.apps.plus.content.EsProvider", "account_status", 1);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "activities", 20);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "activity_view/activity/*", 22);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "activities_stream_view/stream/*", 21);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "activities_stream_view_by_circle/*", 23);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "activities/summary", 24);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "comments_view/activity/*", 30);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "location_queries_view/query/*", 40);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "notifications", 50);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "circles", 60);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "contacts", 70);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "contacts/circle/*", 71);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "contacts/square/*", 75);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "contacts/query/*", 74);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "contacts/query", 74);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "contacts/id/*", 72);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "contacts/suggested", 73);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "circle_contact", 62);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "conversations", 100);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "participants/conversation/*", 110);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "message_notifications_view", 160);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "messages/conversation/*", 120);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "messenger_suggestions", 115);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "hangout_suggestions", 116);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "photo_home", 130);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "album_view/*", 131);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "album_view_by_user/*", 132);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "album_view_by_album_and_owner/*/*", 144);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "album_view_by_stream/*", 133);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "photos_by_photo/*", 134);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "photos_by_album/*", 135);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "photos_by_event/*", 145);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "photos_by_user/*", 139);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "photos_by_stream_and_owner/*/*", 138);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "photo_comment_by_photo/*", 141);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "photo_shape_by_photo/*", 143);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "photo_notification_count", 140);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "network_data_transactions", 180);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "network_data_stats", 181);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "platform_audience/*", 182);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "plus_pages", 190);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "panorama_image", 200);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "squares", 210);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "squares/*", 211);
    URI_MATCHER.addURI("com.google.android.apps.plus.content.EsProvider", "emotishare_data", 212);
    HashMap localHashMap1 = new HashMap();
    ACCOUNTS_PROJECTION_MAP = localHashMap1;
    localHashMap1.put("circle_sync_time", "circle_sync_time");
    ACCOUNTS_PROJECTION_MAP.put("last_sync_time", "last_sync_time");
    ACCOUNTS_PROJECTION_MAP.put("last_stats_sync_time", "last_stats_sync_time");
    ACCOUNTS_PROJECTION_MAP.put("last_contacted_time", "last_contacted_time");
    ACCOUNTS_PROJECTION_MAP.put("wipeout_stats", "wipeout_stats");
    ACCOUNTS_PROJECTION_MAP.put("people_sync_time", "people_sync_time");
    ACCOUNTS_PROJECTION_MAP.put("people_last_update_token", "people_last_update_token");
    ACCOUNTS_PROJECTION_MAP.put("avatars_downloaded", "avatars_downloaded");
    ACCOUNTS_PROJECTION_MAP.put("audience_data", "audience_data");
    ACCOUNTS_PROJECTION_MAP.put("audience_history", "audience_history");
    ACCOUNTS_PROJECTION_MAP.put("user_id", "user_id");
    ACCOUNTS_PROJECTION_MAP.put("contacts_sync_version", "contacts_sync_version");
    ACCOUNTS_PROJECTION_MAP.put("push_notifications", "push_notifications");
    ACCOUNTS_PROJECTION_MAP.put("last_analytics_sync_time", "last_analytics_sync_time");
    ACCOUNTS_PROJECTION_MAP.put("last_settings_sync_time", "last_settings_sync_time");
    ACCOUNTS_PROJECTION_MAP.put("last_squares_sync_time", "last_squares_sync_time");
    ACCOUNTS_PROJECTION_MAP.put("last_emotishare_sync_time", "last_emotishare_sync_time");
    HashMap localHashMap2 = new HashMap();
    ACTIVITIES_VIEW_PROJECTION_MAP = localHashMap2;
    localHashMap2.put("_id", "_id");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("activity_id", "activity_id");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("data_state", "data_state");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("last_activity", "last_activity");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("token", "token");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("author_id", "author_id");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("name", "name");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("avatar", "avatar");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("source_id", "source_id");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("source_name", "source_name");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("total_comment_count", "total_comment_count");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("plus_one_data", "plus_one_data");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("public", "public");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("spam", "spam");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("acl_display", "acl_display");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("can_comment", "can_comment");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("can_reshare", "can_reshare");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("has_muted", "has_muted");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("has_read", "has_read");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("loc", "loc");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("created", "created");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("is_edited", "is_edited");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("modified", "modified");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("event_id", "event_id");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("photo_collection", "photo_collection");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("popular_post", "popular_post");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("content_flags", "content_flags");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("annotation", "annotation");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("annotation_plaintext", "annotation_plaintext");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("title", "title");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("title_plaintext", "title_plaintext");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("original_author_id", "original_author_id");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("original_author_name", "original_author_name");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("event_data", "event_data");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("embed_deep_link", "embed_deep_link");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("embed_media", "embed_media");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("embed_photo_album", "embed_photo_album");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("embed_checkin", "embed_checkin");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("embed_place", "embed_place");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("embed_place_review", "embed_place_review");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("embed_skyjam", "embed_skyjam");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("embed_appinvite", "embed_appinvite");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("embed_hangout", "embed_hangout");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("embed_square", "embed_square");
    ACTIVITIES_VIEW_PROJECTION_MAP.put("embed_emotishare", "embed_emotishare");
    HashMap localHashMap3 = new HashMap();
    ACTIVITY_SUMMARY_PROJECTION_MAP = localHashMap3;
    localHashMap3.put("author_id", "author_id");
    ACTIVITY_SUMMARY_PROJECTION_MAP.put("activity_id", "activity_id");
    ACTIVITY_SUMMARY_PROJECTION_MAP.put("created", "created");
    ACTIVITY_SUMMARY_PROJECTION_MAP.put("is_edited", "is_edited");
    ACTIVITY_SUMMARY_PROJECTION_MAP.put("modified", "modified");
    HashMap localHashMap4 = new HashMap();
    COMMENTS_VIEW_PROJECTION_MAP = localHashMap4;
    localHashMap4.put("_id", "_id");
    COMMENTS_VIEW_PROJECTION_MAP.put("activity_id", "activity_id");
    COMMENTS_VIEW_PROJECTION_MAP.put("comment_id", "comment_id");
    COMMENTS_VIEW_PROJECTION_MAP.put("author_id", "author_id");
    COMMENTS_VIEW_PROJECTION_MAP.put("content", "content");
    COMMENTS_VIEW_PROJECTION_MAP.put("created", "created");
    COMMENTS_VIEW_PROJECTION_MAP.put("name", "name");
    COMMENTS_VIEW_PROJECTION_MAP.put("avatar", "avatar");
    COMMENTS_VIEW_PROJECTION_MAP.put("plus_one_data", "plus_one_data");
    HashMap localHashMap5 = new HashMap();
    LOCATION_QUERIES_VIEW_PROJECTION_MAP = localHashMap5;
    localHashMap5.put("_id", "_id");
    LOCATION_QUERIES_VIEW_PROJECTION_MAP.put("name", "name");
    LOCATION_QUERIES_VIEW_PROJECTION_MAP.put("location", "location");
    HashMap localHashMap6 = new HashMap();
    NOTIFICATIONS_PROJECTION_MAP = localHashMap6;
    localHashMap6.put("_id", "_id");
    NOTIFICATIONS_PROJECTION_MAP.put("notif_id", "notif_id");
    NOTIFICATIONS_PROJECTION_MAP.put("category", "category");
    NOTIFICATIONS_PROJECTION_MAP.put("message", "message");
    NOTIFICATIONS_PROJECTION_MAP.put("timestamp", "timestamp");
    NOTIFICATIONS_PROJECTION_MAP.put("enabled", "enabled");
    NOTIFICATIONS_PROJECTION_MAP.put("read", "read");
    NOTIFICATIONS_PROJECTION_MAP.put("circle_data", "circle_data");
    NOTIFICATIONS_PROJECTION_MAP.put("pd_gaia_id", "pd_gaia_id");
    NOTIFICATIONS_PROJECTION_MAP.put("pd_album_id", "pd_album_id");
    NOTIFICATIONS_PROJECTION_MAP.put("pd_photo_id", "pd_photo_id");
    NOTIFICATIONS_PROJECTION_MAP.put("activity_id", "activity_id");
    NOTIFICATIONS_PROJECTION_MAP.put("ed_event", "ed_event");
    NOTIFICATIONS_PROJECTION_MAP.put("ed_event_id", "ed_event_id");
    NOTIFICATIONS_PROJECTION_MAP.put("ed_creator_id", "ed_creator_id");
    NOTIFICATIONS_PROJECTION_MAP.put("notification_type", "notification_type");
    NOTIFICATIONS_PROJECTION_MAP.put("coalescing_code", "coalescing_code");
    NOTIFICATIONS_PROJECTION_MAP.put("entity_type", "entity_type");
    NOTIFICATIONS_PROJECTION_MAP.put("entity_snippet", "entity_snippet");
    NOTIFICATIONS_PROJECTION_MAP.put("entity_photos_data", "entity_photos_data");
    NOTIFICATIONS_PROJECTION_MAP.put("entity_squares_data", "entity_squares_data");
    NOTIFICATIONS_PROJECTION_MAP.put("square_id", "square_id");
    NOTIFICATIONS_PROJECTION_MAP.put("square_name", "square_name");
    NOTIFICATIONS_PROJECTION_MAP.put("square_photo_url", "square_photo_url");
    NOTIFICATIONS_PROJECTION_MAP.put("taggee_photo_ids", "taggee_photo_ids");
    NOTIFICATIONS_PROJECTION_MAP.put("taggee_data_actors", "taggee_data_actors");
    HashMap localHashMap7 = new HashMap();
    CIRCLES_PROJECTION_MAP = localHashMap7;
    localHashMap7.put("_id", "circles.rowid AS _id");
    CIRCLES_PROJECTION_MAP.put("circle_id", "circles.circle_id AS circle_id");
    CIRCLES_PROJECTION_MAP.put("circle_name", "circle_name");
    CIRCLES_PROJECTION_MAP.put("type", "type");
    CIRCLES_PROJECTION_MAP.put("semantic_hints", "semantic_hints");
    CIRCLES_PROJECTION_MAP.put("contact_count", "contact_count");
    CIRCLES_PROJECTION_MAP.put("member_ids", "group_concat(link_person_id, '|') AS member_ids");
    CIRCLES_PROJECTION_MAP.put("show_order", "show_order");
    CIRCLES_PROJECTION_MAP.put("volume", "volume");
    HashMap localHashMap8 = new HashMap();
    CONTACTS_PROJECTION_MAP = localHashMap8;
    localHashMap8.put("_id", "contacts.rowid AS _id");
    CONTACTS_PROJECTION_MAP.put("person_id", "contacts.person_id AS person_id");
    CONTACTS_PROJECTION_MAP.put("gaia_id", "gaia_id");
    CONTACTS_PROJECTION_MAP.put("avatar", "avatar");
    CONTACTS_PROJECTION_MAP.put("name", "name");
    CONTACTS_PROJECTION_MAP.put("last_updated_time", "last_updated_time");
    CONTACTS_PROJECTION_MAP.put("profile_type", "profile_type");
    CONTACTS_PROJECTION_MAP.put("in_my_circles", "in_my_circles");
    CONTACTS_PROJECTION_MAP.put("for_sharing", "(CASE WHEN person_id IN (SELECT link_person_id FROM circle_contact WHERE link_circle_id IN (SELECT circle_id FROM circles WHERE semantic_hints & 64 != 0)) THEN 1 ELSE 0 END) AS for_sharing");
    CONTACTS_PROJECTION_MAP.put("blocked", "blocked");
    CONTACTS_PROJECTION_MAP.put("packed_circle_ids", "group_concat(link_circle_id, '|') AS packed_circle_ids");
    CONTACTS_PROJECTION_MAP.put("contact_update_time", "contact_update_time");
    CONTACTS_PROJECTION_MAP.put("contact_proto", "contact_proto");
    CONTACTS_PROJECTION_MAP.put("profile_update_time", "profile_update_time");
    CONTACTS_PROJECTION_MAP.put("profile_proto", "profile_proto");
    HashMap localHashMap9 = new HashMap(CONTACTS_PROJECTION_MAP);
    CONTACTS_SEARCH_PROJECTION_MAP = localHashMap9;
    localHashMap9.put("email", "email");
    HashMap localHashMap10 = new HashMap(CONTACTS_SEARCH_PROJECTION_MAP);
    CONTACTS_SEARCH_WITH_PHONES_PROJECTION_MAP = localHashMap10;
    localHashMap10.put("_id", "_id");
    CONTACTS_SEARCH_WITH_PHONES_PROJECTION_MAP.put("person_id", "person_id");
    CONTACTS_SEARCH_WITH_PHONES_PROJECTION_MAP.put("packed_circle_ids", "packed_circle_ids");
    CONTACTS_SEARCH_WITH_PHONES_PROJECTION_MAP.put("phone", "phone");
    CONTACTS_SEARCH_WITH_PHONES_PROJECTION_MAP.put("phone_type", "phone_type");
    HashMap localHashMap11 = new HashMap(CONTACTS_PROJECTION_MAP);
    SUGGESTED_PEOPLE_PROJECTION_MAP = localHashMap11;
    localHashMap11.put("category", "category");
    SUGGESTED_PEOPLE_PROJECTION_MAP.put("category_label", "category_label");
    SUGGESTED_PEOPLE_PROJECTION_MAP.put("explanation", "explanation");
    SUGGESTED_PEOPLE_PROJECTION_MAP.put("properties", "properties");
    SUGGESTED_PEOPLE_PROJECTION_MAP.put("suggestion_id", "suggestion_id");
    HashMap localHashMap12 = new HashMap();
    CONVERSATIONS_PROJECTION_MAP = localHashMap12;
    localHashMap12.put("_id", "_id");
    CONVERSATIONS_PROJECTION_MAP.put("conversation_id", "conversation_id");
    CONVERSATIONS_PROJECTION_MAP.put("is_muted", "is_muted");
    CONVERSATIONS_PROJECTION_MAP.put("is_visible", "is_visible");
    CONVERSATIONS_PROJECTION_MAP.put("latest_event_timestamp", "latest_event_timestamp");
    CONVERSATIONS_PROJECTION_MAP.put("latest_message_timestamp", "latest_message_timestamp");
    CONVERSATIONS_PROJECTION_MAP.put("earliest_event_timestamp", "earliest_event_timestamp");
    CONVERSATIONS_PROJECTION_MAP.put("has_older_events", "has_older_events");
    CONVERSATIONS_PROJECTION_MAP.put("unread_count", "unread_count");
    CONVERSATIONS_PROJECTION_MAP.put("name", "name");
    CONVERSATIONS_PROJECTION_MAP.put("generated_name", "generated_name");
    CONVERSATIONS_PROJECTION_MAP.put("latest_message_text", "latest_message_text");
    CONVERSATIONS_PROJECTION_MAP.put("latest_message_image_url", "latest_message_image_url");
    CONVERSATIONS_PROJECTION_MAP.put("latest_message_author_id", "latest_message_author_id");
    CONVERSATIONS_PROJECTION_MAP.put("latest_message_type", "latest_message_type");
    CONVERSATIONS_PROJECTION_MAP.put("latest_message_author_full_name", "latest_message_author_full_name");
    CONVERSATIONS_PROJECTION_MAP.put("latest_message_author_first_name", "latest_message_author_first_name");
    CONVERSATIONS_PROJECTION_MAP.put("latest_message_author_type", "latest_message_author_type");
    CONVERSATIONS_PROJECTION_MAP.put("is_group", "is_group");
    CONVERSATIONS_PROJECTION_MAP.put("is_pending_accept", "is_pending_accept");
    CONVERSATIONS_PROJECTION_MAP.put("inviter_id", "inviter_id");
    CONVERSATIONS_PROJECTION_MAP.put("inviter_full_name", "inviter_full_name");
    CONVERSATIONS_PROJECTION_MAP.put("inviter_first_name", "inviter_first_name");
    CONVERSATIONS_PROJECTION_MAP.put("inviter_type", "inviter_type");
    CONVERSATIONS_PROJECTION_MAP.put("fatal_error_type", "fatal_error_type");
    CONVERSATIONS_PROJECTION_MAP.put("is_pending_leave", "is_pending_leave");
    CONVERSATIONS_PROJECTION_MAP.put("is_awaiting_event_stream", "is_awaiting_event_stream");
    CONVERSATIONS_PROJECTION_MAP.put("is_awaiting_older_events", "is_awaiting_older_events");
    CONVERSATIONS_PROJECTION_MAP.put("first_event_timestamp", "first_event_timestamp");
    CONVERSATIONS_PROJECTION_MAP.put("packed_participants", "packed_participants");
    HashMap localHashMap13 = new HashMap();
    PARTICIPANTS_PROJECTION_MAP = localHashMap13;
    localHashMap13.put("_id", "_id");
    PARTICIPANTS_PROJECTION_MAP.put("participant_id", "participant_id");
    PARTICIPANTS_PROJECTION_MAP.put("first_name", "first_name");
    PARTICIPANTS_PROJECTION_MAP.put("full_name", "full_name");
    PARTICIPANTS_PROJECTION_MAP.put("type", "type");
    PARTICIPANTS_PROJECTION_MAP.put("active", "active");
    PARTICIPANTS_PROJECTION_MAP.put("sequence", "sequence");
    PARTICIPANTS_PROJECTION_MAP.put("conversation_id", "conversation_id");
    HashMap localHashMap14 = new HashMap();
    MESSENGER_SUGGESTIONS_PROJECTION_MAP = localHashMap14;
    localHashMap14.put("_id", "_id");
    MESSENGER_SUGGESTIONS_PROJECTION_MAP.put("participant_id", "participant_id");
    MESSENGER_SUGGESTIONS_PROJECTION_MAP.put("first_name", "first_name");
    MESSENGER_SUGGESTIONS_PROJECTION_MAP.put("full_name", "full_name");
    HashMap localHashMap15 = new HashMap();
    HANGOUT_SUGGESTIONS_PROJECTION_MAP = localHashMap15;
    localHashMap15.put("_id", "_id");
    HANGOUT_SUGGESTIONS_PROJECTION_MAP.put("participant_id", "participant_id");
    HANGOUT_SUGGESTIONS_PROJECTION_MAP.put("first_name", "first_name");
    HANGOUT_SUGGESTIONS_PROJECTION_MAP.put("full_name", "full_name");
    HashMap localHashMap16 = new HashMap();
    MESSAGES_PROJECTION_MAP = localHashMap16;
    localHashMap16.put("_id", "_id");
    MESSAGES_PROJECTION_MAP.put("message_id", "message_id");
    MESSAGES_PROJECTION_MAP.put("conversation_id", "conversation_id");
    MESSAGES_PROJECTION_MAP.put("author_id", "author_id");
    MESSAGES_PROJECTION_MAP.put("text", "text");
    MESSAGES_PROJECTION_MAP.put("timestamp", "timestamp");
    MESSAGES_PROJECTION_MAP.put("status", "status");
    MESSAGES_PROJECTION_MAP.put("type", "type");
    MESSAGES_PROJECTION_MAP.put("author_first_name", "author_first_name");
    MESSAGES_PROJECTION_MAP.put("author_full_name", "author_full_name");
    MESSAGES_PROJECTION_MAP.put("author_type", "author_type");
    MESSAGES_PROJECTION_MAP.put("image_url", "image_url");
    HashMap localHashMap17 = new HashMap();
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP = localHashMap17;
    localHashMap17.put("_id", "_id");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("message_id", "message_id");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("conversation_id", "conversation_id");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("author_id", "author_id");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("text", "text");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("image_url", "image_url");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("timestamp", "timestamp");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("status", "status");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("type", "type");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("notification_seen", "notification_seen");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("author_full_name", "author_full_name");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("author_first_name", "author_first_name");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("author_type", "author_type");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("conversation_muted", "conversation_muted");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("conversation_group", "conversation_group");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("conversation_name", "conversation_name");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("generated_name", "generated_name");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("conversation_pending_accept", "conversation_pending_accept");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("conversation_pending_leave", "conversation_pending_leave");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("inviter_id", "inviter_id");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("inviter_full_name", "inviter_full_name");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("inviter_first_name", "inviter_first_name");
    MESSAGE_NOTIFICATIONS_PROJECTION_MAP.put("inviter_type", "inviter_type");
    HashMap localHashMap18 = new HashMap();
    PHOTO_HOME_MAP = localHashMap18;
    localHashMap18.put("_id", "_id");
    PHOTO_HOME_MAP.put("photo_count", "photo_count");
    PHOTO_HOME_MAP.put("height", "height");
    PHOTO_HOME_MAP.put("image", "image");
    PHOTO_HOME_MAP.put("notification_count", "notification_count");
    PHOTO_HOME_MAP.put("photo_id", "photo_id");
    PHOTO_HOME_MAP.put("photo_home_key", "photo_home_key");
    PHOTO_HOME_MAP.put("size", "size");
    PHOTO_HOME_MAP.put("sort_order", "sort_order");
    PHOTO_HOME_MAP.put("timestamp", "timestamp");
    PHOTO_HOME_MAP.put("type", "type");
    PHOTO_HOME_MAP.put("url", "url");
    PHOTO_HOME_MAP.put("width", "width");
    HashMap localHashMap19 = new HashMap();
    PHOTO_NOTIFICATION_MAP = localHashMap19;
    localHashMap19.put("_count", "notification_count");
    HashMap localHashMap20 = new HashMap();
    ALBUM_VIEW_MAP = localHashMap20;
    localHashMap20.put("_id", "_id");
    ALBUM_VIEW_MAP.put("album_id", "album_id");
    ALBUM_VIEW_MAP.put("album_type", "album_type");
    ALBUM_VIEW_MAP.put("album_key", "album_key");
    ALBUM_VIEW_MAP.put("cover_photo_id", "cover_photo_id");
    ALBUM_VIEW_MAP.put("entity_version", "entity_version");
    ALBUM_VIEW_MAP.put("height", "height");
    ALBUM_VIEW_MAP.put("is_activity", "is_activity");
    ALBUM_VIEW_MAP.put("owner_id", "owner_id");
    ALBUM_VIEW_MAP.put("photo_count", "photo_count");
    ALBUM_VIEW_MAP.put("photo_id", "photo_id");
    ALBUM_VIEW_MAP.put("size", "size");
    ALBUM_VIEW_MAP.put("sort_order", "sort_order");
    ALBUM_VIEW_MAP.put("stream_id", "stream_id");
    ALBUM_VIEW_MAP.put("timestamp", "timestamp");
    ALBUM_VIEW_MAP.put("title", "title");
    ALBUM_VIEW_MAP.put("url", "url");
    ALBUM_VIEW_MAP.put("width", "width");
    HashMap localHashMap21 = new HashMap();
    .put(null, "_id");
    EVENT_PEOPLE_VIEW_MAP.put("event_id", "event_id");
    EVENT_PEOPLE_VIEW_MAP.put("gaia_id", "gaia_id");
    EVENT_PEOPLE_VIEW_MAP.put("person_id", "person_id");
    EVENT_PEOPLE_VIEW_MAP.put("name", "name");
    EVENT_PEOPLE_VIEW_MAP.put("sort_key", "sort_key");
    EVENT_PEOPLE_VIEW_MAP.put("avatar", "avatar");
    EVENT_PEOPLE_VIEW_MAP.put("last_updated_time", "last_updated_time");
    EVENT_PEOPLE_VIEW_MAP.put("profile_type", "profile_type");
    EVENT_PEOPLE_VIEW_MAP.put("profile_state", "profile_state");
    EVENT_PEOPLE_VIEW_MAP.put("in_my_circles", "in_my_circles");
    EVENT_PEOPLE_VIEW_MAP.put("blocked", "blocked");
    HashMap localHashMap22 = new HashMap();
    PHOTO_VIEW_MAP = localHashMap1;
    localHashMap1.put("_id", "_id");
    PHOTO_VIEW_MAP.put("_count", "COUNT(*) AS _count");
    PHOTO_VIEW_MAP.put("action_state", "action_state");
    PHOTO_VIEW_MAP.put("album_id", "album_id");
    PHOTO_VIEW_MAP.put("album_name", "album_name");
    PHOTO_VIEW_MAP.put("album_stream", "album_stream");
    PHOTO_VIEW_MAP.put("comment_count", "comment_count");
    PHOTO_VIEW_MAP.put("description", "description");
    PHOTO_VIEW_MAP.put("downloadable", "downloadable");
    PHOTO_VIEW_MAP.put("entity_version", "entity_version");
    PHOTO_VIEW_MAP.put("height", "height");
    PHOTO_VIEW_MAP.put("owner_id", "owner_id");
    PHOTO_VIEW_MAP.put("owner_name", "owner_name");
    PHOTO_VIEW_MAP.put("owner_avatar_url", "owner_avatar_url");
    PHOTO_VIEW_MAP.put("pending_status", "pending_status");
    PHOTO_VIEW_MAP.put("photo_id", "photo_id");
    PHOTO_VIEW_MAP.put("plusone_by_me", "plusone_by_me");
    PHOTO_VIEW_MAP.put("plusone_count", "plusone_count");
    PHOTO_VIEW_MAP.put("plusone_data", "plusone_data");
    PHOTO_VIEW_MAP.put("plusone_id", "plusone_id");
    PHOTO_VIEW_MAP.put("fingerprint", "fingerprint");
    PHOTO_VIEW_MAP.put("timestamp", "timestamp");
    PHOTO_VIEW_MAP.put("title", "title");
    PHOTO_VIEW_MAP.put("upload_status", "upload_status");
    PHOTO_VIEW_MAP.put("url", "url");
    PHOTO_VIEW_MAP.put("video_data", "video_data");
    PHOTO_VIEW_MAP.put("is_panorama", "is_panorama");
    PHOTO_VIEW_MAP.put("width", "width");
    PHOTO_VIEW_MAP.put("_count", "count(*)  as _count");
    PHOTO_VIEW_MAP.put("photo_of_user_id", "NULL AS photo_of_user_id");
    PHOTOS_BY_ALBUM_VIEW_MAP = new HashMap(PHOTO_VIEW_MAP);
    HashMap localHashMap23 = new HashMap(PHOTO_VIEW_MAP);
    int i = localHashMap22;
    int j = localHashMap23;
    PHOTOS_BY_EVENT_VIEW_MAP = localHashMap22;
    int k = localHashMap23;
    localHashMap1.put("event_id", "event_id");
    PHOTOS_BY_STREAM_VIEW_MAP = new HashMap(PHOTO_VIEW_MAP);
    HashMap localHashMap24 = new HashMap(PHOTO_VIEW_MAP);
    long l1 = localHashMap21;
    l1 = localHashMap24;
    PHOTOS_BY_USER_VIEW_MAP =  * 0;
    l1 = localHashMap24;
    localHashMap1.put("photo_of_user_id", "photos_of_user.photo_of_user_id as photo_of_user_id");
    HashMap localHashMap25 = new HashMap();
    long l2 = ;
    l2 = localHashMap25;
  }

  public static void analyzeDatabase(Context paramContext, EsAccount paramEsAccount)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.execSQL("ANALYZE");
    localSQLiteDatabase.execSQL("ANALYZE sqlite_master");
  }

  public static Uri.Builder appendAccountParameter(Uri.Builder paramBuilder, EsAccount paramEsAccount)
  {
    return paramBuilder.appendQueryParameter("account", String.valueOf(paramEsAccount.getIndex()));
  }

  public static Uri appendAccountParameter(Uri paramUri, EsAccount paramEsAccount)
  {
    return appendAccountParameter(paramUri.buildUpon(), paramEsAccount).build();
  }

  public static Uri buildActivityViewUri(EsAccount paramEsAccount, String paramString)
  {
    Uri.Builder localBuilder = ACTIVITY_VIEW_BY_ACTIVITY_ID_URI.buildUpon();
    localBuilder.appendPath(paramString);
    appendAccountParameter(localBuilder, paramEsAccount);
    return localBuilder.build();
  }

  public static Uri buildLocationQueryUri(EsAccount paramEsAccount, String paramString)
  {
    Uri.Builder localBuilder = LOCATION_QUERIES_VIEW_URI.buildUpon();
    localBuilder.appendPath("query").appendPath(paramString);
    appendAccountParameter(localBuilder, paramEsAccount);
    return localBuilder.build();
  }

  public static Uri buildMessagesUri(EsAccount paramEsAccount, long paramLong)
  {
    Uri.Builder localBuilder = MESSAGES_BY_CONVERSATION_URI.buildUpon();
    localBuilder.appendPath(Long.toString(paramLong));
    localBuilder.appendQueryParameter("account", String.valueOf(paramEsAccount.getIndex()));
    return localBuilder.build();
  }

  public static Uri buildPanoramaUri(File paramFile)
  {
    return PANORAMA_IMAGE_URI.buildUpon().appendQueryParameter("file", paramFile.getPath()).build();
  }

  public static Uri buildParticipantsUri(EsAccount paramEsAccount)
  {
    Uri.Builder localBuilder = PARTICIPANTS_URI.buildUpon();
    localBuilder.appendQueryParameter("account", String.valueOf(paramEsAccount.getIndex()));
    return localBuilder.build();
  }

  public static Uri buildParticipantsUri(EsAccount paramEsAccount, long paramLong)
  {
    Uri.Builder localBuilder = PARTICIPANTS_URI.buildUpon();
    localBuilder.appendPath("conversation").appendPath(Long.toString(paramLong));
    localBuilder.appendQueryParameter("account", String.valueOf(paramEsAccount.getIndex()));
    return localBuilder.build();
  }

  public static Uri buildPeopleQueryUri(EsAccount paramEsAccount, String paramString1, boolean paramBoolean1, boolean paramBoolean2, String paramString2, int paramInt)
  {
    Uri.Builder localBuilder = CONTACTS_QUERY_URI.buildUpon();
    if (paramString1 == null)
      paramString1 = "";
    localBuilder.appendPath(paramString1);
    localBuilder.appendQueryParameter("limit", String.valueOf(paramInt));
    localBuilder.appendQueryParameter("self_gaia_id", paramEsAccount.getGaiaId());
    String str1;
    if (paramBoolean1)
    {
      str1 = "true";
      localBuilder.appendQueryParameter("plus_pages", str1);
      if (!paramBoolean2)
        break label126;
    }
    label126: for (String str2 = "true"; ; str2 = "false")
    {
      localBuilder.appendQueryParameter("in_circles", str2);
      if (paramString2 != null)
        localBuilder.appendQueryParameter("activity_id", paramString2);
      appendAccountParameter(localBuilder, paramEsAccount);
      return localBuilder.build();
      str1 = "false";
      break;
    }
  }

  public static Uri buildStreamUri(EsAccount paramEsAccount, String paramString)
  {
    Uri.Builder localBuilder = ACTIVITIES_STREAM_VIEW_URI.buildUpon();
    localBuilder.appendPath("stream").appendPath(paramString);
    appendAccountParameter(localBuilder, paramEsAccount);
    return localBuilder.build();
  }

  public static void cleanupData(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean)
  {
    if (!paramBoolean);
    label31: SQLiteDatabase localSQLiteDatabase;
    long l1;
    long l2;
    try
    {
      long l9 = EsAccountsData.getLastDatabaseCleanupTimestamp(paramContext, paramEsAccount);
      long l10 = System.currentTimeMillis();
      if (l10 - l9 < 39600000L);
      while (true)
      {
        return;
        EsMediaCache.cleanup();
        localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
        File localFile1 = new File(localSQLiteDatabase.getPath());
        l1 = localFile1.length();
        l2 = System.currentTimeMillis();
        if ((l1 >= 1000000L) || (paramBoolean))
          break;
        EsAccountsData.saveLastDatabaseCleanupTimestamp(paramContext, paramEsAccount, System.currentTimeMillis());
        if (EsLog.isLoggable("EsProvider", 4))
        {
          File localFile3 = new File(localSQLiteDatabase.getPath());
          long l7 = localFile3.length();
          StringBuffer localStringBuffer3 = new StringBuffer();
          long l8 = System.currentTimeMillis() - l2;
          localStringBuffer3.append(l8 / 1000L).append(".").append(l8 % 1000L).append(" seconds");
          Log.i("EsProvider", ">>>>> cleanup db took " + localStringBuffer3.toString() + " old size: " + l1 + ", new size: " + l7);
        }
      }
    }
    finally
    {
    }
    try
    {
      localSQLiteDatabase.beginTransaction();
    }
    finally
    {
      try
      {
        EsPostsData.cleanupData$3105fef4(localSQLiteDatabase);
        EsNotificationData.cleanupData$3105fef4(localSQLiteDatabase);
        EsSquaresData.cleanupData$3105fef4(localSQLiteDatabase);
        EsEmotiShareData.cleanupData$3105fef4(localSQLiteDatabase);
        EsPhotosData.cleanupData(localSQLiteDatabase, paramEsAccount);
        EsConversationsData.cleanupData$3105fef4(localSQLiteDatabase);
        EsNetworkData.cleanupData$3105fef4();
        EsDeepLinkInstallsData.cleanupData$3105fef4(localSQLiteDatabase);
        EsPeopleData.cleanupData(localSQLiteDatabase, paramEsAccount);
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        EsAccountsData.saveLastDatabaseCleanupTimestamp(paramContext, paramEsAccount, System.currentTimeMillis());
        if (!EsLog.isLoggable("EsProvider", 4))
          break label31;
        File localFile2 = new File(localSQLiteDatabase.getPath());
        long l5 = localFile2.length();
        StringBuffer localStringBuffer2 = new StringBuffer();
        long l6 = System.currentTimeMillis() - l2;
        localStringBuffer2.append(l6 / 1000L).append(".").append(l6 % 1000L).append(" seconds");
        Log.i("EsProvider", ">>>>> cleanup db took " + localStringBuffer2.toString() + " old size: " + l1 + ", new size: " + l5);
      }
      finally
      {
        localSQLiteDatabase.endTransaction();
      }
      EsAccountsData.saveLastDatabaseCleanupTimestamp(paramContext, paramEsAccount, System.currentTimeMillis());
      if (EsLog.isLoggable("EsProvider", 4))
      {
        long l3 = new File(localSQLiteDatabase.getPath()).length();
        StringBuffer localStringBuffer1 = new StringBuffer();
        long l4 = System.currentTimeMillis() - l2;
        localStringBuffer1.append(l4 / 1000L).append(".").append(l4 % 1000L).append(" seconds");
        Log.i("EsProvider", ">>>>> cleanup db took " + localStringBuffer1.toString() + " old size: " + l1 + ", new size: " + l3);
      }
    }
  }

  public static void deleteDatabase(Context paramContext, EsAccount paramEsAccount)
  {
    EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).deleteDatabase();
  }

  private static void ensureActivitiesPageSizes(Context paramContext)
  {
    if (sActivitiesPageSize == 0)
    {
      if (ScreenMetrics.getInstance(paramContext).screenDisplayType != 0)
        break label27;
      sActivitiesPageSize = 15;
    }
    for (sActivitiesFirstPageSize = 10; ; sActivitiesFirstPageSize = 20)
    {
      return;
      label27: sActivitiesPageSize = 24;
    }
  }

  public static int getActivitiesPageSize(Context paramContext)
  {
    ensureActivitiesPageSizes(paramContext);
    return sActivitiesPageSize;
  }

  static String[] getIndexSQLs()
  {
    return new String[] { "CREATE INDEX contacts_in_my_circles ON contacts(in_my_circles,person_id)", "CREATE INDEX contacts_name ON contacts(name)", "CREATE INDEX contacts_sort_key ON contacts(sort_key)", "CREATE INDEX contacts_gaia_id ON contacts(gaia_id)", "CREATE UNIQUE INDEX circle_contact_forward ON circle_contact(link_circle_id,link_person_id)", "CREATE UNIQUE INDEX circle_contact_backward ON circle_contact(link_person_id,link_circle_id)", "CREATE INDEX contact_search_key ON contact_search(search_key)", "CREATE INDEX album_album_id ON album(album_id)", "CREATE INDEX photo_photo_id ON photo(photo_id)", "CREATE INDEX photo_comment_photo_id ON photo_comment(photo_id,comment_id)", "CREATE INDEX photo_shape_photo_id ON photo_shape(photo_id,shape_id)", "CREATE INDEX photos_in_stream_photo_id ON photos_in_stream(stream_id)", "CREATE INDEX photos_in_album_album_id ON photos_in_album(album_id)", "CREATE INDEX photos_in_event_event_id ON photos_in_event(event_id)", "CREATE INDEX photos_of_user_photo_id ON photo_comment(photo_id)", "CREATE INDEX activity_streams_activity_id ON activity_streams(activity_id)" };
  }

  protected static String[] getTableSQLs()
  {
    return new String[] { "CREATE TABLE account_status (user_id TEXT,last_sync_time INT DEFAULT(-1),last_stats_sync_time INT DEFAULT(-1),last_contacted_time INT DEFAULT(-1),wipeout_stats INT DEFAULT(0),circle_sync_time INT DEFAULT(-1),people_sync_time INT DEFAULT(-1),people_last_update_token TEXT,suggested_people_sync_time INT DEFAULT(-1),blocked_people_sync_time INT DEFAULT(-1),event_list_sync_time INT DEFAULT(-1),event_themes_sync_time INT DEFAULT(-1),avatars_downloaded INT DEFAULT(0),audience_data BLOB,audience_history BLOB,contacts_sync_version INT DEFAULT(0),push_notifications INT DEFAULT(0),last_analytics_sync_time INT DEFAULT(-1),last_settings_sync_time INT DEFAULT(-1),last_squares_sync_time INT DEFAULT(-1),last_emotishare_sync_time INT DEFAULT(-1));", "INSERT INTO account_status DEFAULT VALUES;", "CREATE TABLE activity_streams (stream_key TEXT NOT NULL,activity_id TEXT NOT NULL,sort_index INT NOT NULL,last_activity INT,token TEXT,PRIMARY KEY (stream_key,activity_id));", "CREATE TABLE activities (_id INTEGER PRIMARY KEY, activity_id TEXT UNIQUE NOT NULL, data_state INT NOT NULL DEFAULT (0), author_id TEXT NOT NULL, source_id TEXT, source_name TEXT, total_comment_count INT NOT NULL, plus_one_data BLOB, public INT NOT NULL, spam INT NOT NULL, acl_display TEXT, can_comment INT NOT NULL, can_reshare INT NOT NULL, has_muted INT NOT NULL, has_read INT NOT NULL, loc BLOB, created INT NOT NULL, is_edited INT NOT NULL DEFAULT(0), modified INT NOT NULL, popular_post INT NOT NULL DEFAULT(0), content_flags INT NOT NULL DEFAULT(0), annotation TEXT, annotation_plaintext TEXT, title TEXT, title_plaintext TEXT, original_author_id TEXT, original_author_name TEXT, event_id TEXT, photo_collection BLOB, embed_deep_link BLOB, album_id TEXT, embed_media BLOB, embed_photo_album BLOB, embed_checkin BLOB, embed_place BLOB, embed_place_review BLOB, embed_skyjam BLOB, embed_appinvite BLOB, embed_hangout BLOB, embed_square BLOB, embed_emotishare BLOB);", "CREATE TABLE media (_id INTEGER PRIMARY KEY,activity_id TEXT NOT NULL,thumbnail_url TEXT NOT NULL,FOREIGN KEY (activity_id) REFERENCES activities(activity_id) ON DELETE CASCADE);", "CREATE TABLE activity_comments (_id INTEGER PRIMARY KEY,activity_id TEXT NOT NULL,comment_id TEXT UNIQUE NOT NULL,author_id TEXT NOT NULL,content TEXT,created INT NOT NULL,plus_one_data BLOB,FOREIGN KEY (activity_id) REFERENCES activities(activity_id) ON DELETE CASCADE);", "CREATE TABLE locations (_id INTEGER PRIMARY KEY,qrid INT NOT NULL,name TEXT,location BLOB,FOREIGN KEY (qrid) REFERENCES location_queries(_id) ON DELETE CASCADE);", "CREATE TABLE location_queries (_id INTEGER PRIMARY KEY,key TEXT UNIQUE NOT NULL);", "CREATE TABLE notifications (_id INTEGER, notif_id TEXT UNIQUE NOT NULL, coalescing_code TEXT PRIMARY KEY, category INT NOT NULL DEFAULT(0), message TEXT, enabled INT, read INT NOT NULL, seen INT NOT NULL, timestamp INT NOT NULL, circle_data BLOB, pd_gaia_id TEXT, pd_album_id TEXT, pd_photo_id INT, activity_id TEXT, ed_event INT DEFAULT(0),ed_event_id TEXT, ed_creator_id TEXT, notification_type INT NOT NULL DEFAULT(0),entity_type INT NOT NULL DEFAULT(0),entity_snippet TEXT,entity_photos_data BLOB,entity_squares_data BLOB,square_id TEXT,square_name TEXT,square_photo_url TEXT,taggee_photo_ids TEXT,taggee_data_actors BLOB);", "CREATE TABLE contacts (person_id TEXT PRIMARY KEY,gaia_id TEXT,avatar TEXT,name TEXT,sort_key TEXT,last_updated_time INT,profile_type INT DEFAULT(0),profile_state INT DEFAULT(0),in_my_circles INT DEFAULT(0),blocked INT DEFAULT(0) );", "CREATE TABLE circles (circle_id TEXT PRIMARY KEY,circle_name TEXT,sort_key TEXT,type INT, contact_count INT,semantic_hints INT NOT NULL DEFAULT(0),show_order INT,volume INT);", "CREATE TABLE circle_contact (link_circle_id TEXT NOT NULL,link_person_id TEXT NOT NULL,UNIQUE (link_circle_id, link_person_id), FOREIGN KEY (link_circle_id) REFERENCES circles(circle_id) ON DELETE CASCADE,FOREIGN KEY (link_person_id) REFERENCES contacts(person_id) ON DELETE CASCADE);", "CREATE TABLE suggested_people (_id INTEGER PRIMARY KEY, suggested_person_id TEXT NOT NULL,dismissed INT DEFAULT(0),sort_order INT DEFAULT(0),category TEXT NOT NULL,category_label TEXT,category_sort_key TEXT,explanation TEXT,properties TEXT,suggestion_id TEXT );", "CREATE TABLE circle_action (gaia_id TEXT NOT NULL,notification_id INT NOT NULL,UNIQUE (gaia_id, notification_id), FOREIGN KEY (notification_id) REFERENCES notifications(notif_id) ON DELETE CASCADE);", "CREATE TABLE photo_home (_id INTEGER PRIMARY KEY AUTOINCREMENT,type TEXT NOT NULL,photo_count INT,sort_order INT NOT NULL DEFAULT( 100 ),timestamp INT,notification_count INT);", "CREATE TABLE photo_home_cover (photo_home_key INT NOT NULL,photo_id INT,url TEXT NOT NULL,width INT,height INT,size INT,image BLOB, FOREIGN KEY (photo_home_key) REFERENCES photo_home(_id) ON DELETE CASCADE);", "CREATE TABLE profiles (profile_person_id TEXT PRIMARY KEY,contact_update_time INT,contact_proto BLOB,profile_update_time INT,profile_proto BLOB,FOREIGN KEY (profile_person_id) REFERENCES contacts(person_id) ON DELETE CASCADE);", "CREATE TABLE album ( _id INTEGER PRIMARY KEY AUTOINCREMENT, album_id TEXT UNIQUE NOT NULL, title TEXT, photo_count INT, sort_order INT NOT NULL DEFAULT( 100 ), owner_id TEXT, timestamp INT, entity_version INT, album_type TEXT NOT NULL DEFAULT('ALL_OTHERS'), cover_photo_id INT, stream_id TEXT, is_activity BOOLEAN DEFAULT '0' );", "CREATE TABLE album_cover (album_key INT NOT NULL,photo_id INT,url TEXT,width INT,height INT,size INT, FOREIGN KEY (album_key) REFERENCES album(_id) ON DELETE CASCADE);", "CREATE TABLE photo (_id INTEGER PRIMARY KEY AUTOINCREMENT, photo_id INT NOT NULL, url TEXT, title TEXT, description TEXT, action_state INT, comment_count INT, owner_id TEXT, plus_one_key INT NOT NULL, width INT, height INT, album_id TEXT NOT NULL, timestamp INT, entity_version INT, fingerprint BLOB, video_data BLOB, is_panorama INT DEFAULT(0), upload_status TEXT, downloadable BOOLEAN, UNIQUE (photo_id) FOREIGN KEY (album_id) REFERENCES album(album_id) ON DELETE CASCADE);", "CREATE TABLE photo_comment (_id INTEGER PRIMARY KEY, photo_id INT NOT NULL, comment_id TEXT UNIQUE NOT NULL, author_id TEXT NOT NULL, content TEXT, create_time INT, truncated INT, update_time INT, plusone_data BLOB, FOREIGN KEY (photo_id) REFERENCES photo(photo_id) ON DELETE CASCADE);", "CREATE TABLE photo_plusone (_id INTEGER PRIMARY KEY, photo_id INT NOT NULL, plusone_id TEXT, plusone_by_me BOOLEAN DEFAULT '0' NOT NULL, plusone_count INTEGER, plusone_data BLOB, FOREIGN KEY (photo_id) REFERENCES photo(photo_id) ON DELETE CASCADE );", "CREATE TABLE photos_in_album (_id INTEGER PRIMARY KEY, photo_id INT NOT NULL, album_id INT NOT NULL, FOREIGN KEY (photo_id) REFERENCES photo(photo_id) ON DELETE CASCADE);", "CREATE TABLE photos_of_user (photo_id INT NOT NULL, photo_of_user_id TEXT NOT NULL, FOREIGN KEY (photo_id) REFERENCES photo(photo_id) ON DELETE CASCADE);", "CREATE TABLE photos_in_event (_id INTEGER PRIMARY KEY, photo_id INT NOT NULL, event_id TEXT NOT NULL, UNIQUE (photo_id, event_id) FOREIGN KEY (photo_id) REFERENCES photo(photo_id) ON DELETE CASCADE);", "CREATE TABLE photos_in_stream (_id INTEGER PRIMARY KEY, photo_id INT NOT NULL, stream_id TEXT NOT NULL, FOREIGN KEY (photo_id) REFERENCES photo(photo_id) ON DELETE CASCADE);", "CREATE TABLE photo_shape (shape_id INTEGER PRIMARY KEY, photo_id INT NOT NULL, subject_id TEXT, creator_id TEXT NOT NULL, status TEXT, bounds BLOB, FOREIGN KEY (photo_id) REFERENCES photo(photo_id) ON DELETE CASCADE);", "CREATE TABLE conversations (_id INTEGER PRIMARY KEY, conversation_id TEXT, is_muted INT, is_visible INT, latest_event_timestamp INT, latest_message_timestamp INT, earliest_event_timestamp INT, has_older_events INT, unread_count INT, name TEXT, generated_name TEXT, latest_message_text TEXT, latest_message_image_url TEXT, latest_message_author_id TEXT, latest_message_type INT, is_group INT, is_pending_accept INT, inviter_id TEXT, fatal_error_type INT, is_pending_leave INT, is_awaiting_event_stream INT, is_awaiting_older_events INT, first_event_timestamp INT, packed_participants TEXT, UNIQUE (conversation_id ));", "CREATE TABLE conversation_participants (conversation_id INT, participant_id TEXT, active INT, sequence INT, UNIQUE (conversation_id,participant_id) ON CONFLICT REPLACE, FOREIGN KEY (conversation_id) REFERENCES conversations(_id) ON DELETE CASCADE, FOREIGN KEY (participant_id) REFERENCES participants(participant_id) ON DELETE CASCADE);", "CREATE TABLE participants (_id INTEGER PRIMARY KEY, participant_id TEXT UNIQUE ON CONFLICT REPLACE, full_name TEXT, first_name TEXT,type INT);", "CREATE TABLE messages (_id INTEGER PRIMARY KEY, message_id TEXT, conversation_id INT, author_id TEXT, text TEXT, timestamp INT, status INT, type INT, notification_seen INT, image_url TEXT, FOREIGN KEY (conversation_id) REFERENCES conversations(_id) ON DELETE CASCADE,FOREIGN KEY (author_id) REFERENCES participants(participant_id) ON DELETE CASCADE, UNIQUE (conversation_id,timestamp) ON CONFLICT REPLACE);", "CREATE TABLE messenger_suggestions (_id INTEGER PRIMARY KEY, participant_id TEXT UNIQUE ON CONFLICT REPLACE, full_name TEXT, first_name TEXT,sequence INT);", "CREATE TABLE hangout_suggestions (_id INTEGER PRIMARY KEY, participant_id TEXT UNIQUE ON CONFLICT REPLACE, full_name TEXT, first_name TEXT,sequence INT);", "CREATE TABLE realtimechat_metadata (key TEXT UNIQUE, value TEXT)", "CREATE TABLE analytics_events (event_data BLOB NOT NULL)", "CREATE TABLE search (search_key TEXT NOT NULL,continuation_token TEXT,PRIMARY KEY (search_key));", "CREATE TABLE contact_search(search_person_id TEXT NOT NULL,search_key_type TEXT NOT NULL DEFAULT(0),search_key TEXT,FOREIGN KEY (search_person_id) REFERENCES contacts(person_id) ON DELETE CASCADE);", "CREATE TABLE network_data_transactions(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL,time INT,sent INT,recv INT,network_duration INT,process_duration INT,req_count INT,exception TEXT);", "CREATE TABLE network_data_stats(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL,first INT,last INT,sent INT,recv INT,network_duration INT,process_duration INT,req_count INT);", "CREATE TABLE platform_audience(package_name TEXT PRIMARY KEY, audience_data BLOB);", "CREATE TABLE events(_id INTEGER PRIMARY KEY AUTOINCREMENT, event_id TEXT UNIQUE NOT NULL, activity_id TEXT UNIQUE, name TEXT, source INT, update_timestamp INT, refresh_timestamp INT, activity_refresh_timestamp INT, invitee_roster_timestamp INT, fingerprint INT NOT NULL DEFAULT(0), start_time INT NOT NULL, end_time INT NOT NULL, can_invite_people INT DEFAULT (0), can_post_photos INT DEFAULT (0), can_comment INT DEFAULT(0) NOT NULL, mine INT DEFAULT (0) NOT NULL, polling_token TEXT,resume_token TEXT,display_time INT DEFAULT (0),event_data BLOB, invitee_roster BLOB);", "CREATE TABLE event_people(_id INTEGER PRIMARY KEY AUTOINCREMENT, event_id TEXT NOT NULL, gaia_id TEXT NOT NULL, CONSTRAINT uc_eventID UNIQUE (event_id, gaia_id) FOREIGN KEY (event_id) REFERENCES events(event_id) ON DELETE CASCADE);", "CREATE TABLE plus_pages(gaia_id TEXT PRIMARY KEY, name TEXT);", "CREATE TABLE event_activities(_id INTEGER PRIMARY KEY AUTOINCREMENT, event_id TEXT NOT NULL, type INT, owner_gaia_id TEXT, owner_name TEXT, timestamp INT, fingerprint INT NOT NULL DEFAULT(0), url TEXT,comment TEXT,data BLOB, FOREIGN KEY (event_id) REFERENCES events(event_id) ON DELETE CASCADE);", "CREATE TABLE event_themes(_id INTEGER PRIMARY KEY AUTOINCREMENT, theme_id INTEGER UNIQUE NOT NULL, is_default INT DEFAULT(0), is_featured INT DEFAULT(0), image_url TEXT NOT NULL, placeholder_path TEXT, sort_order INT DEFAULT(0));", "CREATE TABLE deep_link_installs(_id INTEGER PRIMARY KEY AUTOINCREMENT, timestamp INT DEFAULT(0), package_name TEXT UNIQUE NOT NULL, launch_source TEXT NOT NULL, activity_id TEXT NOT NULL, author_id TEXT NOT NULL);", "CREATE TABLE squares (_id INTEGER PRIMARY KEY,square_id TEXT UNIQUE NOT NULL,square_name TEXT,tagline TEXT,photo_url TEXT,about_text TEXT,joinability INT NOT NULL DEFAULT(0),member_count INT NOT NULL DEFAULT(0),membership_status INT NOT NULL DEFAULT(0),is_member INT NOT NULL DEFAULT(0),suggested INT NOT NULL DEFAULT(0),post_visibility INT NOT NULL DEFAULT(-1),can_see_members INT NOT NULL DEFAULT(0),can_see_posts INT NOT NULL DEFAULT(0),can_join INT NOT NULL DEFAULT(0),can_request_to_join INT NOT NULL DEFAULT(0),can_share INT NOT NULL DEFAULT(0),can_invite INT NOT NULL DEFAULT(0),notifications_enabled INT NOT NULL DEFAULT(0),square_streams BLOB,inviter_gaia_id TEXT,sort_index INT NOT NULL DEFAULT(0),last_sync INT DEFAULT(-1),last_members_sync INT DEFAULT(-1),invitation_dismissed INT NOT NULL DEFAULT(0),suggestion_sort_index INT NOT NULL DEFAULT(0),auto_subscribe INT NOT NULL DEFAULT(0),disable_subscription INT NOT NULL DEFAULT(0),unread_count INT NOT NULL DEFAULT(0));", "CREATE TABLE square_contact (link_square_id TEXT NOT NULL,link_person_id TEXT NOT NULL,membership_status INT NOT NULL DEFAULT(0),UNIQUE (link_square_id, link_person_id), FOREIGN KEY (link_square_id) REFERENCES squares(square_id) ON DELETE CASCADE,FOREIGN KEY (link_person_id) REFERENCES contacts(person_id) ON DELETE CASCADE);", "CREATE TABLE emotishare_data (_id INTEGER PRIMARY KEY AUTOINCREMENT,type TEXT UNIQUE ON CONFLICT REPLACE,data BLOB,generation INT DEFAULT(-1));" };
  }

  static String[] getViewNames()
  {
    return new String[] { "activities_stream_view", "activity_view", "comments_view", "location_queries_view", "conversations_view", "participants_view", "messages_view", "photo_home_view", "album_view", "photo_view", "photos_by_album_view", "photos_by_event_view", "photos_by_stream_view", "photos_by_user_view", "photo_shape_view", "message_notifications_view", "deep_link_installs_view", "event_people_view" };
  }

  static String[] getViewSQLs()
  {
    String[] arrayOfString = new String[18];
    arrayOfString[0] = "CREATE VIEW activities_stream_view AS SELECT activity_streams.stream_key as stream_key,activity_streams.sort_index as sort_index,activity_streams.last_activity as last_activity,activity_streams.token as token,activities._id as _id,activities.activity_id as activity_id,activities.author_id as author_id,activities.source_id as source_id,activities.source_name as source_name,activities.total_comment_count as total_comment_count,activities.plus_one_data as plus_one_data,activities.public as public,activities.spam as spam,activities.acl_display as acl_display,activities.can_comment as can_comment,activities.can_reshare as can_reshare,activities.has_muted as has_muted,activities.has_read as has_read,activities.loc as loc,activities.created as created,activities.is_edited as is_edited,activities.modified as modified,activities.data_state as data_state,activities.event_id as event_id,activities.photo_collection as photo_collection,activities.popular_post as popular_post,activities.content_flags as content_flags,activities.annotation as annotation,activities.annotation_plaintext as annotation_plaintext,activities.title as title,activities.title_plaintext as title_plaintext,activities.original_author_id as original_author_id,activities.original_author_name as original_author_name,activities.embed_deep_link as embed_deep_link,activities.embed_media as embed_media,activities.embed_photo_album as embed_photo_album,activities.embed_checkin as embed_checkin,activities.embed_place as embed_place,activities.embed_place_review as embed_place_review,activities.embed_skyjam as embed_skyjam,activities.embed_appinvite as embed_appinvite,activities.embed_hangout as embed_hangout,activities.embed_square as embed_square,activities.embed_emotishare as embed_emotishare,events.event_data as event_data,contacts.name as name,contacts.avatar as avatar FROM activity_streams INNER JOIN activities ON activity_streams.activity_id=activities.activity_id INNER JOIN contacts ON activities.author_id=contacts.gaia_id LEFT OUTER JOIN events ON activities.event_id=events.event_id WHERE data_state    IN (1, 0)";
    arrayOfString[1] = "CREATE VIEW activity_view AS SELECT activities._id as _id,activities.activity_id as activity_id,activities.author_id as author_id,activities.source_id as source_id,activities.source_name as source_name,activities.total_comment_count as total_comment_count,activities.plus_one_data as plus_one_data,activities.public as public,activities.spam as spam,activities.acl_display as acl_display,activities.can_comment as can_comment,activities.can_reshare as can_reshare,activities.has_muted as has_muted,activities.has_read as has_read,activities.loc as loc,activities.created as created,activities.is_edited as is_edited,activities.modified as modified,activities.data_state as data_state,contacts.name as name,contacts.avatar as avatar,activities.photo_collection as photo_collection,activities.popular_post as popular_post,activities.content_flags as content_flags,activities.annotation as annotation,activities.annotation_plaintext as annotation_plaintext,activities.title as title,activities.title_plaintext as title_plaintext,activities.original_author_id as original_author_id,activities.original_author_name as original_author_name,activities.embed_deep_link as embed_deep_link,activities.embed_media as embed_media,activities.embed_photo_album as embed_photo_album,activities.embed_checkin as embed_checkin,activities.embed_place as embed_place,activities.embed_place_review as embed_place_review,activities.embed_skyjam as embed_skyjam,activities.embed_appinvite as embed_appinvite,activities.embed_hangout as embed_hangout,activities.embed_square as embed_square,activities.embed_emotishare as embed_emotishare,events.event_data as event_data FROM activities JOIN contacts ON activities.author_id=contacts.gaia_id LEFT OUTER JOIN events ON activities.event_id=events.event_id";
    arrayOfString[2] = "CREATE VIEW comments_view AS SELECT activity_comments._id as _id,activity_comments.activity_id as activity_id,activity_comments.comment_id as comment_id,activity_comments.author_id as author_id,activity_comments.content as content,activity_comments.created as created,activity_comments.plus_one_data as plus_one_data,contacts.name as name,contacts.avatar as avatar FROM activity_comments JOIN contacts ON activity_comments.author_id=contacts.gaia_id";
    arrayOfString[3] = "CREATE VIEW location_queries_view AS SELECT location_queries.key as key,locations._id as _id,locations.name as name,locations.location as location FROM location_queries LEFT JOIN locations ON location_queries._id=locations.qrid";
    arrayOfString[4] = "CREATE VIEW conversations_view AS SELECT conversations._id as _id, conversations.conversation_id as conversation_id, conversations.is_muted as is_muted, conversations.is_visible as is_visible, conversations.latest_event_timestamp as latest_event_timestamp, conversations.latest_message_timestamp as latest_message_timestamp, conversations.earliest_event_timestamp as earliest_event_timestamp, conversations.has_older_events as has_older_events, conversations.unread_count as unread_count, conversations.name as name, conversations.generated_name as generated_name, conversations.latest_message_text as latest_message_text, conversations.latest_message_image_url as latest_message_image_url, conversations.latest_message_author_id as latest_message_author_id, conversations.latest_message_type as latest_message_type, conversations.is_group as is_group, conversations.is_pending_accept as is_pending_accept, conversations.inviter_id as inviter_id, conversations.fatal_error_type as fatal_error_type, conversations.is_pending_leave as is_pending_leave, conversations.is_awaiting_event_stream as is_awaiting_event_stream, conversations.is_awaiting_older_events as is_awaiting_older_events, conversations.first_event_timestamp as first_event_timestamp, conversations.packed_participants as packed_participants, participants.full_name as latest_message_author_full_name, participants.first_name as latest_message_author_first_name, participants.type as latest_message_author_type, inviter_alias.full_name as inviter_full_name, inviter_alias.first_name as inviter_first_name, inviter_alias.type as inviter_type  FROM conversations LEFT JOIN participants ON conversations.latest_message_author_id=participants.participant_id LEFT JOIN participants inviter_alias ON conversations.inviter_id=inviter_alias.participant_id";
    arrayOfString[5] = "CREATE VIEW participants_view AS SELECT participants._id as _id, participants.participant_id as participant_id, participants.full_name as full_name, participants.first_name as first_name, participants.type as type, conversation_participants.conversation_id as conversation_id, conversation_participants.active as active, conversation_participants.sequence as sequence FROM participants JOIN conversation_participants ON participants.participant_id=conversation_participants.participant_id";
    arrayOfString[6] = "CREATE VIEW messages_view AS SELECT messages._id as _id, messages.message_id as message_id, messages.conversation_id as conversation_id, messages.author_id as author_id, messages.text as text, messages.timestamp as timestamp, messages.status as status, messages.type as type, messages.image_url as image_url, participants.full_name as author_full_name, participants.first_name as author_first_name, participants.type as author_type FROM messages LEFT JOIN participants ON messages.author_id=participants.participant_id";
    arrayOfString[7] = "CREATE VIEW photo_home_view AS SELECT photo_home._id as _id, photo_home.photo_count as photo_count, photo_home.notification_count as notification_count, photo_home.sort_order as sort_order, photo_home.timestamp as timestamp, photo_home.type as type, photo_home_cover.height as height, photo_home_cover.image as image, photo_home_cover.photo_id as photo_id, photo_home_cover.photo_home_key as photo_home_key, photo_home_cover.size as size, photo_home_cover.url as url, photo_home_cover.width as width FROM photo_home LEFT JOIN photo_home_cover ON photo_home._id=photo_home_cover.photo_home_key";
    arrayOfString[8] = "CREATE VIEW album_view AS SELECT album._id as _id, album.album_id as album_id, album.entity_version as entity_version, album.is_activity as is_activity, album.owner_id as owner_id, album.photo_count as photo_count, album.sort_order as sort_order, album.stream_id as stream_id, album.timestamp as timestamp, album.title as title, album.cover_photo_id as cover_photo_id, album.album_type as album_type, album_cover.album_key as album_key, album_cover.height as height, album_cover.photo_id as photo_id, album_cover.size as size, album_cover.url as url, album_cover.width as width FROM album LEFT JOIN album_cover ON album._id=album_cover.album_key";
    arrayOfString[9] = PHOTO_VIEW_SQL;
    arrayOfString[10] = PHOTOS_BY_ALBUM_VIEW_SQL;
    arrayOfString[11] = PHOTOS_BY_EVENT_VIEW_SQL;
    arrayOfString[12] = PHOTOS_BY_STREAM_VIEW_SQL;
    arrayOfString[13] = PHOTOS_BY_USER_VIEW_SQL;
    arrayOfString[14] = "CREATE VIEW photo_shape_view AS SELECT photo_shape.photo_id as photo_id, photo_shape.bounds as bounds, photo_shape.creator_id as creator_id, photo_shape.shape_id as shape_id, photo_shape.status as status, photo_shape.subject_id as subject_id, (SELECT a.name FROM contacts as a WHERE a.gaia_id=photo_shape.creator_id ) AS creator_name, (SELECT b.name FROM contacts as b WHERE b.gaia_id=photo_shape.subject_id ) AS subject_name FROM photo_shape";
    arrayOfString[15] = "CREATE VIEW message_notifications_view AS SELECT messages._id as _id, messages.message_id as message_id, messages.conversation_id as conversation_id, messages.author_id as author_id, messages.text as text, messages.image_url as image_url, messages.timestamp as timestamp, messages.status as status, messages.type as type, messages.notification_seen as notification_seen, author_alias.full_name as author_full_name, author_alias.first_name as author_first_name, author_alias.type as author_type, conversations.is_muted as conversation_muted, conversations.is_visible as conversation_visible, conversations.is_group as conversation_group, conversations.is_pending_accept as conversation_pending_accept, conversations.is_pending_leave as conversation_pending_leave, conversations.name as conversation_name, conversations.generated_name as generated_name, inviter_alias.participant_id as inviter_id, inviter_alias.full_name as inviter_full_name, inviter_alias.first_name as inviter_first_name, inviter_alias.type as inviter_type FROM messages LEFT JOIN participants author_alias ON messages.author_id=author_alias.participant_id LEFT JOIN conversations ON messages.conversation_id=conversations._id LEFT JOIN participants inviter_alias ON conversations.inviter_id=inviter_alias.participant_id";
    arrayOfString[16] = "CREATE VIEW deep_link_installs_view AS SELECT deep_link_installs._id as _id,deep_link_installs.timestamp as timestamp,deep_link_installs.package_name as package_name,deep_link_installs.launch_source as launch_source,contacts.name as name,activities.source_name as source_name,activities.embed_deep_link as embed_deep_link FROM deep_link_installs INNER JOIN activities ON deep_link_installs.activity_id=activities.activity_id INNER JOIN contacts ON deep_link_installs.author_id=contacts.gaia_id;";
    arrayOfString[17] = "CREATE VIEW event_people_view AS SELECT event_people._id as _id,event_people.event_id as event_id,event_people.gaia_id as gaia_id,contacts.person_id as person_id,contacts.name as name,contacts.sort_key as sort_key,contacts.avatar as avatar,contacts.last_updated_time as last_updated_time,contacts.profile_type as profile_type,contacts.profile_state as profile_state,contacts.in_my_circles as in_my_circles,contacts.blocked as blocked FROM event_people INNER JOIN contacts ON event_people.gaia_id=contacts.gaia_id;";
    return arrayOfString;
  }

  public static int getsActivitiesFirstPageSize(Context paramContext)
  {
    ensureActivitiesPageSizes(paramContext);
    return sActivitiesFirstPageSize;
  }

  private static void insertVirtualCircle(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("circle_id", paramString1);
    localContentValues.put("circle_name", paramString2);
    localContentValues.put("type", Integer.valueOf(-1));
    localContentValues.put("contact_count", Integer.valueOf(0));
    localContentValues.put("semantic_hints", Integer.valueOf(11));
    localContentValues.put("show_order", Integer.valueOf(paramInt2));
    localContentValues.put("volume", Integer.valueOf(0));
    paramSQLiteDatabase.insertWithOnConflict("circles", "circle_id", localContentValues, 4);
  }

  public static void insertVirtualCircles(Context paramContext, SQLiteDatabase paramSQLiteDatabase)
  {
    insertVirtualCircle(paramSQLiteDatabase, "v.nearby", paramContext.getString(R.string.stream_nearby), -1, 10000);
    insertVirtualCircle(paramSQLiteDatabase, "v.all.circles", paramContext.getString(R.string.stream_circles), -1, 0);
    insertVirtualCircle(paramSQLiteDatabase, "v.whatshot", paramContext.getString(R.string.stream_whats_hot), -1, 10);
  }

  private static boolean isInProjection(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    int i = 1;
    if (paramArrayOfString1 == null);
    while (true)
    {
      return i;
      if (paramArrayOfString2.length == i)
      {
        String str2 = paramArrayOfString2[0];
        int i2 = paramArrayOfString1.length;
        for (int i3 = 0; ; i3++)
        {
          if (i3 >= i2)
            break label107;
          if (str2.equals(paramArrayOfString1[i3]))
            break;
        }
      }
      int k = paramArrayOfString1.length;
      label101: for (int m = 0; ; m++)
      {
        if (m >= k)
          break label107;
        String str1 = paramArrayOfString1[m];
        int n = paramArrayOfString2.length;
        for (int i1 = 0; ; i1++)
        {
          if (i1 >= n)
            break label101;
          if (paramArrayOfString2[i1].equals(str1))
            break;
        }
      }
      label107: int j = 0;
    }
  }

  public static void localeChanged(Context paramContext)
  {
    EsAccount localEsAccount = EsService.getActiveAccount(paramContext);
    if (localEsAccount == null);
    while (true)
    {
      return;
      EsDatabaseHelper localEsDatabaseHelper = EsDatabaseHelper.getDatabaseHelper(paramContext, localEsAccount);
      SQLiteDatabase localSQLiteDatabase = localEsDatabaseHelper.getWritableDatabase();
      localSQLiteDatabase.setLocale(Locale.getDefault());
      localSQLiteDatabase.beginTransaction();
      try
      {
        localEsDatabaseHelper.rebuildTables(localSQLiteDatabase, localEsAccount);
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        ContentResolver.requestSync(AccountsUtil.newAccount(localEsAccount.getName()), "com.google.android.apps.plus.content.EsProvider", new Bundle());
      }
      finally
      {
        localSQLiteDatabase.endTransaction();
      }
    }
  }

  private static void preparePeopleSearchQuery(SQLiteQueryBuilder paramSQLiteQueryBuilder, String paramString1, boolean paramBoolean1, String paramString2, String paramString3, boolean paramBoolean2, String paramString4)
  {
    String[] arrayOfString = paramString1.toLowerCase().split("[\\u0009\\u000A\\u000B\\u000C\\u000D\\u0020\\u0085\\u00A0\\u1680\\u180E\\u2000\\u2001\\u2002\\u2003\\u2004\\u2005\\u2006\\u2007\\u2008\\u2009\\u200A\\u2028\\u2029\\u202F\\u205F\\u3000]");
    String str1 = "";
    for (int i = 0; i < arrayOfString.length; i++)
      str1 = str1 + "SELECT contacts" + ".person_id" + " AS filtered_person_id, MIN((CASE WHEN " + "search_key_type=" + 1 + " THEN search_key" + " ELSE NULL END)) AS email" + " FROM contacts" + " JOIN contact_search" + " ON (contacts" + ".person_id" + "=search_person_id" + ") WHERE " + "search_key GLOB " + DatabaseUtils.sqlEscapeString(new StringBuilder().append(arrayOfString[i]).append('*').toString()) + " AND in_my_circles" + "!=0 GROUP BY filtered_person_id, " + "search_key_type INTERSECT ";
    String str2 = str1.substring(0, -11 + str1.length());
    if (!TextUtils.isEmpty(paramString4))
      str2 = str2 + " UNION SELECT " + "contacts." + "person_id AS filtered_person_id, " + " NULL AS email" + " FROM contacts" + " WHERE gaia_id" + " IN (" + paramString4 + ") AND (" + "name LIKE " + DatabaseUtils.sqlEscapeString(new StringBuilder().append(paramString1).append('%').toString()) + " OR name" + " LIKE " + DatabaseUtils.sqlEscapeString(new StringBuilder("% ").append(paramString1).append('%').toString()) + ")";
    String str3 = str2 + " LIMIT " + paramString2;
    paramSQLiteQueryBuilder.setTables("contacts JOIN (" + str3 + ") ON (contacts" + ".person_id" + "=filtered_person_id) LEFT OUTER JOIN " + "circle_contact ON (" + "contacts." + "person_id=" + "circle_contact." + "link_person_id)");
    paramSQLiteQueryBuilder.appendWhere("gaia_id");
    paramSQLiteQueryBuilder.appendWhere(" != '");
    paramSQLiteQueryBuilder.appendWhere(paramString3);
    paramSQLiteQueryBuilder.appendWhere("'");
    if (!paramBoolean1)
    {
      paramSQLiteQueryBuilder.appendWhere(" AND ");
      paramSQLiteQueryBuilder.appendWhere("profile_type");
      paramSQLiteQueryBuilder.appendWhere(" != ");
      paramSQLiteQueryBuilder.appendWhere(Integer.toString(2));
    }
    if (TextUtils.isEmpty(paramString1))
    {
      paramSQLiteQueryBuilder.appendWhere(" AND ");
      paramSQLiteQueryBuilder.appendWhere("0");
    }
    if (!paramBoolean2)
    {
      paramSQLiteQueryBuilder.appendWhere(" AND ");
      paramSQLiteQueryBuilder.appendWhere("in_my_circles");
      paramSQLiteQueryBuilder.appendWhere(" = 0");
    }
  }

  private static String[] prependArgs(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    String[] arrayOfString;
    if ((paramArrayOfString2 == null) || (paramArrayOfString2.length == 0))
    {
      arrayOfString = paramArrayOfString1;
      return arrayOfString;
    }
    if (paramArrayOfString1 == null);
    for (int i = 0; ; i = paramArrayOfString1.length)
    {
      int j = paramArrayOfString2.length;
      arrayOfString = new String[i + j];
      System.arraycopy(paramArrayOfString2, 0, arrayOfString, 0, j);
      if (i <= 0)
        break;
      System.arraycopy(paramArrayOfString1, 0, arrayOfString, j, i);
      break;
    }
  }

  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString)
  {
    throw new IllegalArgumentException("Delete not supported: " + paramUri);
  }

  public String getType(Uri paramUri)
  {
    String str;
    switch (URI_MATCHER.match(paramUri))
    {
    default:
      throw new IllegalArgumentException("Unknown URI: " + paramUri);
    case 1:
      str = "vnd.android.cursor.dir/vnd.google.android.apps.plus.accounts";
    case 21:
    case 22:
    case 30:
    case 40:
    case 50:
    case 70:
    case 72:
    case 100:
    case 110:
    case 120:
    case 160:
    }
    while (true)
    {
      return str;
      str = "vnd.android.cursor.dir/vnd.google.android.apps.plus.activities";
      continue;
      str = "vnd.android.cursor.dir/vnd.google.android.apps.plus.comments";
      continue;
      str = "vnd.android.cursor.dir/vnd.google.android.apps.plus.locations";
      continue;
      str = "vnd.android.cursor.dir/vnd.google.android.apps.plus.notifications";
      continue;
      str = "vnd.android.cursor.dir/vnd.google.android.apps.plus.contacts";
      continue;
      str = "vnd.android.cursor.dir/vnd.google.android.apps.plus.conversations";
      continue;
      str = "vnd.android.cursor.dir/vnd.google.android.apps.plus.participants";
      continue;
      str = "vnd.android.cursor.dir/vnd.google.android.apps.plus.messages";
      continue;
      str = "vnd.android.cursor.dir/vnd.google.android.apps.plus.message_notifications";
    }
  }

  public Uri insert(Uri paramUri, ContentValues paramContentValues)
  {
    throw new IllegalStateException("Insert not supported " + paramUri);
  }

  public boolean onCreate()
  {
    return true;
  }

  public ParcelFileDescriptor openFile(Uri paramUri, String paramString)
    throws FileNotFoundException
  {
    try
    {
      int i = URI_MATCHER.match(paramUri);
      if (EsLog.isLoggable("EsProvider", 3))
        Log.d("EsProvider", "Open asset file uri: " + paramUri + " -> " + i);
      if (i != 200)
        throw new IllegalArgumentException("Unsupported URI: " + paramUri);
    }
    finally
    {
    }
    long l = Binder.clearCallingIdentity();
    try
    {
      String str = paramUri.getQueryParameter("file");
      if (EsLog.isLoggable("EsProvider", 3))
        Log.d("EsProvider", "Opening panorama file: " + str);
      ParcelFileDescriptor localParcelFileDescriptor = ParcelFileDescriptor.open(new File(str), 268435456);
      Binder.restoreCallingIdentity(l);
      return localParcelFileDescriptor;
    }
    finally
    {
      localObject2 = finally;
      Binder.restoreCallingIdentity(l);
      throw localObject2;
    }
  }

  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2)
  {
    String str1 = paramUri.getQueryParameter("account");
    if (str1 == null)
      throw new IllegalArgumentException("Every URI must have the 'account' parameter specified.\nURI: " + paramUri);
    int i = Integer.parseInt(str1);
    String str2 = null;
    String str3 = paramUri.getQueryParameter("limit");
    String[] arrayOfString1 = paramArrayOfString2;
    int j = URI_MATCHER.match(paramUri);
    if (EsLog.isLoggable("EsProvider", 3))
      Log.d("EsProvider", "QUERY URI: " + paramUri + " -> " + j);
    SQLiteQueryBuilder localSQLiteQueryBuilder = new SQLiteQueryBuilder();
    String str4;
    switch (j)
    {
    default:
      throw new IllegalArgumentException("Unknown URI " + paramUri);
    case 1:
      localSQLiteQueryBuilder.setTables("account_status");
      localSQLiteQueryBuilder.setProjectionMap(ACCOUNTS_PROJECTION_MAP);
      str4 = null;
    case 24:
    case 22:
    case 20:
    case 21:
    case 23:
    case 30:
    case 40:
    case 50:
    case 60:
    case 72:
    case 70:
    case 71:
    case 75:
    case 73:
    case 74:
    case 100:
    case 115:
    case 116:
    case 110:
    case 160:
    case 120:
    case 130:
    case 132:
    case 144:
    case 134:
    case 135:
    case 145:
    case 139:
    case 138:
    case 140:
    case 141:
    case 143:
    case 180:
    case 181:
    case 182:
    case 190:
    case 211:
    case 210:
    case 212:
    }
    while (true)
    {
      if (!TextUtils.isEmpty(paramString2))
        str4 = paramString2;
      if (EsLog.isLoggable("EsProvider", 3))
      {
        StringBuilder localStringBuilder = new StringBuilder("QUERY: ");
        Log.d("EsProvider", localSQLiteQueryBuilder.buildQuery(paramArrayOfString1, paramString1, arrayOfString1, str2, null, paramString2, str3));
      }
      Cursor localCursor = localSQLiteQueryBuilder.query(EsDatabaseHelper.getDatabaseHelper(getContext(), i).getReadableDatabase(), paramArrayOfString1, paramString1, arrayOfString1, str2, null, str4, str3);
      if (EsLog.isLoggable("EsProvider", 3))
        Log.d("EsProvider", "QUERY results: " + localCursor.getCount());
      localCursor.setNotificationUri(getContext().getContentResolver(), paramUri);
      return localCursor;
      localSQLiteQueryBuilder.setTables("activities");
      localSQLiteQueryBuilder.setProjectionMap(ACTIVITY_SUMMARY_PROJECTION_MAP);
      str2 = null;
      str4 = null;
      continue;
      localSQLiteQueryBuilder.appendWhere("activity_id");
      localSQLiteQueryBuilder.appendWhere("=?");
      String[] arrayOfString21 = new String[1];
      arrayOfString21[0] = ((String)paramUri.getPathSegments().get(2));
      arrayOfString1 = prependArgs(arrayOfString1, arrayOfString21);
      localSQLiteQueryBuilder.setTables("activity_view");
      localSQLiteQueryBuilder.setProjectionMap(ACTIVITIES_VIEW_PROJECTION_MAP);
      str2 = null;
      str4 = null;
      continue;
      localSQLiteQueryBuilder.setTables("activities_stream_view");
      localSQLiteQueryBuilder.appendWhere("stream_key");
      localSQLiteQueryBuilder.appendWhere("=?");
      String[] arrayOfString20 = new String[1];
      arrayOfString20[0] = ((String)paramUri.getPathSegments().get(2));
      arrayOfString1 = prependArgs(arrayOfString1, arrayOfString20);
      localSQLiteQueryBuilder.setProjectionMap(ACTIVITIES_VIEW_PROJECTION_MAP);
      str2 = null;
      str4 = null;
      continue;
      localSQLiteQueryBuilder.setTables("activities_stream_view");
      localSQLiteQueryBuilder.appendWhere("('g:'||author_id) IN ( SELECT link_person_id FROM circle_contact WHERE link_circle_id=?)");
      String[] arrayOfString19 = new String[1];
      arrayOfString19[0] = ((String)paramUri.getPathSegments().get(1));
      arrayOfString1 = prependArgs(arrayOfString1, arrayOfString19);
      localSQLiteQueryBuilder.setProjectionMap(ACTIVITIES_VIEW_PROJECTION_MAP);
      str4 = paramString2;
      if (str3 != null)
      {
        boolean bool7 = Long.parseLong(str3) < 20L;
        str2 = null;
        if (bool7)
        {
          str3 = Long.toString(20L);
          str2 = null;
        }
      }
      else
      {
        str3 = Long.toString(20L);
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("comments_view");
        localSQLiteQueryBuilder.appendWhere("activity_id");
        localSQLiteQueryBuilder.appendWhere("=?");
        String[] arrayOfString18 = new String[1];
        arrayOfString18[0] = ((String)paramUri.getPathSegments().get(2));
        arrayOfString1 = prependArgs(arrayOfString1, arrayOfString18);
        localSQLiteQueryBuilder.setProjectionMap(COMMENTS_VIEW_PROJECTION_MAP);
        str2 = null;
        str4 = null;
        continue;
        localSQLiteQueryBuilder.setTables("location_queries_view");
        localSQLiteQueryBuilder.appendWhere("key");
        localSQLiteQueryBuilder.appendWhere("=?");
        String[] arrayOfString17 = new String[1];
        arrayOfString17[0] = ((String)paramUri.getPathSegments().get(2));
        arrayOfString1 = prependArgs(arrayOfString1, arrayOfString17);
        localSQLiteQueryBuilder.setProjectionMap(LOCATION_QUERIES_VIEW_PROJECTION_MAP);
        str2 = null;
        str4 = null;
        continue;
        localSQLiteQueryBuilder.setTables("notifications");
        localSQLiteQueryBuilder.setProjectionMap(NOTIFICATIONS_PROJECTION_MAP);
        if (str3 != null)
          if (Long.parseLong(str3) <= 200L);
        for (str3 = Long.toString(200L); ; str3 = Long.toString(200L))
        {
          str2 = null;
          str4 = null;
          break;
        }
        if (isInProjection(paramArrayOfString1, new String[] { "member_ids" }))
          localSQLiteQueryBuilder.setTables("circles LEFT OUTER JOIN (" + "SELECT link_circle_id,link_person_id FROM circle_contact JOIN contacts AS c  ON (c.person_id=link_person_id) ORDER BY c.sort_key, UPPER(c.name)" + ") AS " + "circle_contact ON ( " + "circle_contact." + "link_circle_id = " + "circles." + "circle_id)");
        for (str2 = "circle_id"; ; str2 = null)
        {
          localSQLiteQueryBuilder.setProjectionMap(CIRCLES_PROJECTION_MAP);
          str4 = null;
          break;
          localSQLiteQueryBuilder.setTables("circles");
        }
        localSQLiteQueryBuilder.appendWhere("person_id");
        localSQLiteQueryBuilder.appendWhere("=?");
        String[] arrayOfString16 = new String[1];
        arrayOfString16[0] = ((String)paramUri.getPathSegments().get(2));
        arrayOfString1 = prependArgs(arrayOfString1, arrayOfString16);
        if (isInProjection(paramArrayOfString1, new String[] { "contact_update_time", "contact_proto", "profile_update_time", "profile_proto" }));
        for (String str12 = "contacts LEFT OUTER JOIN profiles ON (contacts.person_id=profiles.profile_person_id)"; ; str12 = "contacts")
        {
          boolean bool6 = isInProjection(paramArrayOfString1, new String[] { "packed_circle_ids" });
          str2 = null;
          if (bool6)
          {
            str12 = str12 + " LEFT OUTER JOIN circle_contact ON ( circle_contact.link_person_id = contacts.person_id)";
            str2 = "person_id";
          }
          localSQLiteQueryBuilder.setTables(str12);
          localSQLiteQueryBuilder.setProjectionMap(CONTACTS_PROJECTION_MAP);
          str4 = "sort_key, UPPER(name)";
          break;
        }
        localSQLiteQueryBuilder.setTables("contacts JOIN circle_contact ON (contacts.person_id=circle_contact.link_person_id) JOIN circles ON (circle_contact.link_circle_id = circles.circle_id)");
        localSQLiteQueryBuilder.appendWhere("person_id");
        localSQLiteQueryBuilder.appendWhere(" IN (");
        localSQLiteQueryBuilder.appendWhere("SELECT ");
        localSQLiteQueryBuilder.appendWhere("link_person_id");
        localSQLiteQueryBuilder.appendWhere(" FROM ");
        localSQLiteQueryBuilder.appendWhere("circle_contact");
        localSQLiteQueryBuilder.appendWhere(" WHERE ");
        localSQLiteQueryBuilder.appendWhere("link_circle_id");
        localSQLiteQueryBuilder.appendWhere("=?");
        String[] arrayOfString15 = new String[1];
        arrayOfString15[0] = ((String)paramUri.getPathSegments().get(2));
        arrayOfString1 = prependArgs(arrayOfString1, arrayOfString15);
        localSQLiteQueryBuilder.appendWhere(")");
        localSQLiteQueryBuilder.setProjectionMap(CONTACTS_PROJECTION_MAP);
        str4 = "UPPER(name)";
        str2 = "person_id";
        continue;
        String str11 = "contacts INNER JOIN square_contact ON (contacts.person_id=square_contact.link_person_id)";
        boolean bool5 = isInProjection(paramArrayOfString1, new String[] { "packed_circle_ids" });
        str2 = null;
        if (bool5)
        {
          str11 = str11 + " LEFT OUTER JOIN circle_contact ON ( circle_contact.link_person_id = contacts.person_id)";
          str2 = "person_id";
        }
        localSQLiteQueryBuilder.setTables(str11);
        localSQLiteQueryBuilder.appendWhere("person_id");
        localSQLiteQueryBuilder.appendWhere(" IN (");
        localSQLiteQueryBuilder.appendWhere("SELECT ");
        localSQLiteQueryBuilder.appendWhere("link_person_id");
        localSQLiteQueryBuilder.appendWhere(" FROM ");
        localSQLiteQueryBuilder.appendWhere("square_contact");
        localSQLiteQueryBuilder.appendWhere(" WHERE ");
        localSQLiteQueryBuilder.appendWhere("link_square_id");
        localSQLiteQueryBuilder.appendWhere("=?");
        String[] arrayOfString14 = new String[1];
        arrayOfString14[0] = ((String)paramUri.getPathSegments().get(2));
        arrayOfString1 = prependArgs(arrayOfString1, arrayOfString14);
        localSQLiteQueryBuilder.appendWhere(")");
        localSQLiteQueryBuilder.setProjectionMap(SQUARE_CONTACTS_PROJECTION_MAP);
        str4 = "UPPER(name)";
        continue;
        String str10 = "contacts JOIN suggested_people ON (contacts.person_id=suggested_people.suggested_person_id)";
        boolean bool4 = isInProjection(paramArrayOfString1, new String[] { "packed_circle_ids" });
        str2 = null;
        if (bool4)
        {
          str10 = str10 + " LEFT OUTER JOIN circle_contact ON ( circle_contact.link_person_id = contacts.person_id)";
          str2 = "suggested_people._id";
        }
        localSQLiteQueryBuilder.setTables(str10);
        localSQLiteQueryBuilder.setProjectionMap(SUGGESTED_PEOPLE_PROJECTION_MAP);
        paramString1 = "dismissed=0 AND blocked=0";
        str4 = "CAST (category_sort_key AS INTEGER),sort_order";
        continue;
        List localList = paramUri.getPathSegments();
        if (localList.size() == 2);
        for (String str6 = ""; ; str6 = ((String)localList.get(2)).trim())
        {
          String str7 = paramUri.getQueryParameter("self_gaia_id");
          boolean bool1 = "true".equals(paramUri.getQueryParameter("plus_pages"));
          boolean bool2 = "true".equals(paramUri.getQueryParameter("in_circles"));
          String str8 = paramUri.getQueryParameter("activity_id");
          String str9 = null;
          if (str8 != null)
            str9 = new StringBuilder("SELECT author_id FROM activities WHERE activity_id =  ").append(DatabaseUtils.sqlEscapeString(str8)).toString() + " UNION " + new StringBuilder("SELECT author_id FROM activity_comments WHERE activity_id = ").append(DatabaseUtils.sqlEscapeString(str8)).toString();
          if ((str6.startsWith("+")) || (str6.startsWith("@")))
            str6 = str6.substring(1);
          preparePeopleSearchQuery(localSQLiteQueryBuilder, str6, bool1, str3, str7, bool2, str9);
          str2 = "person_id";
          localSQLiteQueryBuilder.setProjectionMap(CONTACTS_SEARCH_PROJECTION_MAP);
          str4 = "UPPER(name)";
          boolean bool3 = TextUtils.isEmpty(str9);
          str3 = null;
          if (bool3)
            break;
          str4 = "gaia_id IN (" + str9 + ") DESC," + str4;
          str3 = null;
          break;
        }
        localSQLiteQueryBuilder.setTables("conversations_view");
        localSQLiteQueryBuilder.setProjectionMap(CONVERSATIONS_PROJECTION_MAP);
        str4 = paramString2;
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("messenger_suggestions");
        localSQLiteQueryBuilder.setProjectionMap(MESSENGER_SUGGESTIONS_PROJECTION_MAP);
        str4 = paramString2;
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("hangout_suggestions");
        localSQLiteQueryBuilder.setProjectionMap(HANGOUT_SUGGESTIONS_PROJECTION_MAP);
        str4 = paramString2;
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("participants_view");
        localSQLiteQueryBuilder.appendWhere("conversation_id");
        localSQLiteQueryBuilder.appendWhere("=?");
        String[] arrayOfString13 = new String[1];
        arrayOfString13[0] = ((String)paramUri.getPathSegments().get(2));
        arrayOfString1 = prependArgs(arrayOfString1, arrayOfString13);
        localSQLiteQueryBuilder.setProjectionMap(PARTICIPANTS_PROJECTION_MAP);
        str4 = paramString2;
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("message_notifications_view");
        localSQLiteQueryBuilder.setProjectionMap(MESSAGE_NOTIFICATIONS_PROJECTION_MAP);
        str4 = paramString2;
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("messages_view");
        localSQLiteQueryBuilder.appendWhere("conversation_id");
        localSQLiteQueryBuilder.appendWhere("=?");
        String[] arrayOfString12 = new String[1];
        arrayOfString12[0] = ((String)paramUri.getPathSegments().get(2));
        arrayOfString1 = prependArgs(arrayOfString1, arrayOfString12);
        localSQLiteQueryBuilder.setProjectionMap(MESSAGES_PROJECTION_MAP);
        str4 = paramString2;
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("photo_home_view");
        localSQLiteQueryBuilder.setProjectionMap(PHOTO_HOME_MAP);
        str4 = paramString2;
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("album_view");
        localSQLiteQueryBuilder.appendWhere("owner_id");
        localSQLiteQueryBuilder.appendWhere("=?");
        localSQLiteQueryBuilder.appendWhere(" AND ");
        localSQLiteQueryBuilder.appendWhere("title");
        localSQLiteQueryBuilder.appendWhere(" IS NOT NULL");
        localSQLiteQueryBuilder.appendWhere(" AND ");
        localSQLiteQueryBuilder.appendWhere("url");
        localSQLiteQueryBuilder.appendWhere(" IS NOT NULL");
        localSQLiteQueryBuilder.appendWhere(" AND ");
        localSQLiteQueryBuilder.appendWhere("is_activity");
        localSQLiteQueryBuilder.appendWhere(" = 0");
        String[] arrayOfString11 = new String[1];
        arrayOfString11[0] = ((String)paramUri.getPathSegments().get(1));
        arrayOfString1 = prependArgs(arrayOfString1, arrayOfString11);
        localSQLiteQueryBuilder.setProjectionMap(ALBUM_VIEW_MAP);
        str4 = paramString2;
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("album_view");
        localSQLiteQueryBuilder.appendWhere("album_id");
        localSQLiteQueryBuilder.appendWhere("=?");
        localSQLiteQueryBuilder.appendWhere(" AND ");
        localSQLiteQueryBuilder.appendWhere("owner_id");
        localSQLiteQueryBuilder.appendWhere("=?");
        localSQLiteQueryBuilder.appendWhere(" AND ");
        localSQLiteQueryBuilder.appendWhere("title");
        localSQLiteQueryBuilder.appendWhere(" IS NOT NULL");
        localSQLiteQueryBuilder.appendWhere(" AND ");
        localSQLiteQueryBuilder.appendWhere("is_activity");
        localSQLiteQueryBuilder.appendWhere(" = 0");
        String[] arrayOfString10 = new String[2];
        arrayOfString10[0] = ((String)paramUri.getPathSegments().get(1));
        arrayOfString10[1] = ((String)paramUri.getPathSegments().get(2));
        arrayOfString1 = prependArgs(arrayOfString1, arrayOfString10);
        localSQLiteQueryBuilder.setProjectionMap(ALBUM_VIEW_MAP);
        str4 = paramString2;
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("photo_view");
        localSQLiteQueryBuilder.appendWhere("photo_id");
        localSQLiteQueryBuilder.appendWhere("=?");
        String[] arrayOfString9 = new String[1];
        arrayOfString9[0] = ((String)paramUri.getPathSegments().get(1));
        arrayOfString1 = prependArgs(arrayOfString1, arrayOfString9);
        localSQLiteQueryBuilder.setProjectionMap(PHOTO_VIEW_MAP);
        localSQLiteQueryBuilder.setDistinct(true);
        str3 = "1";
        str4 = paramString2;
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("photos_by_album_view");
        localSQLiteQueryBuilder.appendWhere("album_id");
        localSQLiteQueryBuilder.appendWhere("=?");
        String[] arrayOfString8 = new String[1];
        arrayOfString8[0] = ((String)paramUri.getPathSegments().get(1));
        arrayOfString1 = prependArgs(arrayOfString1, arrayOfString8);
        localSQLiteQueryBuilder.setProjectionMap(PHOTOS_BY_ALBUM_VIEW_MAP);
        localSQLiteQueryBuilder.setDistinct(true);
        str4 = paramString2;
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("photos_by_event_view");
        localSQLiteQueryBuilder.appendWhere("event_id");
        localSQLiteQueryBuilder.appendWhere("=?");
        String[] arrayOfString7 = new String[1];
        arrayOfString7[0] = ((String)paramUri.getPathSegments().get(1));
        arrayOfString1 = prependArgs(arrayOfString1, arrayOfString7);
        localSQLiteQueryBuilder.setProjectionMap(PHOTOS_BY_EVENT_VIEW_MAP);
        localSQLiteQueryBuilder.setDistinct(true);
        str4 = paramString2;
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("photos_by_user_view");
        localSQLiteQueryBuilder.appendWhere("photo_of_user_id");
        localSQLiteQueryBuilder.appendWhere("=?");
        String[] arrayOfString6 = new String[1];
        arrayOfString6[0] = ((String)paramUri.getPathSegments().get(1));
        arrayOfString1 = prependArgs(arrayOfString1, arrayOfString6);
        localSQLiteQueryBuilder.setProjectionMap(PHOTOS_BY_USER_VIEW_MAP);
        localSQLiteQueryBuilder.setDistinct(true);
        str4 = paramString2;
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("photos_by_stream_view");
        localSQLiteQueryBuilder.appendWhere("stream_id");
        localSQLiteQueryBuilder.appendWhere("=?");
        localSQLiteQueryBuilder.appendWhere(" AND ");
        localSQLiteQueryBuilder.appendWhere("owner_id");
        localSQLiteQueryBuilder.appendWhere("=?");
        localSQLiteQueryBuilder.setProjectionMap(PHOTOS_BY_STREAM_VIEW_MAP);
        localSQLiteQueryBuilder.setDistinct(true);
        str4 = paramString2;
        String[] arrayOfString5 = new String[2];
        arrayOfString5[0] = ((String)paramUri.getPathSegments().get(1));
        arrayOfString5[1] = ((String)paramUri.getPathSegments().get(2));
        arrayOfString1 = prependArgs(arrayOfString1, arrayOfString5);
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("photo_home_view");
        localSQLiteQueryBuilder.appendWhere("type");
        localSQLiteQueryBuilder.appendWhere("='");
        localSQLiteQueryBuilder.appendWhere("photos_of_me");
        localSQLiteQueryBuilder.appendWhere("'");
        localSQLiteQueryBuilder.setProjectionMap(PHOTO_NOTIFICATION_MAP);
        localSQLiteQueryBuilder.setDistinct(true);
        str4 = paramString2;
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("photo_comment JOIN contacts ON photo_comment.author_id=contacts.gaia_id");
        localSQLiteQueryBuilder.appendWhere("photo_id");
        localSQLiteQueryBuilder.appendWhere("=?");
        String[] arrayOfString4 = new String[1];
        arrayOfString4[0] = ((String)paramUri.getPathSegments().get(1));
        arrayOfString1 = prependArgs(arrayOfString1, arrayOfString4);
        localSQLiteQueryBuilder.setProjectionMap(PHOTO_COMMENTS_MAP);
        localSQLiteQueryBuilder.setDistinct(true);
        str4 = paramString2;
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("photo_shape_view");
        localSQLiteQueryBuilder.appendWhere("photo_id");
        localSQLiteQueryBuilder.appendWhere("=?");
        String[] arrayOfString3 = new String[1];
        arrayOfString3[0] = ((String)paramUri.getPathSegments().get(1));
        arrayOfString1 = prependArgs(arrayOfString1, arrayOfString3);
        localSQLiteQueryBuilder.setProjectionMap(PHOTO_SHAPE_VIEW_MAP);
        localSQLiteQueryBuilder.setDistinct(true);
        str4 = paramString2;
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("network_data_transactions");
        localSQLiteQueryBuilder.setProjectionMap(NETWORK_DATA_TRANSACTIONS_PROJECTION_MAP);
        str2 = null;
        str4 = null;
        continue;
        localSQLiteQueryBuilder.setTables("network_data_stats");
        localSQLiteQueryBuilder.setProjectionMap(NETWORK_DATA_STATS_PROJECTION_MAP);
        str2 = null;
        str4 = null;
        continue;
        localSQLiteQueryBuilder.setTables("platform_audience");
        localSQLiteQueryBuilder.appendWhere("package_name");
        localSQLiteQueryBuilder.appendWhere("=");
        localSQLiteQueryBuilder.appendWhereEscapeString(paramUri.getLastPathSegment());
        localSQLiteQueryBuilder.setProjectionMap(PLATFORM_AUDIENCE_PROJECTION_MAP);
        localSQLiteQueryBuilder.setDistinct(true);
        str2 = null;
        str4 = null;
        continue;
        localSQLiteQueryBuilder.setTables("plus_pages");
        localSQLiteQueryBuilder.setProjectionMap(PLUS_PAGES_PROJECTION_MAP);
        str2 = null;
        str4 = null;
        continue;
        localSQLiteQueryBuilder.appendWhere("square_id");
        localSQLiteQueryBuilder.appendWhere("=?");
        String[] arrayOfString2 = new String[1];
        arrayOfString2[0] = ((String)paramUri.getPathSegments().get(1));
        arrayOfString1 = prependArgs(arrayOfString1, arrayOfString2);
        if (isInProjection(paramArrayOfString1, new String[] { "inviter_name", "inviter_photo_url" }));
        for (String str5 = "squares LEFT OUTER JOIN contacts ON (squares.inviter_gaia_id=contacts.gaia_id)"; ; str5 = "squares")
        {
          localSQLiteQueryBuilder.setTables(str5);
          localSQLiteQueryBuilder.setProjectionMap(SQUARES_PROJECTION_MAP);
          if (TextUtils.isEmpty(paramString2))
            break label3620;
          str4 = paramString2;
          str2 = null;
          break;
        }
        label3620: str4 = "sort_index";
        str2 = null;
        continue;
        localSQLiteQueryBuilder.setTables("emotishare_data");
        localSQLiteQueryBuilder.setProjectionMap(EMOTISHARE_PROJECTION_MAP);
        str2 = null;
        str4 = null;
      }
    }
  }

  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString)
  {
    throw new IllegalArgumentException("Update not supported: " + paramUri);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsProvider
 * JD-Core Version:    0.6.2
 */