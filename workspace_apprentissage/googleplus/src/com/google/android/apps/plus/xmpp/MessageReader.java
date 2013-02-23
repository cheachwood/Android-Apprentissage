package com.google.android.apps.plus.xmpp;

import android.util.Log;
import com.google.android.apps.plus.util.EsLog;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public final class MessageReader
{
  private static String TAG = "MessageReader";
  private boolean mAuthenticationRequired;
  private boolean mBindAvailable;
  private String mEventData;
  private final LogInputStream mInputStream;
  private final XmlPullParser mParser;
  private StringBuilder mStringBuilder;
  private boolean mTlsRequired;

  public MessageReader(InputStream paramInputStream, boolean paramBoolean)
  {
    try
    {
      this.mParser = XmlPullParserFactory.newInstance().newPullParser();
      if (paramBoolean)
      {
        this.mInputStream = new LogInputStream(paramInputStream);
        this.mParser.setInput(this.mInputStream, null);
      }
      else
      {
        this.mInputStream = null;
        this.mParser.setInput(paramInputStream, null);
      }
    }
    catch (XmlPullParserException localXmlPullParserException)
    {
      throw new RuntimeException("Unable to create XML parser", localXmlPullParserException);
    }
  }

  private void updateEventData()
  {
    this.mEventData = this.mStringBuilder.toString();
    this.mStringBuilder = null;
  }

  public final String getEventData()
  {
    return this.mEventData;
  }

  public final Event read()
  {
    Event localEvent;
    do
      try
      {
        if (this.mParser.next() != 1)
        {
          int i = this.mParser.getEventType();
          localEvent = null;
          switch (i)
          {
          case 2:
            String str3 = this.mParser.getName();
            if ((str3.equals("push:data")) || (str3.equals("jid")))
              this.mStringBuilder = new StringBuilder();
            if (str3.equals("stream"))
              break label591;
            if (str3.equals("starttls"))
              this.mTlsRequired = true;
            if (str3.equals("stream:features"))
            {
              this.mTlsRequired = false;
              this.mAuthenticationRequired = false;
              this.mBindAvailable = false;
            }
            if (str3.equals("bind"))
              this.mBindAvailable = true;
            if (str3.equals("mechanisms"))
              this.mAuthenticationRequired = true;
            if (str3.equals("proceed"))
            {
              localEvent = Event.PROCEED_WITH_TLS;
            }
            else if (str3.equals("success"))
            {
              localEvent = Event.AUTHENTICATION_SUCCEEDED;
            }
            else
            {
              if (!str3.equals("failure"))
                break label591;
              localEvent = Event.AUTHENTICATION_FAILED;
            }
            break;
          case 3:
            String str2 = this.mParser.getName();
            if (str2.equals("push:data"))
            {
              updateEventData();
              localEvent = Event.DATA_RECEIVED;
            }
            else if (str2.equals("stream:features"))
            {
              if (EsLog.isLoggable(TAG, 4))
                Log.i(TAG, "Processing stream features");
              if (this.mTlsRequired)
              {
                if (EsLog.isLoggable(TAG, 4))
                  Log.i(TAG, "TLS required");
                localEvent = Event.TLS_REQUIRED;
              }
              else if (this.mAuthenticationRequired)
              {
                if (EsLog.isLoggable(TAG, 4))
                  Log.i(TAG, "Authentication required");
                localEvent = Event.AUTHENTICATION_REQUIRED;
              }
              else if (this.mBindAvailable)
              {
                if (EsLog.isLoggable(TAG, 4))
                  Log.i(TAG, "Stream is ready");
                localEvent = Event.STREAM_READY;
              }
              else
              {
                localEvent = Event.UNEXPECTED_FEATURES;
              }
            }
            else
            {
              if (!str2.equals("jid"))
                break label599;
              updateEventData();
              localEvent = Event.JID_AVAILABLE;
            }
            break;
          case 4:
            StringBuilder localStringBuilder = this.mStringBuilder;
            localEvent = null;
            if (localStringBuilder != null)
            {
              this.mStringBuilder.append(this.mParser.getText());
              localEvent = null;
            }
            break;
          }
        }
      }
      catch (Exception localException)
      {
        if (EsLog.isLoggable(TAG, 5))
          Log.w(TAG, "XML parser exception", localException);
        if (this.mInputStream != null)
        {
          String str1 = LogInputStream.getLog();
          if (EsLog.isLoggable(TAG, 5))
            Log.w(TAG, "XML Data (" + str1.length() + "): " + str1);
        }
        localEvent = Event.END_OF_STREAM;
        break;
        localEvent = Event.END_OF_STREAM;
        break;
      }
      catch (Error localError)
      {
        if (EsLog.isLoggable(TAG, 6))
          Log.e(TAG, "Error ", localError);
        localEvent = Event.END_OF_STREAM;
        break;
      }
    while (localEvent == null);
    return localEvent;
    while (true)
    {
      break;
      label591: localEvent = null;
    }
    while (true)
    {
      break;
      label599: localEvent = null;
    }
  }

  public static enum Event
  {
    static
    {
      END_OF_STREAM = new Event("END_OF_STREAM", 1);
      TLS_REQUIRED = new Event("TLS_REQUIRED", 2);
      PROCEED_WITH_TLS = new Event("PROCEED_WITH_TLS", 3);
      AUTHENTICATION_REQUIRED = new Event("AUTHENTICATION_REQUIRED", 4);
      AUTHENTICATION_SUCCEEDED = new Event("AUTHENTICATION_SUCCEEDED", 5);
      AUTHENTICATION_FAILED = new Event("AUTHENTICATION_FAILED", 6);
      STREAM_READY = new Event("STREAM_READY", 7);
      JID_AVAILABLE = new Event("JID_AVAILABLE", 8);
      DATA_RECEIVED = new Event("DATA_RECEIVED", 9);
      Event[] arrayOfEvent = new Event[10];
      arrayOfEvent[0] = UNEXPECTED_FEATURES;
      arrayOfEvent[1] = END_OF_STREAM;
      arrayOfEvent[2] = TLS_REQUIRED;
      arrayOfEvent[3] = PROCEED_WITH_TLS;
      arrayOfEvent[4] = AUTHENTICATION_REQUIRED;
      arrayOfEvent[5] = AUTHENTICATION_SUCCEEDED;
      arrayOfEvent[6] = AUTHENTICATION_FAILED;
      arrayOfEvent[7] = STREAM_READY;
      arrayOfEvent[8] = JID_AVAILABLE;
      arrayOfEvent[9] = DATA_RECEIVED;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.xmpp.MessageReader
 * JD-Core Version:    0.6.2
 */