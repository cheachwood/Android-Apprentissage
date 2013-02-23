package com.google.apps.people.notifications.proto.base;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.Builder;
import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.Internal.EnumLiteMap;
import java.io.IOException;
import java.io.ObjectStreamException;

public final class NotificationEnums extends GeneratedMessageLite
  implements NotificationEnumsOrBuilder
{
  private static final NotificationEnums defaultInstance = new NotificationEnums();
  private static final long serialVersionUID;
  private byte memoizedIsInitialized = -1;
  private int memoizedSerializedSize = -1;

  private NotificationEnums()
  {
  }

  private NotificationEnums(Builder paramBuilder)
  {
    super((byte)0);
  }

  public static NotificationEnums getDefaultInstance()
  {
    return defaultInstance;
  }

  public final int getSerializedSize()
  {
    int i = this.memoizedSerializedSize;
    if (i != -1);
    while (true)
    {
      return i;
      this.memoizedSerializedSize = 0;
      i = 0;
    }
  }

  public final boolean isInitialized()
  {
    int i = 1;
    int j = this.memoizedIsInitialized;
    if (j != -1)
      if (j != i);
    while (true)
    {
      return i;
      i = 0;
      continue;
      this.memoizedIsInitialized = i;
    }
  }

  protected final Object writeReplace()
    throws ObjectStreamException
  {
    return super.writeReplace();
  }

  public final void writeTo(CodedOutputStream paramCodedOutputStream)
    throws IOException
  {
    getSerializedSize();
  }

  public static final class Builder extends GeneratedMessageLite.Builder<NotificationEnums, Builder>
    implements NotificationEnumsOrBuilder
  {
    private NotificationEnums buildPartial()
    {
      return new NotificationEnums(this, (byte)0);
    }

    private Builder clone()
    {
      return new Builder().mergeFrom(buildPartial());
    }

    private Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws IOException
    {
      int i;
      do
      {
        i = paramCodedInputStream.readTag();
        switch (i)
        {
        default:
        case 0:
        }
      }
      while (parseUnknownField(paramCodedInputStream, paramExtensionRegistryLite, i));
      return this;
    }

    public final boolean isInitialized()
    {
      return true;
    }

    public final Builder mergeFrom(NotificationEnums paramNotificationEnums)
    {
      if (paramNotificationEnums == NotificationEnums.getDefaultInstance());
      return this;
    }
  }

  public static enum CircleExplicitInviteNotificationKey
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<CircleExplicitInviteNotificationKey> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value = 1;

    static
    {
      CircleExplicitInviteNotificationKey[] arrayOfCircleExplicitInviteNotificationKey = new CircleExplicitInviteNotificationKey[1];
      arrayOfCircleExplicitInviteNotificationKey[0] = CIRCLE_NOTIFICATION_SOURCE;
    }

    private CircleExplicitInviteNotificationKey(int paramInt1, int paramInt2)
    {
    }

    public static CircleExplicitInviteNotificationKey valueOf(int paramInt)
    {
      switch (paramInt)
      {
      default:
      case 1:
      }
      for (CircleExplicitInviteNotificationKey localCircleExplicitInviteNotificationKey = null; ; localCircleExplicitInviteNotificationKey = CIRCLE_NOTIFICATION_SOURCE)
        return localCircleExplicitInviteNotificationKey;
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static enum CircleNotificationKey
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<CircleNotificationKey> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      CircleNotificationKey[] arrayOfCircleNotificationKey = new CircleNotificationKey[3];
      arrayOfCircleNotificationKey[0] = CIRCLE_ID;
      arrayOfCircleNotificationKey[1] = CIRCLE_NAME;
      arrayOfCircleNotificationKey[2] = CIRCLE_PERSONAL_MESSAGE;
    }

    private CircleNotificationKey(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static CircleNotificationKey valueOf(int paramInt)
    {
      CircleNotificationKey localCircleNotificationKey;
      switch (paramInt)
      {
      default:
        localCircleNotificationKey = null;
      case 1:
      case 2:
      case 3:
      }
      while (true)
      {
        return localCircleNotificationKey;
        localCircleNotificationKey = CIRCLE_ID;
        continue;
        localCircleNotificationKey = CIRCLE_NAME;
        continue;
        localCircleNotificationKey = CIRCLE_PERSONAL_MESSAGE;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static enum CircleNotificationSourceValue
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<CircleNotificationSourceValue> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value = 1;

    static
    {
      CircleNotificationSourceValue[] arrayOfCircleNotificationSourceValue = new CircleNotificationSourceValue[1];
      arrayOfCircleNotificationSourceValue[0] = OFF_NETWORK_USER_ADDED_TO_CIRCLE;
    }

    private CircleNotificationSourceValue(int paramInt1, int paramInt2)
    {
    }

    public static CircleNotificationSourceValue valueOf(int paramInt)
    {
      switch (paramInt)
      {
      default:
      case 1:
      }
      for (CircleNotificationSourceValue localCircleNotificationSourceValue = null; ; localCircleNotificationSourceValue = OFF_NETWORK_USER_ADDED_TO_CIRCLE)
        return localCircleNotificationSourceValue;
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static enum CirclePersonalAddNotificationKey
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<CirclePersonalAddNotificationKey> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value = 1;

    static
    {
      CirclePersonalAddNotificationKey[] arrayOfCirclePersonalAddNotificationKey = new CirclePersonalAddNotificationKey[1];
      arrayOfCirclePersonalAddNotificationKey[0] = RECIPROCATING_MEMBERSHIP;
    }

    private CirclePersonalAddNotificationKey(int paramInt1, int paramInt2)
    {
    }

    public static CirclePersonalAddNotificationKey valueOf(int paramInt)
    {
      switch (paramInt)
      {
      default:
      case 1:
      }
      for (CirclePersonalAddNotificationKey localCirclePersonalAddNotificationKey = null; ; localCirclePersonalAddNotificationKey = RECIPROCATING_MEMBERSHIP)
        return localCirclePersonalAddNotificationKey;
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static enum CommonNotificationKey
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<CommonNotificationKey> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      RECIPIENT_LOCALE = new CommonNotificationKey("RECIPIENT_LOCALE", 2, 3);
      CommonNotificationKey[] arrayOfCommonNotificationKey = new CommonNotificationKey[3];
      arrayOfCommonNotificationKey[0] = APPLICATION_BASE_URL;
      arrayOfCommonNotificationKey[1] = SOURCE_APPLICATION_ID;
      arrayOfCommonNotificationKey[2] = RECIPIENT_LOCALE;
    }

    private CommonNotificationKey(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static CommonNotificationKey valueOf(int paramInt)
    {
      CommonNotificationKey localCommonNotificationKey;
      switch (paramInt)
      {
      default:
        localCommonNotificationKey = null;
      case 1:
      case 2:
      case 3:
      }
      while (true)
      {
        return localCommonNotificationKey;
        localCommonNotificationKey = APPLICATION_BASE_URL;
        continue;
        localCommonNotificationKey = SOURCE_APPLICATION_ID;
        continue;
        localCommonNotificationKey = RECIPIENT_LOCALE;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static enum EntityProfileNotificationKey
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<EntityProfileNotificationKey> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      ENTITY_ID = new EntityProfileNotificationKey("ENTITY_ID", 1, 2);
      ENTITY_PROFILE_PHOTO_URL = new EntityProfileNotificationKey("ENTITY_PROFILE_PHOTO_URL", 2, 3);
      EntityProfileNotificationKey[] arrayOfEntityProfileNotificationKey = new EntityProfileNotificationKey[3];
      arrayOfEntityProfileNotificationKey[0] = ENTITY_NAME;
      arrayOfEntityProfileNotificationKey[1] = ENTITY_ID;
      arrayOfEntityProfileNotificationKey[2] = ENTITY_PROFILE_PHOTO_URL;
    }

    private EntityProfileNotificationKey(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static EntityProfileNotificationKey valueOf(int paramInt)
    {
      EntityProfileNotificationKey localEntityProfileNotificationKey;
      switch (paramInt)
      {
      default:
        localEntityProfileNotificationKey = null;
      case 1:
      case 2:
      case 3:
      }
      while (true)
      {
        return localEntityProfileNotificationKey;
        localEntityProfileNotificationKey = ENTITY_NAME;
        continue;
        localEntityProfileNotificationKey = ENTITY_ID;
        continue;
        localEntityProfileNotificationKey = ENTITY_PROFILE_PHOTO_URL;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static enum GamesNotificationKey
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<GamesNotificationKey> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      GADGET_GID = new GamesNotificationKey("GADGET_GID", 1, 9);
      GADGET_NAME = new GamesNotificationKey("GADGET_NAME", 2, 2);
      GADGET_SCREENSHOT_URL = new GamesNotificationKey("GADGET_SCREENSHOT_URL", 3, 3);
      INVITE_PARAM = new GamesNotificationKey("INVITE_PARAM", 4, 4);
      MESSAGE_TITLE = new GamesNotificationKey("MESSAGE_TITLE", 5, 13);
      MESSAGE_BODY = new GamesNotificationKey("MESSAGE_BODY", 6, 10);
      MESSAGE_ANCHOR_TEXT = new GamesNotificationKey("MESSAGE_ANCHOR_TEXT", 7, 11);
      MESSAGE_IMAGE_URL = new GamesNotificationKey("MESSAGE_IMAGE_URL", 8, 12);
      AUTH_TOKEN = new GamesNotificationKey("AUTH_TOKEN", 9, 14);
      GADGET_ICON_URL = new GamesNotificationKey("GADGET_ICON_URL", 10, 15);
      GADGET_MARQUEE_ICON_URL = new GamesNotificationKey("GADGET_MARQUEE_ICON_URL", 11, 17);
      GADGET_HANGOUT_ID = new GamesNotificationKey("GADGET_HANGOUT_ID", 12, 16);
      GamesNotificationKey[] arrayOfGamesNotificationKey = new GamesNotificationKey[13];
      arrayOfGamesNotificationKey[0] = GADGET_URL;
      arrayOfGamesNotificationKey[1] = GADGET_GID;
      arrayOfGamesNotificationKey[2] = GADGET_NAME;
      arrayOfGamesNotificationKey[3] = GADGET_SCREENSHOT_URL;
      arrayOfGamesNotificationKey[4] = INVITE_PARAM;
      arrayOfGamesNotificationKey[5] = MESSAGE_TITLE;
      arrayOfGamesNotificationKey[6] = MESSAGE_BODY;
      arrayOfGamesNotificationKey[7] = MESSAGE_ANCHOR_TEXT;
      arrayOfGamesNotificationKey[8] = MESSAGE_IMAGE_URL;
      arrayOfGamesNotificationKey[9] = AUTH_TOKEN;
      arrayOfGamesNotificationKey[10] = GADGET_ICON_URL;
      arrayOfGamesNotificationKey[11] = GADGET_MARQUEE_ICON_URL;
      arrayOfGamesNotificationKey[12] = GADGET_HANGOUT_ID;
    }

    private GamesNotificationKey(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static GamesNotificationKey valueOf(int paramInt)
    {
      GamesNotificationKey localGamesNotificationKey;
      switch (paramInt)
      {
      case 5:
      case 6:
      case 7:
      case 8:
      default:
        localGamesNotificationKey = null;
      case 1:
      case 9:
      case 2:
      case 3:
      case 4:
      case 13:
      case 10:
      case 11:
      case 12:
      case 14:
      case 15:
      case 17:
      case 16:
      }
      while (true)
      {
        return localGamesNotificationKey;
        localGamesNotificationKey = GADGET_URL;
        continue;
        localGamesNotificationKey = GADGET_GID;
        continue;
        localGamesNotificationKey = GADGET_NAME;
        continue;
        localGamesNotificationKey = GADGET_SCREENSHOT_URL;
        continue;
        localGamesNotificationKey = INVITE_PARAM;
        continue;
        localGamesNotificationKey = MESSAGE_TITLE;
        continue;
        localGamesNotificationKey = MESSAGE_BODY;
        continue;
        localGamesNotificationKey = MESSAGE_ANCHOR_TEXT;
        continue;
        localGamesNotificationKey = MESSAGE_IMAGE_URL;
        continue;
        localGamesNotificationKey = AUTH_TOKEN;
        continue;
        localGamesNotificationKey = GADGET_ICON_URL;
        continue;
        localGamesNotificationKey = GADGET_MARQUEE_ICON_URL;
        continue;
        localGamesNotificationKey = GADGET_HANGOUT_ID;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static enum HangoutNotificationKey
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<HangoutNotificationKey> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      HANGOUT_URL = new HangoutNotificationKey("HANGOUT_URL", 2, 3);
      HangoutNotificationKey[] arrayOfHangoutNotificationKey = new HangoutNotificationKey[3];
      arrayOfHangoutNotificationKey[0] = ACTIVITY_ID;
      arrayOfHangoutNotificationKey[1] = TYPE;
      arrayOfHangoutNotificationKey[2] = HANGOUT_URL;
    }

    private HangoutNotificationKey(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static HangoutNotificationKey valueOf(int paramInt)
    {
      HangoutNotificationKey localHangoutNotificationKey;
      switch (paramInt)
      {
      default:
        localHangoutNotificationKey = null;
      case 1:
      case 2:
      case 3:
      }
      while (true)
      {
        return localHangoutNotificationKey;
        localHangoutNotificationKey = ACTIVITY_ID;
        continue;
        localHangoutNotificationKey = TYPE;
        continue;
        localHangoutNotificationKey = HANGOUT_URL;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static enum MobileNotificationKey
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<MobileNotificationKey> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      CHAT_PHOTO_URL = new MobileNotificationKey("CHAT_PHOTO_URL", 1, 2);
      HAS_USED_BUNCH = new MobileNotificationKey("HAS_USED_BUNCH", 2, 3);
      MobileNotificationKey[] arrayOfMobileNotificationKey = new MobileNotificationKey[3];
      arrayOfMobileNotificationKey[0] = CHAT_TEXT;
      arrayOfMobileNotificationKey[1] = CHAT_PHOTO_URL;
      arrayOfMobileNotificationKey[2] = HAS_USED_BUNCH;
    }

    private MobileNotificationKey(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static MobileNotificationKey valueOf(int paramInt)
    {
      MobileNotificationKey localMobileNotificationKey;
      switch (paramInt)
      {
      default:
        localMobileNotificationKey = null;
      case 1:
      case 2:
      case 3:
      }
      while (true)
      {
        return localMobileNotificationKey;
        localMobileNotificationKey = CHAT_TEXT;
        continue;
        localMobileNotificationKey = CHAT_PHOTO_URL;
        continue;
        localMobileNotificationKey = HAS_USED_BUNCH;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static enum NotificationCategory
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<NotificationCategory> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      CIRCLE = new NotificationCategory("CIRCLE", 1, 2);
      PHOTOS = new NotificationCategory("PHOTOS", 2, 3);
      GAMES = new NotificationCategory("GAMES", 3, 4);
      SYSTEM = new NotificationCategory("SYSTEM", 4, 5);
      QUESTIONS = new NotificationCategory("QUESTIONS", 5, 6);
      MOBILE = new NotificationCategory("MOBILE", 6, 7);
      HANGOUT = new NotificationCategory("HANGOUT", 7, 8);
      ENTITYPROFILE = new NotificationCategory("ENTITYPROFILE", 8, 9);
      EVENTS = new NotificationCategory("EVENTS", 9, 10);
      SQUARE = new NotificationCategory("SQUARE", 10, 11);
      TARGET = new NotificationCategory("TARGET", 11, 12);
      GENERIC_CATEGORY = new NotificationCategory("GENERIC_CATEGORY", 12, 65535);
      NotificationCategory[] arrayOfNotificationCategory = new NotificationCategory[13];
      arrayOfNotificationCategory[0] = STREAM;
      arrayOfNotificationCategory[1] = CIRCLE;
      arrayOfNotificationCategory[2] = PHOTOS;
      arrayOfNotificationCategory[3] = GAMES;
      arrayOfNotificationCategory[4] = SYSTEM;
      arrayOfNotificationCategory[5] = QUESTIONS;
      arrayOfNotificationCategory[6] = MOBILE;
      arrayOfNotificationCategory[7] = HANGOUT;
      arrayOfNotificationCategory[8] = ENTITYPROFILE;
      arrayOfNotificationCategory[9] = EVENTS;
      arrayOfNotificationCategory[10] = SQUARE;
      arrayOfNotificationCategory[11] = TARGET;
      arrayOfNotificationCategory[12] = GENERIC_CATEGORY;
    }

    private NotificationCategory(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static NotificationCategory valueOf(int paramInt)
    {
      NotificationCategory localNotificationCategory;
      switch (paramInt)
      {
      default:
        localNotificationCategory = null;
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 65535:
      }
      while (true)
      {
        return localNotificationCategory;
        localNotificationCategory = STREAM;
        continue;
        localNotificationCategory = CIRCLE;
        continue;
        localNotificationCategory = PHOTOS;
        continue;
        localNotificationCategory = GAMES;
        continue;
        localNotificationCategory = SYSTEM;
        continue;
        localNotificationCategory = QUESTIONS;
        continue;
        localNotificationCategory = MOBILE;
        continue;
        localNotificationCategory = HANGOUT;
        continue;
        localNotificationCategory = ENTITYPROFILE;
        continue;
        localNotificationCategory = EVENTS;
        continue;
        localNotificationCategory = SQUARE;
        continue;
        localNotificationCategory = TARGET;
        continue;
        localNotificationCategory = GENERIC_CATEGORY;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static enum NotificationType
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<NotificationType> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      STREAM_POST = new NotificationType("STREAM_POST", 1, 1);
      STREAM_COMMENT_NEW = new NotificationType("STREAM_COMMENT_NEW", 2, 2);
      STREAM_COMMENT_FOLLOWUP = new NotificationType("STREAM_COMMENT_FOLLOWUP", 3, 3);
      STREAM_LIKE = new NotificationType("STREAM_LIKE", 4, 4);
      STREAM_RESHARE = new NotificationType("STREAM_RESHARE", 5, 5);
      CIRCLE_PERSONAL_ADD = new NotificationType("CIRCLE_PERSONAL_ADD", 6, 6);
      CIRCLE_STATUS_CHANGE = new NotificationType("CIRCLE_STATUS_CHANGE", 7, 7);
      CIRCLE_INVITE_REQUEST = new NotificationType("CIRCLE_INVITE_REQUEST", 8, 8);
      CIRCLE_MEMBER_JOINED_ES = new NotificationType("CIRCLE_MEMBER_JOINED_ES", 9, 9);
      PHOTOS_TAGGED_IN_PHOTO = new NotificationType("PHOTOS_TAGGED_IN_PHOTO", 10, 10);
      GAMES_INVITE_REQUEST = new NotificationType("GAMES_INVITE_REQUEST", 11, 11);
      GAMES_APPLICATION_MESSAGE = new NotificationType("GAMES_APPLICATION_MESSAGE", 12, 12);
      PHOTOS_TAG_ADDED_ON_PHOTO = new NotificationType("PHOTOS_TAG_ADDED_ON_PHOTO", 13, 13);
      STREAM_COMMENT_ON_MENTION = new NotificationType("STREAM_COMMENT_ON_MENTION", 14, 14);
      STREAM_COMMENT_AT_REPLY = new NotificationType("STREAM_COMMENT_AT_REPLY", 15, 15);
      STREAM_POST_AT_REPLY = new NotificationType("STREAM_POST_AT_REPLY", 16, 16);
      GAMES_PERSONAL_MESSAGE = new NotificationType("GAMES_PERSONAL_MESSAGE", 17, 17);
      PHOTOS_CAMERASYNC_UPLOADED = new NotificationType("PHOTOS_CAMERASYNC_UPLOADED", 18, 18);
      QUESTIONS_REFERRAL = new NotificationType("QUESTIONS_REFERRAL", 19, 19);
      STREAM_PLUSONE_POST = new NotificationType("STREAM_PLUSONE_POST", 20, 20);
      STREAM_PLUSONE_COMMENT = new NotificationType("STREAM_PLUSONE_COMMENT", 21, 21);
      QUESTIONS_REPLY = new NotificationType("QUESTIONS_REPLY", 22, 22);
      STREAM_POST_SHARED = new NotificationType("STREAM_POST_SHARED", 23, 24);
      STREAM_COMMENT_FOR_PHOTO_TAGGED = new NotificationType("STREAM_COMMENT_FOR_PHOTO_TAGGED", 24, 25);
      STREAM_COMMENT_FOR_PHOTO_TAGGER = new NotificationType("STREAM_COMMENT_FOR_PHOTO_TAGGER", 25, 26);
      QUESTIONS_DASHER_WELCOME = new NotificationType("QUESTIONS_DASHER_WELCOME", 26, 27);
      QUESTIONS_UNANSWERED_QUESTION = new NotificationType("QUESTIONS_UNANSWERED_QUESTION", 27, 28);
      MOBILE_NEW_CONVERSATION = new NotificationType("MOBILE_NEW_CONVERSATION", 28, 29);
      QUESTIONS_ANSWERER_FOLLOWUP = new NotificationType("QUESTIONS_ANSWERER_FOLLOWUP", 29, 30);
      QUESTIONS_ASKER_FOLLOWUP = new NotificationType("QUESTIONS_ASKER_FOLLOWUP", 30, 31);
      CIRCLE_EXPLICIT_INVITE = new NotificationType("CIRCLE_EXPLICIT_INVITE", 31, 32);
      HANGOUT_INVITE = new NotificationType("HANGOUT_INVITE", 32, 33);
      ENTITYPROFILE_ADD_ADMIN = new NotificationType("ENTITYPROFILE_ADD_ADMIN", 33, 34);
      ENTITYPROFILE_REMOVE_ADMIN = new NotificationType("ENTITYPROFILE_REMOVE_ADMIN", 34, 35);
      ENTITYPROFILE_TRANSFER_OWNERSHIP = new NotificationType("ENTITYPROFILE_TRANSFER_OWNERSHIP", 35, 36);
      SYSTEM_INVITE = new NotificationType("SYSTEM_INVITE", 36, 37);
      CIRCLE_INVITEE_JOINED_ES = new NotificationType("CIRCLE_INVITEE_JOINED_ES", 37, 38);
      CIRCLE_RECIPROCATING_ADD = new NotificationType("CIRCLE_RECIPROCATING_ADD", 38, 39);
      CIRCLE_DIGESTED_ADD = new NotificationType("CIRCLE_DIGESTED_ADD", 39, 40);
      PHOTOS_FACE_SUGGESTED = new NotificationType("PHOTOS_FACE_SUGGESTED", 40, 41);
      SYSTEM_WELCOME = new NotificationType("SYSTEM_WELCOME", 41, 42);
      SYSTEM_TOOLTIP = new NotificationType("SYSTEM_TOOLTIP", 42, 43);
      SYSTEM_FRIEND_SUGGESTIONS = new NotificationType("SYSTEM_FRIEND_SUGGESTIONS", 43, 44);
      SYSTEM_CELEBRITY_SUGGESTIONS = new NotificationType("SYSTEM_CELEBRITY_SUGGESTIONS", 44, 45);
      SYSTEM_CONNECTED_SITES = new NotificationType("SYSTEM_CONNECTED_SITES", 45, 46);
      EVENTS_INVITE = new NotificationType("EVENTS_INVITE", 46, 47);
      SQUARE_INVITE = new NotificationType("SQUARE_INVITE", 47, 48);
      SQUARE_SUBSCRIPTION = new NotificationType("SQUARE_SUBSCRIPTION", 48, 49);
      SYSTEM_DO_NOT_USE = new NotificationType("SYSTEM_DO_NOT_USE", 49, 50);
      SQUARE_MEMBERSHIP_APPROVED = new NotificationType("SQUARE_MEMBERSHIP_APPROVED", 50, 51);
      SQUARE_MEMBERSHIP_REQUEST = new NotificationType("SQUARE_MEMBERSHIP_REQUEST", 51, 52);
      EVENTS_CHANGE = new NotificationType("EVENTS_CHANGE", 52, 53);
      EVENTS_STARTING = new NotificationType("EVENTS_STARTING", 53, 54);
      EVENTS_PHOTOS_REMINDER = new NotificationType("EVENTS_PHOTOS_REMINDER", 54, 55);
      EVENTS_PHOTOS_COLLECTION = new NotificationType("EVENTS_PHOTOS_COLLECTION", 55, 56);
      EVENTS_INVITEE_CHANGE = new NotificationType("EVENTS_INVITEE_CHANGE", 56, 57);
      EVENTS_CHECKIN = new NotificationType("EVENTS_CHECKIN", 57, 58);
      EVENTS_BEFORE_REMINDER = new NotificationType("EVENTS_BEFORE_REMINDER", 58, 59);
      TARGET_SHARED = new NotificationType("TARGET_SHARED", 59, 60);
      STREAM_POST_FROM_UNCIRCLED = new NotificationType("STREAM_POST_FROM_UNCIRCLED", 60, 61);
      EVENTS_PHOTOS_ADDED = new NotificationType("EVENTS_PHOTOS_ADDED", 61, 62);
      BIRTHDAY_WISH = new NotificationType("BIRTHDAY_WISH", 62, 63);
      STREAM_POST_SUBSCRIBED = new NotificationType("STREAM_POST_SUBSCRIBED", 63, 64);
      NotificationType[] arrayOfNotificationType = new NotificationType[64];
      arrayOfNotificationType[0] = UNKNOWN_NOTIFICATION_TYPE;
      arrayOfNotificationType[1] = STREAM_POST;
      arrayOfNotificationType[2] = STREAM_COMMENT_NEW;
      arrayOfNotificationType[3] = STREAM_COMMENT_FOLLOWUP;
      arrayOfNotificationType[4] = STREAM_LIKE;
      arrayOfNotificationType[5] = STREAM_RESHARE;
      arrayOfNotificationType[6] = CIRCLE_PERSONAL_ADD;
      arrayOfNotificationType[7] = CIRCLE_STATUS_CHANGE;
      arrayOfNotificationType[8] = CIRCLE_INVITE_REQUEST;
      arrayOfNotificationType[9] = CIRCLE_MEMBER_JOINED_ES;
      arrayOfNotificationType[10] = PHOTOS_TAGGED_IN_PHOTO;
      arrayOfNotificationType[11] = GAMES_INVITE_REQUEST;
      arrayOfNotificationType[12] = GAMES_APPLICATION_MESSAGE;
      arrayOfNotificationType[13] = PHOTOS_TAG_ADDED_ON_PHOTO;
      arrayOfNotificationType[14] = STREAM_COMMENT_ON_MENTION;
      arrayOfNotificationType[15] = STREAM_COMMENT_AT_REPLY;
      arrayOfNotificationType[16] = STREAM_POST_AT_REPLY;
      arrayOfNotificationType[17] = GAMES_PERSONAL_MESSAGE;
      arrayOfNotificationType[18] = PHOTOS_CAMERASYNC_UPLOADED;
      arrayOfNotificationType[19] = QUESTIONS_REFERRAL;
      arrayOfNotificationType[20] = STREAM_PLUSONE_POST;
      arrayOfNotificationType[21] = STREAM_PLUSONE_COMMENT;
      arrayOfNotificationType[22] = QUESTIONS_REPLY;
      arrayOfNotificationType[23] = STREAM_POST_SHARED;
      arrayOfNotificationType[24] = STREAM_COMMENT_FOR_PHOTO_TAGGED;
      arrayOfNotificationType[25] = STREAM_COMMENT_FOR_PHOTO_TAGGER;
      arrayOfNotificationType[26] = QUESTIONS_DASHER_WELCOME;
      arrayOfNotificationType[27] = QUESTIONS_UNANSWERED_QUESTION;
      arrayOfNotificationType[28] = MOBILE_NEW_CONVERSATION;
      arrayOfNotificationType[29] = QUESTIONS_ANSWERER_FOLLOWUP;
      arrayOfNotificationType[30] = QUESTIONS_ASKER_FOLLOWUP;
      arrayOfNotificationType[31] = CIRCLE_EXPLICIT_INVITE;
      arrayOfNotificationType[32] = HANGOUT_INVITE;
      arrayOfNotificationType[33] = ENTITYPROFILE_ADD_ADMIN;
      arrayOfNotificationType[34] = ENTITYPROFILE_REMOVE_ADMIN;
      arrayOfNotificationType[35] = ENTITYPROFILE_TRANSFER_OWNERSHIP;
      arrayOfNotificationType[36] = SYSTEM_INVITE;
      arrayOfNotificationType[37] = CIRCLE_INVITEE_JOINED_ES;
      arrayOfNotificationType[38] = CIRCLE_RECIPROCATING_ADD;
      arrayOfNotificationType[39] = CIRCLE_DIGESTED_ADD;
      arrayOfNotificationType[40] = PHOTOS_FACE_SUGGESTED;
      arrayOfNotificationType[41] = SYSTEM_WELCOME;
      arrayOfNotificationType[42] = SYSTEM_TOOLTIP;
      arrayOfNotificationType[43] = SYSTEM_FRIEND_SUGGESTIONS;
      arrayOfNotificationType[44] = SYSTEM_CELEBRITY_SUGGESTIONS;
      arrayOfNotificationType[45] = SYSTEM_CONNECTED_SITES;
      arrayOfNotificationType[46] = EVENTS_INVITE;
      arrayOfNotificationType[47] = SQUARE_INVITE;
      arrayOfNotificationType[48] = SQUARE_SUBSCRIPTION;
      arrayOfNotificationType[49] = SYSTEM_DO_NOT_USE;
      arrayOfNotificationType[50] = SQUARE_MEMBERSHIP_APPROVED;
      arrayOfNotificationType[51] = SQUARE_MEMBERSHIP_REQUEST;
      arrayOfNotificationType[52] = EVENTS_CHANGE;
      arrayOfNotificationType[53] = EVENTS_STARTING;
      arrayOfNotificationType[54] = EVENTS_PHOTOS_REMINDER;
      arrayOfNotificationType[55] = EVENTS_PHOTOS_COLLECTION;
      arrayOfNotificationType[56] = EVENTS_INVITEE_CHANGE;
      arrayOfNotificationType[57] = EVENTS_CHECKIN;
      arrayOfNotificationType[58] = EVENTS_BEFORE_REMINDER;
      arrayOfNotificationType[59] = TARGET_SHARED;
      arrayOfNotificationType[60] = STREAM_POST_FROM_UNCIRCLED;
      arrayOfNotificationType[61] = EVENTS_PHOTOS_ADDED;
      arrayOfNotificationType[62] = BIRTHDAY_WISH;
      arrayOfNotificationType[63] = STREAM_POST_SUBSCRIBED;
    }

    private NotificationType(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static NotificationType valueOf(int paramInt)
    {
      NotificationType localNotificationType;
      switch (paramInt)
      {
      case 23:
      default:
        localNotificationType = null;
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
      case 17:
      case 18:
      case 19:
      case 20:
      case 21:
      case 22:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      case 46:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
      case 54:
      case 55:
      case 56:
      case 57:
      case 58:
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      case 64:
      }
      while (true)
      {
        return localNotificationType;
        localNotificationType = UNKNOWN_NOTIFICATION_TYPE;
        continue;
        localNotificationType = STREAM_POST;
        continue;
        localNotificationType = STREAM_COMMENT_NEW;
        continue;
        localNotificationType = STREAM_COMMENT_FOLLOWUP;
        continue;
        localNotificationType = STREAM_LIKE;
        continue;
        localNotificationType = STREAM_RESHARE;
        continue;
        localNotificationType = CIRCLE_PERSONAL_ADD;
        continue;
        localNotificationType = CIRCLE_STATUS_CHANGE;
        continue;
        localNotificationType = CIRCLE_INVITE_REQUEST;
        continue;
        localNotificationType = CIRCLE_MEMBER_JOINED_ES;
        continue;
        localNotificationType = PHOTOS_TAGGED_IN_PHOTO;
        continue;
        localNotificationType = GAMES_INVITE_REQUEST;
        continue;
        localNotificationType = GAMES_APPLICATION_MESSAGE;
        continue;
        localNotificationType = PHOTOS_TAG_ADDED_ON_PHOTO;
        continue;
        localNotificationType = STREAM_COMMENT_ON_MENTION;
        continue;
        localNotificationType = STREAM_COMMENT_AT_REPLY;
        continue;
        localNotificationType = STREAM_POST_AT_REPLY;
        continue;
        localNotificationType = GAMES_PERSONAL_MESSAGE;
        continue;
        localNotificationType = PHOTOS_CAMERASYNC_UPLOADED;
        continue;
        localNotificationType = QUESTIONS_REFERRAL;
        continue;
        localNotificationType = STREAM_PLUSONE_POST;
        continue;
        localNotificationType = STREAM_PLUSONE_COMMENT;
        continue;
        localNotificationType = QUESTIONS_REPLY;
        continue;
        localNotificationType = STREAM_POST_SHARED;
        continue;
        localNotificationType = STREAM_COMMENT_FOR_PHOTO_TAGGED;
        continue;
        localNotificationType = STREAM_COMMENT_FOR_PHOTO_TAGGER;
        continue;
        localNotificationType = QUESTIONS_DASHER_WELCOME;
        continue;
        localNotificationType = QUESTIONS_UNANSWERED_QUESTION;
        continue;
        localNotificationType = MOBILE_NEW_CONVERSATION;
        continue;
        localNotificationType = QUESTIONS_ANSWERER_FOLLOWUP;
        continue;
        localNotificationType = QUESTIONS_ASKER_FOLLOWUP;
        continue;
        localNotificationType = CIRCLE_EXPLICIT_INVITE;
        continue;
        localNotificationType = HANGOUT_INVITE;
        continue;
        localNotificationType = ENTITYPROFILE_ADD_ADMIN;
        continue;
        localNotificationType = ENTITYPROFILE_REMOVE_ADMIN;
        continue;
        localNotificationType = ENTITYPROFILE_TRANSFER_OWNERSHIP;
        continue;
        localNotificationType = SYSTEM_INVITE;
        continue;
        localNotificationType = CIRCLE_INVITEE_JOINED_ES;
        continue;
        localNotificationType = CIRCLE_RECIPROCATING_ADD;
        continue;
        localNotificationType = CIRCLE_DIGESTED_ADD;
        continue;
        localNotificationType = PHOTOS_FACE_SUGGESTED;
        continue;
        localNotificationType = SYSTEM_WELCOME;
        continue;
        localNotificationType = SYSTEM_TOOLTIP;
        continue;
        localNotificationType = SYSTEM_FRIEND_SUGGESTIONS;
        continue;
        localNotificationType = SYSTEM_CELEBRITY_SUGGESTIONS;
        continue;
        localNotificationType = SYSTEM_CONNECTED_SITES;
        continue;
        localNotificationType = EVENTS_INVITE;
        continue;
        localNotificationType = SQUARE_INVITE;
        continue;
        localNotificationType = SQUARE_SUBSCRIPTION;
        continue;
        localNotificationType = SYSTEM_DO_NOT_USE;
        continue;
        localNotificationType = SQUARE_MEMBERSHIP_APPROVED;
        continue;
        localNotificationType = SQUARE_MEMBERSHIP_REQUEST;
        continue;
        localNotificationType = EVENTS_CHANGE;
        continue;
        localNotificationType = EVENTS_STARTING;
        continue;
        localNotificationType = EVENTS_PHOTOS_REMINDER;
        continue;
        localNotificationType = EVENTS_PHOTOS_COLLECTION;
        continue;
        localNotificationType = EVENTS_INVITEE_CHANGE;
        continue;
        localNotificationType = EVENTS_CHECKIN;
        continue;
        localNotificationType = EVENTS_BEFORE_REMINDER;
        continue;
        localNotificationType = TARGET_SHARED;
        continue;
        localNotificationType = STREAM_POST_FROM_UNCIRCLED;
        continue;
        localNotificationType = EVENTS_PHOTOS_ADDED;
        continue;
        localNotificationType = BIRTHDAY_WISH;
        continue;
        localNotificationType = STREAM_POST_SUBSCRIBED;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static enum PhotosNotificationKey
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<PhotosNotificationKey> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      PHOTO_URL = new PhotosNotificationKey("PHOTO_URL", 2, 2);
      PHOTO_URLS = new PhotosNotificationKey("PHOTO_URLS", 3, 22);
      PHOTO_OWNER_OGID = new PhotosNotificationKey("PHOTO_OWNER_OGID", 4, 3);
      PHOTO_OWNER_NAME = new PhotosNotificationKey("PHOTO_OWNER_NAME", 5, 4);
      TAGGEE_OGID = new PhotosNotificationKey("TAGGEE_OGID", 6, 5);
      TAGGEE_NAME = new PhotosNotificationKey("TAGGEE_NAME", 7, 6);
      PHOTO_PAGE_URL = new PhotosNotificationKey("PHOTO_PAGE_URL", 8, 7);
      PHOTO_PAGE_URLS = new PhotosNotificationKey("PHOTO_PAGE_URLS", 9, 23);
      PHOTO_ID = new PhotosNotificationKey("PHOTO_ID", 10, 8);
      PHOTO_IDS = new PhotosNotificationKey("PHOTO_IDS", 11, 24);
      TAG_APPROVED = new PhotosNotificationKey("TAG_APPROVED", 12, 9);
      TAGGER_OGID = new PhotosNotificationKey("TAGGER_OGID", 13, 10);
      TAGGER_NAME = new PhotosNotificationKey("TAGGER_NAME", 14, 11);
      ALBUM_ID = new PhotosNotificationKey("ALBUM_ID", 15, 12);
      STREAM_ID = new PhotosNotificationKey("STREAM_ID", 16, 13);
      ALBUM_NAME = new PhotosNotificationKey("ALBUM_NAME", 17, 14);
      PHOTO_WIDTH = new PhotosNotificationKey("PHOTO_WIDTH", 18, 15);
      PHOTO_WIDTHS = new PhotosNotificationKey("PHOTO_WIDTHS", 19, 25);
      PHOTO_HEIGHT = new PhotosNotificationKey("PHOTO_HEIGHT", 20, 16);
      PHOTO_HEIGHTS = new PhotosNotificationKey("PHOTO_HEIGHTS", 21, 26);
      ALBUM_PAGE_URL = new PhotosNotificationKey("ALBUM_PAGE_URL", 22, 17);
      IS_VIDEO = new PhotosNotificationKey("IS_VIDEO", 23, 18);
      IS_VIDEOS = new PhotosNotificationKey("IS_VIDEOS", 24, 27);
      ALBUM_LABELS = new PhotosNotificationKey("ALBUM_LABELS", 25, 19);
      ALBUM_ACL_EXPANDED = new PhotosNotificationKey("ALBUM_ACL_EXPANDED", 26, 20);
      NUM_PHOTOS = new PhotosNotificationKey("NUM_PHOTOS", 27, 28);
      PhotosNotificationKey[] arrayOfPhotosNotificationKey = new PhotosNotificationKey[28];
      arrayOfPhotosNotificationKey[0] = TAG_ID;
      arrayOfPhotosNotificationKey[1] = TAG_IDS;
      arrayOfPhotosNotificationKey[2] = PHOTO_URL;
      arrayOfPhotosNotificationKey[3] = PHOTO_URLS;
      arrayOfPhotosNotificationKey[4] = PHOTO_OWNER_OGID;
      arrayOfPhotosNotificationKey[5] = PHOTO_OWNER_NAME;
      arrayOfPhotosNotificationKey[6] = TAGGEE_OGID;
      arrayOfPhotosNotificationKey[7] = TAGGEE_NAME;
      arrayOfPhotosNotificationKey[8] = PHOTO_PAGE_URL;
      arrayOfPhotosNotificationKey[9] = PHOTO_PAGE_URLS;
      arrayOfPhotosNotificationKey[10] = PHOTO_ID;
      arrayOfPhotosNotificationKey[11] = PHOTO_IDS;
      arrayOfPhotosNotificationKey[12] = TAG_APPROVED;
      arrayOfPhotosNotificationKey[13] = TAGGER_OGID;
      arrayOfPhotosNotificationKey[14] = TAGGER_NAME;
      arrayOfPhotosNotificationKey[15] = ALBUM_ID;
      arrayOfPhotosNotificationKey[16] = STREAM_ID;
      arrayOfPhotosNotificationKey[17] = ALBUM_NAME;
      arrayOfPhotosNotificationKey[18] = PHOTO_WIDTH;
      arrayOfPhotosNotificationKey[19] = PHOTO_WIDTHS;
      arrayOfPhotosNotificationKey[20] = PHOTO_HEIGHT;
      arrayOfPhotosNotificationKey[21] = PHOTO_HEIGHTS;
      arrayOfPhotosNotificationKey[22] = ALBUM_PAGE_URL;
      arrayOfPhotosNotificationKey[23] = IS_VIDEO;
      arrayOfPhotosNotificationKey[24] = IS_VIDEOS;
      arrayOfPhotosNotificationKey[25] = ALBUM_LABELS;
      arrayOfPhotosNotificationKey[26] = ALBUM_ACL_EXPANDED;
      arrayOfPhotosNotificationKey[27] = NUM_PHOTOS;
    }

    private PhotosNotificationKey(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static PhotosNotificationKey valueOf(int paramInt)
    {
      PhotosNotificationKey localPhotosNotificationKey;
      switch (paramInt)
      {
      default:
        localPhotosNotificationKey = null;
      case 1:
      case 21:
      case 2:
      case 22:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 23:
      case 8:
      case 24:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 25:
      case 16:
      case 26:
      case 17:
      case 18:
      case 27:
      case 19:
      case 20:
      case 28:
      }
      while (true)
      {
        return localPhotosNotificationKey;
        localPhotosNotificationKey = TAG_ID;
        continue;
        localPhotosNotificationKey = TAG_IDS;
        continue;
        localPhotosNotificationKey = PHOTO_URL;
        continue;
        localPhotosNotificationKey = PHOTO_URLS;
        continue;
        localPhotosNotificationKey = PHOTO_OWNER_OGID;
        continue;
        localPhotosNotificationKey = PHOTO_OWNER_NAME;
        continue;
        localPhotosNotificationKey = TAGGEE_OGID;
        continue;
        localPhotosNotificationKey = TAGGEE_NAME;
        continue;
        localPhotosNotificationKey = PHOTO_PAGE_URL;
        continue;
        localPhotosNotificationKey = PHOTO_PAGE_URLS;
        continue;
        localPhotosNotificationKey = PHOTO_ID;
        continue;
        localPhotosNotificationKey = PHOTO_IDS;
        continue;
        localPhotosNotificationKey = TAG_APPROVED;
        continue;
        localPhotosNotificationKey = TAGGER_OGID;
        continue;
        localPhotosNotificationKey = TAGGER_NAME;
        continue;
        localPhotosNotificationKey = ALBUM_ID;
        continue;
        localPhotosNotificationKey = STREAM_ID;
        continue;
        localPhotosNotificationKey = ALBUM_NAME;
        continue;
        localPhotosNotificationKey = PHOTO_WIDTH;
        continue;
        localPhotosNotificationKey = PHOTO_WIDTHS;
        continue;
        localPhotosNotificationKey = PHOTO_HEIGHT;
        continue;
        localPhotosNotificationKey = PHOTO_HEIGHTS;
        continue;
        localPhotosNotificationKey = ALBUM_PAGE_URL;
        continue;
        localPhotosNotificationKey = IS_VIDEO;
        continue;
        localPhotosNotificationKey = IS_VIDEOS;
        continue;
        localPhotosNotificationKey = ALBUM_LABELS;
        continue;
        localPhotosNotificationKey = ALBUM_ACL_EXPANDED;
        continue;
        localPhotosNotificationKey = NUM_PHOTOS;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static enum QuestionsNotificationKey
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<QuestionsNotificationKey> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      QUESTION_TAG = new QuestionsNotificationKey("QUESTION_TAG", 1, 2);
      REPLY_TEXT = new QuestionsNotificationKey("REPLY_TEXT", 2, 4);
      QUESTION_URL = new QuestionsNotificationKey("QUESTION_URL", 3, 7);
      DOMAIN_OF_USER = new QuestionsNotificationKey("DOMAIN_OF_USER", 4, 8);
      LEARNED_INTERESTS_HTML = new QuestionsNotificationKey("LEARNED_INTERESTS_HTML", 5, 9);
      LEARNED_INTERESTS_PLAIN = new QuestionsNotificationKey("LEARNED_INTERESTS_PLAIN", 6, 10);
      SAMPLE_QUESTION_1 = new QuestionsNotificationKey("SAMPLE_QUESTION_1", 7, 11);
      SAMPLE_QUESTION_2 = new QuestionsNotificationKey("SAMPLE_QUESTION_2", 8, 12);
      SAMPLE_QUESTION_3 = new QuestionsNotificationKey("SAMPLE_QUESTION_3", 9, 13);
      QuestionsNotificationKey[] arrayOfQuestionsNotificationKey = new QuestionsNotificationKey[10];
      arrayOfQuestionsNotificationKey[0] = QUESTION_TEXT;
      arrayOfQuestionsNotificationKey[1] = QUESTION_TAG;
      arrayOfQuestionsNotificationKey[2] = REPLY_TEXT;
      arrayOfQuestionsNotificationKey[3] = QUESTION_URL;
      arrayOfQuestionsNotificationKey[4] = DOMAIN_OF_USER;
      arrayOfQuestionsNotificationKey[5] = LEARNED_INTERESTS_HTML;
      arrayOfQuestionsNotificationKey[6] = LEARNED_INTERESTS_PLAIN;
      arrayOfQuestionsNotificationKey[7] = SAMPLE_QUESTION_1;
      arrayOfQuestionsNotificationKey[8] = SAMPLE_QUESTION_2;
      arrayOfQuestionsNotificationKey[9] = SAMPLE_QUESTION_3;
    }

    private QuestionsNotificationKey(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static QuestionsNotificationKey valueOf(int paramInt)
    {
      QuestionsNotificationKey localQuestionsNotificationKey;
      switch (paramInt)
      {
      case 3:
      case 5:
      case 6:
      default:
        localQuestionsNotificationKey = null;
      case 1:
      case 2:
      case 4:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      }
      while (true)
      {
        return localQuestionsNotificationKey;
        localQuestionsNotificationKey = QUESTION_TEXT;
        continue;
        localQuestionsNotificationKey = QUESTION_TAG;
        continue;
        localQuestionsNotificationKey = REPLY_TEXT;
        continue;
        localQuestionsNotificationKey = QUESTION_URL;
        continue;
        localQuestionsNotificationKey = DOMAIN_OF_USER;
        continue;
        localQuestionsNotificationKey = LEARNED_INTERESTS_HTML;
        continue;
        localQuestionsNotificationKey = LEARNED_INTERESTS_PLAIN;
        continue;
        localQuestionsNotificationKey = SAMPLE_QUESTION_1;
        continue;
        localQuestionsNotificationKey = SAMPLE_QUESTION_2;
        continue;
        localQuestionsNotificationKey = SAMPLE_QUESTION_3;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }

  public static enum StreamNotificationKey
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<StreamNotificationKey> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      StreamNotificationKey[] arrayOfStreamNotificationKey = new StreamNotificationKey[2];
      arrayOfStreamNotificationKey[0] = ACTOR_DISPLAY_NAME;
      arrayOfStreamNotificationKey[1] = COMMENT_ID;
    }

    private StreamNotificationKey(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static StreamNotificationKey valueOf(int paramInt)
    {
      StreamNotificationKey localStreamNotificationKey;
      switch (paramInt)
      {
      default:
        localStreamNotificationKey = null;
      case 1:
      case 2:
      }
      while (true)
      {
        return localStreamNotificationKey;
        localStreamNotificationKey = ACTOR_DISPLAY_NAME;
        continue;
        localStreamNotificationKey = COMMENT_ID;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.apps.people.notifications.proto.base.NotificationEnums
 * JD-Core Version:    0.6.2
 */