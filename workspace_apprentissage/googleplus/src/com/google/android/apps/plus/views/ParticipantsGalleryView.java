package com.google.android.apps.plus.views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.R.styleable;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsConversationsData;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.util.QuickActions;
import com.google.wireless.realtimechat.proto.Data.Participant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ParticipantsGalleryView extends FrameLayout
{
  protected Dialog avatarContextMenuDialog;
  private EsAccount mAccount;
  private CommandListener mCommandListener;
  private TextView mEmptyMessageView;
  private View mParticipantListButton;
  private ViewGroup mParticipantTrayAvatars;

  static
  {
    if (!ParticipantsGalleryView.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public ParticipantsGalleryView(Context paramContext)
  {
    this(paramContext, null);
  }

  public ParticipantsGalleryView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    LayoutInflater.from(paramContext).inflate(R.layout.participants_gallery_view, this, true);
    this.mEmptyMessageView = ((TextView)findViewById(R.id.empty_message));
    this.mParticipantTrayAvatars = ((ViewGroup)findViewById(R.id.participant_tray_avatars));
    this.mParticipantListButton = findViewById(R.id.show_participant_list_button);
    if (paramAttributeSet != null)
    {
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ParticipantsGalleryFragment);
      setBackgroundColor(localTypedArray.getColor(0, 0));
      String str = localTypedArray.getString(1);
      this.mEmptyMessageView.setText(str);
    }
    this.mParticipantListButton.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        if (ParticipantsGalleryView.this.mCommandListener != null)
          ParticipantsGalleryView.this.mCommandListener.onShowParticipantList();
      }
    });
  }

  public final OverlayedAvatarView addParticipant(LayoutInflater paramLayoutInflater, Data.Participant paramParticipant)
  {
    if (this.mAccount == null)
      throw new IllegalStateException("#setAccount needs to be called first");
    this.mEmptyMessageView.setVisibility(8);
    this.mParticipantTrayAvatars.setVisibility(0);
    OverlayedAvatarView localOverlayedAvatarView = OverlayedAvatarView.create(paramLayoutInflater, this.mParticipantTrayAvatars);
    localOverlayedAvatarView.setTag(paramParticipant);
    localOverlayedAvatarView.setParticipantType(EsConversationsData.convertParticipantType(paramParticipant));
    if (paramParticipant.hasParticipantId());
    for (String str = EsPeopleData.extractGaiaId(paramParticipant.getParticipantId()); ; str = null)
    {
      localOverlayedAvatarView.setParticipantGaiaId(str);
      this.mParticipantTrayAvatars.addView(localOverlayedAvatarView);
      localOverlayedAvatarView.setOnTouchListener(new TouchListener(localOverlayedAvatarView));
      localOverlayedAvatarView.setContentDescription(paramParticipant.getFullName());
      return localOverlayedAvatarView;
    }
  }

  public final void addParticipants(ArrayList<Data.Participant> paramArrayList)
  {
    LayoutInflater localLayoutInflater = LayoutInflater.from(getContext());
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
      addParticipant(localLayoutInflater, (Data.Participant)localIterator.next());
  }

  public final void dismissAvatarMenuDialog()
  {
    if (this.avatarContextMenuDialog != null)
    {
      this.avatarContextMenuDialog.dismiss();
      this.avatarContextMenuDialog = null;
    }
  }

  public final void removeAllParticipants()
  {
    this.mEmptyMessageView.setVisibility(0);
    this.mParticipantTrayAvatars.removeAllViews();
    this.mParticipantTrayAvatars.setVisibility(8);
  }

  public void setAccount(EsAccount paramEsAccount)
  {
    assert ((this.mAccount == null) || (this.mAccount.equals(paramEsAccount)));
    this.mAccount = paramEsAccount;
  }

  public void setBackgroundColor(int paramInt)
  {
    findViewById(R.id.root_view).setBackgroundColor(paramInt);
  }

  public void setCommandListener(CommandListener paramCommandListener)
  {
    this.mCommandListener = paramCommandListener;
  }

  public void setEmptyMessage(String paramString)
  {
    this.mEmptyMessageView.setText(paramString);
  }

  public void setParticipantActive(OverlayedAvatarView paramOverlayedAvatarView, boolean paramBoolean)
  {
    if (paramBoolean);
    for (int i = R.color.participants_gallery_active_border; ; i = 0)
    {
      paramOverlayedAvatarView.setBorderResource(i);
      return;
    }
  }

  public void setParticipantActive(String paramString, boolean paramBoolean)
  {
    for (int i = 0; ; i++)
      if (i < this.mParticipantTrayAvatars.getChildCount())
      {
        OverlayedAvatarView localOverlayedAvatarView = (OverlayedAvatarView)this.mParticipantTrayAvatars.getChildAt(i);
        if (localOverlayedAvatarView != null)
        {
          Data.Participant localParticipant = (Data.Participant)localOverlayedAvatarView.getTag();
          if ((localParticipant != null) && (localParticipant.getParticipantId().equals(paramString)))
            setParticipantActive(localOverlayedAvatarView, paramBoolean);
        }
      }
      else
      {
        return;
      }
  }

  public void setParticipantListButtonVisibility(boolean paramBoolean)
  {
    View localView = this.mParticipantListButton;
    if (paramBoolean);
    for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      return;
    }
  }

  public void setParticipantLoudestSpeaker(OverlayedAvatarView paramOverlayedAvatarView, boolean paramBoolean)
  {
    if (paramBoolean);
    for (int i = R.color.participants_gallery_loudest_speaker_border; ; i = 0)
    {
      paramOverlayedAvatarView.setBorderResource(i);
      return;
    }
  }

  public void setParticipants(HashMap<String, Data.Participant> paramHashMap, HashSet<String> paramHashSet1, HashSet<String> paramHashSet2)
  {
    removeAllParticipants();
    HashSet localHashSet = new HashSet(paramHashMap.keySet());
    LayoutInflater localLayoutInflater = LayoutInflater.from(getContext());
    dismissAvatarMenuDialog();
    Iterator localIterator1 = paramHashSet1.iterator();
    while (localIterator1.hasNext())
    {
      String str2 = (String)localIterator1.next();
      if (localHashSet.remove(str2))
        setParticipantActive(addParticipant(localLayoutInflater, (Data.Participant)paramHashMap.get(str2)), true);
    }
    Iterator localIterator2 = paramHashSet2.iterator();
    while (localIterator2.hasNext())
    {
      String str1 = (String)localIterator2.next();
      if (localHashSet.remove(str1))
        setParticipantActive(addParticipant(localLayoutInflater, (Data.Participant)paramHashMap.get(str1)), false);
    }
    Iterator localIterator3 = localHashSet.iterator();
    while (localIterator3.hasNext())
      setParticipantActive(addParticipant(localLayoutInflater, (Data.Participant)paramHashMap.get((String)localIterator3.next())), false);
  }

  private static final class AvatarContextMenuHelper
    implements ContextMenu.ContextMenuInfo, MenuItem.OnMenuItemClickListener, View.OnCreateContextMenuListener
  {
    private final EsAccount mAccount;
    private final Context mContext;
    private final Data.Participant mParticipant;

    AvatarContextMenuHelper(Context paramContext, EsAccount paramEsAccount, Data.Participant paramParticipant)
    {
      this.mContext = paramContext;
      this.mAccount = paramEsAccount;
      this.mParticipant = paramParticipant;
    }

    public final void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo)
    {
      new MenuInflater(this.mContext).inflate(R.menu.conversation_avatar_menu, paramContextMenu);
      MenuItem localMenuItem = paramContextMenu.findItem(R.id.menu_avatar_profile);
      localMenuItem.setTitle(this.mParticipant.getFullName());
      localMenuItem.setOnMenuItemClickListener(this);
    }

    public final boolean onMenuItemClick(MenuItem paramMenuItem)
    {
      Intent localIntent = Intents.getProfileActivityIntent(this.mContext, this.mAccount, this.mParticipant.getParticipantId(), null);
      this.mContext.startActivity(localIntent);
      return true;
    }
  }

  public static abstract interface CommandListener
  {
    public abstract void onAvatarClicked(OverlayedAvatarView paramOverlayedAvatarView, Data.Participant paramParticipant);

    public abstract void onAvatarDoubleClicked(OverlayedAvatarView paramOverlayedAvatarView, Data.Participant paramParticipant);

    public abstract void onShowParticipantList();
  }

  public static class SimpleCommandListener
    implements ParticipantsGalleryView.CommandListener
  {
    private final EsAccount mAccount;
    private final ParticipantsGalleryView mView;

    public SimpleCommandListener(ParticipantsGalleryView paramParticipantsGalleryView, EsAccount paramEsAccount)
    {
      if (paramParticipantsGalleryView == null)
        throw new IllegalArgumentException("view is null");
      if (paramEsAccount == null)
        throw new IllegalArgumentException("account is null");
      this.mView = paramParticipantsGalleryView;
      this.mAccount = paramEsAccount;
    }

    public final void onAvatarClicked(OverlayedAvatarView paramOverlayedAvatarView, Data.Participant paramParticipant)
    {
      ParticipantsGalleryView.AvatarContextMenuHelper localAvatarContextMenuHelper = new ParticipantsGalleryView.AvatarContextMenuHelper(this.mView.getContext(), this.mAccount, paramParticipant);
      this.mView.avatarContextMenuDialog = QuickActions.show(paramOverlayedAvatarView, paramOverlayedAvatarView, localAvatarContextMenuHelper, localAvatarContextMenuHelper, localAvatarContextMenuHelper, true, false);
    }

    public final void onAvatarDoubleClicked(OverlayedAvatarView paramOverlayedAvatarView, Data.Participant paramParticipant)
    {
      onAvatarClicked(paramOverlayedAvatarView, paramParticipant);
    }

    public void onShowParticipantList()
    {
      throw new IllegalStateException("onShowParticipantList is not supported");
    }
  }

  private final class TouchListener extends GestureDetector.SimpleOnGestureListener
    implements View.OnTouchListener
  {
    private final OverlayedAvatarView avatarView;
    private final GestureDetector gestureDetector = new GestureDetector(ParticipantsGalleryView.this.getContext(), this);

    TouchListener(OverlayedAvatarView arg2)
    {
      Object localObject;
      this.avatarView = localObject;
      this.gestureDetector.setOnDoubleTapListener(this);
    }

    public final boolean onDoubleTap(MotionEvent paramMotionEvent)
    {
      if (ParticipantsGalleryView.this.mCommandListener != null)
      {
        Data.Participant localParticipant = (Data.Participant)this.avatarView.getTag();
        ParticipantsGalleryView.this.mCommandListener.onAvatarDoubleClicked(this.avatarView, localParticipant);
      }
      return true;
    }

    public final boolean onDown(MotionEvent paramMotionEvent)
    {
      return true;
    }

    public final boolean onSingleTapConfirmed(MotionEvent paramMotionEvent)
    {
      if ((ParticipantsGalleryView.this.getParent() != null) && (ParticipantsGalleryView.this.getVisibility() == 0) && (this.avatarView.getParent() != null) && (ParticipantsGalleryView.this.mCommandListener != null))
      {
        Data.Participant localParticipant = (Data.Participant)this.avatarView.getTag();
        ParticipantsGalleryView.this.mCommandListener.onAvatarClicked(this.avatarView, localParticipant);
      }
      return true;
    }

    public final boolean onTouch(View paramView, MotionEvent paramMotionEvent)
    {
      return this.gestureDetector.onTouchEvent(paramMotionEvent);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ParticipantsGalleryView
 * JD-Core Version:    0.6.2
 */