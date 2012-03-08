package com.msi.manning.restaurant;

import android.app.Application;

import com.msi.manning.restaurant.data.Review;
import java.util.*;

/**
 * Extend Application for global state information for an application. Access the application via
 * Activity.getApplication().
 * 
 * There are several ways to store global state information, this is one of them. Another is to
 * create a class with static members and just access it from Activities.
 * 
 * Either approach works, and there is debate about which is better. Either way, make sure to clean
 * up in life-cycle pause or destroy methods if you use resources that need cleaning up (static
 * maps, etc).
 * 
 * @author charliecollins
 */
public class RestaurantFinderApplication extends Application {

    private Review currentReview;
    private String reviewCriteriaCuisine;
    private String reviewCriteriaLocation;
    
    // See the appendix section in the Project 7 description for more detailed information on this class
    public class RecentHistory <K,V> extends LinkedHashMap<K,V> {
    	  // accessOrder = true:  LRU
    	  // accessOrder = false: FIFO
    	  public RecentHistory (int initialCapacity, float loadFactor, boolean accessOrder){
    	    super(initialCapacity, loadFactor, accessOrder);
    	  }

    	  // Remove either the LRU-based or the FIFO-based entry from TLB if TLB_SIZE has been exceeded
    	  protected boolean removeEldestEntry(Map.Entry<K,V> eldest){
    	      if (this.size() > Constants.HISTORY_SIZE)
    	        return true;
    	      else
    	        return false;
    	  }
    }
    
    private static RecentHistory<Integer, String> recentHistory;

    public RestaurantFinderApplication() {
        super();
        recentHistory = new RecentHistory<Integer, String>(Constants.HISTORY_SIZE,1,true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public Review getCurrentReview() {
        return this.currentReview;
    }

    public String getReviewCriteriaCuisine() {
        return this.reviewCriteriaCuisine;
    }

    public String getReviewCriteriaLocation() {
        return this.reviewCriteriaLocation;
    }
    
    public RecentHistory<Integer, String> getRecentHistory() {
        return this.recentHistory;
    }

    public void setCurrentReview(Review currentReview) {
        this.currentReview = currentReview;
    }

    public void setReviewCriteriaCuisine(String reviewCriteriaCuisine) {
        this.reviewCriteriaCuisine = reviewCriteriaCuisine;
    }

    public void setReviewCriteriaLocation(String reviewCriteriaLocation) {
        this.reviewCriteriaLocation = reviewCriteriaLocation;
    }
    
    public void setRecentHistory(RecentHistory<Integer, String> rH) {
    	this.recentHistory = rH;
    }
}