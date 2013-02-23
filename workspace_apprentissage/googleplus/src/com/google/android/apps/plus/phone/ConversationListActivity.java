package com.google.android.apps.plus.phone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ConversationListActivity extends Activity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Intent localIntent = Intents.getMessengerActivityIntent(this, null);
    localIntent.addFlags(302055424);
    if ((!ConversationActivity.hasInstance()) && (!NewConversationActivity.hasInstance()))
      localIntent.addFlags(67108864);
    startActivity(localIntent);
    finish();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.ConversationListActivity
 * JD-Core Version:    0.6.2
 */