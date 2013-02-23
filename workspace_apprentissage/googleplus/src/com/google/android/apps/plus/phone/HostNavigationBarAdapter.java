package com.google.android.apps.plus.phone;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.common.widget.EsCompositeCursorAdapter;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.DbDataAction;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.content.EsNotificationData;
import com.google.android.apps.plus.views.AvatarView;
import com.google.api.services.plusi.model.DataAction;
import com.google.api.services.plusi.model.DataActor;
import com.google.api.services.plusi.model.DataItem;
import java.util.ArrayList;
import java.util.List;

public final class HostNavigationBarAdapter extends EsCompositeCursorAdapter
{
  private static final String[] DESTINATIONS_PROJECTION = { "id", "icon", "text", "gaia_id" };
  private static MatrixCursor sEmptyCursor;
  private boolean mCollapsed = true;
  private MatrixCursor mCollapsedCursor;
  private int mCollapsedMenuItemCount;
  private int mColorRead;
  private int mColorUnread;
  private ArrayList<Object[]> mDestinationRows = new ArrayList();
  private MatrixCursor mDestinationsCursor;
  private LayoutInflater mInflater;
  private View mNotificationProgressIndicator;
  private View mNotificationRefreshButton;
  private int mUnreadNotificationCount;

  public HostNavigationBarAdapter(Context paramContext)
  {
    super(paramContext, (byte)0);
    this.mInflater = LayoutInflater.from(paramContext);
    addPartition(false, false);
    addPartition(true, true);
    addPartition(false, false);
    Resources localResources = paramContext.getResources();
    this.mColorRead = localResources.getColor(R.color.notifications_text_color_read);
    this.mColorUnread = localResources.getColor(R.color.notifications_text_color_unread);
  }

  private static void bindNotificationUserAvatar(Cursor paramCursor, AvatarView paramAvatarView, ImageView paramImageView, boolean paramBoolean)
  {
    byte[] arrayOfByte = paramCursor.getBlob(6);
    String str1 = null;
    String str2 = null;
    if (arrayOfByte != null)
    {
      List localList1 = DbDataAction.deserializeDataActionList(arrayOfByte);
      str1 = null;
      str2 = null;
      if (localList1 != null)
      {
        boolean bool1 = localList1.isEmpty();
        str1 = null;
        str2 = null;
        if (!bool1)
        {
          DataAction localDataAction = (DataAction)localList1.get(0);
          str1 = null;
          str2 = null;
          if (localDataAction != null)
          {
            List localList2 = localDataAction.item;
            str1 = null;
            str2 = null;
            if (localList2 != null)
            {
              boolean bool2 = localDataAction.item.isEmpty();
              str1 = null;
              str2 = null;
              if (!bool2)
              {
                DataItem localDataItem = (DataItem)localDataAction.item.get(0);
                str1 = null;
                str2 = null;
                if (localDataItem != null)
                {
                  DataActor localDataActor1 = localDataItem.actor;
                  str1 = null;
                  str2 = null;
                  if (localDataActor1 != null)
                  {
                    DataActor localDataActor2 = localDataItem.actor;
                    str2 = localDataActor2.obfuscatedGaiaId;
                    str1 = EsAvatarData.uncompressAvatarUrl(localDataActor2.photoUrl);
                  }
                }
              }
            }
          }
        }
      }
    }
    paramAvatarView.setRounded(true);
    paramAvatarView.setGaiaIdAndAvatarUrl(str2, str1);
    paramImageView.setVisibility(8);
    paramAvatarView.setVisibility(0);
    paramAvatarView.setDimmed(paramBoolean);
  }

  public final void addDestination(int paramInt1, int paramInt2, int paramInt3)
  {
    addDestination(paramInt1, paramInt2, this.mContext.getResources().getText(paramInt3), null);
  }

  public final void addDestination(int paramInt1, int paramInt2, CharSequence paramCharSequence, String paramString)
  {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = Integer.valueOf(paramInt1);
    arrayOfObject[1] = Integer.valueOf(paramInt2);
    arrayOfObject[2] = paramCharSequence;
    arrayOfObject[3] = paramString;
    this.mDestinationRows.add(arrayOfObject);
    this.mDestinationsCursor.addRow(arrayOfObject);
  }

  protected final void bindHeaderView$3ab248f1(View paramView)
  {
    TextView localTextView = (TextView)paramView.findViewById(R.id.text);
    View localView = paramView.findViewById(R.id.separator);
    this.mNotificationRefreshButton = paramView.findViewById(R.id.refresh_button);
    this.mNotificationProgressIndicator = paramView.findViewById(R.id.progress_indicator);
    Resources localResources = getContext().getResources();
    if (this.mUnreadNotificationCount > 0);
    for (int i = R.color.notifications_header_has_messages; ; i = R.color.notifications_header_no_messages)
    {
      int j = localResources.getColor(i);
      localTextView.setTextColor(j);
      localView.setBackgroundColor(j);
      return;
    }
  }

  protected final void bindView(View paramView, int paramInt1, Cursor paramCursor, int paramInt2)
  {
    int i = 1;
    switch (paramInt1)
    {
    default:
      return;
    case 0:
      int i3 = paramCursor.getInt(i);
      String str3 = paramCursor.getString(2);
      String str4 = paramCursor.getString(3);
      ImageView localImageView2 = (ImageView)paramView.findViewById(R.id.icon);
      AvatarView localAvatarView2 = (AvatarView)paramView.findViewById(R.id.avatar);
      if (str4 != null)
      {
        localAvatarView2.setGaiaId(str4);
        localAvatarView2.setVisibility(0);
        localImageView2.setVisibility(8);
      }
      while (true)
      {
        ((TextView)paramView.findViewById(R.id.text)).setText(str3);
        break;
        localImageView2.setImageResource(i3);
        localImageView2.setVisibility(0);
        localAvatarView2.setVisibility(8);
      }
    case 1:
    }
    int j;
    label164: ImageView localImageView1;
    AvatarView localAvatarView1;
    label203: label230: int i1;
    label308: TextView localTextView;
    if (paramCursor.getInt(11) == i)
    {
      j = i;
      localImageView1 = (ImageView)paramView.findViewById(16908294);
      localAvatarView1 = (AvatarView)paramView.findViewById(R.id.avatar);
      localImageView1.setVisibility(0);
      if (j != 0)
        break label368;
      int k = i;
      localImageView1.setEnabled(k);
      localAvatarView1.setVisibility(8);
      if (paramCursor.getInt(12) != i)
        break label374;
      int n = paramCursor.getInt(3);
      i1 = paramCursor.getInt(15);
      switch (n)
      {
      case 6:
      case 7:
      case 9:
      default:
        localTextView = (TextView)paramView.findViewById(16908308);
        localTextView.setText(paramCursor.getString(4));
        if (j == 0)
          break;
      case 1:
      case 8:
      case 2:
      case 3:
      case 4:
      case 10:
      case 5:
      case 11:
      }
    }
    for (int i2 = this.mColorRead; ; i2 = this.mColorUnread)
    {
      localTextView.setTextColor(i2);
      paramView.setContentDescription(localTextView.getText());
      break;
      j = 0;
      break label164;
      label368: int m = 0;
      break label203;
      label374: i = 0;
      break label230;
      if ((i != 0) || (EsNotificationData.isEventNotificationType(i1)))
      {
        localImageView1.setImageResource(R.drawable.ic_notification_event);
        break label308;
      }
      if (EsNotificationData.isCommentNotificationType(i1))
      {
        localImageView1.setImageResource(R.drawable.ic_notification_comment);
        break label308;
      }
      localImageView1.setImageResource(R.drawable.ic_notification_post);
      break label308;
      localImageView1.setImageResource(R.drawable.ic_notification_post);
      break label308;
      bindNotificationUserAvatar(paramCursor, localAvatarView1, localImageView1, j);
      break label308;
      localImageView1.setImageResource(R.drawable.ic_notification_photo);
      break label308;
      localImageView1.setImageResource(R.drawable.ic_notification_games);
      break label308;
      localImageView1.setImageResource(R.drawable.ic_notification_event);
      break label308;
      localImageView1.setImageResource(R.drawable.ic_notification_alert);
      break label308;
      if (i1 == 48)
      {
        bindNotificationUserAvatar(paramCursor, localAvatarView1, localImageView1, j);
        break label308;
      }
      String str1 = paramCursor.getString(20);
      String str2 = paramCursor.getString(22);
      localAvatarView1.setRounded(false);
      localAvatarView1.setGaiaIdAndAvatarUrl(str1, EsAvatarData.uncompressAvatarUrl(str2));
      localImageView1.setVisibility(8);
      localAvatarView1.setVisibility(0);
      localAvatarView1.setDimmed(j);
      break label308;
    }
  }

  public final int getDestinationId(int paramInt)
  {
    int i = -1;
    if (getPartitionForPosition(paramInt) != 0);
    while (true)
    {
      return i;
      Cursor localCursor = (Cursor)getItem(paramInt);
      if (localCursor != null)
        i = localCursor.getInt(0);
    }
  }

  protected final int getItemViewType(int paramInt1, int paramInt2)
  {
    return paramInt1;
  }

  public final int getItemViewTypeCount()
  {
    return 3;
  }

  public final int getUnreadNotificationCount()
  {
    return this.mUnreadNotificationCount;
  }

  public final void hideProgressIndicator()
  {
    if (this.mNotificationRefreshButton != null)
      this.mNotificationRefreshButton.setVisibility(0);
    if (this.mNotificationProgressIndicator != null)
      this.mNotificationProgressIndicator.setVisibility(8);
  }

  public final boolean isCollapsed()
  {
    return this.mCollapsed;
  }

  public final boolean isEnabled(int paramInt)
  {
    if (super.isEnabled(paramInt));
    for (boolean bool = true; ; bool = isNotificationHeader(paramInt))
      return bool;
  }

  protected final boolean isEnabled$255f299(int paramInt)
  {
    int i = 1;
    if ((paramInt == 0) || (paramInt == i));
    while (true)
    {
      return i;
      i = 0;
    }
  }

  public final boolean isNotificationHeader(int paramInt)
  {
    int i = 1;
    if ((getPartitionForPosition(paramInt) == i) && (getOffsetInPartition(paramInt) == -1));
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  protected final View newHeaderView$4ac0fa28(Context paramContext, int paramInt, ViewGroup paramViewGroup)
  {
    return this.mInflater.inflate(R.layout.host_notification_header, paramViewGroup, false);
  }

  protected final View newView$54126883(Context paramContext, int paramInt, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    View localView;
    switch (paramInt)
    {
    default:
      localView = null;
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      return localView;
      localView = this.mInflater.inflate(R.layout.host_navigation_item, paramViewGroup, false);
      continue;
      localView = this.mInflater.inflate(R.layout.notification_row_view, paramViewGroup, false);
      continue;
      localView = this.mInflater.inflate(R.layout.no_notifications, paramViewGroup, false);
    }
  }

  public final void removeAllDestinations()
  {
    this.mDestinationsCursor = new MatrixCursor(DESTINATIONS_PROJECTION);
    this.mDestinationRows.clear();
    this.mCollapsedCursor = null;
  }

  public final void setCollapsed(boolean paramBoolean)
  {
    if (this.mCollapsed != paramBoolean)
    {
      this.mCollapsed = paramBoolean;
      showDestinations();
    }
  }

  public final void setCollapsedMenuItemCount(int paramInt)
  {
    if (this.mCollapsedMenuItemCount != paramInt)
    {
      this.mCollapsedMenuItemCount = paramInt;
      this.mCollapsedCursor = null;
      showDestinations();
    }
  }

  public final void setNotifications(Cursor paramCursor)
  {
    changeCursor(1, paramCursor);
    int i;
    if ((paramCursor == null) || (paramCursor.getCount() == 0))
    {
      i = 1;
      if (i == 0)
        break label135;
      if (sEmptyCursor == null)
      {
        MatrixCursor localMatrixCursor2 = new MatrixCursor(new String[] { "empty" });
        sEmptyCursor = localMatrixCursor2;
        localMatrixCursor2.addRow(new Object[] { "empty" });
      }
    }
    label135: for (MatrixCursor localMatrixCursor1 = sEmptyCursor; ; localMatrixCursor1 = null)
    {
      changeCursor(2, localMatrixCursor1);
      this.mUnreadNotificationCount = 0;
      if ((paramCursor != null) && (paramCursor.moveToFirst()))
        do
          if (paramCursor.getInt(11) != 1)
            this.mUnreadNotificationCount = (1 + this.mUnreadNotificationCount);
        while (paramCursor.moveToNext());
      return;
      i = 0;
      break;
    }
  }

  public final void showDestinations()
  {
    if ((this.mCollapsed) && (this.mDestinationsCursor != null) && (this.mCollapsedMenuItemCount != 0) && (this.mDestinationsCursor.getCount() > this.mCollapsedMenuItemCount))
    {
      if (this.mCollapsedCursor == null)
      {
        this.mCollapsedCursor = new MatrixCursor(DESTINATIONS_PROJECTION);
        for (int i = 0; i < -1 + this.mCollapsedMenuItemCount; i++)
          this.mCollapsedCursor.addRow((Object[])this.mDestinationRows.get(i));
        String str = getContext().getString(R.string.expand_menu_label);
        MatrixCursor localMatrixCursor = this.mCollapsedCursor;
        Object[] arrayOfObject = new Object[4];
        arrayOfObject[0] = Integer.valueOf(-2);
        arrayOfObject[1] = Integer.valueOf(R.drawable.ic_down_white);
        arrayOfObject[2] = str;
        arrayOfObject[3] = null;
        localMatrixCursor.addRow(arrayOfObject);
      }
      changeCursor(0, this.mCollapsedCursor);
    }
    while (true)
    {
      return;
      changeCursor(0, this.mDestinationsCursor);
    }
  }

  public final void showProgressIndicator()
  {
    if (this.mNotificationProgressIndicator != null)
      this.mNotificationProgressIndicator.setVisibility(0);
    if (this.mNotificationRefreshButton != null)
      this.mNotificationRefreshButton.setVisibility(8);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.HostNavigationBarAdapter
 * JD-Core Version:    0.6.2
 */