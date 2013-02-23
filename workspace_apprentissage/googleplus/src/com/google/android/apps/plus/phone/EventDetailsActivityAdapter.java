package com.google.android.apps.plus.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.android.common.widget.EsCompositeCursorAdapter;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.content.EsEventData.EventCoalescedFrame;
import com.google.android.apps.plus.content.EsEventData.EventPerson;
import com.google.android.apps.plus.content.EsEventData.ResolvedPerson;
import com.google.android.apps.plus.fragments.EventActiveState;
import com.google.android.apps.plus.json.EsJson;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.ColumnGridView.RecyclerListener;
import com.google.android.apps.plus.views.EventActionListener;
import com.google.android.apps.plus.views.EventActivityFrameCardLayout;
import com.google.android.apps.plus.views.EventActivityPhotoCardLayout;
import com.google.android.apps.plus.views.EventActivityUpdateCardLayout;
import com.google.android.apps.plus.views.EventDetailsCardLayout;
import com.google.android.apps.plus.views.EventUpdate;
import com.google.android.apps.plus.views.Recyclable;
import com.google.api.services.plusi.model.PlusEvent;
import com.google.api.services.plusi.model.PlusEventJson;
import java.util.HashMap;
import java.util.List;

public final class EventDetailsActivityAdapter extends EsCompositeCursorAdapter
{
  private static ScreenMetrics sScreenMetrics;
  private EventActionListener mActionListener;
  private EventActiveState mEventState;
  private boolean mLandscape;
  private HashMap<String, EsEventData.ResolvedPerson> mResolvedPeople;
  private ViewUseListener mViewUseListener;
  private boolean mWrapContent;

  public EventDetailsActivityAdapter(Context paramContext, ColumnGridView paramColumnGridView, ViewUseListener paramViewUseListener, EventActionListener paramEventActionListener)
  {
    super(paramContext, (byte)0);
    addPartition(false, false);
    addPartition(false, false);
    this.mViewUseListener = paramViewUseListener;
    this.mActionListener = paramEventActionListener;
    int j;
    int m;
    if (paramContext.getResources().getConfiguration().orientation == 2)
    {
      j = i;
      this.mLandscape = j;
      if (sScreenMetrics == null)
        sScreenMetrics = ScreenMetrics.getInstance(paramContext);
      int k = sScreenMetrics.screenDisplayType;
      boolean bool1 = false;
      if (k == 0)
      {
        boolean bool2 = this.mLandscape;
        bool1 = false;
        if (!bool2)
          bool1 = i;
      }
      this.mWrapContent = bool1;
      if (!this.mLandscape)
        break label198;
      m = i;
      label120: paramColumnGridView.setOrientation(m);
      if (sScreenMetrics.screenDisplayType != 0)
        break label204;
    }
    while (true)
    {
      paramColumnGridView.setColumnCount(i);
      paramColumnGridView.setItemMargin(sScreenMetrics.itemMargin);
      paramColumnGridView.setPadding(sScreenMetrics.itemMargin, sScreenMetrics.itemMargin, sScreenMetrics.itemMargin, sScreenMetrics.itemMargin);
      paramColumnGridView.setRecyclerListener(new ColumnGridView.RecyclerListener()
      {
        public final void onMovedToScrapHeap(View paramAnonymousView)
        {
          if ((paramAnonymousView instanceof Recyclable))
            ((Recyclable)paramAnonymousView).onRecycle();
        }
      });
      return;
      j = 0;
      break;
      label198: m = 2;
      break label120;
      label204: i = 2;
    }
  }

  protected final void bindView(View paramView, int paramInt1, Cursor paramCursor, int paramInt2)
  {
    if (paramCursor.isClosed());
    label499: 
    while (true)
    {
      return;
      switch (paramInt1)
      {
      default:
        break;
      case 0:
        EventDetailsCardLayout localEventDetailsCardLayout = (EventDetailsCardLayout)paramView;
        byte[] arrayOfByte2 = paramCursor.getBlob(1);
        PlusEvent localPlusEvent = (PlusEvent)PlusEventJson.getInstance().fromByteArray(arrayOfByte2);
        if (localPlusEvent != null)
          localEventDetailsCardLayout.bind(localPlusEvent, this.mEventState, this.mActionListener);
        break;
      case 1:
        switch (paramCursor.getInt(1))
        {
        default:
        case 5:
        case 100:
        case 1:
        case 2:
        case 3:
        case 4:
        }
        while (true)
        {
          if (this.mViewUseListener == null)
            break label499;
          this.mViewUseListener.onViewUsed(paramInt2);
          break;
          EventActivityUpdateCardLayout localEventActivityUpdateCardLayout = (EventActivityUpdateCardLayout)paramView;
          EventUpdate localEventUpdate = new EventUpdate();
          localEventUpdate.gaiaId = paramCursor.getString(2);
          localEventUpdate.ownerName = paramCursor.getString(3);
          localEventUpdate.timestamp = paramCursor.getLong(4);
          localEventUpdate.comment = paramCursor.getString(7);
          EventActionListener localEventActionListener = this.mActionListener;
          if (!this.mWrapContent);
          for (boolean bool = true; ; bool = false)
          {
            localEventActivityUpdateCardLayout.bind(localEventUpdate, localEventActionListener, bool);
            break;
          }
          EventActivityPhotoCardLayout localEventActivityPhotoCardLayout = (EventActivityPhotoCardLayout)paramView;
          String str1 = paramCursor.getString(2);
          String str2 = paramCursor.getString(3);
          String str3 = paramCursor.getString(8);
          String str4 = paramCursor.getString(6);
          byte[] arrayOfByte1 = paramCursor.getBlob(5);
          localEventActivityPhotoCardLayout.bind(str2, str1, paramCursor.getLong(4), str4, arrayOfByte1, this.mActionListener, str3);
          continue;
          EventActivityFrameCardLayout localEventActivityFrameCardLayout = (EventActivityFrameCardLayout)paramView;
          int i = paramCursor.getInt(1);
          long l = paramCursor.getLong(4);
          List localList = ((EsEventData.EventCoalescedFrame)EsEventData.EVENT_COALESCED_FRAME_JSON.fromByteArray(paramCursor.getBlob(5))).people;
          if (this.mResolvedPeople != null)
            for (int j = -1 + localList.size(); j >= 0; j--)
            {
              EsEventData.EventPerson localEventPerson = (EsEventData.EventPerson)localList.get(j);
              if (localEventPerson.gaiaId != null)
              {
                EsEventData.ResolvedPerson localResolvedPerson = (EsEventData.ResolvedPerson)this.mResolvedPeople.get(localEventPerson.gaiaId);
                if (localResolvedPerson != null)
                  localEventPerson.name = localResolvedPerson.name;
              }
            }
          localEventActivityFrameCardLayout.bind(i, l, localList, this.mActionListener);
        }
      }
    }
  }

  public final void changeActivityCursor(Cursor paramCursor)
  {
    changeCursor(1, paramCursor);
  }

  public final void changeInfoCursor(Cursor paramCursor, EventActiveState paramEventActiveState)
  {
    changeCursor(0, paramCursor);
    this.mEventState = paramEventActiveState;
  }

  protected final int getItemViewType(int paramInt1, int paramInt2)
  {
    int i = 0;
    switch (paramInt1)
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      return i;
      i = 0;
      continue;
      Cursor localCursor = getCursor(1);
      i = 0;
      if (localCursor != null)
      {
        boolean bool = localCursor.isClosed();
        i = 0;
        if (!bool)
        {
          localCursor.moveToPosition(paramInt2);
          switch (localCursor.getInt(1))
          {
          default:
            i = 0;
            break;
          case 1:
          case 2:
          case 3:
          case 4:
            i = 3;
            break;
          case 5:
            i = 2;
            break;
          case 100:
            i = 1;
          }
        }
      }
    }
  }

  public final int getViewTypeCount()
  {
    return 4;
  }

  public final boolean hasStableIds()
  {
    return false;
  }

  public final boolean isWrapContentEnabled()
  {
    return this.mWrapContent;
  }

  protected final View newView$54126883(Context paramContext, int paramInt, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    Object localObject = null;
    switch (paramInt)
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      return localObject;
      localObject = new EventDetailsCardLayout(paramContext);
      continue;
      switch (paramCursor.getInt(1))
      {
      default:
        localObject = null;
        break;
      case 1:
      case 2:
      case 3:
      case 4:
        localObject = new EventActivityFrameCardLayout(paramContext);
        if (this.mWrapContent)
          ((View)localObject).setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        break;
      case 5:
        localObject = new EventActivityUpdateCardLayout(paramContext);
        if (this.mWrapContent)
          ((View)localObject).setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        break;
      case 100:
        localObject = new EventActivityPhotoCardLayout(paramContext);
      }
    }
  }

  public final void setResolvedPeople(HashMap<String, EsEventData.ResolvedPerson> paramHashMap)
  {
    this.mResolvedPeople = paramHashMap;
  }

  public static abstract interface ViewUseListener
  {
    public abstract void onViewUsed(int paramInt);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.EventDetailsActivityAdapter
 * JD-Core Version:    0.6.2
 */