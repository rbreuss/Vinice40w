package cz.openstars.vinice40w;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

//@SuppressWarnings("deprecation")
class Communicator
{
public String executeHttpGets (String URL) throws Exception
{
    BufferedReader in = null;
    //String tbc = "trouba";
    try 
    {
        HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "android");
        HttpGet request = new HttpGet();
        request.setHeader("Content-Type", "text/plain; charset=utf-8");
        request.setURI(new URI(URL));
        HttpResponse response = client.execute(request);
        in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuilder sb = new StringBuilder("");
        String line;

        String NL = System.getProperty("line.separator");
        while ((line = in.readLine()) != null) 
        {
            sb.append(line + NL);
        }
        in.close();
        //System.out.println(page);
        return sb.toString();
        //return tbc;
    } 
    finally 
    {
        if (in != null) 
        {
            try 
            {
                in.close();
            } 
            catch (IOException e)
            {
                Log.d("BBB", e.toString());
            }
        }
    }
}

}