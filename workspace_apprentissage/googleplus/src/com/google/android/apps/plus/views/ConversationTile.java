package com.google.android.apps.plus.views;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.phone.ConversationActivity;
import com.google.android.apps.plus.realtimechat.RealTimeChatService;
import com.google.android.apps.plus.realtimechat.RealTimeChatServiceListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ConversationTile extends RelativeLayout
  implements Tile
{
  List<Tile.ParticipantPresenceListener> listeners;
  private HashSet<String> mActiveParticipantIds = new HashSet();
  private Long mConversationRowId = null;
  private EditText mEditText;
  private RealTimeChatServiceListener rtcListener = new RTCServiceListener((byte)0);

  public ConversationTile(Context paramContext)
  {
    this(paramContext, null);
  }

  public ConversationTile(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    addView(((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(R.layout.conversation_tile, null));
    this.mEditText = ((EditText)findViewById(R.id.message_text));
  }

  public final void addParticipantPresenceListener(Tile.ParticipantPresenceListener paramParticipantPresenceListener)
  {
    if (this.listeners == null)
      this.listeners = new LinkedList();
    this.listeners.add(paramParticipantPresenceListener);
  }

  public final HashSet<String> getActiveParticipantIds()
  {
    return this.mActiveParticipantIds;
  }

  public final void onCreate(Bundle paramBundle)
  {
  }

  public final void onPause()
  {
    RealTimeChatService.unregisterListener(this.rtcListener);
  }

  public final void onResume()
  {
    RealTimeChatService.registerListener(this.rtcListener);
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
  }

  public final void onStart()
  {
  }

  public final void onStop()
  {
  }

  public final void onTilePause()
  {
    if (this.mConversationRowId != null)
      RealTimeChatService.sendPresenceRequest(getContext(), ((ConversationActivity)getContext()).getAccount(), this.mConversationRowId.longValue(), false, false);
    ((InputMethodManager)getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.mEditText.getWindowToken(), 0);
  }

  public final void onTileResume()
  {
    if (this.mConversationRowId != null)
      RealTimeChatService.sendPresenceRequest(getContext(), ((ConversationActivity)getContext()).getAccount(), this.mConversationRowId.longValue(), true, true);
  }

  public final void onTileStart()
  {
  }

  public final void onTileStop()
  {
  }

  public void setConversationRowId(Long paramLong)
  {
    this.mConversationRowId = paramLong;
  }

  private final class RTCServiceListener extends RealTimeChatServiceListener
  {
    private RTCServiceListener()
    {
    }

    public final void onUserPresenceChanged(long paramLong, String paramString, boolean paramBoolean)
    {
      if (paramBoolean)
        ConversationTile.this.mActiveParticipantIds.add(paramString);
      while ((ConversationTile.this.mConversationRowId != null) && (paramLong == ConversationTile.this.mConversationRowId.longValue()))
      {
        Iterator localIterator = ConversationTile.this.listeners.iterator();
        while (localIterator.hasNext())
          ((Tile.ParticipantPresenceListener)localIterator.next()).onParticipantPresenceChanged();
        ConversationTile.this.mActiveParticipantIds.remove(paramString);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ConversationTile
 * JD-Core Version:    0.6.2
 */