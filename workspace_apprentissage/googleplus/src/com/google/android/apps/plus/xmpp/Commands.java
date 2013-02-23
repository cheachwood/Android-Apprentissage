package com.google.android.apps.plus.xmpp;

import android.util.Base64;
import java.io.UnsupportedEncodingException;

public final class Commands
{
  public static String authenticate(String paramString)
  {
    String str = "" + paramString;
    return "<ns2:auth ns3:service='webupdates' mechanism='X-GOOGLE-TOKEN' xmlns:ns3='http://www.google.com/talk/protocol/auth' xmlns:ns2='urn:ietf:params:xml:ns:xmpp-sasl' ns3:allow-generated-jid='true' ns3:client-uses-full-bind-result='true'>" + Base64.encodeToString(encodeUtf8(str), 0) + "</ns2:auth>";
  }

  private static byte[] encodeUtf8(String paramString)
  {
    try
    {
      byte[] arrayOfByte = paramString.getBytes("UTF-8");
      return arrayOfByte;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    throw new AssertionError();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.xmpp.Commands
 * JD-Core Version:    0.6.2
 */