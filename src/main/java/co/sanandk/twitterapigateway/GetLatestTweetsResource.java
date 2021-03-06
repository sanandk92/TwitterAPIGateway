/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sanandk.twitterapigateway;

import static co.sanandk.twitterapigateway.Config.twitter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import twitter4j.JSONArray;
import twitter4j.JSONObject;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

/**
 * REST Web Service
 *
 * @author SAnanda
 */
@Path("getLatestTweets")
public class GetLatestTweetsResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetLatestTweetsResource
     */
    public GetLatestTweetsResource() {
    }

    /**
     * Retrieves representation of an instance of co.sanandk.twitterapigateway.GetLatestTweetsResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("AuthToken") String AuthToken, @QueryParam("AuthSecret") String AuthSecret) {
        //TODO return proper representation object
        List<Status> statuses=null;
        int success=0;
        String str="Invalid Parameters";
        if(AuthToken!=null && AuthSecret!=null)
        {
            twitter.setOAuthAccessToken(new AccessToken(AuthToken, AuthSecret));
            try {
                statuses = twitter.getHomeTimeline();
                success=1;
            } catch (TwitterException ex) {
                Logger.getLogger(GetLatestTweetsResource.class.getName()).log(Level.SEVERE, null, ex);
                str=ex.getMessage();
            }
        }
        
        JSONObject obj=new JSONObject();
        try
        {
        obj.put("success",success);
        if(success==0)
            obj.put("error",str);
        else
        {
            JSONArray arr=new JSONArray();
            arr.put(statuses);
            obj.put("tweets", arr);
        }        
        }
        catch(Exception e)
        {
        }
        
        return obj.toString();
        
        
    }

    /**
     * PUT method for updating or creating an instance of GetLatestTweetsResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
