package com.openclassrooms.watchlist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WatchlistController {
    private List<WatchlistItem> watchlistItems=new ArrayList<>();
    private static int index=1;
    @GetMapping("/watchlistItemForm")
    public ModelAndView showWatchlistItemForm(@RequestParam(required=false) Integer id) {
        Map<String, Object> model = new HashMap<String, Object>();

        WatchlistItem watchlistItem = findWatchlistItemById(id);
        if (watchlistItem == null) {
            model.put("watchlistItem", new WatchlistItem());
        } else {
            model.put("watchlistItem", watchlistItem);
        }
        return new ModelAndView("watchlistItemForm" , model);
    }

    private WatchlistItem findWatchlistItemById(Integer id) {
        for (WatchlistItem watchlistItem : watchlistItems) {
            if (watchlistItem.getId().equals(id)) {
                return watchlistItem;
            }
        }
        return null;
    }
@PostMapping("watchlistItemForm")
   public ModelAndView submitWatchlistItemForm (WatchlistItem watchlistItem){

        WatchlistItem existingItem=findWatchlistItemById(watchlistItem.getId());
        if(existingItem==null){
            watchlistItem.setId(index++);
            watchlistItems.add(watchlistItem);
        }else{
            existingItem.setTitle(watchlistItem.getTitle());
            existingItem.setRating(watchlistItem.getRating());
            existingItem.setRating(watchlistItem.getRating());
            existingItem.setComment(watchlistItem.getComment());
        }


       RedirectView redirect= new RedirectView();
       redirect.setUrl("/watchlist");
       return new ModelAndView(redirect);
   }

    @GetMapping("/watchlist")
    public ModelAndView getWatchlist(){

       watchlistItems.clear();
        watchlistItems.add(new WatchlistItem(index++,"Lion King","9","High","Hakuna matata"));
        watchlistItems.add(new WatchlistItem(index++,"Madacaskar","7.9","High","olla mundo"));
        watchlistItems.add(new WatchlistItem(index++,"Anbesa door","6.8","medium","Hayeta"));
        watchlistItems.add(new WatchlistItem(index++,"Nimo","5.9","low","fishy"));

        String viewName="Watchlist";
 Map<String,Object> model=new HashMap<>();
 model.put("watchlistItems",watchlistItems);
 model.put("numberOfMovies",watchlistItems.size());
 return new ModelAndView(viewName,model);
    }
}
